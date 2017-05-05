package com.zxcl.webapp.dto;

import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;

public class ReportDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	private String inquiryId;

	private String quotaId;

	private String insId;

	private String orderId;

	private String microId;

	private String prem;

	private String microComm;

	private String agentComm;

	private Double microFee;

	private Double agentFee;

	private String plateNo;

	// 被保人
	private String appinsured;

	private String plyCrtTm;

	private String status;

	private double tax;

	private double tciPrem;

	private double vciPrem;

	private String agentId;

	private String teamId;

	private String classCode;

	private String insName;

	private String count;

	private String teamName;

	private String className;

	private String microName;

	private String agentName;

	private String carApplyNo;

	public String getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	public String getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMicroId() {
		return microId;
	}

	public void setMicroId(String microId) {
		this.microId = microId;
	}

	public String getPrem() {
		return prem;
	}

	public void setPrem(String prem) {
		this.prem = prem;
	}

	public String getMicroComm() {
		return microComm;
	}

	public void setMicroComm(String microComm) {
		this.microComm = microComm;
	}

	public String getAgentComm() {
		return agentComm;
	}

	public void setAgentComm(String agentComm) {
		this.agentComm = agentComm;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getAppinsured() {
		return appinsured;
	}

	public void setAppinsured(String appinsured) {
		this.appinsured = appinsured;
	}

	public String getPlyCrtTm() {
		return plyCrtTm;
	}

	public void setPlyCrtTm(String plyCrtTm) {
		this.plyCrtTm = plyCrtTm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTciPrem() {
		return tciPrem;
	}

	public void setTciPrem(double tciPrem) {
		this.tciPrem = tciPrem;
	}

	public double getVciPrem() {
		return vciPrem;
	}

	public void setVciPrem(double vciPrem) {
		this.vciPrem = vciPrem;
	}

	public Double getMicroFee() {
		return microFee;
	}

	public void setMicroFee(Double microFee) {
		this.microFee = microFee;
	}

	public Double getAgentFee() {
		return agentFee;
	}

	public void setAgentFee(Double agentFee) {
		this.agentFee = agentFee;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getInsName() {
		return insName;
	}

	public void setInsName(String insName) {
		this.insName = insName;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMicroName() {
		return microName;
	}

	public void setMicroName(String microName) {
		this.microName = microName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getCarApplyNo() {
		return carApplyNo;
	}

	public void setCarApplyNo(String carApplyNo) {
		this.carApplyNo = carApplyNo;
	}
}
