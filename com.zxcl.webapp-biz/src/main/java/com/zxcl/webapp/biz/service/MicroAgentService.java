package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.dto.MicroAgentDTO;
import com.zxcl.webapp.dto.MicroAgentDTOKey;

/**
 * @author zxj
 */
public interface MicroAgentService {
    /**
     * 删除名义代理人关联信息
     * @param key
     * @return
     */
    int deleteByPrimaryKey(MicroAgentDTOKey key);

    /**
     * 新增名义代理人关联信息
     * @param record
     * @return
     */
    int insertSelective(MicroAgentDTO record);

    /**
     * 查询一条记录
     * @param key
     * @return
     */
    MicroAgentDTO selectByPrimaryKey(MicroAgentDTOKey key);
    

    /**
     * 更新一条记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(MicroAgentDTO record);
    
    /**
     * 查询用户关联的名义代理人列表
     * @param microId
     * @return
     */
    List<MicroAgentDTO> selectMicroAgentListByMicroId(String microId);
    
    
    
    
    /**
     * 查询名义代理小微被谁关联
     * @param agentMicroId
     * @return
     */
    MicroAgentDTO selectMicroAgentByAgentMicroId(String agentMicroId);
    
}