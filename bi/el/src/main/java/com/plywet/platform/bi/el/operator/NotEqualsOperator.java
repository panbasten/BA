package com.plywet.platform.bi.el.operator;

/**
 * 不等于运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class NotEqualsOperator extends EqualityOperator {

	public static final NotEqualsOperator SINGLETON = new NotEqualsOperator();

	public NotEqualsOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "!=";
	}

	@Override
	public boolean apply(boolean pAreEqual) {
		return !pAreEqual;
	}

}
