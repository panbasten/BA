package com.flywet.platform.bi.el.operator;

import javax.servlet.jsp.el.ELException;

import com.flywet.platform.bi.el.Coercions;
import com.flywet.platform.bi.el.PrimitiveObjects;

/**
 * And运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class AndOperator extends BinaryOperator {

	public static final AndOperator SINGLETON = new AndOperator();

	public AndOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "and";
	}

	@Override
	public Object apply(Object pLeft, Object pRight) throws ELException {
		// Coerce the values to booleans
		boolean left = Coercions.coerceToBoolean(pLeft).booleanValue();
		boolean right = Coercions.coerceToBoolean(pRight).booleanValue();

		return PrimitiveObjects.getBoolean(left && right);
	}

	@Override
	public boolean shouldEvaluate(Object pLeft) {
		return (pLeft instanceof Boolean)
				&& ((Boolean) pLeft).booleanValue() == true;
	}

	@Override
	public boolean shouldCoerceToBoolean() {
		return true;
	}

}
