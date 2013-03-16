package com.plywet.platform.bi.el;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.jsp.el.ELException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.plywet.platform.bi.el.operator.ArithmeticOperator;
import com.plywet.platform.bi.el.operator.EqualityOperator;
import com.plywet.platform.bi.el.operator.RelationalOperator;

/**
 * 
 * <p>
 * This class contains the logic for coercing data types before operators are
 * applied to them.<br>
 * 该类是数据强制转型的工具类，用于运算符使用该数据时。
 * 
 * <p>
 * The following is the list of rules applied for various type conversions.<br>
 * 下面是变量类型转换的应用规则列表：
 * 
 * <ul>
 * 
 * <pre>
 * 应用于算术运算符：
 *   Binary operator - A {+,-,*} B
 *     if A and B are null
 *       return 0
 *     if A or B is BigDecimal, coerce both to BigDecimal and then:
 *       if operator is +, return <code>A.add(B)</code>
 *       if operator is -, return <code>A.subtract(B)</code>
 *       if operator is *, return <code>A.multiply(B)</code>
 *     if A or B is Float, Double, or String containing ".", "e", or "E"
 *       if A or B is BigInteger, coerce both A and B to BigDecimal and apply operator
 *       coerce both A and B to Double and apply operator
 *     if A or B is BigInteger, coerce both to BigInteger and then:
 *       if operator is +, return <code>A.add(B)</code>
 *       if operator is -, return <code>A.subtract(B)</code>
 *       if operator is *, return <code>A.multiply(B)</code>
 *     otherwise
 *       coerce both A and B to Long
 *       apply operator
 *     if operator results in exception (such as divide by 0), error
 * 
 *   Binary operator - A {/,div} B
 *     if A and B are null
 *       return 0
 *     if A or B is a BigDecimal or BigInteger, coerce both to BigDecimal and
 *      return <code>A.divide(B, BigDecimal.ROUND_HALF_UP)</code>
 *     otherwise
 *       coerce both A and B to Double
 *       apply operator
 *     if operator results in exception (such as divide by 0), error
 * 
 *   Binary operator - A {%,mod} B
 *     if A and B are null
 *       return 0
 *     if A or B is BigDecimal, Float, Double, or String containing ".", "e" or "E"
 *       coerce both to Double
 *       apply operator
 *     if A or B is BigInteger, coerce both to BigInteger and return
 *      <code>A.remainder(B)</code>
 *     otherwise
 *       coerce both A and B to Long
 *       apply operator
 *     if operator results in exception (such as divide by 0), error
 * 
 *   Unary minus operator - -A
 *     if A is null
 *       return 0
 *     if A is BigInteger or BigDecimal, return <code>A.negate()</code>
 *     if A is String
 *       if A contains ".", "e", or "E"
 *         coerce to Double, apply operator
 *       otherwise
 *         coerce to a Long and apply operator
 *     if A is Byte,Short,Integer,Long,Float,Double
 *       retain type, apply operator
 *     if operator results in exception, error
 *     otherwise
 *       error
 * 
 * Applying "empty" operator - empty A
 *   if A is null
 *     return true
 *   if A is zero-length String
 *     return true
 *   if A is zero-length array
 *     return true
 *   if A is List and ((List) A).isEmpty()
 *     return true
 *   if A is Map and ((Map) A).isEmpty()
 *     return true
 *   if A is Collection an ((Collection) A).isEmpty()
 *     return true
 *   otherwise
 *     return false
 * 
 * Applying logical operators
 *   Binary operator - A {and,or} B
 *     coerce both A and B to Boolean, apply operator
 *   NOTE - operator stops as soon as expression can be determined, i.e.,
 *     A and B and C and D - if B is false, then only A and B is evaluated
 *   Unary not operator - not A
 *     coerce A to Boolean, apply operator
 * 
 * Applying relational operator
 *   A {<,>,<=,>=,lt,gt,lte,gte} B
 *     if A==B
 *       if operator is >= or <=
 *         return true
 *       otherwise
 *         return false
 *     if A or B is null
 *       return false
 *     if A or B is BigDecimal, coerce both A and B to BigDecimal and use the
 *      return value of <code>A.compareTo(B)</code>
 *     if A or B is Float or Double
 *       coerce both A and B to Double
 *       apply operator
 *     if A or B is BigInteger, coerce both A and B to BigInteger and use the
 *      return value of <code>A.compareTo(B)</code>
 *     if A or B is Byte,Short,Character,Integer,Long
 *       coerce both A and B to Long
 *       apply operator
 *     if A or B is String
 *       coerce both A and B to String, compare lexically
 *     if A is Comparable
 *       if A.compareTo (B) throws exception
 *         error
 *       otherwise
 *         use result of A.compareTo(B)
 *     if B is Comparable
 *       if B.compareTo (A) throws exception
 *         error
 *       otherwise
 *         use result of B.compareTo(A)
 *     otherwise
 *       error
 * 
 * Applying equality operator
 *   A {==,!=} B
 *     if A==B
 *       apply operator
 *     if A or B is null
 *       return false for ==, true for !=
 *     if A or B is BigDecimal, coerce both A and B to BigDecimal and then:
 *       if operator is == or eq, return <code>A.equals(B)</code>
 *       if operator is != or ne, return <code>!A.equals(B)</code>
 *     if A or B is Float or Double
 *       coerce both A and B to Double
 *       apply operator
 *     if A or B is BigInteger, coerce both A and B to BigInteger and then:
 *       if operator is == or eq, return <code>A.equals(B)</code>
 *       if operator is != or ne, return <code>!A.equals(B)</code>
 *     if A or B is Byte,Short,Character,Integer,Long
 *       coerce both A and B to Long
 *       apply operator
 *     if A or B is Boolean
 *       coerce both A and B to Boolean
 *       apply operator
 *     if A or B is String
 *       coerce both A and B to String, compare lexically
 *     otherwise
 *       if an error occurs while calling A.equals(B)
 *         error
 *       apply operator to result of A.equals(B)
 * 
 * coercions
 * 
 *   coerce A to String
 *     A is String
 *       return A
 *     A is null
 *       return ""
 *     A.toString throws exception
 *       error
 *     otherwise
 *       return A.toString
 * 
 *   coerce A to Number type N
 *     A is null or ""
 *       return 0
 *     A is Character
 *       convert to short, apply following rules
 *     A is Boolean
 *       error
 *     A is Number type N
 *       return A
 *     A is Number, coerce quietly to type N using the following algorithm
 *         If N is BigInteger
 *             If A is BigDecimal, return <code>A.toBigInteger()</code>
 *             Otherwise, return <code>BigInteger.valueOf(A.longValue())</code>
 *        if N is BigDecimal
 *             If A is a BigInteger, return <code>new BigDecimal(A)</code>
 *             Otherwise, return <code>new BigDecimal(A.doubleValue())</code>
 *        If N is Byte, return <code>new Byte(A.byteValue())</code>
 *        If N is Short, return <code>new Short(A.shortValue())</code>
 *        If N is Integer, return <code>new Integer(A.integerValue())</code>
 *        If N is Long, return <code>new Long(A.longValue())</code>
 *        If N is Float, return <code>new Float(A.floatValue())</code>
 *        If N is Double, return <code>new Double(A.doubleValue())</code>
 *        otherwise ERROR
 *     A is String
 *       If N is BigDecimal then:
 *            If <code>new BigDecimal(A)</code> throws an exception then ERROR
 *            Otherwise, return <code>new BigDecimal(A)</code>
 *       If N is BigInteger then:
 *            If <code>new BigInteger(A)</code> throws an exception, then ERROR
 *            Otherwise, return <code>new BigInteger(A)</code>
 *       new <code>N.valueOf(A)</code> throws exception
 *         error
 *       return <code>N.valueOf(A)</code>
 *     otherwise
 *       error
 * 
 *   coerce A to Character should be
 *     A is null or ""
 *       return (char) 0
 *     A is Character
 *       return A
 *     A is Boolean
 *       error
 *     A is Number with less precision than short
 *       coerce quietly - return (char) A
 *     A is Number with greater precision than short
 *       coerce quietly - return (char) A
 *     A is String
 *       return A.charAt (0)
 *     otherwise
 *       error
 * 
 *   coerce A to Boolean
 *     A is null or ""
 *       return false
 *     A is Boolean
 *       return A
 *     A is String
 *       Boolean.valueOf(A) throws exception
 *         error
 *       return Boolean.valueOf(A)
 *     otherwise
 *       error
 * 
 *   coerce A to any other type T
 *     A is null
 *       return null
 *     A is assignable to T
 *       coerce quietly
 *     A is String
 *       T has no PropertyEditor
 *         if A is "", return null
 *         otherwise error
 *       T's PropertyEditor throws exception
 *         if A is "", return null
 *         otherwise error
 *       otherwise
 *         apply T's PropertyEditor
 *     otherwise
 *       error
 * </pre>
 * 
 * </ul>
 * 
 * @author PeterPan
 * 
 **/
