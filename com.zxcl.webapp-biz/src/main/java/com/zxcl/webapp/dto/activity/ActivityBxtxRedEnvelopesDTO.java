package com.zxcl.webapp.dto.activity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 保行天下活动红包记录
 * @author xiaoxi
 *
 */
public class ActivityBxtxRedEnvelopesDTO {

	
	private Integer id;
	
	/**
	 * 活动批次号
	 */
	private String activityBatchNo;
	
	/**
	 * 用户编号
	 */
	private String userId;
	
	/**
	 * 红包打开方式
	 */
	private Integer openType;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 红包金额
	 */
	private BigDecimal money;
	
	/**
	 * 创建时间
	 */
	private Date crtTm;
	
	/**
	 * 创建人
	 */
	private String crtCde;
	
	/**
	 * 更新时间
	 */
	private Date updTm;
	
	/**
	 * 更新人
	 */
	private String updCde;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActivityBatchNo() {
		return activityBatchNo;
	}

	public void setActivityBatchNo(String activityBatchNo) {
		this.activityBatchNo = activityBatchNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getOpenType() {
		return openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Date getCrtTm() {
		return crtTm;
	}

	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}

	public String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(String crtCde) {
		this.crtCde = crtCde;
	}

	public Date getUpdTm() {
		return updTm;
	}

	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}

	public String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(String updCde) {
		this.updCde = updCde;
	}
	
}
