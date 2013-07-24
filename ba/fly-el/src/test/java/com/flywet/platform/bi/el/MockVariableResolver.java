package com.flywet.platform.bi.el;

import javax.servlet.jsp.el.VariableResolver;
import javax.servlet.jsp.el.ELException;

import java.util.Map;
import java.util.HashMap;

public class MockVariableResolver implements VariableResolver {

	private Map variableMap;

	public MockVariableResolver() {
		this.variableMap = new HashMap();
	}

	public void addVariable(String variable, Object value) {
		this.variableMap.put(variable, value);
	}

	public Object resolveVariable(String pName) throws ELException {
		return this.variableMap.get(pName);
	}

}
