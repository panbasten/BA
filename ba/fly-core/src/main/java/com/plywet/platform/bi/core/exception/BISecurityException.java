package com.plywet.platform.bi.core.exception;

public class BISecurityException extends BIException {

	private static final long serialVersionUID = 1241369381123356043L;

	public BISecurityException() {
		super();
	}

	public BISecurityException(String message) {
		super(message);
	}

	public BISecurityException(Throwable cause) {
		super(cause);
	}

	public BISecurityException(String message, Throwable cause) {
		super(message, cause);
	}
}
