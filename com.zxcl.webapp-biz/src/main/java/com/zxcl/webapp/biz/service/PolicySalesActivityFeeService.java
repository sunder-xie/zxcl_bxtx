package com.zxcl.webapp.biz.service;

import java.math.BigDecimal;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

/**
 * 保单活动销售分成.
 * @author xiaoxi
 *
 */
public interface PolicySalesActivityFeeService {

	/**
	 * 获取用户可用的抽奖次数
	 * @param userId
	 * @return
	 */
	public Integer getUserPolicySalesCount(String userId);
	
	/**
	 * 用户抽奖，根据用户ID
	 * @param userId
	 * @return
	 */
//	public BigDecimal updateUserLuckDraw(String userId) throws BusinessServiceException;
}
