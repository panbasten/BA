package com.plywet.platform.bi.component.utils;

import javax.servlet.jsp.el.VariableResolver;
import javax.servlet.jsp.el.ELException;

import java.util.Map;
import java.util.HashMap;

public class FLYVariableResolver implements VariableResolver {

	private Map<String, Object> variableMap;

	public FLYVariableResolver() {
		this.variableMap = new HashMap<String, Object>();
	}

	public void addVariable(String variable, Object value) {
		this.variableMap.put(variable, value);
	}

	public Object getVariable(String variable) {
		return this.variableMap.get(variable);
	}

	public boolean containsVariable(String variable) {
		return this.variableMap.containsKey(variable);
	}

	@Override
	public Object resolveVariable(String pName) throws ELException {
		return this.variableMap.get(pName);
	}

	public static FLYVariableResolver instance() {
		return new FLYVariableResolver();
	}

}
