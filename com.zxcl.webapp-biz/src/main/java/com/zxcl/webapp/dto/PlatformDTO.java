package com.zxcl.webapp.dto;

import java.math.BigDecimal;

/**
 * 平台的基础信息
 * 
 * @author 5555
 *
 */
public class PlatformDTO {

	/**
	 * 平台标识信息
	 */
	private String codeClass;
	/**
	 * 平台名称
	 */
	private String name;
	/**
	 * 平台编码
	 */
	private String code;
	
	private String pcode;
	/**
	 * 是否默认
	 */
	private String isDefault;
	/**
	 * 
	 */
	private String orderByID;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 保额
	 */
	private BigDecimal sumLimit;
	
	/**
	 * 备注
	 */
	
	
	private String remark;

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

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

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderByID() {
		return orderByID;
	}

	public void setOrderByID(String orderByID) {
		this.orderByID = orderByID;
	}

	/**
	 * 险种的金额
	 * 
	 * @return
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSumLimit() {
		return sumLimit;
	}

	public void setSumLimit(BigDecimal sumLimit) {
		this.sumLimit = sumLimit;
	}
}
