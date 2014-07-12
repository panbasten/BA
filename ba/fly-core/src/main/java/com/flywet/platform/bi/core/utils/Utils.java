package com.flywet.platform.bi.core.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;

import com.flywet.platform.bi.core.exception.BIConvertException;
import com.flywet.platform.bi.core.exception.BIJSONException;

/**
 * 公共类常量类，涉及系统用的的常量<br>
 * 事件常量
 * 
 * @author 潘巍（Peter Pan）
 * 
 */
public class Utils {
	/**
	 * 浏览类别
	 */
	public static final String CATEGORY_DI = "di";
	public static final String CATEGORY_DI_TRANS = "trans";
	public static final String CATEGORY_DI_JOB = "job";
	public static final String CATEGORY_REPORT = "report";
	public static final String CATEGORY_SMART = "smart";
	public static final String CATEGORY_DB = "db";
	public static final String CATEGORY_FILESYSTEM = "fs";
	public static final String CATEGORY_SYSTOOLS = "systools";

	/**
	 * 浏览面板Dom类型
	 */
	public static final String DOM_ROOT = "root";
	public static final String DOM_NODE = "node";
	public static final String DOM_LEAF = "leaf";

	/**
	 * Ajax请求返回的操作
	 */
	public static final String RESULT_OPERATION_UPDATE = "update";
	public static final String RESULT_OPERATION_APPEND = "append";
	public static final String RESULT_OPERATION_REMOVE = "remove";
	public static final String RESULT_OPERATION_EMPTY = "empty";
	public static final String RESULT_OPERATION_BEFORE = "before";
	public static final String RESULT_OPERATION_AFTER = "after";
	public static final String RESULT_OPERATION_CUSTOM = "custom";

	/**
	 * 字符串相关常量
	 */
	/**
	 * null值字符串显示值
	 */
	public static final String NULL = "null";

	public static final String JSON_NULL = "{}";

	/**
	 * 默认环境的locale（系统默认）
	 */
	public static final Locale DEFAULT_LOCALE = Locale.getDefault();

	/* 通用方法 */

	/**
	 * 判断是否一个字符被认为是空白。<br>
	 * 本系统中认为空格、制表符、换行、回车都认为是空白
	 * 
	 * @param c
	 * @return
	 */
	public static final boolean isSpace(char c) {
		return c == ' ' || c == '\t' || c == '\r' || c == '\n';
	}

	/**
	 * 判断给定的字符串是否只是空白
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean onlySpaces(String str) {
		for (int i = 0; i < str.length(); i++)
			if (!isSpace(str.charAt(i)))
				return false;
		return true;
	}

	/**
	 * 左修剪：移除字符串左边的空白
	 * 
	 * @param str
	 * @return
	 */
	public static String ltrim(String source) {
		if (source == null)
			return null;
		int from = 0;
		while (from < source.length() && isSpace(source.charAt(from)))
			from++;

		return source.substring(from);
	}

	/**
	 * 右修剪：移除字符串右边的空白
	 * 
	 * @param str
	 * @return
	 */
	public static String rtrim(String source) {
		if (source == null)
			return null;

		int max = source.length();
		while (max > 0 && isSpace(source.charAt(max - 1)))
			max--;

		return source.substring(0, max);
	}

	/**
	 * 左右修剪：移除字符串头部和尾部的空白
	 * 
	 * @param str
	 * @return
	 */
	public static final String trim(String str) {
		if (str == null)
			return null;

		int max = str.length() - 1;
		int min = 0;

		while (min <= max && isSpace(str.charAt(min)))
			min++;
		while (max >= 0 && isSpace(str.charAt(max)))
			max--;

		if (max < min)
			return "";

		return str.substring(min, max + 1);
	}

