package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;

public interface CoverageItemService {
	/**
	 * 查询询价单险种信息
	 * 
	 * @param inquiryId
	 * @param microId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<CoverageItemDTO> getCoverageItems(String inquiryId,
			String microId) throws BusinessServiceException;

	public void insert(List<CoverageItemDTO> covers)
			throws BusinessServiceException;

	/**
	 * 根据询价单编码删除询价险别
	 */
	public void deleteCoverageItemsByInquiryId(String inquiryId)
			throws BusinessServiceException;
}
