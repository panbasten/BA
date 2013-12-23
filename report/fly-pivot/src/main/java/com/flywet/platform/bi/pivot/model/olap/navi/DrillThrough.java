package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.olap.model.Cell;
import com.flywet.platform.bi.pivot.model.wcf.table.TableModel;

/**
 * 
 * @author Robin Bagot
 */
public interface DrillThrough extends Extension {
	/**
	 * name of the Extension for lookup
	 */
	public static final String ID = "drillThrough";

	/**
	 * drill through is possible if <code>cell</code> is stored and not
	 * calculated
	 */
	boolean canDrillThrough(Cell cell);

	/**
	 * retrieves the individual data points that made up the value of the Cell
	 */
	TableModel drillThrough(Cell cell);

}
