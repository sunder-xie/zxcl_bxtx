package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.AgentServiceControlDTO;

/**
 * @author zxj
 * @date 2016年10月9日
 * @description 
 */
public interface AgentServiceControlDAO {
	
    /**
     * selectByAgentId
     * @param agentId
     * @return
     */
    AgentServiceControlDTO selectByAgentIdAndServiceType(@Param("agentId")String agentId, @Param("serviceType")Integer serviceType);
}