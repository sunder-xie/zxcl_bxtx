package com.zxcl.webapp.dto;

import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;


/**
 * 减免税车型<br/>
 * 有些保险公司接口需要开具税务机关，因为各大公司要求不同，所以根据需要，自行拼接字符串，如（四川省成都市地方税务局）
 * @author xiaoxi
 *
 */
public class TaxReliefModelsDTO extends BaseDTO{

	private static final long serialVersionUID = 1261880563018363775L;

	/**
	 * 车型号码
	 */
	private String modelNumber;
	
	/**
	 * 初登日期  ，如："早于2010-05-05"  ，"大于2011-01-01"  ，如果是不做判断则为""
	 */
	private String fstReg;
	
	/**
	 * 缴税方式。减税、免税、全税
	 */
	private String taxMode;
	
	/**
	 * 减免税原因，能源减免
	 */
	private String reasonTaxRelief;
	
	/**
	 * 能源类型
	 */
	private String energyType;
	
	/**
	 * 减免税方案，比例减税、免税
	 */
	private String taxExemptionScheme;
	
	/**
	 * 减免税比例，1-100.
	 */
	private Integer taxReliefProportion;
	
	/**
	 * 减免税凭证号
	 */
	private String certificateNo;

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	
	

	public String getFstReg() {
		return fstReg;
	}

	public void setFstReg(String fstReg) {
		this.fstReg = fstReg;
	}

	public String getTaxMode() {
		return taxMode;
	}

	public void setTaxMode(String taxMode) {
		this.taxMode = taxMode;
	}

	public String getReasonTaxRelief() {
		return reasonTaxRelief;
	}

	public void setReasonTaxRelief(String reasonTaxRelief) {
		this.reasonTaxRelief = reasonTaxRelief;
	}

	public String getEnergyType() {
		return energyType;
	}

	public void setEnergyType(String energyType) {
		this.energyType = energyType;
	}

	public String getTaxExemptionScheme() {
		return taxExemptionScheme;
	}

	public void setTaxExemptionScheme(String taxExemptionScheme) {
		this.taxExemptionScheme = taxExemptionScheme;
	}

	public Integer getTaxReliefProportion() {
		return taxReliefProportion;
	}

	public void setTaxReliefProportion(Integer taxReliefProportion) {
		this.taxReliefProportion = taxReliefProportion;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	@Override
	public String toString() {
		return "TaxReliefModelsDTO [modelNumber=" + modelNumber
				+ ", fstReg=" + fstReg + ", taxMode=" + taxMode
				+ ", reasonTaxRelief=" + reasonTaxRelief + ", energyType="
				+ energyType + ", taxExemptionScheme=" + taxExemptionScheme
				+ ", taxReliefProportion=" + taxReliefProportion
				+ ", certificateNo=" + certificateNo + "]";
	}
	
	
}
