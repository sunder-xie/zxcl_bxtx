package com.zxcl.webapp.biz.service.activity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.activity.ActivityBxtxBWinningListDTO;
import com.zxcl.webapp.dto.activity.ActivityBxtxCNameListDTO;

/**
 * 保行天下活动-转盘活动
 * @author xiaoxi
 *
 */
public interface ActivityBxtxCService {

	/**
	 * 获取抽奖次数
	 * @param userId
	 * @return
	 */
	public Integer getLotteryNumber(String userId);
	
	/**
	 * 抽奖
	 * @return
	 */
	public Map<String,String> updateLuckdraw(String userId) throws BusinessServiceException, Exception;
	
	/**
	 * 获取已抽奖的名单
	 * @return
	 */
	public List<ActivityBxtxCNameListDTO> getActivityBxtxBWinnings();
	
}
