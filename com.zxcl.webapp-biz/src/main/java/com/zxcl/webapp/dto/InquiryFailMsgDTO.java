package com.zxcl.webapp.dto;

import java.util.Date;

/**
 * 询价单报价、核保失败信息
 * @author xiaoxi
 *
 */
public class InquiryFailMsgDTO {
	
	/**
	 * 询价单号
	 */
	private String inquiryId;
	
	/**
	 * 保险公司编码
	 */
	private String insId;
	
	/**
	 * 请求类型1报价2核保
	 */
	private String reqType;
	
	/**
	 * 错误信息
	 */
	private String errorMassage;
	
	/**
	 * 替换后的提示信息
	 */
	private String errorMsgReplaced;
	
	/**
	 * 创建人
	 */
	private String crtCde;
	
	/**
	 * 创建时间
	 */
	private Date crtTm;
	
	/**
	 * 更新人
	 */
	private String updCde;
	
	/**
	 * 更新时间
	 */
	private Date updTm;

	public String getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getErrorMassage() {
		return errorMassage;
	}

	public void setErrorMassage(String errorMassage) {
		this.errorMassage = errorMassage;
	}

	public String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(String crtCde) {
		this.crtCde = crtCde;
	}

	public Date getCrtTm() {
		return crtTm;
	}

	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}

	public String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(String updCde) {
		this.updCde = updCde;
	}

	public Date getUpdTm() {
		return updTm;
	}

	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}
	
	public String getErrorMsgReplaced() {
		return errorMsgReplaced;
	}

	public void setErrorMsgReplaced(String errorMsgReplaced) {
		this.errorMsgReplaced = errorMsgReplaced;
	}

	@Override
	public String toString() {
		return "InquiryFailMsgDTO [inquiryId=" + inquiryId + ", insId=" + insId
				+ ", reqType=" + reqType + ", errorMassage=" + errorMassage
				+ ", errorMsgReplaced=" + errorMsgReplaced + ", crtCde="
				+ crtCde + ", crtTm=" + crtTm + ", updCde=" + updCde
				+ ", updTm=" + updTm + "]";
	}

}
