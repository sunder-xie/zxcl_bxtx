package com.zxcl.webapp.biz.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

import com.zxcl.webapp.biz.service.SmsSendConfigService;
import com.zxcl.webapp.integration.dao.SmsSendConfigDAO;

@Service
public class SmsSendConfigServiceImpl implements SmsSendConfigService{
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SmsSendConfigDAO smsSendConfigDAO;
	
	@Override
	public String[] getPhone(String insId, String agentId)
			throws BusinessServiceException {
		logger.info("根据保险公司ID和代理公司ID获取电话号码 入参    保险公司ID："+insId+" 中介公司ID："+agentId);
		String [] phones = null;
		try {
			phones = smsSendConfigDAO.getPhone(insId, agentId);
		} catch (Exception e) {
			logger.error("查询出单人员电话号码失败",e);
			throw new BusinessServiceException("根据保险公司ID和代理公司ID获取电话号码失败");
		}
		return phones;
	}

}
