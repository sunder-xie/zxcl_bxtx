package com.zxcl.webapp.integration.dao;

import com.zxcl.webapp.dto.LogLoginInfo;

public interface LogLoginInfoDAO {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(LogLoginInfo record);

    LogLoginInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogLoginInfo record);
    
    LogLoginInfo selectNearRecordByUserId(String userId);
}