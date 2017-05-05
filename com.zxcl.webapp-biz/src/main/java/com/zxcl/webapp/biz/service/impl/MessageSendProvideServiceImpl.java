package com.zxcl.webapp.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bxtx.intf.weixin.biz.service.WeiXinService;
import bxtx.intf.weixin.dto.SendTemplateMessageDTO;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.service.MessageSendProvideService;
import com.zxcl.webapp.biz.service.MessageService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.PlatformService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WXMessageService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.MessageDTO;
import com.zxcl.webapp.dto.MessageTargetDTOWithBLOBs;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.util.constants.MessageConstant;
import com.zxcl.webapp.util.constants.WXMessageConstant;

/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 2016年4月13日 17:59:54
 */

@Service
public class MessageSendProvideServiceImpl implements MessageSendProvideService {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private WXMessageService wxMessageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private PlatformService platformService;
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private WeiXinService weiXinService;
	
	
	@Override
	public String sendMessage(String msgType, String[] value) {
		
		try {
			if(StringUtils.equals(MessageSendProvideService.CASH_WITHDRAWAL_AUDIT, msgType)){
				sendMessageWithCashCheck(value[0],value[1],value[2],value[3],value[4],value[5]);
				return "";
			}else if(StringUtils.equals(MessageSendProvideService.CASH_WITHDRAWAL_SUCCESS, msgType)){
				sendMessageWithCashSuccess(value[0],value[1],value[2],value[3],value[4]);
				return "";
			}else if(StringUtils.equals(MessageSendProvideService.CASH_WITHDRAWAL_FAIL, msgType)){
				sendMessageWithCashFailed(value[0],value[1],value[2],value[3],value[4]);
				return "";
			}else if(StringUtils.equals(MessageSendProvideService.INSURANCE_SUCCESS, msgType)){
				sendMessageWithInsureSuccess(value[0],value[1],value[2],value[3],value[4]);
				return "";
			}else if(StringUtils.equals(MessageSendProvideService.INSURANCE_POLICY_GENERATED, msgType)){
				sendMessageWithInsureSuccess(value[0],value[1]);
				return "";
			}else if(StringUtils.equals(MessageSendProvideService.MQ_QUOTAED_TO_USER, msgType)){
				sendMessageWithMqToUser(value[0],value[1],value[2],value[3],value[4],value[5]);
				return "";
			}
			
			return "未知类型";
		} catch (Exception e) {
			logger.error("发送消息失败", e);
			return e.getMessage();
		}
		
	}

	@Log("提现审核通知")
	public void sendMessageWithCashCheck(String userId, String firstContent, String moneyAmount, String ownerName, String checkTime, String remark) throws BusinessServiceException,Exception {
		
		//APP消息
		MessageTargetDTOWithBLOBs messageTargetDTO = new MessageTargetDTOWithBLOBs();
		Date date = new Date();
		messageTargetDTO.setCrtBy("system");
		messageTargetDTO.setCrtTm(date);
		messageTargetDTO.setMessageBody(firstContent);
		messageTargetDTO.setMessageTheme(WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_2);
		messageTargetDTO.setUpdBy("system");
		messageTargetDTO.setUpdTm(date);
		
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setMessageTargetDTO(messageTargetDTO);
		messageDTO.setCrtBy("system");
		messageDTO.setCrtTm(date);
		messageDTO.setMessageSendFrom("system");
		messageDTO.setMessageSendTo(userId);
		messageDTO.setSendResultMsg("处理成功");
		messageDTO.setStatus(MessageConstant.STATUS_2);
		messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
		
		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_1);
		
		
		messageService.saveMessage(messageDTO);
		
		//微信消息
		messageTargetDTO = new MessageTargetDTOWithBLOBs();
		messageTargetDTO.setCrtBy("system");
		messageTargetDTO.setCrtTm(date);
		messageTargetDTO.setMessageBody(firstContent);
		messageTargetDTO.setMessageTheme(WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_2);
		messageTargetDTO.setUpdBy("system");
		messageTargetDTO.setUpdTm(date);
		
