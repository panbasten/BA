package com.flywet.platform.bi.component.utils;

import java.io.StringReader;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.flywet.platform.bi.core.exception.BIPageException;
import com.flywet.platform.bi.el.expression.Expression;
import com.flywet.platform.bi.el.expression.ExpressionString;
import com.flywet.platform.bi.el.parser.ELParser;

public class FLYExpressionResolver {

	private final static Logger log = Logger
			.getLogger(FLYExpressionResolver.class);

	/**
	 * 解析嵌套的表达式，最终返回字符串
	 * 
	 * @param string
	 *            目标替换的字符串
	 * @param attrs
	 *            参数
	 * @return
	 * @throws BIPageException
	 */
	public static String evaluate(String string, FLYVariableResolver attrs)
			throws BIPageException {
		StringTokenizer tokens = new StringTokenizer(string, "${}", true);
		StringBuilder builder = new StringBuilder(string.length());
		while (tokens.hasMoreTokens()) {
			String tok = tokens.nextToken();

			if (!"$".equals(tok) || !tokens.hasMoreTokens()) {
				builder.append(tok);
				continue;
			}

			// 1.如果是$
			String nextTok = tokens.nextToken();

			// 2.如果下一个也是$，将其进栈，待下次循环处理，直到最底层进行后续处理
			while (nextTok.equals("$") && tokens.hasMoreTokens()) {
				builder.append(tok);
				nextTok = tokens.nextToken();
			}

			// 3.如果下一个是{
			if ("{".equals(nextTok)) {
				String attr = tokens.nextToken();

				builder.append(evaluateString("${" + attr + "}", attrs));

				// 丢弃后面一个}
				tokens.nextToken();

			}
			// 4.如果下一个是其他，则直接当做非关键字显示
			else {
				builder.append(tok).append(nextTok);
			}
		}

		return builder.toString();
	}

	/**
	 * 解析单个的表达式，最终返回字符串
	 * 
	 * @param input
	 * @param resolver
	 * @return
	 */
	private static String evaluateString(String input,
			FLYVariableResolver resolver) {
		try {
			if (input == null || input == "") {
				return input;
			}
			StringReader rdr = new StringReader(input);
			ELParser parser = new ELParser(rdr);
			Object result = parser.ExpressionString();
			if (result instanceof String) {
				return (String) result;
			} else if (result instanceof Expression) {
				Expression expr = (Expression) result;
				result = expr.evaluate(resolver, FLYFunctionMapper.singleton);
				return result == null ? "" : result.toString();
			} else if (result instanceof ExpressionString) {
				ExpressionString expr = (ExpressionString) result;
				result = expr.evaluate(resolver, FLYFunctionMapper.singleton);
				return result == null ? "" : result.toString();
			} else {
				throw new RuntimeException(
						"无效的解析类型：非 String、Expression、ExpressionString");
			}
		} catch (Exception pe) {
			log.error("解析EL表达式出现异常:[" + input + "]");
			throw new RuntimeException(pe.getMessage(), pe);
		}
	}

	/**
	 * 解析单个的表达式，最终返回一个对象
	 * 
	 * @param input
	 * @param resolver
	 * @return
	 */
	public static Object evaluateObject(String input,
			FLYVariableResolver resolver) {
		try {
			if (input == null || input == "") {
				return input;
			}
			StringReader rdr = new StringReader(input);
			ELParser parser = new ELParser(rdr);
			Object result = parser.ExpressionString();
			if (result instanceof String) {
				return (String) result;
			} else if (result instanceof Expression) {
				Expression expr = (Expression) result;
				result = expr.evaluate(resolver, FLYFunctionMapper.singleton);
				return result;
			} else if (result instanceof ExpressionString) {
				ExpressionString expr = (ExpressionString) result;
				result = expr.evaluate(resolver, FLYFunctionMapper.singleton);
				return result;
			} else {
				throw new RuntimeException(
						"无效的解析类型：非 String、Expression、ExpressionString");
			}
		} catch (Exception pe) {
			log.error("解析EL表达式出现异常:[" + input + "]");
			throw new RuntimeException(pe.getMessage(), pe);
		}
	}

}
