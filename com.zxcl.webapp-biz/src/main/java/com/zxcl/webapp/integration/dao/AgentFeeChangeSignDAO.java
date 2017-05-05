package com.zxcl.webapp.integration.dao;

/**
 * 代理公司费改标志DAO
 * @author zx
 *
 */
public interface AgentFeeChangeSignDAO {
	
	/**
	 * 查询所有未费改的代理公司ID
	 * @return
	 * @throws Exception
	 */
	public String[] getAll() throws Exception;
}
