package com.zxcl.webapp.biz.util.resultEntity;

public class ResultObject<T> {
	private boolean success;
	private String message;
	private long dateline = System.currentTimeMillis();
	private T result;

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getDateline() {
		return this.dateline;
	}

	public T getResult() {
		return this.result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}