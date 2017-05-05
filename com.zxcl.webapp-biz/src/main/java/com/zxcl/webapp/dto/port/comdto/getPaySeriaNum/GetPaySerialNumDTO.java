package com.zxcl.webapp.dto.port.comdto.getPaySeriaNum;

import java.util.List;

import com.zxcl.webapp.dto.port.comdto.AttestationDTO;

/**
 * 获取流水号
 * @author zx
 *
 */
public class GetPaySerialNumDTO {

	/**
	 * 认证
	 */
	private AttestationDTO attestationBase;
	
	/**
	 * 天平：机构代码
	 */
	private String departmentCode;
	
	/**
	 * 天平：渠道代码
	 */
	private String transSourceCode;
	
	/**
	 * 天平：支付平台代码
	 */
	private String platformCode;
	
	/**
	 * 天平：支付超时时间
	 */
	private String payTimeout;
	
	/**
	 * 天平：订单金额
	 */
	private String orderAmt;
	
	/**
	 * 天平：支付方式
	 */
	private String paymentMode;
	
	/**
	 * 天平：真实支付方式
	 */
	private String realPaymentMode;
	
	/**
	 * 天平：支付类型
	 */
	private String payType;
	
	/**
	 * 产品列表
	 */
	private List<ProductDTO> productList;

	public AttestationDTO getAttestationBase() {
		return attestationBase;
	}

	public void setAttestationBase(AttestationDTO attestationBase) {
		this.attestationBase = attestationBase;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getTransSourceCode() {
		return transSourceCode;
	}

	public void setTransSourceCode(String transSourceCode) {
		this.transSourceCode = transSourceCode;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getPayTimeout() {
		return payTimeout;
	}

	public void setPayTimeout(String payTimeout) {
		this.payTimeout = payTimeout;
	}

	public String getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public List<ProductDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDTO> productList) {
		this.productList = productList;
	}
	
}
