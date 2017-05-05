package com.zxcl.webapp.biz.action.pay;

import javax.servlet.http.HttpServletRequest;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.OrderDTO;

public interface PayAction {

	/**
	 * 支付
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
	public boolean pay(HttpServletRequest request, String userId, OrderDTO order);

	/**
	 * 支付回调
	 * 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public boolean payedCallback(HttpServletRequest request, String userId) throws BusinessServiceException;
	
	/**
	 * 支付回调
	 * @param orderId
	 * @return
	 */
	public boolean payedCallback2(HttpServletRequest request,String orderId);
}
