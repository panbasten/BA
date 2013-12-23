package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * Cell of an OLAP result. Cells may have properties for alerting, e.g. a cell
 * may have an "arrow" property, thats value is some code for an trend arrow.
 * 
 * @author av
 */
public interface Cell extends PropertyHolder, Visitable, Decorator {
	/**
	 * return the value of the cell
	 */
	Object getValue();

	/**
	 * If the cells value represents a java.lang.Number, return its format.
	 * Returns null otherwise.
	 * 
	 * @see getValue()
	 */
	NumberFormat getFormat();

	/**
	 * return the formatted value of the cell
	 */
	String getFormattedValue();

	/**
	 * true if the cell is null
	 */
	boolean isNull();
}
