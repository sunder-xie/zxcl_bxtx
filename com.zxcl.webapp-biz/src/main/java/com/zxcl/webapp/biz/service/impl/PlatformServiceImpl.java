package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.PlatformService;
import com.zxcl.webapp.dto.PlatformDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.integration.dao.PlatformDAO;

@Service
public class PlatformServiceImpl implements PlatformService {
	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	private PlatformDAO baseDao;

	@Override
	public List<PlatformDTO> getCodeClass(String codeClass) throws BusinessServiceException {
		logger.info("根据关键字获取平台基础信息 入参    codeClass："+codeClass);
		List<PlatformDTO> list = new ArrayList<PlatformDTO>();
		try { 
			list = baseDao.getByCodeClass(codeClass);
		} catch (Exception e) {
			logger.error("查询根据关键字:" + codeClass + "平台基础信息失败:" + e,e);
			throw new BusinessServiceException("根据关键字获取平台基础信息失败");
		}
		return list;
	}

	@Override
	public List<PlatformDTO> getByCode(String codeClass, List<CoverageItemDTO> coverageItems)
			throws BusinessServiceException {
		logger.info("查询根据关键字处理险别基础信息 入参    codeClass："+codeClass+"  List<CoverageItemDTO>："+coverageItems);
		List<PlatformDTO> list = new ArrayList<PlatformDTO>();
		try {
			list = baseDao.getByCode(codeClass, coverageItems);
		} catch (Exception e) {
			logger.error("查询根据关键字:" + codeClass + "处理险别基础信息失败:" + e,e);
			throw new BusinessServiceException("查询根据关键字处理险别基础信息失败");
		}
		return list;
	}

	@Override
	public PlatformDTO getPlatByCode(String codeClass, String code) throws BusinessServiceException {
		logger.info("获取平台基础信息 入参    codeClass:"+codeClass+"  code："+code);
		PlatformDTO platformDTO = null;
		try {
			platformDTO = baseDao.getPlatByCode(codeClass, code);
		} catch (Exception e) {
			logger.error("查询根据关键字:" + codeClass + "和平台编码:" + code + "处理险别基础信息失败:" + e,e);
			throw new BusinessServiceException("获取平台基础信息失败");
		}
		return platformDTO;
	}
}
