package com.plywet.platform.bi.el.expression;

/**
 * 该类表示一个字符串常量值
 * 
 * @author PeterPan
 * 
 */
public class StringLiteral extends Literal {

	StringLiteral(Object pValue) {
		super(pValue);
	}

	/**
	 * 由给定的令牌解析返回一个StringLiteral（被单引号或者双引号扩住的部分）
	 * 
	 * @param pToken
	 * @return
	 */
	public static StringLiteral fromToken(String pToken) {
		return new StringLiteral(getValueFromToken(pToken));
	}

	/**
	 * 由给定的字符串解析返回一个StringLiteral
	 * 
	 * @param pValue
	 * @return
	 */
	public static StringLiteral fromLiteralValue(String pValue) {
		return new StringLiteral(pValue);
	}

	/**
	 * 解析一个给定的令牌为常量值
	 * 
	 * @param pToken
	 * @return
	 */
	public static String getValueFromToken(String pToken) {
		StringBuffer buf = new StringBuffer();
		int len = pToken.length() - 1;
		boolean escaping = false;
		for (int i = 1; i < len; i++) {
			char ch = pToken.charAt(i);
			if (escaping) {
				buf.append(ch);
				escaping = false;
			} else if (ch == '\\') {
				escaping = true;
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 转换一个指定值为字符串令牌，使用"(双引号)并且将需要转义的字符进行转义
	 * 
	 * @param pValue
	 * @return
	 */
	public static String toStringToken(String pValue) {
		// See if any escaping is needed
		if (pValue.indexOf('\"') < 0 && pValue.indexOf('\\') < 0) {
			return "\"" + pValue + "\"";
		}

		// Escaping is needed
		else {
			StringBuffer buf = new StringBuffer();
			buf.append('\"');
			int len = pValue.length();
			for (int i = 0; i < len; i++) {
				char ch = pValue.charAt(i);
				if (ch == '\\') {
					buf.append('\\');
					buf.append('\\');
				} else if (ch == '\"') {
					buf.append('\\');
					buf.append('\"');
				} else {
					buf.append(ch);
				}
			}
			buf.append('\"');
			return buf.toString();
		}
	}

	/**
	 * 转换一个指定值为标识令牌，如果不符合要求，则转换为一个字符串常量。
	 * 
	 * @param pValue
	 * @return
	 */
	public static String toIdentifierToken(String pValue) {
		// See if it's a valid java identifier
		if (isJavaIdentifier(pValue)) {
			return pValue;
		}

		// Return as a String literal
		else {
			return toStringToken(pValue);
		}
	}

	/**
	 * 判断给定值是否是一个合法的java标识
	 * 
	 * @param pValue
	 * @return
	 */
	static boolean isJavaIdentifier(String pValue) {
		int len = pValue.length();
		if (len == 0) {
			return false;
		} else {
			if (!Character.isJavaIdentifierStart(pValue.charAt(0))) {
				return false;
			} else {
				for (int i = 1; i < len; i++) {
					if (!Character.isJavaIdentifierPart(pValue.charAt(i))) {
						return false;
					}
				}
				return true;
			}
		}
	}

	@Override
	public String getExpressionString() {
		return toStringToken((String) getValue());
	}

}
