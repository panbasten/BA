package com.flywet.platform.bi.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 请求参数的上下文模型
 * 
 * @author han
 * 
 */
public class ParameterContext {
	private Map<String, List<String>> parameterHolder = new HashMap<String, List<String>>();

	/**
	 * 用参数字串填充参数上下文
	 * 
	 * @param paramsString
	 */
	public void fillContext(String paramsString) {
		if (StringUtils.isEmpty(paramsString)) {
			return;
		}

		if (!StringUtils.contains(paramsString, '=')) {
			return;
		}

		String[] paramEntrys = paramsString.split("&");
		for (String entry : paramEntrys) {
			if (!StringUtils.contains(entry, '=')) {
				continue;
			}
			String[] param = entry.split("=");
			if (param.length < 2) {
				continue;
			}
			List<String> values = parameterHolder.get(param[0]);
			if (values == null) {
				values = new ArrayList<String>();
			}
			values.add(param[1]);
			parameterHolder.put(param[0], values);
		}
	}

	public Map<String, List<String>> getParameterHolder() {
		return parameterHolder;
	}

	/**
	 * 从参数上下文中根据参数名获取对应的参数值 如果对应的值为多个则返回第一个
	 * 
	 * @param paramName
	 *            参数名
	 * @return
	 */
	public String getParameter(String paramName) {
		if (StringUtils.isEmpty(paramName)) {
			return null;
		}
		List<String> paramValues = parameterHolder.get(paramName);
		if (paramValues == null || paramValues.isEmpty()) {
			return null;
		}

		return paramValues.get(0);
	}

	/**
	 * 从参数上下文中根据参数名获取对应的多个值
	 * 
	 * @param paramName
	 *            参数名
	 * @return
	 */
	public List<String> getParameterValues(String paramName) {
		if (StringUtils.isEmpty(paramName)) {
			return null;
		}

		List<String> paramValues = parameterHolder.get(paramName);
		return paramValues;
	}

	public String getStringParameter(String paramName) {
		return getStringParameter(paramName, null);
	}

	public String getStringParameter(String paramName, String def) {
		String v = getParameter(paramName);
		if (v != null && !"".equals(v.trim())) {
			return v.trim();
		}
		return def;
	}

	public int getIntParameter(String paramName) {
		return getIntParameter(paramName, 0);
	}

	public int getIntParameter(String paramName, int def) {
		String v = getParameter(paramName);
		try {
			return Integer.valueOf(v);
		} catch (Exception e) {
			return def;
		}
	}

	public long getLongParameter(String paramName) {
		return getLongParameter(paramName, 0L);
	}

	public long getLongParameter(String paramName, long def) {
		String v = getParameter(paramName);
		try {
			return Long.valueOf(v);
		} catch (Exception e) {
			return def;
		}
	}

	public boolean getBooleanParameter(String paramName) {
		String v = getParameter(paramName);
		return (v != null);
	}

}
