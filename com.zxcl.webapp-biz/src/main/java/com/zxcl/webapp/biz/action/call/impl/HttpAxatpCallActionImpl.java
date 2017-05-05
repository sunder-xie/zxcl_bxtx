package com.zxcl.webapp.biz.action.call.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.tianping_http.api.TianpingHttpQuoteService;
import com.zxcl.tianping_http.dto.ApplyInfoDTO;
import com.zxcl.tianping_http.dto.quoteresult.QuoteBackMessageDTO;
import com.zxcl.webapp.biz.action.call.HttpAxatpCallAction;
import com.zxcl.webapp.biz.action.data.HttpAxatpDataAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

@Service
public class HttpAxatpCallActionImpl implements HttpAxatpCallAction{
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private HttpAxatpDataAction httpAxatpDataAction;
	
	@Autowired
	private TianpingHttpQuoteService tianpingHttpQuoteService;
	
	@Override
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId,
			Map<String, Object> configMap) throws ActionException {
		logger.info("HttpAxatpCallActionImpl    quotas  天平报价   入参   userId："+userId+"   inquiryId："+inquiryId+"  insId："+insId+"  configMap："+configMap);
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		quotaReturnDTO.setInsId(insId);
		//保险公司信息
		InsuranceDTO insuranceDTO = new InsuranceDTO();
		try {
			insuranceDTO = insuranceService.get(insId);
		} catch (BusinessServiceException e) {
			logger.error("天平报价接口查询保险公司失败", e);
			return new QuotaReturnDTO("error","系统异常",insId);
		}
		ApplyInfoDTO applyInfoDTO = new ApplyInfoDTO();
		try {			
			applyInfoDTO = httpAxatpDataAction.quota(userId, insuranceDTO, inquiryId,configMap);
		} catch (ActionException e) {
			logger.error("天平数据组装失败",e);
			return new QuotaReturnDTO("error",e.getMessage(),insId);
		}
		
		QuoteBackMessageDTO result = new QuoteBackMessageDTO();
		result = tianpingHttpQuoteService.quote(applyInfoDTO);
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
				quotaReturnDTO = new QuotaReturnDTO("error",reInsureInfo,insId);
				quotaReturnDTO.setTciReInsureSign(result.getReInsureItem().isTciReInsure() ? "1" : "0");
				quotaReturnDTO.setVciReInsureSign(result.getReInsureItem().isVciReInsure() ? "1" : "0");
				
				if(result.getReInsureItem().getTciBeginDate() != null) {
					quotaReturnDTO.setTciReInsureBeginDate(DateUtil.dateToString("yyyy-MM-dd",result.getReInsureItem().getTciBeginDate()));
				}
				if(result.getReInsureItem().getTciEndDate() != null) {
					quotaReturnDTO.setTciReInsureEndDate(DateUtil.dateToString("yyyy-MM-dd",result.getReInsureItem().getTciEndDate()));
				}
				if(result.getReInsureItem().getEffectiveDate() != null) {
					quotaReturnDTO.setVciReInsureEndDate(DateUtil.dateToString("yyyy-MM-dd",result.getReInsureItem().getEffectiveDate()));
				}
				if(result.getReInsureItem().getExpireDate() != null) {
					quotaReturnDTO.setVciReInsureEndDate(DateUtil.dateToString("yyyy-MM-dd",result.getReInsureItem().getExpireDate()));
				}
				return quotaReturnDTO;
			}
		}
		if(null != result && null != result.getQtnBase() && "SUCCESS".equals(result.getQtnBase().getErrorCode())){
			logger.info("天平报价返回成功");
			quotaReturnDTO = httpAxatpDataAction.quotaReturn(result,insId);
		}else if(null != result && null != result.getQtnBase() && "FAIL".equals(result.getQtnBase().getErrorCode())){
			logger.info("天平报价返回失败");
			logger.info("天平报价失败原因："+result.getQtnBase().getErrorMsg());
			return new QuotaReturnDTO("error",result.getQtnBase().getErrorMsg(),insId);
		}else if(null != result && null != result.getQtnBase() && "PSDERROR".equals(result.getQtnBase().getErrorCode())){
			logger.info("天平报价账号密码错误");
			return new QuotaReturnDTO("PSDERROR",result.getQtnBase().getErrorMsg(),insId,applyInfoDTO.getClientUser().getAgentUserId());
		}else{
			logger.info("天平报价系统异常");
			return new QuotaReturnDTO("error","系统异常",insId);
		}
		
		return quotaReturnDTO;
	}

}
