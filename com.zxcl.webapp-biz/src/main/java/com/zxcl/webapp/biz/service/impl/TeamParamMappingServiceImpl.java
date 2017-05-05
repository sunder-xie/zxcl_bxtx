package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.TeamParamMappingService;
import com.zxcl.webapp.dto.TeamParamMappingDTO;
import com.zxcl.webapp.integration.dao.TeamParamMappingDAO;

@Service
public class TeamParamMappingServiceImpl implements TeamParamMappingService{
	
	private static Logger logger = Logger.getLogger(TaxReliefModelsServiceImpl.class);
	
	@Autowired
	private TeamParamMappingDAO teamParamMappingDAO;
	
	@Override
	public List<TeamParamMappingDTO> getTeamParamMappingInfo(String userId,
			String serviceType) throws BusinessServiceException {
		logger.info("TeamParamMappingServiceImpl   getTeamParamMappingInfo userId:"+userId+"  serviceType："+serviceType);
		List<TeamParamMappingDTO> teamParamMappingList = new ArrayList<TeamParamMappingDTO>();
		try {
			Integer i = teamParamMappingDAO.getCountAutoQuote(userId, serviceType);
			String quotnType = "";
			if(i > 0){
				quotnType = "A";
			}else{
				quotnType = "M";
			}
			teamParamMappingList = teamParamMappingDAO.getTeamParamMappingInfo(userId, serviceType, quotnType);
			if("A".equals(quotnType)){
				String [] insIds = teamParamMappingDAO.getOnelyManualQuotnInsId(userId, serviceType);
				for (String insId : insIds) {
					TeamParamMappingDTO teamParamMappingDTO = new TeamParamMappingDTO();
					teamParamMappingDTO = this.getTeamParamMappingInfoByInsId(userId, serviceType, insId,"M");
					teamParamMappingList.add(teamParamMappingDTO);
				}
			}
		} catch (Exception e) {
			logger.error("获取信息失败",e);
			throw new BusinessServiceException("获取信息失败");
		}
		return teamParamMappingList;
	}

	@Override
	public TeamParamMappingDTO getTeamParamMappingInfoByInsId(String userId,
			String serviceType, String insId,String quotnType)
			throws BusinessServiceException {
		logger.info("TeamParamMappingServiceImpl   getTeamParamMappingInfoByInsId  userId:"+userId+"  serviceType:"+serviceType+"  insId:"+insId+" quotnType:"+quotnType);
		TeamParamMappingDTO teamParamMappingDTO = new TeamParamMappingDTO();
		try {
			teamParamMappingDTO = teamParamMappingDAO.getTeamParamMappingInfoByInsId(userId, serviceType, quotnType, insId);
		} catch (Exception e) {
			logger.error("根据保险公司ID获取信息失败",e);
			throw new BusinessServiceException("根据保险公司ID获取信息失败");
		}
		return teamParamMappingDTO;
	}

	@Override
	public String getConfigId(String insId, String serviceType,
			String quotnType, String userId) throws BusinessServiceException {
		logger.info("TeamParamMappingServiceImpl  getConfigId   insId:"+insId+"  serviceType:"+serviceType+"  quotnType:"+quotnType+"  userId:"+userId);
		String configId = "";
		try {
			configId = teamParamMappingDAO.getConfigId(insId, serviceType, quotnType, userId);
		} catch (Exception e) {
			logger.error("获取configId失败",e);
			throw new BusinessServiceException("获取configId失败");
		}
		return configId;
	}

}
