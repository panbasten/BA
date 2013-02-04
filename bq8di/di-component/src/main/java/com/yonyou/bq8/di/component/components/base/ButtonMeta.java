package com.yonyou.bq8.di.component.components.base;

import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.components.SimpleComponentMeta;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;

/**
 * 按键功能
 * 
 * @author panwei
 * @version 1.0
 * @since 2009/06/22
 */
public class ButtonMeta extends SimpleComponentMeta {

	private String type = HTML.ATTR_BUTTON_TYPE_BUTTON;

	@Override
	public void removeAll() {
		super.removeAll();
		this.type = HTML.ATTR_BUTTON_TYPE_BUTTON;
	}

	@Override
	public JSONObject getFormJo() throws DIJSONException {
		super.addAttribute(HTML.ATTR_TYPE, this.type);
		return super.getFormJo();
	}

	@Override
	public String[] getAttributesName() {
		return HTML.BUTTON_ATTRS;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BASE_BUTTON;
	}

	public void setTypeButton() {
		type = HTML.ATTR_BUTTON_TYPE_BUTTON;
	}

	public void setTypeReset() {
		type = HTML.ATTR_BUTTON_TYPE_RESET;
	}

	public void setTypeSubmit() {
		type = HTML.ATTR_BUTTON_TYPE_SUBMIT;
	}

}