	/**
	 * 右修饰字符串：增加空格直到到达指定长度。<br>
	 * 如果字符串长度大于限制的长度，字符串将被截取。
	 * 
	 * @param ret
	 * @param limit
	 * @return
	 */
	public static final String rightPad(String ret, int limit) {
		if (ret == null)
			return rightPad(new StringBuffer(), limit);
		else
			return rightPad(new StringBuffer(ret), limit);
	}

	/**
	 * 右修饰字符串：增加空格直到到达指定长度。<br>
	 * 如果字符串长度大于限制的长度，字符串将被截取。
	 * 
	 * @param ret
	 * @param limit
	 * @return
	 */
	public static final String rightPad(StringBuffer ret, int limit) {
		int len = ret.length();
		int l;

		if (len > limit) {
			ret.setLength(limit);
		} else {
			for (l = len; l < limit; l++)
				ret.append(' ');
		}
		return ret.toString();
	}

	/**
	 * 检查提供的字符串是否为空。<br>
	 * 当它为null或者长度为0时认为是空的。
	 * 
	 * @param string
	 * @return
	 */
	public static final boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	/**
	 * 检查提供的stringBuffer是否为空。<br>
	 * 当它为null或者长度为0时认为是空的。
	 * 
	 * @param string
	 * @return
	 */
	public static final boolean isEmpty(StringBuffer string) {
		return string == null || string.length() == 0;
	}

	/**
	 * 检查提供的字符串数组是否为空。<br>
	 * 当它为null或者长度为0时认为是空的。
	 * 
	 * @param string
	 * @return
	 */
	public static final boolean isEmpty(String[] strings) {
		return strings == null || strings.length == 0;
	}

	/**
	 * 检查提供的对象数组是否为空。<br>
	 * 当它为null或者长度为0时认为是空的。
	 * 
	 * @param array
	 * @return
	 */
	public static final boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	public static final Object autoConvert(String str)
			throws BIConvertException {
		if (str == null || "".equals(str)) {
			return str;
		} else if (isInteger(str)) {
			return Integer.valueOf(str);
		} else if (isNumber(str)) {
			return Double.valueOf(str);
		} else if (isDate(str)) {
			return convertStringToDate(str);
		} else if (isBoolean(str)) {
			return convertStringToBoolean(str);
		}
		return str;
	}

	/*
	 * 判断是否为整数
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/*
	 * 判断是否为浮点数，包括double和float
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	public static boolean isColor(String str) {
		Pattern pattern = Pattern.compile("^#?[0-9a-fA-F]{6}$"), pattern2 = Pattern
				.compile("^#?[0-9a-fA-F]{3}$");
		return pattern.matcher(str).matches()
				|| pattern2.matcher(str).matches();
	}

	public static boolean isDate(String str) {
		return isDate(str, DateUtils.GENERALIZED_DATE_FORMAT);
	}

	public static boolean isDate(String str, String mask) {
		try {
			SimpleDateFormat fdate = new SimpleDateFormat(mask);
			fdate.parse(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isBoolean(String str) {
		return Boolean.valueOf("Y".equalsIgnoreCase(str)
				|| "N".equalsIgnoreCase(str) || "TRUE".equalsIgnoreCase(str)
				|| "FALSE".equalsIgnoreCase(str) || "YES".equalsIgnoreCase(str)
				|| "NO".equalsIgnoreCase(str));
	}

	/**
	 * 实现Oracle风格的NVL函数
	 * 
	 * @param source
	 *            源参数
	 * @param def
	 *            默认值，如果源参数为null或者字符串长度为0，返回默认值
	 * @return
	 */
	public static final String NVL(String source, String def) {
		if (source == null || source.length() == 0)
			return def;
		return source;
	}

	public static final boolean isJSEmpty(String string) {
		return isEmpty(string) || "null".equals(string)
				|| "undefined".equals(string);
	}

	public static final int indexOfString(String lookup, String array[]) {
		if (array == null)
			return -1;
		if (lookup == null)
			return -1;

		for (int i = 0; i < array.length; i++) {
			if (lookup.equalsIgnoreCase(array[i]))
				return i;
		}
		return -1;
	}