		messageDTO = new MessageDTO();
		messageDTO.setMessageTargetDTO(messageTargetDTO);
		messageDTO.setCrtBy("system");
		messageDTO.setCrtTm(date);
		messageDTO.setMessageSendFrom("system");
		messageDTO.setMessageSendTo(userId);
		
		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_2);
		
		UserDTO ud = userService.queryUserByUserId(userId);
		if(ud != null && StringUtils.isNotBlank(ud.getWechartId())){
			List<SendTemplateMessageDTO> messages = new ArrayList<>();
			
			
			SendTemplateMessageDTO message = new SendTemplateMessageDTO();
			message.setToUser(ud.getWechartId());
			message.setValue(new String[]{firstContent,moneyAmount,ownerName,checkTime,remark});
			messages.add(message);
			
			String result = weiXinService.sendMessageByTemplate("BXTX_CASH_REQ_AUDIT", messages);
			if(!StringUtils.equals("", result)){
				messageDTO.setStatus(MessageConstant.STATUS_2);
				messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
				messageDTO.setSendResultMsg("已提交发送请求");
			}else{
				messageDTO.setSendResultMsg(result);
			}
//			PlatformDTO baseDTO = platformService.getPlatByCode(BaseFinal.WECHAT_MESSAGE_TEMPLATE, WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_2);
//			Map<String, SendTempMsgItemDTO> data = new HashMap<String, SendTempMsgItemDTO>(5);
//			data.put("first", new SendTempMsgItemDTO(firstContent, "#173177"));
//			data.put("keyword1", new SendTempMsgItemDTO(moneyAmount, "#173177"));//提现金额
//			data.put("keyword2", new SendTempMsgItemDTO(ownerName, "#173177"));//提现账户
//			data.put("keyword3", new SendTempMsgItemDTO(checkTime, "#173177"));//时间
//			data.put("remark", new SendTempMsgItemDTO("", "#141415"));
		}else{
			logger.info("用户userId:" +userId + " 的openId为空，不发送微信消息，messageId:" + messageDTO.getMessageId());
		}
		
		//这里保存不再用微信的保存了。直接当普通message保存。后面的代码也可以删除了.
		messageService.saveMessage(messageDTO);
	}

	@Log("提现成功通知")
	public void sendMessageWithCashSuccess(String userId, String firstContent, String moneyAmount, String time, String remark) throws BusinessServiceException,Exception {
		
		//APP消息
		Date date = new Date();
		MessageTargetDTOWithBLOBs messageTargetDTO = new MessageTargetDTOWithBLOBs();
		messageTargetDTO.setCrtBy("system");
		messageTargetDTO.setCrtTm(date);
		messageTargetDTO.setMessageBody(firstContent);
		messageTargetDTO.setMessageTheme(WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_3);
		messageTargetDTO.setUpdBy("system");
		messageTargetDTO.setUpdTm(date);
		
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setMessageTargetDTO(messageTargetDTO);
		messageDTO.setCrtBy("system");
		messageDTO.setCrtTm(date);
		messageDTO.setMessageSendFrom("system");
		messageDTO.setMessageSendTo(userId);
		messageDTO.setStatus(MessageConstant.STATUS_2);
		messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
		messageDTO.setSendResultMsg("处理成功");
		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_1);
		messageService.saveMessage(messageDTO);
		
		//微信消息
		messageTargetDTO = new MessageTargetDTOWithBLOBs();
		messageTargetDTO.setCrtBy("system");
		messageTargetDTO.setCrtTm(date);
		messageTargetDTO.setMessageBody(firstContent);
		messageTargetDTO.setMessageTheme(WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_3);
		messageTargetDTO.setUpdBy("system");
		messageTargetDTO.setUpdTm(date);
		
		messageDTO = new MessageDTO();
		messageDTO.setMessageTargetDTO(messageTargetDTO);
		messageDTO.setCrtBy("system");
		messageDTO.setCrtTm(date);
		messageDTO.setMessageSendFrom("system");
		messageDTO.setMessageSendTo(userId);
		messageDTO.setSendResultMsg("待系统处理");
		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_2);
