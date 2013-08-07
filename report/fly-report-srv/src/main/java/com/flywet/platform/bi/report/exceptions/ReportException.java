package com.flywet.platform.bi.report.exceptions;

public class ReportException extends Exception {

	private static final long serialVersionUID = -5180479026788787927L;

	public ReportException() {
		super();
	}

	public ReportException(String message) {
		super(message);
	}

	public ReportException(Throwable cause) {
		super(cause);
	}

	public ReportException(String message, Throwable cause) {
		super(message, cause);
	}

}
