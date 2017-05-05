package com.zxcl.webapp.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 险别资源信息
 * @author zx
 *
 */
public class ResourceVehicleCvrgDTO implements Serializable{
	
	private static final long serialVersionUID = 3585395483474618847L;
	
	/**
	 * 车牌号
	 */
	private String plateNo;
	/**
	 * 险别编码
	 */
	private String cvrgId;
	/**
	 * 险别保额
	 */
	private BigDecimal gvrgAmount;
	/**
	 * 不计免赔(0否,1是)
	 */
	private String excldDeductible;
	/**
	 * 玻璃类型，(1国产，2进口)
	 */
	private String glsType;
	
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public String getCvrgId() {
		return cvrgId;
	}
	public void setCvrgId(String cvrgId) {
		this.cvrgId = cvrgId;
	}
	public BigDecimal getGvrgAmount() {
		return gvrgAmount;
	}
	public void setGvrgAmount(BigDecimal gvrgAmount) {
		this.gvrgAmount = gvrgAmount;
	}
	public String getExcldDeductible() {
		return excldDeductible;
	}
	public void setExcldDeductible(String excldDeductible) {
		this.excldDeductible = excldDeductible;
	}
	@Override
	public String toString() {
		return "ResourceVehicleCvrgDTO [plateNo=" + plateNo + ", cvrgId="
				+ cvrgId + ", gvrgAmount=" + gvrgAmount + ", excldDeductible="
				+ excldDeductible + "]";
	}
	public String getGlsType() {
		return glsType;
	}
	public void setGlsType(String glsType) {
		this.glsType = glsType;
	}
}
