package com.zxcl.webapp.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.zxcl.webapp.dto.port.comdto.AttestationDTO;
import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**
 * 
 * 
 * @author 5555
 *
 */
public class QuotaDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	public QuotaDTO() {
		super();
	}

	public QuotaDTO(String quotaId) {
		super();
		this.quotaId = quotaId;
	}

	public QuotaDTO(String quotaId, InquiryDTO inquiry, InsuranceDTO insurance, MicroDTO micro) {
		super();
		this.quotaId = quotaId;
		this.inquiry = inquiry;
		this.insurance = insurance;
		this.micro = micro;
	}

	private String orderId;
	
	private String quotaId;

	private InquiryDTO inquiry;

	private InsuranceDTO insurance;

	private MicroDTO micro;

	/**
	 * 交强险
	 */
	private BigDecimal TCIPremTax;
	/**
	 * 商业险
	 */
	private BigDecimal VCIPremTax;

	/**
	 * 车船税
	 */
	private BigDecimal vehicleTax;

	/**
	 * 商业险和交强险总金额
	 */
	private BigDecimal tciAndTax;
	
	/**
	 * 商业险,交强险和车船税总金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 报价单状态
	 */
	private String status;
	/**
	 * 报价单状态
	 */
	private String statusName;
	/**
	 * 保险公司
	 */
	private String insCompanyName;
	/**
	 * 报价类型 A自动 M人工报价
	 */
	private String quotaType;
	
	/**
	 * 人工报价用（
	 */
	private String showType;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	private String orderStatusName;
	
	/**
	 * 折扣
	 */
	private BigDecimal discount;
	
	/**
	 * 出险次数
	 */
	private int lastYearClaimNum;
	
	/**
	 * 报价结果警告信息，json字符串.
	 */
	private String warns;
	
	/**
	 * 富德：业务号
	 */
	private String businessNo;
	
	
	private AttestationDTO attestationBase;
	
	public String getBusinessNo() {
		return businessNo;
	}

	public AttestationDTO getAttestationBase() {
		return attestationBase;
	}

	public void setAttestationBase(AttestationDTO attestationBase) {
		this.attestationBase = attestationBase;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	// 支付页面的交强险（含税）
	public String getTciAndTax() {
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal mon = new BigDecimal(0.00);
		if (null != TCIPremTax) {
			mon = mon.add(TCIPremTax);
		}
		if (null != vehicleTax) {
			mon = mon.add(vehicleTax);
		}
		return df.format(mon);
	}

	// 总金额
	public String getTotalAmount() {
		DecimalFormat df = new DecimalFormat("0.00");
		if(this.totalAmount != null){
			return df.format(totalAmount);
		}
		BigDecimal total = new BigDecimal("0");
		if (null != TCIPremTax) {
			total = total.add(TCIPremTax);
		}
		if (null != vehicleTax) {
			total = total.add(vehicleTax);
		}
		if (null != VCIPremTax) {
			total = total.add(VCIPremTax);
		}
		this.totalAmount = total;
		return df.format(total);
	}

	public String getQuotaType() {
		return quotaType;
	}

	public void setQuotaType(String quotaType) {
		this.quotaType = quotaType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTciAndTax(BigDecimal tciAndTax) {
		this.tciAndTax = tciAndTax;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getInsCompanyName() {
		return insCompanyName;
	}

	public void setInsCompanyName(String insCompanyName) {
		this.insCompanyName = insCompanyName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public BigDecimal getTotalAmountBig() {
		BigDecimal total = new BigDecimal("0");
		if (null != TCIPremTax) {
			total = total.add(TCIPremTax);
		}
		if (null != vehicleTax) {
			total = total.add(vehicleTax);
		}
		if (null != VCIPremTax) {
			total = total.add(VCIPremTax);
		}
		return total;
	}
	
	
	public String getOrderId() {
		return orderId;
	}

	public String getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}

	public InquiryDTO getInquiry() {
		return inquiry;
	}

	public void setInquiry(InquiryDTO inquiry) {
		this.inquiry = inquiry;
	}

	public InsuranceDTO getInsurance() {
		return insurance;
	}

	public void setInsurance(InsuranceDTO insurance) {
		this.insurance = insurance;
	}

	public MicroDTO getMicro() {
		return micro;
	}

	public void setMicro(MicroDTO micro) {
		this.micro = micro;
	}

	public BigDecimal getTCIPremTax() {
		return null != TCIPremTax ? TCIPremTax : new BigDecimal(0.00);
	}

	public void setTCIPremTax(BigDecimal tCIPremTax) {
		TCIPremTax = tCIPremTax;
	}

	public BigDecimal getVCIPremTax() {
		return null != VCIPremTax ? VCIPremTax : new BigDecimal(0.00);
	}

	public void setVCIPremTax(BigDecimal vCIPremTax) {
		VCIPremTax = vCIPremTax;
	}

	public BigDecimal getVehicleTax() {
		return null != vehicleTax ? vehicleTax : new BigDecimal(0.00);
	}

	public void setVehicleTax(BigDecimal vehicleTax) {
		this.vehicleTax = vehicleTax;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public int getLastYearClaimNum() {
		return lastYearClaimNum;
	}

	public void setLastYearClaimNum(int lastYearClaimNum) {
		this.lastYearClaimNum = lastYearClaimNum;
	}

	public String getWarns() {
		return warns;
	}

	public void setWarns(String warns) {
		this.warns = warns;
	}
	
}
