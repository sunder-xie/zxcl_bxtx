package com.zxcl.webapp.biz.util.resultEntity;

import java.util.List;

public class ResultList<T> {
	private boolean success;
	private String message;
	private long dateline = System.currentTimeMillis();
	private List<T> resultList;

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

	public int getTotalCount() {
		return this.resultList == null ? 0 : this.resultList.size();
	}

	public List<T> getResultList() {
		return this.resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
}
