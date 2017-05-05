package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.ApiAppControlDTO;

/**
 * @author zxj
 * @date 2016年9月27日
 * @description 
 */
public interface ApiAppControlDAO {
    int insertSelective(ApiAppControlDTO record);
    
    ApiAppControlDTO selectByAppId(@Param("appId")String appId, @Param("controlItem")String controlItem);
    
}