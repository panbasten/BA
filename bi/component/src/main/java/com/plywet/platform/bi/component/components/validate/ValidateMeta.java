package com.plywet.platform.bi.component.components.validate;

import org.json.simple.JSONObject;

import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.core.exception.BIJSONException;

public class ValidateMeta implements ComponentMetaInterface {

	public static final String VALIDATE_TYPE_EMAIL = "email";
	public static final String VALIDATE_TYPE_URL = "url";
	public static final String VALIDATE_TYPE_DATE = "date";
	public static final String VALIDATE_TYPE_DATEISO = "dateISO";
	public static final String VALIDATE_TYPE_DATEDE = "dateDE";
	public static final String VALIDATE_TYPE_NUMBER = "number";
	public static final String VALIDATE_TYPE_NUMBERDE = "numberDE";
	public static final String VALIDATE_TYPE_DIGITS = "digits";
	public static final String VALIDATE_TYPE_CREDITCARD = "creditcard";

	public ValidateMeta instance() {
		return new ValidateMeta();
	}

	/**
	 * 是否必选
	 */
	private boolean required;

	/**
	 * 值类型
	 */
	private String type;

	/**
	 * 必须修改，原始值
	 */
	private String remote;

	/**
	 * 与之相同的值，目标值
	 */
	private String equalTo;

	/**
	 * 合法后缀名，后缀名
	 */
	private String accept;

	/**
	 * 最小值
	 */
	private int min = -1;

	/**
	 * 最大值
	 */
	private int max = -1;

	/**
	 * 介于之间的值
	 */
	private int[] range = null;

	/**
	 * 最小长度
	 */
	private int minlength = -1;

	/**
	 * 最大长度
	 */
	private int maxlength = -1;

	/**
	 * 长度区间
	 */
	private int[] rangelength = null;

	public boolean isRequired() {
		return required;
	}

	public ValidateMeta setRequired(boolean required) {
		this.required = required;
		return this;
	}

	public String getType() {
		return type;
	}

	public ValidateMeta setType(String type) {
		this.type = type;
		return this;
	}

	public String getRemote() {
		return remote;
	}

	public ValidateMeta setRemote(String remote) {
		this.remote = remote;
		return this;
	}

	public String getEqualTo() {
		return equalTo;
	}

	public ValidateMeta setEqualTo(String equalTo) {
		this.equalTo = equalTo;
		return this;
	}

	public String getAccept() {
		return accept;
	}

	public ValidateMeta setAccept(String accept) {
		this.accept = accept;
		return this;
	}

	public int getMin() {
		return min;
	}

	public ValidateMeta setMin(int min) {
		this.min = min;
		return this;
	}

	public int getMax() {
		return max;
	}

	public ValidateMeta setMax(int max) {
		this.max = max;
		return this;
	}

	public int[] getRange() {
		return range;
	}

	public ValidateMeta setRange(int range0, int range1) {
		this.range = new int[] { range0, range1 };
		return this;
	}

	public int getMinlength() {
		return minlength;
	}

	public ValidateMeta setMinlength(int minlength) {
		this.minlength = minlength;
		return this;
	}

	public int getMaxlength() {
		return maxlength;
	}

	public ValidateMeta setMaxlength(int maxlength) {
		this.maxlength = maxlength;
		return this;
	}

	public int[] getRangelength() {
		return rangelength;
	}

	public ValidateMeta setRangelength(int rangelength0, int rangelength1) {
		this.rangelength = new int[] { rangelength0, rangelength1 };
		return this;
	}

	@Override
	public JSONObject getFormJo() throws BIJSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void append(ComponentMetaInterface content) throws BIJSONException {
	}

	@Override
	public void appendTo(ComponentMetaInterface content) throws BIJSONException {
		content.append(this);
	}

	@Override
	public void removeAll() throws BIJSONException {
		required = false;
		type = null;
		remote = null;
		equalTo = null;
		max = -1;
		range = null;
		minlength = -1;
		maxlength = -1;
		rangelength = null;
	}
}
