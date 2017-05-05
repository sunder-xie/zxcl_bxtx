package com.zxcl.webapp.biz.action.call.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bxtx.AI.intf.biz.action.AICSCalcPremiumAction;
import com.bxtx.AI.intf.dto.bxtx.quotareq.CompanyInfo;
import com.bxtx.AI.intf.dto.bxtx.quotareq.InsureInfoDTO;
import com.bxtx.AI.intf.dto.bxtx.quotareq.OwnerInfoDTO;
import com.bxtx.AI.intf.dto.bxtx.quotareq.QuotaReqDTO;
import com.bxtx.AI.intf.dto.bxtx.quotareq.VehicleDTO;
import com.bxtx.AI.intf.dto.bxtx.quotaresp.QuoteBackMessageDTO;
import com.bxtx.AI.intf.util.DateUtils;
import com.zxcl.webapp.biz.action.call.HttpAlltrustCallAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.exception.ThirdInsurNotFoundException;
import com.zxcl.webapp.biz.service.CorrespondService;
import com.zxcl.webapp.biz.service.CoverageItemService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsuranceCompanyConfigService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.util.BaseFinal;
import com.zxcl.webapp.dto.CorrespondDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

@Service
public class HttpAlltrustCallActionImpl implements HttpAlltrustCallAction {
	private Logger logger=Logger.getLogger(getClass());
	
	@Resource(name="aiQuotaRemoteService")
	private AICSCalcPremiumAction calcPremiumService;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private  CorrespondService corrService;
	
