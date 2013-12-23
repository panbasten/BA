package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * Hierarchy of a dimension
 * 
 * @see Dimension
 * @author av
 */
public interface Hierarchy extends Expression, Displayable, Visitable,
		Decorator {
	/**
	 * get the Dimension to which this Hierarchy belongs.
	 */
	Dimension getDimension();

	/**
	 * get the Levels of this Hierarchy. Returns null, if this is a ragged
	 * hierarchy that does not support levels.
	 */
	Level[] getLevels();

	boolean hasAll();
}
