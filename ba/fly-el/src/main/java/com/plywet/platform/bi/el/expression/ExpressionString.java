package com.plywet.platform.bi.el.expression;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

/**
 * 
 * <p>
 * Represents an expression String consisting of a mixture of Strings and
 * Expressions.
 * 
 * 该类表示一个表达式字符串，由String和Expression混合而成。
 * 
 * @author PeterPan
 **/

public class ExpressionString extends Expression {

	Object[] mElements;

	public Object[] getElements() {
		return mElements;
	}

	public void setElements(Object[] pElements) {
		mElements = pElements;
	}

	public ExpressionString(Object[] pElements) {
		mElements = pElements;
	}

	/**
	 * 
	 * Evaluates the expression string by evaluating each element, converting it
	 * to a String (using toString, or "" for null values) and concatenating the
	 * results into a single String.
	 **/
	@Override
	public Object evaluate(VariableResolver pResolver, FunctionMapper functions)
			throws ELException {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < mElements.length; i++) {
			Object elem = mElements[i];
			if (elem instanceof String) {
				buf.append((String) elem);
			} else if (elem instanceof Expression) {
				Object val = ((Expression) elem).evaluate(pResolver, functions);
				if (val != null) {
					buf.append(val.toString());
				}
			}
		}
		return buf.toString();
	}

	@Override
	public String getExpressionString() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < mElements.length; i++) {
			Object elem = mElements[i];
			if (elem instanceof String) {
				buf.append((String) elem);
			} else if (elem instanceof Expression) {
				buf.append("${");
				buf.append(((Expression) elem).getExpressionString());
				buf.append("}");
			}
		}
		return buf.toString();
	}

	@Override
	public Expression bindFunctions(FunctionMapper functions)
			throws ELException {
		final Object[] boundElements = new Object[mElements.length];
		for (int i = 0; i < mElements.length; i++) {
			if (mElements[i] instanceof Expression) {
				boundElements[i] = ((Expression) mElements[i])
						.bindFunctions(functions);
			} else {
				boundElements[i] = mElements[i];
			}
		}
		return new ExpressionString(boundElements);
	}
}
