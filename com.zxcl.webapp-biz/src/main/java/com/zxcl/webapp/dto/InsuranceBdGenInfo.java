package com.zxcl.webapp.dto;

/**
 * 回写业管系统表信息
 * @author xiaoxi
 *
 */
public class InsuranceBdGenInfo {

	
	/**
	 * uuid
	 */
	private String id;
	
	/**
	 * 保险公司编码
	 */
	private String insId;
	
	/**
	 * 保单号
	 */
	private String bdNo;
	
	/**
	 * 保单类型 1:交强,0商业
	 */
	private String bdType;
	
	/**
	 * 业管代理公司代码
	 */
	private String compCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getBdNo() {
		return bdNo;
	}

	public void setBdNo(String bdNo) {
		this.bdNo = bdNo;
	}

	public String getBdType() {
		return bdType;
	}

	public void setBdType(String bdType) {
		this.bdType = bdType;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	@Override
	public String toString() {
		return "InsuranceBdGenInfo [id=" + id + ", insId=" + insId + ", bdNo="
				+ bdNo + ", bdType=" + bdType + ", compCode=" + compCode + "]";
	}
}
