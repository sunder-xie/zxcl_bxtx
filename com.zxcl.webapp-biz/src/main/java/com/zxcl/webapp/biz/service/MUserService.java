package com.zxcl.webapp.biz.service;

import com.meyacom.fw.um.dto.UserInfo;
import com.zxcl.webapp.biz.exception.BusinessServiceException;

/**
 * @ClassName: 
 * @Description:权限相关 用户登录及获取用户信息
 * @author zxj
 * @date 
 */
public abstract interface MUserService {
	/**
	 * 登录认证
	 * @param userId
	 * @param password
	 * @param appId
	 * @param ip
	 * @return
	 * @throws BusinessServiceException
	 */
	public abstract boolean authenticate(String userId,
			String password,
			String appId,
			String ip) throws BusinessServiceException;


	/**
	 * 通过用户ID获取用户信息
	 * @param paramString
	 * @return
	 * @throws BusinessServiceException
	 */
	public abstract UserInfo getUser(String userId)
			throws BusinessServiceException;

}
