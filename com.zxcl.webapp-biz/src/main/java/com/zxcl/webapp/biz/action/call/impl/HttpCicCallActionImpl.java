package com.zxcl.webapp.biz.action.call.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.action.call.HttpCicCallAction;
import com.zxcl.webapp.biz.action.data.HttpCicDataAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.zhonghua_intf.api.quote.ZhonghuaCrawlingQuoteService;
import com.zxcl.zhonghua_intf.dto.ApplyInfo;
import com.zxcl.zhonghua_intf.dto.quoteresult.QuoteBackMessageDTO;

@Service
public class HttpCicCallActionImpl implements HttpCicCallAction{
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private HttpCicDataAction cicDataAction;
	
	@Autowired
	private ZhonghuaCrawlingQuoteService zhonghuaCrawlingQuoteService;
	
	@Override
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId,Map<String,Object> configMap)
			throws ActionException {
		logger.info("入参   用户ID："+userId+"   询价单号："+inquiryId+"  保险公司ID："+insId);
		QuotaReturnDTO quotaRequest = new QuotaReturnDTO();
		quotaRequest.setInsId(insId);
		//保险公司信息
		InsuranceDTO insuranceDTO = new InsuranceDTO();
		try {
			insuranceDTO = insuranceService.get(insId);
		} catch (BusinessServiceException e) {
			logger.error("中华报价接口查询保险公司失败", e);
			return new QuotaReturnDTO("error","系统异常",insId);
		}
		//拼装DTO
		ApplyInfo applyInfo = new ApplyInfo();
		try {			
			applyInfo = cicDataAction.quota(userId, insuranceDTO, inquiryId,configMap);
		} catch (ActionException e) {
			logger.error("中华数据组装失败",e);
			return new QuotaReturnDTO("error",e.getMessage(),insId);
		}
		logger.info("中华数据组装结束");
		//中华返回数据
		QuoteBackMessageDTO result = new QuoteBackMessageDTO();
		try {			
			result = zhonghuaCrawlingQuoteService.quote(applyInfo);
		} catch (Exception e) {
			logger.error("中华连接失败",e);
			return new QuotaReturnDTO("error","系统异常",insId);
		}
		//处理返回数据
		if(null != result && null != result.getReInsureItem()){//重复投保
			boolean i = false;
			String reInsureInfo = "";
			//判断商业险是否重复投保
			if (result.getReInsureItem().isTciReInsure()) {
				reInsureInfo += "交强险重复投保，最新保单止期：" + DateUtil.dateToString("yyyy-MM-dd", result.getReInsureItem().getTciEndDate()) + "。";
				i = true;
			}
			//判断交强险是否重复投保
			if (result.getReInsureItem().isVciReInsure()) {
				reInsureInfo += "商业险重复投保，最新保单止期：" + DateUtil.dateToString("yyyy-MM-dd", result.getReInsureItem().getExpireDate()) + "。";
				i = true;
			}
			if(i){
				return new QuotaReturnDTO("error",reInsureInfo,insId);
			}
		}
		if(null != result && null != result.getQtnBase() && "SUCCESS".equals(result.getQtnBase().getErrorCode())){
			logger.info("中华报价返回成功");
			quotaRequest = cicDataAction.quotaReturn(result,insId);
		}else if(null != result && null != result.getQtnBase() && "FAIL".equals(result.getQtnBase().getErrorCode())){
			logger.info("中华报价返回失败");
			logger.info("中华报价失败原因："+result.getQtnBase().getErrorMsg());
			return new QuotaReturnDTO("error",result.getQtnBase().getErrorMsg(),insId);
		}else if(null != result && null != result.getQtnBase() && "PSDERROR".equals(result.getQtnBase().getErrorCode())){
			logger.info("中华报价账号密码错误");
			return new QuotaReturnDTO("PSDERROR",result.getQtnBase().getErrorMsg(),insId,applyInfo.getClientUser().getAgentUserId());
		}else{
			logger.info("中华报价系统异常");
			return new QuotaReturnDTO("error","系统异常",insId);
		}
		//报价单号
		quotaRequest.setQuotaId(applyInfo.getFrontQtnId());
		return quotaRequest;
	}
	
}
