package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.olap.model.Member;

/**
 * allows the user to delete calculated members from the query
 * 
 * @author av
 */
public interface MemberDeleter extends Extension {
	public static final String ID = "memberDeleter";

	boolean isDeletable(Member m);

	void delete(Member m);

}
