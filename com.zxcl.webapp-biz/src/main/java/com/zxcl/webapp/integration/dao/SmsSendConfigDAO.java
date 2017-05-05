package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;



/**
 * 出单人员
 * @author zx
 *
 */
public interface SmsSendConfigDAO {
	
	/**
	 * 根据保险公司ID和代理公司ID获取电话号码
	 * @param insId
	 * @param agentId
	 * @return
	 * @throws Exception
	 */
	public String[] getPhone(@Param("insId")String insId,@Param("agentId")String agentId) throws Exception;
}
