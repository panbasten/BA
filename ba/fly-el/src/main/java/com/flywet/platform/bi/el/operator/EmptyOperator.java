package com.flywet.platform.bi.el.operator;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import javax.servlet.jsp.el.ELException;

import com.flywet.platform.bi.el.PrimitiveObjects;

/**
 * 判空运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class EmptyOperator extends UnaryOperator {

	public static final EmptyOperator SINGLETON = new EmptyOperator();

	public EmptyOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "empty";
	}

	@Override
	public Object apply(Object pValue) throws ELException {
		// See if the value is null
		if (pValue == null) {
			return PrimitiveObjects.getBoolean(true);
		}

		// See if the value is a zero-length String
		else if ("".equals(pValue)) {
			return PrimitiveObjects.getBoolean(true);
		}

		// See if the value is a zero-length array
		else if (pValue.getClass().isArray() && Array.getLength(pValue) == 0) {
			return PrimitiveObjects.getBoolean(true);
		}

		// See if the value is an empty Map
		else if (pValue instanceof Map && ((Map) pValue).isEmpty()) {
			return PrimitiveObjects.getBoolean(true);
		}

		// See if the value is an empty Collection
		else if (pValue instanceof Collection
				&& ((Collection) pValue).isEmpty()) {
			return PrimitiveObjects.getBoolean(true);
		}

		// Otherwise, not empty
		else {
			return PrimitiveObjects.getBoolean(false);
		}
	}

}
