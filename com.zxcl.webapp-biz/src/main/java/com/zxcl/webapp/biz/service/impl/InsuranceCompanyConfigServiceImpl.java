package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InsuranceCompanyConfigService;
import com.zxcl.webapp.dto.InsuranceCompanyConfigDTO;
import com.zxcl.webapp.integration.dao.InsuranceCompanyConfigDAO;

@Service
public class InsuranceCompanyConfigServiceImpl implements InsuranceCompanyConfigService{

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private InsuranceCompanyConfigDAO insuranceCompanyConfigDAO;
	
	@Override
	public List<InsuranceCompanyConfigDTO> getInfoByInsId(String configId)
			throws BusinessServiceException {
		logger.info("获取保险公司配置信息，返回list集合 入参    配置信息ID："+configId);
		List<InsuranceCompanyConfigDTO> list = new ArrayList<InsuranceCompanyConfigDTO>();
		try {
			list = insuranceCompanyConfigDAO.getInfoByConfigId(configId);
		} catch (Exception e) {
			logger.error("获取保险公司配置信息失败",e);
			throw new BusinessServiceException("获取保险公司配置信息失败");
		}
		return list;
	}

	@Override
	public Map<String, Object> getMapByInsId(String configId)
			throws BusinessServiceException {
		logger.info("获取保险公司配置信息，返回Map集合 入参    配置信息ID："+configId);
		Map<String,Object> map = new HashMap<String,Object>();
		List<InsuranceCompanyConfigDTO> list = this.getInfoByInsId(configId);
		for (InsuranceCompanyConfigDTO insuranceCompanyConfigDTO : list) {
			map.put(insuranceCompanyConfigDTO.getKey(), insuranceCompanyConfigDTO.getValue());
		}
		return map;
	}

	@Override
	public Map<String, String> getMapByInsIdTwo(String configId)
			throws BusinessServiceException {
		logger.info("获取保险公司配置信息，返回Map集合 入参    配置信息ID："+configId);
		Map<String,String> map = new HashMap<String,String>();
		List<InsuranceCompanyConfigDTO> list = this.getInfoByInsId(configId);
		for (InsuranceCompanyConfigDTO insuranceCompanyConfigDTO : list) {
			map.put(insuranceCompanyConfigDTO.getKey(), insuranceCompanyConfigDTO.getValue());
		}
		return map;
	}

}
