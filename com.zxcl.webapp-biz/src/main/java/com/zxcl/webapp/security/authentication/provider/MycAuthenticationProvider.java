package com.zxcl.webapp.security.authentication.provider;

import com.meyacom.fw.um.security.client.ClientInfo;
import com.meyacom.fw.um.security.client.ClientInfoHolder;
import com.zxcl.webapp.biz.service.MUserService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.impl.MicroServiceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */
@Component("mycAuthenticationProviderWithbxtx")
public final class MycAuthenticationProvider implements AuthenticationProvider,
		InitializingBean, MessageSourceAware {
	protected Logger logger = Logger.getLogger(getClass());

	protected MessageSourceAccessor messages = SpringSecurityMessageSource
			.getAccessor();

	@Autowired
	@Qualifier("userDetailServiceImpl")
	protected UserDetailsService userDetailsService;

	@Autowired
	@Qualifier("saltSHA")
	protected PasswordEncoder passwordEncoder;
	private String userNotFoundEncodedPassword;

	
	@Autowired
	protected MUserService userService;
	
	@Autowired
	private MicroService microService;

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String username = authentication.getPrincipal() == null ? "NONE_PROVIDED"
				: authentication.getName();

		String clientIp = "";
		ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
		if (clientInfo != null) {
			clientIp = clientInfo.getClientIpAddress();
		}
		try {
			boolean result = microService.microIsAllowLogon(username, null);
			if (!result) {
				throw new AuthenticationServiceException(MicroServiceImpl.tl.get());
			}
			
			result = userService.authenticate(username,
					String.valueOf(authentication.getCredentials()), "",
					clientIp);

			if (!result) {
				throw new BadCredentialsException("用户密码不匹配");
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

		UserDetails userDetails = retrieveUser(username,
				(UsernamePasswordAuthenticationToken) authentication);

		return createSuccessAuthentication(userDetails, authentication,
				userDetails);
	}

	protected Authentication createSuccessAuthentication(Object principal,
			Authentication authentication, UserDetails user) {
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
				principal, authentication.getCredentials(),
				user.getAuthorities());
		result.setDetails(authentication.getDetails());
		return result;
	}

	protected final UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException

	{
		UserDetails loadedUser;
		try {
			loadedUser = getUserDetailsService().loadUserByUsername(username);
		} catch (UsernameNotFoundException notFound) {

			if (authentication.getCredentials() != null) {
				String presentedPassword = authentication.getCredentials()
						.toString();
				this.passwordEncoder.matches(this.userNotFoundEncodedPassword,
						presentedPassword);
			}

			throw notFound;
		} catch (Exception repositoryProblem) {
			throw new AuthenticationServiceException(
					repositoryProblem.getMessage(), repositoryProblem);
		}
		if (loadedUser == null) {
			throw new AuthenticationServiceException("加载用户信息失败");
		}

		return loadedUser;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public void afterPropertiesSet() throws Exception {
	}

	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication);
	}

	public UserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.userNotFoundEncodedPassword = passwordEncoder
				.encode("userNotFoundPassword");
	}
}