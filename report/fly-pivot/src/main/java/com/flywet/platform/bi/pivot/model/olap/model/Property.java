package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * Property of a Member
 * 
 * @author av
 */
public interface Property extends Visitable, Decorator, Displayable,
		PropertyHolder, Alignable {
	/**
	 * @return the name of the Property
	 */
	String getName();

	/**
	 * the value of the property
	 */
	String getValue();

	/**
	 * if true, this property may be a nested property whose path is specified
	 * by '.' in its name.
	 */
	boolean isNormalizable();
}
