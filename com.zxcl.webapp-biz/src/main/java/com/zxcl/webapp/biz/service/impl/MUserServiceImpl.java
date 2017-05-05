package com.zxcl.webapp.biz.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meyacom.fw.app.biz.service.ServiceException;
import com.meyacom.fw.um.dto.UserInfo;
import com.meyacom.fw.um.security.client.ClientInfo;
import com.meyacom.fw.um.security.client.ClientInfoHolder;
import com.meyacom.fw.um.security.crypto.password.SaltSHAPasswordEncoder;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MUserService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.LoginLoggingDTO;
import com.zxcl.webapp.integration.dao.LoginLoggingDAO;
import com.zxcl.webapp.integration.dao.UserDAO;

/**
 * @ClassName: 
 * @Description:权限相关 用户登录及获取用户信息
 * @author zxj
 * @date 
 */

@Service
public class MUserServiceImpl implements MUserService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	protected UserDAO userDAO;
	
	@Autowired
    private LoginLoggingDAO logLoginDAO;
	
	protected SaltSHAPasswordEncoder saltSHA;
	
	public MUserServiceImpl() {
        saltSHA = new com.meyacom.fw.um.security.crypto.password.SaltSHAPasswordEncoder();
    }

	@Override
	@Log("登录认证")
	public boolean authenticate(String userId, String password, String appId, String ip) throws BusinessServiceException {
		logger.debug("开始验证用户密码");
        userId = convertToLowercase(userId);
        UserInfo userInfo = queryUserInfo(userId);
        return authInternal(userInfo, password, appId, ip);
	}
	
	/**
     * 转换成小写的方法
     * 
     * @param str
     *            传入的字符串
     * @return
     */
    protected String convertToLowercase(String str) {
        if (str != null) {
            return str.toLowerCase();
        } else {
            return null;
        }
    }
    
    /**
     * 查询用户信息userInfo
     * 
     * @param userId
     *            um账号
     * @return
     */
    protected UserInfo queryUserInfo(String userId) {
        if (userId != null) {
            return userDAO.queryUser2(userId);
        } else {
            return null;
        }
    }

	@Override
	@Log("通过用户ID获取用户信息")
	public UserInfo getUser(String userId) throws BusinessServiceException {
		logger.debug("开始查询用户信息");
        userId = convertToLowercase(userId);
        UserInfo userInfo = queryUserInfo(userId);
        if (logger.isDebugEnabled()) {
            logger.debug("查询到的用户信息为" + userInfo);
        }
        return userInfo;
	}
	
	
	 /**
     * 权限认证
     * 
     * @param userInfo
     *            用户信息
     * @param password
     *            密码
     * @param appId
     *            调用的系统
     * @param ip
     *            调用的系统ip
     * @return boolean 认证是否成功
     * @throws ServiceException
     */
    boolean authInternal(UserInfo userInfo, String password, String appId,
            String ip) throws BusinessServiceException {
        // 检查参数是否有效.
        if (userInfo == null || userInfo.getUserId() == null) {
            return false;
        }
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
        // 设置属性.
        String userId = userInfo.getUserId();
        LoginLoggingDTO loginLog = new LoginLoggingDTO();
        loginLog.setAppId(appId == null ? "" : appId);
        loginLog.setAppName("");
        loginLog.setRemoteIPaddress(clientip);
        loginLog.setServerIPaddress(serverIp);
        loginLog.setUserId(userId);
        loginLog.setLoginDateTime(new java.util.Date());

        try {
            // modify by zxj 添加用户锁定时的处理
            if (!"A".equals(userInfo
                    .getUserStatus())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("用户状态不是激活状态，不允许登录 :" + userInfo);
                }
                loginLog.setResult("0");
                loginLog.setReason("用户被锁定");
                return false;
            }
            // 身份验证
            if (password == null || userInfo.getLoginPassword() == null) {
                loginLog.setResult("0");
                return false;
            }
            try {
                // 因为如果密码为空，验证时会有数组越界的异常，在这里进行捕捉
                boolean result = saltSHA.matches(password,
                        userInfo.getLoginPassword());
                if (logger.isDebugEnabled()) {
                    logger.debug("用户密码验证结果为 " + result);
                }
                loginLog.setResult(result ? "1" : "0");
                loginLog.setReason(result ? "密码验证成功" : "密码验证失败");
                return result;
            } catch (Exception e) {
                logger.error("验证用户密码出错", e);
                loginLog.setReason("密码验证失败");
                loginLog.setResult("0");
                return false;
            }
        } finally {
            try {
                logLoginDAO.insertLogLogin(loginLog);
                logger.info("写登录日志成功");
            } catch (Exception ex) {
                logger.warn("写登录日志发生错误", ex);
            }
        }
    }

}
