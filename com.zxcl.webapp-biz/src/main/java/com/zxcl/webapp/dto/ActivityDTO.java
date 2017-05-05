package com.zxcl.webapp.dto;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zxcl.webapp.biz.util.CustomDateSerializer;

/**
 * 活动DTO
 * @author xiaoxi
 *
 */
public class ActivityDTO {

	public Integer id;
	
	/**
	 * 活动类型，活动类型1代理，2保行天下
	 */
	private Integer activityType;
	
	/**
	 * 代理人ID
	 */
	private String agentId;
	
	/**
	 * 活动标题
	 */
	private String title;
	
	/**
	 * 活动开始时间
	 */
	private Date startTime;
	
	/**
	 * 活动结束时间
	 */
	private Date endTime;
	
	/**
	 * 状态 -1已过期1正常
	 */
	private Integer status;
	
	/**
	 * 活动主图的地址
	 */
	private String mainImgUrl;
	
	/**
	 * 活动相关的更多图片
	 */
	private List<String> moreImgUrlList;
	
	/**
	 * 活动内容html代码
	 */
	private String activityContent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMainImgUrl() {
		return mainImgUrl;
	}

	public void setMainImgUrl(String mainImgUrl) {
		this.mainImgUrl = mainImgUrl;
	}

	public List<String> getMoreImgUrlList() {
		return moreImgUrlList;
	}

	public void setMoreImgUrlList(List<String> moreImgUrlList) {
		this.moreImgUrlList = moreImgUrlList;
	}

	public String getActivityContent() {
		return activityContent;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}
	
	
}
