package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.RelyInsuranceDTO;

/**
 *  有车型查询依赖关系的保险公司Service
 * @author zx
 *
 */
public interface RelyInsuranceService {
	/**
	 * 查询所有
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<RelyInsuranceDTO> getAll() throws BusinessServiceException;
	
}
