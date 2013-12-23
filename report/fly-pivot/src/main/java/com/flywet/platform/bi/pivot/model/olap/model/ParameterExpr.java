package com.flywet.platform.bi.pivot.model.olap.model;

public interface ParameterExpr extends Expression {
	public static final int STRING = 0;
	public static final int NUMBER = 1;
	public static final int MEMBER = 2;

	/** internal name that identifies this parameter */
	String getName();

	/** label to display to the user */
	String getLabel();

	/** type of parameter */
	int getType();

	/** value of the parameter */
	Expression getValue();
}
