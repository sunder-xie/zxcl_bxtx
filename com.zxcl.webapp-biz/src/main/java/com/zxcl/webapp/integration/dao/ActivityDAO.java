package com.zxcl.webapp.integration.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.ActivityDTO;

/**
 * 活动DAO
 * @author xiaoxi
 *
 */
public interface ActivityDAO {

	/**
	 * 获取正在进行中的活动
	 * @param activityType
	 * @param agentId
	 * @param currentTime
	 * @return
	 */
	public List<ActivityDTO> getOngoingActivity(
			@Param("activityType")Integer activityType,
			@Param("agentId")String agentId,
			@Param("currentTime")Date currentTime);
	
	/**
	 * 获取历史的活动记录
	 * @param activityType
	 * @param agentId
	 * @return
	 */
	public List<ActivityDTO> getHistoryActivitys(
			@Param("activityType")Integer activityType,
			@Param("agentId")String agentId,
			@Param("pageSize")Integer pageSize,
			@Param("limitNo")Integer limitNo);
	
	/**
	 * 获取历史活动的总数
	 * @param activityType
	 * @param agentId
	 * @return
	 */
	public Integer getHistoryActivityCount(
			@Param("activityType")Integer activityType,
			@Param("agentId")String agentId);
	
	/**
	 * 获取完成的活动详情信息.
	 * @param id
	 * @return
	 */
	public ActivityDTO getActivityDetailById(ActivityDTO activityDTO);
	
	
	public int selectCountBetwByTitle(String title);
	
	/**
	 * 获取保行天下活动c的订单订单总数
	 * @return
	 */
	public Integer getActivityBxtxCOrderCount(@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	public Integer getActivityBxtxcUsersCount(@Param("startTime")String startTime,@Param("endTime")String endTime);
}
