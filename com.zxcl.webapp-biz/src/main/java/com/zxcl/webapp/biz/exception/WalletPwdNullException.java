package com.zxcl.webapp.biz.exception;

public class WalletPwdNullException extends BusinessServiceException{
	private static final long serialVersionUID = 1L;

	public WalletPwdNullException() {
		super();
	}

	public WalletPwdNullException(String message) {
		super(message);
	}

	public WalletPwdNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public WalletPwdNullException(Throwable cause) {
		super(cause);
	}
}
