package com.plywet.platform.bi.component.resolvers.enums;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;

public class FLYEnumUtils {

	public static final Map<String, JSONArray> enumMap = new HashMap<String, JSONArray>();

	static {
		enumMap.put(FLYAlignHorizontalEnum.COMPONENT_ENUM_NAME,
				FLYAlignHorizontalEnum.getEnumJSONArray());
		enumMap.put(FLYAlignVerticalEnum.COMPONENT_ENUM_NAME,
				FLYAlignVerticalEnum.getEnumJSONArray());
		enumMap.put(FLYBorderStyleEnum.COMPONENT_ENUM_NAME, FLYBorderStyleEnum
				.getEnumJSONArray());
		enumMap.put(FLYColorEnum.COMPONENT_ENUM_NAME, FLYColorEnum
				.getEnumJSONArray());
		enumMap.put(FLYFontWeightEnum.COMPONENT_ENUM_NAME, FLYFontWeightEnum
				.getEnumJSONArray());
		enumMap.put(FLYOverflowEnum.COMPONENT_ENUM_NAME, FLYOverflowEnum
				.getEnumJSONArray());
	}

	public static JSONArray getEnumJSONArray(String enumName) {
		return enumMap.get(enumName);
	}

}
