package com.zxcl.webapp.biz.util.resultEntity;

import java.util.Map;

public class ResultMap {
	private boolean success;
	private String message;
	private long dateline = System.currentTimeMillis();
	private Map<String, Object> resultMap;
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

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
		return this.resultMap == null ? 0 : this.resultMap.size();
	}

	public Map<String, Object> getResultMap() {
		return this.resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
}
