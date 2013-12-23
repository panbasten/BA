package com.flywet.platform.bi.pivot.model.olap.navi;

/**
 * Describes a page
 * 
 * @author av
 */
public interface PageInfo {

	/**
	 * 0-based index of page page
	 */
	int getPageIndex();

	/**
	 * 1-based number of page page
	 */
	int getPageNo();

	/**
	 * number of available pages
	 */
	int getPageCount();

	/**
	 * short description of the first Item
	 */
	String getFirstShort();

	/**
	 * long description of the first Item
	 */
	String getFirstLong();

	/**
	 * short description of the last Item
	 */
	String getLastShort();

	/**
	 * long description of the last Item
	 */
	String getLastLong();
}
