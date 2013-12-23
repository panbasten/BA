package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * An MDX expression.
 * <p>
 * 
 * Function names are all lowercase:
 * <ul>
 * <li>()</li> for Tupel
 * <li>{}</li> for Set
 * <li>.ident</li> for property ident, e.g. <code>.children</code>
 * <li>ident</li> for function ident, e.g. topcount()</code>
 * <li>*,/,+,- for infix operators
 * </ul>
 * 
 * @author av
 */
public interface FunCallExpr extends Expression {
	/**
	 * returns the function name
	 */
	String getName();

	Expression[] getArgs();
}
