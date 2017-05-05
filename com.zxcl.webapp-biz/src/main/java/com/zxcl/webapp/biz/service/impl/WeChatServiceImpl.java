package com.zxcl.webapp.biz.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meyacom.fw.um.security.client.ClientInfo;
import com.meyacom.fw.um.security.client.ClientInfoHolder;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.WeChatService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.LoginLoggingDTO;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.integration.dao.LoginLoggingDAO;
import com.zxcl.webapp.integration.dao.UserDAO;

@Service("weChatService")
public class WeChatServiceImpl implements WeChatService {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private UserDAO userDao;
	
	@Autowired
    private LoginLoggingDAO logLoginDAO;

	@Override
	@Log("根据小微ID得到用户信息")
	public UserDTO selectUserByWeChatid(String weChatId) throws BusinessServiceException {
		logger.info("WeChatServiceImpl  selectUserByWeChatid 入参 ==>小微openId:"+weChatId);
		UserDTO userDTO = null;
		try {
			userDTO =  userDao.queryUser(weChatId);
		} catch (Exception e) {
			logger.error("根据weChatId:" + weChatId + "获取用户信息失败:" + e.getMessage(),e);
			throw new BusinessServiceException("根据小微ID得到用户信息失败");
		}
		return userDTO;
	}

	@Override
	public UserDTO queryUserByUserId(String userId) throws BusinessServiceException {
		logger.info("根据用户ID得到用户信息 入参    用户ID："+userId);
		UserDTO userDTO = null;
		try {
			userDTO = userDao.queryUserByUserId(userId);
		} catch (Exception e) {
			logger.error("获取用户:" + userId + "信息失败:" + e.getMessage(),e);
			throw new BusinessServiceException("根据用户ID得到用户信息失败");
		}
		return userDTO;
	}

	@Override
	public void updateWeChatId(String weChatId, String nickName, String userId)
			throws BusinessServiceException {
		logger.info("更新用户:" + userId + "的信息 入参    小微ID："+weChatId+" nickName："+nickName+" 用户ID："+userId);
		try {
			userDao.updateWeChatId(weChatId, nickName, userId);
		} catch (Exception e) {
			logger.error("更新用户:" + userId + "的信息失败:" + e.getMessage(),e);
			throw new BusinessServiceException("更新用户:" + userId + "的信息失败");
		}
	}

	@Override
	public void deleteWeChatId(String userId) throws BusinessServiceException {
		logger.info("根据userId去解除绑定微信（将weChatId设为空字符串） 入参    用户ID："+userId);
		try {
			userDao.deleteWeChatId(userId);
		} catch (Exception e) {
			logger.error("删除用户:" + userId + "的WeChatId失败:" + e.getMessage(),e);
			throw new BusinessServiceException("根据userId去解除绑定微信失败");
		}
	}

	@Override
	public void saveWechatLogonLog(String userId) {
		try {
			ClientInfo ci = ClientInfoHolder.getClientInfo();

	        String clientip;
	        if (ci != null) {
	            clientip = ci.getClientIpAddress();
	        } else {
	            clientip = "";
	        }
	        String serverIp;
	        if (ci != null) {
	            serverIp = ci.getServerIpAddress();
	        } else {
	            serverIp = "";
	        }
	        
			LoginLoggingDTO loginLog = new LoginLoggingDTO();
	        loginLog.setAppId("");
	        loginLog.setAppName("");
	        loginLog.setRemoteIPaddress(clientip);
	        loginLog.setServerIPaddress(serverIp);
	        loginLog.setUserId(userId);
	        loginLog.setLoginDateTime(new java.util.Date());
	        loginLog.setResult("1");
	        loginLog.setReason("微信登录成功");
	        logLoginDAO.insertLogLogin(loginLog);
	        
	        
	        logger.info("微信登录写登录日志成功");
		} catch (Exception e) {
			logger.error("微信登录写登录日志失败", e);
		}
	}

}
