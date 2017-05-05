package com.zxcl.webapp.biz.action.pay.impl;



import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.action.pay.PayAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.util.ConstantsUtill;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.port.paic.PAICPayCallBackDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.integration.dao.ResponseXmlDAO;

@Service("paicwapAction")
public class PAICPayActionImpl implements PayAction {
	
	private Logger logger=Logger.getLogger(getClass());

	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MicroService microService;
	
	
	@Autowired
	private ResponseXmlDAO	responseXmlDAO;
	
	@Autowired
	private CallAction callAction;
	
	
	
//	public PAICPayReturnDTO pay(HttpServletRequest request, String userId, OrderDTO order) {
//		logger.info("用户:"+userId+"生成保险公司:"+order.getInsurance().getInsId()+",订单号:"+order.getOrderId()+"支付链接");
//		PAICPayReturnDTO payReturnDTO = new PAICPayReturnDTO();
//		PAICJsonResponseDTO baseResponseDTO = null;
//		PAICNoticeOrderResponseDTO responseDTO = null;
//		try {
//			
//			//调用地址
//			payReturnDTO.setPayUrl(paicService.getAccessUrlService("15","PAIC",userId));
//			
//			
//			//获取通知单号
//			InsXmlDTO insXml = new InsXmlDTO();
//			insXml.setXmlType("61");
//			insXml.setInsId("PAIC");
//			insXml.setRequestOrBack("B");
//			insXml.setOrderId(order.getOrderId());
//			insXml.setQuotaId(order.getQuota().getQuotaId());
//			insXml.setInquiryId(order.getInquiry().getInquiryId());
//			insXml = responseXmlDAO.getDataByDTO(insXml);
//			baseResponseDTO = JSONObject.parseObject(insXml.getXmlFile(), PAICJsonResponseDTO.class);
//			responseDTO = JSONObject.parseObject(baseResponseDTO.getData(), PAICNoticeOrderResponseDTO.class);
//			payReturnDTO.setBusinessNo(responseDTO.getNoticeno_out());
//			
//			//客户姓名 手机号
//			MicroDTO microDTO = microService.getMicroByUserId(userId);
//			payReturnDTO.setCustomerName(microDTO.getMicro_name());
//			payReturnDTO.setPhoneNo(microDTO.getTel());
//			
//			//金额
//			BigDecimal amount = new BigDecimal("0.00");
//			amount = amount.add(order.getQuota().getTCIPremTax());
//			amount = amount.add(order.getQuota().getVCIPremTax());
//			amount = amount.add(order.getQuota().getVehicleTax());
//			payReturnDTO.setAmount(amount.toString());
//			
//			
//		} catch (Exception e) {
//			logger.error("获取平安支付数据失败", e);
//		}
//		
//		return payReturnDTO;
//	}

