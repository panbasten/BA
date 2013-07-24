package com.flywet.platform.bi.web.model;


/**
 * 命名参数封装对象
 * 
 * @author PeterPan
 * 
 */
public class NamedParameterObject {
	private String key;
	private String value;
	private String desc;

	public static final NamedParameterObject instance() {
		return new NamedParameterObject();
	}

	public String getKey() {
		return key;
	}

	public NamedParameterObject setKey(String key) {
		this.key = key;
		return this;
	}

	public String getValue() {
		return value;
	}

	public NamedParameterObject setValue(String value) {
		this.value = value;
		return this;
	}

	public String getDesc() {
		return desc;
	}

	public NamedParameterObject setDesc(String desc) {
		this.desc = desc;
		return this;
	}
}
