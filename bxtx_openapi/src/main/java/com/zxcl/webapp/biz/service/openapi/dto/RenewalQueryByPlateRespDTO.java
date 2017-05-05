package com.zxcl.webapp.biz.service.openapi.dto;


public class RenewalQueryByPlateRespDTO {
	private String errorCode;
	private String errorMessage;
	private RenewalVehicleDTO renewalVehicle;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public RenewalVehicleDTO getRenewalVehicle() {
		return renewalVehicle;
	}
	public void setRenewalVehicle(RenewalVehicleDTO renewalVehicle) {
		this.renewalVehicle = renewalVehicle;
	}
}
