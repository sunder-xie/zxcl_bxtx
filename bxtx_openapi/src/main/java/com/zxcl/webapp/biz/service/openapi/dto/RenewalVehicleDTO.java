package com.zxcl.webapp.biz.service.openapi.dto;

import java.util.List;

public class RenewalVehicleDTO {
	/**
	 * 车牌号
	 */
	private String plateNo;
	/**
	 * 发动机号
	 */
	private String engNo;
	/**
	 * 车架号
	 */
	private String frmNo;
	/**
	 * 初登年月:年月日(20130201)
	 */
	private String fstRegYm;
	/**
	 * 车型名称
	 */
	private String modelNme;
	/**
	 * 车主名称
	 */
	private String ownerNme;
	/**
	 * 身份证号
	 */
	private String certfCde;
	/**
	 * 商业保险止期
	 */
	private String vciInsureEnd;
	/**
	 * 交强保险止期
	 */
	private String tciInsureEnd;
	
	private List<CvrgDTO> cvrgList;

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getEngNo() {
		return engNo;
	}

	public void setEngNo(String engNo) {
		this.engNo = engNo;
	}

	public String getFrmNo() {
		return frmNo;
	}

	public void setFrmNo(String frmNo) {
		this.frmNo = frmNo;
	}

	public String getFstRegYm() {
		return fstRegYm;
	}

	public void setFstRegYm(String fstRegYm) {
		this.fstRegYm = fstRegYm;
	}

	public String getModelNme() {
		return modelNme;
	}

	public void setModelNme(String modelNme) {
		this.modelNme = modelNme;
	}

	public String getOwnerNme() {
		return ownerNme;
	}

	public void setOwnerNme(String ownerNme) {
		this.ownerNme = ownerNme;
	}

	public String getCertfCde() {
		return certfCde;
	}

	public void setCertfCde(String certfCde) {
		this.certfCde = certfCde;
	}

	public String getVciInsureEnd() {
		return vciInsureEnd;
	}

	public void setVciInsureEnd(String vciInsureEnd) {
		this.vciInsureEnd = vciInsureEnd;
	}

	public String getTciInsureEnd() {
		return tciInsureEnd;
	}

	public void setTciInsureEnd(String tciInsureEnd) {
		this.tciInsureEnd = tciInsureEnd;
	}

	public List<CvrgDTO> getCvrgList() {
		return cvrgList;
	}

	public void setCvrgList(List<CvrgDTO> cvrgList) {
		this.cvrgList = cvrgList;
	}
	
}
