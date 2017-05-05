package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.CorrespondDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;

public interface CorrespondService {
	
	public List<CoverageItemDTO>  getInsInfo(String insId, List<CoverageItemDTO> items) throws BusinessServiceException;

	public CorrespondDTO get(CorrespondDTO corr) throws BusinessServiceException;
	
	public CorrespondDTO getTwo(CorrespondDTO corr) throws BusinessServiceException;
	/**
	 * 根据保险公司ID获取
	 * @param insId
	 * @return
	 */
//	public List<CorrespondDTO> getCorrespondByInsId(String insId);
}
