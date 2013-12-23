package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.olap.model.Axis;
import com.flywet.platform.bi.pivot.model.olap.model.ExpressionConstants;
import com.flywet.platform.bi.pivot.model.olap.model.OlapException;
import com.flywet.platform.bi.pivot.model.olap.model.Position;

/**
 * Allows sorting and ranking
 * 
 * @author av
 */
public interface SortRank extends Extension, ExpressionConstants {
	/**
	 * name of the Extension for lookup
	 */
	public static final String ID = "sortRank";

	/**
	 * turns sorting on / off (off = natural sorting)
	 */
	public boolean isSorting();

	public void setSorting(boolean enabled);

	/**
	 * returns true if user may sort by the members. This will be the case when
	 * one of the members is a measure. The GUI will paint a "sort button" for
	 * one of the members
	 */
	boolean isSortable(Position position);

	/**
	 * return true if the result is sorted by the members of the position
	 */
	boolean isCurrentSorting(Position position);

	int getSortMode();

	void setSortMode(int mode);

	/**
	 * number of members for topcount and bottomcount
	 */
	int getTopBottomCount();

	void setTopBottomCount(int topBottomCount);

	/**
	 * changes current sorting. If <code>mode</code> is <code>TOPCOUNT</code> or
	 * <code>BOTTOMCOUNT</code> the current value of <code>topBottomCount</code>
	 * will be used.
	 * 
	 * @param membersToSort
	 *            the axis to sort. Its one of the "other" axes, that do not
	 *            contain position
	 * @param position
	 *            the sort criteria
	 */
	void sort(Axis membersToSort, Position position) throws OlapException;

}