public class Coercions {

	private static final Number ZERO = new Integer(0);
	private static Log log = LogFactory.getLog(Coercions.class);

	/**
	 * 强制转换给定值为特定类
	 * 
	 * @param pValue
	 * @param pClass
	 * @param pLogger
	 * @return
	 * @throws ELException
	 */
	public static Object coerce(Object pValue, Class pClass) throws ELException {
		if (pClass == String.class) {
			return coerceToString(pValue);
		} else if (isNumberClass(pClass)) {
			return coerceToPrimitiveNumber(pValue, pClass);
		} else if (pClass == Character.class || pClass == Character.TYPE) {
			return coerceToCharacter(pValue);
		} else if (pClass == Boolean.class || pClass == Boolean.TYPE) {
			return coerceToBoolean(pValue);
		} else {
			return coerceToObject(pValue, pClass);
		}
	}

	/**
	 * 判断给定class是否数字类型。<br>
	 * 
	 * @param pClass
	 * @return 返回true，如果class类型为Byte, Short, Integer, Long, Float, Double,
	 *         BigInteger, or BigDecimal
	 */
	static boolean isNumberClass(Class pClass) {
		return pClass == Byte.class || pClass == Byte.TYPE
				|| pClass == Short.class || pClass == Short.TYPE
				|| pClass == Integer.class || pClass == Integer.TYPE
				|| pClass == Long.class || pClass == Long.TYPE
				|| pClass == Float.class || pClass == Float.TYPE
				|| pClass == Double.class || pClass == Double.TYPE
				|| pClass == BigInteger.class || pClass == BigDecimal.class;
	}

