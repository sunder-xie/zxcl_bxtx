package com.zxcl.webapp.dto;

/**
 * 保险公司配置信息DTO
 * @author zx
 *
 */
public class InsuranceCompanyConfigDTO {
	
	/**
	 * 键
	 */
	private String key;
	
	/**
	 * 值
	 */
	private String value;
	
	/**
	 * 备注
	 */
	private String remark;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
