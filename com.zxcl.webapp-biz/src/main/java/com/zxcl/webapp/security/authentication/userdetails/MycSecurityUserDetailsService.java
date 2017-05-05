package com.zxcl.webapp.security.authentication.userdetails;

import com.meyacom.fw.um.dto.UserInfo;
import com.meyacom.fw.um.security.core.userdetails.MycSecurityUser;
import com.zxcl.webapp.biz.service.MUserService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */
@Component("userDetailServiceImpl")
public class MycSecurityUserDetailsService implements UserDetailsService {
	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	protected MUserService userService;

	public final UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		this.logger.debug("查询登录用户信息");
		UserInfo user = obtionSecurityUser(username);
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("登录用户信息为" + user);
		}
		if (user != null) {
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			
			MycSecurityUser scu = new MycSecurityUser(username, authorities);
			try {
				ConvertUtils.register(new DateConverter(null), Date.class);
				
				BeanUtils.copyProperties(scu, user);
			} catch (Exception e) {
				this.logger.fatal("复制用户属性时发生错误", e);
			}
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("返回登录用户信息为" + scu);
			}
			return scu;
		}
		throw new UsernameNotFoundException(username);
	}

	final UserInfo obtionSecurityUser(String username) {
		try {
			this.logger.debug("查询用户信息");
			UserInfo userInfo = this.userService.getUser(username);
			this.logger.debug("检索到用户信息为");
			this.logger.debug(userInfo);
			return userInfo;
		} catch (Exception e) {
			this.logger.fatal("查询登录用户信息失败", e);
		}
		return null;
	}
}