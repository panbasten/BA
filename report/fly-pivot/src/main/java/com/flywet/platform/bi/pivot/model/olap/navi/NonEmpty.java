package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;

/**
 * suppresses the display of empty rows/columns on an axis.
 * 
 * @author av
 */
public interface NonEmpty extends Extension {
	/**
	 * name of the Extension for lookup
	 */
	public static final String ID = "nonEmpty";

	/**
	 * @return true if non-empty rows are currently suppressed
	 */
	boolean isNonEmpty();

	/**
	 * change the visability of non-empty rows
	 */
	void setNonEmpty(boolean nonEmpty);
}
