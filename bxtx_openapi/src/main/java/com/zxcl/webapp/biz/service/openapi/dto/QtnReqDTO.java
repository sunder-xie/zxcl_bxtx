package com.zxcl.webapp.biz.service.openapi.dto;

import java.util.List;

public class QtnReqDTO {

	private QtnBaseDTO qtnBase;
	private QtnVhlDTO vehicle;
	private List<CvrgDTO> cvrgList;
	private QtnVhlOwnerDTO vehicleOwner;
	public QtnBaseDTO getQtnBase() {
		return qtnBase;
	}
	public void setQtnBase(QtnBaseDTO qtnBase) {
		this.qtnBase = qtnBase;
	}
	public QtnVhlDTO getVehicle() {
		return vehicle;
	}
	public void setVehicle(QtnVhlDTO vehicle) {
		this.vehicle = vehicle;
	}
	public List<CvrgDTO> getCvrgList() {
		return cvrgList;
	}
	public void setCvrgList(List<CvrgDTO> cvrgList) {
		this.cvrgList = cvrgList;
	}
	public QtnVhlOwnerDTO getVehicleOwner() {
		return vehicleOwner;
	}
	public void setVehicleOwner(QtnVhlOwnerDTO vehicleOwner) {
		this.vehicleOwner = vehicleOwner;
	}
}
