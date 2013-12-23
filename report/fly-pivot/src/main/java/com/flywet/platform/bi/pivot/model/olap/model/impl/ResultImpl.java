package com.flywet.platform.bi.pivot.model.olap.model.impl;

import java.util.List;

import com.flywet.platform.bi.pivot.model.olap.model.Axis;
import com.flywet.platform.bi.pivot.model.olap.model.Result;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

/**
 * Created on 11.10.2002
 * 
 * @author av
 */
public class ResultImpl implements Result {

	List cells;
	Axis slicer;
	Axis[] axes;

	/**
	 * Constructor for ResultImpl.
	 */
	public ResultImpl() {
		super();
	}

	/**
	 * Returns the axes.
	 * 
	 * @return Axis[]
	 */
	public Axis[] getAxes() {
		return axes;
	}

	/**
	 * Returns the cells.
	 * 
	 * @return List
	 */
	public List getCells() {
		return cells;
	}

	/**
	 * Returns the slicer.
	 * 
	 * @return Axis
	 */
	public Axis getSlicer() {
		return slicer;
	}

	/**
	 * Sets the axes.
	 * 
	 * @param axes
	 *            The axes to set
	 */
	public void setAxes(Axis[] axes) {
		this.axes = axes;
	}

	/**
	 * Sets the cells.
	 * 
	 * @param cells
	 *            The cells to set
	 */
	public void setCells(List cells) {
		this.cells = cells;
	}

	/**
	 * Sets the slicer.
	 * 
	 * @param slicer
	 *            The slicer to set
	 */
	public void setSlicer(Axis slicer) {
		this.slicer = slicer;
	}

	public void accept(Visitor visitor) {
		visitor.visitResult(this);
	}

	public Object getRootDecoree() {
		return this;
	}

	public boolean isOverflowOccured() {
		return false;
	}

}
