package com.zxcl.webapp.biz.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.AgencyDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

public interface AgencyService {

	/**
	 * 查询和小薇有关联保险公司
	 * 
	 * @param micro_id
	 * @return
	 * @throws BusinessServiceException 
	 * @throws Exception
	 */
//	public List<InsuranceDTO> getInsByMicroId(String micro_id) throws BusinessServiceException;

	/**
	 * 获取前端流程控制，1-报价；2-投保；3-支付
	 * @param microId 小微ID
	 * @param insId 保险公司ID
	 * @param quotnType 报价类型 A自动 M人工报价
	 * @param serviceType 业务类型
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getTeamType(@Param("microId")String microId,@Param("insId")String insId,String quotnType,String serviceType) throws BusinessServiceException;
	
	/**
	 * 根据团队ID获取该团队下车主输入开关，1-开，2-关
	 * @param teamId 团队ID
	 * @return
	 * @throws Exception
	 */
	public String getSwitchByTeamId(String teamId) throws BusinessServiceException;
}
