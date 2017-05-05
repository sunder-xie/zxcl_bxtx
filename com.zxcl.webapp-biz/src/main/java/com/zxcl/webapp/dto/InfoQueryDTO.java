package com.zxcl.webapp.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.biz.util.DateUtils;
import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;

/**
 * 综合查询DTO
 * 
 * @author 5555
 *
 */
public class InfoQueryDTO extends BaseDTO implements Cloneable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String inquiryId;

	/**
	 * 车牌号
	 */
	private String plateNo;
	/**
	 * 车主名称
	 */
	private String ownerName;
	
	private String insId;
	
	/**
	 * 状态0-正常(默认) 1-暂存数据
	 */
	private String state="0";
	/**
	 * 状态中文说明
	 */
	private String stateName;
	
	/**
	 * 交强险
	 */
	private BigDecimal sumTCIPremTax;
	/**
	 * 商业险
	 */
	private BigDecimal sumVCIPremTax;

	/**
	 * 车船税
	 */
	private BigDecimal sumvehicleTax;
	/**
	 * 商业险,交强险和车船税总金额
	 */
	private BigDecimal sumTotalAmount;
	/**
	 *  报价类型 A自动 M人工报价
	 */
	private String quotaType;
	
	public String getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getQuotaType() {
		return quotaType;
	}

	public void setQuotaType(String quotaType) {
		this.quotaType = quotaType;
	}

	public BigDecimal getSumTCIPremTax() {
		return sumTCIPremTax;
	}

	public void setSumTCIPremTax(BigDecimal sumTCIPremTax) {
		this.sumTCIPremTax = sumTCIPremTax;
	}

	public BigDecimal getSumVCIPremTax() {
		return sumVCIPremTax;
	}

	public void setSumVCIPremTax(BigDecimal sumVCIPremTax) {
		this.sumVCIPremTax = sumVCIPremTax;
	}

	public BigDecimal getSumvehicleTax() {
		return sumvehicleTax;
	}

	public void setSumvehicleTax(BigDecimal sumvehicleTax) {
		this.sumvehicleTax = sumvehicleTax;
	}

	public String getSumTotalAmount() {
		// 总金额
			DecimalFormat df = new DecimalFormat("0.00");
			BigDecimal total = new BigDecimal("0");
			if (null != sumTCIPremTax) {
				total = total.add(sumTCIPremTax);
			}
			if (null != sumvehicleTax) {
				total = total.add(sumvehicleTax);
			}
			if (null != sumVCIPremTax) {
				total = total.add(sumVCIPremTax);
			}
			return df.format(total);
	}

	
}
