package com.flywet.platform.bi.pivot.model.olap.model;

import com.flywet.platform.bi.pivot.model.util.JPivotException;

public class OlapException extends JPivotException {

	/**
	 * Constructor for OlapException.
	 */
	public OlapException() {
		super();
	}

	/**
	 * Constructor for OlapException.
	 * 
	 * @param arg0
	 */
	public OlapException(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor for OlapException.
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public OlapException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for OlapException.
	 * 
	 * @param arg0
	 */
	public OlapException(Throwable arg0) {
		super(arg0);
	}

}
