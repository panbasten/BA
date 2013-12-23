package com.flywet.platform.bi.pivot.model.olap.model.impl;

import com.flywet.platform.bi.pivot.model.olap.model.Dimension;
import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;
import com.flywet.platform.bi.pivot.model.olap.model.Level;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

/**
 * Created on 11.10.2002
 * 
 * @author av
 */
public class HierarchyImpl implements Hierarchy {
	Dimension dimension;
	Level[] levels;
	String label;
	boolean hasAll;

	/**
	 * Returns the dimension.
	 * 
	 * @return Dimension
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * Returns the levels.
	 * 
	 * @return Level[]
	 */
	public Level[] getLevels() {
		return levels;
	}

	/**
	 * Sets the dimension.
	 * 
	 * @param dimension
	 *            The dimension to set
	 */
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	/**
	 * Sets the levels.
	 * 
	 * @param levels
	 *            The levels to set
	 */
	public void setLevels(Level[] levels) {
		this.levels = levels;
	}

	public void accept(Visitor visitor) {
		visitor.visitHierarchy(this);
	}

	public Object getRootDecoree() {
		return this;
	}

	/**
	 * Returns the label.
	 * 
	 * @return String
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label
	 *            The label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	public boolean hasAll() {
		return hasAll;
	}

	public void setHasAll(boolean hasAll) {
		this.hasAll = hasAll;
	}
}
