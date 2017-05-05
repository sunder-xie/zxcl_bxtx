package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.PlatformDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;

/**
 * 平台基础信息
 * 
 * @author 5555
 *
 */
public interface PlatformService {

	public List<PlatformDTO> getCodeClass(String codeClass) throws BusinessServiceException;
	
	/**
	 * 根据险种信息
	 * 
	 * @param coverageItems
	 * @return
	 * @throws BusinessServiceException 
	 */
	public List<PlatformDTO> getByCode(String codeClass,List<CoverageItemDTO> coverageItems) throws BusinessServiceException;

	public PlatformDTO getPlatByCode(String codeClass, String code) throws BusinessServiceException;

}
