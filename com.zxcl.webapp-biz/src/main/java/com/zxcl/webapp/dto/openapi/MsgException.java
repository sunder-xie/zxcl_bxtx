package com.zxcl.webapp.dto.openapi;

public class MsgException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public MsgException(String m) {
		super(m);
	}
	
	public MsgException(String m, Throwable e) {
		super(m,e);
	}
}
