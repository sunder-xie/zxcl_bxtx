package com.zxcl.webapp.biz.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.TeamParamMappingDTO;

/**
 * 
 * @author zx
 *
 */
public interface TeamParamMappingService {
	
	/**
	 * 获取信息
	 * @param userId 用户ID
	 * @param serviceType 业务类型
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<TeamParamMappingDTO> getTeamParamMappingInfo(String userId,String serviceType) throws BusinessServiceException;

	/**
	 * 根据保险公司ID获取信息
	 * @param userId 用户ID
	 * @param serviceType 业务类型
	 * @param insId 保险公司ID
	 * @return
	 * @throws Exception
	 */
	public TeamParamMappingDTO getTeamParamMappingInfoByInsId(String userId,String serviceType,
			String insId,String quotnType) throws BusinessServiceException;
	
	/**
	 * 获取configId
	 * @param insId 保险公司ID
	 * @param serviceType 业务类型
	 * @param quotnType 报价类型，A-自动报价，M-人工报价
	 * @param userId 用户ID
	 * @return
	 * @throws Exception
	 */
	public String getConfigId(String insId,String serviceType,String quotnType,String userId) throws BusinessServiceException;
}
