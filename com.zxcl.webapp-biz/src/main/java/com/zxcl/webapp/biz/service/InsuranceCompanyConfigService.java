package com.zxcl.webapp.biz.service;

import java.util.List;
import java.util.Map;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.InsuranceCompanyConfigDTO;

/**
 * 获取保险公司配置信息
 * @author zx
 *
 */
public interface InsuranceCompanyConfigService {
	
	/**
	 * 根据保险公司ID获取配置信息
	 * @param configId
	 * @return List集合信息
	 * @throws BusinessServiceException
	 */
	public List<InsuranceCompanyConfigDTO> getInfoByInsId(String configId) throws BusinessServiceException;
	
	/**
	 * 根据保险公司ID获取配置信息
	 * @param configId
	 * @return Map信息
	 * @throws BusinessServiceException
	 */
	public Map<String,Object> getMapByInsId(String configId) throws BusinessServiceException;
	
	/**
	 * 根据保险公司ID获取配置信息
	 * @param configId 
	 * @return Map信息
	 * @throws BusinessServiceException
	 */
	public Map<String,String> getMapByInsIdTwo(String configId) throws BusinessServiceException;
}
