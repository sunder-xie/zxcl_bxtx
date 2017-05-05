package com.zxcl.webapp.dto.rmi.intf.vote.resp;

import java.io.Serializable;

import com.zxcl.webapp.dto.rmi.intf.common.CommonDTO;

/**
 * 投保成功返回的DTO
 * @author zxj
 * @date 2016年7月13日
 * @description 
 */
public class VoteInsuranceReturnDTO extends CommonDTO implements Serializable {
	
	private static final long serialVersionUID = 4647994676416230426L;
	
	/**
	 * 保险公司编码
	 */
	private String insId;
	/**
	 * 错误编码:
	 */
	private String errorCode;

	/**
	 * 错误消息
	 */
	private String errorMessage;

	/**
	 * 联合投保单号
	 */
	private String carApplyNo;

	/**
	 * 交强险投保单号
	 */
	private String tciApplyNo;

	/**
	 * 商业险投保单号
	 */
	private String vciApplyNo;
	
	/**
	 * 商业险保单号
	 */
	private String vciPlyNo;

	/**
	 * 交强险保单号
	 */
	private String tciPlyNo;

	/**
	 * 订单号
	 */
	private String noticeNo;

	/**
	 * 订单状态
	 */
	private String status;

	/**
	 * 审核标准：1、3：通过，2：不通过
	 */
	private String underWriteInd;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 富德：支付金额
	 */
	private String orderAmount;
	
	/**
	 * 核保是否成功标识(包括转人工核保也为true)
	 */
	private boolean insureSucc = false;
	
	public String getInsId() {
		return insId;
	}

	
	public String getOrderAmount() {
		return orderAmount;
	}


	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}


	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCarApplyNo() {
		return carApplyNo;
	}

	public void setCarApplyNo(String carApplyNo) {
		this.carApplyNo = carApplyNo;
	}

	public String getTciApplyNo() {
		return tciApplyNo;
	}

	public void setTciApplyNo(String tciApplyNo) {
		this.tciApplyNo = tciApplyNo;
	}

	public String getVciApplyNo() {
		return vciApplyNo;
	}

	public void setVciApplyNo(String vciApplyNo) {
		this.vciApplyNo = vciApplyNo;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getUnderWriteInd() {
		return underWriteInd;
	}

	public void setUnderWriteInd(String underWriteInd) {
		this.underWriteInd = underWriteInd;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean isInsureSucc() {
		return insureSucc;
	}

	public void setInsureSucc(boolean insureSucc) {
		this.insureSucc = insureSucc;
	}


	public String getVciPlyNo() {
		return vciPlyNo;
	}


	public void setVciPlyNo(String vciPlyNo) {
		this.vciPlyNo = vciPlyNo;
	}


	public String getTciPlyNo() {
		return tciPlyNo;
	}


	public void setTciPlyNo(String tciPlyNo) {
		this.tciPlyNo = tciPlyNo;
	}


	@Override
	public String toString() {
		return "VoteInsuranceReturnDTO [insId=" + insId + ", errorCode="
				+ errorCode + ", errorMessage=" + errorMessage
				+ ", carApplyNo=" + carApplyNo + ", tciApplyNo=" + tciApplyNo
				+ ", vciApplyNo=" + vciApplyNo + ", vciPlyNo=" + vciPlyNo
				+ ", tciPlyNo=" + tciPlyNo + ", noticeNo=" + noticeNo
				+ ", status=" + status + ", underWriteInd=" + underWriteInd
				+ ", orderId=" + orderId + ", orderAmount=" + orderAmount
				+ ", insureSucc=" + insureSucc + "]";
	}

}
