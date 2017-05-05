package com.zxcl.webapp.biz.service.openapi.dto;

public class RenewalQueryByPlateReqDTO {

	private String plateNo;
	
	private String cityCode;

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
}
