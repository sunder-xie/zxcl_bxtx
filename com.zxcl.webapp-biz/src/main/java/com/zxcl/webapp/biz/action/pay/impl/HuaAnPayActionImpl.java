package com.zxcl.webapp.biz.action.pay.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.action.pay.HuaAnPayAction;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.util.ConstantsUtill;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

@Service
public class HuaAnPayActionImpl implements HuaAnPayAction {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private OrderService orderService;


	@Autowired
	private InsuranceService insuranceService;

	@Override
	public void payedCallback(HttpServletRequest request) throws BusinessServiceException {
		logger.info("华安支付后台回调接收到的参数: " + " result:" + request.getParameter("result") + " 支付申请号:" + request.getParameter("pay_app_no") + " 商业保单号:" + request.getParameter("sy_ply_no") + " 商业投保单号:" + request.getParameter("sy_app_ply_no") + " 交强保单号:" + request.getParameter("jq_ply_no") + " 交强投保单号:" + request.getParameter("jq_app_ply_no"));
		try {
			OrderDTO orderDTO = null;
			if (StringUtils.isNotBlank(request.getParameter("sy_app_ply_no"))) {
				orderDTO = orderService.getOrderIdByApplyNo(request.getParameter("sy_app_ply_no"));
			} else {
				orderDTO = orderService.getOrderIdByApplyNo(request.getParameter("jq_app_ply_no"));
			}
//			orderDTO.setTciPlyNo(request.getParameter("jq_ply_no"));
//			orderDTO.setVciPlyNo(request.getParameter("sy_ply_no"));
			String result_code = request.getParameter("result");// 支付结果
			String queryStage=orderService.getOrderStatusByOrderId(orderDTO.getOrderId());
			logger.info("华安  订单状态：" + queryStage);
			if (!"6".equals(queryStage)) {
				if (result_code.equals("0")) {
					orderService.createPolicyOperation(orderDTO.getOrderId(), request.getParameter("jq_ply_no"), request.getParameter("sy_ply_no"), orderDTO); 
//					ordQueService.updateQueryStage(orderDTO.getOrderId(), orderDTO.getInsurance().getInsId(), ConstantsUtill.ORDERSTATUS_BILLS);
				} else {
					//ordQueService.updateQueryStage(orderDTO.getOrderId(), orderDTO.getInsurance().getInsId(), ConstantsUtill.ORDERSTATUS_PAYFAIL);
				}
//				orderService.updateOrder(orderDTO);
			}
		} catch (Exception e) {
			logger.error("支付回调通知错误", e);
			throw new BusinessServiceException("调用华安支付后台回调接口失败.");
		}
	}

	@Override
	public boolean payedCallbackPage(HttpServletRequest request, String orderId) throws BusinessServiceException {
		try {
			OrderDTO orderDTO = orderService.getAppNoByOrderId(orderId);
			OrderDTO order = orderService.selectOrderByOrderIdAndInsId(orderId, orderDTO.getInsurance().getInsId());
			String queryStage=orderService.getOrderStatusByOrderId(order.getOrderId());
			logger.info("华安支付返回时订单状态：" + queryStage);
			if (null != order) {
				request.setAttribute("success", "1");
				if (!"6".equals(queryStage)) {
					orderService.updateOrderStatusByOrderId(ConstantsUtill.ORDERSTATUS_PAYSUCCESS, orderId);
//					ordQueService.updateQueryStage(order.getOrderId(), order.getInsurance().getInsId(), ConstantsUtill.ORDERSTATUS_PAYSUCCESS);
				}
			} else {
				logger.error("没有查询到投保单号：" + order.getCarApplyNo() + "的订单信息");
			}
			// 查询报价信息
			request.setAttribute("order", orderService.getOrderById(orderDTO.getMicro().getMicro_id(), orderDTO.getInsurance().getInsId(), order.getOrderId()));
			InsuranceDTO insDTO = insuranceService.get(order.getInsurance().getInsId());
			request.setAttribute("insDTO", insDTO);
			return true;
		} catch (BusinessServiceException e) {
			logger.error("华安支付前台回调失败:" + e);
		}
		return false;

	}
}
