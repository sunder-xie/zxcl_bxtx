package com.zxcl.webapp.integration.dao;

import java.util.List;


import com.zxcl.webapp.dto.InsuranceCompanyConfigDTO;

/**
 * 保险公司配置信息DTO
 * @author zx
 *
 */
public interface InsuranceCompanyConfigDAO {
	
	/**
	 * 根据配置ID得到配置信息
	 * @param configId  保险公司ID+"_CONFIG"
	 * @return
	 * @throws Exception
	 */
	public List<InsuranceCompanyConfigDTO> getInfoByConfigId(String configId) throws Exception;
	
}
