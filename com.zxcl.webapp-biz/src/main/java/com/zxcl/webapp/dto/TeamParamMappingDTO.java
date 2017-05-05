package com.zxcl.webapp.dto;

/**
 * 
 * @author zx
 *
 */
public class TeamParamMappingDTO {
	
	/**
	 * 保险公司配置信息ID
	 */
	private String configId;
	
	/**
	 * 团队ID
	 */
	private String teamId;
	
	/**
	 * 保险公司ID
	 */
	private String insId;
	
	/**
	 * 业务类型
	 */
	private String serviceType;
	
	/**
	 * 报价类型，A-自动，M-人工
	 */
	private String quotnType;
	
	/**
	 * 前端流程控制，1-报价；2-投保；3-支付
	 */
	private String flowControl;

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getQuotnType() {
		return quotnType;
	}

	public void setQuotnType(String quotnType) {
		this.quotnType = quotnType;
	}

	public String getFlowControl() {
		return flowControl;
	}

	public void setFlowControl(String flowControl) {
		this.flowControl = flowControl;
	}

	@Override
	public String toString() {
		return "TeamParamMappingDTO [configId=" + configId + ", teamId="
				+ teamId + ", insId=" + insId + ", serviceType=" + serviceType
				+ ", quotnType=" + quotnType + ", flowControl=" + flowControl
				+ "]";
	}
}