	public static final int[] indexsOfFoundStrings(String lookup[],
			String array[]) {
		List<Integer> indexesList = new ArrayList<Integer>();
		for (int i = 0; i < lookup.length; i++) {
			int idx = indexOfString(lookup[i], array);
			if (idx >= 0)
				indexesList.add(Integer.valueOf(idx));
		}
		int[] indexes = new int[indexesList.size()];
		for (int i = 0; i < indexesList.size(); i++)
			indexes[i] = (indexesList.get(i)).intValue();
		return indexes;
	}

	public static final int[] indexsOfStrings(String lookup[], String array[]) {
		int[] indexes = new int[lookup.length];
		for (int i = 0; i < indexes.length; i++)
			indexes[i] = indexOfString(lookup[i], array);
		return indexes;
	}

	public static final int indexOfString(String lookup, List<String> list) {
		if (list == null)
			return -1;

		for (int i = 0; i < list.size(); i++) {
			String compare = list.get(i);
			if (lookup.equalsIgnoreCase(compare))
				return i;
		}
		return -1;
	}

	public static final String joinStrings(String[] input, String join) {
		String j = "";
		if (input != null && input.length > 0) {
			j = input[0];
			for (int i = 1; i < input.length; i++) {
				j += join;
				j += input[i];
			}
		}
		return j;
	}

	public static final String[] sortStrings(String[] input) {
		Arrays.sort(input);
		return input;
	}

	/**
	 * 通过separator转换string为String数组
	 * <p>
	 * <code>
	 * Example: a;b;c;d    ==>  new String[] { a, b, c, d }
	 * </code>
	 * 
	 * 注意：与String.split()不同的是，该方法没有使用正则表达式
	 * 
	 * @param string
	 * @param separator
	 * @return
	 */
	public static final String[] splitString(String string, String separator) {
		/*
		 * 0123456 Example a;b;c;d --> new String[] { a, b, c, d }
		 */
		// System.out.println("splitString ["+path+"] using ["+separator+"]");
		List<String> list = new ArrayList<String>();

		if (string == null || string.length() == 0) {
			return new String[] {};
		}

		int sepLen = separator.length();
		int from = 0;
		int end = string.length() - sepLen + 1;

		for (int i = from; i < end; i += sepLen) {
			if (string.substring(i, i + sepLen).equalsIgnoreCase(separator)) {
				list.add(NVL(string.substring(from, i), ""));
				from = i + sepLen;
			}
		}

		if (from + sepLen <= string.length()) {
			list.add(NVL(string.substring(from, string.length()), ""));
		}

		return list.toArray(new String[list.size()]);
	}

	/**
	 * 通过separator转换string为String数组
	 * <p>
	 * <code>
	 * Example: a;b;c;d    ==  new String[] { a, b, c, d }
	 * </code>
	 * 
	 * @param string
	 * @param separator
	 * @return
	 */
	public static final String[] splitString(String string, char separator) {
		/*
		 * 0123456 Example a;b;c;d --> new String[] { a, b, c, d }
		 */
		// System.out.println("splitString ["+path+"] using ["+separator+"]");
		List<String> list = new ArrayList<String>();

		if (string == null || string.length() == 0) {
			return new String[] {};
		}

		int from = 0;
		int end = string.length();

		for (int i = from; i < end; i += 1) {
			if (string.charAt(i) == separator) {
				list.add(NVL(string.substring(from, i), ""));
				from = i + 1;
			}
		}

		if (from + 1 <= string.length()) {
			list.add(NVL(string.substring(from, string.length()), ""));
		}

		return list.toArray(new String[list.size()]);
	}

