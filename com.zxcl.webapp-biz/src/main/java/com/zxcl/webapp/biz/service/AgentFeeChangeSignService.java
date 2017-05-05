package com.zxcl.webapp.biz.service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

/**
 * 代理公司费改标志service
 * @author zx
 *
 */
public interface AgentFeeChangeSignService {
	
	/**
	 * 查询所有未费改的代理公司ID
	 * @return
	 * @throws Exception
	 */
	public String[] getAll() throws BusinessServiceException;
}
