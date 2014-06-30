package com.flywet.platform.bi.pivot.exception;

import com.flywet.platform.bi.core.exception.BIException;

public class BIPivotException extends BIException {

	private static final long serialVersionUID = 412928335285812346L;

	public BIPivotException() {
		super();
	}

	public BIPivotException(String message) {
		super(message);
	}

	public BIPivotException(Throwable cause) {
		super(cause);
	}

	public BIPivotException(String message, Throwable cause) {
		super(message, cause);
	}

}
