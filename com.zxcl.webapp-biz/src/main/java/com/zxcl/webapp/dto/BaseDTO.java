package com.zxcl.webapp.dto;

/**
 * 基础信息
 * @author zx
 *
 */
public class BaseDTO {
	
	/**
	 * 编码对象
	 */
	private String codeClass;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 父级编码
	 */
	private String parCode;
	
	/**
	 * 是否默认，设置默认字段
	 */
	private String isDefault;
	
	/**
	 * 排序编码
	 */
	private String orderById;
	
	/**
	 * 备注
	 */
	private String remark;

	public String getCodeClass() {
		return codeClass;
	}

	public void setCodeClass(String codeClass) {
		this.codeClass = codeClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParCode() {
		return parCode;
	}

	public void setParCode(String parCode) {
		this.parCode = parCode;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getOrderById() {
		return orderById;
	}

	public void setOrderById(String orderById) {
		this.orderById = orderById;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "BaseDTO [codeClass=" + codeClass + ", name=" + name + ", code="
				+ code + ", parCode=" + parCode + ", isDefault=" + isDefault
				+ ", orderById=" + orderById + ", remark=" + remark + "]";
	}
}
