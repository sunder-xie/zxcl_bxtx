package com.zxcl.webapp.web.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.meyacom.fw.um.security.SecurityHolder;
import com.zxcl.webapp.dto.LogLoginInfo;
import com.zxcl.webapp.integration.dao.LogLoginInfoDAO;
import com.zxcl.webapp.web.util.UserAgentUtils;

/**
 * @ClassName: 核保终端记录拦截器
 * @Description:
 * @author zxj
 * @date 
 */

@Component
public class AccountingLogInterceptor implements HandlerInterceptor {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SecurityHolder securityHolder;
	
	@Autowired
	private LogLoginInfoDAO logLoginInfoDAO;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("提交核保终端记录开始");
		String userId = securityHolder.getAuthenticatedUserId();
		String userAgent = request.getHeader("User-Agent");
		String SID = request.getSession().getId();
		boolean isMobile = UserAgentUtils.isMoblie(request);
		logger.info("userId="+userId+", isMobile="+isMobile+", userAgent="+userAgent+", SID="+SID);
		
		try {
			
			LogLoginInfo logLoginInfo = new LogLoginInfo();
			Date date = new Date();
			logLoginInfo.setIsMobile(isMobile?1:0);
			logLoginInfo.setUserAgent(StringUtils.isBlank(userAgent)?"不详":userAgent);
			logLoginInfo.setSid(SID);
			logLoginInfo.setCrtBy(StringUtils.isBlank(userId)?"system":userId);
			logLoginInfo.setUserId(logLoginInfo.getCrtBy());
			logLoginInfo.setUpdBy(logLoginInfo.getCrtBy());
			logLoginInfo.setCrtTm(date);
			logLoginInfo.setUpdTm(date);
			logLoginInfo.setRemark("提交核保,报价单号=" + request.getParameter("quotaId"));
			logLoginInfoDAO.insertSelective(logLoginInfo);
			
		} catch (Exception e) {
			logger.info("提交核保终端记录失败", e);
		}
		logger.info("提交核保终端记录结束");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
