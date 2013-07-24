package com.flywet.platform.bi.core.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import junit.framework.TestCase;

public class JSONUtilsTest extends TestCase {

	public void testConvertStringToJSONObject() {
		try {
			String obj = "{\"required\":true}";
			JSONParser parser = new JSONParser();
			JSONObject jo = (JSONObject) parser.parse(obj);
			assertEquals(true, jo.get("required"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void testValidate() {
		validateRequired("{\"required\":true}");
		validateRequired("{required:true}");
		validateRequired("{'required':true}");
	}

	private void validateRequired(String validate) {
		int validateIndex = validate.indexOf("required");
		if (validateIndex > -1) {
			int validateIndex2 = validate.indexOf(",", validateIndex);
			if (validateIndex2 < validateIndex) {
				validateIndex2 = validate.length();
			}
			String subVal = validate.substring(validateIndex, validateIndex2);
			subVal = subVal.replaceAll("\"", "").replaceAll("'", "").replace(
					"}", "");
			String[] kv = subVal.split(":");
			assertEquals(Boolean.TRUE, Boolean.valueOf(kv[1].trim()));
		}
	}
}
