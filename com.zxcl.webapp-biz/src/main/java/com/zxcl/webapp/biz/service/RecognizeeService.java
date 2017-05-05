package com.zxcl.webapp.biz.service;

import java.text.ParseException;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;

/**
 * 
 * 
 * @author 5555
 *
 */
public interface RecognizeeService {
	/**
	 * 插入信息
	 * 
	 * @param rec
	 * @throws BusinessServiceException 
	 * @throws Exception
	 */
	public void insert(RecognizeeDTO rec) throws BusinessServiceException ;

	/**
	 * 组装被保人信息
	 * 
	 * @param owner
	 * @return
	 * @throws ParseException
	 * @throws BusinessServiceException 
	 */
	public RecognizeeDTO organizeRecognizee(String userId, QuotaDTO quota, RecognizeeDTO rec) throws ParseException, BusinessServiceException;

	/**
	 * 
	 * 根据订单信息
	 * 
	 * @param order
	 * @return
	 * @throws BusinessServiceException 
	 */
	public RecognizeeDTO getByOrder(OrderDTO order) throws BusinessServiceException;
	
	/**
	 * 查询被保人信息
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public RecognizeeDTO getRecognizeeByOrderId(String orderId)
			throws BusinessServiceException;
}
