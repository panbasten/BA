package com.flywet.platform.bi.pivot.model.olap.model.impl;

import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;
import com.flywet.platform.bi.pivot.model.olap.model.Level;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

/**
 * Created on 11.10.2002
 * 
 * @author av
 */
public class LevelImpl implements Level {
	String label;
	Hierarchy hierarchy;

	public LevelImpl() {
	}

	public LevelImpl(String label, Hierarchy hierarchy) {
		this.label = label;
		this.hierarchy = hierarchy;
	}

	/**
	 * Returns the hierarchy.
	 * 
	 * @return Hierarchy
	 */
	public Hierarchy getHierarchy() {
		return hierarchy;
	}

	/**
	 * Sets the hierarchy.
	 * 
	 * @param hierarchy
	 *            The hierarchy to set
	 */
	public void setHierarchy(Hierarchy hierarchy) {
		this.hierarchy = hierarchy;
	}

	public void accept(Visitor visitor) {
		visitor.visitLevel(this);
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

	/**
	 * @return unique name
	 */
	public String getUniqueName() {
		return "[" + label + "]";
	}

}
