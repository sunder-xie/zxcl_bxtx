/**
 * 
 */
package com.zxcl.webapp.web.security.authentication.provider;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.zxcl.webapp.biz.service.MUserService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.WeChatService;
import com.zxcl.webapp.biz.service.impl.MicroServiceImpl;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.web.security.authentication.WeChatAuthenticationToken;

/**
 * @author MAXiaoqiang
 *
 */
@Component("weChatAuthenticationProvider")
public class WeChatAuthenticationProvider implements AuthenticationProvider, InitializingBean,
		MessageSourceAware {
	/**
     * 
     */
	protected Logger logger = Logger.getLogger(this.getClass());

	/**
     * 
     */
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	/**
     * 
     */
	@Autowired
	@Qualifier("userDetailServiceImpl")
	protected UserDetailsService userDetailsService;

	/**
     * 
     */
	@Autowired
	protected MUserService userService;

	/**
     * 
     */
	@Autowired
	@Qualifier("weChatService")
	public WeChatService weChatService;
	
	@Autowired
	private MicroService microService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// 此处完成微信端提交过来的认证信息的认真，然后获取到微信号对应的用户身份信息
		// 此处我写死只要有微信id，我就把它作为管理员身份。
		WeChatAuthenticationToken token = (WeChatAuthenticationToken) authentication;
		if (token.getWeChatId() != null) {
			// 只要有wechatid就认为认证通过，实际中需要将微信认证逻辑写到这里。
			// 查询用户相信信息
			try {
				boolean result = microService.microIsAllowLogon(null, token.getWeChatId());
				if (!result) {
					throw new AuthenticationServiceException(MicroServiceImpl.tl.get());
				}
			} catch (Exception ex) {
				if(ex instanceof AuthenticationServiceException){
					throw new AuthenticationServiceException(ex.getMessage(), ex);
				}
				else{
					throw new AuthenticationServiceException("用户密码不匹配", ex);
				}
			} finally{
				MicroServiceImpl.tl.remove();
			}
			String weChatId = token.getWeChatId();
			UserDTO userDTO = null;
			UserDetails userDetails = null;
			try {
				userDTO = weChatService.selectUserByWeChatid(weChatId);
				String username = userDTO.getUserId();
				userDetails = retrieveUser(username);
				
				//记录登录日志
				weChatService.saveWechatLogonLog(userDTO.getUserId());
			} catch (Exception e) {
				logger.error("根据:" + weChatId + "查询用户信息失败:" + e);
			}
			return createSuccessAuthentication(token.getWeChatId(), userDetails, token, userDetails);
		} else {
			throw new AuthenticationServiceException("登录失败");
		}
	}

	/**
	 * 此方法用来创建一个新的合法的AuthenticationToken对象. 其中应包括登录成功后用户的账户信息, 及其他详细信息.
	 * 
	 * 在AbstractAuthenticationToken类（WeChatAuthenticationToken父类）中，
	 * 有一个方法setDetails，
	 * 这个方法是用来给这个token设置更多其他信息，在我们框架的应用中，我们是将用户的基本信息，包括中文姓名、所属机构
	 * 、联系方式等，都封装到对象UserInfo中，然后作为UserDetails的是一个实现类设置进Token中。
	 * 你们可以参考MycAuthenticationProvider中的retrieveUser方法。 下面的实现我直接使用。
	 * 
	 * @param authentication
	 * @return
	 */
	protected Authentication createSuccessAuthentication(String weChatId, Object principal,
			Authentication authentication, UserDetails user) {
		WeChatAuthenticationToken result = new WeChatAuthenticationToken(weChatId, principal,
				authentication.getCredentials(), user.getAuthorities());
		result.setDetails(authentication.getDetails());
		return result;
	}

	/**
	 * 获取用户的其他基本信息, 这个完全可以不断扩展用户的属性。 直接从MycAuthenticationProvider拷贝过来并做了修改。
	 * 
	 * @param username
	 * @param authentication
	 * @return
	 * @throws AuthenticationException
	 */
	protected final UserDetails retrieveUser(String username) throws AuthenticationException {
		UserDetails loadedUser;
		try {
			loadedUser = this.getUserDetailsService().loadUserByUsername(username);
		} catch (UsernameNotFoundException notFound) {
			throw notFound;
		} catch (Exception repositoryProblem) {
			throw new AuthenticationServiceException(repositoryProblem.getMessage(),
					repositoryProblem);
		}
		if (loadedUser == null) {
			throw new AuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
		}
		return loadedUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.MessageSourceAware#setMessageSource(org.
	 * springframework.context.MessageSource)
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.authentication.AuthenticationProvider#supports
	 * (java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return (WeChatAuthenticationToken.class.isAssignableFrom(authentication));
	}

	/**
	 * @return the userDetailsService
	 */
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	/**
	 * @param userDetailsService
	 *            the userDetailsService to set
	 */
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}