	/**
	 * 通过separator将路径转换为字符串数组
	 * <p>
	 * <code>
	 *   Example /a/b/c --> new String[] { a, b, c }
	 * </code>
	 * 
	 * @param path
	 * @param separator
	 * @return
	 */
	public static final String[] splitPath(String path, String separator) {
		//
		// Example /a/b/c --> new String[] { a, b, c }
		//
		// 确保"/"被移除
		//
		// Example /a/b/c/ --> new String[] { a, b, c }
		//

		// 检查空路径
		//
		if (path == null || path.length() == 0 || path.equals(separator)) {
			return new String[] {};
		}

		// 移除最后的"/"
		//
		while (path.endsWith(separator)) {
			path = path.substring(0, path.length() - 1);
		}

		int sepLen = separator.length();
		int nr_separators = 1;
		int from = path.startsWith(separator) ? sepLen : 0;

		for (int i = from; i < path.length(); i += sepLen) {
			if (path.substring(i, i + sepLen).equalsIgnoreCase(separator)) {
				nr_separators++;
			}
		}

		String spath[] = new String[nr_separators];
		int nr = 0;
		for (int i = from; i < path.length(); i += sepLen) {
			if (path.substring(i, i + sepLen).equalsIgnoreCase(separator)) {
				spath[nr] = path.substring(from, i);
				nr++;

				from = i + sepLen;
			}
		}
		if (nr < spath.length) {
			spath[nr] = path.substring(from);
		}

		//
		// a --> { a }
		//
		if (spath.length == 0 && path.length() > 0) {
			spath = new String[] { path };
		}

		return spath;
	}

	/**
	 * 排序字符串数组，确保唯一字符串
	 * 
	 * @param strings
	 * @return 排序的唯一存在的数组
	 */
	public static final String[] getDistinctStrings(String[] strings) {
		if (strings == null)
			return null;
		if (strings.length == 0)
			return new String[] {};

		String[] sorted = sortStrings(strings);
		List<String> result = new ArrayList<String>();
		String previous = "";
		for (int i = 0; i < sorted.length; i++) {
			if (!sorted[i].equalsIgnoreCase(previous)) {
				result.add(sorted[i]);
			}
			previous = sorted[i];
		}

		return result.toArray(new String[result.size()]);
	}

	/**
	 * 用其他值替代原始字符串中的值
	 * 
	 * @param string
	 *            原始字符串
	 * @param repl
	 *            要替换的文本
	 * @param with
	 *            替换的文本
	 * @return
	 */
	public static final String replace(String string, String repl, String with) {
		StringBuffer str = new StringBuffer(string);
		for (int i = str.length() - 1; i >= 0; i--) {
			if (str.substring(i).startsWith(repl)) {
				str.delete(i, i + repl.length());
				str.insert(i, with);
			}
		}
		return str.toString();
	}

	/**
	 * 四舍五入双精度数f到任意位数
	 * 
	 * @param f
	 * @param places
	 *            小数位数
	 * @return
	 */

	public static final double round(double f, int places) {
		java.math.BigDecimal bdtemp = java.math.BigDecimal.valueOf(f);
		bdtemp = bdtemp.setScale(places, java.math.BigDecimal.ROUND_HALF_EVEN);
		return bdtemp.doubleValue();
	}

