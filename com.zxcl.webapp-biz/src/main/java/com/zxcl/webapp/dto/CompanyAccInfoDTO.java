package com.zxcl.webapp.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author xiaoxi
 *
 */
public class CompanyAccInfoDTO {

	/**
	 * 公司代码
	 */
	private String compCode;
	/**
	 * 首签日期
	 */
	private Date firstSignDate;
	/**
	 * 协议开始日期
	 */
	private Date startDate;
	/**
	 * 协议结束日期
	 */
	private Date endDate;
	/**
	 * 1:正常 -1:冻结欠费等
	 */
	private int status;
	/**
	 * 预存款总额
	 */
	private BigDecimal expectedTotal;
	/**
	 * 预存余额
	 */
	private BigDecimal balance;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 预留
	 */
	private int reserve;
	/**
	 * 预留1
	 */
	private String reserve1;
	
	public String getCompCode() {
		return compCode;
	}
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	public Date getFirstSignDate() {
		return firstSignDate;
	}
	public void setFirstSignDate(Date firstSignDate) {
		this.firstSignDate = firstSignDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BigDecimal getExpectedTotal() {
		return expectedTotal;
	}
	public void setExpectedTotal(BigDecimal expectedTotal) {
		this.expectedTotal = expectedTotal;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	
	
}
