package com.plywet.platform.bi.exceptions;

import com.plywet.platform.bi.core.exception.BIException;

public class BIKettleException extends BIException {

	private static final long serialVersionUID = -8205557582486372505L;

	public BIKettleException() {
		super();
	}

	public BIKettleException(String message) {
		super(message);
	}

	public BIKettleException(Throwable cause) {
		super(cause);
	}

	public BIKettleException(String message, Throwable cause) {
		super(message, cause);
	}
}
