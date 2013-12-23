package com.flywet.platform.bi.pivot.model.olap.model;

import java.util.Comparator;

/**
 * OLAP Dimension
 * 
 * @author av
 */
public interface Dimension extends Expression, Displayable, Visitable,
		Decorator {

	/**
	 * sorts Dimensions by labels except Measures which always comes first.
	 */
	static Comparator MEASURES_FIRST_COMPARATOR = new Comparator() {
		public int compare(Object o1, Object o2) {
			Dimension d1 = (Dimension) o1;
			Dimension d2 = (Dimension) o2;
			if (d1.isMeasure() != d2.isMeasure())
				return d1.isMeasure() ? -1 : 1;
			return d1.getLabel().compareTo(d2.getLabel());
		}
	};

	/**
	 * return the hierarchies of this dimension
	 */
	Hierarchy[] getHierarchies();

	/**
	 * return true if this is a time dimension. Its common to have multiple time
	 * dimensions, e.g. one for receipt of order and one for shipping.
	 */
	boolean isTime();

	/**
	 * true if this is the measures dimension
	 */
	boolean isMeasure();
}
