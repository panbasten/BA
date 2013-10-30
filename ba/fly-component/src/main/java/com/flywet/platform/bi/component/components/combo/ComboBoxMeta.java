package com.flywet.platform.bi.component.components.combo;

import java.util.List;
import java.util.Map;

import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

public class ComboBoxMeta extends ComboMeta implements ComponentMetaInterface {

	public static final String ATTR_VALUE_FIELD = "valueField";
	public static final String ATTR_TEXT_FIELD = "textField";
	public static final String ATTR_MODE = "mode";
	public static final String ATTR_MODE_LOCAL = "local";
	public static final String ATTR_MODE_REMOTE = "remote";

	public static final String ATTR_METHOD = "method";
	public static final String ATTR_URL = "url";
	public static final String ATTR_DATA = "data";

	public static final String ATTR_FILTER = "filter";
	public static final String ATTR_FORMATTER = "formatter";
	public static final String ATTR_LOADER = "loader";
	public static final String ATTR_ON_BEFORE_LOAD = "onBeforeLoad";
	public static final String ATTR_ON_LOAD_SUCCESS = "onLoadSuccess";
	public static final String ATTR_ON_LOAD_ERROR = "onLoadError";
	public static final String ATTR_ON_SELECT = "onSelect";
	public static final String ATTR_ON_UNSELECT = "onUnselect";

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_COMBO_BOX;
	}

	public String getValueField() {
		return (String) this.getAttribute(ATTR_VALUE_FIELD);
	}

	public String getTextField() {
		return (String) this.getAttribute(ATTR_TEXT_FIELD);
	}

	public String getMode() {
		return (String) this.getAttribute(ATTR_MODE);
	}

	public String getMethod() {
		return (String) this.getAttribute(ATTR_METHOD);
	}

	public String getUrl() {
		return (String) this.getAttribute(ATTR_URL);
	}

	public String getFilter() {
		return (String) this.getAttribute(ATTR_FILTER);
	}

	public String getFormatter() {
		return (String) this.getAttribute(ATTR_FORMATTER);
	}

	public String getLoader() {
		return (String) this.getAttribute(ATTR_LOADER);
	}

	public String getOnBeforeLoad() {
		return (String) this.getAttribute(ATTR_ON_BEFORE_LOAD);
	}

	public String getOnLoadSuccess() {
		return (String) this.getAttribute(ATTR_ON_LOAD_SUCCESS);
	}

	public String getOnLoadError() {
		return (String) this.getAttribute(ATTR_ON_LOAD_ERROR);
	}

	public String getOnSelect() {
		return (String) this.getAttribute(ATTR_ON_SELECT);
	}

	public String getOnUnselect() {
		return (String) this.getAttribute(ATTR_ON_UNSELECT);
	}

	public ComboBoxMeta setLocalData(List<? extends Object> list)
			throws BIJSONException {
		this.addAttribute(ATTR_MODE, ATTR_MODE_LOCAL);
		this.setData(list);
		return this;
	}

	public ComboBoxMeta setRemoteData(String method, String url,
			Map<String, Object> data) throws BIJSONException {
		this.addAttribute(ATTR_MODE, ATTR_MODE_REMOTE);
		this.addAttribute(ATTR_METHOD, method);
		this.addAttribute(ATTR_URL, url);
		this.setData(data);
		return this;
	}

	public ComboBoxMeta setValueField(String val) throws BIJSONException {
		this.addAttribute(ATTR_VALUE_FIELD, val);
		return this;
	}

	public ComboBoxMeta setTextField(String val) throws BIJSONException {
		this.addAttribute(ATTR_TEXT_FIELD, val);
		return this;
	}

	public ComboBoxMeta setFilter(String val) throws BIJSONException {
		this.addAttribute(ATTR_FILTER, val);
		return this;
	}

	public ComboBoxMeta setFormatter(String val) throws BIJSONException {
		this.addAttribute(ATTR_FORMATTER, val);
		return this;
	}

	public ComboBoxMeta setLoader(String val) throws BIJSONException {
		this.addAttribute(ATTR_LOADER, val);
		return this;
	}

	public ComboBoxMeta setOnBeforeLoad(String val) throws BIJSONException {
		this.addAttribute(ATTR_ON_BEFORE_LOAD, val);
		return this;
	}

	public ComboBoxMeta setOnLoadSuccess(String val) throws BIJSONException {
		this.addAttribute(ATTR_ON_LOAD_SUCCESS, val);
		return this;
	}

	public ComboBoxMeta setOnLoadError(String val) throws BIJSONException {
		this.addAttribute(ATTR_ON_LOAD_ERROR, val);
		return this;
	}

	public ComboBoxMeta setOnSelect(String val) throws BIJSONException {
		this.addAttribute(ATTR_ON_SELECT, val);
		return this;
	}

	public ComboBoxMeta setOnUnselect(String val) throws BIJSONException {
		this.addAttribute(ATTR_ON_UNSELECT, val);
		return this;
	}

}