	@Override
	public boolean payedCallback(HttpServletRequest request,String userId) throws BusinessServiceException {
		
		PAICPayCallBackDTO PAICPayCallBackDTO = new PAICPayCallBackDTO();
		PAICPayCallBackDTO.setBankOrderNo(request.getParameter("bankOrderNo"));
		PAICPayCallBackDTO.setBusinessNo(request.getParameter("businessNo"));
		PAICPayCallBackDTO.setDocumentNo(request.getParameter("documentNo"));
//		PAICPayCallBackDTO.setErrorMsg(request.getParameter("errorMsg"));
		PAICPayCallBackDTO.setPaymentDate(request.getParameter("paymentDate"));
		PAICPayCallBackDTO.setPaymentState(request.getParameter("paymentState"));
		PAICPayCallBackDTO.setPaymentSum(request.getParameter("paymentSum"));
		PAICPayCallBackDTO.setRemark(request.getParameter("remark"));
		PAICPayCallBackDTO.setSignMsg(request.getParameter("signMsg"));
		logger.info("平安支付回调参数=" + PAICPayCallBackDTO.toString());
		
		logger.info("通知单号:" + PAICPayCallBackDTO.getBusinessNo() + ",支付状态：" + PAICPayCallBackDTO.getPaymentState());
		OrderDTO order = orderService.getOrderByNoticeNo(userId, PAICPayCallBackDTO.getBusinessNo());
		if(null == userId){
			userId = order.getCrtCode();
		}
		String microId = microService.getMicroByUserId(userId).getMicro_id();
		if (null != order) {
			order = orderService.getOrderById(microId, order.getInsurance().getInsId(), order.getOrderId());
		}
		if (null != order) {
			if("1".equals(PAICPayCallBackDTO.getPaymentState())){
				logger.info("平安投保支付成功");
				if(ConstantsUtill.ORDERSTATUS_PAYSUCCESS.equals(order.getQueryStage())){
					throw new BusinessServiceException("平安订单号:" + order.getOrderId() + "状态为已支付  不能重复更新");
				}
				orderService.updateOrderStatusByOrderId(ConstantsUtill.ORDERSTATUS_PAYSUCCESS, order.getOrderId());
//				ordQueService.updateQueryStage(order.getOrderId(), order.getInsurance().getInsId(), ConstantsUtill.ORDERSTATUS_PAYSUCCESS);
				logger.info("平安订单号=" + order.getOrderId() + " 更新已支付成功");
				logger.info("平安支付成功  调用承保接口开始");
				try {
					
					CombinedQueryDTO combinedQuery = callAction.combinedQuery(userId, order.getInsurance().getInsId(),order.getOrderId(), null);
					
					
					//处理状态
					logger.info("处理状态");
					String statusQuery="";
					if(StringUtils.isNotBlank(combinedQuery.getVciApplyStatus()) && StringUtils.isNotBlank(combinedQuery.getTciApplyStatus())){
						if(combinedQuery.getVciApplyStatus().equals(combinedQuery.getTciApplyStatus())){
							statusQuery = combinedQuery.getVciApplyStatus();
						}else{
							if("3".equals(combinedQuery.getVciApplyStatus()) || "3".equals(combinedQuery.getTciApplyStatus())){
								statusQuery = "3";
							}else if("2".equals(combinedQuery.getVciApplyStatus()) || "2".equals(combinedQuery.getTciApplyStatus())){
								statusQuery = "2";
							}else if("5".equals(combinedQuery.getVciApplyStatus()) || "5".equals(combinedQuery.getTciApplyStatus())){
								statusQuery = "5";
							}else{
								logger.info("错误,交强险状态："+combinedQuery.getTciApplyStatus()+" 商业险状态:"+combinedQuery.getVciApplyStatus());
							}
						}
					}else{
						statusQuery = StringUtils.isNotBlank(combinedQuery.getVciApplyStatus())?combinedQuery.getVciApplyStatus():combinedQuery.getTciApplyStatus();
					}
					logger.info("处理状态statusQuery=" + statusQuery);
					
					OrderDTO orderDTO=new OrderDTO();
					if(StringUtils.isNotBlank(combinedQuery.getTciPolicyNO())){
						orderDTO.setTciPlyNo(combinedQuery.getTciPolicyNO());
					}
					if(StringUtils.isNotBlank(combinedQuery.getVciPolicyNO())){
						orderDTO.setVciPlyNo(combinedQuery.getVciPolicyNO());
					}
					orderDTO.setInsurance(new InsuranceDTO(order.getInsurance().getInsId()));
					orderDTO.setOrderId(order.getOrderId());
					orderDTO.setQueryStage(statusQuery);
					orderDTO.setUpdCode("system");
					if(StringUtils.isNotBlank(statusQuery) && !("2".equals(statusQuery) || "4".equals(statusQuery) || "14".equals(statusQuery))){
						logger.info("开始更新订单为已出单");
						orderService.updatePolicyNoAndQueryState(order);
						logger.info("更新订单结束");
						
					}else{
						logger.info("未更新订单为已出单");
					}
				} catch (ActionException e) {
					logger.error("平安支付成功  调用承保接口失败,订单号="+order.getOrderId(), e);
				}
				logger.info("平安支付成功  调用承保接口结束");
			}else{
				logger.info("平安投保支付失败");
				//ordQueService.updateQueryStage(order.getOrderId(), order.getInsurance().getInsId(), ConstantsUtill.ORDERSTATUS_PAYFAIL);
			}
		} else {
			logger.warn("没有查询到订单号：【" + PAICPayCallBackDTO.getBusinessNo() + "】的订单信息");
		}
		return true;
	}



	@Override
	public boolean pay(HttpServletRequest request, String userId, OrderDTO order) {
		return false;
	}



	@Override
	public boolean payedCallback2(HttpServletRequest request, String orderId) {
		// TODO Auto-generated method stub
		return false;
	}
}
