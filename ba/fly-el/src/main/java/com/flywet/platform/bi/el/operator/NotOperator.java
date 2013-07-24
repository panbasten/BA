package com.flywet.platform.bi.el.operator;

import javax.servlet.jsp.el.ELException;

import com.flywet.platform.bi.el.Coercions;
import com.flywet.platform.bi.el.PrimitiveObjects;

/**
 * not运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class NotOperator extends UnaryOperator {

	public static final NotOperator SINGLETON = new NotOperator();

	public NotOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "not";
	}

	@Override
	public Object apply(Object pValue) throws ELException {
		// Coerce the value to a boolean
		boolean val = Coercions.coerceToBoolean(pValue).booleanValue();

		return PrimitiveObjects.getBoolean(!val);
	}

}
