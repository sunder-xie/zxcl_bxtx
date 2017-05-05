package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.TeamParamMappingDTO;

/**
 * 
 * @author zx
 *
 */
public interface TeamParamMappingDAO {
	
	/**
	 * 获取自动报价的保险公司数量
	 * @param userId 用户ID
	 * @param serviceType 
	 * @return
	 * @throws Exception
	 */
	public Integer getCountAutoQuote(@Param("userId")String userId,@Param("serviceType")String serviceType) throws Exception;
	
	/**
	 * 获取信息
	 * @param userId 用户ID
	 * @param serviceType 业务类型
	 * @param quotnType 报价类型，A-自动，M-人工
	 * @return
	 * @throws Exception
	 */
	public List<TeamParamMappingDTO> getTeamParamMappingInfo(@Param("userId")String userId,@Param("serviceType")String serviceType,
			@Param("quotnType")String quotnType) throws Exception;
	
	/**
	 * 根据保险公司ID获取信息
	 * @param userId 用户ID
	 * @param serviceType 业务类型
	 * @param quotnType 报价类型，A-自动，M-人工
	 * @param insId 保险公司ID
	 * @return
	 * @throws Exception
	 */
	public TeamParamMappingDTO getTeamParamMappingInfoByInsId(@Param("userId")String userId,@Param("serviceType")String serviceType,
			@Param("quotnType")String quotnType,@Param("insId")String insId) throws Exception;
	
	/**
	 * 获取configId
	 * @param insId 保险公司ID
	 * @param serviceType 业务类型
	 * @param quotnType 报价类型，A-自动报价，M-人工报价
	 * @param userId 用户ID
	 * @return
	 * @throws Exception
	 */
	public String getConfigId(@Param("insId")String insId,@Param("serviceType")String serviceType,@Param("quotnType")String quotnType,@Param("userId")String userId) throws Exception;
	
	/**
	 * 获取该类型只有人工报价的保险公司ID
	 * @param userId 用户ID
	 * @param serviceType 业务类型
	 * @return
	 * @throws Exception
	 */
	public String [] getOnelyManualQuotnInsId(@Param("userId")String userId,@Param("serviceType")String serviceType) throws Exception;
}
