package com.flywet.platform.bi.el.operator;

import javax.servlet.jsp.el.ELException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.flywet.platform.bi.el.Coercions;
import com.flywet.platform.bi.el.Constants;
import com.flywet.platform.bi.el.MessageUtil;
import com.flywet.platform.bi.el.PrimitiveObjects;

/**
 * 整数除法运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class IntegerDivideOperator extends BinaryOperator {
	private static Log log = LogFactory.getLog(IntegerDivideOperator.class);

	public static final IntegerDivideOperator SINGLETON = new IntegerDivideOperator();

	public IntegerDivideOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "idiv";
	}

	@Override
	public Object apply(Object pLeft, Object pRight) throws ELException {
		if (pLeft == null && pRight == null) {
			if (log.isWarnEnabled()) {
				log.warn(MessageUtil.getMessageWithArgs(
						Constants.ARITH_OP_NULL, getOperatorSymbol()));
			}
			return PrimitiveObjects.getInteger(0);
		}

		long left = Coercions.coerceToPrimitiveNumber(pLeft, Long.class)
				.longValue();
		long right = Coercions.coerceToPrimitiveNumber(pRight, Long.class)
				.longValue();

		try {
			return PrimitiveObjects.getLong(left / right);
		} catch (Exception exc) {
			if (log.isErrorEnabled()) {
				String message = MessageUtil.getMessageWithArgs(
						Constants.ARITH_ERROR, getOperatorSymbol(), "" + left,
						"" + right);
				log.error(message);
			}
			return PrimitiveObjects.getInteger(0);
		}
	}

}
