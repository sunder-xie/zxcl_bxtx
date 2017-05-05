/**
 * 
 */
package com.zxcl.webapp.web.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author MAXiaoqiang
 *
 */
public class WeChatAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 
     */
    private static final long serialVersionUID = 8986008648678747470L;

    /**
     * 
     */
    private String weChatId;

    /**
     * 
     */
    private final Object principal;

    /**
     * 
     */
    private Object credentials;

    /**
     * 
     * @param principal
     * @param credentials
     * @param authorities
     */
    public WeChatAuthenticationToken(String weChatId, Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.weChatId = weChatId;
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.Authentication#getCredentials()
     */
    @Override
    public Object getCredentials() {
        return credentials;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.Authentication#getPrincipal()
     */
    @Override
    public Object getPrincipal() {
        return principal;
    }

    /**
     * 
     * @return
     */
    public String getWeChatId() {
        return weChatId;
    }

    @Override
    public String toString() {
        return "WeChatAuthenticationToken [weChatId=" + weChatId
                + ", principal=" + principal + ", credentials=" + credentials
                + "]";
    }

}
