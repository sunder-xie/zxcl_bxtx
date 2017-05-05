package com.zxcl.webapp.integration.dao;

import java.util.List;

import com.zxcl.webapp.dto.InsMsgMatchDTO;

/**
 * @author zxj
 * @date 2016年10月8日
 * @description 
 */
public interface InsMsgMatchDAO {
	
    List<InsMsgMatchDTO> selectByInsId(String insId);
    
}