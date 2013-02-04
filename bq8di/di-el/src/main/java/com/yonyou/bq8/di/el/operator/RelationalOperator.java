package com.yonyou.bq8.di.el.operator;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.jsp.el.ELException;

import com.yonyou.bq8.di.el.Coercions;

/**
 * 所有关系运算符（除了==和!=）
 * 
 * @author PeterPan
 * 
 */
public abstract class RelationalOperator extends BinaryOperator {

	@Override
	public Object apply(Object pLeft, Object pRight) throws ELException {
		return Coercions.applyRelationalOperator(pLeft, pRight, this);
	}

	public abstract boolean apply(double pLeft, double pRight);

	public abstract boolean apply(long pLeft, long pRight);

	public abstract boolean apply(String pLeft, String pRight);

	public abstract boolean apply(BigDecimal pLeft, BigDecimal pRight);

	public abstract boolean apply(BigInteger pLeft, BigInteger pRight);

	/**
	 * 用于测试BigInteger/BigDecimal的A.compareTo(B)的返回值
	 * 
	 * @param val
	 *            - BigInteger/BigDecimal调用compareTo()的返回值
	 * @return
	 */
	protected boolean isLess(int val) {
		if (val < 0)
			return true;
		else
			return false;
	}

	/**
	 * 用于测试BigInteger/BigDecimal的A.compareTo(B)的返回值
	 * 
	 * @param val
	 *            - BigInteger/BigDecimal调用compareTo()的返回值
	 * @return
	 */
	protected boolean isEqual(int val) {
		if (val == 0)
			return true;
		else
			return false;
	}

	/**
	 * 用于测试BigInteger/BigDecimal的A.compareTo(B)的返回值
	 * 
	 * @param val
	 *            - BigInteger/BigDecimal调用compareTo()的返回值
	 * @return
	 */
	protected boolean isGreater(int val) {
		if (val > 0)
			return true;
		else
			return false;
	}
}
