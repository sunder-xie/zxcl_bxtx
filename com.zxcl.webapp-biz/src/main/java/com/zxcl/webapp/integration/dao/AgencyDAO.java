package com.zxcl.webapp.integration.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;









import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;


public interface AgencyDAO {

	/**
	 * 查询和小薇有关联保险公司
	 * @param microId
	 * @return
	 */
//	public List<InsuranceDTO> getInsByMicroId(String microId);

	/**
	 * 查询是否能使用名义代理人
	 * @param userId
	 * @return
	 */
	public String selectAgencyTaxRuleByUserId(String userId);
	
	/**
	 * 获取前端流程控制，1-报价；2-投保；3-支付
	 * @param microId 小微ID
	 * @param insId 保险公司ID
	 * @return
	 * @throws Exception
	 */
	public Integer getTeamType(@Param("microId")String microId,@Param("insId")String insId,@Param("quotnType")String quotnType,@Param("serviceType")String serviceType) throws Exception;
	
	/**
	 * getAgtTeamByTeamId
	 * @param teamId
	 * @return
	 */
	public HashMap<String, String> getAgtTeamByTeamId(String teamId);
	
	/**
	 * 根据团队ID获取该团队下车主输入开关，1-开，2-关
	 * @param teamId 团队ID
	 * @return
	 * @throws Exception
	 */
	public String getSwitchByTeamId(String teamId) throws Exception;
	
	public int findCountCompCodeWithTeamId(@Param("compCode")String compCode,@Param("teamId")String teamId);
	
	public String findTeamIdWithCompCodeAndTeamIdMapping(@Param("compCode")String compCode,@Param("thirdTeamId")String thirdTeamId);

	/**
	 * getAgtByAgtId
	 * @param agtId
	 * @return
	 */
	public HashMap<String, String> getAgtByAgtId(String agtId);
}
