package com.yonyou.bq8.di.component.components;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * 表单验证功能
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public class ValidateModule implements Cloneable {

	/**
	 * 是否必选字段
	 */
	private boolean required;

	/**
	 * 是否电邮
	 */
	private boolean email;

	/**
	 * 是否URL
	 */
	private boolean url;

	/**
	 * 是否日期
	 */
	private boolean date;

	/**
	 * 是否日期（ISO）
	 */
	private boolean dateISO;
	/**
	 * 日期设计
	 */
	private boolean dateDE;

	/**
	 * 是否数字
	 */
	private boolean number;

	/**
	 * 数字设计
	 */
	private boolean numberDE;

	/**
	 * 是否整数
	 */
	private boolean digits;

	/**
	 * 是否信用卡号
	 */
	private boolean creditcard;

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

	public static ValidateModule getInstance() {
		return new ValidateModule();
	}

	/**
	 * 得到验证字符串JSON代码
	 * 
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJo() {
		JSONObject v = new JSONObject();

		if (required)
			v.put("required", true);
		if (email)
			v.put("email", true);
		if (url)
			v.put("url", true);
		if (minlength != -1)
			v.put("minlength", minlength);
		if (maxlength != -1)
			v.put("maxlength", maxlength);
		if (rangelength != null) {
			JSONArray rl = new JSONArray();
			rl.add(rangelength[0]);
			rl.add(rangelength[1]);
			v.put("rangelength", rl);
		}
		if (min != -1)
			v.put("min", min);
		if (max != -1)
			v.put("max", max);
		if (range != null) {
			JSONArray r = new JSONArray();
			r.add(range[0]);
			r.add(range[1]);
			v.put("range", r);
		}

		if (date)
			v.put("date", date);
		if (dateISO)
			v.put("dateISO", dateISO);
		if (dateDE)
			v.put("dateDE", dateDE);
		if (number)
			v.put("number", number);
		if (numberDE)
			v.put("numberDE", numberDE);
		if (digits)
			v.put("digits", digits);
		if (creditcard)
			v.put("creditcard", creditcard);

		if (!StringUtils.isEmpty(remote))
			v.put("remote", remote);
		if (!StringUtils.isEmpty(equalTo))
			v.put("equalTo", "[id=" + equalTo + "]");
		if (!StringUtils.isEmpty(accept))
			v.put("accept", accept);

		return v;
	}

	/**
	 * 日期返回
	 * 
	 * @return
	 */
	public boolean isDate() {
		return date;
	}

	/**
	 * 定义日期
	 * 
	 * @param date
	 * @return
	 */
	public ValidateModule setDate(boolean date) {
		this.date = date;
		return this;
	}

	/**
	 * 返回是否日期
	 * 
	 * @return
	 */
	public boolean isDateISO() {
		return dateISO;
	}

	/**
	 * 定义是否日期
	 * 
	 * @param dateISO
	 * @return
	 */
	public ValidateModule setDateISO(boolean dateISO) {
		this.dateISO = dateISO;
		return this;
	}

	/**
	 * 返回日期设计
	 * 
	 * @return
	 */
	public boolean isDateDE() {
		return dateDE;
	}

	/**
	 * 定义日期设计
	 * 
	 * @param dateDE
	 * @return
	 */
	public ValidateModule setDateDE(boolean dateDE) {
		this.dateDE = dateDE;
		return this;
	}

	/**
	 * 返回值
	 * 
	 * @return
	 */
	public boolean isNumber() {
		return number;
	}

	/**
	 * 返回数量
	 * 
	 * @param number
	 * @return
	 */
	public ValidateModule setNumber(boolean number) {
		this.number = number;
		return this;
	}

	/**
	 * 定义数量
	 * 
	 * @return
	 */
	public boolean isNumberDE() {
		return numberDE;
	}

	/**
	 * numberDE定义
	 * 
	 * @param numberDE
	 * @return
	 */
	public ValidateModule setNumberDE(boolean numberDE) {
		this.numberDE = numberDE;
		return this;
	}

	/**
	 * numberDE返回
	 * 
	 * @return
	 */
	public boolean isDigits() {
		return digits;
	}

	/**
	 * 返回精度
	 * 
	 * @param digits
	 * @return
	 */
	public ValidateModule setDigits(boolean digits) {
		this.digits = digits;
		return this;
	}

	/**
	 * Creditcard返回值
	 * 
	 * @return
	 */
	public boolean isCreditcard() {
		return creditcard;
	}

	/**
	 * 定义Creditcard
	 * 
	 * @param creditcard
	 * @return
	 */
	public ValidateModule setCreditcard(boolean creditcard) {
		this.creditcard = creditcard;
		return this;
	}

	/**
	 * 间接值返回
	 * 
	 * @return
	 */
	public String getRemote() {
		return remote;
	}

	/**
	 * 定义间接
	 * 
	 * @param remote
	 * @return
	 */
	public ValidateModule setRemote(String remote) {
		this.remote = remote;
		return this;
	}

	/**
	 * 平均
	 * 
	 * @return
	 */
	public String getEqualTo() {
		return equalTo;
	}

	/**
	 * 定义平均
	 * 
	 * @param equalTo
	 * @return
	 */
	public ValidateModule setEqualTo(String equalTo) {
		this.equalTo = equalTo;
		return this;
	}

	/**
	 * 接受返回
	 * 
	 * @return
	 */
	public String getAccept() {
		return accept;
	}

	/**
	 * 定义接受
	 * 
	 * @param accept
	 * @return
	 */
	public ValidateModule setAccept(String accept) {
		this.accept = accept;
		return this;
	}

	/**
	 * 定义列
	 * 
	 * @param range
	 */
	public void setRange(int[] range) {
		this.range = range;
	}

	/**
	 * 定义列长度
	 * 
	 * @param rangelength
	 * @return
	 */
	public ValidateModule setRangelength(int[] rangelength) {
		this.rangelength = rangelength;
		return this;
	}

	/**
	 * 最小值
	 * 
	 * @return
	 */
	public int getMin() {
		return min;
	}

	/**
	 * 定义最小值
	 * 
	 * @param min
	 * @return
	 */
	public ValidateModule setMin(int min) {
		this.min = min;
		return this;
	}

	/**
	 * 最大值返回
	 * 
	 * @return
	 */
	public int getMax() {
		return max;
	}

	/**
	 * 定义最大值
	 * 
	 * @param max
	 * @return
	 */
	public ValidateModule setMax(int max) {
		this.max = max;
		return this;
	}

	/**
	 * 列
	 * 
	 * @return
	 */
	public int[] getRange() {
		return range;
	}

	/**
	 * 设置取值范围
	 * 
	 * @param min
	 *            最小值
	 * 
	 * @param max
	 *            最大值
	 * @return
	 */
	public ValidateModule setRange(int min, int max) {
		this.range = new int[2];
		this.range[0] = min;
		this.range[1] = max;
		return this;
	}

	/**
	 * 必要
	 * 
	 * @return
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * 必要定义
	 * 
	 * @param required
	 * @return
	 */
	public ValidateModule setRequired(boolean required) {
		this.required = required;
		return this;
	}

	/**
	 * 返回电邮
	 * 
	 * @return
	 */
	public boolean isEmail() {
		return email;
	}

	/**
	 * 定义是否电邮
	 * 
	 * @param email
	 * @return
	 */
	public ValidateModule setEmail(boolean email) {
		this.email = email;
		return this;
	}

	/**
	 * 定义url
	 * 
	 * @return
	 */
	public boolean isUrl() {
		return url;
	}

	/**
	 * url返回
	 * 
	 * @param url
	 * @return
	 */
	public ValidateModule setUrl(boolean url) {
		this.url = url;
		return this;
	}

	/**
	 * 返回最小长度
	 * 
	 * @return
	 */
	public int getMinlength() {
		return minlength;
	}

	/**
	 * 定义最小长度
	 * 
	 * @param minlength
	 * @return
	 */
	public ValidateModule setMinlength(int minlength) {
		this.minlength = minlength;
		return this;
	}

	/**
	 * 得到最大长度
	 * 
	 * @return
	 */
	public int getMaxlength() {
		return maxlength;
	}

	/**
	 * 返回最大长度
	 * 
	 * @param maxlength
	 * @return
	 */
	public ValidateModule setMaxlength(int maxlength) {
		this.maxlength = maxlength;
		return this;
	}

	/**
	 * 返回列长度
	 * 
	 * @return
	 */
	public int[] getRangelength() {
		return rangelength;
	}

	/**
	 * 定义长度范围
	 * 
	 * @param minlength
	 *            最短长度
	 * @param maxlength
	 *            最大长度
	 * @return
	 */
	public ValidateModule setRangelength(int minlength, int maxlength) {
		this.rangelength = new int[2];
		this.rangelength[0] = minlength;
		this.rangelength[1] = maxlength;
		return this;
	}

}
