package com.zxcl.webapp.biz.exception;

public class SAOException extends Exception {
	private static final long serialVersionUID = 1L;

	public SAOException() {
		super();
	}

	public SAOException(String message) {
		super(message);
	}

	public SAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public SAOException(Throwable cause) {
		super(cause);
	}

	protected SAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
