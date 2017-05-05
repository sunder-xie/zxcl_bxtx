package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.PolicyBaseDTO;

public interface PolicyBaseDAO {
    int deleteByPrimaryKey(String policyBaseId);

    int insertSelective(PolicyBaseDTO record);

    PolicyBaseDTO selectByPrimaryKey(String policyBaseId);

    int updateByPrimaryKeySelective(PolicyBaseDTO record);

	/**
	 * 条件获取保费
	 * @param teamId
	 * @param startTm
	 * @param endTm
	 * @return
	 */
	String teamPolicyFee(@Param("teamId") String teamId, @Param("type") String type, @Param("userId") String userId);
}