package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.RelyInsuranceService;
import com.zxcl.webapp.dto.RelyInsuranceDTO;
import com.zxcl.webapp.integration.dao.RelyInsuranceDAO;

@Service
public class RelyInsuranceServiceImpl implements RelyInsuranceService{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private RelyInsuranceDAO relyInsuranceDAO;

	@Override
	public List<RelyInsuranceDTO> getAll() throws BusinessServiceException {
		List<RelyInsuranceDTO> list = new ArrayList<RelyInsuranceDTO>();
		try {
			list = relyInsuranceDAO.getAll();
		} catch (Exception e) {
			logger.error("查询所有失败",e);
			throw new BusinessServiceException("查询所有失败");
		}
		return list;
	}

}
