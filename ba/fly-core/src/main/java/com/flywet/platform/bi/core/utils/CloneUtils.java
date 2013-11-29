package com.flywet.platform.bi.core.utils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.ibm.icu.math.BigDecimal;

public class CloneUtils {
	public static final Class<?>[] ITOM_CLASS = new Class<?>[] { String.class,
			Integer.class, Long.class, Short.class, Byte.class, Boolean.class,
			Float.class, Double.class, BigDecimal.class, Character.class };

	/**
	 * 深度克隆一个对象
	 * 
	 * @param <T>
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deepCopy(T obj) {
		if (obj == null) {
			return null;
		}

		if (obj instanceof Class<?> || isItomObject(obj)) {
			return obj;
		}

		if (obj instanceof Collection) {
			return (T) deepCopy((Collection) obj);
		}

		if (obj instanceof Map) {
			return (T) deepCopy((Map) obj);
		}

		try {
			Method method = obj.getClass().getMethod("clone");
			if (!method.isAccessible()) {
				method.setAccessible(true);
			}
			T copy = (T) method.invoke(obj);
			return copy;
		} catch (Exception e) {
			throw new RuntimeException("深度拷贝出现错误");
		}

	}

	/**
	 * 对集合进行克隆
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Collection deepCopy(Collection obj) {
		if (obj == null) {
			return null;
		}
		Collection copy = null;
		try {
			copy = (Collection) obj.getClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		for (Iterator iter = obj.iterator(); iter.hasNext();) {
			Object element = iter.next();
			copy.add(deepCopy(element));
		}

		return copy;
	}

	/**
	 * 对Map进行克隆
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2> Map<T1, T2> deepCopy(Map<T1, T2> obj) {
		if (obj == null) {
			return null;
		}
		Map copy = null;
		try {
			copy = (Map) obj.getClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		for (Iterator iter = obj.keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			Object value = obj.get(key);
			copy.put(deepCopy(key), deepCopy(value));
		}

		return copy;
	}

	/**
	 * 判断对象是否是原子对象
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isItomObject(Object obj) {
		if (obj == null) {
			return false;
		}
		Class<?> cls = obj.getClass();
		if (cls.isPrimitive() || cls.isEnum()) {
			return true;
		}

		for (Class<?> itomCls : ITOM_CLASS) {
			if (itomCls == cls) {
				return true;
			}
		}

		return false;
	}

}
