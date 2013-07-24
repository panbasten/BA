package com.flywet.platform.bi.el.operator;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 减法运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class MinusOperator extends ArithmeticOperator {

	public static final MinusOperator SINGLETON = new MinusOperator();

	public MinusOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "-";
	}

	@Override
	public double apply(double pLeft, double pRight) {
		return pLeft - pRight;
	}

	@Override
	public long apply(long pLeft, long pRight) {
		return pLeft - pRight;
	}

	@Override
	public BigDecimal apply(BigDecimal pLeft, BigDecimal pRight) {
		return pLeft.subtract(pRight);
	}

	@Override
	public BigInteger apply(BigInteger pLeft, BigInteger pRight) {
		return pLeft.subtract(pRight);
	}

}
