package com.yonyou.bq8.di.component.components;

import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;

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
	public JSONObject getFormJo() throws DIJSONException {
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
