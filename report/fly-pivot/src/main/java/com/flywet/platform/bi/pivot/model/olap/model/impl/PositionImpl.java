package com.flywet.platform.bi.pivot.model.olap.model.impl;

import com.flywet.platform.bi.pivot.model.olap.model.Member;
import com.flywet.platform.bi.pivot.model.olap.model.Position;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

/**
 * Created on 11.10.2002
 * 
 * @author av
 */
public class PositionImpl implements Position {
	Member members[];

	/**
	 * Returns the members.
	 * 
	 * @return Member[]
	 */
	public Member[] getMembers() {
		return members;
	}

	/**
	 * Sets the members.
	 * 
	 * @param members
	 *            The members to set
	 */
	public void setMembers(Member[] members) {
		this.members = members;
	}

	public void accept(Visitor visitor) {
		visitor.visitPosition(this);
	}

	public Object getRootDecoree() {
		return this;
	}
}
