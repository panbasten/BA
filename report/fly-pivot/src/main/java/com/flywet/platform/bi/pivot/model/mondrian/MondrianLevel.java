package com.flywet.platform.bi.pivot.model.mondrian;

import java.util.ArrayList;

import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;
import com.flywet.platform.bi.pivot.model.olap.model.Level;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;
import com.flywet.platform.bi.pivot.model.olap.query.MDXElement;
import com.flywet.platform.bi.pivot.model.olap.query.MDXLevel;
import com.flywet.platform.bi.pivot.model.res.Resources;

/**
 * Level implementation for Mondrian. MondrianLevel is an adapter class for the
 * Mondrian Level.
 */
public class MondrianLevel implements Level, MDXElement, MDXLevel {

	private mondrian.olap.Level monLevel;
	private MondrianHierarchy hierarchy;
	private ArrayList aMembers;
	private MondrianModel model;
	private Resources resources;

	/**
	 * Constructor
	 * 
	 * @param monLevel
	 *            corresponding Mondrian Level
	 * @param hierarchy
	 *            parent object
	 * @param model
	 *            Model
	 */
	protected MondrianLevel(mondrian.olap.Level monLevel,
			MondrianHierarchy hierarchy, MondrianModel model) {
		this.monLevel = monLevel;
		this.hierarchy = hierarchy;
		this.model = model;
		this.resources = Resources.instance(model.getLocale(),
				MondrianLevel.class);
		aMembers = new ArrayList();
		hierarchy.addLevel(this);
	}

	/**
	 * add member to level
	 * 
	 * @param members
	 *            Array of MondrianMember
	 */
	protected void addMember(MondrianMember member) {
		aMembers.add(member);
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Level#getHierarchy()
	 */
	public Hierarchy getHierarchy() {
		return hierarchy;
	}

	public String getLabel() {
		String label = monLevel.getCaption();
		return resources.getOptionalString(label, label);
	}

	/**
	 * @return the level's depth (root level = 0)
	 * @see com.flywet.platform.bi.pivot.model.olap.query.MDXLevel#getDepth()
	 */
	public int getDepth() {
		return monLevel.getDepth();
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Visitable#accept(Visitor)
	 */
	public void accept(Visitor visitor) {
		visitor.visitLevel(this);
	}

	public Object getRootDecoree() {
		return this;
	}

	/**
	 * @return the assigned Mondrian Level
	 */
	public mondrian.olap.Level getMonLevel() {
		return monLevel;
	}

	/**
	 * @return the level's unique name
	 */
	public String getUniqueName() {
		return monLevel.getUniqueName();
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.query.MDXLevel#isAll()
	 */
	public boolean isAll() {
		return monLevel.isAll();
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.query.MDXLevel#hasChildLevel()
	 */
	public boolean hasChildLevel() {
		return (monLevel.getChildLevel() != null);
	}

} // MondrianLevel
