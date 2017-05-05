package com.zxcl.webapp.dto;

import java.util.List;

/**
 * 人工报价任务表
 * @author zx
 *
 */
public class ManualQuotationTaskDTO {
	
	/**
	 * 任务ID
	 */
	private String taskId;
	
	/**
	 * 询价单号
	 */
	private String inquiryId;
	
	/**
	 * 报价单号
	 */
	private String quoteId;
	
	/**
	 * 保险公司ID
	 */
	private String insId;
	
	/**
	 * 团队号
	 */
	private String teamId;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 任务状态，1-待报价、2-报价中、3-已报价、4-退回、5-已撤回确认投保、6-待核保（已提核）、7-核保退回、8-待缴费、9-生成保单
	 */
	private String taskStatus;
	
	/**
	 * 状态
	 */
	private String recordStatus;
	
	/**
	 * 人工报价任务轨迹表
	 */
	private List<QuoteTrackDTO> quoteTrackList;
	
	/**
	 * 文件ID
	 */
	private List<String> fileIds;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public List<QuoteTrackDTO> getQuoteTrackList() {
		return quoteTrackList;
	}

	public void setQuoteTrackList(List<QuoteTrackDTO> quoteTrackList) {
		this.quoteTrackList = quoteTrackList;
	}

	public List<String> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<String> fileIds) {
		this.fileIds = fileIds;
	}

	@Override
	public String toString() {
		return "ManualQuotationTaskDTO [taskId=" + taskId + ", inquiryId="
				+ inquiryId + ", quoteId=" + quoteId + ", insId=" + insId
				+ ", teamId=" + teamId + ", userId=" + userId + ", taskStatus="
				+ taskStatus + ", recordStatus=" + recordStatus
				+ ", quoteTrackList=" + quoteTrackList + ", fileIds=" + fileIds
				+ "]";
	}
}
