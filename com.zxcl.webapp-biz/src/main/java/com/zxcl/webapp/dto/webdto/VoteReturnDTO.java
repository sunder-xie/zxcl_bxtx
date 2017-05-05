package com.zxcl.webapp.dto.webdto;

public class VoteReturnDTO {

	/**
	 * 类型
	 */
	private String quotaType;
	/**
	 * 返回的编码
	 */
	private String returnCode;
	/**
	 * 返回的消息
	 */
	private String returnMsg;
	/**
	 * 订单号
	 */
	private String noticeNo;
	/**
	 * 保险公司
	 */
	private String insId;

	/**
	 * 订单号
	 */
	private String orderId;
	
	public String getQuotaType() {
		return quotaType;
	}

	public void setQuotaType(String quotaType) {
		this.quotaType = quotaType;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
