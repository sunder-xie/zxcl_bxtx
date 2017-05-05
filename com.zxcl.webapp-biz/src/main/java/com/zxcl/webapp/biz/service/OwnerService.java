package com.zxcl.webapp.biz.service;

import java.text.ParseException;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;

/**
 * 
 * 
 * @author 5555
 *
 */
public interface OwnerService {
	/**
	 * 插入信息
	 * 
	 * @param rec
	 * @throws Exception
	 */
	public void insert(OwnerDTO owner) throws BusinessServiceException;

	/**
	 * 根据订单Id查询车主信息
	 * 
	 * @param orderId
	 * @return
	 * @throws BusinessServiceException
	 */
	public OwnerDTO getByOrder(OrderDTO order) throws BusinessServiceException;

	/**
	 * 获取驾驶员信息
	 * 
	 * @param order
	 * @return
	 * @throws BusinessServiceException
	 */
	public OwnerDTO getOwnerByOrderId(String orderId) throws BusinessServiceException;

	/**
	 * 组装车主信息
	 * 
	 * @param owner
	 * @return
	 * @throws BusinessServiceException
	 */
	public OwnerDTO organizeOwner(String userId, QuotaDTO quota, OwnerDTO owner)
			throws ParseException, BusinessServiceException;
}
