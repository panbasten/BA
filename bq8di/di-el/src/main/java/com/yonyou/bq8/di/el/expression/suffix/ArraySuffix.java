package com.yonyou.bq8.di.el.expression.suffix;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yonyou.bq8.di.el.BeanInfoIndexedProperty;
import com.yonyou.bq8.di.el.BeanInfoManager;
import com.yonyou.bq8.di.el.BeanInfoProperty;
import com.yonyou.bq8.di.el.Coercions;
import com.yonyou.bq8.di.el.Constants;
import com.yonyou.bq8.di.el.MessageUtil;
import com.yonyou.bq8.di.el.expression.Expression;

/**
 * 
 * <p>
 * Represents an operator that obtains a Map entry, an indexed value, a property
 * value, or an indexed property value of an object. The following are the rules
 * for evaluating this operator:<br>
 * 该类表示一个组合类操作符，可以从下面的对象中获得值：<br>
 * 从Map中获得entry，从集合中获得索引值，从对象中获得索引值。<br>
 * 以下是该操作符的规则：
 * 
 * <ul>
 * 
 * <pre>
 * Evaluating a[b] (assuming a.b == a["b"])
 *   a is null
 *     return null
 *   b is null
 *     return null
 *   a is Map
 *     !a.containsKey (b)
 *       return null
 *     a.get(b) == null
 *       return null
 *     otherwise
 *       return a.get(b)
 *   a is List or array
 *     coerce b to int (using coercion rules)
 *     coercion couldn't be performed
 *       error
 *     a.get(b) or Array.get(a, b) throws ArrayIndexOutOfBoundsException or IndexOutOfBoundsException
 *       return null
 *     a.get(b) or Array.get(a, b) throws other exception
 *       error
 *     return a.get(b) or Array.get(a, b)
 * 
 *   coerce b to String
 *   b is a readable property of a
 *     getter throws an exception
 *       error
 *     otherwise
 *       return result of getter call
 * 
 *   otherwise
 *     error
 * </pre>
 * 
 * </ul>
 * 
 * @author PeterPan
 * 
 **/

public class ArraySuffix extends ValueSuffix {

	private static Log log = LogFactory.getLog(ArraySuffix.class);

	static Object[] sNoArgs = new Object[0];

	Expression mIndex;

	public Expression getIndex() {
		return mIndex;
	}

	public void setIndex(Expression pIndex) {
		mIndex = pIndex;
	}

	public ArraySuffix(Expression pIndex) {
		mIndex = pIndex;
	}

	/**
	 * 在给定的上下文中计算索引的结果值
	 * 
	 * @param pResolver
	 * @param functions
	 * @return
	 * @throws ELException
	 */
	Object evaluateIndex(VariableResolver pResolver, FunctionMapper functions)
			throws ELException {
		return mIndex.evaluate(pResolver, functions);
	}

	/**
	 * 返回运算符标识
	 * 
	 * @return
	 */
	String getOperatorSymbol() {
		return "[]";
	}


	@Override
	public String getExpressionString() {
		return "[" + mIndex.getExpressionString() + "]";
	}

