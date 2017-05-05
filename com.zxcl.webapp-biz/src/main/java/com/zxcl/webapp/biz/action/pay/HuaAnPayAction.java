package com.zxcl.webapp.biz.action.pay;

import javax.servlet.http.HttpServletRequest;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

/**
 * 华安支付
 * @author zxcl
 *
 */
public interface HuaAnPayAction {
	/**
	 * 支付回调页面
	 * 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public boolean payedCallbackPage(HttpServletRequest request, String orderId) throws BusinessServiceException;

	/**
	 * 支付回调后台
	 * @param orderId
	 * @return
	 */
	public void payedCallback(HttpServletRequest request)  throws BusinessServiceException;
}
