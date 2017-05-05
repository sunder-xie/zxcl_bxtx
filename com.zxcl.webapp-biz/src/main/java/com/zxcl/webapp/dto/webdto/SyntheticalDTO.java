package com.zxcl.webapp.dto.webdto;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class SyntheticalDTO {
	/**
	 * 保险公司编码
	 */
	private String insId;
	
	/**
	 * 询价单编码
	 */
	private String inquiryId;
	
	/**
	 * 车牌
	 */
	private String plateNo;
	
	/**
	 * 报价单编码
	 */
	private String quotaId;
	
	/**
	 * 订单编码
	 */
	private String orderId;
	
	/**
	 * 保险公司名称
	 */
	private String insCompanyName;
	
	/**
	 * 车主名称
	 */
	private String ownerName;
	
	/**
	 * 报价类型
	 */
	private String quotaType;
	
	/**
	 * 订单状态
	 */
	private String status;

	/**
	 * 状态名称
	 */
	private String statusName;
	
	/**
	 * 商业险金额
	 */
	private BigDecimal vciMon;
	
	/**
	 * 交强险金额
	 */
	private BigDecimal tciMon;
	
	/**
	 * 车船税金额
	 */
	private BigDecimal taxMon;
	
	/**
	 * 计算总保费
	 */
	public String getTotalMonStr(){
		DecimalFormat format=new DecimalFormat("#.00");
		BigDecimal mon=new BigDecimal(0.00);
		if(null!=vciMon){
			mon=mon.add(vciMon);
		}
		if(null!=tciMon){
			mon=mon.add(tciMon);
		}
		if(null!=taxMon){
			mon=mon.add(taxMon);
		}
		return format.format(mon);
	}
	
	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}


	public BigDecimal getVciMon() {
		return vciMon;
	}


	public void setVciMon(BigDecimal vciMon) {
		this.vciMon = vciMon;
	}


	public BigDecimal getTciMon() {
		return tciMon;
	}


	public void setTciMon(BigDecimal tciMon) {
		this.tciMon = tciMon;
	}


	public BigDecimal getTaxMon() {
		return taxMon;
	}


	public void setTaxMon(BigDecimal taxMon) {
		this.taxMon = taxMon;
	}


	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
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

	public String getInsCompanyName() {
		return insCompanyName;
	}

	public void setInsCompanyName(String insCompanyName) {
		this.insCompanyName = insCompanyName;
	}

	public String getQuotaType() {
		return quotaType;
	}

	public void setQuotaType(String quotaType) {
		this.quotaType = quotaType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}
