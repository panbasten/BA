package com.plywet.platform.bi.el.operator;

/**
 * 等号运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class EqualsOperator extends EqualityOperator {

	public static final EqualsOperator SINGLETON = new EqualsOperator();

	public EqualsOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "==";
	}

	@Override
	public boolean apply(boolean pAreEqual) {
		return pAreEqual;
	}

}
