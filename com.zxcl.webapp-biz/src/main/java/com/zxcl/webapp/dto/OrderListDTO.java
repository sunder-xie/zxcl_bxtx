 package com.zxcl.webapp.dto;

import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**
 * 核保成功以后返回的页面信息
 * 
 * @author 5555
 *
 */
public class OrderListDTO {

	/**
	 * 报价
	 */
	private QuotaDTO quota;

	/**
	 * 询价
	 */
	private InquiryDTO inquiry;

	/**
	 * 订单
	 */
	private OrderDTO order;

	/**
	 * 保险公司
	 */
	private InsuranceDTO insurance;

	/**
	 * 车牌
	 */
	private String plateNo;

	/**
	 * 交强险
	 */
	private String tciPrem;

	/**
	 * 商业险
	 */
	private String vciPrem;

	/**
	 * 车船税
	 */
	private String tax;

	/**
	 * 状态
	 */
	private String status;

	public QuotaDTO getQuota() {
		return quota;
	}

	public void setQuota(QuotaDTO quota) {
		this.quota = quota;
	}

	public InquiryDTO getInquiry() {
		return inquiry;
	}

	public void setInquiry(InquiryDTO inquiry) {
		this.inquiry = inquiry;
	}

	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}

	public InsuranceDTO getInsurance() {
		return insurance;
	}

	public void setInsurance(InsuranceDTO insurance) {
		this.insurance = insurance;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getTciPrem() {
		return tciPrem;
	}

	public void setTciPrem(String tciPrem) {
		this.tciPrem = tciPrem;
	}

	public String getVciPrem() {
		return vciPrem;
	}

	public void setVciPrem(String vciPrem) {
		this.vciPrem = vciPrem;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderListDTO [quota=" + quota + ", inquiry=" + inquiry + ", order=" + order + ", insurance=" + insurance + ", plateNo=" + plateNo
				+ ", tciPrem=" + tciPrem + ", vciPrem=" + vciPrem + ", tax=" + tax + "]";
	}
}
