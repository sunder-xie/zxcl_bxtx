package com.zxcl.webapp.dto.rmi.intf.quota.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.zxcl.webapp.dto.rmi.intf.common.CommonDTO;

/**
 * 报价成功后返回的信息
 * @author zxj
 * @date 2016年7月13日
 * @description 
 */
public class QuotaReturnDTO extends CommonDTO implements Serializable {
	
	
	
	private static final long serialVersionUID = -2817990875101649731L;

	public QuotaReturnDTO() {
		super();
	}

	public QuotaReturnDTO(String errorCode, String errorMessages,String insId) {
		super();
		this.insId = insId;
		this.errorCode = errorCode;
		this.errorMessages = errorMessages;
	}

	public QuotaReturnDTO(String errorCode, String errorMessages,String insId,
			String userName) {
		super();
		this.insId = insId;
		this.errorCode = errorCode;
		this.errorMessages = errorMessages;
		this.userName = userName;
	}

	/**
	 * 保险公司ID
	 */
	private String insId;
	/**
	 * 返回的code
	 */
	private String errorCode;
	/**
	 * 返回的信息
	 */
	private String errorMessages;

	/**
	 * 报价单号
	 */
	private String quotaId;

	/**
	 * 交强险费用
	 */
	private BigDecimal premTCITax;

	/**
	 * 商业险费用
	 */
	private BigDecimal premVCITax;

	/**
	 * 车船税费用
	 */
	private BigDecimal vehicleTax;
	
	/**
	 * 车船税计算是否成功
	 */
	private boolean calcSuccess;

	/**
	 * 报价成功后的所有险种
	 */
	private List<CoverageItemDTO> coverageItems;

	private Integer vciReInsure;
	
	private Integer tciReInsure;
	
	/**
	 * 总费用
	 */
	private BigDecimal totalCost;
	
	/**
	 * 重复投保信息
	 */
	private String reInsureInfo;
	
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
	 * 用户名
	 */
	private String userName;
	
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
	
	private BigDecimal taxCurrentYear;
	private BigDecimal taxLastYear;
	private BigDecimal taxOverdue;
	private String taxBeginDate;
	private String taxEndDate;
	
	private String vciReInsureSign;
	private String tciReInsureSign;
	private String vciReInsureBeginDate;
	private String vciReInsureEndDate;
	private String tciReInsureBeginDate;
	private String tciReInsureEndDate;
	
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
	
    /**
     * 是否能转人工报价，1-能，非1-不能
     */
    private String manualQuotn;
	
	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(String errorMessages) {
		this.errorMessages = errorMessages;
	}

