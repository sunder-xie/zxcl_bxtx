package com.zxcl.webapp.dto.activity;

import java.util.Date;

/**
 * 保行天下活动B，转盘抽奖，已抽奖名单。
 * @author xiaoxi
 *
 */
public class ActivityBxtxBDrawListDTO {

	/**
	 * 已抽奖的用户编号
	 */
	private String userId;
	
	/**
	 * 是否中奖 1 否 2 是
	 */
	private Integer isHad;
	
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

	public Integer getIsHad() {
		return isHad;
	}

	public void setIsHad(Integer isHad) {
		this.isHad = isHad;
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
