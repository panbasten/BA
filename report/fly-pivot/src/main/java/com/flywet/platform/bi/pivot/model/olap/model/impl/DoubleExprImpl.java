package com.flywet.platform.bi.pivot.model.olap.model.impl;

import com.flywet.platform.bi.pivot.model.olap.model.DoubleExpr;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

public class DoubleExprImpl implements DoubleExpr {

	private double value;

	public DoubleExprImpl() {
	}

	public DoubleExprImpl(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double d) {
		value = d;
	}

	public void accept(Visitor visitor) {
		visitor.visitDoubleExpr(this);
	}

}
