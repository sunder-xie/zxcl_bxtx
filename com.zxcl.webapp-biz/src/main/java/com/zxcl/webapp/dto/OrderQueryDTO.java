package com.zxcl.webapp.dto;

import java.util.Date;

import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**
 * 订单查询
 * 
 * @author 5555
 *
 */
public class OrderQueryDTO extends BaseDTO {
	@Override
	public String toString() {
		return "OrderQueryDTO [inquiry=" + inquiry + ", quota=" + quota
				+ ", insurance=" + insurance + ", order=" + order + ", micro="
				+ micro + ", status=" + status + ", queryDate=" + queryDate
				+ ", queryStage=" + queryStage + "]";
	}

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
	 * 小薇账户
	 */
	private MicroDTO micro;

	/**
	 * 状态:有效,无效
	 */
	private String status;

	/**
	 * 查询日期
	 */
	private Date queryDate;

	/**
	 * 订单状态
	 */
	private String queryStage;

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

	public MicroDTO getMicro() {
		return micro;
	}

	public void setMicro(MicroDTO micro) {
		this.micro = micro;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public String getQueryStage() {
		return queryStage;
	}

	public void setQueryStage(String queryStage) {
		this.queryStage = queryStage;
	}
}
