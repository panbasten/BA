package com.flywet.platform.bi.el.operator;

import javax.servlet.jsp.el.ELException;

import com.flywet.platform.bi.el.Coercions;

/**
 * 所有等号运算符的超类(==, !=)
 * 
 * @author PeterPan
 * 
 */
public abstract class EqualityOperator extends BinaryOperator {

	@Override
	public Object apply(Object pLeft, Object pRight) throws ELException {
		return Coercions.applyEqualityOperator(pLeft, pRight, this);
	}

	public abstract boolean apply(boolean pAreEqual);

}
