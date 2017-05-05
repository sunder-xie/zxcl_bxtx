package com.zxcl.webapp.dto.webdto;

public class InitialDTO {
	
	/**
	 * 省级编码
	 */
	private String provinceCode;
	/**
	 * 城市编码
	 */
	private String cityCode;
	/**
	 * 新车标记
	 */
	private int newCarSign;

	/**
	 * 车牌
	 */
	private String plateNo;
	
	/**
	 * 车主姓名
	 */
	private String ownerName;

//	public String getInquiryId() {
//		return inquiryId;
//	}
//
//	public void setInquiryId(String inquiryId) {
//		this.inquiryId = inquiryId;
//	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getNewCarSign() {
		return newCarSign;
	}

	public void setNewCarSign(int newCarSign) {
		this.newCarSign = newCarSign;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}
