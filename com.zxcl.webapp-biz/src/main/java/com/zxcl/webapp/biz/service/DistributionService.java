package com.zxcl.webapp.biz.service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.DistributionDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;

/**
 * 配送
 * 
 * @author 5555
 *
 */
public interface DistributionService {
	/**
	 * 插入信息
	 * 
	 * @param rec
	 * @throws BusinessServiceException 
	 */
	public void insert(DistributionDTO dis) throws BusinessServiceException;

	/**
	 * 根据订单号查询出订单配送信息
	 * 
	 * @param orderId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public DistributionDTO getByOrder(OrderDTO order) throws BusinessServiceException;

	/**
	 * 组装被保人信息
	 * 
	 * @param owner
	 * @return
	 * @throws BusinessServiceException 
	 */
	public DistributionDTO organizeDistribution(String userId, QuotaDTO quota, DistributionDTO dis) throws BusinessServiceException;
	
	/**
	 * 获取配送信息
	 * 
	 * @param orderId
	 * @return
	 */
	public DistributionDTO getDistributionByOrderId(String orderId)
			throws BusinessServiceException;
}
