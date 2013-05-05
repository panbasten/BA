package com.plywet.platform.bi.el.operator;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 乘法运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class MultiplyOperator extends ArithmeticOperator {

	public static final MultiplyOperator SINGLETON = new MultiplyOperator();

	public MultiplyOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "*";
	}

	@Override
	public double apply(double pLeft, double pRight) {
		return pLeft * pRight;
	}

	@Override
	public long apply(long pLeft, long pRight) {
		return pLeft * pRight;
	}

	@Override
	public BigDecimal apply(BigDecimal pLeft, BigDecimal pRight) {
		return pLeft.multiply(pRight);
	}

	@Override
	public BigInteger apply(BigInteger pLeft, BigInteger pRight) {
		return pLeft.multiply(pRight);
	}

}