	/**
	 * 强制转换指定值为字符串
	 * 
	 * @param pValue
	 * @param pLogger
	 * @return
	 * @throws ELException
	 */
	public static String coerceToString(Object pValue) throws ELException {
		if (pValue == null) {
			return "";
		} else if (pValue instanceof String) {
			return (String) pValue;
		} else {
			try {
				return pValue.toString();
			} catch (Exception exc) {
				if (log.isErrorEnabled()) {
					String message = MessageUtil.getMessageWithArgs(
							Constants.TOSTRING_EXCEPTION, pValue.getClass()
									.getName());
					log.error(message, exc);
					throw new ELException(exc);
				}
				return "";
			}
		}
	}

	/**
	 * 强制转换给定值为原始Number类型
	 * 
	 * @param pValue
	 * @param pClass
	 * @param pLogger
	 * @return
	 * @throws ELException
	 */
	public static Number coerceToPrimitiveNumber(Object pValue, Class pClass)
			throws ELException {
		if (pValue == null || "".equals(pValue)) {
			return coerceToPrimitiveNumber(ZERO, pClass);
		} else if (pValue instanceof Character) {
			char val = ((Character) pValue).charValue();
			return coerceToPrimitiveNumber(new Short((short) val), pClass);
		} else if (pValue instanceof Boolean) {
			if (log.isErrorEnabled()) {
				String message = MessageUtil.getMessageWithArgs(
						Constants.BOOLEAN_TO_NUMBER, pValue, pClass.getName());
				log.error(message);
				throw new ELException(message);
			}
			return coerceToPrimitiveNumber(ZERO, pClass);
		} else if (pValue.getClass() == pClass) {
			return (Number) pValue;
		} else if (pValue instanceof Number) {
			return coerceToPrimitiveNumber((Number) pValue, pClass);
		} else if (pValue instanceof String) {
			try {
				return coerceToPrimitiveNumber((String) pValue, pClass);
			} catch (Exception exc) {
				if (log.isErrorEnabled()) {
					String message = MessageUtil.getMessageWithArgs(
							Constants.STRING_TO_NUMBER_EXCEPTION,
							(String) pValue, pClass.getName());
					log.error(message);
					throw new ELException(message);
				}
				return coerceToPrimitiveNumber(ZERO, pClass);
			}
		} else {
			if (log.isErrorEnabled()) {
				String message = MessageUtil.getMessageWithArgs(
						Constants.COERCE_TO_NUMBER,
						pValue.getClass().getName(), pClass.getName());
				log.error(message);
				throw new ELException(message);
			}
			return coerceToPrimitiveNumber(0, pClass);
		}
	}

