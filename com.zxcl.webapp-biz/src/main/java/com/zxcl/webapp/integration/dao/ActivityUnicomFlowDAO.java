package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.activity.unicome.ActivityUnicomFlowDTO;


/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */
public interface ActivityUnicomFlowDAO {
    int deleteByPrimaryKey(String flowId);

    int insertSelective(ActivityUnicomFlowDTO record);

    ActivityUnicomFlowDTO selectByPrimaryKey(String flowId);

    int updateByPrimaryKeySelective(ActivityUnicomFlowDTO record);

    ActivityUnicomFlowDTO selectBySecurityId(String securityId);

	List<ActivityUnicomFlowDTO> getUnicomeFlowByUserIdAndStage(@Param("userId")String userId, @Param("stage")Byte stage);

	List<ActivityUnicomFlowDTO> getUnicomeFlowByContactPhone(@Param("contactPhone")String contactPhone, @Param("stage")Byte stage);
}