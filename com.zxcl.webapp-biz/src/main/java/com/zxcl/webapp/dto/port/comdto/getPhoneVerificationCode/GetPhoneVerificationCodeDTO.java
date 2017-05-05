package com.zxcl.webapp.dto.port.comdto.getPhoneVerificationCode;

import com.zxcl.webapp.dto.port.comdto.AttestationDTO;

/**
 * 获取手机验证码
 * @author zx
 *
 */
public class GetPhoneVerificationCodeDTO {
	
	
	/**
	 * 认证
	 */
	private AttestationDTO attestationBase;
	
	/**
	 * 天平：支付平台代码
	 */
	private String platformCode;
	
	/**
	 * 天平：订单号
	 */
	private String orderId;
	
	/**
	 * 天平：订单金额
	 */
	private String orderAmt;
	
	/**
	 * 天平：折扣金额
	 */
	private String discount;
	
	/**
	 * 银行卡信息
	 */
	public BankCardInfoDTO bankCardInfoDTO;

	public AttestationDTO getAttestationBase() {
		return attestationBase;
	}

	public void setAttestationBase(AttestationDTO attestationBase) {
		this.attestationBase = attestationBase;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public BankCardInfoDTO getBankCardInfoDTO() {
		return bankCardInfoDTO;
	}

	public void setBankCardInfoDTO(BankCardInfoDTO bankCardInfoDTO) {
		this.bankCardInfoDTO = bankCardInfoDTO;
	}
	
}
