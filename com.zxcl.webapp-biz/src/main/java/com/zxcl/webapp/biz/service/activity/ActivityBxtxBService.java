package com.zxcl.webapp.biz.service.activity;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.activity.ActivityBxtxBWinningListDTO;

/**
 * 保行天下活动-转盘活动
 * @author xiaoxi
 *
 */
public interface ActivityBxtxBService {

	/**
	 * 获取已抽奖次数
	 * @param userId
	 * @return
	 */
	public Integer getLotteryNumber(String userId);
	
	/**
	 * 抽奖
	 * @return
	 */
	public Integer updateLuckdraw(String userId) throws BusinessServiceException, Exception;
	
	/**
	 * 获取已抽奖的名单
	 * @return
	 */
	public List<ActivityBxtxBWinningListDTO> getActivityBxtxBWinnings();
	
}
