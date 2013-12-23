package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;
import com.flywet.platform.bi.pivot.model.olap.model.Member;
import com.flywet.platform.bi.pivot.model.util.JPivotRuntimeException;

/**
 * exposes parent/child relationship between members. Allows members to be
 * displayed in a tree style GUI. Implementations will have to send metadata
 * queries to the database.
 * 
 * @author av
 */

public interface MemberTree extends Extension {

	/**
	 * thrown if too many member would be returned
	 */
	public class TooManyMembersException extends JPivotRuntimeException {
		public TooManyMembersException() {
			super();
		}

		public TooManyMembersException(Throwable cause) {
			super(cause);
		}
	}

	/**
	 * name of the Extension for lookup
	 */
	public static final String ID = "memberTree";

	/**
	 * @return the root members of a hierarchy. This is for example the "All"
	 *         member or the list of measures.
	 */
	Member[] getRootMembers(Hierarchy hier) throws TooManyMembersException;

	/**
	 * @return true if the member can be expanded
	 */
	boolean hasChildren(Member member);

	/**
	 * @return the children of the member
	 */
	Member[] getChildren(Member member) throws TooManyMembersException;

	/**
	 * @return the parent of member or null, if this is a root member
	 */
	Member getParent(Member member);
}
