package com.zxcl.webapp.biz.service.openapi.dto;

public class QtnReInsureDTO {
	
	/**
	 * 是否商业重复投保 1/0
	 */
	private String vciReInsure;
	/**
	 * 是否重复投保
	 */
	private String tciReInsure;
	/**
	 * 商业重复投保起期
	 */
	private String vciBeginDate;
	/**
	 * 商业重复投保止期
	 */
	private String vciEndDate;
	private String tciBeginDate;
	private String tciEndDate;
	public String getVciReInsure() {
		return vciReInsure;
	}
	public void setVciReInsure(String vciReInsure) {
		this.vciReInsure = vciReInsure;
	}
	public String getTciReInsure() {
		return tciReInsure;
	}
	public void setTciReInsure(String tciReInsure) {
		this.tciReInsure = tciReInsure;
	}
	public String getVciBeginDate() {
		return vciBeginDate;
	}
	public void setVciBeginDate(String vciBeginDate) {
		this.vciBeginDate = vciBeginDate;
	}
	public String getVciEndDate() {
		return vciEndDate;
	}
	public void setVciEndDate(String vciEndDate) {
		this.vciEndDate = vciEndDate;
	}
	public String getTciBeginDate() {
		return tciBeginDate;
	}
	public void setTciBeginDate(String tciBeginDate) {
		this.tciBeginDate = tciBeginDate;
	}
	public String getTciEndDate() {
		return tciEndDate;
	}
	public void setTciEndDate(String tciEndDate) {
		this.tciEndDate = tciEndDate;
	}

}
