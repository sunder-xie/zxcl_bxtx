package com.zxcl.webapp.dto;

/**
 * 有车型查询依赖关系的保险公司
 * @author zx
 *
 */
public class RelyInsuranceDTO {
	/**
	 * 保险公司ID
	 */
	private String insId;
	/**
	 * 被依赖的保险公司的ID
	 */
	private String relyInsId;
	public String getInsId() {
		return insId;
	}
	public void setInsId(String insId) {
		this.insId = insId;
	}
	public String getRelyInsId() {
		return relyInsId;
	}
	public void setRelyInsId(String relyInsId) {
		this.relyInsId = relyInsId;
	}
	@Override
	public String toString() {
		return "RelyInsuranceDTO [insId=" + insId + ", relyInsId=" + relyInsId
				+ "]";
	}
	
}
