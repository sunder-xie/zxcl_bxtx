package com.zxcl.webapp.dto;

import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;

/**
 * 定时任务
 * 
 * @author 5555
 *
 */
public class SyncTaskDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String pkId;

	/**
	 * 执行者ID
	 */
	private String executorId;

	/**
	 * 状态:N待同步，R同步中，Y同步成功，E同步失败
	 */
	private String syncStatus;

	/**
	 * 同步业务关键字：OrderNO
	 */
	private String bussNo;

	/**
	 * 已经同步次数
	 */
	private int retryTimes;

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getExecutorId() {
		return executorId;
	}

	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}

	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getBussNo() {
		return bussNo;
	}

	public void setBussNo(String bussNo) {
		this.bussNo = bussNo;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}
}
