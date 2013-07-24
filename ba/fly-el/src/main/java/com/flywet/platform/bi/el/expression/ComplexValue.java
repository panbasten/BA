package com.flywet.platform.bi.el.expression;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import com.flywet.platform.bi.el.expression.suffix.ValueSuffix;

/**
 * 
 * <p>
 * Represents a dynamic value, which consists of a prefix and an optional set of
 * ValueSuffix elements. A prefix is something like an identifier, and a suffix
 * is something like a "property of" or "indexed element of" operator.
 * 
 * 该类表示一个动态的值，它包含一个前缀和一个ValueSuffix元素集合。
 * 前缀是类似标识符的对象，后缀是一个类似“...的属性”或者“可序列的对象”的运算符。
 * 
 * @author PeterPan
 * 
 **/

public class ComplexValue extends Expression {

	Expression mPrefix;

	public Expression getPrefix() {
		return mPrefix;
	}

	public void setPrefix(Expression pPrefix) {
		mPrefix = pPrefix;
	}

	List mSuffixes;

	public List getSuffixes() {
		return mSuffixes;
	}

	public void setSuffixes(List pSuffixes) {
		mSuffixes = pSuffixes;
	}

	public ComplexValue(Expression pPrefix, List pSuffixes) {
		mPrefix = pPrefix;
		mSuffixes = pSuffixes;
	}

	@Override
	public String getExpressionString() {
		StringBuffer buf = new StringBuffer();
		buf.append(mPrefix.getExpressionString());

		for (int i = 0; mSuffixes != null && i < mSuffixes.size(); i++) {
			ValueSuffix suffix = (ValueSuffix) mSuffixes.get(i);
			buf.append(suffix.getExpressionString());
		}

		return buf.toString();
	}

	@Override
	public Object evaluate(VariableResolver pResolver, FunctionMapper functions)
			throws ELException {
		Object ret = mPrefix.evaluate(pResolver, functions);

		// Apply the suffixes
		for (int i = 0; mSuffixes != null && i < mSuffixes.size(); i++) {
			ValueSuffix suffix = (ValueSuffix) mSuffixes.get(i);
			ret = suffix.evaluate(ret, pResolver, functions);
		}

		return ret;
	}

	@Override
	public Expression bindFunctions(final FunctionMapper functions)
			throws ELException {
		final List suffixes = new ArrayList(mSuffixes.size());
		for (int i = 0; mSuffixes != null && i < mSuffixes.size(); i++) {
			ValueSuffix suffix = (ValueSuffix) mSuffixes.get(i);
			suffixes.add(suffix.bindFunctions(functions));
		}
		return new ComplexValue(mPrefix.bindFunctions(functions), suffixes);
	}

}
