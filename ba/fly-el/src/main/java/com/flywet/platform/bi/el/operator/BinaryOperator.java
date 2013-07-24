package com.flywet.platform.bi.el.operator;

import javax.servlet.jsp.el.ELException;

/**
 * 所有二元运算符的超类
 * 
 * @author PeterPan
 * 
 */
public abstract class BinaryOperator {

	public BinaryOperator() {
	}

	/**
	 * 返回运算符的标识
	 */
	public abstract String getOperatorSymbol();

	/**
	 * 应用运算符返回结果值
	 */
	public abstract Object apply(Object pLeft, Object pRight)
			throws ELException;

	/**
	 * 是否根据左值进行判定。 <br>
	 * 只针对And/Or运算符有效。<br>
	 */
	public boolean shouldEvaluate(Object pLeft) {
		return true;
	}

	/**
	 * 如果该运算符期望参数被强制转成Boolean值，则返回true。<br>
	 * 只针对And/Or操作符有效。<br>
	 */
	public boolean shouldCoerceToBoolean() {
		return false;
	}

}
