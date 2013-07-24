package com.flywet.platform.bi.el.operator;

import javax.servlet.jsp.el.ELException;

import com.flywet.platform.bi.el.Coercions;
import com.flywet.platform.bi.el.PrimitiveObjects;

/**
 * Or运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class OrOperator extends BinaryOperator {

	public static final OrOperator SINGLETON = new OrOperator();

	public OrOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "or";
	}

	@Override
	public Object apply(Object pLeft, Object pRight) throws ELException {
		// Coerce the values to booleans
		boolean left = Coercions.coerceToBoolean(pLeft).booleanValue();
		boolean right = Coercions.coerceToBoolean(pRight).booleanValue();

		return PrimitiveObjects.getBoolean(left || right);
	}

	@Override
	public boolean shouldEvaluate(Object pLeft) {
		return (pLeft instanceof Boolean)
				&& ((Boolean) pLeft).booleanValue() == false;
	}

	@Override
	public boolean shouldCoerceToBoolean() {
		return true;
	}

}
