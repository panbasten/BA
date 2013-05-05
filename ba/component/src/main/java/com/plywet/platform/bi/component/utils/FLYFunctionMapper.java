package com.plywet.platform.bi.component.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.el.FunctionMapper;

/**
 * EL表达式用到的解析方法映射
 * 
 * @author PeterPan
 * 
 */
public class FLYFunctionMapper implements FunctionMapper {

	public static final FLYFunctionMapper singleton = new FLYFunctionMapper();

	private Map<String, Method> mappings = new HashMap<String, Method>();

	public void setMapping(final String name, final Method function) {
		mappings.put(name, function);
	}

	@Override
	public Method resolveFunction(final String prefix, final String localName) {
		return mappings.get(localName);
	}

}
