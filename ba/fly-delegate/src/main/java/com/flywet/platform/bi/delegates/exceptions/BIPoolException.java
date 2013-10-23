package com.flywet.platform.bi.delegates.exceptions;

import com.flywet.platform.bi.core.exception.BIException;

public class BIPoolException extends BIException {

	private static final long serialVersionUID = -5686637134063391885L;

	public BIPoolException() {
		super();
	}

	public BIPoolException(String message) {
		super(message);
	}

	public BIPoolException(Throwable cause) {
		super(cause);
	}

	public BIPoolException(String message, Throwable cause) {
		super(message, cause);
	}

}
