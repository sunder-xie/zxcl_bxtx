package com.zxcl.webapp.biz.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.integration.dao.InsuranceDAO;

@Service
public class InsuranceServiceImpl implements InsuranceService {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private InsuranceDAO insDao;

	// public List<InsuranceDTO> getHeadOffice() {
	// try {
	// return insDao.getHeadOffice();
	// } catch (Exception e) {
	// logger.equals("无参数.从基础信息表中获取所有的保险公司.");
	// }
	// return null;
	// }

	@Override
	public InsuranceDTO get(String code) throws BusinessServiceException {
		logger.info("根据code获取保险公司信息 入参    code："+code);
		InsuranceDTO insuranceDTO = null;
		try {
			insuranceDTO = insDao.get(code);
		} catch (Exception e) {
			logger.error("获取编码为:" + code + "的保险公司:" + e,e);
			throw new BusinessServiceException("根据code获取保险公司信息失败");
		}
		return insuranceDTO;
	}
}
