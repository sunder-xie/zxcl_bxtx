package com.zxcl.webapp.biz.service;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.SmsForAlarmDTO;

/**
 * 短信监控用户密码
 * @author zx
 *
 */
public interface SmsForAlarmService {
	
	/**
	 * 保存短信监控用户密码信息
	 * @param smsForAlarmDTO 
	 * @throws Exception
	 */
	public void insert(SmsForAlarmDTO smsForAlarmDTO) throws BusinessServiceException;
	
	/**
	 * 获取当天发送短信数量
	 * @param insId 保险公司ID
	 * @param errorType 短信类型
	 * @param noteTime 时间（YYYY-MM-DD格式）
	 * @return
	 * @throws Exception
	 */
	public Integer getCount(@Param("insId")String insId,@Param("errorType")String errorType,@Param("noteTime")String noteTime) throws BusinessServiceException;
	
}
