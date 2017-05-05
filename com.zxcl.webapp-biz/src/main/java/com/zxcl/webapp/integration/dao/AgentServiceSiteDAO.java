package com.zxcl.webapp.integration.dao;

import java.util.List;


import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.dto.AgentServiceSiteDTO;


public interface AgentServiceSiteDAO {
	
    /**
     * selectByPrimaryKey
     * @param orgId
     * @return
     */
    AgentServiceSiteDTO selectByPrimaryKey(String orgId) throws Exception;
    
    /**
     * deleteByPrimaryKey
     * @param orgId
     * @return
     */
    int deleteByPrimaryKey(String orgId) throws Exception;

    /**
     * insertSelective
     * @param site
     * @return
     */
    int insertSelective(AgentServiceSiteDTO site) throws Exception;

    /**
     * updateByPrimaryKeySelective
     * @param site
     * @return
     */
    int updateByPrimaryKeySelective(AgentServiceSiteDTO site) throws Exception;

	/**
	 * siteListCount
	 * @param pageParam
	 * @return
	 */
	int siteListCount(PageParam pageParam) throws Exception;//TODO xml未写

	/**
	 * siteList
	 * @param pageParam
	 * @return
	 */
	List<AgentServiceSiteDTO> siteList(PageParam pageParam) throws Exception;//TODO xml未写

	/**
	 * 通过中介编号查询中介对应的报价机构
	 * @param agentId
	 * @return
	 */
	List<AgentServiceSiteDTO> findSitesByAgentId(String agentId) throws Exception;
}