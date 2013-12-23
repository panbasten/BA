package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.olap.model.Level;
import com.flywet.platform.bi.pivot.model.olap.model.Member;
import com.flywet.platform.bi.pivot.model.olap.model.MemberPropertyMeta;

/**
 * retrieves the description of the available member properties
 * 
 * @author av
 */
public interface MemberProperties extends Extension {

	/**
	 * name of the Extension for lookup
	 */
	static final String ID = "memberProperties";

	MemberPropertyMeta[] getMemberPropertyMetas(Level level);

	/**
	 * Properties are either Level scope or Dimension scope. Properties are
	 * unique within their scope. If scope is level, then properties with same
	 * name in different levels are treated as different Properties. If false,
	 * the scope will be Dimension.
	 * 
	 * @return
	 */
	boolean isLevelScope();

	/**
	 * returns a string <code>scope</code> that represents the scope of Member
	 * m. The returned String <code>scope</code> ensures that
	 * <code>scope.equals(MemberPropertyMeta.getScope())</code> is true if the
	 * property belongs to member m.
	 */
	String getPropertyScope(Member m);

	/**
	 * sets the visible properties. Optimizing implementations of PropertyHolder
	 * may only return these properties.
	 * 
	 * @see com.flywet.platform.bi.pivot.model.olap.model.PropertyHolder
	 */
	void setVisibleProperties(MemberPropertyMeta[] props);

}
