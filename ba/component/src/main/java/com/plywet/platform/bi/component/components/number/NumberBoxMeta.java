package com.plywet.platform.bi.component.components.number;

import com.plywet.platform.bi.component.components.validate.ValidateBoxMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIJSONException;

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
			throws BIJSONException {
		this.addAttribute(ATTR_DECIMAL_SEPARATOR, val);
		return this;
	}

	public String getDecimalSeparator() throws BIJSONException {
		return (String) this.getAttribute(ATTR_DECIMAL_SEPARATOR);
	}

	public ValidateBoxMeta setGroupSeparator(String val) throws BIJSONException {
		this.addAttribute(ATTR_GROUP_SEPARATOR, val);
		return this;
	}

	public String getGroupSeparator() throws BIJSONException {
		return (String) this.getAttribute(ATTR_GROUP_SEPARATOR);
	}

	public ValidateBoxMeta setPrefix(String val) throws BIJSONException {
		this.addAttribute(ATTR_PREFIX, val);
		return this;
	}

	public String getPrefix() throws BIJSONException {
		return (String) this.getAttribute(ATTR_PREFIX);
	}

	public ValidateBoxMeta setSuffix(String val) throws BIJSONException {
		this.addAttribute(ATTR_SUFFIX, val);
		return this;
	}

	public String getSuffix() throws BIJSONException {
		return (String) this.getAttribute(ATTR_SUFFIX);
	}

	public ValidateBoxMeta setMin(Number val) throws BIJSONException {
		this.addAttribute(ATTR_MIN, val);
		return this;
	}

	public Number getMin() throws BIJSONException {
		return (Number) this.getAttribute(ATTR_MIN);
	}

	public ValidateBoxMeta setMax(Number val) throws BIJSONException {
		this.addAttribute(ATTR_MAX, val);
		return this;
	}

	public Number getMax() throws BIJSONException {
		return (Number) this.getAttribute(ATTR_MAX);
	}

	public ValidateBoxMeta setPrecision(Integer val) throws BIJSONException {
		this.addAttribute(ATTR_PRECISION, val);
		return this;
	}

	public Integer getPrecision() throws BIJSONException {
		return (Integer) this.getAttribute(ATTR_PRECISION);
	}

	public ValidateBoxMeta setDisabled(Boolean val) throws BIJSONException {
		this.addAttribute(ATTR_DISABLED, val);
		return this;
	}

	public Boolean getDisabled() throws BIJSONException {
		return (Boolean) this.getAttribute(ATTR_DISABLED);
	}

	public ValidateBoxMeta setValue(String val) throws BIJSONException {
		this.addAttribute(ATTR_VALUE, val);
		return this;
	}

	public String getValue() throws BIJSONException {
		return (String) this.getAttribute(ATTR_VALUE);
	}

	public ValidateBoxMeta setFormatter(String val) throws BIJSONException {
		this.addAttribute(ATTR_FORMATTER, val);
		return this;
	}

	public String getFormatter() throws BIJSONException {
		return (String) this.getAttribute(ATTR_FORMATTER);
	}

	public ValidateBoxMeta setParser(String val) throws BIJSONException {
		this.addAttribute(ATTR_PARSER, val);
		return this;
	}

	public String getParser() throws BIJSONException {
		return (String) this.getAttribute(ATTR_PARSER);
	}
}
