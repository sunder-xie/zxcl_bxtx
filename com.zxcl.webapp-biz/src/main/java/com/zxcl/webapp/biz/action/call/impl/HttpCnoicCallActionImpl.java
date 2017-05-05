package com.zxcl.webapp.biz.action.call.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.huahai_http.api.HuahaiHttpQuoteService;
import com.zxcl.huahai_http.dto.ApplyInfoDTO;
import com.zxcl.huahai_http.dto.quoteresult.QuoteBackMessageDTO;
import com.zxcl.webapp.biz.action.call.HttpCnoicCallAction;
import com.zxcl.webapp.biz.action.data.HttpCnoicDataAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

@Service
public class HttpCnoicCallActionImpl implements HttpCnoicCallAction{
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private HttpCnoicDataAction httpCnoicDataAction;
	
	@Autowired
	private HuahaiHttpQuoteService huahaiHttpQuoteService;
	
	@Override
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId,
			Map<String, Object> configMap) throws ActionException {
		logger.info("HttpCnoicCallActionImpl  quotas  华海报价     入参    userId："+userId+"  inquiryId："+inquiryId+"   insId："+insId+"  configMap："+configMap);
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		quotaReturnDTO.setInsId(insId);
		//保险公司信息
		InsuranceDTO insuranceDTO = new InsuranceDTO();
		try {
			insuranceDTO = insuranceService.get(insId);
		} catch (BusinessServiceException e) {
			logger.error("华海报价接口查询保险公司失败", e);
			return new QuotaReturnDTO("error","系统异常",insId);
		}
		//拼装DTO
		ApplyInfoDTO apply = new ApplyInfoDTO();
		try {
			apply = httpCnoicDataAction.quota(userId, insuranceDTO, inquiryId, configMap);
			QuoteBackMessageDTO result = huahaiHttpQuoteService.quote(apply);
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
				logger.info("华海报价返回成功");
				quotaReturnDTO = httpCnoicDataAction.quotaReturn(result,insId);
			}else if(null != result && null != result.getQtnBase() && "FAIL".equals(result.getQtnBase().getErrorCode())){
				logger.info("华海报价返回失败");
				logger.info("华海报价失败原因："+result.getQtnBase().getErrorMsg());
				return new QuotaReturnDTO("error",result.getQtnBase().getErrorMsg(),insId);
			}else if(null != result && null != result.getQtnBase() && "PSDERROR".equals(result.getQtnBase().getErrorCode())){
				logger.info("华海报价账号密码错误");
				return new QuotaReturnDTO("PSDERROR",result.getQtnBase().getErrorMsg(),insId,apply.getClientUser().getAgentUserId());
			}else{
				logger.info("华海报价系统异常");
				return new QuotaReturnDTO("error","系统异常",insId);
			}
		} catch (Exception e) {
			logger.error("华海报价接口数据组装失败",e);
			return new QuotaReturnDTO("error", "系统异常", insId);
		}
		
		
		return quotaReturnDTO;
	}

}
