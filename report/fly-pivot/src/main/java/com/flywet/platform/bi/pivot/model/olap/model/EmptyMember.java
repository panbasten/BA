package com.flywet.platform.bi.pivot.model.olap.model;

public class EmptyMember implements Displayable {

	public String getLabel() {
		return "";
	}

	public void accept(Visitor visitor) {
		visitor.visitEmptyMember(this);
	}

}
