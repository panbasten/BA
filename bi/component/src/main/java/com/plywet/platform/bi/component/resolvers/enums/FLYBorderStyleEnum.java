package com.plywet.platform.bi.component.resolvers.enums;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.i18n.BaseMessages;

import com.plywet.platform.bi.component.core.ComponentEnumInterface;

public enum FLYBorderStyleEnum implements ComponentEnumInterface {

	none, dotted, dashed, solid, _double {
		@Override
		public String getValue() {
			return "double";
		}
	},
	groove, ridge, inset, outset;

	public static final String COMPONENT_ENUM_NAME = "BorderStyle";

	private static Class<?> PKG = FLYBorderStyleEnum.class;

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

		for (FLYBorderStyleEnum e : FLYBorderStyleEnum.values()) {
			JSONObject jo = new JSONObject();
			jo.put("text", e.getText());
			jo.put("value", e.getValue());
			ja.add(jo);
		}

		return ja;
	}

}
