package com.yonyou.bq8.di.el.expression.suffix;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

/**
 * 
 * <p>
 * Represents an element that can appear as a suffix in a complex value, such as
 * a property or index operator, or a method call (should they ever need to be
 * supported).<br>
 * 
 * 该类表示所有值后缀的派生类，用作复杂值类型表达式的后缀。 <br>
 * 例如，属性或者索引运算符，或者一个方法调用（需要提供特定支持代码）
 * 
 * @author PeterPan
 * 
 **/

public abstract class ValueSuffix {
	/**
	 * 返回符合表达式语法的表达式字符串
	 * 
	 * @return
	 */
	public abstract String getExpressionString();

	/**
	 * 在给定的上下文中计算表达式的值
	 * 
	 * @param pValue
	 * @param pResolver
	 * @param functions
	 * @return
	 * @throws ELException
	 */
	public abstract Object evaluate(Object pValue, VariableResolver pResolver,
			FunctionMapper functions) throws ELException;

	public abstract ValueSuffix bindFunctions(FunctionMapper functions)
			throws ELException;

}
