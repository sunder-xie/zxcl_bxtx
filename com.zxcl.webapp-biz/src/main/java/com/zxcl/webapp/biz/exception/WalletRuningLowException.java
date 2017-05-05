package com.zxcl.webapp.biz.exception;

/**
 * @ClassName: 
 * @Description:佣金结算余额不足
 * @author zxj
 * @date 
 */
public class WalletRuningLowException extends Exception {
	private static final long serialVersionUID = 1L;

	public WalletRuningLowException() {
		super();
	}

	public WalletRuningLowException(String message) {
		super(message);
	}

	public WalletRuningLowException(String message, Throwable cause) {
		super(message, cause);
	}

	public WalletRuningLowException(Throwable cause) {
		super(cause);
	}
}
