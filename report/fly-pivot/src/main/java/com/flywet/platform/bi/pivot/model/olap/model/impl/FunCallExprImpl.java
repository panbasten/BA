package com.flywet.platform.bi.pivot.model.olap.model.impl;

import java.util.List;

import com.flywet.platform.bi.pivot.model.olap.model.Expression;
import com.flywet.platform.bi.pivot.model.olap.model.FunCallExpr;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

public class FunCallExprImpl implements FunCallExpr {
	private Expression[] args;
	private String name;

	public FunCallExprImpl() {
	}

	public FunCallExprImpl(String name) {
		this.name = name;
		this.args = new Expression[0];
	}

	public FunCallExprImpl(String name, Expression arg) {
		this.name = name;
		this.args = new Expression[] { arg };
	}

	public FunCallExprImpl(String name, Expression arg0, Expression arg1) {
		this.name = name;
		this.args = new Expression[] { arg0, arg1 };
	}

	public FunCallExprImpl(String name, Expression arg0, Expression arg1,
			Expression arg2) {
		this.name = name;
		this.args = new Expression[] { arg0, arg1, arg2 };
	}

	public FunCallExprImpl(String name, Expression[] args) {
		this.name = name;
		this.args = args;
	}

	public FunCallExprImpl(String name, List args) {
		this.name = name;
		this.args = (Expression[]) args.toArray(new Expression[args.size()]);
	}

	public Expression[] getArgs() {
		return args;
	}

	public String getName() {
		return name;
	}

	public void setArgs(Expression[] expressions) {
		args = expressions;
	}

	public void setArgs(List list) {
		this.args = (Expression[]) list.toArray(new Expression[list.size()]);
	}

	public void setName(String string) {
		name = string;
	}

	public void accept(Visitor visitor) {
		visitor.visitFunCallExpr(this);
	}

}
