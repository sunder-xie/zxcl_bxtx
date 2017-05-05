package com.zxcl.webapp.biz.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.CommonCarInsurance;
import com.zxcl.webapp.biz.service.MessageSendProvideService;
import com.zxcl.webapp.biz.service.PolicyBaseService;
import com.zxcl.webapp.biz.util.Log;

/**
 * @ClassName: 
 * @Description:统一公共调用接口 （如保单生成调用此接口） 
 * @author zxj
 * @date 2016年4月14日 10:10:09
 */
@Service
public class CommonCarInsuranceImpl implements CommonCarInsurance {
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MessageSendProvideService messageSendProvideService;
	
	@Autowired
	private PolicyBaseService policyBaseService;
	
	@Override
	@Log("保单已生成 公共接口调用开始")
	public void insureSucc(String insId, String orderId) {
		logger.info("insId="+insId+",orderId="+orderId);
		try {
			
			//保单已生成 消息推送
			//messageSendProvideService.sendMessageWithInsureSuccess(insId, orderId);
			messageSendProvideService.sendMessage(MessageSendProvideService.INSURANCE_POLICY_GENERATED, new String[]{insId, orderId});
			
		} catch (Exception e) {
			logger.error("投保成功公共处理失败==>保单已生成 消息推送==>订单号："+orderId, e);
		}
		
		try {
			
			//保单已生成 添加用户费用计算数据
			policyBaseService.addPolicyBase(orderId, insId);
		} catch (Exception e) {
			logger.error("投保成功公共处理失败==>保单已生成 添加用户费用计算数据==>订单号："+orderId, e);
		}
		
		
	}

}
