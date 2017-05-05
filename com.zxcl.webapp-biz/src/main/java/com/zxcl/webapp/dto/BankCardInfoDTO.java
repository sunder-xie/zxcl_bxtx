package com.zxcl.webapp.dto;

import java.util.Date;

/**
 * 银行卡信息
 * @author zx
 *
 */
public class BankCardInfoDTO {
	
	/**
	 * 卡号
	 */
	private String cardNum;
	
	/**
	 * 客户姓名
	 */
	private String name;
	
	/**
	 * 身份证号
	 */
	private String cardHolderId;
	
	/**
	 * 电话号码
	 */
	private String phone;
	
	/**
	 * 手机验证码
	 */
	private String validCode;
	
	/**
	 * 银行卡类型
	 */
	private String cardType;
	
	/**
	 * 有效期MMYY（信用卡）
	 */
	private String expiredDate;
	
	/**
	 * 对于银联和VISA卡，对应卡片背面的CVV2数字；对于MasterCard卡，对应卡片背面的CVC2数字（信用卡）
	 */
	private String cvv2;

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardHolderId() {
		return cardHolderId;
	}

	public void setCardHolderId(String cardHolderId) {
		this.cardHolderId = cardHolderId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	
}
