package com.flywet.platform.bi.component.components;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

public abstract class SimpleValidateComponentMeta extends SimpleComponentMeta implements
		ComponentMetaInterface {
	/**
	 * 验证信息
	 */
	private ValidateModule validate = null;

	@Override
	public void removeAll() {
		super.removeAll();
		this.validate = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormJo() throws BIJSONException {
		JSONObject formJo = super.getFormJo();
		if (validate != null)
			formJo.put(HTML.TAG_VALIDATE, validate.getJo());
		return formJo;
	}

	public ValidateModule getValidate() {
		return validate;
	}

	public void setValidate(ValidateModule validate) {
		this.validate = validate;
	}
}
