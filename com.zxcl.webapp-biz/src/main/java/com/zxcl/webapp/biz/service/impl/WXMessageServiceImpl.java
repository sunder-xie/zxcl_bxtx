package com.zxcl.webapp.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import bxtx.intf.weixin.biz.service.WeiXinService;

import com.alibaba.fastjson.JSONObject;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MessageService;
import com.zxcl.webapp.biz.service.WXMessageService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.MessageDTO;
import com.zxcl.webapp.dto.MessageTargetDTOWithBLOBs;
import com.zxcl.webapp.dto.wxmsg.SendGroupContentMsgDTO;
import com.zxcl.webapp.dto.wxmsg.SendGroupMsgDTO;
import com.zxcl.webapp.dto.wxmsg.SendTempMsgDTO;
import com.zxcl.webapp.dto.wxmsg.SendTempMsgItemDTO;
import com.zxcl.webapp.integration.dao.MessageTargetDAO;
import com.zxcl.webapp.util.HttpOrsClient;
import com.zxcl.webapp.util.constants.WXMessageConstant;

/**
 * @ClassName: 
 * @Description:微信消息推送
 * @author zxj
 * @date 2016年4月11日 10:45:10
 */
@Service
public class WXMessageServiceImpl implements WXMessageService {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private WeiXinService weiXinService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MessageTargetDAO messageTargetDAO;
	
	
	@Override
	@Log("即时推送微信模版消息")
	public void sendMessageByTemp(String templateId, Map<String, ? extends SendTempMsgItemDTO> data, String openId) throws Exception {
		if(StringUtils.isBlank(templateId)){
			throw new BusinessServiceException("模板号不能为空");
		}
		if(StringUtils.isBlank(openId)){
			throw new BusinessServiceException("消息接收人不能为空");
		}
		
		HttpResponse response = null;
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		String result = null;
		JSONObject jsonObj = null;
		StringEntity stringEntity = null;
		httpClient = HttpOrsClient.httpClientProvide();
		String accessToken = null;
		
		//获取ACCESS_TOKEN
		accessToken = weiXinService.getAccessToken();
		
		//推送模版消息
		httpPost = new HttpPost(WXMessageConstant.WX_SENDMSG_URL + accessToken);
		SendTempMsgDTO SendTempMsgDTO = new SendTempMsgDTO();
		SendTempMsgDTO.setTemplate_id(templateId);
		SendTempMsgDTO.setTouser(openId);
		SendTempMsgDTO.setUrl("");
		SendTempMsgDTO.setData(data);
		stringEntity = new StringEntity(JSONObject.toJSONString(SendTempMsgDTO), "UTF-8");
		httpPost.setEntity(stringEntity);
		response = httpClient.execute(httpPost);
		
		result = EntityUtils.toString(response.getEntity(), "UTF-8");
		logger.info(result);
		jsonObj = JSONObject.parseObject(result);
		if(!"0".equals(jsonObj.get("errcode").toString())){
			throw new BusinessServiceException("推送消息失败,参数openId="+openId+",msg="+result);
		}
	}
	
	
	@Override
	@Log("根据消息ID即时推送微信模版消息")
	public String sendMessageByTemp(String messageId) throws Exception {
		if(StringUtils.isBlank(messageId)){
			throw new BusinessServiceException("消息ID不能为空");
		}
		
		HttpResponse response = null;
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		String result = null;
		JSONObject jsonObj = null;
		StringEntity stringEntity = null;
		String accessToken = null;
		String data = null;
		
		//获取消息待发送内容
		MessageDTO MessageDTO = messageService.selectByMessageId(messageId);
		if(null == MessageDTO || StringUtils.isBlank(MessageDTO.getMessageBodyId())){
			throw new BusinessServiceException("发送内容不能为空");
		}else{
			MessageTargetDTOWithBLOBs messageTarget = messageTargetDAO.selectByPrimaryKey(MessageDTO.getMessageBodyId());
			if(null == messageTarget || StringUtils.isBlank(messageTarget.getWxPostContent())){
				throw new BusinessServiceException("发送内容不能为空");
			}
			else{
				data = messageTarget.getWxPostContent();
			}
		}
				
		httpClient = HttpOrsClient.httpClientProvide();
		
		//获取ACCESS_TOKEN
		accessToken = weiXinService.getAccessToken();
		
		
		//推送模版消息
		httpPost = new HttpPost(WXMessageConstant.WX_SENDMSG_URL + accessToken);
		stringEntity = new StringEntity(data, "UTF-8");
		httpPost.setEntity(stringEntity);
		response = httpClient.execute(httpPost);
		result = EntityUtils.toString(response.getEntity(), "UTF-8");
		logger.info(result);
		jsonObj = JSONObject.parseObject(result);
		if(!"0".equals(jsonObj.get("errcode").toString())){
			throw new BusinessServiceException("推送消息失败,参数messageId="+messageId+",result="+result);
		}
		return result;
	}


	@Override
	@Log("推送微信模版消息前-获取待发送消息文本")
	public String sendPreMessageByTemp(String templateId, Map<String, ? extends SendTempMsgItemDTO> data, String openId) throws Exception {
		if(StringUtils.isBlank(templateId)){
			throw new BusinessServiceException("模板号不能为空");
		}
		if(StringUtils.isBlank(openId)){
			throw new BusinessServiceException("消息接收人不能为空");
		}
		
		//推送模版消息
		SendTempMsgDTO SendTempMsgDTO = new SendTempMsgDTO();
		SendTempMsgDTO.setTemplate_id(templateId);
		SendTempMsgDTO.setTouser(openId);
		SendTempMsgDTO.setUrl("");
		SendTempMsgDTO.setData(data);
		
		return JSONObject.toJSONString(SendTempMsgDTO);
	}
	
	@Override
	@Log("微信群发消息(即时发送)")
	public void sendMessageTextByGroup(List<String> openIdList, String message) throws BusinessServiceException,Exception {
		if(CollectionUtils.isEmpty(openIdList) || openIdList.size() < 2){
			throw new BusinessServiceException("推送消息失败,接受消息人数至少2人");
		}
		if(StringUtils.isBlank(message)){
			throw new BusinessServiceException("推送消息内容不能为空");
		}
		
		logger.info("微信消息群发待推送用户=" + openIdList);
		
		HttpResponse response = null;
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		String result = null;
		StringEntity stringEntity = null;
		String accessToken = null;
		httpClient = HttpOrsClient.httpClientProvide();
		
		//获取ACCESS_TOKEN
		accessToken = weiXinService.getAccessToken();
		
		//发送消息
		httpPost = new HttpPost(WXMessageConstant.WX_SENDMSG_GROUP_URL + accessToken);
		SendGroupMsgDTO sendGroupMsgDTO = new SendGroupMsgDTO();
		sendGroupMsgDTO.setTouser(openIdList);
		sendGroupMsgDTO.setText(new SendGroupContentMsgDTO(message));
		
		
		stringEntity = new StringEntity(JSONObject.toJSONString(sendGroupMsgDTO), "UTF-8");
		httpPost.setEntity(stringEntity);
		response = httpClient.execute(httpPost);
		result = EntityUtils.toString(response.getEntity(), "UTF-8");
		logger.info("发送结果 " + result);
		JSONObject jsonObj = JSONObject.parseObject(result);
		if(!"0".equals(jsonObj.get("errcode").toString())){
			throw new BusinessServiceException("微信消息群发失败,msg="+result);
		}
		logger.info("微信群发消息成功");
	}
	
}
