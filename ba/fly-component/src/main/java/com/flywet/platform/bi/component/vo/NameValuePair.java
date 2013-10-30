package com.flywet.platform.bi.component.vo;

import java.util.ArrayList;
import java.util.List;

public class NameValuePair {

	public static final String ATTR_NAME = "name";
	public static final String ATTR_VALUE = "value";

	private String name;
	private String value;

	public NameValuePair() {

	}

	public NameValuePair(String value) {
		this.name = value;
		this.value = value;
	}

	public NameValuePair(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public static List<NameValuePair> instance(String[] values) {
		if (values != null) {
			List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
			for (String value : values) {
				nvpList.add(new NameValuePair(value));
			}
			return nvpList;
		}
		return null;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
