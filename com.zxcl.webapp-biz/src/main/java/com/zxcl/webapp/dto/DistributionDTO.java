package com.zxcl.webapp.dto;

import java.util.Date;

import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**
 * 保单配送
 * 
 * @author 5555
 *
 */
public class DistributionDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 询价
	 */
	private InquiryDTO inquiry;

	/**
	 * 报价单号
	 */
	private QuotaDTO quota;

	/**
	 * 保险公司
	 */
	private InsuranceDTO insurance;

	/**
	 * 订单
	 */
	private OrderDTO order;

	/**
	 * 名字
	 */
	private String disName;

	/**
	 * 配送时间
	 */
	private Date dispatchTime;
	/**
	 * 配送方式(0-配送 1-自取)
	 */
	private String dispatchType;
	
	/**
	 * 配送保单的快递单号
	 */
	private String expressDeliveryNum;
	
	
	public String getExpressDeliveryNum() {
		return expressDeliveryNum;
	}

	public void setExpressDeliveryNum(String expressDeliveryNum) {
		this.expressDeliveryNum = expressDeliveryNum;
	}

	/**
	 * 电话
	 */
	private String disPhone;
	/**
	 * 状态
	 */
	private char status;
	/**
	 * 地址
	 */
	private String disAddress;

	/**
	 * 省级编码
	 */
	private String disProvince;

	/**
	 * 市级编码
	 */
	private String disCity;
	
	/**
	 * 配送时间段
	 */
	private String sendScope;
	
	private String orderId;
	
	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDispatchType() {
		return dispatchType;
	}

	public void setDispatchType(String dispatchType) {
		this.dispatchType = dispatchType;
	}

	public InquiryDTO getInquiry() {
		return inquiry;
	}

	public void setInquiry(InquiryDTO inquiry) {
		this.inquiry = inquiry;
	}

	public QuotaDTO getQuota() {
		return quota;
	}

	public void setQuota(QuotaDTO quota) {
		this.quota = quota;
	}

	public InsuranceDTO getInsurance() {
		return insurance;
	}

	public void setInsurance(InsuranceDTO insurance) {
		this.insurance = insurance;
	}

	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}

	public String getDisName() {
		return disName;
	}

	public void setDisName(String disName) {
		this.disName = disName;
	}

	public Date getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(Date dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public String getDisPhone() {
		return disPhone;
	}

	public void setDisPhone(String disPhone) {
		this.disPhone = disPhone;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getDisAddress() {
		return disAddress;
	}

	public void setDisAddress(String disAddress) {
		this.disAddress = disAddress;
	}

	public String getDisProvince() {
		return disProvince;
	}

	public void setDisProvince(String disProvince) {
		this.disProvince = disProvince;
	}

	public String getDisCity() {
		return disCity;
	}

	public void setDisCity(String disCity) {
		this.disCity = disCity;
	}

	public String getSendScope() {
		return sendScope;
	}

	public void setSendScope(String sendScope) {
		this.sendScope = sendScope;
	}
}