	@Override
	public Object evaluate(Object pValue, VariableResolver pResolver,
			FunctionMapper functions) throws ELException {
		Object indexVal;
		String indexStr;
		BeanInfoProperty property;
		BeanInfoIndexedProperty ixproperty;

		// Check for null value
		if (pValue == null) {
			if (log.isWarnEnabled()) {
				log.warn(MessageUtil.getMessageWithArgs(
						Constants.CANT_GET_INDEXED_VALUE_OF_NULL,
						getOperatorSymbol()));
				return null;
			}
		}

		// Evaluate the index
		else if ((indexVal = evaluateIndex(pResolver, functions)) == null) {
			if (log.isWarnEnabled()) {
				log.warn(MessageUtil.getMessageWithArgs(
						Constants.CANT_GET_NULL_INDEX, getOperatorSymbol()));
				return null;
			}
		}

		// See if it's a Map
		else if (pValue instanceof Map) {
			Map val = (Map) pValue;
			return val.get(indexVal);
		}

		// See if it's a List or array
		else if (pValue instanceof List || pValue.getClass().isArray()) {
			Integer indexObj = Coercions.coerceToInteger(indexVal);
			if (indexObj == null) {
				if (log.isErrorEnabled()) {
					String message = MessageUtil.getMessageWithArgs(
							Constants.BAD_INDEX_VALUE, getOperatorSymbol(),
							indexVal.getClass().getName());
					log.error(message);
					throw new ELException(message);
				}
				return null;
			} else if (pValue instanceof List) {
				try {
					return ((List) pValue).get(indexObj.intValue());
				} catch (ArrayIndexOutOfBoundsException aob) {
					if (log.isWarnEnabled()) {
						log.warn(MessageUtil.getMessageWithArgs(
								Constants.EXCEPTION_ACCESSING_LIST, indexObj),
								aob);
					}
					return null;
				} catch (IndexOutOfBoundsException iob) {
					if (log.isWarnEnabled()) {
						log.warn(MessageUtil.getMessageWithArgs(
								Constants.EXCEPTION_ACCESSING_LIST, indexObj),
								iob);
					}
					return null;
				} catch (Throwable t) {
					if (log.isErrorEnabled()) {
						String message = MessageUtil.getMessageWithArgs(
								Constants.EXCEPTION_ACCESSING_LIST, indexObj);
						log.error(message, t);
						throw new ELException(message, t);
					}
					return null;
				}
			} else {
				try {
					return Array.get(pValue, indexObj.intValue());
				} catch (ArrayIndexOutOfBoundsException aob) {
					if (log.isWarnEnabled()) {
						log.warn(MessageUtil.getMessageWithArgs(
								Constants.EXCEPTION_ACCESSING_ARRAY, indexObj),
								aob);
					}
					return null;
				} catch (IndexOutOfBoundsException iob) {
					if (log.isWarnEnabled()) {
						log.warn(MessageUtil.getMessageWithArgs(
								Constants.EXCEPTION_ACCESSING_ARRAY, indexObj),
								iob);
					}
					return null;
				} catch (Throwable t) {
					if (log.isErrorEnabled()) {
						String message = MessageUtil.getMessageWithArgs(
								Constants.EXCEPTION_ACCESSING_ARRAY, indexObj);
						log.error(message, t);
						throw new ELException(message, t);
					}
					return null;
				}
			}
		}

		// Coerce to a String for property access

		else if ((indexStr = Coercions.coerceToString(indexVal)) == null) {
			return null;
		}

		// Look for a JavaBean property
		else if ((property = BeanInfoManager.getBeanInfoProperty(pValue
				.getClass(), indexStr)) != null
				&& property.getReadMethod() != null) {
			try {
				return property.getReadMethod().invoke(pValue, sNoArgs);
			} catch (InvocationTargetException exc) {
				if (log.isErrorEnabled()) {
					String message = MessageUtil.getMessageWithArgs(
							Constants.ERROR_GETTING_PROPERTY, indexStr, pValue
									.getClass().getName());
					Throwable t = exc.getTargetException();
					log.warn(message, t);
					throw new ELException(message, t);
				}
				return null;
			} catch (Throwable t) {
				if (log.isErrorEnabled()) {
					String message = MessageUtil.getMessageWithArgs(
							Constants.ERROR_GETTING_PROPERTY, indexStr, pValue
									.getClass().getName());
					log.warn(message, t);
					throw new ELException(message, t);
				}
				return null;
			}
		} else {
			if (log.isErrorEnabled()) {
				String message = MessageUtil.getMessageWithArgs(
						Constants.CANT_FIND_INDEX, indexVal, pValue.getClass()
								.getName(), getOperatorSymbol());
				log.error(message);
				throw new ELException(message);
			}
			return null;
		}
		return null;
	}

	@Override
	public ValueSuffix bindFunctions(final FunctionMapper functions)
			throws ELException {
		return new ArraySuffix(mIndex.bindFunctions(functions));
	}
}
