package com.flywet.platform.bi.pivot.model.olap.model.impl;

import com.flywet.platform.bi.pivot.model.olap.model.Expression;
import com.flywet.platform.bi.pivot.model.olap.model.ParameterExpr;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

public class ParameterExprImpl implements ParameterExpr {

	private String name;
	private String label;
	private int type;
	private Expression value;

	public ParameterExprImpl() {
	}

	public ParameterExprImpl(String name, String label, int type,
			Expression value) {
		this.name = name;
		this.label = label;
		this.type = type;
		this.value = value;
	}

	public void accept(Visitor visitor) {
		visitor.visitParameterExpr(this);
	}

	public String getLabel() {
		return label;
	}

	public Expression getValue() {
		return value;
	}

	public void setLabel(String string) {
		label = string;
	}

	public void setValue(Expression expression) {
		value = expression;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public void setName(String string) {
		name = string;
	}

	public void setType(int i) {
		type = i;
	}

}
