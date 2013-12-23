package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;

/**
 * resets the query to its initial state. This undoes all navigations that the
 * user has performed.
 * 
 * @author av
 */

public interface ResetQuery extends Extension {
	/**
	 * name of the Extension for lookup
	 */
	public static final String ID = "resetQuery";

	/**
	 * reset the query
	 */
	void reset();
}
