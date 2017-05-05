package com.zxcl.webapp.dto;


/**
 * 接口数据组织参数
 * 
 * @author 5555
 *
 */
public class DataConfigDTO {
	/**
	 * 用户编码
	 */
	private String userId;
	/**
	 * 保险公司编码
	 */
	private String insId;

	/**
	 * 中介公司编码
	 */
	private String agentId;

	/**
	 * 询价单编码
	 */
	private String inquiryId;

	/**
	 * 报价单编码
	 */
	private String quotaId;

	/**
	 * 订单编码
	 */
	private String orderId;
	/**
	 * 输入的搜索主键
	 */
	private String searchName;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	public String getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

}
