package com.zxcl.webapp.util.constants;

public class WXMessageConstant {
	
	
	/**
	 * 发送模板消息URL
	 * @param access_token
	 */
	public static final String WX_SENDMSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	/**
	 * 获得模板ID的URL
	 * @param access_token
	 */
	public static final String WX_SENDMSG_GETTMPID_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=";
	
	/**
	 * 微信群发URL
	 * @param access_token
	 */
	public static final String WX_SENDMSG_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=";
	
	
	/**
	 * 投保成功提醒
	 */
	public static final String WECHAT_MESSAGE_TEMPLATE_1 = "投保成功提醒";
	
	/**
	 * 审核通过提醒
	 */
	public static final String WECHAT_MESSAGE_TEMPLATE_2 = "提现审核通知";
	
	/**
	 * 提现成功通知
	 */
	public static final String WECHAT_MESSAGE_TEMPLATE_3 = "提现成功通知";
	
	/**
	 * 提现失败通知
	 */
	public static final String WECHAT_MESSAGE_TEMPLATE_4 = "提现失败通知";
	
	/**
	 * 提现失败通知
	 */
	public static final String WECHAT_MESSAGE_TEMPLATE_5 = "人工报价通知";
	
}
