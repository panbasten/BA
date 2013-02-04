package com.yonyou.bq8.di.component.components.number;

import com.yonyou.bq8.di.component.components.validate.ValidateBoxMeta;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;

public class NumberBoxMeta extends ValidateBoxMeta implements
		ComponentMetaInterface {

	public static final String ATTR_DECIMAL_SEPARATOR = "decimalSeparator";
	public static final String ATTR_GROUP_SEPARATOR = "groupSeparator";
	public static final String ATTR_PREFIX = "prefix";
	public static final String ATTR_SUFFIX = "suffix";
	public static final String ATTR_MIN = "min";
	public static final String ATTR_MAX = "max";
	public static final String ATTR_PRECISION = "precision";
	public static final String ATTR_DISABLED = "disabled";
	public static final String ATTR_VALUE = "value";
	public static final String ATTR_FORMATTER = "formatter";
	public static final String ATTR_PARSER = "parser";

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_NUMBER_BOX;
	}

	public ValidateBoxMeta setDecimalSeparator(String val)
			throws DIJSONException {
		this.addAttribute(ATTR_DECIMAL_SEPARATOR, val);
		return this;
	}

	public String getDecimalSeparator() throws DIJSONException {
		return (String) this.getAttribute(ATTR_DECIMAL_SEPARATOR);
	}

	public ValidateBoxMeta setGroupSeparator(String val) throws DIJSONException {
		this.addAttribute(ATTR_GROUP_SEPARATOR, val);
		return this;
	}

	public String getGroupSeparator() throws DIJSONException {
		return (String) this.getAttribute(ATTR_GROUP_SEPARATOR);
	}

	public ValidateBoxMeta setPrefix(String val) throws DIJSONException {
		this.addAttribute(ATTR_PREFIX, val);
		return this;
	}

	public String getPrefix() throws DIJSONException {
		return (String) this.getAttribute(ATTR_PREFIX);
	}

	public ValidateBoxMeta setSuffix(String val) throws DIJSONException {
		this.addAttribute(ATTR_SUFFIX, val);
		return this;
	}

	public String getSuffix() throws DIJSONException {
		return (String) this.getAttribute(ATTR_SUFFIX);
	}

	public ValidateBoxMeta setMin(Number val) throws DIJSONException {
		this.addAttribute(ATTR_MIN, val);
		return this;
	}

	public Number getMin() throws DIJSONException {
		return (Number) this.getAttribute(ATTR_MIN);
	}

	public ValidateBoxMeta setMax(Number val) throws DIJSONException {
		this.addAttribute(ATTR_MAX, val);
		return this;
	}

	public Number getMax() throws DIJSONException {
		return (Number) this.getAttribute(ATTR_MAX);
	}

	public ValidateBoxMeta setPrecision(Integer val) throws DIJSONException {
		this.addAttribute(ATTR_PRECISION, val);
		return this;
	}

	public Integer getPrecision() throws DIJSONException {
		return (Integer) this.getAttribute(ATTR_PRECISION);
	}

	public ValidateBoxMeta setDisabled(Boolean val) throws DIJSONException {
		this.addAttribute(ATTR_DISABLED, val);
		return this;
	}

	public Boolean getDisabled() throws DIJSONException {
		return (Boolean) this.getAttribute(ATTR_DISABLED);
	}

	public ValidateBoxMeta setValue(String val) throws DIJSONException {
		this.addAttribute(ATTR_VALUE, val);
		return this;
	}

	public String getValue() throws DIJSONException {
		return (String) this.getAttribute(ATTR_VALUE);
	}

	public ValidateBoxMeta setFormatter(String val) throws DIJSONException {
		this.addAttribute(ATTR_FORMATTER, val);
		return this;
	}

	public String getFormatter() throws DIJSONException {
		return (String) this.getAttribute(ATTR_FORMATTER);
	}

	public ValidateBoxMeta setParser(String val) throws DIJSONException {
		this.addAttribute(ATTR_PARSER, val);
		return this;
	}

	public String getParser() throws DIJSONException {
		return (String) this.getAttribute(ATTR_PARSER);
	}
}
