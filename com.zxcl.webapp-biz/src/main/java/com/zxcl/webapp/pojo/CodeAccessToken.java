package com.zxcl.webapp.pojo;

/**
 * @author lenovo
 */
public class CodeAccessToken {
	// 获取到的凭证
	private String accessToken;
	
	// 凭证有效时间，单位：秒
	private int expiresIn;
	
	//用于刷新的token
	private String refreshToken;
	
	//openId
	private String openid;
	
	//作用域(snsapi_userinfo,snsapi_basic)
	private String scope;
	
	private CodeUserInfo codeUserInfo;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public CodeUserInfo getCodeUserInfo() {
		return codeUserInfo;
	}

	public void setCodeUserInfo(CodeUserInfo codeUserInfo) {
		this.codeUserInfo = codeUserInfo;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "CodeAccessToken [accessToken=" + accessToken + ", expiresIn="
				+ expiresIn + ", refreshToken=" + refreshToken + ", openid="
				+ openid + ", scope=" + scope + ", codeUserInfo="
				+ codeUserInfo + "]";
	}

	
	
}