	/**
	 * 强制转换给定值为Integer型，如果强制转换失败，返回null。
	 * 
	 * @param pValue
	 * @param pLogger
	 * @return
	 * @throws ELException
	 */
	public static Integer coerceToInteger(Object pValue) throws ELException {
		if (pValue == null) {
			return null;
		} else if (pValue instanceof Character) {
			return PrimitiveObjects.getInteger((int) (((Character) pValue)
					.charValue()));
		} else if (pValue instanceof Boolean) {
			if (log.isWarnEnabled()) {
				log.warn(MessageUtil.getMessageWithArgs(
						Constants.BOOLEAN_TO_NUMBER, pValue, Integer.class
								.getName()));
			}
			return PrimitiveObjects.getInteger(((Boolean) pValue)
					.booleanValue() ? 1 : 0);
		} else if (pValue instanceof Integer) {
			return (Integer) pValue;
		} else if (pValue instanceof Number) {
			return PrimitiveObjects.getInteger(((Number) pValue).intValue());
		} else if (pValue instanceof String) {
			try {
				return Integer.valueOf((String) pValue);
			} catch (Exception exc) {
				if (log.isWarnEnabled()) {
					log.warn(MessageUtil.getMessageWithArgs(
							Constants.STRING_TO_NUMBER_EXCEPTION,
							(String) pValue, Integer.class.getName()));
				}
				return null;
			}
		} else {
			if (log.isWarnEnabled()) {
				log.warn(MessageUtil.getMessageWithArgs(
						Constants.COERCE_TO_NUMBER,
						pValue.getClass().getName(), Integer.class.getName()));
			}
			return null;
		}
	}

	/**
	 * 强制转换long型到Number类型
	 * 
	 * @param pValue
	 * @param pClass
	 *            -原始类型
	 * @return
	 * @throws ELException
	 */
	static Number coerceToPrimitiveNumber(long pValue, Class pClass)
			throws ELException {
		if (pClass == Byte.class || pClass == Byte.TYPE) {
			return PrimitiveObjects.getByte((byte) pValue);
		} else if (pClass == Short.class || pClass == Short.TYPE) {
			return PrimitiveObjects.getShort((short) pValue);
		} else if (pClass == Integer.class || pClass == Integer.TYPE) {
			return PrimitiveObjects.getInteger((int) pValue);
		} else if (pClass == Long.class || pClass == Long.TYPE) {
			return PrimitiveObjects.getLong(pValue);
		} else if (pClass == Float.class || pClass == Float.TYPE) {
			return PrimitiveObjects.getFloat((float) pValue);
		} else if (pClass == Double.class || pClass == Double.TYPE) {
			return PrimitiveObjects.getDouble((double) pValue);
		} else {
			return PrimitiveObjects.getInteger(0);
		}
	}

	/**
	 * 强制转换double型到原始Number类型
	 * 
	 * @param pValue
	 * @param pClass
	 *            -原始类型
	 * @return
	 * @throws ELException
	 */
	static Number coerceToPrimitiveNumber(double pValue, Class pClass)
			throws ELException {
		if (pClass == Byte.class || pClass == Byte.TYPE) {
			return PrimitiveObjects.getByte((byte) pValue);
		} else if (pClass == Short.class || pClass == Short.TYPE) {
			return PrimitiveObjects.getShort((short) pValue);
		} else if (pClass == Integer.class || pClass == Integer.TYPE) {
			return PrimitiveObjects.getInteger((int) pValue);
		} else if (pClass == Long.class || pClass == Long.TYPE) {
			return PrimitiveObjects.getLong((long) pValue);
		} else if (pClass == Float.class || pClass == Float.TYPE) {
			return PrimitiveObjects.getFloat((float) pValue);
		} else if (pClass == Double.class || pClass == Double.TYPE) {
			return PrimitiveObjects.getDouble(pValue);
		} else {
			return PrimitiveObjects.getInteger(0);
		}
	}

