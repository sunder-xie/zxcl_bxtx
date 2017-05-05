package com.zxcl.webapp.dto;

/**
 * 人工报价控制信息
 * @author zx
 *
 */
public class QuotnConfigDTO {
	
	/**
	 * 团队号
	 */
	private String teamId;
	
	/**
	 * 业务类型
	 */
	private String serviceType;
	
	/**
	 * 保险公司ID
	 */
	private String insId;
	
	/**
	 * 人工，自动
	 */
	private String quotnType;
	
	/**
	 * 提示如何缴费
	 */
	private String payTipMessage;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getQuotnType() {
		return quotnType;
	}

	public void setQuotnType(String quotnType) {
		this.quotnType = quotnType;
	}

	public String getPayTipMessage() {
		return payTipMessage;
	}

	public void setPayTipMessage(String payTipMessage) {
		this.payTipMessage = payTipMessage;
	}

	@Override
	public String toString() {
		return "QuotnConfigDTO [teamId=" + teamId + ", serviceType="
				+ serviceType + ", insId=" + insId + ", quotnType=" + quotnType
				+ ", payTipMessage=" + payTipMessage + "]";
	}
}
