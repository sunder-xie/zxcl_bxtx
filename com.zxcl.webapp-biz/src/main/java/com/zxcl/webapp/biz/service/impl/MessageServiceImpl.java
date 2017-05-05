package com.zxcl.webapp.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MessageService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WXMessageService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.PageBean;
import com.zxcl.webapp.dto.MessageDTO;
import com.zxcl.webapp.dto.MessageTargetDTOWithBLOBs;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.dto.wxmsg.SendTempMsgItemDTO;
import com.zxcl.webapp.integration.dao.MessageDAO;
import com.zxcl.webapp.integration.dao.MessageTargetDAO;
import com.zxcl.webapp.util.constants.MessageConstant;

/**
 * @ClassName: 
 * @Description:消息推送
 * @author zxj
 * @date 
 */
@Service
public class MessageServiceImpl implements MessageService {
	
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MessageDAO messageDao ;
	
	@Autowired
	private MessageTargetDAO messageTargetDAO;
	
	@Autowired
	private WXMessageService wxMessageService;
	
	@Autowired
	private UserService userService;
	
	
	@Override
	@Log("保存消息")
	public void saveMessage(MessageDTO messageDTO) throws BusinessServiceException,Exception {
		if(null == messageDTO){
			throw new BusinessServiceException("参数不能为空");
		}
		if(StringUtils.isBlank(messageDTO.getMessageSendFrom())){
			throw new BusinessServiceException("消息发送者不能为空");
		}
		if(StringUtils.isBlank(messageDTO.getMessageSendTo())){
			throw new BusinessServiceException("消息接收方不能为空");
		}
		if(StringUtils.isBlank(messageDTO.getMessageType())){
			throw new BusinessServiceException("消息类型不能为空");
		}
		Date date = new Date();
		
		if(StringUtils.isBlank(messageDTO.getMessageBodyId())){
			MessageTargetDTOWithBLOBs messageTargetDTO = messageDTO.getMessageTargetDTO();
			if(null == messageTargetDTO || StringUtils.isBlank(messageTargetDTO.getMessageBody())){
				throw new BusinessServiceException("消息内容不能为空");
			}
			if(StringUtils.isBlank(messageTargetDTO.getMessageTheme())){
				throw new BusinessServiceException("消息标题不能为空");
			}
			messageTargetDTO.setCrtBy(messageDTO.getMessageSendFrom());
			messageTargetDTO.setUpdTm(date);
			messageTargetDTO.setUpdBy(messageDTO.getMessageSendFrom());
			messageTargetDTO.setUpdTm(date);
			messageTargetDTO.setMessageBodyId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			messageTargetDAO.insertSelective(messageTargetDTO);
			messageDTO.setMessageBodyId(messageTargetDTO.getMessageBodyId());
		}
		
		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_1);
		messageDTO.setCrtBy(messageDTO.getMessageSendFrom());
		messageDTO.setUpdTm(date);
		messageDTO.setUpdBy(messageDTO.getMessageSendFrom());
		messageDTO.setUpdTm(date);
		messageDTO.setMessageId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
		messageDTO.setStatus(MessageConstant.STATUS_1);
		messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_1);
		messageDao.insertSelective(messageDTO);
	}
