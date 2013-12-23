package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * Position of an OLAP Result Axis
 * 
 * @author av
 */
public interface Position extends Visitable, Decorator {

	/**
	 * get the members of this position
	 */
	Member[] getMembers();

}
