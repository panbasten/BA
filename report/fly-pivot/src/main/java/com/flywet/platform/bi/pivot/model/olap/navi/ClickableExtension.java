package com.flywet.platform.bi.pivot.model.olap.navi;

import java.util.Collection;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.table.ClickableMember;

public interface ClickableExtension extends Extension {
	public static final String ID = "clickable";

	/**
	 * List of ClickableMember
	 * 
	 * @see ClickableMember
	 */
	Collection getClickables();

	/**
	 * List of ClickableMember
	 * 
	 * @see ClickableMember
	 */
	void setClickables(Collection clickables);
}
