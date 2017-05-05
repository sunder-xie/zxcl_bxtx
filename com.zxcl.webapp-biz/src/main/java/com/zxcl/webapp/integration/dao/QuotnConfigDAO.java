package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.QuotnConfigDTO;

/**
 * 人工报价控制DAO
 * @author zx
 *
 */
public interface QuotnConfigDAO {
	
	/**
	 * 根据团队号获取信息
	 * @param teamId 团队号
	 * @return
	 * @throws Exception
	 */
	public List<QuotnConfigDTO> queryByTeamId(String teamId) throws Exception;
	
	/**
	 * 根据团队号和投保类型获取信息
	 * @param teamId 团队号
	 * @param quotnType 投保类型
	 * @return
	 * @throws Exception
	 */
	public List<QuotnConfigDTO> queryByTeamIdAndQuotnType(@Param("teamId")String teamId,@Param("quotnType")String quotnType) throws Exception;
	
	/**
	 * 根据团队号和投保类型获取保险公司ID
	 * @param teamId 团队号
	 * @param quotnType 投保类型
	 * @return
	 * @throws Exception
	 */
	public String[] queryInsIdByTeamIdAndQuotnType(@Param("teamId")String teamId,@Param("quotnType")String quotnType) throws Exception;
	
	/**
	 * 根据团队号、投保类型、业务类型和保险公司号获取信息
	 * @param teamId 团队号
	 * @param quotnType 投保类型
	 * @param insId 保险公司ID
	 * @return
	 * @throws Exception
	 */
	public QuotnConfigDTO queryByTeamIdAndQuotnTypeAndInsId(@Param("teamId")String teamId,@Param("quotnType")String quotnType,@Param("insId")String insId,@Param("serviceType")String serviceType) throws Exception;
}
