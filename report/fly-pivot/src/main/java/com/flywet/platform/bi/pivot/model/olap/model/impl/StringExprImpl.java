package com.flywet.platform.bi.pivot.model.olap.model.impl;

import com.flywet.platform.bi.pivot.model.olap.model.StringExpr;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

public class StringExprImpl implements StringExpr {
	private String value;

	public StringExprImpl() {
	}

	public StringExprImpl(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String string) {
		value = string;
	}

	public void accept(Visitor visitor) {
		visitor.visitStringExpr(this);
	}

}
