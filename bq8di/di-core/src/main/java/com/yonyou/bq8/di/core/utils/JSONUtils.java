package com.yonyou.bq8.di.core.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.yonyou.bq8.di.core.exception.DIConvertException;
import com.yonyou.bq8.di.core.exception.DIJSONException;

public class JSONUtils {
	/**
	 * 将一个普通java对象输出为json数据格式
	 * 
	 * @param obj
	 * @return json字符串
	 * @throws Exception
	 */
	public static String toJsonString(Object obj) throws Exception {
		JSONObject jsonObject = convertToJSONObject(obj);
		if (jsonObject == null) {
			return Utils.JSON_NULL;
		}

		return jsonObject.toJSONString();
	}

	/**
	 * 将一个对象集合输出为json格式
	 * 
	 * @param jsonCollection
	 * @return json array字符串
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static String toJsonString(Collection jsonCollection)
			throws Exception {
		JSONArray array = convertToJSONArray(jsonCollection);
		if (array == null) {
			return "[]";
		}
		return array.toJSONString();
	}

	public static String toJsonString(String[] jsonCollection) throws Exception {
		JSONArray array = convertToJSONArray(jsonCollection);
		if (array == null) {
			return "[]";
		}
		return array.toJSONString();
	}

	public static JSONObject convertStringToJSONObject(String obj)
			throws Exception {
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(obj);
	}

	@SuppressWarnings("unchecked")
	public static Object convert(Object obj) throws DIJSONException {
		if (obj == null) {
			return null;
		}
		if (obj.getClass().isPrimitive() || (obj instanceof Number)
				|| (obj instanceof Boolean) || (obj instanceof String)) {
			return obj;
		} else if (obj instanceof String[]) {
			return convertToJSONArray((String[]) obj);
		} else if (obj instanceof Collection) {
			if (isPrimitiveCollection((Collection) obj)) {
				return obj;
			} else {
				return convertToJSONArray((Collection) obj);
			}
		} else if (obj instanceof Map) {
			return convertToJSONObject((Map) obj);
		} else {
			return convertToJSONObject(obj);
		}

	}

	/**
	 * 将一个普通java对象转成JSONObject
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static JSONObject convertToJSONObject(Object obj)
			throws DIJSONException {
		return convertToJSONObject(obj, null, true);
	}

	@SuppressWarnings("unchecked")
	public static JSONObject convertToJSONObject(Object obj, String[] strs,
			boolean dis) throws DIJSONException {
		try {
			if (obj == null) {
				return null;
			}

			JSONObject json = new JSONObject();
			Field[] fields = obj.getClass().getDeclaredFields();
			if (fields == null || fields.length == 0) {
				return json;
			}

			for (Field field : fields) {
				String fieldName = field.getName();
				boolean march = false;
				if (strs != null) {
					for (String d : strs) {
						if (fieldName.equals(d)) {
							march = true;
							break;
						}
					}
				}
				if (march != dis) {
					Object val = PropertyUtils.getProperty(obj, fieldName);
					if (val == null) {
						json.put(fieldName, null);
						continue;
					}
					json.put(fieldName, convert(val));
				}
			}

			return json;
		} catch (Exception e) {
			throw new DIJSONException("解析普通java对象到JSONObject出现错误.", e);
		}
	}

	@SuppressWarnings("unchecked")
	private static boolean isPrimitiveCollection(Collection coll) {
		if (coll == null || coll.isEmpty()) {
			return true;
		}
		Object entity = coll.iterator().next();
		return Utils.isPrimitive(entity);
	}

	/**
	 * 将一个普通java对象转成JSONObject
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject convertToJSONObject(Map map)
			throws DIJSONException {
		if (map == null) {
			return null;
		}

		JSONObject json = new JSONObject();
		Map.Entry<String, Object> entry;
		for (Iterator<Map.Entry<String, Object>> iter = map.entrySet()
				.iterator(); iter.hasNext();) {
			entry = iter.next();
			Object v = entry.getValue();
			if (v != null) {
				json.put(entry.getKey(), convert(v));
			}
		}
		return json;
	}

	/**
	 * 将一个属性集转换为一个json数组
	 * 
	 * @param props
	 * @return
	 * @throws DIJSONException
	 */
	@SuppressWarnings("unchecked")
	public static JSONArray convertToJSONArray(Properties props)
			throws DIJSONException {
		if (props == null) {
			return null;
		}
		try {
			JSONArray ja = new JSONArray();
			for (String key : props.stringPropertyNames()) {
				JSONObject jo = new JSONObject();
				jo.put("key", key);
				jo.put("value", Utils.autoConvert(props.getProperty(key)));
				ja.add(jo);
			}
			return ja;
		} catch (DIConvertException e) {
			throw new DIJSONException(e);
		}
	}

	/**
	 * 将一个集合转换为json数组
	 * 
	 * @param collection
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static JSONArray convertToJSONArray(Collection collection)
			throws DIJSONException {
		if (collection == null) {
			return null;
		}

		JSONArray array = new JSONArray();
		if (collection.isEmpty()) {
			return array;
		}

		for (Object obj : collection) {
			array.add(convert(obj));
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	public static JSONArray convertToJSONArray(String[] strs) {
		if (strs == null) {
			return null;
		}
		JSONArray array = new JSONArray();

		for (String s : strs) {
			array.add(s);
		}
		return array;
	}

	public static String replaceJavascriptString(String str) {
		if (str == null) {
			return "";
		}
		return str.replaceAll("\"", "\\\\\"");
	}

	public static String replaceJavascriptHTMLString(String str) {
		if (str == null) {
			return "";
		}
		return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
				">", "&gt;").replaceAll("\"", "\\\\\"");
	}
}
