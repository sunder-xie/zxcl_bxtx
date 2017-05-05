package com.zxcl.webapp.dto;

import java.math.BigDecimal;
import java.util.List;

import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**
 * 报价成功后，页面显示报价信息
 * 
 * @author 5555
 *
 */
public class QuotedDTO {
	
	
	
	/**
	 * 为报价结果支付提供订单，跟询价单信息
	 */
	private String orderId;
	
	private InquiryDTO inquiry;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public InquiryDTO getInquiry() {
		return inquiry;
	}

	public void setInquiry(InquiryDTO inquiry) {
		this.inquiry = inquiry;
	}

	
	
	/**
	 * 保险公司
	 */
	private InsuranceDTO insurance;

	/**
	 * 询价单号
	 */
	private String inquiryId;
	
	/**
	 * 报价的总的金额
	 */
	private BigDecimal amount;

	/**
	 * 报价单号
	 */
	private String quotaId;

	/**
	 *	消息
	 */
	private String msg;
	
	/**
	 * 报价类型 A自动 M人工报价
	 */
	private String quotaType; 
	/**
	 * 人工报价需要 0-金额为0 1-有金额
	 */
	private String showType;
	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 验车状态
	 */
	private int carCheckStatus;
	
	/**
	 * 出险次数
	 */
	private int lastYearClaimNum;
	
	/**
	 * 折扣
	 */
	private BigDecimal discount;
	
	/**
	 * 是否修改起保日期，1修改了，2是未修改
	 */
	private String isOrNoUpdateStartDateSign;
	
	/**
	 * 交强险起保日期
	 */
	private String ticStartQuotaDate;
	
	/**
	 * 商业险起保日期
	 */
	private String vicStartQuotaDate;
	
	/**
	 * 商业费率
	 */
	private String ratio;
	
	/**
	 * 人工报价任务信息
	 */
	private ManualQuotationTaskDTO manualQuotationTaskDTO;
	
	/**
	 * 默认为0，不允许  1 允许
	 * 
	 * 在某些异常的情况下，如网络异常。允许用户在界面上点击重新报价. 
	 * 允许用户重新报价依然要讲错误信息返回到前台，要把错误信息展示给用户看.
	 * 
	 */
	private int isAllowReQuote;
	
	/**
	 * 报价返回的提示信息.提示信息不影响用户投保。仅用于提示
	 */
	private List<String> warns;
	
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

	public String getQuotaType() {
		return quotaType;
	}

	public void setQuotaType(String quotaType) {
		this.quotaType = quotaType;
	}

	public InsuranceDTO getInsurance() {
		return insurance;
	}

	public void setInsurance(InsuranceDTO insurance) {
		this.insurance = insurance;
	}

	public String getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCarCheckStatus() {
		return carCheckStatus;
	}

	public void setCarCheckStatus(int carCheckStatus) {
		this.carCheckStatus = carCheckStatus;
	}

	public int getLastYearClaimNum() {
		return lastYearClaimNum;
	}

	public void setLastYearClaimNum(int lastYearClaimNum) {
		this.lastYearClaimNum = lastYearClaimNum;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getIsOrNoUpdateStartDateSign() {
		return isOrNoUpdateStartDateSign;
	}

	public void setIsOrNoUpdateStartDateSign(String isOrNoUpdateStartDateSign) {
		this.isOrNoUpdateStartDateSign = isOrNoUpdateStartDateSign;
	}

	public String getTicStartQuotaDate() {
		return ticStartQuotaDate;
	}

	public void setTicStartQuotaDate(String ticStartQuotaDate) {
		this.ticStartQuotaDate = ticStartQuotaDate;
	}

	public String getVicStartQuotaDate() {
		return vicStartQuotaDate;
	}

	public void setVicStartQuotaDate(String vicStartQuotaDate) {
		this.vicStartQuotaDate = vicStartQuotaDate;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public ManualQuotationTaskDTO getManualQuotationTaskDTO() {
		return manualQuotationTaskDTO;
	}

	public void setManualQuotationTaskDTO(ManualQuotationTaskDTO manualQuotationTaskDTO) {
		this.manualQuotationTaskDTO = manualQuotationTaskDTO;
	}

	public int getIsAllowReQuote() {
		return isAllowReQuote;
	}

	public void setIsAllowReQuote(int isAllowReQuote) {
		this.isAllowReQuote = isAllowReQuote;
	}

	public List<String> getWarns() {
		return warns;
	}

	public void setWarns(List<String> warns) {
		this.warns = warns;
	}

	@Override
	public String toString() {
		return "QuotedDTO [orderId=" + orderId + ", inquiry=" + inquiry
				+ ", insurance=" + insurance + ", inquiryId=" + inquiryId
				+ ", amount=" + amount + ", quotaId=" + quotaId + ", msg="
				+ msg + ", quotaType=" + quotaType + ", showType=" + showType
				+ ", orderStatus=" + orderStatus + ", carCheckStatus="
				+ carCheckStatus + ", lastYearClaimNum=" + lastYearClaimNum
				+ ", discount=" + discount + ", isOrNoUpdateStartDateSign="
				+ isOrNoUpdateStartDateSign + ", ticStartQuotaDate="
				+ ticStartQuotaDate + ", vicStartQuotaDate="
				+ vicStartQuotaDate + ", ratio=" + ratio
				+ ", manualQuotationTaskDTO=" + manualQuotationTaskDTO
				+ ", isAllowReQuote=" + isAllowReQuote + ", warns=" + warns
				+ "]";
	}

}
