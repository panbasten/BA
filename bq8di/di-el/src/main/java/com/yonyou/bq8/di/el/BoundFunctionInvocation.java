package com.yonyou.bq8.di.el;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;

import com.yonyou.bq8.di.el.expression.FunctionInvocation;

/**
 * A subclass of <code>FunctionInvocation</code> which is bound to a particular
 * <code>Method</code>. A bound function does not require a
 * <code>FunctionMapper</code> to be evaluated.
 * 
 * @author PeterPan
 * 
 */
public class BoundFunctionInvocation extends FunctionInvocation {
	private final Method method;

	/**
	 * @param functionName
	 * @param argumentList
	 */
	public BoundFunctionInvocation(final Method method,
			final String functionName, final List argumentList) {
		super(functionName, argumentList);
		this.method = method;
	}

	/**
	 * Returns the <code>Method</code>supplied to the constructor.
	 * 
	 * @param functions
	 *            unused
	 */
	protected Method resolveFunction(FunctionMapper functions)
			throws ELException {
		return method;
	}
}
