package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;

/**
 * @author av
 */
public interface SwapAxes extends Extension {
	/**
	 * name of the Extension for lookup
	 */
	public static final String ID = "swapAxes";

	/**
	 * @return true if axes can be swapped, i.e. if the result is 2 dimensional
	 */
	boolean canSwapAxes();

	/**
	 * swaps the axes
	 */
	void setSwapAxes(boolean swap);

	boolean isSwapAxes();
}
