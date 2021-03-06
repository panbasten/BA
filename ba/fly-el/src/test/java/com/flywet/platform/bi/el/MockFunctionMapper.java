package com.flywet.platform.bi.el;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.el.FunctionMapper;

public class MockFunctionMapper implements FunctionMapper {

	private Map functionMap;

	public MockFunctionMapper() {
		this.functionMap = new HashMap();
	}

	public MockFunctionMapper(Map map) {
		this.functionMap = map;
	}

	public void addIdentityMethod(String name) {
		try {
			getClass().getMethod("identity", new Class[] { Object.class });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object identity(Object input) {
		return input;
	}

	public Method resolveFunction(String prefix, String localName) {
		return (Method) this.functionMap.get(localName);
	}

}