	public String getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}

	public BigDecimal getPremTCITax() {
		return premTCITax;
	}

	public void setPremTCITax(BigDecimal premTCITax) {
		this.premTCITax = premTCITax;
	}

	public BigDecimal getPremVCITax() {
		return premVCITax;
	}

	public void setPremVCITax(BigDecimal premVCITax) {
		this.premVCITax = premVCITax;
	}

	public BigDecimal getVehicleTax() {
		return vehicleTax;
	}

	public void setVehicleTax(BigDecimal vehicleTax) {
		this.vehicleTax = vehicleTax;
	}

	public List<CoverageItemDTO> getCoverageItems() {
		return coverageItems;
	}

	public void setCoverageItems(List<CoverageItemDTO> coverageItems) {
		this.coverageItems = coverageItems;
	}

	public Integer getVciReInsure() {
		return vciReInsure==null?0:vciReInsure;
	}

	public void setVciReInsure(Integer vciReInsure) {
		this.vciReInsure = vciReInsure;
	}

	public Integer getTciReInsure() {
		return tciReInsure==null?0:tciReInsure;
	}

	public void setTciReInsure(Integer tciReInsure) {
		this.tciReInsure = tciReInsure;
	}

	public BigDecimal getTotalCost() {
		if(null==totalCost){
			BigDecimal mon=new BigDecimal(0);
			if(null!=premTCITax){
				mon=mon.add(premTCITax);
			}
			if(null!=premVCITax){
				mon=mon.add(premVCITax);
			}
			if(null!=vehicleTax){
				mon=mon.add(vehicleTax);
			}
			return mon;
		}
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public boolean isCalcSuccess() {
		return calcSuccess;
	}

	public void setCalcSuccess(boolean calcSuccess) {
		this.calcSuccess = calcSuccess;
	}

	public String getReInsureInfo() {
		return reInsureInfo;
	}

	public void setReInsureInfo(String reInsureInfo) {
		this.reInsureInfo = reInsureInfo;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public BigDecimal getTaxCurrentYear() {
		return taxCurrentYear;
	}

	public void setTaxCurrentYear(BigDecimal taxCurrentYear) {
		this.taxCurrentYear = taxCurrentYear;
	}

	public BigDecimal getTaxLastYear() {
		return taxLastYear;
	}

	public void setTaxLastYear(BigDecimal taxLastYear) {
		this.taxLastYear = taxLastYear;
	}

	public BigDecimal getTaxOverdue() {
		return taxOverdue;
	}

	public void setTaxOverdue(BigDecimal taxOverdue) {
		this.taxOverdue = taxOverdue;
	}

	public String getTaxBeginDate() {
		return taxBeginDate;
	}

	public void setTaxBeginDate(String taxBeginDate) {
		this.taxBeginDate = taxBeginDate;
	}

	public String getTaxEndDate() {
		return taxEndDate;
	}

	public void setTaxEndDate(String taxEndDate) {
		this.taxEndDate = taxEndDate;
	}

	public String getVciReInsureSign() {
		return vciReInsureSign;
	}

	public void setVciReInsureSign(String vciReInsureSign) {
		this.vciReInsureSign = vciReInsureSign;
	}

	public String getTciReInsureSign() {
		return tciReInsureSign;
	}

	public void setTciReInsureSign(String tciReInsureSign) {
		this.tciReInsureSign = tciReInsureSign;
	}

	public String getVciReInsureBeginDate() {
		return vciReInsureBeginDate;
	}

	public void setVciReInsureBeginDate(String vciReInsureBeginDate) {
		this.vciReInsureBeginDate = vciReInsureBeginDate;
	}

	public String getVciReInsureEndDate() {
		return vciReInsureEndDate;
	}

	public void setVciReInsureEndDate(String vciReInsureEndDate) {
		this.vciReInsureEndDate = vciReInsureEndDate;
	}

	public String getTciReInsureBeginDate() {
		return tciReInsureBeginDate;
	}

	public void setTciReInsureBeginDate(String tciReInsureBeginDate) {
		this.tciReInsureBeginDate = tciReInsureBeginDate;
	}

	public String getTciReInsureEndDate() {
		return tciReInsureEndDate;
	}

	public void setTciReInsureEndDate(String tciReInsureEndDate) {
		this.tciReInsureEndDate = tciReInsureEndDate;
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

	public String getManualQuotn() {
		return manualQuotn;
	}

	public void setManualQuotn(String manualQuotn) {
		this.manualQuotn = manualQuotn;
	}

	@Override
	public String toString() {
		return "QuotaReturnDTO [insId=" + insId + ", errorCode=" + errorCode
				+ ", errorMessages=" + errorMessages + ", quotaId=" + quotaId
				+ ", premTCITax=" + premTCITax + ", premVCITax=" + premVCITax
				+ ", vehicleTax=" + vehicleTax + ", calcSuccess=" + calcSuccess
				+ ", coverageItems=" + coverageItems + ", vciReInsure="
				+ vciReInsure + ", tciReInsure=" + tciReInsure + ", totalCost="
				+ totalCost + ", reInsureInfo=" + reInsureInfo
				+ ", carCheckStatus=" + carCheckStatus + ", lastYearClaimNum="
				+ lastYearClaimNum + ", discount=" + discount + ", userName="
				+ userName + ", isOrNoUpdateStartDateSign="
				+ isOrNoUpdateStartDateSign + ", ticStartQuotaDate="
				+ ticStartQuotaDate + ", vicStartQuotaDate="
				+ vicStartQuotaDate + ", taxCurrentYear=" + taxCurrentYear
				+ ", taxLastYear=" + taxLastYear + ", taxOverdue=" + taxOverdue
				+ ", taxBeginDate=" + taxBeginDate + ", taxEndDate="
				+ taxEndDate + ", vciReInsureSign=" + vciReInsureSign
				+ ", tciReInsureSign=" + tciReInsureSign
				+ ", vciReInsureBeginDate=" + vciReInsureBeginDate
				+ ", vciReInsureEndDate=" + vciReInsureEndDate
				+ ", tciReInsureBeginDate=" + tciReInsureBeginDate
				+ ", tciReInsureEndDate=" + tciReInsureEndDate
				+ ", isAllowReQuote=" + isAllowReQuote + ", warns=" + warns
				+ ", manualQuotn=" + manualQuotn + "]";
	}
}