	/**
	 * 强制转换Number型到原始Number类型
	 * 
	 * @param pValue
	 * @param pClass
	 *            -原始类型
	 * @return
	 * @throws ELException
	 */
	static Number coerceToPrimitiveNumber(Number pValue, Class pClass)
			throws ELException {
		if (pClass == Byte.class || pClass == Byte.TYPE) {
			return PrimitiveObjects.getByte(pValue.byteValue());
		} else if (pClass == Short.class || pClass == Short.TYPE) {
			return PrimitiveObjects.getShort(pValue.shortValue());
		} else if (pClass == Integer.class || pClass == Integer.TYPE) {
			return PrimitiveObjects.getInteger(pValue.intValue());
		} else if (pClass == Long.class || pClass == Long.TYPE) {
			return PrimitiveObjects.getLong(pValue.longValue());
		} else if (pClass == Float.class || pClass == Float.TYPE) {
			return PrimitiveObjects.getFloat(pValue.floatValue());
		} else if (pClass == Double.class || pClass == Double.TYPE) {
			return PrimitiveObjects.getDouble(pValue.doubleValue());
		} else if (pClass == BigInteger.class) {
			if (pValue instanceof BigDecimal)
				return ((BigDecimal) pValue).toBigInteger();
			else
				return BigInteger.valueOf(pValue.longValue());
		} else if (pClass == BigDecimal.class) {
			if (pValue instanceof BigInteger)
				return new BigDecimal((BigInteger) pValue);
			else
				return new BigDecimal(pValue.doubleValue());
		} else {
			return PrimitiveObjects.getInteger(0);
		}
	}

	/**
	 * 强制转换String型到原始Number类型
	 * 
	 * @param pValue
	 * @param pClass
	 *            -原始类型
	 * @return
	 * @throws ELException
	 */
	static Number coerceToPrimitiveNumber(String pValue, Class pClass)
			throws ELException {
		if (pClass == Byte.class || pClass == Byte.TYPE) {
			return Byte.valueOf(pValue);
		} else if (pClass == Short.class || pClass == Short.TYPE) {
			return Short.valueOf(pValue);
		} else if (pClass == Integer.class || pClass == Integer.TYPE) {
			return Integer.valueOf(pValue);
		} else if (pClass == Long.class || pClass == Long.TYPE) {
			return Long.valueOf(pValue);
		} else if (pClass == Float.class || pClass == Float.TYPE) {
			return Float.valueOf(pValue);
		} else if (pClass == Double.class || pClass == Double.TYPE) {
			return Double.valueOf(pValue);
		} else if (pClass == BigInteger.class) {
			return new BigInteger(pValue);
		} else if (pClass == BigDecimal.class) {
			return new BigDecimal(pValue);
		} else {
			return PrimitiveObjects.getInteger(0);
		}
	}

	/**
	 * 强制转换一个值到字符型
	 * 
	 * @param pValue
	 * @param pLogger
	 * @return
	 * @throws ELException
	 */
	public static Character coerceToCharacter(Object pValue) throws ELException {
		if (pValue == null || "".equals(pValue)) {
			return PrimitiveObjects.getCharacter((char) 0);
		} else if (pValue instanceof Character) {
			return (Character) pValue;
		} else if (pValue instanceof Boolean) {
			if (log.isErrorEnabled()) {
				String message = MessageUtil.getMessageWithArgs(
						Constants.BOOLEAN_TO_CHARACTER, pValue);
				log.error(message);
				throw new ELException(message);
			}
			return PrimitiveObjects.getCharacter((char) 0);
		} else if (pValue instanceof Number) {
			return PrimitiveObjects.getCharacter((char) ((Number) pValue)
					.shortValue());
		} else if (pValue instanceof String) {
			String str = (String) pValue;
			return PrimitiveObjects.getCharacter(str.charAt(0));
		} else {
			if (log.isErrorEnabled()) {
				String message = MessageUtil.getMessageWithArgs(
						Constants.COERCE_TO_CHARACTER, pValue.getClass()
								.getName());
				log.error(message);
				throw new ELException(message);
			}
			return PrimitiveObjects.getCharacter((char) 0);
		}
	}

	/**
	 * 强制转换一个值到布尔型
	 * 
	 * @param pValue
	 * @param pLogger
	 * @return
	 * @throws ELException
	 */
	public static Boolean coerceToBoolean(Object pValue) throws ELException {
		if (pValue == null || "".equals(pValue)) {
			return Boolean.FALSE;
		} else if (pValue instanceof Boolean) {
			return (Boolean) pValue;
		} else if (pValue instanceof String) {
			String str = (String) pValue;
			try {
				return Boolean.valueOf(str);
			} catch (Exception exc) {
				if (log.isErrorEnabled()) {
					String message = MessageUtil.getMessageWithArgs(
							Constants.STRING_TO_BOOLEAN, (String) pValue);
					log.error(message, exc);
					throw new ELException(message, exc);
				}
				return Boolean.FALSE;
			}
		} else {
			if (log.isErrorEnabled()) {
				String message = MessageUtil.getMessageWithArgs(
						Constants.COERCE_TO_BOOLEAN, pValue.getClass()
								.getName());
				log.error(message);
				throw new ELException(message);
			}
			return Boolean.TRUE;
		}
	}

