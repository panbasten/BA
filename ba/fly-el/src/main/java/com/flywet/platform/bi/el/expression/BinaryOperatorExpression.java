package com.flywet.platform.bi.el.expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import com.flywet.platform.bi.el.Coercions;
import com.flywet.platform.bi.el.operator.BinaryOperator;


/**
 * 该类表示一个二元运算符的表达式
 * 
 * @author PeterPan
 * 
 */
public class BinaryOperatorExpression extends Expression {

	Expression mExpression;

	public Expression getExpression() {
		return mExpression;
	}

	public void setExpression(Expression pExpression) {
		mExpression = pExpression;
	}

	List mOperators;

	public List getOperators() {
		return mOperators;
	}

	public void setOperators(List pOperators) {
		mOperators = pOperators;
	}

	List mExpressions;

	public List getExpressions() {
		return mExpressions;
	}

	public void setExpressions(List pExpressions) {
		mExpressions = pExpressions;
	}

	public BinaryOperatorExpression(Expression pExpression, List pOperators,
			List pExpressions) {
		mExpression = pExpression;
		mOperators = pOperators;
		mExpressions = pExpressions;
	}

	@Override
	public String getExpressionString() {
		StringBuffer buf = new StringBuffer();
		buf.append("(");
		buf.append(mExpression.getExpressionString());
		for (int i = 0, size = mOperators.size(); i < size; i++) {
			BinaryOperator operator = (BinaryOperator) mOperators.get(i);
			Expression expression = (Expression) mExpressions.get(i);
			buf.append(" ");
			buf.append(operator.getOperatorSymbol());
			buf.append(" ");
			buf.append(expression.getExpressionString());
		}
		buf.append(")");

		return buf.toString();
	}

	@Override
	public Object evaluate(VariableResolver pResolver, FunctionMapper functions)
			throws ELException {
		Object value = mExpression.evaluate(pResolver, functions);
		for (int i = 0, size = mOperators.size(); i < size; i++) {
			BinaryOperator operator = (BinaryOperator) mOperators.get(i);

			// For the And/Or operators, we need to coerce to a boolean
			// before testing if we shouldEvaluate
			if (operator.shouldCoerceToBoolean()) {
				value = Coercions.coerceToBoolean(value);
			}

			if (operator.shouldEvaluate(value)) {
				Expression expression = (Expression) mExpressions.get(i);
				Object nextValue = expression.evaluate(pResolver, functions);

				value = operator.apply(value, nextValue);
			}
		}
		return value;
	}

	@Override
	public Expression bindFunctions(final FunctionMapper functions)
			throws ELException {
		final List args = new ArrayList(mExpressions.size());
		for (Iterator argIter = mExpressions.iterator(); argIter.hasNext();) {
			args.add(((Expression) argIter.next()).bindFunctions(functions));
		}
		// it would be nice if we knew for sure that the operators list
		// was immutable, but we'll just assume so for now.
		return new BinaryOperatorExpression(mExpression
				.bindFunctions(functions), mOperators, args);
	}

}
