package com.flywet.platform.bi.el.operator;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.jsp.el.ELException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.flywet.platform.bi.el.Coercions;
import com.flywet.platform.bi.el.Constants;
import com.flywet.platform.bi.el.MessageUtil;
import com.flywet.platform.bi.el.PrimitiveObjects;

/**
 * 一元减号运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class UnaryMinusOperator extends UnaryOperator {

	private static Log log = LogFactory.getLog(UnaryMinusOperator.class);

	public static final UnaryMinusOperator SINGLETON = new UnaryMinusOperator();

	public UnaryMinusOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "-";
	}

	@Override
	public Object apply(Object pValue) throws ELException {
		if (pValue == null) {

			return PrimitiveObjects.getInteger(0);
		}

		else if (pValue instanceof BigInteger) {
			return ((BigInteger) pValue).negate();
		}

		else if (pValue instanceof BigDecimal) {
			return ((BigDecimal) pValue).negate();
		}

		else if (pValue instanceof String) {
			if (Coercions.isFloatingPointString(pValue)) {
				double dval = ((Number) (Coercions.coerceToPrimitiveNumber(
						pValue, Double.class))).doubleValue();
				return PrimitiveObjects.getDouble(-dval);
			} else {
				long lval = ((Number) (Coercions.coerceToPrimitiveNumber(
						pValue, Long.class))).longValue();
				return PrimitiveObjects.getLong(-lval);
			}
		}

		else if (pValue instanceof Byte) {
			return PrimitiveObjects.getByte((byte) -(((Byte) pValue)
					.byteValue()));
		} else if (pValue instanceof Short) {
			return PrimitiveObjects.getShort((short) -(((Short) pValue)
					.shortValue()));
		} else if (pValue instanceof Integer) {
			return PrimitiveObjects.getInteger((int) -(((Integer) pValue)
					.intValue()));
		} else if (pValue instanceof Long) {
			return PrimitiveObjects.getLong((long) -(((Long) pValue)
					.longValue()));
		} else if (pValue instanceof Float) {
			return PrimitiveObjects.getFloat((float) -(((Float) pValue)
					.floatValue()));
		} else if (pValue instanceof Double) {
			return PrimitiveObjects.getDouble((double) -(((Double) pValue)
					.doubleValue()));
		}

		else {
			if (log.isErrorEnabled()) {
				String message = MessageUtil.getMessageWithArgs(
						Constants.UNARY_OP_BAD_TYPE, getOperatorSymbol(),
						pValue.getClass().getName());
				log.error(message);
				throw new ELException(message);
			}
			return PrimitiveObjects.getInteger(0);
		}
	}

}
