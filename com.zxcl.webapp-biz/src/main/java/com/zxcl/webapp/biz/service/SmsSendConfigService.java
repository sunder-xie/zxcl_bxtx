package com.zxcl.webapp.biz.service;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.exception.BusinessServiceException;


/**
 * 出单人员
 * @author zx
 *
 */
public interface SmsSendConfigService {
	/**
	 * 根据保险公司ID和代理公司ID获取
	 * @param insId
	 * @param agentId
	 * @return
	 * @throws Exception
	 */
	public String[] getPhone(@Param("insId")String insId,@Param("agentId")String agentId) throws BusinessServiceException;
}