//		messageService.saveMessage(messageDTO);
		
		UserDTO ud = userService.queryUserByUserId(userId);
		if(ud != null && StringUtils.isNotBlank(ud.getWechartId())){
			List<SendTemplateMessageDTO> messages = new ArrayList<>();
			
			
			SendTemplateMessageDTO message = new SendTemplateMessageDTO();
			message.setToUser(ud.getWechartId());
			message.setValue(new String[]{firstContent,moneyAmount,time,remark});
			messages.add(message);
			
			String result = weiXinService.sendMessageByTemplate("BXTX_CASH_REQ_SUCC", messages);
			if(!StringUtils.equals("", result)){
				messageDTO.setStatus(MessageConstant.STATUS_2);
				messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
				messageDTO.setSendResultMsg("已提交发送请求");
			}else{
				messageDTO.setSendResultMsg(result);
			}
			
		}else{
			logger.info("用户userId:" +userId + " 的openId为空，不发送微信消息，messageId:" + messageDTO.getMessageId());
		}
		
		
//		PlatformDTO baseDTO = platformService.getPlatByCode(BaseFinal.WECHAT_MESSAGE_TEMPLATE, WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_3);
//		Map<String, SendTempMsgItemDTO> data = new HashMap<String, SendTempMsgItemDTO>(4);
//		data.put("first", new SendTempMsgItemDTO(firstContent, "#173177"));
//		data.put("money", new SendTempMsgItemDTO(moneyAmount, "#173177"));//提现金额
//		data.put("timet", new SendTempMsgItemDTO(time, "#173177"));//时间
//		data.put("remark", new SendTempMsgItemDTO("", "#141415"));
		messageService.saveMessage(messageDTO);
	}

	@Log("投保成功提醒")
	public void sendMessageWithInsureSuccess(String userId, String firstContent, String amount, String orderNo, String remark) throws BusinessServiceException,Exception {
	
		//APP消息
		Date date = new Date();
		MessageTargetDTOWithBLOBs messageTargetDTO = new MessageTargetDTOWithBLOBs();
		messageTargetDTO.setCrtBy("system");
		messageTargetDTO.setCrtTm(date);
		messageTargetDTO.setMessageBody(firstContent+"保单："+orderNo);
		messageTargetDTO.setMessageTheme(WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_1);
		messageTargetDTO.setUpdBy("system");
		messageTargetDTO.setUpdTm(date);
		
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setMessageTargetDTO(messageTargetDTO);
		messageDTO.setCrtBy("system");
		messageDTO.setCrtTm(date);
		messageDTO.setMessageSendFrom("system");
		messageDTO.setMessageSendTo(userId);
		messageDTO.setSendResultMsg("处理成功");
		messageDTO.setStatus(MessageConstant.STATUS_2);
		messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_1);
		
		messageService.saveMessage(messageDTO);
		
		//微信消息
		messageTargetDTO = new MessageTargetDTOWithBLOBs();
		messageTargetDTO.setCrtBy("system");
		messageTargetDTO.setCrtTm(date);
		messageTargetDTO.setMessageBody(firstContent+"保单："+orderNo);
		messageTargetDTO.setMessageTheme(WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_1);
		messageTargetDTO.setUpdBy("system");
		messageTargetDTO.setUpdTm(date);
		
		messageDTO = new MessageDTO();
		messageDTO.setMessageTargetDTO(messageTargetDTO);
		messageDTO.setCrtBy("system");
		messageDTO.setCrtTm(date);
		messageDTO.setMessageSendFrom("system");
		messageDTO.setMessageSendTo(userId);
		messageDTO.setSendResultMsg("待系统处理");
		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_2);
		
		UserDTO ud = userService.queryUserByUserId(userId);
		if(ud != null && StringUtils.isNotBlank(ud.getWechartId())){
			List<SendTemplateMessageDTO> messages = new ArrayList<>();
			
			
			SendTemplateMessageDTO message = new SendTemplateMessageDTO();
			message.setToUser(ud.getWechartId());
			message.setValue(new String[]{firstContent,amount,orderNo,remark});
			messages.add(message);
			
			String result = weiXinService.sendMessageByTemplate("BXTX_INSURANCE_SUCC", messages);
			if(!StringUtils.equals("", result)){
				messageDTO.setStatus(MessageConstant.STATUS_2);
				messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
				messageDTO.setSendResultMsg("已提交发送请求");
			}else{
				messageDTO.setSendResultMsg(result);
			}
			
		}else{
			logger.info("用户userId:" +userId + " 的openId为空，不发送微信消息，messageId:" + messageDTO.getMessageId());
		}
		
