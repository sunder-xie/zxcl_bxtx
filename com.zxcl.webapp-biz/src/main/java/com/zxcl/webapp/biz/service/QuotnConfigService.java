package com.zxcl.webapp.biz.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.QuotnConfigDTO;

/**
 * 人工报价控制Service
 * @author zx
 *
 */
public interface QuotnConfigService {
	
	/**
	 * 根据团队号获取信息
	 * @param teamId 团队号
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<QuotnConfigDTO> queryByTeamId(String teamId) throws BusinessServiceException;
	
	/**
	 * 根据团队号和投保类型获取信息
	 * @param teamId 团队号
	 * @param quotnType 投保类型
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<QuotnConfigDTO> queryByTeamIdAndQuotnType(String teamId,String quotnType) throws BusinessServiceException;
	
	/**
	 * 根据团队号和投保类型获取保险公司ID
	 * @param teamId 团队号
	 * @param quotnType 投保类型
	 * @return
	 * @throws BusinessServiceException
	 */
	public String[] queryInsIdByTeamIdAndQuotnType(@Param("teamId")String teamId,@Param("quotnType")String quotnType) throws BusinessServiceException;
	
	/**
	 * 根据团队号、投保类型和保险公司号获取信息
	 * @param teamId 团队号
	 * @param quotnType 投保类型
	 * @param insId 保险公司ID
	 * @param serviceType 业务类型
	 * @return
	 * @throws BusinessServiceException
	 */
	public QuotnConfigDTO queryByTeamIdAndQuotnTypeAndInsId(@Param("teamId")String teamId,@Param("quotnType")String quotnType,@Param("insId")String insId,String serviceType) throws BusinessServiceException;
}
