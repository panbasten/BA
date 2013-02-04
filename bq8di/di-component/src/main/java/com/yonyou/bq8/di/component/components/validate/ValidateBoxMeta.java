package com.yonyou.bq8.di.component.components.validate;

import com.yonyou.bq8.di.component.components.BaseComponentMeta;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;

public class ValidateBoxMeta extends BaseComponentMeta implements
		ComponentMetaInterface {

	public static final String ATTR_VALID_TYPE = "validType";
	public static final String ATTR_MISSING_MESSAGE = "missingMessage";
	public static final String ATTR_INVALID_MESSAGE = "invalidMessage";
	public static final String ATTR_TIP_POSITION = "tipPosition";
	public static final String ATTR_REQUIRED = "required";

	@Override
	public String[] getAttributesName() {
		return null;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_VALIDATE_BOX;
	}

	public ValidateBoxMeta setValidType(String val) throws DIJSONException {
		this.addAttribute(ATTR_VALID_TYPE, val);
		return this;
	}

	public String getValidType() throws DIJSONException {
		return (String) this.getAttribute(ATTR_VALID_TYPE);
	}

	public ValidateBoxMeta setMissingMessage(String val) throws DIJSONException {
		this.addAttribute(ATTR_MISSING_MESSAGE, val);
		return this;
	}

	public String getMissingMessage() throws DIJSONException {
		return (String) this.getAttribute(ATTR_MISSING_MESSAGE);
	}

	public ValidateBoxMeta setInvalidMessage(String val) throws DIJSONException {
		this.addAttribute(ATTR_INVALID_MESSAGE, val);
		return this;
	}

	public String getInvalidMessage() throws DIJSONException {
		return (String) this.getAttribute(ATTR_INVALID_MESSAGE);
	}

	public ValidateBoxMeta setTipPosition(String val) throws DIJSONException {
		this.addAttribute(ATTR_TIP_POSITION, val);
		return this;
	}

	public String getTipPosition() throws DIJSONException {
		return (String) this.getAttribute(ATTR_TIP_POSITION);
	}

	public ValidateBoxMeta setRequired(boolean val) throws DIJSONException {
		this.addAttribute(ATTR_REQUIRED, val);
		return this;
	}

	public Boolean getRequired() throws DIJSONException {
		return (Boolean) this.getAttribute(ATTR_REQUIRED);
	}

}
