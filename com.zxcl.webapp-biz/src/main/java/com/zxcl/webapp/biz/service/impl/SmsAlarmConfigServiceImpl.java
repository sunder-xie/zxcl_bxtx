package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

import com.zxcl.webapp.biz.service.SmsAlarmConfigService;
import com.zxcl.webapp.dto.SmsAlarmConfigDTO;
import com.zxcl.webapp.integration.dao.SmsAlarmConfigDAO;

@Service
public class SmsAlarmConfigServiceImpl implements SmsAlarmConfigService{
	
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SmsAlarmConfigDAO smsAlarmConfigDAO;

	@Override
	public List<SmsAlarmConfigDTO> getList(String insId,
			String warningSignalType) throws BusinessServiceException {
		logger.info("根据保险公司ID和告警短信类型获取短信监控配置人信息 入参   保险公司ID："+insId+"   告警短信类型："+warningSignalType);
		List<SmsAlarmConfigDTO> list = new ArrayList<SmsAlarmConfigDTO>();
		try {
			list = smsAlarmConfigDAO.getList(insId, warningSignalType);
		} catch (Exception e) {
			logger.error("根据保险公司ID和告警短信类型获取短信监控配置人信息失败",e);
			throw new BusinessServiceException("根据保险公司ID和告警短信类型获取短信监控配置人信息失败");
		}
		return list;
	}

	@Override
	public String[] getPhones(String insId, String warningSignalType)
			throws BusinessServiceException {
		logger.info("根据保险公司ID和告警短信类型获取短信监控配置人的电话号码 入参   保险公司ID："+insId+"   告警短信类型："+warningSignalType);
		String [] phones= null;
		try {
			phones = smsAlarmConfigDAO.getPhones(insId, warningSignalType);
		} catch (Exception e) {
			logger.error("根据保险公司ID和告警短信类型获取短信监控配置人的电话号码失败",e);
			throw new BusinessServiceException("根据保险公司ID和告警短信类型获取短信监控配置人的电话号码失败");
		}
		return phones;
	}
}
