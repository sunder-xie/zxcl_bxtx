package com.zxcl.webapp.dto.openapi;

public class ApiAppQtnConfigDTO {

	/**
	 * 第三方应用标识
	 */
	private String appId;
	
	/**
	 * 允许访问的保险公司标识
	 */
	private String insId;
	
	/**
	 * 报价使用的保险公司工号
	 * 
	 * 通过该工号到 t_ins_company_config 查找工号对应的报价参数
	 */
	private String insUserName;
	
	private String status;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getInsUserName() {
		return insUserName;
	}

	public void setInsUserName(String insUserName) {
		this.insUserName = insUserName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}
	
}
