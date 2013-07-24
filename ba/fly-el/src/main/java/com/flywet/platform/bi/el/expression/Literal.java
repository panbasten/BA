package com.flywet.platform.bi.el.expression;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

/**
 * 常量值表达式派生的抽象类
 * 
 * @author PeterPan
 * 
 */
public abstract class Literal extends Expression {

	Object mValue;

	public Object getValue() {
		return mValue;
	}

	public void setValue(Object pValue) {
		mValue = pValue;
	}

	public Literal(Object pValue) {
		mValue = pValue;
	}

	@Override
	public Object evaluate(VariableResolver pResolver, FunctionMapper functions)
			throws ELException {
		return mValue;
	}

	@Override
	public Expression bindFunctions(FunctionMapper functions)
			throws ELException {
		return this;
	}

}
