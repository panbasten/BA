package com.flywet.platform.bi.component.resolvers.enums;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.component.core.ComponentEnumInterface;

public enum FLYColorEnum implements ComponentEnumInterface {

	aqua, black, blue, fuchsia, gray, green, lime, maroon, navy, olive, purple, red, silver, teal, white, yellow;

	public static final String COMPONENT_ENUM_NAME = "Color";

	private static Class<?> PKG = FLYColorEnum.class;

	@Override
	public String getText() {
		return BaseMessages.getString(PKG, COMPONENT_ENUM_LONG_DESC_PREFIX
				+ "." + COMPONENT_ENUM_NAME + "." + this.name());
	}

	@Override
	public String getValue() {
		return this.name();
	}

	@SuppressWarnings("unchecked")
	public static JSONArray getEnumJSONArray() {
		JSONArray ja = new JSONArray();

		for (FLYColorEnum e : FLYColorEnum.values()) {
			JSONObject jo = new JSONObject();
			jo.put("text", e.getText());
			jo.put("value", e.getValue());
			ja.add(jo);
		}

		return ja;
	}
}
