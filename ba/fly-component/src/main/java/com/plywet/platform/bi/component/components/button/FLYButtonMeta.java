package com.plywet.platform.bi.component.components.button;

import org.json.simple.JSONObject;

import com.plywet.platform.bi.component.components.SimpleComponentMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIJSONException;

public class FLYButtonMeta extends SimpleComponentMeta implements
		ComponentMetaInterface {

	public static final String BUTTON_TYPE_BUTTON = "button";
	public static final String BUTTON_TYPE_SEPARATOR = "separator";

	private String type = BUTTON_TYPE_BUTTON;

	@Override
	public JSONObject getFormJo() throws BIJSONException {
		super.addExtendAttribute("type", type);
		return super.getFormJo();
	}

	@Override
	public String[] getAttributesName() {
		return HTML.BUTTON_ATTRS;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BUTTON;
	}

	public void setButtonType() {
		type = BUTTON_TYPE_BUTTON;
	}

	public void setSeparatorType() {
		type = BUTTON_TYPE_SEPARATOR;
	}

}
