package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.olap.model.Member;

/**
 * allows to place selected members on the slicer axis.
 * 
 * @author av
 */
public interface ChangeSlicer extends Extension {

	public static final String ID = "changeSlicer";

	/**
	 * @return the current slicer.
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Result#getSlicer
	 */
	Member[] getSlicer();

	/**
	 * sets the slicer
	 */
	void setSlicer(Member[] members);
}
