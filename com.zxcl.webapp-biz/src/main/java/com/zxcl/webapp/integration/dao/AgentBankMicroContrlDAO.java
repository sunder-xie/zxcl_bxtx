package com.zxcl.webapp.integration.dao;

import com.zxcl.webapp.dto.AgentBankMicroContrlDTO;

/**
 * @author zxj
 * @date 2016年10月10日
 * @description 
 */
public interface AgentBankMicroContrlDAO {

    /**
     * insertSelective
     * @param record
     * @return
     */
    int insertSelective(AgentBankMicroContrlDTO record);
    
    /**
     * getByUserId
     * @param userId
     * @return
     */
    AgentBankMicroContrlDTO getByUserId(String userId);
    
    /**
     * updateSelective
     * @param record
     * @return
     */
    int updateSelective(AgentBankMicroContrlDTO record);
}