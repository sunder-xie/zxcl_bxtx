package com.zxcl.webapp.integration.dao;

import java.util.List;

import com.zxcl.webapp.dto.MicroApproveFileDTO;

/**
 * @author zxj
 * @date 2016年8月22日
 * @description 
 */
public interface MicroApproveFileDAO {
    /**
     * @param record
     * @return
     */
    int insertSelective(MicroApproveFileDTO record);

	/**
	 * @param approveId
	 * @return
	 */
	List<MicroApproveFileDTO> findFileListByApproveId(Long approveId);
}