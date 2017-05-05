package com.zxcl.webapp.util;

import org.apache.commons.lang.StringUtils;

import bxtx.intf.weixin.biz.service.WeiXinService;

/**
 * 微信帮助类
 * @author xiaoxi
 *
 */
public class WeiXinUtils {

	private static String appId;
	
	/**
	 * 认证方式为base,完成后至index.do
	 */
	private static String oauthBaseToIndex;
	
	private static String oauthUserInfoToBind;
	
	public static String getAppId(){
		if(StringUtils.isNotBlank(appId)){
			return appId;
		}
		WeiXinService wxService = SpringContextUtil.getBean(WeiXinService.class);
		appId = wxService.getAppId();
		return appId;
	}
	
	public static String getOauthBaseToIndex(String indexUrl){
		if(StringUtils.isNotBlank(oauthBaseToIndex)){
			return oauthBaseToIndex;
		}
		WeiXinService wxService = SpringContextUtil.getBean(WeiXinService.class);
		oauthBaseToIndex = wxService.oauth2buildAuthorizationUrl(indexUrl, "snsapi_base", "1").toLowerCase();
		return oauthBaseToIndex;
	}
	
	public static String getOauthUserInfoToBind(String bindUrl){
		if(StringUtils.isNotBlank(oauthUserInfoToBind)){
			return oauthUserInfoToBind;
		}
		WeiXinService wxService = SpringContextUtil.getBean(WeiXinService.class);
		oauthUserInfoToBind = wxService.oauth2buildAuthorizationUrl(bindUrl, "snsapi_userinfo", "1");
		return oauthUserInfoToBind;
	}
}
