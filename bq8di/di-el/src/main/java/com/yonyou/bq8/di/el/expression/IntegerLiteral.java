package com.yonyou.bq8.di.el.expression;

/**
 * 该类表示一个整数常量值的表达式，该值内部存储为long
 * 
 * @author PeterPan
 * 
 */
public class IntegerLiteral extends Literal {
	public IntegerLiteral(String pToken) {
		super(getValueFromToken(pToken));
	}

	/**
	 * 
	 * Parses the given token into the literal value
	 **/
	static Object getValueFromToken(String pToken) {
		return new Long(pToken);
	}

	@Override
	public String getExpressionString() {
		return getValue().toString();
	}

}