	/**
	 * 强制转换一个值到指定的类型，该指定类型不在上面的用例中包括的类型。
	 * 
	 * @param pValue
	 * @param pClass
	 * @param pLogger
	 * @return
	 * @throws ELException
	 */
	public static Object coerceToObject(Object pValue, Class pClass)
			throws ELException {
		if (pValue == null) {
			return null;
		} else if (pClass.isAssignableFrom(pValue.getClass())) {
			return pValue;
		} else if (pValue instanceof String) {
			String str = (String) pValue;
			PropertyEditor pe = PropertyEditorManager.findEditor(pClass);
			if (pe == null) {
				if ("".equals(str)) {
					return null;
				} else {
					if (log.isErrorEnabled()) {
						String message = MessageUtil.getMessageWithArgs(
								Constants.NO_PROPERTY_EDITOR, str, pClass
										.getName());
						log.error(message);
						throw new ELException(message);
					}
					return null;
				}
			}
			try {
				pe.setAsText(str);
				return pe.getValue();
			} catch (IllegalArgumentException exc) {
				if ("".equals(str)) {
					return null;
				} else {
					if (log.isErrorEnabled()) {
						String message = MessageUtil.getMessageWithArgs(
								Constants.PROPERTY_EDITOR_ERROR, pValue, pClass
										.getName());
						log.error(message, exc);
						throw new ELException(message, exc);
					}
					return null;
				}
			}
		} else {
			if (log.isErrorEnabled()) {
				String message = MessageUtil.getMessageWithArgs(
						Constants.COERCE_TO_OBJECT,
						pValue.getClass().getName(), pClass.getName());
				log.error(message);
				throw new ELException(message);
			}
			return null;
		}
	}

	/**
	 * 应用算术运算符，按运算符判断，执行相应的转换方法
	 * 
	 * @param pLeft
	 * @param pRight
	 * @param pOperator
	 * @param pLogger
	 * @return
	 * @throws ELException
	 */
	public static Object applyArithmeticOperator(Object pLeft, Object pRight,
			ArithmeticOperator pOperator) throws ELException {
		if (pLeft == null && pRight == null) {
			if (log.isWarnEnabled()) {
				log
						.warn(MessageUtil.getMessageWithArgs(
								Constants.ARITH_OP_NULL, pOperator
										.getOperatorSymbol()));
			}
			return PrimitiveObjects.getInteger(0);
		}

		else if (isBigDecimal(pLeft) || isBigDecimal(pRight)) {
			BigDecimal left = (BigDecimal) coerceToPrimitiveNumber(pLeft,
					BigDecimal.class);
			BigDecimal right = (BigDecimal) coerceToPrimitiveNumber(pRight,
					BigDecimal.class);
			return pOperator.apply(left, right);
		}

		else if (isFloatingPointType(pLeft) || isFloatingPointType(pRight)
				|| isFloatingPointString(pLeft)
				|| isFloatingPointString(pRight)) {
			if (isBigInteger(pLeft) || isBigInteger(pRight)) {
				BigDecimal left = (BigDecimal) coerceToPrimitiveNumber(pLeft,
						BigDecimal.class);
				BigDecimal right = (BigDecimal) coerceToPrimitiveNumber(pRight,
						BigDecimal.class);
				return pOperator.apply(left, right);
			} else {
				double left = coerceToPrimitiveNumber(pLeft, Double.class)
						.doubleValue();
				double right = coerceToPrimitiveNumber(pRight, Double.class)
						.doubleValue();
				return PrimitiveObjects.getDouble(pOperator.apply(left, right));
			}
		}

		else if (isBigInteger(pLeft) || isBigInteger(pRight)) {
			BigInteger left = (BigInteger) coerceToPrimitiveNumber(pLeft,
					BigInteger.class);
			BigInteger right = (BigInteger) coerceToPrimitiveNumber(pRight,
					BigInteger.class);
			return pOperator.apply(left, right);
		}

		else {
			long left = coerceToPrimitiveNumber(pLeft, Long.class).longValue();
			long right = coerceToPrimitiveNumber(pRight, Long.class)
					.longValue();
			return PrimitiveObjects.getLong(pOperator.apply(left, right));
		}
	}

