package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.FeeDTO;
import com.zxcl.webapp.dto.FeeRulesDTO;


/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */
public interface FeeRulesDAO {

    
    /**
     * 当前用户的当前代理公司的商业费率
     * @param insId
     * @param userId
     * @return
     */
    FeeDTO selectRatioNowWith2(@Param("insId")String insId, @Param("userId")String userId, @Param("productCode")String productCode, @Param("teamId")String teamId);
    
  
}