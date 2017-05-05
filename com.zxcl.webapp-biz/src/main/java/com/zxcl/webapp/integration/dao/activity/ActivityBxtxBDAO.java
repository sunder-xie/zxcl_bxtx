package com.zxcl.webapp.integration.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.activity.ActivityBxtxBDrawListDTO;
import com.zxcl.webapp.dto.activity.ActivityBxtxBWinningListDTO;

/**
 * 保行天下活动-转盘抽奖DAO
 * @author xiaoxi
 *
 */
public interface ActivityBxtxBDAO {

	/**
	 * 获取已抽奖次数
	 * 
	 * @param userId
	 * @return
	 */
	public Integer getLotteryNumber(String userId);
	
	/**
	 * 添加已抽奖记录.
	 * @param activityBxtxBDrawListDTO
	 * @return 受影响行数
	 */
	public Integer insertActivityBxtxBDrawList(ActivityBxtxBDrawListDTO activityBxtxBDrawListDTO);
	
	/**
	 * 获取会中奖的名单
	 * @return
	 */
	public List<ActivityBxtxBWinningListDTO> getActivityBxtxBWinnings();
	
	/**
	 * 更新会中奖的名单的状态.
	 * @param userId 中奖名单的用户编号
	 * @param status 状态
	 * @return 受影响行数
	 */
	public Integer updateActivityBxtxBWinningStatus(
			@Param("userId")String userId,@Param("status")Integer status);
	
}
