package com.zxcl.webapp.biz.action.data.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zxcl.pingan_http.api.dto.ApplyInfoDTO;
import com.zxcl.pingan_http.api.dto.ClientUser;
import com.zxcl.pingan_http.api.dto.CvrgDTO;
import com.zxcl.pingan_http.api.dto.quoteresult.QuoteBackMessageDTO;
import com.zxcl.webapp.biz.action.data.HttpPingAnDataAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.CorrespondService;
import com.zxcl.webapp.biz.service.CoverageItemService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsXmlService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.util.BaseFinal;
import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.CorrespondDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.util.Common;

@Service
public class HttpPingAnDataActionImpl implements HttpPingAnDataAction{

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private MicroService microService;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private InsXmlService insXmlService;
	
	@Autowired
	private CoverageItemService coverService;

	@Autowired
	private CorrespondService corrService;

	@Override
	public ApplyInfoDTO quota(String userId, InsuranceDTO insuranceDTO,
			String inquiryId, Map<String, Object> configMap)
			throws ActionException {
		logger.info("HttpPingAnDataActionImpl   quota  平安报价数据组装    入参    userId："+userId+"  insuranceDTO："+insuranceDTO+"  inquiryId："+inquiryId+"  configMap："+configMap);
		//小微
		MicroDTO microDTO = new MicroDTO();
		String microId = null;
		try {
			microDTO = microService.getMicroByUserId(userId);
			if(microDTO != null)
				microId = microDTO.getMicro_id();
		} catch (BusinessServiceException e) {
			logger.error("平安报价接口数据组装查询小微失败", e);
			throw new ActionException("系统异常");
		}
		
		//询价单
		InquiryDTO inquiryDTO = new InquiryDTO();
		try {
			inquiryDTO = inquiryService.get(inquiryId, microId);
		} catch (BusinessServiceException e) {
			logger.error("平安报价接口数据组装查询询价单失败", e);
			throw new ActionException("系统异常");
		}
		
		//查询险种集合	
		List<CoverageItemDTO> coverItems = new ArrayList<CoverageItemDTO>();
		try {
			coverItems = coverService.getCoverageItems(inquiryId,
					microId);
		} catch (BusinessServiceException e) {
			logger.error("太平报价接口数据组装查询险种集合失败", e);
			throw new ActionException("系统异常");
		}
		
		// 处理车牌号
		inquiryDTO.setPlateNo(StringUtils.isBlank(inquiryDTO.getPlateNo())
				|| inquiryDTO.getPlateNo().length() != 7 ? null : inquiryDTO.getPlateNo());
		ApplyInfoDTO apply = new ApplyInfoDTO();
		
		ClientUser clientUser = new ClientUser();
		clientUser.setAgentUserId(CommonUtil.valueOf(configMap.get("PINGANHTTP_USERNAME")));
		clientUser.setAgentUserPwd(CommonUtil.valueOf(configMap.get("PINGANHTTP_PASSOWRD")));
		
		HashMap<String, String> userConfigMap = new HashMap<String, String>();
		userConfigMap.put("ALL_CONFIG", JSONObject.toJSONString(configMap, SerializerFeature.WriteMapNullValue));
		userConfigMap.put("PINGANHTTP_PARTNERWORKNETCODE", CommonUtil.valueOf(configMap.get("PINGANHTTP_PARTNERWORKNETCODE")));//合作网点代码必传
		userConfigMap.put("PINGANHTTP_TAX3_NO", CommonUtil.valueOf(configMap.get("PINGANHTTP_TAX3_NO")));
		userConfigMap.put("PINGANHTTP_TAX3_ORG", CommonUtil.valueOf(configMap.get("PINGANHTTP_TAX3_ORG")));
		userConfigMap.put("PINGANHTTP_TAX4_NO", CommonUtil.valueOf(configMap.get("PINGANHTTP_TAX4_NO")));
		userConfigMap.put("PINGANHTTP_TAX4_ORG", CommonUtil.valueOf(configMap.get("PINGANHTTP_TAX4_ORG")));
		userConfigMap.put("PINGANHTTP_TAX5_NO", CommonUtil.valueOf(configMap.get("PINGANHTTP_TAX5_NO")));
		userConfigMap.put("PINGANHTTP_TAX5_ORG", CommonUtil.valueOf(configMap.get("PINGANHTTP_TAX5_ORG")));
		
		clientUser.setMap(userConfigMap);
		
		clientUser.setXiaoweiUserId(userId);
		apply.setClientUser(clientUser);
		
		String productCode = "";
		if("1".equals(inquiryDTO.getTciSign())){
			productCode += "C51";
		}
		if("1".equals(inquiryDTO.getVciSign())){
			productCode += "C01";
		}
		apply.setProductCode(productCode); //C51C01 联合单; C51交强; C01商业;
		
		List<CvrgDTO> cvrgList = new ArrayList<CvrgDTO>();
		apply.setCvrgList(cvrgList);
		int count = 1;
		for (CoverageItemDTO coverageItemDTO : coverItems) {
			CvrgDTO cvrg = new CvrgDTO();
			try {
				CorrespondDTO correspondDTO = corrService.get(new CorrespondDTO(insuranceDTO.getInsId(), BaseFinal.INSURANCETYPECODE, coverageItemDTO.getCode()));
				cvrg.setCvrgCode(correspondDTO.getIns_code());
				if("030009".equals(coverageItemDTO.getCode()) || "030001".equals(coverageItemDTO.getCode())){
					if("030009".equals(coverageItemDTO.getCode())){
						count = inquiryDTO.getSeatNum()-1;						
					}
					cvrg.setPerAmount(coverageItemDTO.getSumLimit().intValue()/count+"");
				}else if(null != coverageItemDTO.getSumLimit()){
					cvrg.setAmount(coverageItemDTO.getSumLimit().toString());
				}
				if(coverageItemDTO.getNoDduct() == 1){//投了不计免赔
					CvrgDTO cvrg1 = new CvrgDTO();
					CorrespondDTO correspondDTO2 = corrService.get(new CorrespondDTO(insuranceDTO.getInsId(), BaseFinal.INSURANCETYPECODE, correspondDTO.getRemark()));
					cvrg1.setCvrgCode(correspondDTO2.getIns_code());
					cvrgList.add(cvrg1);
				}
			} catch (BusinessServiceException e) {
				logger.error("平安报价接口数据组装险种代码失败，险种代码为："+coverageItemDTO.getCode(), e);
				throw new ActionException("系统异常");
			}
			count = 1;
			cvrgList.add(cvrg);
		}
		//过户日期
		if("1".equals(inquiryDTO.getTransferSign())){			
			apply.setChangeOwnerFlag("1");//是否过户1/0
			apply.setTransferDate(inquiryDTO.getTransferDateStr());//过户日期
		}

		apply.setPlateNo(inquiryDTO.getPlateNo());
		apply.setEngineNo(inquiryDTO.getEngNo());
		apply.setFirstRegDate(inquiryDTO.getFstRegNoStr());
		apply.setFrameNo(inquiryDTO.getFrmNo());
		apply.setVhlModelCode(inquiryDTO.getModelCode());
		apply.setVhlModelMarkDate(inquiryDTO.getMarketDate());
		
		if("1".equals(inquiryDTO.getTciSign())){			
			apply.setJqBeginDate(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getTciStartDate())+" 00:00:00");
			apply.setJqEndDate(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getTciEndDate())+" 23:59:59");
		}
		if("1".equals(inquiryDTO.getVciSign())){			
			apply.setSyBeginDate(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getVciStartDate())+" 00:00:00");
			apply.setSyEndDate(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getVciEndDate())+" 23:59:59");
		}
		
		apply.setOwnerCertNo(inquiryDTO.getOwnerCertNo());
		apply.setOwnerName(inquiryDTO.getOwnerName());
		apply.setVhlModelName(inquiryDTO.getVehicleName()); //车型名称
		apply.setVhlModelPriceNoTax(inquiryDTO.getVehiclePrice());//车价(不含税)
		return apply;
	}

	@Override
	public QuotaReturnDTO quotaReturn(QuoteBackMessageDTO result, String insId)
			throws ActionException {
		logger.info("HttpPingAnDataActionImpl   quotaReturn  平安报价数据返回组装    入参   result："+result+"  insId："+insId);
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		//保险公司ID
		quotaReturnDTO.setInsId(insId);
		//返回的code
		quotaReturnDTO.setErrorCode(result.getQtnBase().getErrorCode());
		//返回的信息
		quotaReturnDTO.setErrorMessages(result.getQtnBase().getErrorMsg());
		//报价单号
		quotaReturnDTO.setQuotaId(StringUtils.isNotBlank(result.getQuotaId())?result.getQuotaId():Common.getUUID());
		logger.info("quotaReturnDTO.QuotaId="+quotaReturnDTO.getQuotaId());
		
		if(null != result.getApplicationTCI()){			
			//交强险费用
			quotaReturnDTO.setPremTCITax(result.getApplicationTCI().getExpectPrm());
		}
		if(null != result.getApplicationVCI()){			
			//商业险费用
			quotaReturnDTO.setPremVCITax(result.getApplicationVCI().getExpectPrm());
			//商业险折扣
			quotaReturnDTO.setDiscount(result.getApplicationVCI().getDiscount());
		}
		if(null != result.getVehicleTaxation()){			
			//车船税费用
			quotaReturnDTO.setVehicleTax(result.getVehicleTaxation().getSumTax());
			//车船税计算是否成功
			quotaReturnDTO.setCalcSuccess(result.getVehicleTaxation().isCalcSuccess());
			quotaReturnDTO.setTaxCurrentYear(result.getVehicleTaxation().getAnnualTaxDue());
			quotaReturnDTO.setTaxLastYear(result.getVehicleTaxation().getSumTaxDefault());
			quotaReturnDTO.setTaxOverdue(result.getVehicleTaxation().getSumOverdue());
			if(null != result.getVehicleTaxation().getTaxEffBgnTm()){				
				quotaReturnDTO.setTaxBeginDate(DateUtil.dateToString("yyyy-MM-dd", result.getVehicleTaxation().getTaxEffBgnTm()));
			}
			if(null != result.getVehicleTaxation().getTaxEffEndTm()){				
				quotaReturnDTO.setTaxEndDate(DateUtil.dateToString("yyyy-MM-dd", result.getVehicleTaxation().getTaxEffEndTm()));
			}
		}
		
		//险种信息
		List<CoverageItemDTO> coverageItems = new ArrayList<CoverageItemDTO>();
		if(null != result.getCoverageItems() && result.getCoverageItems().size() > 0 ){
			for(com.zxcl.pingan_http.api.dto.quoteresult.CoverageItemDTO coverageItemDTO : result.getCoverageItems()){
				try {
					CoverageItemDTO coverageItem = new CoverageItemDTO();
					CorrespondDTO correspondDTO = corrService.getTwo(new CorrespondDTO(insId, BaseFinal.INSURANCETYPECODE, "",coverageItemDTO.getCoverageCode()));
					if(!"27".equals(coverageItemDTO.getCoverageCode()) && !"28".equals(coverageItemDTO.getCoverageCode())
							&& !"48".equals(coverageItemDTO.getCoverageCode()) && !"49".equals(coverageItemDTO.getCoverageCode())
							&& !"80".equals(coverageItemDTO.getCoverageCode()) && !"75".equals(coverageItemDTO.getCoverageCode())
							&& !"77".equals(coverageItemDTO.getCoverageCode()) && !"79".equals(coverageItemDTO.getCoverageCode())){						
						//险种
						coverageItem.setCode(correspondDTO.getCode());
						//保费
						coverageItem.setAmount(coverageItemDTO.getExpectPrm());
						//保额
						coverageItem.setSumLimit(coverageItemDTO.getSumLimit());
						coverageItems.add(coverageItem);
					}
				} catch (BusinessServiceException e) {
					logger.error("平安报价返回数据组装查询险种失败",e);
					return new QuotaReturnDTO("error","系统异常",insId);
				}
			}
		}
		//不计免赔
		CoverageItemDTO coverageItem = new CoverageItemDTO();
		coverageItem.setName("不计免赔");
		coverageItem.setCode("00000");
		BigDecimal buji = BigDecimal.ZERO;
		if(null != result.getMainCvrgDductPrmTotal()){//基本险不计免赔保费总额
			buji = buji.add(result.getMainCvrgDductPrmTotal());
		}
		if(null != result.getSubCvrgDductPrmTotal()){//附加险不计免赔保费总额
			buji = buji.add(result.getSubCvrgDductPrmTotal());
		}
		coverageItem.setAmount(buji);
		if(buji.intValue() > 0){			
			coverageItems.add(coverageItem);
		}
		
		quotaReturnDTO.setCoverageItems(coverageItems);
		return quotaReturnDTO;
	}

}
