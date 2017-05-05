package com.zxcl.webapp.biz.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.AgencyService;
import com.zxcl.webapp.dto.AgencyDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.integration.dao.AgencyDAO;

/**
 * 中介公司
 * 
 * @author 5555
 *
 */
@Service
public class AgencyServiceImpl implements AgencyService {

	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	private AgencyDAO agencyDao;

	/*@Override
	public List<InsuranceDTO> getInsByMicroId(String microId) throws BusinessServiceException {
		logger.info("查询和小薇有关联保险公司 ==> 入参    小微ID："+microId);
		List<InsuranceDTO> list = null;
		try {
			list = agencyDao.getInsByMicroId(microId);
		} catch (Exception e) {
			logger.error("查询小薇:" + microId + "配置的保险公司失败:" + e.getMessage(),e);
			throw new BusinessServiceException("查询和小薇有关联保险公司失败");
		}
		return list;
	}*/

	@Override
	public Integer getTeamType(String microId, String insId,String quotnType,String serviceType)
			throws BusinessServiceException {
		Integer flowControl = null;
		try {
			flowControl = agencyDao.getTeamType(microId, insId,quotnType,serviceType);
		} catch (Exception e) {
			logger.error("小微："+microId+"  获取前端流程控制失败",e);
			throw new BusinessServiceException("小微："+microId+"  获取前端流程控制失败");
		}
		return flowControl;
	}

	@Override
	public String getSwitchByTeamId(String teamId)
			throws BusinessServiceException {
		logger.info("根据团队ID获取该团队下车主输入开关，团队ID："+teamId);
		String teamSwitch = "";
		try {
			teamSwitch = agencyDao.getSwitchByTeamId(teamId);
		} catch (Exception e) {
			logger.error("根据团队ID获取该团队下车主输入开关失败，团队ID："+teamId,e);
			throw new BusinessServiceException("根据团队ID获取该团队下车主输入开关失败，团队ID："+teamId);
		}
		return teamSwitch;
	}

}
