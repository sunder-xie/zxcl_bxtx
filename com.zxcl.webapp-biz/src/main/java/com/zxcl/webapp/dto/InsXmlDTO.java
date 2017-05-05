package com.zxcl.webapp.dto;

import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;


public class InsXmlDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	public InsXmlDTO() {

	}

	public InsXmlDTO(String insId, String crtCde, String updCde) {
		super.crtCode = crtCde;
		super.updCode = updCde;
		this.insId = insId;
		
	}

	public InsXmlDTO(String insId, String inquiryId, String crtCde, String updCde) {
		super();
		this.inquiryId = inquiryId;
		this.insId = insId;
		super.crtCode = crtCde;
		super.updCode = updCde;
	}

	public InsXmlDTO(String insId, String inquiryId, String quotaId, String crtCde, String updCde) {
		super();
		this.inquiryId = inquiryId;
		this.quotaId = quotaId;
		this.insId = insId;
		super.crtCode = crtCde;
		super.updCode = updCde;
	}

	public InsXmlDTO(String insId, String inquiryId, String quotaId, String orderId, String crtCde,
			String updCde) {
		super();
		this.inquiryId = inquiryId;
		this.quotaId = quotaId;
		this.insId = insId;
		this.orderId = orderId;
		super.crtCode = crtCde;
		super.updCode = updCde;
	}

	private String xmlId;

	private String inquiryId;

	private String quotaId;

	private String insId;

	private String orderId;

	private String xmlFile;
	
	/**
	 * 发送还是返回    R:发送，B返回
	 */
	private String requestOrBack;
	
	/**
	 * 报文类型,1-车型查询，2-套餐，3-保费计算，4-投保，5-支付校验，6-支付 61财务开单；7-浮动告知单
	 * 列外：保费计算交强，31，保费计算商业，32等
	 *      
	 */
	private String xmlType;

	public String getXmlId() {
		return xmlId;
	}

	public void setXmlId(String xmlId) {
		this.xmlId = xmlId;
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

	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public String getRequestOrBack() {
		return requestOrBack;
	}

	public void setRequestOrBack(String requestOrBack) {
		this.requestOrBack = requestOrBack;
	}

	public String getXmlType() {
		return xmlType;
	}

	public void setXmlType(String xmlType) {
		this.xmlType = xmlType;
	}
}
