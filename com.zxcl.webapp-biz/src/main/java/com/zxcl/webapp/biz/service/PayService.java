package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

public interface PayService {

	/**
	 * 获取所有的支付方式
	 */
	public List<String> getPayWay(String userId) throws BusinessServiceException;

	/**
	 * 获取系统支持的所有方式
	 * 
	 * @param payName
	 * @return
	 */
	public String[] getSupportPayNames() throws BusinessServiceException;
}
