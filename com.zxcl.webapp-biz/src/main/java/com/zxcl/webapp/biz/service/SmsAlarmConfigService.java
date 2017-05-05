package com.zxcl.webapp.biz.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.SmsAlarmConfigDTO;

/**
 * 短信监控配置人
 * @author zx
 *
 */
public interface SmsAlarmConfigService {
	
	/**
	 * 根据保险公司ID和告警短信类型获取短信监控配置人信息
	 * @param insId 保险公司ID
	 * @param warningSignalType 告警短信类型
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<SmsAlarmConfigDTO> getList(@Param("insId")String insId,@Param("warningSignalType")String warningSignalType) throws BusinessServiceException;
	
	/**
	 * 根据保险公司ID和告警短信类型获取短信监控配置人的电话号码
	 * @param insId 保险公司ID
	 * @param warningSignalType 告警短信类型
	 * @return
	 * @throws BusinessServiceException
	 */
	public String [] getPhones(@Param("insId")String insId,@Param("warningSignalType")String warningSignalType) throws BusinessServiceException;
}
