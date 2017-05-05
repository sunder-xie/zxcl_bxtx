package com.zxcl.webapp.web.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.meyacom.fw.um.security.SecurityHolder;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.impl.MicroServiceImpl;
import com.zxcl.webapp.dto.AgentDisplayDTO;
import com.zxcl.webapp.dto.ApiAppControlDTO;
import com.zxcl.webapp.integration.dao.AgentDisplayDAO;
import com.zxcl.webapp.integration.dao.ApiAppControlDAO;


/**
 * @author zxj
 * @date 2016年9月27日
 * @description 
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
    private MicroService microService;
	
	@Autowired
	private ApiAppControlDAO apiAppControlDAO;
	
	@Autowired
	private AgentDisplayDAO agentDisplayDAO;
	
	@Autowired
	private SecurityHolder securityHolder ;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean noAuth = true;//true有权限
		
		try {
			HttpSession session = request.getSession();
			String menuControl = "0";//菜单控制开关 1:开-隐藏  0关-显示
			
			//权限控制
			if("GET".equals(request.getMethod())){
				try {
		    		if(!microService.microIsAllowLogon(securityHolder.getAuthenticatedUserId(), null)){
//		    			request.getSession().invalidate();
		    			request.getSession().removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		    			SecurityContextHolder.getContext().setAuthentication(null);
		    			request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new Exception(MicroServiceImpl.tl.get()));
		    			response.sendRedirect(request.getContextPath()+"/logon.do?noAuth=1");
		    			noAuth = false;
		    		}
				} catch (Exception e) {
					logger.error(e.getMessage());
				} finally{
					MicroServiceImpl.tl.remove();
				}
			}
			
			
			//只针对第三方用户GET请求菜单控制
			if("GET".equals(request.getMethod()) && null != session && null != session.getAttribute("USER_APPID") && session.getAttribute("USER_APPID").toString().trim().length() > 0){
				logger.info("菜单控制开始");
				ApiAppControlDTO r = apiAppControlDAO.selectByAppId(session.getAttribute("USER_APPID").toString().trim(), "menu");
				if(null != r && null != r.getIsOn() && r.getIsOn().equals(1)){
					menuControl = "1";
				}
				logger.info("菜单控制结束,menuControl:"+menuControl);
			}
			request.setAttribute("menuControl", menuControl);
			
			
			//登录页面根据comp_code带出logo图片及title标题
			AgentDisplayDTO agentDisplayDTO = null;
			if("GET".equals(request.getMethod())){
				String compCode = request.getParameter("comp_code");
				Object sessionCompCode = session.getAttribute("comp_code");
				if(StringUtils.isBlank(compCode)){
					compCode = null != sessionCompCode?sessionCompCode.toString():null;
				}
				if(StringUtils.isNotBlank(compCode)){
					session.setAttribute("comp_code", compCode);
					agentDisplayDTO = agentDisplayDAO.selectByPrimaryKey(compCode);
					logger.info("agentDisplayDTO:"+JSONObject.toJSONString(agentDisplayDTO));
				}
				
				if(null == agentDisplayDTO){
					agentDisplayDTO = AgentDisplayDTO.defaultDisplay();
				}
			}
			request.setAttribute("agentDisplayDTO", agentDisplayDTO);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return noAuth;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
