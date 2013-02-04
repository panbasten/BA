package com.yonyou.bq8.di.component.utils;

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
public class BQFunctionMapper implements FunctionMapper {

	public static final BQFunctionMapper singleton = new BQFunctionMapper();

	private Map<String, Method> mappings = new HashMap<String, Method>();

	public void setMapping(final String name, final Method function) {
		mappings.put(name, function);
	}

	@Override
	public Method resolveFunction(final String prefix, final String localName) {
		return mappings.get(localName);
	}

}
