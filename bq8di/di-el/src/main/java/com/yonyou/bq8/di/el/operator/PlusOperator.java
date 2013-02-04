package com.yonyou.bq8.di.el.operator;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 加法运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class PlusOperator extends ArithmeticOperator {

	public static final PlusOperator SINGLETON = new PlusOperator();

	public PlusOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "+";
	}

	@Override
	public double apply(double pLeft, double pRight) {
		return pLeft + pRight;
	}

	@Override
	public long apply(long pLeft, long pRight) {
		return pLeft + pRight;
	}

	@Override
	public BigDecimal apply(BigDecimal pLeft, BigDecimal pRight) {
		return pLeft.add(pRight);
	}

	@Override
	public BigInteger apply(BigInteger pLeft, BigInteger pRight) {
		return pLeft.add(pRight);
	}

}
