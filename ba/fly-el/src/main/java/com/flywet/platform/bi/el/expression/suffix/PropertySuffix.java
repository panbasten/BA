package com.flywet.platform.bi.el.expression.suffix;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import com.flywet.platform.bi.el.expression.StringLiteral;

/**
 * 
 * <p>
 * Represents an operator that obtains the value of another value's property.
 * This is a specialization of ArraySuffix - a.b is equivalent to a["b"]<br>
 * 
 * 该类表示一个属性操作符，可以从另一个值的属性中获得值。<br>
 * 该类是ArraySuffix的一个特定实现。<br>
 * a.b == a["b"]
 * 
 * @author PeterPan
 **/

public class PropertySuffix extends ArraySuffix {

	String mName;

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public PropertySuffix(String pName) {
		super(null);
		mName = pName;
	}

	@Override
	Object evaluateIndex(VariableResolver pResolver, FunctionMapper functions)
			throws ELException {
		return mName;
	}

	@Override
	String getOperatorSymbol() {
		return ".";
	}

	@Override
	public String getExpressionString() {
		return "." + StringLiteral.toIdentifierToken(mName);
	}

}
