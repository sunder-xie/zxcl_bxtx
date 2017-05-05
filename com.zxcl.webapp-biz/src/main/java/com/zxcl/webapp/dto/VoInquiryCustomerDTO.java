package com.zxcl.webapp.dto;

import java.io.Serializable;

/**
 * 保险报价-车辆信息填写页面的被保人投保人信息
 * @author zxj
 * @date 2016年8月15日
 * @description 
 */
public class VoInquiryCustomerDTO implements Serializable{
	private static final long serialVersionUID = 8322821234332674196L;
	private String insureSameAsOwner;
	private String insureNoSameAsOwnerName;
	private String insureNoSameAsOwnerCard;
	private String insNoSameAsCertfCdeType;
	
	private String appSameAsOwner;
	private String appNoSameAsOwnerName;
	private String appNoSameAsOwnerCard;
	private String appNoSameAsCertfCdeType;
	
	public String getInsureSameAsOwner() {
		return insureSameAsOwner;
	}
	public void setInsureSameAsOwner(String insureSameAsOwner) {
		this.insureSameAsOwner = insureSameAsOwner;
	}
	public String getAppSameAsOwner() {
		return appSameAsOwner;
	}
	public void setAppSameAsOwner(String appSameAsOwner) {
		this.appSameAsOwner = appSameAsOwner;
	}
	public String getInsureNoSameAsOwnerName() {
		return insureNoSameAsOwnerName;
	}
	public void setInsureNoSameAsOwnerName(String insureNoSameAsOwnerName) {
		this.insureNoSameAsOwnerName = insureNoSameAsOwnerName;
	}
	public String getInsureNoSameAsOwnerCard() {
		return insureNoSameAsOwnerCard;
	}
	public void setInsureNoSameAsOwnerCard(String insureNoSameAsOwnerCard) {
		this.insureNoSameAsOwnerCard = insureNoSameAsOwnerCard;
	}
	public String getAppNoSameAsOwnerName() {
		return appNoSameAsOwnerName;
	}
	public void setAppNoSameAsOwnerName(String appNoSameAsOwnerName) {
		this.appNoSameAsOwnerName = appNoSameAsOwnerName;
	}
	public String getAppNoSameAsOwnerCard() {
		return appNoSameAsOwnerCard;
	}
	public void setAppNoSameAsOwnerCard(String appNoSameAsOwnerCard) {
		this.appNoSameAsOwnerCard = appNoSameAsOwnerCard;
	}
	public String getInsNoSameAsCertfCdeType() {
		return insNoSameAsCertfCdeType;
	}
	public void setInsNoSameAsCertfCdeType(String insNoSameAsCertfCdeType) {
		this.insNoSameAsCertfCdeType = insNoSameAsCertfCdeType;
	}
	public String getAppNoSameAsCertfCdeType() {
		return appNoSameAsCertfCdeType;
	}
	public void setAppNoSameAsCertfCdeType(String appNoSameAsCertfCdeType) {
		this.appNoSameAsCertfCdeType = appNoSameAsCertfCdeType;
	}
	
	
}
