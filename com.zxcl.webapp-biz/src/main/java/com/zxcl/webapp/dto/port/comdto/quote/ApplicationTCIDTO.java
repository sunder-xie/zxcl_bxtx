package com.zxcl.webapp.dto.port.comdto.quote;

/**
 * 投保单信息（交强）      发送
 * @author zx
 *
 */
public class ApplicationTCIDTO {
	
	/**
	 * 锦泰：产品编号
	 */
	private String prodNo;
	
	/**
	 * 锦泰：签单日期（格式：精确到天YYYYMMDD）
	 */
	private String billDate;
	
	/**
	 * 锦泰：起保日期（格式：精确到分钟yyyyMMddHHmm）
	 * 天平：起保日期（EffectiveDate：格式：精确到日）
	 */
	private String effectiveDate;
	
	/**
	 * 锦泰：终保日期（格式：精确到分钟yyyyMMddHHmm）
	 * 天平：终保日期（ExpireDate：格式：精确到日）
	 */
	private String expireDate;
	
	/**
	 * 锦泰：期限描述信息
	 */
	private String periodDesc;
	
	/**
	 * 天平：上年保单号
	 */
	private String lastPolicyNo;
	
	/**
	 * 天平：险种
	 */
	private String planCode;

	public String getProdNo() {
		return prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getPeriodDesc() {
		return periodDesc;
	}

	public void setPeriodDesc(String periodDesc) {
		this.periodDesc = periodDesc;
	}

	public String getLastPolicyNo() {
		return lastPolicyNo;
	}

	public void setLastPolicyNo(String lastPolicyNo) {
		this.lastPolicyNo = lastPolicyNo;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	
	
	
}

