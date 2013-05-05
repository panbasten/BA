package com.plywet.platform.bi.el.expression;

/**
 * 该类表示一个空值常量表达式
 * 
 * @author PeterPan
 * 
 */
public class NullLiteral extends Literal {

	public static final NullLiteral SINGLETON = new NullLiteral();

	public NullLiteral() {
		super(null);
	}

	@Override
	public String getExpressionString() {
		return "null";
	}

}
