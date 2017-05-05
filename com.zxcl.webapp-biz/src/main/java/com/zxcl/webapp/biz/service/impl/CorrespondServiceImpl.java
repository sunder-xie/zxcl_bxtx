package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.CorrespondService;
import com.zxcl.webapp.dto.CorrespondDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.integration.dao.CorrespondDAO;

@Service
public class CorrespondServiceImpl implements CorrespondService {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private CorrespondDAO corrDao;

	// @Override
	// public List<CoverageItemDTO> getInsInfo(String insId,
	// List<CoverageItemDTO> items) {
	// List<CorrespondDTO> insInfo = corrDao.getInsInfo(insId, items);
	// // 处理险种
	// List<CoverageItemDTO> coverages = new ArrayList<CoverageItemDTO>();
	// for (CorrespondDTO corres : insInfo) {
	// if (corres.getIns_id().equals(insId)) {
	// for (CoverageItemDTO item : items) {
	// if (item.getCoverageCode().equals(corres.getCode())) {
	// item.setCoverageCode(corres.getIns_code());
	// item.setCoverageName(corres.getName());
	// coverages.add(item);
	// }
	// }
	// }
	// }
	// return coverages;
	// }

	/**
	 * 险种
	 */
	@Override
	public List<CoverageItemDTO> getInsInfo(String insId, List<CoverageItemDTO> items)
			throws BusinessServiceException {
		logger.info("合并保险公司:" + insId + "险别信息" + "入参    保险公司ID："+insId+"  List<CoverageItemDTO>："+items);
		List<CoverageItemDTO> coverages = new ArrayList<CoverageItemDTO>();
		try {
			List<CorrespondDTO> insInfo = corrDao.getInsInfo(insId, items);
			coverages = new ArrayList<CoverageItemDTO>();
			for (CorrespondDTO corres : insInfo) {
				if (corres.getIns_id().equals(insId)) {
					for (CoverageItemDTO item : items) {
						if (item.getCode().equals(corres.getCode())) {
							item.setCode(corres.getIns_code());
							item.setName(corres.getName());
							coverages.add(item);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("合并保险公司:" + insId + "险别信息失败:" + e,e);
			throw new BusinessServiceException("合并保险公司:" + insId + "险别信息失败");
		}
		return coverages;

	}

	/**
	 * 获取单个
	 */
	public CorrespondDTO get(CorrespondDTO corr) throws BusinessServiceException {
		logger.info("根据平台的基础信息查询保险公司的基础信息 入参    CorrespondDTO："+corr);
		CorrespondDTO correspondDTO = null;
		try {
			correspondDTO = corrDao.get(corr);
		} catch (Exception e) {
			logger.error("根据平台的基础信息查询保险公司的基础信息失败:" + e,e);
			throw new BusinessServiceException("根据平台的基础信息查询保险公司的基础信息失败");
		}
		return correspondDTO;
	}
	
	/**
	 * 获取单个
	 */
	public CorrespondDTO getTwo(CorrespondDTO corr) throws BusinessServiceException {
		logger.info("根据平台的基础信息查询保险公司的基础信息入参    CorrespondDTO："+corr);
		CorrespondDTO correspondDTO = null;
		try {
			correspondDTO = corrDao.getTwo(corr);
		} catch (Exception e) {
			logger.error("根据平台的基础信息查询保险公司的基础信息失败:" + e);
			throw new BusinessServiceException("根据平台的基础信息查询保险公司的基础信息失败");
		}
		return correspondDTO;
	}
}
