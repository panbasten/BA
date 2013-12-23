package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * container for name/value pairs.
 * 
 * @author av
 */

public interface PropertyHolder {

	/**
	 * get all properties of this member
	 */
	Property[] getProperties();

	/**
	 * get a specific property of this member
	 */
	Property getProperty(String name);
}
