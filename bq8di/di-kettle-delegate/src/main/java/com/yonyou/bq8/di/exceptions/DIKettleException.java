package com.yonyou.bq8.di.exceptions;

import com.yonyou.bq8.di.core.exception.DIException;

public class DIKettleException extends DIException {

	private static final long serialVersionUID = -8205557582486372505L;

	public DIKettleException() {
		super();
	}

	public DIKettleException(String message) {
		super(message);
	}

	public DIKettleException(Throwable cause) {
		super(cause);
	}

	public DIKettleException(String message, Throwable cause) {
		super(message, cause);
	}
}
