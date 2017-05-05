package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.CoverageItemService;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.integration.dao.CoverageItemDAO;

@Service
public class CoverageItemServiceImpl implements CoverageItemService {

	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private CoverageItemDAO corrDAO;

	@Override
	public List<CoverageItemDTO> getCoverageItems(String inquiryId,
			String microId) throws BusinessServiceException {
		logger.info("查询询价单险种信息入参    询价单号："+inquiryId+"  小微ID："+microId);
		List<CoverageItemDTO> list = new ArrayList<CoverageItemDTO>();
		try {
			list = corrDAO.getCoverageItems(inquiryId, microId);
		} catch (Exception e) {
			logger.error("查询小薇:" + microId + "询价单:" + inquiryId + "险种信息失败:" + e,e);
			throw new BusinessServiceException("查询询价单险种信息失败");
		}
		return list;
	}

	@Override
	public void insert(List<CoverageItemDTO> covers)
			throws BusinessServiceException {
		logger.info("插入询价单险种信息 入参    List<CoverageItemDTO>："+covers);
		try {
			corrDAO.insert(covers);
		} catch (Exception e) {
			logger.error("插入询价单险种信息失败:" + e,e);
			throw new BusinessServiceException("插入询价单险种信息失败");
		}
	}

	@Override
	public void deleteCoverageItemsByInquiryId(String inquiryId) throws BusinessServiceException{
		logger.info("根据询价单编码删除询价险别 入参    询价单号："+inquiryId);
		try {
			corrDAO.deleteCoverageItemsByInquiryId(inquiryId);
		} catch (Exception e) {
			logger.error("更新询价单险种信息失败:" + e,e);
			throw new BusinessServiceException("根据询价单编码删除询价险别失败");
		}
	}
}
