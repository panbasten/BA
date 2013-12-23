package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * expression defining a calculated member's property
 */
public interface PropertyExpr extends Visitable {

	/**
	 * @return the Value expression
	 */
	public Expression getValueExpr();

	/**
	 * @return the name of this property
	 */
	public String getName();

	/**
	 * @return the possible values
	 */
	public String[] getChoices();

} // PropertyExpr
