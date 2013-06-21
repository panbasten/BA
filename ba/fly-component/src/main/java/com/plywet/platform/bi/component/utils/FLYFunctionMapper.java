package com.plywet.platform.bi.component.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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

	private FLYFunctionMapper() {
		registerFunctions(null, FLYSystemFunctions.class);
	}

	private void registerFunctions(String prefix, Class<?> clazz) {
		Method methods[] = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			if (Modifier.isStatic(m.getModifiers())) {
				if (prefix != null && !"".equals(prefix)) {
					setMapping(prefix + ":" + m.getName(), m);
				} else {
					setMapping(m.getName(), m);
				}
			}
		}
	}

	public void setMapping(final String name, final Method function) {
		mappings.put(name, function);
	}

	@Override
	public Method resolveFunction(final String prefix, final String localName) {
		if (prefix != null && !"".equals(prefix)) {
			return mappings.get(prefix + ":" + localName);
		}
		return mappings.get(localName);
	}

}
