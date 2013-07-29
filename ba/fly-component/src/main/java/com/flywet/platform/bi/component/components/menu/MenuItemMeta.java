package com.flywet.platform.bi.component.components.menu;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.components.ComplexComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

public class MenuItemMeta extends ComplexComponentMeta implements
		ComponentMetaInterface {

	public static final String ATTR_MENU_ITEMS = "menuItems";

	public MenuItemMeta setText(String val) throws BIJSONException {
		this.addAttribute(HTML.ATTR_TEXT, val);
		return this;
	}

	public String getText() throws BIJSONException {
		return (String) this.getAttribute(HTML.ATTR_TEXT);
	}

	public MenuItemMeta setIconCls(String val) throws BIJSONException {
		this.addAttribute(HTML.ATTR_ICON_CLASS, val);
		return this;
	}

	public String getIconCls() throws BIJSONException {
		return (String) this.getAttribute(HTML.ATTR_ICON_CLASS);
	}

	public MenuItemMeta setOnClick(String val) throws BIJSONException {
		this.addAttribute(HTML.ATTR_ON_CLICK, val);
		return this;
	}

	public String getOnClick() throws BIJSONException {
		return (String) this.getAttribute(HTML.ATTR_ON_CLICK);
	}

	@Override
	public String[] getAttributesName() {
		return null;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_MENU_ITEM;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormJo() throws BIJSONException {
		JSONObject formJo = super.getAttrbuteJo();
		formJo.put(HTML.ATTR_ID, this.getId());
		if (this.getContents() != null && this.getContents().size() > 0) {
			JSONArray sub = new JSONArray();
			for (ComponentMetaInterface dataMeta : this.getContents()) {
				if (dataMeta != null) {
					if (dataMeta instanceof ComplexComponentMeta) {
						if (((ComplexComponentMeta) dataMeta).isMultiRoot()) {
							sub.addAll(((ComplexComponentMeta) dataMeta)
									.getFormJa());
						} else {
							sub.add(dataMeta.getFormJo());
						}
					} else {
						sub.add(dataMeta.getFormJo());
					}
				}
			}
			formJo.put(ATTR_MENU_ITEMS, sub);
		}
		return formJo;
	}

}
