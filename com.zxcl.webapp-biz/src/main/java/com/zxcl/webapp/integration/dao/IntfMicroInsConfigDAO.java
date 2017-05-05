package com.zxcl.webapp.integration.dao;


import com.zxcl.webapp.dto.IntfMicroInsConfigDTO;
import com.zxcl.webapp.dto.IntfMicroInsConfigDTOKey;

/**
 * 访问记录信息
 * @author 444
 *
 */
public interface IntfMicroInsConfigDAO {
    /**
     * 保存访问记录信息
     * @param record
     * @return
     */
	int insertSelective(IntfMicroInsConfigDTO record) throws Exception;

	/**
	 * 查询访问记录信息
	 * @param key
	 * @return
	 */
    IntfMicroInsConfigDTO selectByPrimaryKey(IntfMicroInsConfigDTOKey key) throws Exception;
    
    /**
     * 更新访问记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(IntfMicroInsConfigDTO record) throws Exception;
}