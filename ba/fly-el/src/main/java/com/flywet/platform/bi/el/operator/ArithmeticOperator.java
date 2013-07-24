package com.flywet.platform.bi.el.operator;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.jsp.el.ELException;

import com.flywet.platform.bi.el.Coercions;

/**
 * 所有二元算术运算符的超类
 * 
 * @author PeterPan
 * 
 */
public abstract class ArithmeticOperator extends BinaryOperator {

	@Override
	public Object apply(Object pLeft, Object pRight) throws ELException {
		return Coercions.applyArithmeticOperator(pLeft, pRight, this);
	}

	public abstract double apply(double pLeft, double pRight);

	public abstract long apply(long pLeft, long pRight);

	public abstract BigDecimal apply(BigDecimal pLeft, BigDecimal pRight);

	public abstract BigInteger apply(BigInteger pLeft, BigInteger pRight);

}
