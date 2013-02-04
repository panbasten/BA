package com.yonyou.bq8.di.el.operator;

import java.math.BigInteger;

import javax.servlet.jsp.el.ELException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yonyou.bq8.di.el.Coercions;
import com.yonyou.bq8.di.el.Constants;
import com.yonyou.bq8.di.el.MessageUtil;
import com.yonyou.bq8.di.el.PrimitiveObjects;

/**
 * 取模运算符的实现类
 * 
 * @author PeterPan
 * 
 */
public class ModulusOperator extends BinaryOperator {

	private static Log log = LogFactory.getLog(ModulusOperator.class);

	public static final ModulusOperator SINGLETON = new ModulusOperator();

	public ModulusOperator() {
	}

	@Override
	public String getOperatorSymbol() {
		return "%";
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

		if ((pLeft != null && (Coercions.isFloatingPointType(pLeft) || Coercions
				.isFloatingPointString(pLeft)))
				|| Coercions.isBigDecimal(pLeft)
				|| (pRight != null && (Coercions.isFloatingPointType(pRight)
						|| Coercions.isFloatingPointString(pRight) || Coercions
						.isBigDecimal(pRight)))) {
			double left = Coercions
					.coerceToPrimitiveNumber(pLeft, Double.class).doubleValue();
			double right = Coercions.coerceToPrimitiveNumber(pRight,
					Double.class).doubleValue();

			try {
				return PrimitiveObjects.getDouble(left % right);
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
		} else if (Coercions.isBigInteger(pLeft)
				|| Coercions.isBigInteger(pRight)) {
			BigInteger left = (BigInteger) Coercions.coerceToPrimitiveNumber(
					pLeft, BigInteger.class);
			BigInteger right = (BigInteger) Coercions.coerceToPrimitiveNumber(
					pRight, BigInteger.class);

			try {
				return left.remainder(right);
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
			long left = Coercions.coerceToPrimitiveNumber(pLeft, Long.class)
					.longValue();
			long right = Coercions.coerceToPrimitiveNumber(pRight, Long.class)
					.longValue();

			try {
				return PrimitiveObjects.getLong(left % right);
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
