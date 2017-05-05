package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.zxcl.webapp.dto.SmsAlarmConfigDTO;

/**
 * 短信监控配置人
 * @author zx
 *
 */
public interface SmsAlarmConfigDAO {
	
	/**
	 * 根据保险公司ID和告警短信类型获取短信监控配置人信息
	 * @param insId 保险公司ID
	 * @param warningSignalType 告警短信类型
	 * @return
	 * @throws Exception
	 */
	public List<SmsAlarmConfigDTO> getList(@Param("insId")String insId,@Param("warningSignalType")String warningSignalType) throws Exception;
	
	/**
	 * 根据保险公司ID和告警短信类型获取短信监控配置人的电话号码
	 * @param insId 保险公司ID
	 * @param warningSignalType 告警短信类型
	 * @return
	 * @throws Exception
	 */
	public String [] getPhones(@Param("insId")String insId,@Param("warningSignalType")String warningSignalType) throws Exception;
}
