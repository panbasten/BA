package com.flywet.platform.bi.component.components.base;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.components.SimpleValidateComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

/**
 * 输入
 * 
 * @author panwei
 * @version 1.0
 * @since 2009/06/22
 */
public class InputMeta extends SimpleValidateComponentMeta implements
		ComponentMetaInterface {

	private String type = HTML.ATTR_INPUT_TYPE_TEXT;

	@Override
	public void removeAll() {
		super.removeAll();
		this.type = HTML.ATTR_INPUT_TYPE_TEXT;
	}

	@Override
	public JSONObject getFormJo() throws BIJSONException {
		super.addAttribute(HTML.ATTR_TYPE, type);
		return super.getFormJo();
	}

	@Override
	public String[] getAttributesName() {
		return HTML.INPUT_TEXT_ATTRS;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BASE_INPUT;
	}

	public void setTypeButton() {
		this.type = HTML.ATTR_INPUT_TYPE_BUTTON;
	}

	public void setTypeCheckbox() {
		this.type = HTML.ATTR_INPUT_TYPE_CHECKBOX;
	}

	public void setTypeFile() {
		this.type = HTML.ATTR_INPUT_TYPE_FILE;
	}

	public void setTypeHidden() {
		this.type = HTML.ATTR_INPUT_TYPE_HIDDEN;
	}

	public void setTypeImage() {
		this.type = HTML.ATTR_INPUT_TYPE_IMAGE;
	}

	public void setTypePassword() {
		this.type = HTML.ATTR_INPUT_TYPE_PASSWORD;
	}

	public void setTypeRadio() {
		this.type = HTML.ATTR_INPUT_TYPE_RADIO;
	}

	public void setTypeReset() {
		this.type = HTML.ATTR_INPUT_TYPE_RESET;
	}

	public void setTypeSubmit() {
		this.type = HTML.ATTR_INPUT_TYPE_SUBMIT;
	}

	public void setTypeText() {
		this.type = HTML.ATTR_INPUT_TYPE_TEXT;
	}

}
