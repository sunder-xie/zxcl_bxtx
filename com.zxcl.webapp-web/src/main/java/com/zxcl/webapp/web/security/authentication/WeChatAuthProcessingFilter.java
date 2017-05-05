/**
 * 
 */
package com.zxcl.webapp.web.security.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import bxtx.intf.weixin.biz.service.WeiXinService;
import bxtx.intf.weixin.dto.WxUserInfoDTO;

import com.meyacom.fw.um.security.SecurityHolder;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.WeChatService;
import com.zxcl.webapp.dto.UserDTO;

/**
 * @author MAXiaoqiang
 *
 */
public class WeChatAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {
	/**
     * 
     */
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	@Qualifier("weChatService")
	public WeChatService weChatService;
	
	@Autowired
	private SecurityHolder securityHolder;

	@Autowired
	private WeiXinService weiXinService;
	
	/**
     * 
     */
	public WeChatAuthProcessingFilter() {
		super("/j_wechat_security_check");
	}

	/**
	 * @param defaultFilterProcessesUrl
	 */
	public WeChatAuthProcessingFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	/**
	 * @param requiresAuthenticationRequestMatcher
	 */
	public WeChatAuthProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AbstractAuthenticationProcessingFilter
	 * #attemptAuthentication(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request,
//			HttpServletResponse response) throws AuthenticationException, IOException,
//			ServletException {
//		logger.debug("开始进行微信验证");
//		String weChatId = request.getParameter("weChatId");
//		if (logger.isDebugEnabled()) {
//			logger.debug("微信ID " + weChatId);
//		}
//		final WeChatAuthenticationToken token = new WeChatAuthenticationToken(weChatId, null, null,
//				null);
//		return this.getAuthenticationManager().authenticate(token);
//	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException,	ServletException {
		logger.debug("开始进行微信验证");
		String weChatId = (String) request.getAttribute("openid");
		if (logger.isDebugEnabled()) {
			logger.debug("微信ID " + weChatId);
		}
		WeChatAuthenticationToken token = new WeChatAuthenticationToken(weChatId, null, null, null);
		try {
			return this.getAuthenticationManager().authenticate(token);
		} catch (Exception e) {
			logger.error(e.getMessage());
			request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", e);
			response.sendRedirect(request.getContextPath()+"/logon.do?noAuth=1");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AbstractAuthenticationProcessingFilter
	 * #successfulAuthentication(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
					+ authResult);
		}
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AbstractAuthenticationProcessingFilter
	 * #requiresAuthentication(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
//	@Override
//	protected boolean requiresAuthentication(final HttpServletRequest request,
//			final HttpServletResponse response) {
//		logger.debug("判断是否走微信验证");
//		String weChatId = request.getParameter("weChatId");
//		String id = request.getParameter("id");
//		String md5s = Md5(weChatId + "3ef80716ef7c60dbd9d1c18918447d66");
//		try {
//			UserDTO userDTO = weChatService.selectUserByWeChatid(weChatId);
//			if (null == userDTO) {
//				return false;
//			}
//		} catch (BusinessServiceException e) {
//			logger.error("根据用户的weChatId:" + weChatId + "查询用户信息失败:" + e);
//			return false;
//		}
//		return request.getParameter("weChatId") != null && md5s.equals(id);
//	}
	
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("判断是否走微信验证");
		
		WxUserInfoDTO cat = null;
		String weChatId = null;
		
		HttpSession session = request.getSession();
		if(null != request.getParameter("invitation") && StringUtils.isNotBlank(request.getParameter("invitation"))){
			session.setAttribute("rinvitation", request.getParameter("invitation"));
		}
				
		try {
			String code = request.getParameter("code");
			if(StringUtils.isBlank(code)){
				return false;
			}
			cat = weiXinService.getUserInfoByCode(code);
			if(cat == null){
				return false;
			}
			
			weChatId = cat.getOpenId();
			if (StringUtils.isBlank(weChatId)) {
				return false;
			}
			request.getSession().setAttribute("ropenId", weChatId);
			long tamp = System.currentTimeMillis();
			request.setAttribute("check_tamp", tamp);
			request.setAttribute("openid", weChatId);
			String nickname = cat.getNickname();
			request.setAttribute("nickname", nickname);
			
			try {
				if(StringUtils.isNotBlank(securityHolder.getAuthenticatedUserId())){
					logger.debug("判断是否走微信验证==>检测到已登录 否");
					return false;
				}
			} catch (Exception e) {
			}
			
			
			UserDTO userDTO = weChatService.selectUserByWeChatid(weChatId);
			if (null == userDTO || StringUtils.isBlank(userDTO.getWechartId())) {
				request.removeAttribute("check_tamp");
				logger.debug("判断是否走微信验证==>否");
				return false;
			}
			
		} catch (BusinessServiceException e) {
			logger.error("根据用户的weChatId:" + weChatId + "查询用户信息失败:" + e);
			return false;
		}
		logger.debug("判断是否走微信验证==>是");
		return true;
	}

//	private String Md5(String plainText) {
//		try {
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(plainText.getBytes());
//			byte b[] = md.digest();
//
//			int i;
//
//			StringBuffer buf = new StringBuffer("");
//			for (int offset = 0; offset < b.length; offset++) {
//				i = b[offset];
//				if (i < 0)
//					i += 256;
//				if (i < 16)
//					buf.append("0");
//				buf.append(Integer.toHexString(i));
//			}
//			return buf.toString();
//			// System.out.println("result: " + buf.toString());//32位的加密
//
//			// System.out.println("result: " +
//			// buf.toString().substring(8,24));//16位的加密
//
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//
//	}

}
