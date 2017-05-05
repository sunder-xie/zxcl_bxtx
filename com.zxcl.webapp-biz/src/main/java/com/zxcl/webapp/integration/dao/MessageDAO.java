package com.zxcl.webapp.integration.dao;

import java.util.List;

import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.dto.MessageDTO;


/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */
public interface MessageDAO {
    int deleteByPrimaryKey(String messageId);

    int insertSelective(MessageDTO record);

    MessageDTO selectByPrimaryKey(String messageId);

    int updateByPrimaryKeySelective(MessageDTO record);

    List<MessageDTO> selectByTimerStatus(Integer timerStatus);

	int messageListCount(PageParam pageParam);

	List<MessageDTO> messageList(PageParam pageParam);
	
	public Integer getUnreadMessageCount(String userId);
}