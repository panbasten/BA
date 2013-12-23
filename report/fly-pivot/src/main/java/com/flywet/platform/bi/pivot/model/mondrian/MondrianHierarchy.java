package com.flywet.platform.bi.pivot.model.mondrian;

import java.util.ArrayList;

import com.flywet.platform.bi.pivot.model.olap.model.Dimension;
import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;
import com.flywet.platform.bi.pivot.model.olap.model.Level;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;
import com.flywet.platform.bi.pivot.model.olap.query.MDXElement;
import com.flywet.platform.bi.pivot.model.res.Resources;

/**
 * MondrianHierarchy is an adapter class for the Mondrian Hierarchy.
 */
public class MondrianHierarchy implements Hierarchy, MDXElement {

	private mondrian.olap.Hierarchy monHierarchy;
	private MondrianDimension dimension;
	private ArrayList aLevels;
	private MondrianModel model;
	private Resources resources;

	/**
	 * Constructor
	 * 
	 * @param monHierarchy
	 *            Mondrian Hierarchy
	 * @param dimension
	 *            parent
	 */
	protected MondrianHierarchy(mondrian.olap.Hierarchy monHierarchy,
			MondrianDimension dimension, MondrianModel model) {
		this.monHierarchy = monHierarchy;
		this.dimension = dimension;
		this.model = model;
		this.resources = Resources.instance(model.getLocale(),
				MondrianHierarchy.class);
		aLevels = new ArrayList();
		dimension.addHierarchy(this);
	}

	/**
	 * add level
	 * 
	 * @param level
	 *            MondrianLevel
	 */
	protected void addLevel(MondrianLevel level) {
		aLevels.add(level);
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Hierarchy#getDimension()
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Hierarchy#getLevels()
	 */
	public Level[] getLevels() {
		return (Level[]) aLevels.toArray(new MondrianLevel[0]);
	}

	public String getLabel() {
		String label = monHierarchy.getCaption();
		return resources.getOptionalString(label, label);
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Visitable#accept(Visitor)
	 */
	public void accept(Visitor visitor) {
		visitor.visitHierarchy(this);
	}

	/**
	 * Returns the monHierarchy.
	 * 
	 * @return mondrian.olap.Hierarchy
	 */
	public mondrian.olap.Hierarchy getMonHierarchy() {
		return monHierarchy;
	}

	public Object getRootDecoree() {
		return this;
	}

	/**
	 * @return the unique name
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Hierarchy#getUniqueName()
	 */
	public String getUniqueName() {
		return monHierarchy.getUniqueName();
	}

	public boolean hasAll() {
		return monHierarchy.hasAll();
	}
}