//	
//	@Override
//	@Log("保存微信消息")
//	public void saveWXMessage(MessageDTO messageDTO, String templateId, Map<String, ? extends SendTempMsgItemDTO> data)
//			throws BusinessServiceException,Exception,Exception {
//		
//		String openId = null;
//		if(null == messageDTO){
//			throw new BusinessServiceException("参数不能为空");
//		}
//		
//		MessageTargetDTOWithBLOBs messageTargetDTO = messageDTO.getMessageTargetDTO();
//		if(messageTargetDTO == null){
//			messageTargetDTO = new MessageTargetDTOWithBLOBs();
//		}
//		
//		if(StringUtils.isBlank(messageDTO.getMessageSendFrom())){
//			throw new BusinessServiceException("消息发送者不能为空");
//		}
//		if(StringUtils.isBlank(messageDTO.getMessageSendTo())){
//			throw new BusinessServiceException("消息接收方不能为空");
//		}
//		if(StringUtils.isBlank(messageTargetDTO.getMessageBody())){
//			messageTargetDTO.setMessageBody("微信消息模板");
//		}
//		if(StringUtils.isBlank(messageTargetDTO.getMessageTheme())){
//			messageTargetDTO.setMessageTheme("微信消息模板");
//		}
//			
//		//获取小微openid
//		UserDTO userDTO = userService.queryUserByUserId(messageDTO.getMessageSendTo());
//		if(null == userDTO || StringUtils.isBlank(userDTO.getWechartId())){
//			logger.info("用户"+messageDTO.getMessageSendTo()+"不存在或openid为空");
//			return;
//		}
//		openId = userDTO.getWechartId();
//		
//		
//		//获取微信待发送消息文本
//		String wxPostContent = wxMessageService.sendPreMessageByTemp(templateId, data, openId);
//		
//		Date date = new Date();
//		messageTargetDTO.setCrtBy(messageDTO.getMessageSendFrom());
//		messageTargetDTO.setUpdTm(date);
//		messageTargetDTO.setUpdBy(messageDTO.getMessageSendFrom());
//		messageTargetDTO.setUpdTm(date);
//		messageTargetDTO.setWxPostContent(wxPostContent);
//		messageTargetDTO.setMessageBodyId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
//		messageTargetDAO.insertSelective(messageTargetDTO);
//		
//		messageDTO.setMessageBodyId(messageTargetDTO.getMessageBodyId());
//		messageDTO.setWxTemplateId(templateId);
//		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_2);
//		messageDTO.setCrtBy(messageDTO.getMessageSendFrom());
//		messageDTO.setUpdTm(date);
//		messageDTO.setUpdBy(messageDTO.getMessageSendFrom());
//		messageDTO.setUpdTm(date);
//		messageDTO.setMessageId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
//		messageDTO.setStatus(MessageConstant.STATUS_1);
//		messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_1);
//		messageDao.insertSelective(messageDTO);
//	}

	@Override
	@Log("更新消息")
	public void updateMessage(MessageDTO messageDTO) throws BusinessServiceException,Exception {
		if(null == messageDTO || messageDTO.getMessageId() == null){
			throw new BusinessServiceException("消息ID不能为空");
		}
		if(null == selectByMessageId(messageDTO.getMessageId())){
			throw new BusinessServiceException("更新失败，消息不存在");
		}
		if(null == messageDao.selectByPrimaryKey(messageDTO.getMessageId())){
			throw new BusinessServiceException("更新失败，该消息不存在");
		}
		messageDao.updateByPrimaryKeySelective(messageDTO);
	}

	@Override
	@Log("根据消息ID查询消息")
	public MessageDTO selectByMessageId(String messageId) throws BusinessServiceException,Exception {
		MessageDTO messageDTO = messageDao.selectByPrimaryKey(messageId);
		if(null != messageDTO && null != messageDTO.getMessageBodyId()){
			messageDTO.setMessageTargetDTO(messageTargetDAO.selectByPrimaryKey(messageDTO.getMessageBodyId()));
		}
		return messageDTO;
	}

	@Override
	@Log("分页查询消息列表")
	public PageBean<MessageDTO> selectByPage(PageParam pageParam) throws BusinessServiceException,Exception {
		PageBean<MessageDTO> page = new PageBean<MessageDTO>();
		if(null == pageParam.getPageSize()){
			pageParam.setPageSize(30);
		}
		if(null == pageParam.getCurrentPage()){
			pageParam.setCurrentPage(1);
		}
		int recordCount = 0;
		List<MessageDTO> dataList = null;
		pageParam.setStart(pageParam.getCurrentPage()==1?0:(pageParam.getCurrentPage()-1)*pageParam.getPageSize());
		try {
			
			//未读和已读消息
			recordCount = messageDao.messageListCount(pageParam);
			dataList = messageDao.messageList(pageParam);
		} catch (Exception e) {
			logger.error("获取消息列表失败", e);
		}
		if(CollectionUtils.isNotEmpty(dataList)){
			for (MessageDTO messageDTO : dataList) {
				messageDTO.setMessageTargetDTO(messageTargetDAO.selectByPrimaryKey(messageDTO.getMessageBodyId()));
			}
		}
		
		page.setPageSize(pageParam.getPageSize());
		page.setCurrentPage(pageParam.getCurrentPage());
		page.setRecordCount(recordCount);
		page.setPageCount(recordCount);
		page.setDataList(dataList);
	    return page;
	}

	@Override
	@Log("相对系统状态查询消息")
	public List<MessageDTO> selectByTimerStatus(Integer timerStatus) throws BusinessServiceException,Exception {
		return messageDao.selectByTimerStatus(timerStatus);
	}
//	
//	@Override
//	@Log("推送消息")
//	public void sendMessage(String messageId) throws BusinessServiceException,Exception {
//		if(null == messageId || messageId.isEmpty()){
//			return;
//		}
//		MessageDTO messageDTO = messageDao.selectByPrimaryKey(messageId);
//		if(null == messageDTO){
//			return;
//		}
//		
//		//个人微信消息类型
//		if(MessageConstant.MESSAGE_TYPE_2.equals(messageDTO.getMessageType())){
//			MessageDTO updMessageDTO = new MessageDTO();
//			updMessageDTO.setMessageId(messageId);
//			updMessageDTO.setUpdBy("system");
//			String sendResultMsg = null;
//			try {
//				sendResultMsg = wxMessageService.sendMessageByTemp(messageId);
//				updMessageDTO.setStatus(MessageConstant.STATUS_2);
//				updMessageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
//				updMessageDTO.setSendResultMsg(sendResultMsg);
//			} catch (Exception e) {
//				updMessageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_4);
//				updMessageDTO.setSendResultMsg(e.getMessage());
//			}
//			updateMessage(updMessageDTO);
//		}
//		
//		//个人APP消息类型
//		else if(MessageConstant.MESSAGE_TYPE_1.equals(messageDTO.getMessageType())){
//			MessageDTO updMessageDTO = new MessageDTO();
//			updMessageDTO.setMessageId(messageId);
//			updMessageDTO.setUpdBy("system");
//			updMessageDTO.setStatus(MessageConstant.STATUS_2);
//			updMessageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
//			updMessageDTO.setSendResultMsg("处理成功");
//			updateMessage(updMessageDTO);
//		}
//	}

	@Override
	public Integer getUnreadMessageCount(String userId) throws BusinessServiceException,Exception {
		return messageDao.getUnreadMessageCount(userId);
	}
	
	

}
