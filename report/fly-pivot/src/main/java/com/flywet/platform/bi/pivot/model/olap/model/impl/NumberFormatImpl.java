package com.flywet.platform.bi.pivot.model.olap.model.impl;

import com.flywet.platform.bi.pivot.model.olap.model.NumberFormat;

/**
 * @author av
 */
public class NumberFormatImpl implements NumberFormat {
	boolean grouping = true;
	int fractionDigits = 2;
	boolean percent = false;

	/**
	 * @return int
	 */
	public int getFractionDigits() {
		return fractionDigits;
	}

	/**
	 * @return boolean
	 */
	public boolean isGrouping() {
		return grouping;
	}

	/**
	 * Sets the fractionDigits.
	 * 
	 * @param fractionDigits
	 *            The fractionDigits to set
	 */
	public void setFractionDigits(int fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	/**
	 * Sets the grouping.
	 * 
	 * @param grouping
	 *            The grouping to set
	 */
	public void setGrouping(boolean grouping) {
		this.grouping = grouping;
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.NumberFormat#isPercent()
	 */
	public boolean isPercent() {
		return percent;
	}

}
