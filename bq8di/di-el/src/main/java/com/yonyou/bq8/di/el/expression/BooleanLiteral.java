package com.yonyou.bq8.di.el.expression;

/**
 * 该类表示一个布尔常量表达式
 * 
 * @author PeterPan
 * 
 */
public class BooleanLiteral extends Literal {

	public static final BooleanLiteral TRUE = new BooleanLiteral("true");
	public static final BooleanLiteral FALSE = new BooleanLiteral("false");

	public BooleanLiteral(String pToken) {
		super(getValueFromToken(pToken));
	}

	/**
	 * 从令牌中解析值
	 */
	static Object getValueFromToken(String pToken) {
		return ("true".equals(pToken)) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public String getExpressionString() {
		return (getValue() == Boolean.TRUE) ? "true" : "false";
	}

}
