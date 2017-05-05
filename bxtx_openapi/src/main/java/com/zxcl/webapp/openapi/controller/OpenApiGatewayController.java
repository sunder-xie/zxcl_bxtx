package com.zxcl.webapp.openapi.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxcl.webapp.biz.service.openapi.OpenApiExecService;
import com.zxcl.webapp.biz.service.openapi.OpenApiManageService;
import com.zxcl.webapp.dto.openapi.ApiAppDTO;
import com.zxcl.webapp.dto.openapi.ApiCallRecordDTO;
import com.zxcl.webapp.dto.openapi.MsgDTO;
import com.zxcl.webapp.dto.openapi.MsgException;
import com.zxcl.webapp.dto.openapi.ResultMsgDTO;
import com.zxcl.webapp.openapi.util.ApiUtil;

@Controller
public class OpenApiGatewayController {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private OpenApiManageService openApiManageService;
	
	@Autowired
	private OpenApiExecService openApiExecService;
	
	@RequestMapping(value="/gateway.do")
	public void gateway(HttpServletRequest httpReq, HttpServletResponse httpResp){
		long start = System.currentTimeMillis();
		String method = null;
		String appId = null;
		Date callBeginTm = new Date();
		ApiCallRecordDTO record = null;
		ResultMsgDTO respMsg = null; //调用业务返回的结果对象
		try {
			if(httpReq.getMethod().equalsIgnoreCase("GET")){
				throw new MsgException("请发送POST请求");
			}
			
			String reqXml = IOUtils.toString(httpReq.getInputStream(), "utf-8");
			logger.info("接收到请求报文如下:\n" + reqXml);

			Map<String,String> reqMap = ApiUtil.parseXmlFields(reqXml);
			
			appId = reqMap.get("appId");
			if(StringUtils.isEmpty(appId)){
				throw new MsgException("appId必填");
			}
			
			method = reqMap.get("method");
			if(StringUtils.isEmpty(method)){
				throw new MsgException("method必填");
			}
			
			String timestamp = reqMap.get("timestamp");
			if(StringUtils.isEmpty(timestamp)){
				throw new MsgException("timestamp必填");
			}
			
			String sign = reqMap.get("sign");
			if(StringUtils.isEmpty(sign)){
				throw new MsgException("sign必填");
			}
			
			String bizContent = reqMap.get("bizContent");
			if(StringUtils.isEmpty(bizContent)){
				throw new MsgException("bizContent必填");
			}
			
			ApiAppDTO app = lookupApp(appId);
			if(app == null) {
				throw new MsgException( "appId:" + appId + "不合法");
			}

			String mySign = ApiUtil.makeSign(reqMap, app.getAppKey());
			logger.info("mySign:" + mySign);
			if(!mySign.equals(sign)) {
				throw new MsgException( "签名验证错误");
			} else {
				logger.info("签名验证通过");
			}

			MsgDTO msg = new MsgDTO();
			msg.setAppId(appId);
			msg.setBizContent(bizContent);
			msg.setMethod(method);
			msg.setTimestamp(timestamp);
			
			record = new ApiCallRecordDTO();
			record.setAppId(appId);
			record.setMethod(method);
			record.setCallTm(callBeginTm);
			record.setCostMs(0);
			record.setResultCode("S");
			long callId = openApiManageService.addApiCallRecord(record);
			msg.setCallId(callId);
			logger.info("接口请求处理开始|" + appId + "|" + method + "|" + callId);
			respMsg = routeRequest(msg);

			String respXml = packRespMsg(respMsg.getResultXml(), msg, app.getAppKey());
			
			httpResp.getOutputStream().write(respXml.getBytes("utf-8"));
			
			long costMs = (System.currentTimeMillis() - start);
			record.setCostMs(costMs);
			record.setResultCode("S");
			record.setResultMsg("调用成功");
			record.setBizRetCode(respMsg.getBizRetCode());
			record.setBizRetMsg(respMsg.getBizRetMsg());
			openApiManageService.updateApiCallRecord(record);
			logger.info("保存接口调用记录完成");
			logger.info("接口请求处理成功|" + appId + "|" + method + "|耗时:" + costMs);
		} catch (Exception e) {
			
			if( e instanceof MsgException) {
				logger.error("消息处理错误:" + e.getMessage(),e);
				writeError(httpResp, e.getMessage());
			} else {
				logger.error("网关处理错误",e);
				writeError(httpResp, "网关处理错误");
			}
			
			long costMs = (System.currentTimeMillis() - start);
			
			String err = e.getMessage();
			String bizCode = null;
			String bizMsg = null;
			if(err != null && err.length() > 100){
				err = err.substring(0,100);
			}
			if(respMsg != null) {
				bizCode = respMsg.getBizRetCode();
				bizMsg = respMsg.getBizRetMsg();
			}
			if(record != null && record.getCallId() > 0) {
				record.setCostMs(costMs);
				record.setResultCode("F");
				record.setResultMsg(err);
				record.setBizRetCode(bizCode);
				record.setBizRetMsg(bizMsg);
				openApiManageService.updateApiCallRecord(record);
			} else if(!StringUtils.isEmpty(appId)){
				record = new ApiCallRecordDTO();
				record.setAppId(appId);
				record.setMethod(method == null ? "-" : method);
				record.setCallTm(callBeginTm);
				record.setCostMs(costMs);
				record.setResultCode("F");
				record.setResultMsg(err);
				record.setBizRetCode(bizCode);
				record.setBizRetMsg(bizMsg);
				openApiManageService.addApiCallRecord(record);
			}
			logger.info("保存接口调用记录完成");
			logger.info("接口请求处理失败|" + appId + "|" + method + "|耗时" + costMs);
		} 
		
	}
	
