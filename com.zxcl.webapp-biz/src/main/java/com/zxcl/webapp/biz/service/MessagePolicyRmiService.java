package com.zxcl.webapp.biz.service;


/**
 * 消息推送RMI调用接口
 * @author zxj
 * @date 2016年8月5日
 * @description 
 */
public interface MessagePolicyRmiService {
	
	/**
	 * 保单生成消息推送
	 * @param userIdList  用户ID列表
	 * @param message 消息
	 * @throws Exception
	 */
	public void sendPolicyMessage(String orderId, String insId) throws Exception;
	
}
