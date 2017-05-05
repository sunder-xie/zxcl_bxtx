package com.zxcl.webapp.dto.rmi.intf.common;

/**
 * 保险公司
 */
public class InsuranceDTO extends CommonDTO {

	private static final long serialVersionUID = 7167263187105053982L;

	/**
	 * 主键
	 */
	private String insId;
	
	/**
	 * 配置id
	 * configId
	 */
	private String configId;
	/**
	 * 名称
	 */
	private String insName;
	
	/**
	 * 保险公司短名称
	 */
	private String insPetName;

	/**
	 * 保险公司类型
	 * 1-接口，2-模拟
	 */
	private String insType;
	
	/**
	 * 1有效 0无效 2维护中
	 */
	private String status;
	
	/**
	 * 报价类型
	 */
	private String quotnType;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public InsuranceDTO() {
		super();
	}

	public InsuranceDTO(String insId) {
		super();
		this.insId = insId;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getInsName() {
		return insName;
	}

	public void setInsName(String insName) {
		this.insName = insName;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	
	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}

	public String getInsPetName() {
		return insPetName;
	}

	public void setInsPetName(String insPetName) {
		this.insPetName = insPetName;
	}

	public String getQuotnType() {
		return quotnType;
	}

	public void setQuotnType(String quotnType) {
		this.quotnType = quotnType;
	}

	@Override
	public String toString() {
		return "InsuranceDTO [insId=" + insId + ", configId=" + configId
				+ ", insName=" + insName + ", insPetName=" + insPetName
				+ ", insType=" + insType + ", status=" + status
				+ ", quotnType=" + quotnType + "]";
	}
}
