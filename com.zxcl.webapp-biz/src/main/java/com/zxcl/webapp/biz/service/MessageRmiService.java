package com.zxcl.webapp.biz.service;

import java.util.List;


/**
 * @ClassName:
 * @Description:消息推送RMI调用接口
 * @author zxj
 * @date 2016年4月13日 09:30:39
 */
public interface MessageRmiService {
	
	/**
	 * 消息推送
	 * @param userIdList  用户ID列表
	 * @param message 消息
	 * @throws Exception
	 */
	public void sendMessageByGroup(String sendUserId, List<String> userIdList, String messageTheme, String messageContent, String messageAgentIds) throws Exception;
	
	
	/**
	 * 分页查询推送记录
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
//	public PageBean<MessageTargetDTOWithBLOBs> selectMessageTargetByPage(String userId, Integer currentPage, Integer pageSize) throws Exception;
}
