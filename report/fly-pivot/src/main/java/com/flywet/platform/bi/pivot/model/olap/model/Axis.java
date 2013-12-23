package com.flywet.platform.bi.pivot.model.olap.model;

import java.util.List;

/**
 * Axis of an OLAP Result.
 * 
 * @author av
 */
public interface Axis extends Visitable, Decorator {

	/**
	 * return the positions on this axis
	 * 
	 * @see Position
	 */
	List getPositions();

	/**
	 * returns the hierachies that are currently visible on this axis. 0 =
	 * outermost, N = innermost. The order is significant.
	 */
	Hierarchy[] getHierarchies();

}
