package com.zxcl.webapp.biz.service;

import java.text.ParseException;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;

/**
 * 
 * 投保人
 * 
 * @author 5555
 *
 */
public interface VoteInsuranceService {
	/**
	 * 插入信息
	 * 
	 * @param rec
	 * @throws BusinessServiceException
	 * @throws Exception
	 */
	public void insert(VoteInsuranceDTO vote) throws BusinessServiceException;

	/**
	 * 根据订单信息查询投保人信息
	 * 
	 * @param order
	 * @return
	 * @throws BusinessServiceException
	 */
	public VoteInsuranceDTO getByOrder(OrderDTO order)
			throws BusinessServiceException;

	/**
	 * 组装被保人信息
	 * 
	 * @param owner
	 * @return
	 * @throws ParseException
	 * @throws BusinessServiceException
	 */
	public VoteInsuranceDTO organizeVoteInsurance(String userId,
			QuotaDTO quota, VoteInsuranceDTO vote) throws ParseException,
			BusinessServiceException;

	/**
	 * 获取投保人信息
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public VoteInsuranceDTO getVoteInsuranceByOrderId(String orderId)
			throws BusinessServiceException;
}
