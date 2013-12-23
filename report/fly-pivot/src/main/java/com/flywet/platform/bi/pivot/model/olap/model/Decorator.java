package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * provides access to the decorated object
 * 
 * @author av
 */
public interface Decorator {

	/**
	 * returns the object that has been created by the olap server. If this is
	 * part of a decorator chain, unwinds the chain and returns the root of all
	 * decorators.
	 */
	Object getRootDecoree();
}
