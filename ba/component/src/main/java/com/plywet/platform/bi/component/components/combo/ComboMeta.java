package com.plywet.platform.bi.component.components.combo;

import java.util.HashMap;
import java.util.Map;

import com.plywet.platform.bi.component.components.validate.ValidateBoxMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIJSONException;

public class ComboMeta extends ValidateBoxMeta implements
		ComponentMetaInterface {

	public static final String ATTR_WIDTH = "width";
	public static final String ATTR_HEIGHT = "height";
	public static final String ATTR_SEPARATOR = "separator";
	public static final String ATTR_PANEL_WIDTH = "panelWidth";
	public static final String ATTR_PANEL_HEIGHT = "panelHeight";
	public static final String ATTR_MULTIPLE = "multiple";
	public static final String ATTR_EDITABLE = "editable";
	public static final String ATTR_DISABLED = "disabled";
	public static final String ATTR_HAS_DOWN_ARROW = "hasDownArrow";
	public static final String ATTR_KEY_HANDLER = "keyHandler";
	public static final String ATTR_KEY_HANDLER_UP = "up";
	public static final String ATTR_KEY_HANDLER_DOWN = "down";
	public static final String ATTR_KEY_HANDLER_ENTER = "enter";
	public static final String ATTR_KEY_HANDLER_QUERY = "query";
	public static final String ATTR_ON_SHOW_PANEL = "onShowPanel";
	public static final String ATTR_ON_HIDE_PANEL = "onHidePanel";
	public static final String ATTR_ON_CHANGE = "onChange";

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_COMBO;
	}

	public Integer getWidth() {
		return (Integer) this.getAttribute(ATTR_WIDTH);
	}

	public Integer getHeight() {
		return (Integer) this.getAttribute(ATTR_HEIGHT);
	}

	public String getSeparator() {
		return (String) this.getAttribute(ATTR_SEPARATOR);
	}

	public Integer getPanelWidth() {
		return (Integer) this.getAttribute(ATTR_PANEL_WIDTH);
	}

	public Integer getPanelHeight() {
		return (Integer) this.getAttribute(ATTR_PANEL_HEIGHT);
	}

	public Boolean getMultiple() {
		return (Boolean) this.getAttribute(ATTR_MULTIPLE);
	}

	public Boolean getEditable() {
		return (Boolean) this.getAttribute(ATTR_EDITABLE);
	}

	public Boolean getDisabled() {
		return (Boolean) this.getAttribute(ATTR_DISABLED);
	}

	public Boolean getHasDownArrow() {
		return (Boolean) this.getAttribute(ATTR_HAS_DOWN_ARROW);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getKeyHandler() {
		return (Map<String, String>) this.getAttribute(ATTR_KEY_HANDLER);
	}

	public String getKeyHandlerUp() {
		return (String) this.getAttribute(ATTR_KEY_HANDLER_UP);
	}

	public String getKeyHandlerDown() {
		return (String) this.getAttribute(ATTR_KEY_HANDLER_DOWN);
	}

	public String getKeyHandlerEnter() {
		return (String) this.getAttribute(ATTR_KEY_HANDLER_ENTER);
	}

	public String getKeyHandlerQuery() {
		return (String) this.getAttribute(ATTR_KEY_HANDLER_QUERY);
	}

	public String getOnShowPanel() {
		return (String) this.getAttribute(ATTR_ON_SHOW_PANEL);
	}

	public String getOnHidePanel() {
		return (String) this.getAttribute(ATTR_ON_HIDE_PANEL);
	}

	public String getOnChange() {
		return (String) this.getAttribute(ATTR_ON_CHANGE);
	}

	public ComboMeta setWidth(Integer val) throws BIJSONException {
		this.addAttribute(ATTR_WIDTH, val);
		return this;
	}

	public ComboMeta setHeight(Integer val) throws BIJSONException {
		this.addAttribute(ATTR_HEIGHT, val);
		return this;
	}

	public ComboMeta setSeparator(String val) throws BIJSONException {
		this.addAttribute(ATTR_SEPARATOR, val);
		return this;
	}

	public ComboMeta setPanelWidth(Integer val) throws BIJSONException {
		this.addAttribute(ATTR_PANEL_WIDTH, val);
		return this;
	}

	public ComboMeta setPanelHeight(Integer val) throws BIJSONException {
		this.addAttribute(ATTR_PANEL_HEIGHT, val);
		return this;
	}

	public ComboMeta setMultiple(Boolean val) throws BIJSONException {
		this.addAttribute(ATTR_MULTIPLE, val);
		return this;
	}

	public ComboMeta setEditable(Boolean val) throws BIJSONException {
		this.addAttribute(ATTR_EDITABLE, val);
		return this;
	}

	public ComboMeta setDisabled(Boolean val) throws BIJSONException {
		this.addAttribute(ATTR_DISABLED, val);
		return this;
	}

	public ComboMeta setHasDownArrow(Boolean val) throws BIJSONException {
		this.addAttribute(ATTR_HAS_DOWN_ARROW, val);
		return this;
	}

	public ComboMeta setKeyHandler(Map<String, String> val)
			throws BIJSONException {
		this.addAttribute(ATTR_KEY_HANDLER_UP, val);
		return this;
	}

	public ComboMeta setKeyHandlerUp(String val) throws BIJSONException {
		Map<String, String> keyHandler = this.getKeyHandler();
		if (keyHandler == null) {
			keyHandler = new HashMap<String, String>();
			this.setKeyHandler(keyHandler);
		}
		keyHandler.put(ATTR_KEY_HANDLER_UP, val);
		return this;
	}

	public ComboMeta setKeyHandlerDown(String val) throws BIJSONException {
		Map<String, String> keyHandler = this.getKeyHandler();
		if (keyHandler == null) {
			keyHandler = new HashMap<String, String>();
			this.setKeyHandler(keyHandler);
		}
		keyHandler.put(ATTR_KEY_HANDLER_DOWN, val);
		return this;
	}

	public ComboMeta setKeyHandlerEnter(String val) throws BIJSONException {
		Map<String, String> keyHandler = this.getKeyHandler();
		if (keyHandler == null) {
			keyHandler = new HashMap<String, String>();
			this.setKeyHandler(keyHandler);
		}
		keyHandler.put(ATTR_KEY_HANDLER_ENTER, val);
		return this;
	}

	public ComboMeta setKeyHandlerQuery(String val) throws BIJSONException {
		Map<String, String> keyHandler = this.getKeyHandler();
		if (keyHandler == null) {
			keyHandler = new HashMap<String, String>();
			this.setKeyHandler(keyHandler);
		}
		keyHandler.put(ATTR_KEY_HANDLER_QUERY, val);
		return this;
	}

	public ComboMeta setOnShowPanel(String val) throws BIJSONException {
		this.addAttribute(ATTR_ON_SHOW_PANEL, val);
		return this;
	}

	public ComboMeta setOnHidePanel(String val) throws BIJSONException {
		this.addAttribute(ATTR_ON_HIDE_PANEL, val);
		return this;
	}

	public ComboMeta setOnChange(String val) throws BIJSONException {
		this.addAttribute(ATTR_ON_CHANGE, val);
		return this;
	}

}
