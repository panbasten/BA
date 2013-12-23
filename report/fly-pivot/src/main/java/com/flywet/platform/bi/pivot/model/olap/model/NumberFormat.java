package com.flywet.platform.bi.pivot.model.olap.model;

public interface NumberFormat {
	boolean isGrouping();

	int getFractionDigits();

	boolean isPercent();
}
