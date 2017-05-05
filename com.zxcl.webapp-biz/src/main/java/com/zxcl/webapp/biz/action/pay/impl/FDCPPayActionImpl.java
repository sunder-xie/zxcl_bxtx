package com.zxcl.webapp.biz.action.pay.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.action.pay.PayAction;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.util.ConstantsUtill;
import com.zxcl.webapp.dto.OrderDTO;

/**
 * 富德支付
 * 
 * @author zxj
 *
 */
@Service("fdcpwapAction")
public class FDCPPayActionImpl implements PayAction {

	Logger logger = Logger.getLogger(this.getClass());


	@Autowired
	private OrderService orderService;

	@Autowired
	private MicroService microService;

	@Autowired
	private CallAction callAction;

	@Override
	public boolean pay(HttpServletRequest request, String userId, OrderDTO order) {
		return true;
	}

	@Override
	public boolean payedCallback(HttpServletRequest request, String userId) throws BusinessServiceException {
		String returnCode = request.getParameter("payStatus");//1成功 0 失败
		String noticeNo = request.getParameter("orderNo");
		logger.info("订单号:" + noticeNo + ",支付状态：" + returnCode);
		OrderDTO order = orderService.getOrderByNoticeNo(userId, noticeNo);
		if (null != order) {
			userId = (StringUtils.isBlank(userId))?order.getCrtCode():userId;
			String microId = microService.getMicroByUserId(userId).getMicro_id();
			OrderDTO orderDTO = orderService.getOrderById(microId, order.getInsurance().getInsId(), order.getOrderId());
			if(null != orderDTO){
				if(ConstantsUtill.ORDERSTATUS_PAYSUCCESS.equals(orderDTO.getQueryStage())){
					throw new BusinessServiceException("订单号：【" + noticeNo + "】状态为已支付，不能重复更新");
				}
				if(ConstantsUtill.ORDERSTATUS_BILLS.equals(orderDTO.getQueryStage())){
					throw new BusinessServiceException("订单号：【" + noticeNo + "】状态为已经生成保单，不能更新为已支付");
				}
			}
			else{
				logger.warn("没有查询到订单：【" + order.getOrderId() + "】的订单信息");
			}
			orderService.updateOrderStatusByOrderId(ConstantsUtill.ORDERSTATUS_PAYSUCCESS, order.getOrderId());
//			ordQueService.updateQueryStage(order.getOrderId(), order.getInsurance().getInsId(), ConstantsUtill.ORDERSTATUS_PAYSUCCESS);
		} else {
			logger.warn("没有查询到订单号：【" + noticeNo + "】的订单信息");
			throw new BusinessServiceException("没有查询到订单号：【" + noticeNo + "】的订单信息");
		}
		return true;
	}

	@Override
	public boolean payedCallback2(HttpServletRequest request, String orderId) {
		// TODO Auto-generated method stub
		return false;
	}
}
