package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.util.JPivotException;

public class InvalidExpressionException extends JPivotException {

	public InvalidExpressionException() {
		super();
	}

	public InvalidExpressionException(String message) {
		super(message);
	}

	public InvalidExpressionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidExpressionException(Throwable cause) {
		super(cause);
	}

}
