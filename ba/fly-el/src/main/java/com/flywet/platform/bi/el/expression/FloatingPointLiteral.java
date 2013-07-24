package com.flywet.platform.bi.el.expression;

/**
 * 
 * <p>
 * An expression representing a floating point literal value. The value is
 * stored internally as a double.
 * 
 * 该类表示一个浮点数常量值的表达式，该值内部存储为double
 * 
 * @author PeterPan
 * 
 **/

public class FloatingPointLiteral extends Literal {
	public FloatingPointLiteral(String pToken) {
		super(getValueFromToken(pToken));
	}

	/**
	 * 从令牌中解析值
	 */
	static Object getValueFromToken(String pToken) {
		return new Double(pToken);
	}

	@Override
	public String getExpressionString() {
		return getValue().toString();
	}

}
