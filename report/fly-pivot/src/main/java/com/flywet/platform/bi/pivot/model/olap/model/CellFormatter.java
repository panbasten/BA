package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * Handle the cell formatting exit
 */
public interface CellFormatter {

	/**
	 * user provided cell formatting function
	 * 
	 * @param cell
	 * @return the formatted value
	 */
	public String formatCell(Cell cell);

} // CellFormatter
