package com.zxcl.webapp.biz.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meyacom.fw.um.dto.Role;
import com.zxcl.webapp.biz.exception.BusinessServiceException;

import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.integration.dao.UserDAO;

/**
 * 用户service实现
 * 
 * @author 444
 *
 */
@Service
public class UserServiceImpl implements UserService {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private UserDAO userDao;

	@Override
	public Role getRoleByUserId(String userId)throws BusinessServiceException {
		logger.info("根据用户ID获取角色 入参    用户ID："+userId);
		Role role = null;
		try {
			role = userDao.getRoleByUserId(userId);
		} catch (Exception e) {
			logger.error("获取用户:" + userId + "的角色信息失败:" + e,e);
			throw new BusinessServiceException("根据用户ID获取角色失败");
		}
		return role;
	}

	@Override
	public UserDTO queryUserByUserId(String userId) throws BusinessServiceException{
		logger.info("根据用户ID获取用户DTO入参 ,用户ID："+userId);
		UserDTO userDTO = null;
		try {
			userDTO = userDao.queryUserByUserId(userId);
		} catch (Exception e) {
			logger.error("获取用户:" + userId + "的信息失败:" + e,e);
			throw new BusinessServiceException("根据用户ID获取用户DTO失败");
		}
		return userDTO;
	}

	@Override
	public void addSuggest(String userId, String suggest) throws BusinessServiceException {
		logger.info("添加用户反馈信息 入参    用户ID："+userId+" 反馈内容:"+ suggest);
		try {
			userDao.addSuggest(userId, suggest);
		} catch (Exception e) {
			logger.error("用户:" + userId + "添加用户反馈信息失败:" + e,e);
			throw new BusinessServiceException("添加用户反馈信息失败");
		}
	}
}
