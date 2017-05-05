package com.zxcl.webapp.web.security.authentication.provider;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.log4j.Logger;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.meyacom.fw.um.security.core.userdetails.MycSecurityUser;

@Component("casUserDetailsService2")
public class MycCasAttributesUserDetailsService extends AbstractCasAssertionUserDetailsService {
	protected Logger logger = Logger.getLogger(getClass());
	protected static final String DEFAULT_ROLE = "ROLE_USER";
	
	protected UserDetails loadUserDetails(Assertion assertion) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(DEFAULT_ROLE));

		MycSecurityUser scu = new MycSecurityUser(assertion.getPrincipal().getName(), authorities);
		Map<String, Object> attrs = assertion.getPrincipal().getAttributes();
		ConvertUtils.register(new DateConverter(null), Date.class);
		for (String str : attrs.keySet()) {
			try {
				BeanUtils.setProperty(scu, str, attrs.get(str));
			} catch (Exception e) {
				this.logger.fatal("无法在CAS登录后设置用户基本信息", e);
			}
		}
		return scu;
	}
}