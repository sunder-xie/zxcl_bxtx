package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;


import com.zxcl.webapp.dto.DistributionDTO;
import com.zxcl.webapp.dto.OrderDTO;

/**
 * 保单配送
 * 
 * @author 5555
 *
 */
public interface DistributionDAO {
	/**
	 * 插入信息
	 * 
	 * @param rec
	 */
	public void insert(DistributionDTO dis);

	/**
	 * 根据订单号查询出订单配送信息
	 * 
	 * @param orderId
	 * @return
	 */
	public DistributionDTO getByOrder(OrderDTO order);

	/**
	 * 根据报价单号删除配送信息
	 * 
	 * @param quotaId
	 */
	public void deleteByQuotaId(@Param("quotaId") String quotaId, @Param("microId") String microId, @Param("insId") String insId);

	/**
	 * 获取配送信息
	 * 
	 * @param orderId
	 * @return
	 */
	public DistributionDTO getDistributionByOrderId(String orderId);

	/**
	 * 删除配送信息
	 */
	public void deleteByOrderId(String orderId);
	
	/**
	 * 根据保单号获取配置信息
	 * @param orderId
	 * @return
	 */
	public DistributionDTO selectByOrderId(String orderId);
	
	/**
	 * 更新配送信息
	 * @param distributionDTO
	 * @return
	 */
	public int updateDistribution(DistributionDTO distributionDTO);
}
