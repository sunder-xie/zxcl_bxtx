package com.zxcl.webapp.biz.service;

import java.util.List;
import java.util.Map;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.PageBean;
import com.zxcl.webapp.dto.MessageDTO;
import com.zxcl.webapp.dto.wxmsg.SendTempMsgItemDTO;


/**
 * @ClassName: 
 * @Description:消息推送
 * @author zxj
 * @date 
 */
public interface MessageService {
	/**
	 * 保存消息
	 * @param messageDTO
	 * @throws BusinessServiceException,Exception
	 */
	public void saveMessage(MessageDTO messageDTO) throws BusinessServiceException,Exception;
	
	/**
	 * 保存微信消息
	 * @param messageDTO
	 * @param templateId
	 * @param data
	 * @param openId
	 * @throws BusinessServiceException,Exception
	 */
	//public void saveWXMessage(MessageDTO messageDTO, String templateId, Map<String, ? extends SendTempMsgItemDTO> data) throws BusinessServiceException,Exception;
	
	/**
	 * 更新消息
	 * @param messageDTO
	 * @throws BusinessServiceException,Exception
	 */
	public void updateMessage(MessageDTO messageDTO) throws BusinessServiceException,Exception;
	
	/**
	 * 根据消息ID查询消息
	 * @param messageId
	 * @return
	 * @throws BusinessServiceException,Exception
	 */
	public MessageDTO selectByMessageId(String messageId) throws BusinessServiceException,Exception;
	
	/**
	 * 分页查询消息
	 * @param pageParam
	 * @return
	 * @throws BusinessServiceException,Exception
	 */
	public PageBean<MessageDTO> selectByPage(PageParam pageParam) throws BusinessServiceException,Exception;
	
	/**
	 * 相对系统状态查询消息
	 * @param timerStatus
	 * @return
	 * @throws BusinessServiceException,Exception
	 */
	public List<MessageDTO> selectByTimerStatus(Integer timerStatus) throws BusinessServiceException,Exception;
	
	/**
	 * 推送微信消息
	 * @param openIdList
	 * @throws BusinessServiceException,Exception
	 */
	//public void sendMessage(String messageId) throws BusinessServiceException,Exception;
	
	/**
	 * 获取未读消息总数
	 * @param userId
	 * @return
	 */
	public Integer getUnreadMessageCount(String userId) throws BusinessServiceException,Exception;
}
