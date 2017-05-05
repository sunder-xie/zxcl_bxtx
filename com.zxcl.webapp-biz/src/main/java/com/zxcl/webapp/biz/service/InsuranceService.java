package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**	
 * 保险公司
 * 
 * @author 5555
 *
 */
public interface InsuranceService {

	/**
	 * 根据code 获取单个保险公司的信息
	 * 
	 * @param code
	 * @return
	 * @throws BusinessServiceException 
	 */
	public InsuranceDTO get(String code) throws BusinessServiceException;
	
}
