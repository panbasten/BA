package com.flywet.platform.bi.el.expression;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

/**
 * 所有表达式类型派生的抽象类
 * 
 * @author PeterPan
 * 
 */
public abstract class Expression {
	/**
	 * 返回符合表达式语法的表达式字符串
	 * 
	 * @return
	 */
	public abstract String getExpressionString();

	/**
	 * 在给定的上下文中计算表达式的值
	 * 
	 * @param pResolver
	 * @param functions
	 * @return
	 * @throws ELException
	 */
	public abstract Object evaluate(VariableResolver pResolver,
			FunctionMapper functions) throws ELException;

	// -------------------------------------

	/**
	 * Returns an expression with all <code>FunctionInvocation</code>s replaced
	 * by <code>BoundFunctionInvocation</code>s.
	 * 
	 * @param functions
	 *            the functions to use in this transformation
	 * @return an Expression identical to this expression except with all
	 *         <code>FunctionInvocation</code>s replaced by
	 *         <code>BoundFunctionInvocation</code>s.
	 * @throws ELException
	 *             if any of the functions in this <code>Expression</code> are
	 *             not present in <code>functions</code>
	 */
	public abstract Expression bindFunctions(FunctionMapper functions)
			throws ELException;

}
