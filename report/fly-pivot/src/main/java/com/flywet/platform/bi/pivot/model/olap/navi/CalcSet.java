package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.olap.model.Expression;
import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;

/**
 * @author av
 */

public interface CalcSet extends Extension {

	public static final String ID = "calcSet";

	/**
	 * evaluates the expression once in global context.
	 * <p>
	 * Example: create an expression for customer, e.g. TopCount
	 * 
	 * <pre>
	 * Products   Customer
	 *   A         TopCount(Customer.members, 5)
	 *   B
	 *   C
	 * </pre>
	 * <p>
	 * SIMPLE will find the top 5 customers globally and will display the same 5
	 * customers for every product
	 */
	public static final int SIMPLE = 0;

	/**
	 * in case of multiple hierarchies on an axis, the expression is evaluated
	 * for every member of the hierarchies, that are left of this hierarchy.
	 * <p>
	 * Example: create an expression for customer, e.g. TopCount
	 * 
	 * <pre>
	 * Products   Customer
	 *   A         TopCount(Customer.members, 5)
	 *   B
	 *   C
	 * </pre>
	 * <p>
	 * GENERATE will find the top 5 customers for every product, it will show
	 * different customers for every product.
	 */
	public static final int GENERATE = 1;

	/**
	 * Expression is re-evaluated when the user navigates. Navigation is
	 * restricted.
	 */
	public static final int STICKY = 2;

	/**
	 * creates an Object that may be placed on an axis via
	 * PlaceHierarchiesOnAxes.
	 * 
	 * @param mode
	 *            one of SIMPLE, GENERATE, STICKY
	 * 
	 * @param expr
	 *            the Expression to parse
	 * 
	 * @param hier
	 *            the Hierarchy that the expression evaluates to
	 * 
	 * @return the object to place on the axis
	 * @throws InvalidExpressionException
	 *             in case the expression is not valid, e.g. misspelled function
	 *             names etc
	 * @see com.flywet.platform.bi.pivot.model.olap.navi.PlaceHierarchiesOnAxes#setQueryAxis
	 */
	Object createAxisExpression(int mode, Expression expr, Hierarchy hier)
			throws InvalidExpressionException;

}
