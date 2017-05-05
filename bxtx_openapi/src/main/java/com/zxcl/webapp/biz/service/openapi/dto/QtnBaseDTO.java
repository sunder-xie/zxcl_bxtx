package com.zxcl.webapp.biz.service.openapi.dto;

public class QtnBaseDTO {

	/**
	 * 是否投保交强险 1/0
	 */
	private String tciSign;
	
	/**
	 * 是否投保商业险 1/0
	 */
	private String vciSign;
	
	/**
	 * 保险公司代码
	 */
	private String insCompanyCode;
	
	/**
	 * 保险公司系统的出单工号
	 */
	private String insUserName;
	
	/**
	 * 保险公司系统的出单密码
	 */
	private String insUserPwd;
	
	/**
	 * 交强险起期
	 * YYYY-MM-DD
	 */
	private String tciStartDate;
	
	private String tciEndDate;
	
	private String vciStartDate;
	
	private String vciEndDate;

	public String getTciSign() {
		return tciSign;
	}

	public void setTciSign(String tciSign) {
		this.tciSign = tciSign;
	}

	public String getVciSign() {
		return vciSign;
	}

	public void setVciSign(String vciSign) {
		this.vciSign = vciSign;
	}

	public String getInsUserName() {
		return insUserName;
	}

	public void setInsUserName(String insUserName) {
		this.insUserName = insUserName;
	}

	public String getInsUserPwd() {
		return insUserPwd;
	}

	public void setInsUserPwd(String insUserPwd) {
		this.insUserPwd = insUserPwd;
	}

	public String getTciStartDate() {
		return tciStartDate;
	}

	public void setTciStartDate(String tciStartDate) {
		this.tciStartDate = tciStartDate;
	}

	public String getTciEndDate() {
		return tciEndDate;
	}

	public void setTciEndDate(String tciEndDate) {
		this.tciEndDate = tciEndDate;
	}

	public String getVciStartDate() {
		return vciStartDate;
	}

	public void setVciStartDate(String vciStartDate) {
		this.vciStartDate = vciStartDate;
	}

	public String getVciEndDate() {
		return vciEndDate;
	}

	public void setVciEndDate(String vciEndDate) {
		this.vciEndDate = vciEndDate;
	}

	public String getInsCompanyCode() {
		return insCompanyCode;
	}

	public void setInsCompanyCode(String insCompanyCode) {
		this.insCompanyCode = insCompanyCode;
	}
	
}
