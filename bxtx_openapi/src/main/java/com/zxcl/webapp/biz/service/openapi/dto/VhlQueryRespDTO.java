package com.zxcl.webapp.biz.service.openapi.dto;

import java.util.List;

public class VhlQueryRespDTO {

	private String errorCode;
	
	private String errorMessage;
	
	private List<VhlModelDTO> vehicleModelList;

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

	public List<VhlModelDTO> getVehicleModelList() {
		return vehicleModelList;
	}

	public void setVehicleModelList(List<VhlModelDTO> vehicleModelList) {
		this.vehicleModelList = vehicleModelList;
	}
	
}
