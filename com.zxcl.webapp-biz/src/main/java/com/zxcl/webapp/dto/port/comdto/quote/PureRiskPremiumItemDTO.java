package com.zxcl.webapp.dto.port.comdto.quote;

/**
 * 纯风险保费
 * @author zx
 *
 */
public class PureRiskPremiumItemDTO {
	
	/**
	 * 天平：行业车型编码
	 */
	private String modelCode;
	
	/**
	 * 天平：纯风险保费
	 */
	private String pureRiskPremium;
	
	/**
	 * 天平：纯风险保费标志
	 */
	private String pureRiskPremiumFlag;

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getPureRiskPremium() {
		return pureRiskPremium;
	}

	public void setPureRiskPremium(String pureRiskPremium) {
		this.pureRiskPremium = pureRiskPremium;
	}

	public String getPureRiskPremiumFlag() {
		return pureRiskPremiumFlag;
	}

	public void setPureRiskPremiumFlag(String pureRiskPremiumFlag) {
		this.pureRiskPremiumFlag = pureRiskPremiumFlag;
	}
	
}
