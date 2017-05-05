package com.zxcl.webapp.biz.util.resultEntity;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -316893008921125541L;
	private boolean success;
	private String message;
	private Long dateline;
	private Integer currentPage;
	private Integer pageSize;
	private Long pageCount;
	private List<T> dataList;
	private Long recordCount;

	
	public PageBean() {
		this.success = true;
		this.dateline = System.currentTimeMillis();
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
		return this.dateline.longValue();
	}

	public void setDateline(long dateline) {
		this.dateline = Long.valueOf(dateline);
	}

	public int getCurrentPage() {
		return this.currentPage.intValue();
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = Integer.valueOf(currentPage);
	}

	public int getPageSize() {
		return this.pageSize.intValue();
	}

	public void setPageSize(int pageSize) {
		this.pageSize = Integer.valueOf(pageSize);
	}

	public long getPageCount() {
		return this.pageCount.longValue();
	}

	public void setPageCount(long recordCount) {
		if (this.pageSize.intValue() == 0)
			return;
		if (recordCount % this.pageSize.intValue() == 0L)
			this.pageCount = Long.valueOf(recordCount
					/ this.pageSize.intValue());
		else
			this.pageCount = Long.valueOf(recordCount
					/ this.pageSize.intValue() + 1L);
	}

	public List<T> getDataList() {
		return this.dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public long getRecordCount() {
		return this.recordCount.longValue();
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = Long.valueOf(recordCount);
	}
}
