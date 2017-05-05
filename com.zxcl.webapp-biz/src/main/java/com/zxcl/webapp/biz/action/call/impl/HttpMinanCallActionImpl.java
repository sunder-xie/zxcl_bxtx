package com.zxcl.webapp.biz.action.call.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.minan_intf.api.MinanCrawlingQuoteService;
import com.zxcl.minan_intf.dto.ApplyInfoDTO;
import com.zxcl.minan_intf.dto.quoteresult.QuoteBackMessageDTO;
import com.zxcl.webapp.biz.action.call.HttpMinanCallAction;
import com.zxcl.webapp.biz.action.data.HttpMinanDataAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.InsXmlDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

@Service
public class HttpMinanCallActionImpl implements HttpMinanCallAction{
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private HttpMinanDataAction httpMinanDataAction;
	
	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private MinanCrawlingQuoteService minanCrawlingQuoteService;

//	/**
//	 * 组装报文DTO
//	 * @param insId 保险公司ID
//	 * @param userId 用户ID
//	 * @param inquiryId 询价单号
//	 * @return
//	 */
//	private InsXmlDTO getInsXmlDTO(String insId,String userId,String inquiryId){
//		logger.info("HttpCicCallActionImpl   getInsXmlDTO");
//		logger.info("中华组装报文DTO");
//		logger.info("入参   保险公司ID："+insId+"   用户ID："+userId+"  询价单号："+inquiryId);
//		InsXmlDTO insXMl =new InsXmlDTO();
//		insXMl.setInsId(insId);
//		insXMl.setCrtCode(userId);
//		insXMl.setUpdCode(userId);
//		insXMl.setInquiryId(inquiryId);
//		logger.info("出参  InsXmlDTO："+insXMl);
//		return insXMl;
//	}	
	
	@Override
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId,
			Map<String, Object> configMap) throws ActionException {
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		//保险公司信息
		InsuranceDTO insuranceDTO = new InsuranceDTO();
		try {
			insuranceDTO = insuranceService.get(insId);
		} catch (BusinessServiceException e) {
			logger.error("太平报价接口查询保险公司失败", e);
			return new QuotaReturnDTO("error","系统异常",insId);
		}
		ApplyInfoDTO applyInfoDTO = httpMinanDataAction.quota(userId, insuranceDTO, inquiryId, configMap);
		
		QuoteBackMessageDTO result = new QuoteBackMessageDTO();
		result = minanCrawlingQuoteService.quote(applyInfoDTO);
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
			logger.info("民安报价返回成功");
			quotaReturnDTO = httpMinanDataAction.quotaReturn(result, insId);
		}else if(null != result && null != result.getQtnBase() && "FAIL".equals(result.getQtnBase().getErrorCode())){
			logger.info("民安报价返回失败");
			logger.info("民安报价失败原因："+result.getQtnBase().getErrorMsg());
			return new QuotaReturnDTO("error",result.getQtnBase().getErrorMsg(),insId);
		}else if(null != result && null != result.getQtnBase() && "PSDERROR".equals(result.getQtnBase().getErrorCode())){
			logger.info("民安报价账号密码错误");
			return new QuotaReturnDTO("PSDERROR",result.getQtnBase().getErrorMsg(),insId,applyInfoDTO.getClientUser().getAgentUserId());
		}else{
			logger.info("民安报价系统异常");
			return new QuotaReturnDTO("error","系统异常",insId);
		}
		return quotaReturnDTO;
	}

}
