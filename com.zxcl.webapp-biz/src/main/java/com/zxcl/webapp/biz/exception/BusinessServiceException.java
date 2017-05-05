package com.zxcl.webapp.biz.exception;

public class BusinessServiceException extends Exception {
	private static final long serialVersionUID = 1L;

	public BusinessServiceException() {
		super();
	}

	public BusinessServiceException(String message) {
		super(message);
	}

	public BusinessServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessServiceException(Throwable cause) {
		super(cause);
	}
}
