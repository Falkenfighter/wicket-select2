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

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONStringer;

import com.vaynberg.wicket.select2.json.Json;

/**
 * Select2 settings. Refer to the Select2 documentation for what these options mean.
 * 
 * @author igor
 */
public final class Settings implements Serializable {

    private Integer minimumInputLength, minimumResultsForSearch;
    private Integer maximumSelectionSize;
    private Object placeholder;
    private Boolean allowClear;
    private Boolean multiple;
    private Boolean closeOnSelect;
    private String id, matcher, tokenizer;
    private String formatSelection, formatSelectionTooBig, formatResult, formatNoMatches, formatInputTooShort,
	    formatResultCssClass, formatLoadMore, formatSearching;
    private String createSearchChoice;
    private String initSelection;
    private String query;
    private String width;
    private Boolean openOnEnter;
    private String containerCss, dropdownCss, containerCssClass, dropdownCssClass;

    private AjaxSettings ajax;
    private String data;
    private String tags;
    private String separator;
    private String[] tokenSeparators;

    public CharSequence toJson() {
	try {
	    JSONStringer writer = new JSONStringer();
	    writer.object();
	    Json.writeObject(writer, "minimumInputLength", minimumInputLength);
	    Json.writeObject(writer, "minimumResultsForSearch", minimumResultsForSearch);
	    Json.writeObject(writer, "maximumSelectionSize", maximumSelectionSize);
	    Json.writeObject(writer, "placeholder", placeholder);
	    Json.writeObject(writer, "allowClear", allowClear);
	    Json.writeObject(writer, "multiple", multiple);
	    Json.writeObject(writer, "closeOnSelect", closeOnSelect);
	    Json.writeFunction(writer, "id", id);
	    Json.writeFunction(writer, "matcher", matcher);
	    Json.writeFunction(writer, "tokenizer", tokenizer);
	    Json.writeFunction(writer, "formatSelection", formatSelection);
	    Json.writeFunction(writer, "formatResult", formatResult);
	    Json.writeFunction(writer, "formatNoMatches", formatNoMatches);
	    Json.writeFunction(writer, "formatInputTooShort", formatInputTooShort);
	    Json.writeFunction(writer, "formatResultCssClass", formatResultCssClass);
	    Json.writeFunction(writer, "formatSelectionTooBig", formatSelectionTooBig);
	    Json.writeFunction(writer, "formatLoadMore", formatLoadMore);
	    Json.writeFunction(writer, "formatSearching", formatSearching);
	    Json.writeFunction(writer, "createSearchChoice", createSearchChoice);
	    Json.writeFunction(writer, "initSelection", initSelection);
	    Json.writeFunction(writer, "query", query);
	    Json.writeObject(writer, "width", width);
	    Json.writeObject(writer, "openOnEnter", openOnEnter);
	    Json.writeObject(writer, "containerCss", containerCss);
	    Json.writeObject(writer, "containerCssClass", containerCssClass);
	    Json.writeObject(writer, "dropdownCss", dropdownCss);
	    Json.writeObject(writer, "dropdownCssClass", dropdownCssClass);
	    Json.writeObject(writer, "separator", separator);
	    Json.writeObject(writer, "tokenSeparators", tokenSeparators);
	    writer.key("ajax");
	    ajax.toJson(writer);

	    Json.writeFunction(writer, "data", data);
	    Json.writeFunction(writer, "tags", tags);
	    writer.endObject();

	    return writer.toString();
	} catch (JSONException e) {
	    throw new RuntimeException("Could not convert Select2 settings object to Json", e);
	}
    }

    public Integer getMinimumInputLength() {
	return minimumInputLength;
    }

    public void setMinimumInputLength(Integer minimumInputLength) {
	this.minimumInputLength = minimumInputLength;
    }

    public Integer getMinimumResultsForSearch() {
	return minimumResultsForSearch;
    }

    public void setMinimumResultsForSearch(Integer minimumResultsForSearch) {
	this.minimumResultsForSearch = minimumResultsForSearch;
    }

    public Object getPlaceholder() {
	return placeholder;
    }

    public void setPlaceholder(Object placeholder) {
	this.placeholder = placeholder;
    }

    public Boolean getAllowClear() {
	return allowClear;
    }

    public void setAllowClear(Boolean allowClear) {
	this.allowClear = allowClear;
    }

    public Boolean getMultiple() {
	return multiple;
    }

    public void setMultiple(Boolean multiple) {
	this.multiple = multiple;
    }

    public Boolean getCloseOnSelect() {
	return closeOnSelect;
    }

    public void setCloseOnSelect(Boolean closeOnSelect) {
	this.closeOnSelect = closeOnSelect;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getFormatSelection() {
	return formatSelection;
    }

    public void setFormatSelection(String formatSelection) {
	this.formatSelection = formatSelection;
    }

    public String getFormatResult() {
	return formatResult;
    }

    public void setFormatResult(String formatResult) {
	this.formatResult = formatResult;
    }

    public String getFormatNoMatches() {
	return formatNoMatches;
    }

    public void setFormatNoMatches(String formatNoMatches) {
	this.formatNoMatches = formatNoMatches;
    }

    public String getFormatInputTooShort() {
	return formatInputTooShort;
    }

    public void setFormatInputTooShort(String formatInputTooShort) {
	this.formatInputTooShort = formatInputTooShort;
    }

    public String getCreateSearchChoice() {
	return createSearchChoice;
    }

    public void setCreateSearchChoice(String createSearchChoice) {
	this.createSearchChoice = createSearchChoice;
    }

    public String getInitSelection() {
	return initSelection;
    }

    public void setInitSelection(String initSelection) {
	this.initSelection = initSelection;
    }

    public String getQuery() {
	return query;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    public AjaxSettings getAjax() {
	return getAjax(false);
    }

    public AjaxSettings getAjax(boolean createIfNotSet) {
	if (createIfNotSet) {
	    ajax = new AjaxSettings();
	}
	return ajax;
    }

    public void setAjax(AjaxSettings ajax) {
	this.ajax = ajax;
    }

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }

    public String getTags() {
	return tags;
    }

    public void setTags(String tags) {
	this.tags = tags;
    }

}
