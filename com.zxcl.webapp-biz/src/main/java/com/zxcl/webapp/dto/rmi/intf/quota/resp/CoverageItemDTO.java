package com.zxcl.webapp.dto.rmi.intf.quota.resp;

import java.math.BigDecimal;

import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;

/**
 * 险种信息
 * 
 * @author 5555
 *
 */
public class CoverageItemDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 询价的基础信息
	 */
	private InquiryDTO inquiry;

	/**
	 * 险种代码
	 */
	private String code;

	/**
	 * 险种的名称
	 */
	private String name;

	/**
	 * 保额/限额(10,2)
	 */
	private BigDecimal sumLimit;

	/**
	 * 是否投了不计免赔:0-否,1-是
	 */
	private int noDduct;

	/**
	 * 玻璃类型
	 */
	private String glsType;

	/**
	 * 险种的金额
	 */
	private BigDecimal amount;

	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 险别 1-交强险 2-商业险
	 */
	private String insType;

	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}


	public InquiryDTO getInquiry() {
		return inquiry;
	}

	public void setInquiry(InquiryDTO inquiry) {
		this.inquiry = inquiry;
	}

	/**
	 * 保额/限额
	 */
	public BigDecimal getSumLimit() {
		return sumLimit;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 保额/限额
	 */
	public void setSumLimit(BigDecimal sumLimit) {
		this.sumLimit = sumLimit;
	}

	public int getNoDduct() {
		return noDduct;
	}

	public void setNoDduct(int noDduct) {
		this.noDduct = noDduct;
	}

	/**
	 * 玻璃类型
	 */
	public String getGlsType() {
		return glsType;
	}

	/**
	 * 玻璃类型
	 */
	public void setGlsType(String glsType) {
		this.glsType = glsType;
	}

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
}
