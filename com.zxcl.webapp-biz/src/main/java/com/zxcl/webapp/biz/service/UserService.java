package com.zxcl.webapp.biz.service;

import org.apache.ibatis.annotations.Param;

import com.meyacom.fw.um.dto.Role;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.UserDTO;

/**
 * 用户service
 * @author 444
 *
 */
public interface UserService {

	public Role getRoleByUserId(String userId) throws BusinessServiceException;

	/**
	 * 根据userId 查询用户信息
	 * @param userId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public UserDTO queryUserByUserId(@Param("userId") String userId) throws BusinessServiceException;

	/**
	 * 添加用户反馈信息
	 * @param userId
	 * @param tel
	 * @param suggest
	 * @throws BusinessServiceException
	 */
	public void addSuggest(String userId, String suggest) throws BusinessServiceException;
}
