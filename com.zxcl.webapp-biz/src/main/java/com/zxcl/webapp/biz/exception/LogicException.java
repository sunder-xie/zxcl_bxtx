package com.zxcl.webapp.biz.exception;

public class LogicException extends Exception {
	private static final long serialVersionUID = 1L;

	private String errorCode;

	public LogicException() {
		super();
	}

	public LogicException(String message) {
		super(message);
	}

	public LogicException(String message, String errorCode) {
		super(message);
		this.errorCode=errorCode;
	}

	public LogicException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogicException(Throwable cause) {
		super(cause);
	}

	protected LogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