	/**
	 * 应用关系运算符，根据运算符判断，执行相应的转换方法
	 * 
	 * @param pLeft
	 * @param pRight
	 * @param pOperator
	 * @param pLogger
	 * @return
	 * @throws ELException
	 */
	public static Object applyRelationalOperator(Object pLeft, Object pRight,
			RelationalOperator pOperator) throws ELException {
		if (isBigDecimal(pLeft) || isBigDecimal(pRight)) {
			BigDecimal left = (BigDecimal) coerceToPrimitiveNumber(pLeft,
					BigDecimal.class);
			BigDecimal right = (BigDecimal) coerceToPrimitiveNumber(pRight,
					BigDecimal.class);
			return PrimitiveObjects.getBoolean(pOperator.apply(left, right));
		}

		else if (isFloatingPointType(pLeft) || isFloatingPointType(pRight)) {
			double left = coerceToPrimitiveNumber(pLeft, Double.class)
					.doubleValue();
			double right = coerceToPrimitiveNumber(pRight, Double.class)
					.doubleValue();
			return PrimitiveObjects.getBoolean(pOperator.apply(left, right));
		}

		else if (isBigInteger(pLeft) || isBigInteger(pRight)) {
			BigInteger left = (BigInteger) coerceToPrimitiveNumber(pLeft,
					BigInteger.class);
			BigInteger right = (BigInteger) coerceToPrimitiveNumber(pRight,
					BigInteger.class);
			return PrimitiveObjects.getBoolean(pOperator.apply(left, right));
		}

		else if (isIntegerType(pLeft) || isIntegerType(pRight)) {
			long left = coerceToPrimitiveNumber(pLeft, Long.class).longValue();
			long right = coerceToPrimitiveNumber(pRight, Long.class)
					.longValue();
			return PrimitiveObjects.getBoolean(pOperator.apply(left, right));
		}

		else if (pLeft instanceof String || pRight instanceof String) {
			String left = coerceToString(pLeft);
			String right = coerceToString(pRight);
			return PrimitiveObjects.getBoolean(pOperator.apply(left, right));
		}

		else if (pLeft instanceof Comparable) {
			try {
				int result = ((Comparable) pLeft).compareTo(pRight);
				return PrimitiveObjects.getBoolean(pOperator.apply(result,
						-result));
			} catch (Exception exc) {
				if (log.isErrorEnabled()) {
					String message = MessageUtil.getMessageWithArgs(
							Constants.COMPARABLE_ERROR, pLeft.getClass()
									.getName(), (pRight == null) ? "null"
									: pRight.getClass().getName(), pOperator
									.getOperatorSymbol());
					log.error(message, exc);
					throw new ELException(message, exc);
				}
				return Boolean.FALSE;
			}
		}

		else if (pRight instanceof Comparable) {
			try {
				int result = ((Comparable) pRight).compareTo(pLeft);
				return PrimitiveObjects.getBoolean(pOperator.apply(-result,
						result));
			} catch (Exception exc) {
				if (log.isErrorEnabled()) {
					String message = MessageUtil.getMessageWithArgs(
							Constants.COMPARABLE_ERROR, pRight.getClass()
									.getName(), (pLeft == null) ? "null"
									: pLeft.getClass().getName(), pOperator
									.getOperatorSymbol());
					log.error(message, exc);
					throw new ELException(message, exc);
				}
				return Boolean.FALSE;
			}
		}

		else {
			if (log.isErrorEnabled()) {
				String message = MessageUtil.getMessageWithArgs(
						Constants.ARITH_OP_BAD_TYPE, pOperator
								.getOperatorSymbol(), pLeft.getClass()
								.getName(), pRight.getClass().getName());
				log.error(message);
				throw new ELException(message);
			}
			return Boolean.FALSE;
		}
	}

