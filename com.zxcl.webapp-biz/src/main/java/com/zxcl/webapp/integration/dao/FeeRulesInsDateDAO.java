package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.FeeRulesInsDateDTO;

/**
 * @author zxj
 * @date 2016年11月16日
 * @description 
 */
public interface FeeRulesInsDateDAO {
    int insertSelective(FeeRulesInsDateDTO record);
    
    java.util.List<FeeRulesInsDateDTO> findFeeByParam(
    		@Param("userId")String userId, 
    		@Param("insId")String insId, 
    		@Param("productCode")String productCode, 
    		@Param("insureStartDate")String insureStartDate);
}