//		PlatformDTO baseDTO = platformService.getPlatByCode(BaseFinal.WECHAT_MESSAGE_TEMPLATE, WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_1);
//		Map<String, SendTempMsgItemDTO> data = new HashMap<String, SendTempMsgItemDTO>(5);
//		data.put("first", new SendTempMsgItemDTO(firstContent, "#173177"));
//		data.put("keyword1", new SendTempMsgItemDTO(amount, "#173177"));//支付保费金额
//		data.put("keyword2", new SendTempMsgItemDTO(orderNo, "#173177"));//保单号
//		data.put("remark", new SendTempMsgItemDTO("", "#141415"));
		messageService.saveMessage(messageDTO);
	}

	@Log("保单已生成 消息推送")
	public void sendMessageWithInsureSuccess(@NotNull String insId, @NotNull String orderId) {
		logger.info("insId="+insId+",orderId="+orderId);
		if(StringUtils.isBlank(insId) || StringUtils.isBlank(orderId)){
			return;
		}
		String amount = "";
		String userId = null;
//		OrderQueryDTO orderQueryDTO = null;
		OrderDTO orderDTO = null;
		BigDecimal amountDecimal = new BigDecimal("0.00");
		String vicOrVciPlyNo = "";
		String insName = "";
		
		try {
			
//			orderQueryDTO = orderQueryService.selectOrderByOrderIdAndInsId(orderId, insId);
//			if(null == orderQueryDTO){
//				logger.info("保单状态查询为空");
//				return;
//			}
			orderDTO = orderService.getOrderById(null, insId, orderId);			
			if(null != orderDTO.getMicro()){
				userId = microService.getUserIdByMicId(orderDTO.getMicro().getMicro_id());
				if(null == userId){
					logger.info("用户不存在");
					return;
				}					
			}
			
			//总保费
			if(null != orderDTO.getQuota().getVehicleTax()){
				amountDecimal = amountDecimal.add(orderDTO.getQuota().getVehicleTax());
			}
			if(null != orderDTO.getQuota().getVCIPremTax()){
				amountDecimal = amountDecimal.add(orderDTO.getQuota().getVCIPremTax());
			}
			if(null != orderDTO.getQuota().getTCIPremTax()){
				amountDecimal = amountDecimal.add(orderDTO.getQuota().getTCIPremTax());
			}
			amount = amountDecimal.toString()+"元";
			
			//保单号
			if(StringUtils.isBlank(orderDTO.getVciPlyNo()) && StringUtils.isBlank(orderDTO.getTciPlyNo())){
				vicOrVciPlyNo = orderId+"(订单号)";
			}else{
				if(StringUtils.isNotBlank(orderDTO.getVciPlyNo())){
					vicOrVciPlyNo += " "+orderDTO.getVciPlyNo()+"(商业)";
				}
				if(StringUtils.isNotBlank(orderDTO.getTciPlyNo())){
					vicOrVciPlyNo += " "+orderDTO.getTciPlyNo()+"(交强)";
				}
			}
			
			//保险公司名称
			InsuranceDTO insuranceDTO = insuranceService.get(insId);
			if(insuranceDTO != null && StringUtils.isNotBlank(insuranceDTO.getInsName())){
				insName = insuranceDTO.getInsPetName();
			}else{
				insName = insId;
			}
			
			//获取车主,车牌
			InquiryDTO inquiryDTO = inquiryService.get(orderDTO.getInquiry().getInquiryId(), orderDTO.getMicro().getMicro_id());
			String ownerName = inquiryDTO.getOwnerName();
			String plateNo = inquiryDTO.getPlateNo();
			
			//如: 川A32423，车主张天华，中国平安保单已生成 
			String content = plateNo+"，车主"+ownerName+"，"+insName+"保单已生成";
			sendMessageWithInsureSuccess(userId, content, amount, vicOrVciPlyNo, "如有疑问请联系保行天下客服(4009691365),感谢你的支持！");
		} catch (Exception e) {
			logger.error("投保消息推送失败 订单号："+orderId, e);
		}
	}

	@Log("提现失败通知")
	public void sendMessageWithCashFailed(String userId, String firstContent, String moneyAmount, String time, String remark) throws BusinessServiceException, Exception {
		
		//APP消息
		Date date = new Date();
		MessageTargetDTOWithBLOBs messageTargetDTO = new MessageTargetDTOWithBLOBs();
		messageTargetDTO.setCrtBy("system");
		messageTargetDTO.setCrtTm(date);
		messageTargetDTO.setMessageBody(firstContent);
		messageTargetDTO.setMessageTheme(WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_4);
		messageTargetDTO.setUpdBy("system");
		messageTargetDTO.setUpdTm(date);
		
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setMessageTargetDTO(messageTargetDTO);
		messageDTO.setCrtBy("system");
		messageDTO.setCrtTm(date);
		messageDTO.setMessageSendFrom("system");
		messageDTO.setMessageSendTo(userId);
		messageDTO.setSendResultMsg("处理成功");
		messageDTO.setStatus(MessageConstant.STATUS_2);
		messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_1);
		
		messageService.saveMessage(messageDTO);
		
		//微信消息
		messageTargetDTO = new MessageTargetDTOWithBLOBs();
		messageTargetDTO.setCrtBy("system");
		messageTargetDTO.setCrtTm(date);
		messageTargetDTO.setMessageBody(firstContent);
		messageTargetDTO.setMessageTheme(WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_4);
		messageTargetDTO.setUpdBy("system");
		messageTargetDTO.setUpdTm(date);
		
		messageDTO = new MessageDTO();
		messageDTO.setMessageTargetDTO(messageTargetDTO);
		messageDTO.setCrtBy("system");
		messageDTO.setCrtTm(date);
		messageDTO.setMessageSendFrom("system");
		messageDTO.setMessageSendTo(userId);
		messageDTO.setSendResultMsg("待系统处理");
		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_2);
		
		UserDTO ud = userService.queryUserByUserId(userId);
		if(ud != null && StringUtils.isNotBlank(ud.getWechartId())){
			List<SendTemplateMessageDTO> messages = new ArrayList<>();
			
			
			SendTemplateMessageDTO message = new SendTemplateMessageDTO();
			message.setToUser(ud.getWechartId());
			message.setValue(new String[]{firstContent,moneyAmount,time,remark});
			messages.add(message);
			
			String result = weiXinService.sendMessageByTemplate("BXTX_CASH_REQ_FAIL", messages);
			if(!StringUtils.equals("", result)){
				messageDTO.setStatus(MessageConstant.STATUS_2);
				messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
				messageDTO.setSendResultMsg("已提交发送请求");
			}else{
				messageDTO.setSendResultMsg(result);
			}
			
		}else{
			logger.info("用户userId:" +userId + " 的openId为空，不发送微信消息，messageId:" + messageDTO.getMessageId());
		}
