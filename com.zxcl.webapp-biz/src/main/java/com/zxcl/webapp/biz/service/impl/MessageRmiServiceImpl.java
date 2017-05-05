package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.MessageRmiService;
import com.zxcl.webapp.biz.service.MessageService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WXMessageService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.MessageDTO;
import com.zxcl.webapp.dto.MessageTargetDTOWithBLOBs;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.integration.dao.MessageTargetDAO;
import com.zxcl.webapp.integration.dao.UserDAO;
import com.zxcl.webapp.util.constants.MessageConstant;

/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */

@Service
public class MessageRmiServiceImpl implements MessageRmiService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private WXMessageService wxMessageService;
	
	@Autowired
	private MessageTargetDAO messageTargetDao;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Log("远程调用-群发消息推送")
	public void sendMessageByGroup(String sendFromUserId, List<String> userIdList,String messageTheme, String messageContent, String messageAgentIds) throws Exception {
		if(CollectionUtils.isEmpty(userIdList)){
			logger.info("群发用户列表为空");
			return;
		}
		if(StringUtils.isBlank(messageContent)){
			logger.info("群发消息内容为空");
			return;
		}
		sendFromUserId = StringUtils.isBlank(sendFromUserId)?"system":sendFromUserId;
		messageTheme = StringUtils.isBlank(messageTheme)?"系统消息":messageTheme;
		
		logger.info("群发消息用户列表=" + userIdList.toString()+" 主题=" + messageTheme + " 内容=" + messageContent + " 发送者=" + sendFromUserId);
		
		MessageTargetDTOWithBLOBs messageTargetDTO = null;
		MessageDTO messageDTO = null;
		Date date = new Date();
		
		//关注者列表
		List<String> openIdList = new ArrayList<String>(userIdList.size());
		
		List<UserDTO> userDTOList = userDAO.queryUserByUserIds(userIdList);
		if(CollectionUtils.isEmpty(userDTOList)){
			throw new RuntimeException("用户列表为空，发送失败");
		}
		try {
		
			//消息内容
			messageTargetDTO = new MessageTargetDTOWithBLOBs();
			messageTargetDTO.setCrtBy(sendFromUserId);
			messageTargetDTO.setCrtTm(date);
			messageTargetDTO.setMessageBody(messageContent);
			messageTargetDTO.setMessageTheme(messageTheme);
			messageTargetDTO.setMessageAgentIds(messageAgentIds);
			messageTargetDTO.setUpdBy(sendFromUserId);
			messageTargetDTO.setUpdTm(date);
			messageTargetDTO.setMessageBodyId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			messageTargetDTO.setContentType(2);
			messageTargetDao.insertSelective(messageTargetDTO);
			
			//消息发送信息
			messageDTO = new MessageDTO();
			messageDTO.setCrtBy(sendFromUserId);
			messageDTO.setCrtTm(date);
			messageDTO.setMessageSendFrom(sendFromUserId);
			messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_1);
			messageDTO.setSendResultMsg("待系统处理");
			messageDTO.setStatus(MessageConstant.STATUS_1);
			messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_1);
			messageDTO.setUpdBy(sendFromUserId);
			messageDTO.setUpdTm(date);
			messageDTO.setMessageBodyId(messageTargetDTO.getMessageBodyId());
			
			for(UserDTO user : userDTOList){
				if(StringUtils.isNotBlank(user.getWechartId())){
					openIdList.add(user.getWechartId());
				}
				
				//发送APP内部系统消息
				if(StringUtils.isBlank(user.getUserId())){
					logger.info("用户ID为空,userDTO="+user.toString());
				}else{
					messageDTO.setMessageSendTo(user.getUserId());
					messageService.saveMessage(messageDTO);
				}
				
			}
		
		} catch (Exception e) {
			logger.error("消息推送失败", e);
			throw new Exception(e.getMessage());
		}
		
		//群发推送微信消息
		if(CollectionUtils.isNotEmpty(openIdList)){
			try {
				StringBuffer buffer = new StringBuffer();
				buffer.append(messageTheme);
				buffer.append(": ");
				buffer.append("\n");
				buffer.append("\n");
				buffer.append("  ");
				buffer.append(messageContent);
				wxMessageService.sendMessageTextByGroup(openIdList, buffer.toString());
			} catch (Exception e) {
				logger.error("微信群发推送失败", e);
			}
		}
	}

//	@Override
//	@Log("分页查询推送记录")
//	public PageBean<MessageTargetDTOWithBLOBs> selectMessageTargetByPage(String userId, Integer currentPage, Integer pageSize) throws Exception {
//		PageBean<MessageTargetDTOWithBLOBs> page = new PageBean<MessageTargetDTOWithBLOBs>();
//		PageParam pageParam = new PageParam();
//		pageParam.setOperateUser(userId);
//		pageParam.setCurrentPage(currentPage);
//		pageParam.setPageSize(pageSize);
//		
//		if(null == pageParam.getPageSize()){
//			pageParam.setPageSize(30);
//		}
//		if(null == pageParam.getCurrentPage()){
//			pageParam.setCurrentPage(1);
//		}
//		int recordCount = 0;
//		List<MessageTargetDTOWithBLOBs> dataList = null;
//		pageParam.setStart(pageParam.getCurrentPage()==1?0:(pageParam.getCurrentPage()-1)*pageParam.getPageSize());
//		try {
//			
//			recordCount = messageTargetDao.messageTargetListCount(pageParam);
//			dataList = messageTargetDao.messageTargetList(pageParam);
//		} catch (Exception e) {
//			logger.error("获取消息内容列表失败", e);
//		}
//		if(CollectionUtils.isNotEmpty(dataList)){
//		}
//		
//		page.setPageSize(pageParam.getPageSize());
//		page.setCurrentPage(pageParam.getCurrentPage());
//		page.setRecordCount(recordCount);
//		page.setPageCount(recordCount);
//		page.setDataList(dataList);
//		return page;
//	}
	

}