	/**
	 * 将String转换成integer。如果转换出错，返回默认值
	 * 
	 * @param str
	 * @param def
	 * @return
	 */
	public static final Integer toInt(String str, Integer def) {
		if (str == null) {
			return def;
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 将String转换成boolean。如果转换出错，返回默认值
	 * 
	 * @param str
	 * @param def
	 * @return
	 */
	public static final Boolean toBoolean(String str, Boolean def) {
		if (str != null && !"".equals(str)) {
			if ("true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str)
					|| "y".equalsIgnoreCase(str) || "1".equals(str)) {
				return Boolean.TRUE;
			} else if ("false".equalsIgnoreCase(str)
					|| "no".equalsIgnoreCase(str) || "n".equalsIgnoreCase(str)
					|| "0".equals(str)) {
				return Boolean.FALSE;
			} else {
				try {
					return Boolean.parseBoolean(str);
				} catch (Exception e) {

				}
			}
		}
		return def;
	}

	/**
	 * 将String转换成long integer。如果转换出错，返回默认值
	 * 
	 * @param str
	 * @param def
	 * @return
	 */
	public static final Long toLong(String str, Long def) {
		if (str == null) {
			return def;
		}
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 将String转换成float。如果转换出错，返回默认值
	 * 
	 * @param str
	 * @param def
	 * @return
	 */
	public static final Float toFloat(String str, Float def) {
		if (str == null) {
			return def;
		}
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 将String转换成double。如果转换出错，返回默认值
	 * 
	 * @param str
	 * @param def
	 * @return
	 */
	public static final Double toDouble(String str, Double def) {
		if (str == null) {
			return def;
		}
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 返回一个指定异常的堆栈轨迹字符串
	 * 
	 * @param e
	 *            异常
	 * @return
	 */
	public static final String getStackTracker(Throwable e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		String string = stringWriter.getBuffer().toString();
		try {
			stringWriter.close();
		} catch (IOException ioe) {
		}
		return string;
	}

	/**
	 * 转换JSON数组为String数组
	 * 
	 * @param ja
	 * @return
	 * @throws JSONException
	 */
	public static final String[] convertJSONArrayToStringArray(JSONArray ja)
			throws BIJSONException {
		try {
			int n = ja.size();
			String[] rtn = new String[n];
			for (int i = 0; i < n; i++) {
				rtn[i] = (String) ja.get(i);
			}
			return rtn;
		} catch (Exception ex) {
			throw new BIJSONException(ex);
		}
	}

	/**
	 * 转换JSON数组为int数组
	 * 
	 * @param ja
	 * @return
	 * @throws JSONException
	 */
	public static final int[] convertJSONArrayToIntArray(JSONArray ja)
			throws BIJSONException {
		try {
			int n = ja.size();
			int[] rtn = new int[n];
			for (int i = 0; i < n; i++) {
				rtn[i] = (Integer) ja.get(i);
			}
			return rtn;
		} catch (Exception ex) {
			throw new BIJSONException(ex);
		}
	}

	/**
	 * 将Map转换成JSON对象
	 * 
	 * @param data
	 * @return
	 * @throws BIJSONException
	 */
	@SuppressWarnings("unchecked")
	public static String convertMaptoJSONObject(Map<String, String> data)
			throws BIJSONException {
		try {
			JSONObject dataToJSON = new JSONObject();
			String key;
			for (Iterator<String> iter = data.keySet().iterator(); iter
					.hasNext();) {
				key = iter.next();
				dataToJSON.put(key, data.get(key));
			}
			return dataToJSON.toString();
		} catch (Exception ex) {
			throw new BIJSONException(ex);
		}

	}

	/**
	 * 转换字符串数组为一个字符串
	 * 
	 * @param data
	 *            待转换字符串数组
	 * @param split
	 *            分隔符
	 * @param hasQuot
	 *            是否有引号
	 * @return
	 */
	public static String convertStringArrayToString(String[] data,
			String split, boolean hasQuot) {
		if (split == null)
			split = "";
		if (data != null && data.length > 0) {
			StringBuffer rtn = new StringBuffer();
			for (String d : data) {
				if (hasQuot)
					rtn.append("'");
				rtn.append(d);
				if (hasQuot)
					rtn.append("'");
				rtn.append(split);
			}
			return rtn.toString().substring(0, rtn.length() - split.length());
		}
		return null;
	}

	public static final Date convertStringToDate(String str)
			throws BIConvertException {
		return convertStringToDate(str, DateUtils.GENERALIZED_DATE_FORMAT);
	}

	public static final Date convertStringToDate(String str, String mask)
			throws BIConvertException {
		if (isEmpty(str))
			return null;
		try {
			SimpleDateFormat fdate = new SimpleDateFormat(mask);
			return fdate.parse(str);
		} catch (ParseException e) {
			throw new BIConvertException("转换字符串到日期型出错错误.");
		}

	}

	public static final Boolean convertStringToBoolean(String string) {
		if (isEmpty(string))
			return null;
		return Boolean.valueOf("y".equalsIgnoreCase(string)
				|| "true".equalsIgnoreCase(string)
				|| "yes".equalsIgnoreCase(string) || "1".equals(string));
	}

	@SuppressWarnings("unchecked")
	public static boolean isPrimitive(Object obj) {
		Class clazz = obj.getClass();
		if (clazz.isPrimitive()) {
			return true;
		}

		if ((obj instanceof Number) || (obj instanceof Boolean)
				|| clazz.equals(String.class)) {
			return true;
		}

		return false;
	}

	public static Map<String, String> getAttributes(String reg, String str) {
		Pattern pattern = Pattern.compile(reg);
		if (isEmpty(str))
			return null;
		Map<String, String> rtn = new HashMap<String, String>();
		int start = 0;
		while (true) {
			Matcher matcher = pattern.matcher(str);
			if (matcher.find(start)) {
				rtn.put(matcher.group(1), matcher.group(2));
				start = matcher.end();
			} else {
				break;
			}
		}
		return rtn;
	}

	public static Map<String, String> getAttributes(String str) {
		String REG = "\\s*([a-zA-Z0-9]+)\\s*=\\s*\"([^\"]+)\"\\s*";
		return getAttributes(REG, str);
	}

	/**
	 * 通过正则表达式从字符串获得属性
	 * 
	 * @return
	 */
	public static Map<String, String> getAttributes(String[] attrNames,
			String str) {
		String REG = "\\s*(" + joinStrings(attrNames, "|")
				+ ")\\s*=\\s*\"([^\"]+)\"\\s*";
		return getAttributes(REG, str);
	}

	/**
	 * 判断应用是否在Debug模式下运行
	 * 
	 * @return
	 */
	public static boolean isApplicationDebug() {
		if (PropertyUtils.APPLICATION_TYPE_DEBUG.equals(PropertyUtils
				.getProperty(PropertyUtils.APPLICATION_TYPE))) {
			return true;
		}
		return false;
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param collection
	 * @return
	 */
	public static <T> boolean isEmpty(Collection<T> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * 解析URL
	 * 
	 * @param paramsString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeURL(String paramsString)
			throws UnsupportedEncodingException {
		return URLDecoder.decode(paramsString, Const.XML_ENCODING);
	}

	public static String decodeURL(String paramsString, String charset)
			throws UnsupportedEncodingException {
		return URLDecoder.decode(paramsString, charset);
	}

	public static String encodeURL(String paramsString)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(paramsString, Const.XML_ENCODING);
	}

	public static String encodeURL(String paramsString, String charset)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(paramsString, charset);
	}

	/**
	 * 首字母转小写
	 * 
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstCharacter(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	/**
	 * 首字母转大写
	 * 
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstCharacter(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	/**
	 * 10进制数转26进制字符串A-Z
	 * 
	 * @param in
	 * @return
	 */
	public static String s10t26(int n) {
		String s = "";
		while (n > 0) {
			int m = n % 26;
			if (m == 0) {
				m = 26;
			}
			s = (char) (m + 64) + s;
			n = (n - m) / 26;
		}

		return s;
	}

	/**
	 * 26进制A-Z转换为10进制数字
	 * 
	 * @param s
	 * @return
	 */
	public static int s26t10(String s) {
		if (isEmpty(s))
			return 0;

		int n = 0, j = 1;
		char[] c = s.toUpperCase().toCharArray();
		for (int i = c.length - 1; i >= 0; i--) {
			if (c[i] < 'A' || c[i] > 'Z')
				return 0;

			n += (c[i] - 64) * j;
			j *= 26;
		}
		return n;
	}
}
