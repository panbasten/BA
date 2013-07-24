package com.flywet.platform.bi.el.operator;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.jsp.el.ELException;

/**
 * 大于运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class GreaterThanOperator extends RelationalOperator {

	public static final GreaterThanOperator SINGLETON = new GreaterThanOperator();

	public GreaterThanOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return ">";
	}

	@Override
	public Object apply(Object pLeft, Object pRight) throws ELException {
		if (pLeft == pRight) {
			return Boolean.FALSE;
		} else if (pLeft == null || pRight == null) {
			return Boolean.FALSE;
		} else {
			return super.apply(pLeft, pRight);
		}
	}

	@Override
	public boolean apply(double pLeft, double pRight) {
		return pLeft > pRight;
	}

	@Override
	public boolean apply(long pLeft, long pRight) {
		return pLeft > pRight;
	}

	@Override
	public boolean apply(String pLeft, String pRight) {
		return pLeft.compareTo(pRight) > 0;
	}

	@Override
	public boolean apply(BigDecimal pLeft, BigDecimal pRight) {
		return isGreater(pLeft.compareTo(pRight));
	}

	@Override
	public boolean apply(BigInteger pLeft, BigInteger pRight) {
		return isGreater(pLeft.compareTo(pRight));
	}

}
