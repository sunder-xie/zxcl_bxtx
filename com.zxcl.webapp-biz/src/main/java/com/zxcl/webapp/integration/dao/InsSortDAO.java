package com.zxcl.webapp.integration.dao;

import com.zxcl.webapp.dto.InsSortDTO;

/**
 * @author zxj
 * @date 2016年7月29日
 * @description 
 */
public interface InsSortDAO {
    int insertSelective(InsSortDTO insSortDTO);
}