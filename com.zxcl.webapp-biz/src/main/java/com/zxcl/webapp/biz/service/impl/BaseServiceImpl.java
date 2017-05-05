package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.BaseService;
import com.zxcl.webapp.dto.BaseDTO;
import com.zxcl.webapp.integration.dao.BaseDAO;

@Service
public class BaseServiceImpl implements BaseService{

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private BaseDAO baseDAO;
	
	@Override
	public List<BaseDTO> queryByCodeClass(String codeClass)
			throws BusinessServiceException {
		logger.info("queryByCodeClass codeClass:"+codeClass);
		List<BaseDTO> baseList = new ArrayList<BaseDTO>();
		try {
			baseList = baseDAO.queryByCodeClass(codeClass);
		} catch (Exception e) {
			logger.error("获取该编码对象的信息失败",e);
			throw new BusinessServiceException("获取该编码对象的信息失败");
		}
		return baseList;
	}

	@Override
	public BaseDTO queryByCode(String codeClass, String code)
			throws BusinessServiceException {
		logger.info("queryByCode codeClass:"+codeClass+"  code:"+code);
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = baseDAO.queryByCode(codeClass, code);
		} catch (Exception e) {
			logger.error("根据编码获取对象信息失败",e);
			throw new BusinessServiceException("根据编码获取对象信息失败");
		}
		return baseDTO;
	}

}
