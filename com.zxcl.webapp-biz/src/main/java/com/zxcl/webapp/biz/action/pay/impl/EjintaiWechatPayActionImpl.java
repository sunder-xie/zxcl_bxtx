package com.zxcl.webapp.biz.action.pay.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.action.pay.PayAction;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.util.ConstantsUtill;
import com.zxcl.webapp.biz.util.pay.PayUtil;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.WeChatPayDTO;

/**
 * 锦泰的微信支付
 * 
 * @author 5555
 *
 */
@Service("jticwechatAction")
public class EjintaiWechatPayActionImpl implements PayAction {

	Logger logger = Logger.getLogger(this.getClass());

	@Value("${ejintai.weChat.weChatUrl}")
	private String weChatUrl;

	@Autowired
	private OrderService orderService;

	@Autowired
	private MicroService microService;

	@Autowired
	private CallAction callAction;

	@Override
	public boolean pay(HttpServletRequest request, String userId, OrderDTO order) {
		try {
			WeChatPayDTO packet = callAction.weChatPay(userId,
					order.getInsurance().getInsId(), order.getOrderId(), weChatUrl);
			request.setAttribute("weChatUrl", packet.getUrl());
		} catch (Exception e) {
			logger.error("订单:" + order.getOrderId() + ",调用微信缴费接口失败" + e);
		}
		return true;
	}

	@Override
	public boolean payedCallback(HttpServletRequest request, String userId) {
		String returnCode = request.getParameter("result_code");
		String carApplyNo = request.getParameter("carApplyNo");
		logger.info("投保单号:" + carApplyNo + ",支付状态：" + returnCode);
		//处理
		if(StringUtils.isNotBlank(carApplyNo)){
			String last=carApplyNo.substring(carApplyNo.length()-1, carApplyNo.length());
			if("/".equals(last)){
				carApplyNo=carApplyNo.substring(0,carApplyNo.length()-1);
			} 
		}
		try {
			String microId = microService.getMicroByUserId(userId).getMicro_id();
			OrderDTO order = orderService.getOrderByCarApplyNo(microId, carApplyNo);
			if (null != order) {
				if (PayUtil.EJINTAI_WECHATPAY_SUCCESS.equals(returnCode)) {
//					ordQueService.updateQueryStage(order.getOrderId(), order.getInsurance()
//							.getInsId(), ConstantsUtill.ORDERSTATUS_PAYSUCCESS);
					orderService.updateOrderStatusByOrderId(ConstantsUtill.ORDERSTATUS_PAYSUCCESS, order.getOrderId());
					orderService.insertReport(order.getInsurance().getInsId(), order.getOrderId(),
							userId);
					request.setAttribute("success", "1");
				} else {
//					ordQueService.updateQueryStage(order.getOrderId(), order.getInsurance()
//							.getInsId(), ConstantsUtill.ORDERSTATUS_PAYFAIL);
					request.setAttribute("success", "0");
				}
			} else {
				logger.warn("没有查询到投保单号：" + carApplyNo + "的订单信息");
			}
			// 查询报价信息
			request.setAttribute("order",
					orderService.getOrderById(microId, order.getInsurance().getInsId(),
							order.getOrderId()));
			return true;
		} catch (BusinessServiceException e) {
			logger.error("用户:" + userId + "微信回调失败:" + e);
		}
		return false;
	}

	@Override
	public boolean payedCallback2(HttpServletRequest request, String orderId) {
		// TODO Auto-generated method stub
		return false;
	}
}
