package com.zxcl.webapp.dto;

/**
 * 平安综合查询返回
 * @author zxj
 * @date 2016年11月9日
 * @description 
 */
public class PaFindResultDTO {
	private String productCode;//TCI:交强  VCI:商业
	private String applyNo;//投保单号
	private String plyNo;//保单号
	private String totalActualPremium;//保费
	private String totalTaxMoney;//车船税(只有productCode=TCI才有)
	
	/**
	 * 平安爬虫:B1待审核 (人工审核);B2待缴费(核保通过) ;B5已承保
	 */
	private String applyStatus;//投保状态(保险公司自己的状态)
	
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getPlyNo() {
		return plyNo;
	}
	public void setPlyNo(String plyNo) {
		this.plyNo = plyNo;
	}
	public String getTotalActualPremium() {
		return totalActualPremium;
	}
	public void setTotalActualPremium(String totalActualPremium) {
		this.totalActualPremium = totalActualPremium;
	}
	public String getTotalTaxMoney() {
		return totalTaxMoney;
	}
	public void setTotalTaxMoney(String totalTaxMoney) {
		this.totalTaxMoney = totalTaxMoney;
	}
	
}
