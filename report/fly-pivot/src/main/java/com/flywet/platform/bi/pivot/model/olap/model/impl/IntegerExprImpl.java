package com.flywet.platform.bi.pivot.model.olap.model.impl;

import com.flywet.platform.bi.pivot.model.olap.model.IntegerExpr;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

public class IntegerExprImpl implements IntegerExpr {
	private int value;

	public IntegerExprImpl() {
	}

	public IntegerExprImpl(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int i) {
		value = i;
	}

	public void accept(Visitor visitor) {
		visitor.visitIntegerExpr(this);
	}

}
