package com.flywet.platform.bi.component.components.menu;

import com.flywet.platform.bi.component.components.BaseComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

public class MenuItemMeta extends BaseComponentMeta implements
		ComponentMetaInterface {

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

}
