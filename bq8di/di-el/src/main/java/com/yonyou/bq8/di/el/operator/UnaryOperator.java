package com.yonyou.bq8.di.el.operator;

import javax.servlet.jsp.el.ELException;

/**
 * 所有一元运算符的超类
 * 
 * @author PeterPan
 * 
 */
public abstract class UnaryOperator {
	public UnaryOperator() {
	}

	/**
	 * 返回运算符的标识
	 */
	public abstract String getOperatorSymbol();

	/**
	 * 应用运算符返回结果值
	 */
	public abstract Object apply(Object pValue) throws ELException;

}
