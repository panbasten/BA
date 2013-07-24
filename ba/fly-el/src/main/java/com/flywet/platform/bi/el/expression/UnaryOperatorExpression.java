package com.flywet.platform.bi.el.expression;

import java.util.List;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import com.flywet.platform.bi.el.operator.UnaryOperator;

/**
 * 处理由一个或多个一元运算符组成的表达式的值
 * 
 * @author PeterPan
 * 
 */
public class UnaryOperatorExpression extends Expression {

	UnaryOperator mOperator;

	public UnaryOperator getOperator() {
		return mOperator;
	}

	public void setOperator(UnaryOperator pOperator) {
		mOperator = pOperator;
	}

	List mOperators;

	public List getOperators() {
		return mOperators;
	}

	public void setOperators(List pOperators) {
		mOperators = pOperators;
	}

	Expression mExpression;

	public Expression getExpression() {
		return mExpression;
	}

	public void setExpression(Expression pExpression) {
		mExpression = pExpression;
	}

	public UnaryOperatorExpression(UnaryOperator pOperator, List pOperators,
			Expression pExpression) {
		mOperator = pOperator;
		mOperators = pOperators;
		mExpression = pExpression;
	}

	@Override
	public String getExpressionString() {
		StringBuffer buf = new StringBuffer();
		buf.append("(");
		if (mOperator != null) {
			buf.append(mOperator.getOperatorSymbol());
			buf.append(" ");
		} else {
			for (int i = 0, size = mOperators.size(); i < size; i++) {
				UnaryOperator operator = (UnaryOperator) mOperators.get(i);
				buf.append(operator.getOperatorSymbol());
				buf.append(" ");
			}
		}
		buf.append(mExpression.getExpressionString());
		buf.append(")");
		return buf.toString();
	}

	@Override
	public Object evaluate(VariableResolver pResolver, FunctionMapper functions)
			throws ELException {
		Object value = mExpression.evaluate(pResolver, functions);
		if (mOperator != null) {
			value = mOperator.apply(value);
		} else {
			for (int i = mOperators.size() - 1; i >= 0; i--) {
				UnaryOperator operator = (UnaryOperator) mOperators.get(i);
				value = operator.apply(value);
			}
		}
		return value;
	}

	@Override
	public Expression bindFunctions(final FunctionMapper functions)
			throws ELException {
		return new UnaryOperatorExpression(mOperator, mOperators, mExpression
				.bindFunctions(functions));
	}

}
