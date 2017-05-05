package com.zxcl.webapp.biz.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zxcl.webapp.biz.action.timer.TimerOrderStatusQueryService;
import com.zxcl.webapp.biz.service.MessagePolicyRmiService;
import com.zxcl.webapp.biz.service.MessageSendProvideService;
import com.zxcl.webapp.biz.service.PolicyBaseService;
import com.zxcl.webapp.biz.service.ResourceVehicleService;
import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.integration.dao.InquiryDAO;
import com.zxcl.webapp.integration.dao.OrderDAO;


/**
 * @author zxj
 * @date 2016年8月5日
 * @description 
 */
@Service
public class MessagePolicyRmiServiceImpl implements MessagePolicyRmiService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private PolicyBaseService policyBaseService;
	
	@Autowired
	private MessageSendProvideService messageSendProvideService;
	
	@Autowired
	private ResourceVehicleService resourceVehicleService;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private InquiryDAO inquiryDAO;
	
	@Autowired
	private TimerOrderStatusQueryService timerOrderStatusQueryService;

	@Log("远程调用-保单生成消息推送+新增保费数据t_policy_base")
	@Override
	public void sendPolicyMessage(String orderId, String insId) throws Exception {
		logger.info("sendPolicyMessage,insId="+insId+",orderId="+orderId);
		try {
			OrderDTO orderDTO = orderDAO.getOrderById(null, null, orderId);
			logger.info("orderDTO:"+JSON.toJSONString(orderDTO));
			
			timerOrderStatusQueryService.doExecute(orderDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		try {
			
			//保单已生成 消息推送
			logger.info("保单已生成 消息推送");
			//messageSendProvideService.sendMessageWithInsureSuccess(insId, orderId);
			messageSendProvideService.sendMessage(MessageSendProvideService.INSURANCE_POLICY_GENERATED, new String[]{insId, orderId});
			
		} catch (Exception e) {
			logger.error("投保成功公共处理失败==>保单已生成 消息推送==>订单号："+orderId, e);
		}
		
		
		String inquiryId= "";
		try {
			logger.info("根据订单号获取询价单号和保险公司ID，订单号为："+orderId);
			Map<String,Object> map = orderDAO.getInquiryIdByOrderId(orderId);
			if(null != map){
				inquiryId = CommonUtil.valueOf(map.get("inquiryId"));
				insId = CommonUtil.valueOf(map.get("insId"));
			}
		} catch (Exception e) {
			logger.error("根据订单号获取询价单号和保险公司ID失败，订单号为："+orderId,e);
		}
		if(StringUtils.isNotBlank(inquiryId)){
			try {
				//状态为6，保单生成，添加资源信息
				resourceVehicleService.insert(inquiryId);
			} catch (Exception e) {
				logger.error("资源信息添加失败，订单号为："+orderId,e);
			}
			try {						
				//更新询价单状态
				inquiryDAO.updateInquiryStatusByInquiryId("3", inquiryId, null);
			} catch (Exception e) {
				logger.error("更新询价单状态失败，订单号为："+orderId,e);
			}
		}
		
		
		try {
			
			//保单已生成 添加用户费用计算数据
			logger.info("保单已生成 添加用户费用计算数据");
			policyBaseService.addPolicyBase(orderId, insId);
		} catch (Exception e) {
			logger.error("投保成功公共处理失败==>保单已生成 添加用户费用计算数据==>订单号："+orderId, e);
		}
	}
}
