package com.zxcl.webapp.biz.service.openapi.dto;

public class CvrgDTO {

	/**
	 * 险别代码
	 */
	private String coverageCode;
	/**
	 * 保额
	 */
	private String sumLimit;
	/**
	 * 是否不计免赔 0/1
	 */
	private String noDduct;
	/**
	 * 玻璃类型 1-国产  2-进口
	 */
	private String glsType;
	
	/**
	 * 保费
	 */
	private String prm;
	
	public String getCoverageCode() {
		return coverageCode;
	}
	public void setCoverageCode(String coverageCode) {
		this.coverageCode = coverageCode;
	}
	public String getSumLimit() {
		return sumLimit;
	}
	public void setSumLimit(String sumLimit) {
		this.sumLimit = sumLimit;
	}
	public String getNoDduct() {
		return noDduct;
	}
	public void setNoDduct(String noDduct) {
		this.noDduct = noDduct;
	}
	public String getGlsType() {
		return glsType;
	}
	public void setGlsType(String glsType) {
		this.glsType = glsType;
	}
	public String getPrm() {
		return prm;
	}
	public void setPrm(String prm) {
		this.prm = prm;
	}
	
}
