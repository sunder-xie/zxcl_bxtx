package com.zxcl.webapp.biz.service.impl.activity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MessageService;
import com.zxcl.webapp.biz.service.activity.ActivityBxtxBService;
import com.zxcl.webapp.dto.MessageDTO;
import com.zxcl.webapp.dto.MessageTargetDTOWithBLOBs;
import com.zxcl.webapp.dto.activity.ActivityBxtxBDrawListDTO;
import com.zxcl.webapp.dto.activity.ActivityBxtxBWinningListDTO;
import com.zxcl.webapp.integration.dao.activity.ActivityBxtxBDAO;
import com.zxcl.webapp.util.constants.MessageConstant;

@Service
public class ActivityBxtxBServiceImpl implements ActivityBxtxBService {

	private static Logger logger = Logger.getLogger(ActivityBxtxBServiceImpl.class);
	
	@Autowired
	private ActivityBxtxBDAO activityBxtxBDAO;
	
	@Autowired
	private MessageService messageService;
	
	@Override
	public Integer getLotteryNumber(String userId) {
		return activityBxtxBDAO.getLotteryNumber(userId);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Integer updateLuckdraw(String userId) throws BusinessServiceException, Exception {
		
		/**
		 * 1. 获取可抽奖次数
		 * 2. 获取抽奖名单是否有自己
		 * 	2.1 如果有自己，更新会中奖的名字数据的状态
		 * 3.插入抽奖记录
		 */
		Integer count = getLotteryNumber(userId);
		
		if(count != null && count.intValue() > 0){
			logger.info("用户编号：" + userId + " 已抽奖.");
			return -10;
		}
		
		List<ActivityBxtxBWinningListDTO> lists = getActivityBxtxBWinnings();
		
		if(lists == null || lists.size() < 1){
			logger.info("转盘抽奖未设置中奖名单.");
			return -11;
		}
		
		boolean isHave = false;
		Integer result = 0;
		for(ActivityBxtxBWinningListDTO win : lists){
			if(StringUtils.equals(win.getUserId(), userId)){
				isHave = true;
				result = win.getLevel();
			}
		}
		
		ActivityBxtxBDrawListDTO activityBxtxBDrawListDTO = new ActivityBxtxBDrawListDTO();
		activityBxtxBDrawListDTO.setUserId(userId);
		activityBxtxBDrawListDTO.setCrtCde(userId);
		activityBxtxBDrawListDTO.setUpdCde(userId);
		activityBxtxBDrawListDTO.setCrtTm(new Date());
		activityBxtxBDrawListDTO.setUpdTm(new Date());
		
		
		
		if(isHave){
			activityBxtxBDrawListDTO.setIsHad(2);
			
			logger.info("开始更新保行天下活动B-转盘抽奖，userId:" + userId + ",状态：2" );
			
			Integer row = activityBxtxBDAO.updateActivityBxtxBWinningStatus(userId, 2);
			if(row == null || row.intValue() < 1){
				logger.info("更新中奖人员名单的数据失败.userId：" + userId);
				return -12;
			}
			
			logger.info("已完成更新保行天下活动B-转盘抽奖，userId:" + userId + ",状态：2" );
			
			//推送消息到个人信息中心
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setMessageSendFrom("system");
			messageDTO.setMessageSendTo(userId);
			messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_1);
			//messageDTO.setSendResultMsg("尊敬的用户你好，恭喜您已通过转盘抽奖活动获得 \"" + result+ "等奖\"，我们的客服稍后将会与您联系，请您保持电话通畅！谢谢！");
			MessageTargetDTOWithBLOBs bloBs = new MessageTargetDTOWithBLOBs();
			bloBs.setMessageTheme("保行天下神秘大奖活动中奖通知");
			String messageBody = "尊敬的用户你好，恭喜您已通过神秘大奖抽奖活动获得";
			if(result != null && result.intValue() == 1 ){
				messageBody += " 一等奖 “Applewatch” ";
			}else if(result != null && result.intValue() == 1 ){
				messageBody += " 二等奖 “车载空气净化器” ";
			}else if(result != null && result.intValue() == 1 ){
				messageBody += " 一等奖 “行车记录仪 ” ";
			}
			messageBody += "，我们的客服稍后将会与您联系，请您保持电话通畅！谢谢！";
			
			bloBs.setMessageBody(messageBody);
			messageDTO.setMessageTargetDTO(bloBs);
			messageService.saveMessage(messageDTO);
			
		}else{
			activityBxtxBDrawListDTO.setIsHad(1);
		}
		
		activityBxtxBDAO.insertActivityBxtxBDrawList(activityBxtxBDrawListDTO);
		
		logger.info("完成保行天下活动B-转盘抽奖记录，userId:" + userId);
		
		return result; //返回中奖的等级。0未抽奖 1 一等奖 2二等奖 3三等奖
	}

	@Override
	public List<ActivityBxtxBWinningListDTO> getActivityBxtxBWinnings() {
		return activityBxtxBDAO.getActivityBxtxBWinnings();
	}

}
