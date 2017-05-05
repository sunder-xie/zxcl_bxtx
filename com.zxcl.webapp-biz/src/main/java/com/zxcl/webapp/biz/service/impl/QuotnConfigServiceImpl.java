package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.QuotnConfigService;
import com.zxcl.webapp.dto.QuotnConfigDTO;
import com.zxcl.webapp.integration.dao.QuotnConfigDAO;

@Service
public class QuotnConfigServiceImpl implements QuotnConfigService{

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private QuotnConfigDAO quotnConfigDao;
	
	@Override
	public List<QuotnConfigDTO> queryByTeamId(String teamId)
			throws BusinessServiceException {
		logger.info("queryByTeamId teamId:"+teamId);
		List<QuotnConfigDTO> quotnConfigList = new ArrayList<QuotnConfigDTO>();
		try {
			quotnConfigList = quotnConfigDao.queryByTeamId(teamId);
		} catch (Exception e) {
			logger.error("根据团队号获取信息失败",e);
			throw new BusinessServiceException("根据团队号获取信息失败");
		}
		return quotnConfigList;
	}

	@Override
	public List<QuotnConfigDTO> queryByTeamIdAndQuotnType(String teamId,
			String quotnType) throws BusinessServiceException {
		logger.info("queryByTeamIdAndQuotnType teamId:"+teamId+"  quotnType："+quotnType);
		List<QuotnConfigDTO> quotnConfigList = new ArrayList<QuotnConfigDTO>();
		try {
			quotnConfigList = quotnConfigDao.queryByTeamIdAndQuotnType(teamId, quotnType);
		} catch (Exception e) {
			logger.error("根据团队号和投保类型获取信息失败",e);
			throw new BusinessServiceException("根据团队号和投保类型获取信息失败");
		}
		return quotnConfigList;
	}

	@Override
	public QuotnConfigDTO queryByTeamIdAndQuotnTypeAndInsId(String teamId,
			String quotnType, String insId,String serviceType) throws BusinessServiceException {
		logger.info("queryByTeamIdAndQuotnTypeAndInsId teamId:"+teamId+"  quotnType："+quotnType+"  insId："+insId+" serviceType："+serviceType);
		QuotnConfigDTO quotnConfigDTO = new QuotnConfigDTO();
		try {
			quotnConfigDTO = quotnConfigDao.queryByTeamIdAndQuotnTypeAndInsId(teamId, quotnType, insId,serviceType);
		} catch (Exception e) {
			logger.error("根据团队号、投保类型和保险公司号获取信息失败",e);
			throw new BusinessServiceException("根据团队号、投保类型和保险公司号获取信息失败");
		}
		return quotnConfigDTO;
	}

	@Override
	public String[] queryInsIdByTeamIdAndQuotnType(String teamId,
			String quotnType) throws BusinessServiceException {
		logger.info("queryInsIdByTeamIdAndQuotnType  teamId:"+teamId+"  quotnType："+quotnType);
		String [] insIds = new String[]{};
		try {
			insIds = quotnConfigDao.queryInsIdByTeamIdAndQuotnType(teamId, quotnType);
		} catch (Exception e) {
			logger.error("根据团队号和投保类型获取保险公司ID失败",e);
			throw new BusinessServiceException("根据团队号和投保类型获取保险公司ID失败");
		}
		return insIds;
	}

}