	@Autowired
	private CoverageItemService coverService;
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private InsuranceCompanyConfigService configService;
	
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId)
			throws ActionException{
		try {
			QuotaReqDTO quotaReq = this.getQuotaReq(userId, inquiryId, insId);
			logger.debug("永城保险报价请求信息:"+JSONObject.toJSONString(quotaReq));
			//调用接口
			QuoteBackMessageDTO quoteBackMessageDTO = calcPremiumService.calcPremium(quotaReq);
			logger.debug("永城保险报价返回信息:"+JSONObject.toJSONString(quoteBackMessageDTO));
			if(null==quoteBackMessageDTO){
				logger.info("永城报价接口返回信息为null");
				return new QuotaReturnDTO("error", "系统异常", "AICSHTTP");
			}
			//处理返回的报价信息
			QuotaReturnDTO quotaReturnDTO=new QuotaReturnDTO();
			quotaReturnDTO.setErrorMessages(quoteBackMessageDTO.getQtnBase().getErrorMessage());
			if("200".equals(quoteBackMessageDTO.getQtnBase().getErrorCode()))
			{
				List<CoverageItemDTO> coverageItems = new ArrayList<CoverageItemDTO>();
				for (com.bxtx.AI.intf.dto.bxtx.quotaresp.CoverageItemDTO coverData:quoteBackMessageDTO.getCoverageItems()) 
				{
					CoverageItemDTO coverItemDTO=new CoverageItemDTO();
					String code=corrService.get(new CorrespondDTO("AICSHTTP",BaseFinal.INSURANCETYPECODE, null, coverData.getCoverageCode())).getCode();
					coverItemDTO.setCode(code);
					coverItemDTO.setSumLimit(null!=coverData.getSumLimit()?coverData.getSumLimit():new BigDecimal(0));
					coverItemDTO.setAmount(coverData.getExpectPrm());
					coverageItems.add(coverItemDTO);
				}
				quotaReturnDTO.setCoverageItems(coverageItems);
				quotaReturnDTO.setQuotaId(quoteBackMessageDTO.getQtnBase().getQtnID());
				quotaReturnDTO.setPremTCITax(quoteBackMessageDTO.getApplicationTCI().getExpectPrm());
				quotaReturnDTO.setPremVCITax(quoteBackMessageDTO.getApplicationVCI().getExpectPrm());
				quotaReturnDTO.setVehicleTax(quoteBackMessageDTO.getVehicleTaxation().getSumTax());
				quotaReturnDTO.setDiscount(quoteBackMessageDTO.getApplicationVCI().getDiscount());
				//重复投保信息
				String reInsureInfo="";
				if (quoteBackMessageDTO.getReInsureItem().isVciReInsure()) 
				{
					reInsureInfo += "商业险重复投保，最新保单止期：" + DateUtils.getDateToStr( quoteBackMessageDTO.getReInsureItem().getExpireDate(), "yyyy-MM-dd") + "。";
					quotaReturnDTO.setErrorCode("error");
				}
				if (quoteBackMessageDTO.getReInsureItem().isTciReInsure()) 
				{
					reInsureInfo += "交强险重复投保，最新保单止期：" + DateUtils.getDateToStr(quoteBackMessageDTO.getReInsureItem().getTciEndDate(), "yyyy-MM-dd")  + "。";
					quotaReturnDTO.setErrorCode("error");
				}
				quotaReturnDTO.setReInsureInfo(reInsureInfo);
				quotaReturnDTO.setErrorMessages(reInsureInfo);
				quotaReturnDTO.setCalcSuccess(quoteBackMessageDTO.getVehicleTaxation().isCalcSuccess());
			}else if(null!=quoteBackMessageDTO.getQtnBase().getErrorCode() 
                    && "PSDERROR".equals(quoteBackMessageDTO.getQtnBase().getErrorCode()))
			{
               quotaReturnDTO.setErrorCode(quoteBackMessageDTO.getQtnBase().getErrorCode());
               quotaReturnDTO.setUserName(quoteBackMessageDTO.getQtnBase().getUserName());
			}
			else
			{
				quotaReturnDTO.setErrorCode("error");
			}
			quotaReturnDTO.setInsId(insId);
			
			//（状态返回==200 && （商业险已投保||交强险已投保）） 就返回已投保的险种提示信息.
			//状态不为200，直接返回错误code和错误信息.
//			if(StringUtils.equals("200", quoteBackMessageDTO.getQtnBase().getErrorCode()) && 
//					(quoteBackMessageDTO.getReInsureItem().isVciReInsure() || quoteBackMessageDTO.getReInsureItem().isTciReInsure() )
//				){
//				return new QuotaReturnDTO("error", quotaReturnDTO.getReInsureInfo(), "AICSHTTP");
//			}else if(!StringUtils.equals("200", quoteBackMessageDTO.getQtnBase().getErrorCode()) ){
//				return new QuotaReturnDTO(quoteBackMessageDTO.getQtnBase().getErrorCode(), quotaReturnDTO.getErrorMessages(), "AICSHTTP");
//			}
			return quotaReturnDTO;
		}catch(ThirdInsurNotFoundException e){
			return new QuotaReturnDTO("error", "保险公司暂不能承保“无法找到第三方特约险”", "AICSHTTP");
		} catch (Exception e) {
			logger.error("HHTP永城保险报价失败:",e);
			return new QuotaReturnDTO("error", "系统异常", "AICSHTTP");
		}
	}

	private QuotaReqDTO getQuotaReq(String userId, String inquiryId, String insId) throws Exception{
		QuotaReqDTO quotaReqDTO=new QuotaReqDTO();
		CompanyInfo companyInfo=new CompanyInfo();
		List<com.bxtx.AI.intf.dto.bxtx.quotareq.CoverageItemDTO> coverageItemReqs=new 
				ArrayList<com.bxtx.AI.intf.dto.bxtx.quotareq.CoverageItemDTO>();
		InsureInfoDTO insureInfo=new InsureInfoDTO();
		OwnerInfoDTO ownerInfo=new OwnerInfoDTO();
		VehicleDTO vehicle=new VehicleDTO();
		
		String microId =null;
		InquiryDTO inquiryDTO =null;
		List<CoverageItemDTO> coverageItems=null;
		try {
			microId = microService.getMicroByUserId(userId).getMicro_id();
		} catch (BusinessServiceException e) {
			logger.error("查询小薇信息失败",e);
			throw new ActionException("查询小薇信息失败",e);
		}
		try {
			inquiryDTO = inquiryService.get(inquiryId, microId);
		} catch (BusinessServiceException e) {
			logger.error("查询询价信息失败",e);
			throw new ActionException("查询询价信息失败",e);
		}
		try {
			coverageItems=coverService.getCoverageItems(inquiryId, microId);
		} catch (BusinessServiceException e) {
			logger.error("查询险别信息失败",e);
			throw new ActionException("查询险别信息失败",e);
		}
		try {
			String glsType=null;
			//险别信息
			for (CoverageItemDTO coverageItemDTO : coverageItems) {
				if(StringUtils.equals("033008", coverageItemDTO.getCode())){
					throw new ThirdInsurNotFoundException("保险公司暂不能承保“无法找到第三方特约险”");
				}
				com.bxtx.AI.intf.dto.bxtx.quotareq.CoverageItemDTO coverItemReqDTO=new com.bxtx.AI.intf.dto.bxtx.quotareq.CoverageItemDTO();
				CorrespondDTO correspondDTO = corrService.get(new CorrespondDTO("AICSHTTP",BaseFinal.INSURANCETYPECODE,
						coverageItemDTO.getCode()));
				coverItemReqDTO.setCode(correspondDTO.getIns_code());
				//第三者责任险
				if("030018".equals(correspondDTO.getIns_code())){
					String otherInsCode = corrService.get(new CorrespondDTO("AICSHTTP","三者险保额",
							String.valueOf(coverageItemDTO.getSumLimit().intValue()))).getIns_code();
					coverItemReqDTO.setRemark(otherInsCode);
				}
				if("030110".equals(correspondDTO.getIns_code())){
					glsType=coverageItemDTO.getGlsType();
				}
				coverItemReqDTO.setName(correspondDTO.getIns_name());
				coverItemReqDTO.setSumLimit(coverageItemDTO.getSumLimit());
				coverItemReqDTO.setNoDduct(coverageItemDTO.getNoDduct());
				coverItemReqDTO.setGlsType(coverageItemDTO.getGlsType());
				coverageItemReqs.add(coverItemReqDTO);
			}
			quotaReqDTO.setCoverageItems(coverageItemReqs);
			//车辆信息
			vehicle.setEngNo(inquiryDTO.getEngNo());
			vehicle.setFrmNo(inquiryDTO.getFrmNo());
			vehicle.setPlateNo(inquiryDTO.getPlateNo());
			vehicle.setRegisterDate(inquiryDTO.getFstRegNo());
			vehicle.setVehicleId(inquiryDTO.getModelCode());
			vehicle.setTransferSign(inquiryDTO.getTransferSign());
			vehicle.setTransferDate(inquiryDTO.getTransferDate());
			vehicle.setGlsType(glsType);
			quotaReqDTO.setVehicle(vehicle);
			//投保信息
			insureInfo.setTciStartDate(inquiryDTO.getTciStartDate());
			insureInfo.setTciEndDate(inquiryDTO.getTciEndDate());
			insureInfo.setVciStartDate(inquiryDTO.getVciStartDate());
			insureInfo.setVciEndDate(inquiryDTO.getVciEndDate());
			insureInfo.setTciSign(inquiryDTO.getTciSign());
			insureInfo.setVciSign(inquiryDTO.getVciSign());
			quotaReqDTO.setInsureInfo(insureInfo);
			//车主信息
			ownerInfo.setCertNo(inquiryDTO.getOwnerCertNo());
			ownerInfo.setBirth(inquiryDTO.getBirth());
			ownerInfo.setName(inquiryDTO.getOwnerName());
			ownerInfo.setSex(inquiryDTO.getOwnerSex());
			ownerInfo.setAge(inquiryDTO.getOwnerAge());
			quotaReqDTO.setOwner(ownerInfo);
			//保险公司配置信息
			try {	
				String configId = microService.getConfigIdByInsIdAndMicroId(insId, microId);
				Map<String, Object> mapConfig = configService.getMapByInsId(configId);
				companyInfo.setUsername(mapConfig.get("AICSHTTP_USERNAME").toString());
				companyInfo.setPassword(mapConfig.get("AICSHTTP_PASSWORD").toString());
				companyInfo.setChannelCode(mapConfig.get("AICSHTTP_CHANELCODE").toString());
				companyInfo.setSalesmanCode(mapConfig.get("AICSHTTP_SALESMANCODE").toString());
			} catch (Exception e) {
				logger.error("设置永城HTTP保险公司配置信息失败",e);
				throw new ActionException("设置永城HTTP保险公司配置信息失败",e);
			}
			quotaReqDTO.setCompanyInfo(companyInfo);
			return quotaReqDTO;
		}catch (ThirdInsurNotFoundException e){
			logger.error("永城保险http 暂不支持<无法找到第三方险>");
			throw new ThirdInsurNotFoundException("保险公司暂不能承保“无法找到第三方特约险”");
		} catch (Exception e) {
			logger.error("组织信息失败",e);
			throw new ActionException("组织信息失败",e);
		}
	}
}
