package com.flywet.platform.bi.pivot.model.olap.model;

import java.util.List;

/**
 * Result of an OLAP query.
 * 
 * @author av
 */
public interface Result extends Visitable, Decorator {

	/**
	 * returns the cells of the result in row order. For a two dimensional
	 * result the <em>cell [rowIndex] [colIndex]</em> is found at index
	 * <em>columnCount *
	 * rowIndex + colIndex</em>
	 * 
	 * @return the Cells of the result
	 * @see Cell
	 */
	List getCells();

	/**
	 * return the axes
	 */
	Axis[] getAxes();

	/**
	 * return the slicer axis
	 */
	Axis getSlicer();

	/**
	 * @return true, if the result was rolled back due to overflow condition
	 */
	boolean isOverflowOccured();
}
