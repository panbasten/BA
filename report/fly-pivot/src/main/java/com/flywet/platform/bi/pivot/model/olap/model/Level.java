package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * Level of a Hierarchy.
 * <p>
 * To support "ragged hierarchies" or "parent-child hierarchies" no assuptions
 * are made regarding the number, ordering or hierarchy of Levels instances.
 * 
 * @author av
 */
public interface Level extends Expression, Displayable, Visitable, Decorator {
	/**
	 * get the Hierarchy to which this Level belongs
	 */
	Hierarchy getHierarchy();
	/**
	 * @deprecated use ExpressionParser instead get the unique name
	 */
	// String getUniqueName();
}
