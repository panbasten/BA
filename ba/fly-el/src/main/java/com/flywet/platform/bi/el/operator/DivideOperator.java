package com.flywet.platform.bi.el.operator;

import java.math.BigDecimal;

import javax.servlet.jsp.el.ELException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.flywet.platform.bi.el.Coercions;
import com.flywet.platform.bi.el.Constants;
import com.flywet.platform.bi.el.MessageUtil;
import com.flywet.platform.bi.el.PrimitiveObjects;

/**
 * 除法运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class DivideOperator extends BinaryOperator {

	private static Log log = LogFactory.getLog(DivideOperator.class);

	public static final DivideOperator SINGLETON = new DivideOperator();

	public DivideOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "/";
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

		if (Coercions.isBigDecimal(pLeft) || Coercions.isBigInteger(pLeft)
				|| Coercions.isBigDecimal(pRight)
				|| Coercions.isBigInteger(pRight)) {

			BigDecimal left = (BigDecimal) Coercions.coerceToPrimitiveNumber(
					pLeft, BigDecimal.class);
			BigDecimal right = (BigDecimal) Coercions.coerceToPrimitiveNumber(
					pRight, BigDecimal.class);

			try {
				return left.divide(right, BigDecimal.ROUND_HALF_UP);
			} catch (Exception exc) {
				if (log.isErrorEnabled()) {
					String message = MessageUtil.getMessageWithArgs(
							Constants.ARITH_ERROR, getOperatorSymbol(), ""
									+ left, "" + right);
					log.error(message);
					throw new ELException(message);
				}
				return PrimitiveObjects.getInteger(0);
			}
		} else {

			double left = Coercions
					.coerceToPrimitiveNumber(pLeft, Double.class).doubleValue();
			double right = Coercions.coerceToPrimitiveNumber(pRight,
					Double.class).doubleValue();

			try {
				return PrimitiveObjects.getDouble(left / right);
			} catch (Exception exc) {
				if (log.isErrorEnabled()) {
					String message = MessageUtil.getMessageWithArgs(
							Constants.ARITH_ERROR, getOperatorSymbol(), ""
									+ left, "" + right);
					log.error(message);
					throw new ELException(message);
				}
				return PrimitiveObjects.getInteger(0);
			}
		}
	}

}