	/**
	 * 应用等号运算符，根据运算符判断，执行相应的转换方法
	 * 
	 * @param pLeft
	 * @param pRight
	 * @param pOperator
	 * @param pLogger
	 * @return
	 * @throws ELException
	 */
	public static Object applyEqualityOperator(Object pLeft, Object pRight,
			EqualityOperator pOperator) throws ELException {
		if (pLeft == pRight) {
			return PrimitiveObjects.getBoolean(pOperator.apply(true));
		}

		else if (pLeft == null || pRight == null) {
			return PrimitiveObjects.getBoolean(pOperator.apply(false));
		}

		else if (isBigDecimal(pLeft) || isBigDecimal(pRight)) {
			BigDecimal left = (BigDecimal) coerceToPrimitiveNumber(pLeft,
					BigDecimal.class);
			BigDecimal right = (BigDecimal) coerceToPrimitiveNumber(pRight,
					BigDecimal.class);
			return PrimitiveObjects.getBoolean(pOperator.apply(left
					.equals(right)));
		}

		else if (isFloatingPointType(pLeft) || isFloatingPointType(pRight)) {
			double left = coerceToPrimitiveNumber(pLeft, Double.class)
					.doubleValue();
			double right = coerceToPrimitiveNumber(pRight, Double.class)
					.doubleValue();
			return PrimitiveObjects.getBoolean(pOperator.apply(left == right));
		}

		else if (isBigInteger(pLeft) || isBigInteger(pRight)) {
			BigInteger left = (BigInteger) coerceToPrimitiveNumber(pLeft,
					BigInteger.class);
			BigInteger right = (BigInteger) coerceToPrimitiveNumber(pRight,
					BigInteger.class);
			return PrimitiveObjects.getBoolean(pOperator.apply(left
					.equals(right)));
		}

		else if (isIntegerType(pLeft) || isIntegerType(pRight)) {
			long left = coerceToPrimitiveNumber(pLeft, Long.class).longValue();
			long right = coerceToPrimitiveNumber(pRight, Long.class)
					.longValue();
			return PrimitiveObjects.getBoolean(pOperator.apply(left == right));
		}

		else if (pLeft instanceof Boolean || pRight instanceof Boolean) {
			boolean left = coerceToBoolean(pLeft).booleanValue();
			boolean right = coerceToBoolean(pRight).booleanValue();
			return PrimitiveObjects.getBoolean(pOperator.apply(left == right));
		}

		else if (pLeft instanceof String || pRight instanceof String) {
			String left = coerceToString(pLeft);
			String right = coerceToString(pRight);
			return PrimitiveObjects.getBoolean(pOperator.apply(left
					.equals(right)));
		}

		else {
			try {
				return PrimitiveObjects.getBoolean(pOperator.apply(pLeft
						.equals(pRight)));
			} catch (Exception exc) {
				if (log.isErrorEnabled()) {
					String message = MessageUtil.getMessageWithArgs(
							Constants.ERROR_IN_EQUALS, pLeft.getClass()
									.getName(), pRight.getClass().getName(),
							pOperator.getOperatorSymbol());
					log.error(message, exc);
					throw new ELException(message, exc);
				}
				return Boolean.FALSE;
			}
		}
	}

	/**
	 * 判断给定值是否是浮点类型
	 * 
	 * @param pObject
	 * @return
	 */
	public static boolean isFloatingPointType(Object pObject) {
		return pObject != null && isFloatingPointType(pObject.getClass());
	}

	/**
	 * 判断给定类型是否是浮点类型
	 * 
	 * @param pClass
	 * @return
	 */
	public static boolean isFloatingPointType(Class pClass) {
		return pClass == Float.class || pClass == Float.TYPE
				|| pClass == Double.class || pClass == Double.TYPE;
	}

	/**
	 * 判断给定字符串是否包含浮点数。<br>
	 * 例如，包含".", "e", 或者 "E"。
	 * 
	 * @param pObject
	 * @return
	 */
	public static boolean isFloatingPointString(Object pObject) {
		if (pObject instanceof String) {
			String str = (String) pObject;
			int len = str.length();
			for (int i = 0; i < len; i++) {
				char ch = str.charAt(i);
				if (ch == '.' || ch == 'e' || ch == 'E') {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * 判断给定值是否是整型
	 * 
	 * @param pObject
	 * @return
	 */
	public static boolean isIntegerType(Object pObject) {
		return pObject != null && isIntegerType(pObject.getClass());
	}

	/**
	 * 判断给定类型是否是整型
	 * 
	 * @param pClass
	 * @return
	 */
	public static boolean isIntegerType(Class pClass) {
		return pClass == Byte.class || pClass == Byte.TYPE
				|| pClass == Short.class || pClass == Short.TYPE
				|| pClass == Character.class || pClass == Character.TYPE
				|| pClass == Integer.class || pClass == Integer.TYPE
				|| pClass == Long.class || pClass == Long.TYPE;
	}

	/**
	 * 判断给定对象是否是BigInteger型
	 * 
	 * @param pObject
	 * @return
	 */
	public static boolean isBigInteger(Object pObject) {
		return pObject != null && pObject instanceof BigInteger;
	}

	/**
	 * 判断给定对象是否是BigDecimal型
	 * 
	 * @param pObject
	 * @return
	 */
	public static boolean isBigDecimal(Object pObject) {
		return pObject != null && pObject instanceof BigDecimal;
	}
}