	protected ApiAppDTO lookupApp(String appId) {
		
		return openApiManageService.findApp(appId);
		
	}
	
	/**
	 * 路由请求报文到后端系统
	 * @param msg 消息对象
	 * @return 响应消息文本
	 */
	protected ResultMsgDTO routeRequest(MsgDTO msg) throws MsgException{
		
		try {
			ResultMsgDTO result = null;
			if("agent.vhlquery".equals(msg.getMethod())) {
				result = openApiExecService.agentVhlQuery(msg);
			} else if("agent.renewal.query.plate".equals(msg.getMethod())) {
				result = openApiExecService.agentRenewalQuery(msg);
			} else if("agent.vhlquote".equals(msg.getMethod())) {
				result = openApiExecService.agentVhlQuote(msg);
			} else {
				throw new MsgException("不支持的method:" + msg.getMethod());
			}
			
			logger.info("业务服务调用完成,返回状态errorCode=" + result.getBizRetCode() + ",errorMsg=" + result.getBizRetCode());
			return result;
			
		} catch(MsgException e){
			throw e;
		} catch (Exception e) {
			throw new MsgException( "业务服务器处理错误" , e);
		}
		
	}
	
	public void writeError(HttpServletResponse httpResp,String msg){
		httpResp.addHeader("Content-Type", "text/xml;charset=UTF-8");
		String xml = "<xml>\n"
				+ "<returnCode>FAIL</returnCode>\n"
				+ "<returnMsg>" + msg + "</returnMsg>\n"
				+ "</xml>";
		try {
			logger.info("返回错误报文:" + xml);
			httpResp.getOutputStream().write(xml.getBytes("utf-8"));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
	
	protected String packRespMsg(String bizContent, MsgDTO msg, String appKey) {
		
		logger.info("返回的业务报文:");
		logger.info(bizContent);
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("returnCode", "SUCCESS");
		params.put("returnMsg", "调用成功");
		params.put("bizContent", bizContent);
		
		String sign = ApiUtil.makeSign(params,appKey);
		logger.info("响应签名:" + sign);
		
		String xml = "<xml>\n";
		
		xml += "<returnCode>" + params.get("returnCode") + "</returnCode>\n";
		
		xml += "<returnMsg><![CDATA[" + params.get("returnMsg") + "]]></returnMsg>\n";
		
		xml += "<sign>" + sign + "</sign>\n";
		
		xml += "<bizContent><![CDATA[" + bizContent + "]]></bizContent>\n";
		
		xml += "</xml>";

		logger.info("签名后的响应报文:");
		
		logger.info(xml);
		
		return xml;
	}
}
