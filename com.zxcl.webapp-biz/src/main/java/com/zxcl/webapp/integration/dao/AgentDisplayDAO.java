package com.zxcl.webapp.integration.dao;

import com.zxcl.webapp.dto.AgentDisplayDTO;

/**
 * @author zxj
 * @date 2016年11月3日
 * @description 
 */
public interface AgentDisplayDAO {
    int deleteByPrimaryKey(String compCode);

    int insertSelective(AgentDisplayDTO record);

    AgentDisplayDTO selectByPrimaryKey(String compCode);

    int updateByPrimaryKeySelective(AgentDisplayDTO record);
}