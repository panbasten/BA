package com.flywet.platform.bi.component.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ComponentFunction {
	private List<String> parameters = new ArrayList<String>();

	private List<String> statements = new ArrayList<String>();

	private ComponentFunction() {

	}

	public static ComponentFunction instance() {
		return new ComponentFunction();
	}

	public ComponentFunction addParameter(String param) {
		this.parameters.add(param);
		return this;
	}

	public ComponentFunction addStatement(String statement) {
		this.statements.add(statement);
		return this;
	}

	public String toString() {
		StringBuffer rtn = new StringBuffer("function(");
		rtn.append(StringUtils.join(parameters, ","));
		rtn.append("){");
		rtn.append(StringUtils.join(statements, ""));
		rtn.append("}");

		return rtn.toString();
	}

}
