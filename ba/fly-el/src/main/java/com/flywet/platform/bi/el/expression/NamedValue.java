package com.flywet.platform.bi.el.expression;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

/**
 * 该类表示一个命名元素的表达式
 * 
 * @author PeterPan
 * 
 */
public class NamedValue extends Expression {

	String mName;

	public String getName() {
		return mName;
	}

	public NamedValue(String pName) {
		mName = pName;
	}

	@Override
	public String getExpressionString() {
		return StringLiteral.toIdentifierToken(mName);
	}

	@Override
	public Object evaluate(VariableResolver pResolver, FunctionMapper functions)
			throws ELException {
		if (pResolver == null) {
			return null;
		} else {
			return pResolver.resolveVariable(mName);
		}
	}

	@Override
	public Expression bindFunctions(FunctionMapper functions)
			throws ELException {
		return this;
	}

}
