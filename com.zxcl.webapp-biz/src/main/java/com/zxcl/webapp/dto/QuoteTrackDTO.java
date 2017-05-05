package com.zxcl.webapp.dto;

/**
 * 报价轨迹DTO
 */
public class QuoteTrackDTO {
	
	/**
	 * 操作ID
	 */
	private String operatId ;
	
	/**
	 * 上一个操作ID
	 */
	private String onOperatId;

	/**
	 * 任务ID
	 */
	private String taskId;

	/**
	 * 操作时间
	 */
	private String operatTime;

	/**
	 * 操作人ID
	 */
	private String operatUser;

	/**
	 * 操作状态，1-提交报价，2-接受报价任务，3-已报价，4-审核退回，5-撤回，6-确认投保、7-已提核，8-已核待缴费，9-核保退回，10-已缴费，11-回写保单
	 */
	private String operatStatus;

	/**
	 * 备注
	 */
	private String remark;

	public String getOperatId() {
		return operatId;
	}

	public void setOperatId(String operatId) {
		this.operatId = operatId;
	}

	public String getOnOperatId() {
		return onOperatId;
	}

	public void setOnOperatId(String onOperatId) {
		this.onOperatId = onOperatId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOperatTime() {
		return operatTime;
	}

	public void setOperatTime(String operatTime) {
		this.operatTime = operatTime;
	}

	public String getOperatUser() {
		return operatUser;
	}

	public void setOperatUser(String operatUser) {
		this.operatUser = operatUser;
	}

	public String getOperatStatus() {
		return operatStatus;
	}

	public void setOperatStatus(String operatStatus) {
		this.operatStatus = operatStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "QuoteTrackDTO [operatId=" + operatId + ", onOperatId="
				+ onOperatId + ", taskId=" + taskId + ", operatTime="
				+ operatTime + ", operatUser=" + operatUser + ", operatStatus="
				+ operatStatus + ", remark=" + remark + "]";
	} 
}
