package com.zxcl.webapp.biz.exception;

public class SMSTBException extends Exception {
	private static final long serialVersionUID = 1L;

	public SMSTBException() {
		super();
	}

	public SMSTBException(String message) {
		super(message);
	}

	public SMSTBException(String message, Throwable cause) {
		super(message, cause);
	}

	public SMSTBException(Throwable cause) {
		super(cause);
	}
}
