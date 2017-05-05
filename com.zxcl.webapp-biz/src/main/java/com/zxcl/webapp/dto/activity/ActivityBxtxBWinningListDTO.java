package com.zxcl.webapp.dto.activity;

import java.util.Date;

/**
 * 保行天下活动-转盘抽奖，会中奖的名单
 * @author xiaoxi
 *
 */
public class ActivityBxtxBWinningListDTO {

	/**
	 * 会中奖的用户编号
	 */
	private String userId;
	
	/**
	 * 中奖等级 1 一等奖 2 二等奖 3三等奖
	 */
	private Integer level;
	
	/**
	 * 状态 1 未抽 2 已抽 3已领取
	 */
	private Integer status;
	
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
