package com.zxcl.webapp.integration.dao;

import com.zxcl.webapp.dto.MicroApproveDTO;

/**
 * @author zxj
 * @date 2016年8月22日
 * @description 
 */
public interface MicroApproveDAO {
    /**
     * 删除
     * @param approveId
     * @return
     */
    int deleteByPrimaryKey(Long approveId);
    
    /**
     * 使无效
     * @return
     */
    int updApproveForInvalid(Long approveId);

    /**
     * @param record
     * @return
     */
    int insertSelective(MicroApproveDTO record);

    /**
     * @param approveId
     * @return
     */
    MicroApproveDTO selectByPrimaryKey(Long approveId);

    /**
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(MicroApproveDTO record);

	/**
	 * @param microId
	 * @return
	 */
	MicroApproveDTO findConfirm(String microId);

	/**
	 * @param userId
	 * @return
	 */
	String isNeedApporve(String userId);
}