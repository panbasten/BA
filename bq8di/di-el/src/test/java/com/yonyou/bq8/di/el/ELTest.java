package com.yonyou.bq8.di.el;

import java.io.StringReader;

import javax.servlet.jsp.el.ELException;

import junit.framework.TestCase;

import com.yonyou.bq8.di.el.expression.Expression;
import com.yonyou.bq8.di.el.expression.ExpressionString;
import com.yonyou.bq8.di.el.parser.ELParser;
import com.yonyou.bq8.di.el.parser.ParseException;

public class ELTest extends TestCase {

	private MockVariableResolver resolver;
	private MockFunctionMapper mapper;

	protected void setUp() {
		this.resolver = new MockVariableResolver();
		this.mapper = new MockFunctionMapper();
	}

	protected void tearDown() {
		this.resolver = null;
		this.mapper = null;
	}

	public void testMath() {
		assertEquals("2", evaluate("${1 + 1}"));
		assertEquals("5", evaluate("${10 - 5}"));
	}

	public void testLiteral() {
		assertEquals("ac", evaluate("${'ac'}"));
		assertEquals("\"", evaluate("${'\"'}"));
		assertEquals("true", evaluate("${true}"));
	}

	public void testEL6() {
		assertEquals("a''c", evaluate("${'a\\'\\'c'}"));
	}

	private String evaluate(String input) {
		try {
			StringReader rdr = new StringReader(input);
			ELParser parser = new ELParser(rdr);
			Object result = parser.ExpressionString();
			if (result instanceof String) {
				return (String) result;
			} else if (result instanceof Expression) {
				Expression expr = (Expression) result;
				result = expr.evaluate(this.resolver, this.mapper);
				return result == null ? null : result.toString();
			} else if (result instanceof ExpressionString) {
				Expression expr = (Expression) result;
				result = expr.evaluate(this.resolver, this.mapper);
				return result == null ? null : result.toString();
			} else {
				throw new RuntimeException(
						"Incorrect type returned; not String, Expression or ExpressionString");
			}
		} catch (ParseException pe) {
			throw new RuntimeException("ParseException thrown: "
					+ pe.getMessage(), pe);
		} catch (ELException ele) {
			throw new RuntimeException("ELException thrown: "
					+ ele.getMessage(), ele);
		}
	}

}