//		
//		PlatformDTO baseDTO = platformService.getPlatByCode(BaseFinal.WECHAT_MESSAGE_TEMPLATE, WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_4);
//		Map<String, SendTempMsgItemDTO> data = new HashMap<String, SendTempMsgItemDTO>(4);
//		data.put("first", new SendTempMsgItemDTO(firstContent, "#173177"));
//		data.put("money", new SendTempMsgItemDTO(moneyAmount, "#173177"));//提现金额
//		data.put("time", new SendTempMsgItemDTO(time, "#173177"));//时间
//		data.put("remark", new SendTempMsgItemDTO("", "#141415"));
		messageService.saveMessage(messageDTO);
	}

	
	@Log("人工报价已完成通知")
	public void sendMessageWithMqToUser(String userId, String firstContent, String keyword1, String keyword2,String keyword3, String remark) throws BusinessServiceException, Exception {
		
		//APP消息
		Date date = new Date();
		MessageTargetDTOWithBLOBs messageTargetDTO = new MessageTargetDTOWithBLOBs();
		messageTargetDTO.setCrtBy("system");
		messageTargetDTO.setCrtTm(date);
		messageTargetDTO.setMessageBody(firstContent);
		messageTargetDTO.setMessageTheme(WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_5);
		messageTargetDTO.setUpdBy("system");
		messageTargetDTO.setUpdTm(date);
		
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setMessageTargetDTO(messageTargetDTO);
		messageDTO.setCrtBy("system");
		messageDTO.setCrtTm(date);
		messageDTO.setMessageSendFrom("system");
		messageDTO.setMessageSendTo(userId);
		messageDTO.setSendResultMsg("处理成功");
		messageDTO.setStatus(MessageConstant.STATUS_2);
		messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_1);
		
		messageService.saveMessage(messageDTO);
		
		//微信消息
		messageTargetDTO = new MessageTargetDTOWithBLOBs();
		messageTargetDTO.setCrtBy("system");
		messageTargetDTO.setCrtTm(date);
		messageTargetDTO.setMessageBody(firstContent);
		messageTargetDTO.setMessageTheme(WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_5);
		messageTargetDTO.setUpdBy("system");
		messageTargetDTO.setUpdTm(date);
		
		messageDTO = new MessageDTO();
		messageDTO.setMessageTargetDTO(messageTargetDTO);
		messageDTO.setCrtBy("system");
		messageDTO.setCrtTm(date);
		messageDTO.setMessageSendFrom("system");
		messageDTO.setMessageSendTo(userId);
		messageDTO.setSendResultMsg("待系统处理");
		messageDTO.setMessageType(MessageConstant.MESSAGE_TYPE_2);
		
		UserDTO ud = userService.queryUserByUserId(userId);
		if(ud != null && StringUtils.isNotBlank(ud.getWechartId())){
			List<SendTemplateMessageDTO> messages = new ArrayList<>();
			
			SendTemplateMessageDTO message = new SendTemplateMessageDTO();
			message.setToUser(ud.getWechartId());
			message.setValue(new String[]{firstContent,keyword1,keyword2,keyword3,remark});
			messages.add(message);
			
			String result = weiXinService.sendMessageByTemplate("BXTX_MQ_2USER", messages);
			if(!StringUtils.equals("", result)){
				messageDTO.setStatus(MessageConstant.STATUS_2);
				messageDTO.setTimerStatus(MessageConstant.TIMER_STATUS_3);
				messageDTO.setSendResultMsg("已提交发送请求");
			}else{
				messageDTO.setSendResultMsg(result);
			}
			
		}else{
			logger.info("用户userId:" +userId + " 的openId为空，不发送微信消息，messageId:" + messageDTO.getMessageId());
		}
//		
//		PlatformDTO baseDTO = platformService.getPlatByCode(BaseFinal.WECHAT_MESSAGE_TEMPLATE, WXMessageConstant.WECHAT_MESSAGE_TEMPLATE_4);
//		Map<String, SendTempMsgItemDTO> data = new HashMap<String, SendTempMsgItemDTO>(4);
//		data.put("first", new SendTempMsgItemDTO(firstContent, "#173177"));
//		data.put("money", new SendTempMsgItemDTO(moneyAmount, "#173177"));//提现金额
//		data.put("time", new SendTempMsgItemDTO(time, "#173177"));//时间
//		data.put("remark", new SendTempMsgItemDTO("", "#141415"));
		messageService.saveMessage(messageDTO);
	}
}
