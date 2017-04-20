/*
 * Copyright 2012 Igor Vaynberg
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.vaynberg.wicket.select2;

import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.http.WebResponse;
import org.json.JSONException;
import org.json.JSONWriter;

/**
 * Base class for Select2 components
 *
 * @param <T> type of choice object
 * @param <M> type of model object
 * @author igor
 */
abstract class AbstractSelect2Choice<T, M> extends Select2ChoiceBaseComponent<T, M> implements IResourceListener {

    /**
     * Constructor
     *
     * @param id component id
     */
    public AbstractSelect2Choice(String id) {
        this(id, null, null);
    }

    /**
     * Constructor
     *
     * @param id    component id
     * @param model component model
     */
    public AbstractSelect2Choice(String id, IModel<M> model) {
        this(id, model, null);
    }

    /**
     * Constructor.
     *
     * @param id       component id
     * @param provider choice provider
     */
    public AbstractSelect2Choice(String id, ChoiceProvider<T> provider) {
        this(id, null, provider);
    }

    /**
     * Constructor
     *
     * @param id       component id
     * @param model    component model
     * @param provider choice provider
     */
    public AbstractSelect2Choice(String id, IModel<M> model, ChoiceProvider<T> provider) {
        super(id, model, provider);
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        getSettings().getAjax().setUrl(urlFor(IResourceListener.INTERFACE, null));
    }

    @Override
    public void onResourceRequested() {
        final Response<T> response = new Response<T>();
        query(response);

        final OutputStreamWriter out = getOutputStreamWriter();
        final JSONWriter json = new JSONWriter(out);

        writeResponse(json, response);

        try {
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not write Json to servlet response", e);
        }
    }

    @Override
    protected void onDetach() {
        getProvider().detach();
        super.onDetach();
    }

    @Override
    protected boolean getStatelessHint() {
        return false;
    }

    /**
     * Handles building the JSON structure to provide to Select2.
     *
     * <pre>{@code
     * {
     *   "results": [
     *     {
     *       "id": 1,
     *       "text": "Some Text
     *     }
     *   ],
     *   "more": true
     * }
     * }</pre>
     *
     * @param json
     * @param response
     */
    protected void writeResponse(final JSONWriter json, final Response<T> response) {
        try {
            json.object().key("results").array();
            writeValues(json, response);
            json.endArray().key("more").value(response.getHasMore()).endObject();
        } catch (JSONException e) {
            throw new RuntimeException("Could not write Json response", e);
        }
    }

    /**
     * Updates the response object with the values matching the query. Matching is done in the
     * provided {@link ChoiceProvider#query(String, int, Response)} implementation
     *
     * @param response the response to update
     */
    protected void query(final Response<T> response) {
        // this is the callback that retrieves matching choices used to populate the dropdown
        final IRequestParameters params = getRequestCycle().getRequest().getRequestParameters();

        // select2 uses 1-based paging, but in wicket world we are used to 0-based
        final int page = params.getParameterValue("page").toInt(1) - 1;

        // retrieve choices matching the search term
        final String term = params.getParameterValue("term").toOptionalString();
        getProvider().query(term, page, response);
    }

    /**
     * Sets the response type to application/json before converting it into an {@link OutputStreamWriter}
     *
     * @return the response as a new {@link OutputStreamWriter}
     */
    protected OutputStreamWriter getOutputStreamWriter() {
        final WebResponse webResponse = (WebResponse) getRequestCycle().getResponse();
        webResponse.setContentType("application/json");
        return new OutputStreamWriter(webResponse.getOutputStream(), getRequest().getCharset());
    }

    protected void writeValues(final JSONWriter json, final Iterable<T> response) throws JSONException {
        for (T item : response) {
            json.object();
            getProvider().toJson(item, json);
            json.endObject();
        }
    }
}
