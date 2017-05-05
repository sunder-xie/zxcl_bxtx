package com.zxcl.webapp.biz.service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.UserDTO;

public interface WeChatService {
	
	//更具微信id查询用户信息
	public UserDTO selectUserByWeChatid(String weChatId) throws BusinessServiceException;
	
	/**
	 * 根据userId 查询用户信息
	 * @param userId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public UserDTO queryUserByUserId( String userId) throws BusinessServiceException;
	
	/**
	 *  根据userId去绑定微信（将weChatId 插入到对应的数据中）
	 * @throws BusinessServiceException 
	 */
	public void updateWeChatId(String weChatId,String nickName,String userId) throws BusinessServiceException;
	
	/**
	 * 根据userId去解除绑定微信（将weChatId设为空字符串）
	 * @throws BusinessServiceException 
	 */
	public void deleteWeChatId(String userId) throws BusinessServiceException;

	/**
	 * 记录微信登录日志
	 * @param userId
	 */
	public void saveWechatLogonLog(String userId);

}
