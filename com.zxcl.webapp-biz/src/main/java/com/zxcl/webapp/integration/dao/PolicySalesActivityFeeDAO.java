package com.zxcl.webapp.integration.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.PolicySalesActivityFeeDTO;

public interface PolicySalesActivityFeeDAO {

	/**
	 * 根据userid获取可抽奖次数.
	 * @param userId
	 * @return
	 */
	public Integer getPolicySalesCount(String userId);
	
	/**
	 * 获取一条可抽奖的数据
	 * @param userId
	 * @return
	 */
	public PolicySalesActivityFeeDTO getALuckyDrawData(String userId);
	
	/**
	 * 开始抽奖，更新对应分成数据的状态。
	 * @param userId 用户编号
	 * @param psafId 分成编号
	 * @return 受影响行数。如果受影响行数为0，表示失败。
	 */
	public Integer updateLuckDraw(@Param("userId")String userId,
			@Param("psafId")String psafId);
	
	/**
	 * 获取保单的总金额
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public BigDecimal getPolicyFeeCount(@Param("startTime")String startTime,@Param("endTime")String endTime);
}
