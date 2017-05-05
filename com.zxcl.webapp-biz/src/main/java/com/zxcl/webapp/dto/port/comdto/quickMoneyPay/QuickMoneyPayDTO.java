package com.zxcl.webapp.dto.port.comdto.quickMoneyPay;

import com.zxcl.webapp.dto.port.comdto.AttestationDTO;

/**
 * 快钱支付
 * @author zx
 *
 */
public class QuickMoneyPayDTO {
	
	/**
	 * 认证
	 */
	private AttestationDTO attestationBase;
	
	/**
	 * 天平：支付平台代码
	 */
	private String platformCode;
	
	/**
	 * 天平：银行编码
	 */
	private String bankCode;
	
	/**
	 * 天平：支付方式
	 */
	private String paymentMode;
	
	/**
	 * 天平：真实支付方式
	 */
	private String realPaymentMode;
	
	/**
	 * 天平：订单状态
	 */
	private String orderStatus;
	
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
	 * 天平：优惠卷折扣
	 */
	private String voucherNo;
	
	/**
	 * 银行卡信息
	 */
	private BankCardInfoDTO bankCardInfoDTO;

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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getRealPaymentMode() {
		return realPaymentMode;
	}

	public void setRealPaymentMode(String realPaymentMode) {
		this.realPaymentMode = realPaymentMode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public BankCardInfoDTO getBankCardInfoDTO() {
		return bankCardInfoDTO;
	}

	public void setBankCardInfoDTO(BankCardInfoDTO bankCardInfoDTO) {
		this.bankCardInfoDTO = bankCardInfoDTO;
	}
}
