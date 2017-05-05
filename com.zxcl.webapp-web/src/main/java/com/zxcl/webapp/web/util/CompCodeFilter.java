package com.zxcl.webapp.web.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.zxcl.webapp.util.WeiXinUtils;


public class CompCodeFilter implements Filter {

	private String currentServerUrl;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) arg0;
		if("GET".equals(request.getMethod())){
			String compCode = request.getParameter("comp_code");
			if(null != compCode){
				request.getSession().setAttribute("comp_code", compCode);
			}
			String URL = request.getRequestURI();
			logger.info("URL:"+URL+",params:"+request.getQueryString());
			if(null != URL && URL.indexOf("index.do") != -1){
				boolean logined = false;
				try {
					
					String userId = null;
					logger.info("SESSION_ID:"+request.getSession().getId());
					SecurityContextImpl token = (SecurityContextImpl) request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
					logger.info("token:"+token);
					if(null != token){
						if(null == token.getAuthentication()){
							logger.info("token.getAuthentication() is null");
						}
						else{
							Object obj = token.getAuthentication().getPrincipal();
							logger.info("obj:"+obj);
							if(null != obj){
								if(obj instanceof String){
									userId = (String) obj;
								}
								else{
									UserDetails details = (UserDetails) obj;
									userId = details.getUsername();
								}
							}
							
							logger.info("USER_ID:"+userId);
							if(StringUtils.isNotBlank(userId)){
								logined = true;
							}
						}
					}
					else{
						logger.info("token is null");
					}
				} catch (Exception e) {
					//ignore
					logger.error("GETUSER:"+e.getMessage());
				}
				if(!logined){
					logger.info("IS NOT LOGINED");
					HttpServletResponse response = (HttpServletResponse) arg1;
					String userAgent = request.getHeader("User-Agent");
					try {
						if(StringUtils.isBlank(userAgent)){
							logger.info("CompCodeFilter:userAgent=null");
						}
						else if(userAgent.toLowerCase().indexOf("micromessenger") != -1){
							logger.info("CompCodeFilter:确定为微信浏览器");
							if(request.getParameter("code") != null){
								logger.info("自动登录中...");
							}
							else{
								response.sendRedirect(WeiXinUtils.getOauthBaseToIndex(currentServerUrl+"/bxtx/index.do"));
								return;
							}
						}
					} catch (Exception e) {
						logger.error("CompCodeFilter:重定向微信失败", e);
					}
				}
				else{
					logger.info("LOGINED");
				}
			}
		}
		arg2.doFilter(arg0, arg1);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		

	}

	public String getCurrentServerUrl() {
		return currentServerUrl;
	}

	public void setCurrentServerUrl(String currentServerUrl) {
		this.currentServerUrl = currentServerUrl;
	}

	
}
