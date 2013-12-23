package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;
import com.flywet.platform.bi.pivot.model.olap.model.Member;

/**
 * Substitues the members of a hierarchy with the children of a member. Example:
 * A table shows the continents "America", "Asia", "Europe" etc. DrillReplace
 * for "Europe" will replace the continents with the countries of "Europe".
 * 
 * @author av
 */
public interface DrillReplace extends Extension {
	/**
	 * name of the Extension for lookup
	 */
	public static final String ID = "drillReplace";

	/**
	 * drill down is possible if <code>member</code> has children
	 */
	boolean canDrillDown(Member member);

	/**
	 * drill up is possible if not all members of the top level hierarchy are
	 * shown.
	 */
	boolean canDrillUp(Hierarchy hier);

	/**
	 * replaces the members. Let <code>H</code> be the hierarchy that member
	 * belongs to. Then drillDown will replace all members from <code>H</code>
	 * that are currently visible with the children of <code>member</code>.
	 */
	void drillDown(Member member);

	/**
	 * replaces all visible members of hier with the members of the next higher
	 * level.
	 */
	void drillUp(Hierarchy hier);

}
