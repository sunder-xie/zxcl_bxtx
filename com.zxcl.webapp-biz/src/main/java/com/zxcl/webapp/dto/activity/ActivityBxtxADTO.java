package com.zxcl.webapp.dto.activity;

import java.util.Date;

/**
 * 保行天下发布的第一次活动。。所以命名A结尾。。<br />
 * 保行天下每周一20：00-24：00发红包活动，活动时间2016-05-16至2016-07-01
 * @author xiaoxi
 *
 */
public class ActivityBxtxADTO {

	public Integer id;
	
	/**
	 * 用户编号
	 */
	private String userId;
	
	/**
	 * 1现金2其他
	 */
	private Integer prizeType;
	
	/**
	 * 当type为1时，值为现金，其他暂时为字符串
	 */
	private String prize;
	
	/**
	 * -1已失效1未领取2已领取
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(Integer prizeType) {
		this.prizeType = prizeType;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
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
