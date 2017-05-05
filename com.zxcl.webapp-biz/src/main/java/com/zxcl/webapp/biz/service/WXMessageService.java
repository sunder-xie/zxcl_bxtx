package com.zxcl.webapp.biz.service;

import java.util.List;
import java.util.Map;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.wxmsg.SendTempMsgItemDTO;

/**
 * @ClassName: 
 * @Description:微信消息推送接口
 * @author zxj
 * @date 2016年4月11日 10:45:14
 */
public interface WXMessageService {
	/**
	 * 推送微信模版消息(直接发送)
	 * @param templateId  微信消息推送模版ID
	 * @param data 模版内容
	 * @param openId 消息接受者
	 * @throws BusinessServiceException
	 * @throws Exception 
	 */
	public void sendMessageByTemp(String templateId, Map<String, ? extends SendTempMsgItemDTO> data, String openId) throws BusinessServiceException, Exception;
	
	
	/**
	 * 推送微信模版消息前-获取待发送消息文本
	 * @param templateId
	 * @param data
	 * @param openId
	 * @throws BusinessServiceException
	 * @throws Exception
	 */
	public String sendPreMessageByTemp(String templateId, Map<String, ? extends SendTempMsgItemDTO> data, String openId) throws BusinessServiceException, Exception;
	
	/**
	 * 推送微信模版消息
	 * @param messageId  消息ID
	 * @throws BusinessServiceException
	 * @throws Exception
	 */
	public String sendMessageByTemp(String messageId) throws BusinessServiceException, Exception;
	
	
	/**
	 * 微信群发消息
	 * @param openIdList 接受者列表（至少两个用户）
	 * @param message 推送消息内容
	 * @throws BusinessServiceException
	 */
	public void sendMessageTextByGroup(List<String> openIdList, String message) throws BusinessServiceException, Exception;
}
