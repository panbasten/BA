package com.yonyou.bq8.di.component.components.button;

import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.components.SimpleComponentMeta;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;

public class BQButtonMeta extends SimpleComponentMeta implements
		ComponentMetaInterface {

	public static final String BUTTON_TYPE_BUTTON = "button";
	public static final String BUTTON_TYPE_SEPARATOR = "separator";

	private String type = BUTTON_TYPE_BUTTON;

	@Override
	public JSONObject getFormJo() throws DIJSONException {
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
