package com.yonyou.bq8.di.el;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.Expression;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import com.yonyou.bq8.di.el.ExpressionEvaluatorImpl;

import junit.framework.TestCase;

public class FunctionBindingTest extends TestCase {
	public static String before() {
		return "before";
	}

	public static String after() {
		return "after";
	}

	public static String echo(final String s) {
		return s;
	}

	private static class UpdatableFunctionMapper implements FunctionMapper {
		private Map mappings = new HashMap();

		public void setMapping(final String name, final Method function) {
			mappings.put(name, function);
		}

		public Method resolveFunction(final String prefix,
				final String localName) {
			return (Method) mappings.get(localName);
		}
	}

	public void testFunctionBindingWithParameters() throws ELException,
			NoSuchMethodException {
		final VariableResolver vr = new VariableResolver() {
			public Object resolveVariable(String arg0) throws ELException {
				return arg0 + "_";
			}
		};

		final UpdatableFunctionMapper fm = new UpdatableFunctionMapper();
		final Method echo = FunctionBindingTest.class.getDeclaredMethod("echo",
				new Class[] { String.class });
		fm.setMapping("echo", echo);

		final String theExpression = "${echo('1')}";
		final String theExpression2 = "${echo(ver)}";

		final ExpressionEvaluator evaluator = new ExpressionEvaluatorImpl();

		final Expression expr = evaluator.parseExpression(theExpression,
				String.class, fm);
		final Expression expr2 = evaluator.parseExpression(theExpression2,
				String.class, fm);

		assertEquals("1", expr.evaluate(vr));
		assertEquals("ver_", expr2.evaluate(vr));

	}

	public void testFunctionBinding() throws ELException, NoSuchMethodException {
		final String theExpression = "${theFunction()}";
		final String nestedExpression = "${echo(theFunction())}";
		final VariableResolver emptyVariableResolver = new VariableResolver() {
			public Object resolveVariable(String arg0) throws ELException {
				return null;
			}
		};
		final UpdatableFunctionMapper fm = new UpdatableFunctionMapper();
		final Method before = FunctionBindingTest.class.getDeclaredMethod(
				"before", new Class[0]);
		final Method after = FunctionBindingTest.class.getDeclaredMethod(
				"after", new Class[0]);
		final Method echo = FunctionBindingTest.class.getDeclaredMethod("echo",
				new Class[] { String.class });
		final ExpressionEvaluator evaluator = new ExpressionEvaluatorImpl();
		fm.setMapping("theFunction", before);
		fm.setMapping("echo", echo);

		final Expression expr = evaluator.parseExpression(theExpression,
				String.class, fm);
		final Expression nestedExpr = evaluator.parseExpression(
				nestedExpression, String.class, fm);
		assertEquals("before", expr.evaluate(emptyVariableResolver));
		assertEquals("before", nestedExpr.evaluate(emptyVariableResolver));

		fm.setMapping("theFunction", after);
		assertEquals("after", evaluator.evaluate(theExpression, String.class,
				emptyVariableResolver, fm));
		assertEquals("before", expr.evaluate(emptyVariableResolver));
		assertEquals("after", evaluator.evaluate(nestedExpression,
				String.class, emptyVariableResolver, fm));
		assertEquals("before", nestedExpr.evaluate(emptyVariableResolver));
	}
}
