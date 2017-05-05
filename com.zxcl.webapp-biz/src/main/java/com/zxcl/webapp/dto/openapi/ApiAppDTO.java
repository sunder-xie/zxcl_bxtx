package com.zxcl.webapp.dto.openapi;

/**
 * 合作伙伴的应用定义(接口调用方,接口调用账号)
 * t_api_app;
 * 
 * @author wenchang001
 */
public class ApiAppDTO {

	/**
	 * 合作伙伴应用标识,我司分配
	 */
	private String appId;
	
	/**
	 * 秘钥,32位字符
	 */
	private String appKey;
	
	/**
	 * 合作伙伴应用名称,如果是代理人,通常写代理名称
	 */
	private String appName;
	
	/**
	 * 1/0
	 */
	private String status;
	
	/**
	 * 代理公司代码
	 */
	private String compCode;
	
	/**
	 * 1/0 第三方团队映射标记
	 */
	private String teamMappingSign;
	
	/**
	 * 保单上传;1:是,0:否
	 */
	private String policyUploadSign;
	
	/**
	 * 保单上传url
	 */
	private String policyUploadUrl;
	
	
	
	public String getPolicyUploadSign() {
		return policyUploadSign;
	}

	public String getPolicyUploadUrl() {
		return policyUploadUrl;
	}

	public void setPolicyUploadSign(String policyUploadSign) {
		this.policyUploadSign = policyUploadSign;
	}

	public void setPolicyUploadUrl(String policyUploadUrl) {
		this.policyUploadUrl = policyUploadUrl;
	}

	public String getTeamMappingSign() {
		return teamMappingSign;
	}

	public void setTeamMappingSign(String teamMappingSign) {
		this.teamMappingSign = teamMappingSign;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
