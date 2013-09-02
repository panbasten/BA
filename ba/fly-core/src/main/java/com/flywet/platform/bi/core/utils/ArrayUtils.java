package com.flywet.platform.bi.core.utils;

import java.util.List;

/**
 * 数组工具类
 * 
 * @author PeterPan
 * 
 */
public class ArrayUtils {

	private ArrayUtils() {
	}

	public static boolean contains(String[] array, String searchedText) {

		if (array == null || array.length == 0)
			return false;

		for (int i = 0; i < array.length; i++) {
			if (array[i].equalsIgnoreCase(searchedText))
				return true;
		}

		return false;
	}

	public static String[] concat(String[] array1, String[] array2) {
		int length1 = array1.length;
		int length2 = array2.length;
		int length = length1 + length2;

		String[] dest = new String[length];

		System.arraycopy(array1, 0, dest, 0, length1);
		System.arraycopy(array2, 0, dest, length1, length2);

		return dest;
	}

	public static String[] concat(String[]... arrays) {
		int destSize = 0;
		for (int i = 0; i < arrays.length; i++) {
			destSize += arrays[i].length;
		}
		String[] dest = new String[destSize];
		int lastIndex = 0;
		for (int i = 0; i < arrays.length; i++) {
			String[] array = arrays[i];
			System.arraycopy(array, 0, dest, lastIndex, array.length);
			lastIndex += array.length;
		}

		return dest;
	}

	public static String join(String[] arrays) {
		return join(arrays, "");
	}

	public static String join(List<String> arrays) {
		return join(arrays, "");
	}

	public static String join(String[] arrays, String sep) {
		if (arrays != null && arrays.length > 0) {
			String str = arrays[0];
			for (int i = 1; i < arrays.length; i++) {
				str += sep;
				str += arrays[i];
			}
			return str;
		}
		return null;
	}

	public static String join(List<String> arrays, String sep) {
		if (arrays != null && arrays.size() > 0) {
			String str = arrays.get(0);
			for (int i = 1; i < arrays.size(); i++) {
				str += sep;
				str += arrays.get(i);
			}
			return str;
		}
		return "";
	}

	/**
	 * 二维数组转置
	 * 
	 * @param objs
	 * @return
	 */
	public static Object[][] transpose(Object[][] objs) {
		int i = objs.length, j = objs[0].length;
		Object[][] t = new Object[j][i];
		for (int ii = 0; ii < j; ii++) {
			for (int jj = 0; jj < i; jj++) {
				t[ii][jj] = objs[jj][ii];
			}
		}
		return t;
	}
}
