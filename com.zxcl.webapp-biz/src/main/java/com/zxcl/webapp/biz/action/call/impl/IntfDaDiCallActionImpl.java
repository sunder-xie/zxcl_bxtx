package com.zxcl.webapp.biz.action.call.impl;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxcl.webapp.biz.action.call.IntfDaDiCallAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.InsXmlDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.PayReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;

import bxtx.intf.dadi.biz.action.DaDiIntfAction;
@Service
public class IntfDaDiCallActionImpl implements IntfDaDiCallAction {

	private Logger logger = Logger.getLogger(getClass());



	@Autowired
	private DaDiIntfAction daDiIntfAction;

	@Autowired
	private MicroService microService;

	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private QuotaService quotaService;
	
	@Autowired
	private OrderService orderService;

	/**
	 * 组装报文
	 * 
	 * @param insId保险公司ID
	 * @param userId用户ID
	 * @param inquiryId询价单号
	 * @return
	 */
	private InsXmlDTO getInsXmlDTO(String insId, String userId, String inquiryId, String xmlType) {

		logger.info("大地处理报文，入参    保险公司ID：" + insId + "   用户ID：" + userId + "  询价单号：" + inquiryId);
		InsXmlDTO insXMl = new InsXmlDTO();
		insXMl.setInsId(insId);
		insXMl.setCrtCode(userId);
		insXMl.setUpdCode(userId);
		insXMl.setInquiryId(inquiryId);
		insXMl.setXmlType(xmlType);
		return insXMl;
	}
	
	@Override
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId, Map<String, Object> configMap) throws ActionException {

		logger.info("大地报价，入参   用户ID：" + userId + "   询价单号：" + inquiryId + "  保险公司ID：" + insId);
		// 生成报价单号
		String quotaId = "HA" + DateUtil.dateToString("yyyyMMddHHmmssSSS", new Date());
		InsXmlDTO insXmlDTO = this.getInsXmlDTO(insId, userId, inquiryId, "3");
		insXmlDTO.setQuotaId(quotaId);
		String quotaRequest;

		// 小微
		MicroDTO microDTO = new MicroDTO();
		try {
			microDTO = microService.getMicroByUserId(userId);
		} catch (BusinessServiceException e) {
			logger.error("大地报价接口数据组装查询小微失败", e);
			throw new ActionException("华安报价接口查询小薇失败");
		}
		// 询价单
		InquiryDTO inquiryDTO = new InquiryDTO();
		try {
			inquiryDTO = inquiryService.get(inquiryId, microDTO.getMicro_id());
			logger.info("询价单：" + JSONObject.toJSONString(inquiryDTO));

		} catch (BusinessServiceException e) {
			logger.error("大地报价接口数据组装查询询价单失败", e);
			throw new ActionException("华安报价接口查询询价单失败");
		}
		try {
			quotaRequest = daDiIntfAction.quotas(userId, inquiryId, insId,null,JSONObject.toJSONString(configMap));
		} catch (Exception e) {
			logger.error("大地接口车型查询查询询价单失败", e);
			return new QuotaReturnDTO("error", "系统错误", insId);
		}
		QuotaReturnDTO quotaReturnDTO = JSON.parseObject(quotaRequest, QuotaReturnDTO.class);
		return quotaReturnDTO;
	}

	@Override
	public VoteInsuranceReturnDTO vote(String userId, String insId, String quotaId, OwnerDTO owner, VoteInsuranceDTO vote, RecognizeeDTO rec, Map<String, Object> configMap) throws ActionException {

		logger.info("大地核保，入参   用户ID：" + userId + "  保险公司ID：" + insId + "   报价单号：" + quotaId + "  OwnerDTO：" + owner + "   VoteInsuranceDTO：" + vote + "   RecognizeeDTO：" + rec);

		// 报价单信息
		QuotaDTO quota = new QuotaDTO();
		try {
			quota = quotaService.getByQuotaId(quotaId);
		} catch (Exception e) {
			logger.error("大地投保接口查询报价单失败", e);
			throw new ActionException("大地获取报价单失败");
		}
		
		String returnJson;
		try {
			returnJson = daDiIntfAction.vote(userId, insId, JSONObject.toJSONString(quota), JSONObject.toJSONString(owner), JSONObject.toJSONString(vote), JSONObject.toJSONString(rec), JSONObject.toJSONString(configMap));
		} catch (Exception e) {
			logger.error("大地接口投保失败", e);
			throw new ActionException("大地接口投保失败");
		}
		VoteInsuranceReturnDTO voteInsuranceReturnDTO = JSON.parseObject(returnJson, VoteInsuranceReturnDTO.class);
		return voteInsuranceReturnDTO;
	}

	@Override
	public PayReturnDTO pay(String userId, String insId, String orderId, String callBackUrl, Map<String, Object> configMap) throws ActionException {

		logger.info("大地支付申请，入参   用户ID：" + userId + "   保险公司ID：" + insId + "   订单ID：" + orderId + "   回调地址：" + callBackUrl);
		// 订单信息
		OrderDTO orderDTO = new OrderDTO();
		try {
			orderDTO = orderService.getOrderById(microService.getMicroByUserId(userId).getMicro_id(), insId, orderId);
		} catch (BusinessServiceException e) {
			logger.error("大地支付接口查询订单失败", e);
			throw new ActionException("大地查询订单失败");
		}
		configMap.put("callBackUrl", callBackUrl);
		String returnJson;
		try {
			returnJson = daDiIntfAction.pay(userId, insId, JSONObject.toJSONString(orderDTO), JSONObject.toJSONString(configMap));
		} catch (Exception e) {
			logger.error("大地支付接口申请支付失败", e);
			throw new ActionException("大地支付接口申请支付失败");
		}
		PayReturnDTO payReturnDTO = JSON.parseObject(returnJson, PayReturnDTO.class);
		return payReturnDTO;
	}

	@Override
	public String undrResult(String xml) throws ActionException {
		logger.info("大地核保回调处理：xml:" + xml);
		String respXml = "";
		try {
			respXml = daDiIntfAction.undrResult(xml, "");
		} catch (Exception e) {
			logger.error("大地核保回调处理失败", e);
			throw new ActionException("大地核保回调处理失败");
		}
		return respXml;
	}

}
