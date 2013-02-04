package com.yonyou.bq8.di.el.expression;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import com.yonyou.bq8.di.el.Coercions;

/**
 * 
 * <p>
 * Represents a conditional expression. I've decided not to produce an abstract
 * base "TernaryOperatorExpression" class since (a) future ternary operators are
 * unlikely and (b) it's not clear that there would be a meaningful way to
 * abstract them. (For instance, would they all be right- associative? Would
 * they all have two fixed operator symbols?)
 * 
 * 该类表示一个条件表达式。<br>
 * 决定不创建一个三元运算符表达式的抽象类的原因：<br>
 * 1.将来不可能有三元表达式； 2.抽象他们的意义不清晰；
 * 
 * @author PeterPan
 **/

public class ConditionalExpression extends Expression {

	Expression mCondition;

	public Expression getCondition() {
		return mCondition;
	}

	public void setCondition(Expression pCondition) {
		mCondition = pCondition;
	}

	Expression mTrueBranch;

	public Expression getTrueBranch() {
		return mTrueBranch;
	}

	public void setTrueBranch(Expression pTrueBranch) {
		mTrueBranch = pTrueBranch;
	}

	Expression mFalseBranch;

	public Expression getFalseBranch() {
		return mFalseBranch;
	}

	public void setFalseBranch(Expression pFalseBranch) {
		mFalseBranch = pFalseBranch;
	}

	public ConditionalExpression(Expression pCondition, Expression pTrueBranch,
			Expression pFalseBranch) {
		mCondition = pCondition;
		mTrueBranch = pTrueBranch;
		mFalseBranch = pFalseBranch;
	}

	@Override
	public String getExpressionString() {
		return "( " + mCondition.getExpressionString() + " ? "
				+ mTrueBranch.getExpressionString() + " : "
				+ mFalseBranch.getExpressionString() + " )";
	}

	@Override
	public Object evaluate(VariableResolver vr, FunctionMapper f)
			throws ELException {
		// first, evaluate the condition (and coerce the result to a boolean
		// value)
		boolean condition = Coercions.coerceToBoolean(
				mCondition.evaluate(vr, f)).booleanValue();

		// then, use this boolean to branch appropriately
		if (condition)
			return mTrueBranch.evaluate(vr, f);
		else
			return mFalseBranch.evaluate(vr, f);
	}

	@Override
	public Expression bindFunctions(final FunctionMapper functions)
			throws ELException {
		return new ConditionalExpression(mCondition.bindFunctions(functions),
				mTrueBranch.bindFunctions(functions), mFalseBranch
						.bindFunctions(functions));
	}

}
