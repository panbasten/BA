package com.flywet.platform.bi.component.exceptions;

import com.flywet.platform.bi.core.exception.BIException;

public class BIComponentException extends BIException {

	private static final long serialVersionUID = 412928335285812346L;

	public BIComponentException() {
		super();
	}

	public BIComponentException(String message) {
		super(message);
	}

	public BIComponentException(Throwable cause) {
		super(cause);
	}

	public BIComponentException(String message, Throwable cause) {
		super(message, cause);
	}

}
