package com.zxcl.webapp.biz.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

import com.zxcl.webapp.biz.service.SmsForAlarmService;
import com.zxcl.webapp.dto.SmsForAlarmDTO;
import com.zxcl.webapp.integration.dao.SmsForAlarmDAO;

@Service
public class SmsForAlarmServiceImpl implements SmsForAlarmService{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private SmsForAlarmDAO smsForAlarmDAO;

	@Override
	public void insert(SmsForAlarmDTO smsForAlarmDTO) throws BusinessServiceException {
		logger.info("保存短信监控用户密码信息 入参   smsForAlarmDTO："+smsForAlarmDTO);
		try {
			smsForAlarmDAO.insert(smsForAlarmDTO);
		} catch (Exception e) {
			logger.error("保存短信监控用户密码信息失败",e);
			throw new BusinessServiceException("保存短信监控用户密码信息失败");
		}
	}

	@Override
	public Integer getCount(String insId, String errorType, String noteTime)
			throws BusinessServiceException {
		logger.info("获取当天发送短信数量 入参    保险公司ID："+insId+"   短信类型："+errorType+"  时间："+noteTime);
		Integer i = 0;
		try {
			i = smsForAlarmDAO.getCount(insId, errorType, noteTime);
		} catch (Exception e) {
			logger.error("获取当天发送短信数量失败",e);
			throw new BusinessServiceException("获取当天发送短信数量失败");
		}
		return i;
	}
	
}
