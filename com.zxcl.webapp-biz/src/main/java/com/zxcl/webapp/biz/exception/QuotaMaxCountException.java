package com.zxcl.webapp.biz.exception;

public class QuotaMaxCountException extends ActionException {
	private static final long serialVersionUID = 1L;

	public QuotaMaxCountException() {
		super();
	}

	public QuotaMaxCountException(String message) {
		super(message);
	}

	public QuotaMaxCountException(String message, Throwable cause) {
		super(message, cause);
	}

	public QuotaMaxCountException(Throwable cause) {
		super(cause);
	}
}
