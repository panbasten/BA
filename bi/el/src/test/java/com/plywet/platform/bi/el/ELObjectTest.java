package com.plywet.platform.bi.el;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;

import junit.framework.TestCase;

import com.plywet.platform.bi.el.expression.Expression;
import com.plywet.platform.bi.el.expression.ExpressionString;
import com.plywet.platform.bi.el.parser.ELParser;
import com.plywet.platform.bi.el.parser.ParseException;

public class ELObjectTest extends TestCase {

	private class ObjectFunctionMapper implements FunctionMapper {
		private Map mappings = new HashMap();

		public void setMapping(final String name, final Method function) {
			mappings.put(name, function);
		}

		public Method resolveFunction(final String prefix,
				final String localName) {
			System.out.println(prefix);
			System.out.println(localName);
			return (Method) mappings.get(localName);
		}
	}

	private MockVariableResolver resolver;
	private ObjectFunctionMapper mapper;

	protected void setUp() {
		this.resolver = new MockVariableResolver();
		this.mapper = new ObjectFunctionMapper();

		List list = new ArrayList();
		list.add("str0");
		list.add("str1");
		this.resolver.addVariable("list", list);

		String[] arrays = new String[] { "arr0", "arr1" };
		this.resolver.addVariable("arrays", arrays);

		MockSimpleObject so = new MockSimpleObject();
		so.setParam1("pa1");
		so.setParam2("key2");
		this.resolver.addVariable("obj", so);

		Map map = new HashMap();
		map.put("key1", "value1");
		map.put("key2", "value2");
		this.resolver.addVariable("map", map);
	}

	protected void tearDown() {
		this.resolver = null;
		this.mapper = null;
	}

	public void testArray() throws ELException, NoSuchMethodException {

		final String objExpression = "${obj.param1}";
		final String objExpression2 = "${obj['param1']}";
		final String listExpression = "${list[1]}";
		final String arrayExpression = "${arrays[1]}";
		final String mapExpression = "${map['key1']}";
		final String mapExpression2 = "${map[obj.param2]}";

		assertEquals("pa1", evaluate(objExpression));
		assertEquals("pa1", evaluate(objExpression2));
		assertEquals("str1", evaluate(listExpression));
		assertEquals("arr1", evaluate(arrayExpression));
		assertEquals("value1", evaluate(mapExpression));
		assertEquals("value2", evaluate(mapExpression2));
	}

	public void testObjectMethod() throws ELException, NoSuchMethodException {

		final String objExpression = "${obj.getParam1()}";

		// String aa = evaluate(objExpression);
		// System.out.println(aa);
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
