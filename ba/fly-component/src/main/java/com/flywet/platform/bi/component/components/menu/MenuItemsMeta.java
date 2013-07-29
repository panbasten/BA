package com.flywet.platform.bi.component.components.menu;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.components.ComplexComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

public class MenuItemsMeta extends ComplexComponentMeta implements
		ComponentMetaInterface {

	public static final String ATTR_ITEM_WIDTH = "itemWidth";
	public static final String ATTR_MENU_ITEMS = "menuItems";
	
	public MenuItemsMeta setItemWidth(String val) throws BIJSONException {
		this.addAttribute(ATTR_ITEM_WIDTH, val);
		return this;
	}

	public String getItemWidth() throws BIJSONException {
		return (String) this.getAttribute(ATTR_ITEM_WIDTH);
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_MENU_ITEMS;
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
