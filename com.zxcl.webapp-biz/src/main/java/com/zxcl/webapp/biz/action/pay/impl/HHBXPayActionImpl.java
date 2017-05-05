package com.zxcl.webapp.biz.action.pay.impl;



import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.action.pay.PayAction;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.integration.dao.ResponseXmlDAO;

@Service("hhbxwapAction")
public class HHBXPayActionImpl implements PayAction {
	
	protected Logger logger=Logger.getLogger(getClass());

	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private ResponseXmlDAO	responseXmlDAO;
	
	@Autowired
	private CallAction callAction;

	@Override
	public boolean payedCallback(HttpServletRequest request,String userId) throws BusinessServiceException {
		logger.info("华海支付回调开始");
		try {
			
			Map<String, String[]>  map = request.getParameterMap();
			if(null != map){
				for (String item : map.keySet()) {
					logger.info(item+"="+Arrays.toString(map.get(item)));
				}
			}
		} catch (Exception e) {
			logger.error("华海支付回调失败", e);
		}
		logger.info("华海支付回调结束");
		return true;
	}






	@Override
	public boolean payedCallback2(HttpServletRequest request, String orderId) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean pay(HttpServletRequest request, String userId, OrderDTO order) {
		// TODO Auto-generated method stub
		return false;
	}
}
