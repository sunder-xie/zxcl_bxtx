package com.zxcl.webapp.biz.action.data.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.tianping_http.dto.ApplyInfoDTO;
import com.zxcl.tianping_http.dto.ClientUser;
import com.zxcl.tianping_http.dto.CvrgDTO;
import com.zxcl.tianping_http.dto.quoteresult.QuoteBackMessageDTO;
import com.zxcl.webapp.biz.action.data.HttpAxatpDataAction;
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
public class HttpAxatpDataActionImpl implements HttpAxatpDataAction{

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
		logger.info("HttpAxatpDataActionImpl   quota  天平报价数据组装    入参    userId："+userId+"  insuranceDTO："+insuranceDTO+"  inquiryId："+inquiryId+"  configMap："+configMap);
		
		//THESE CODES SHOULD BE REBUILD
		//小微
		MicroDTO microDTO = new MicroDTO();
		String microId = null;
		try {
			microDTO = microService.getMicroByUserId(userId);
			if(microDTO != null)
				microId = microDTO.getMicro_id();
		} catch (BusinessServiceException e) {
			logger.error("天平报价接口数据组装查询小微失败", e);
			throw new ActionException("系统异常");
		}
		
		//询价单
		InquiryDTO inquiryDTO = new InquiryDTO();
		try {
			inquiryDTO = inquiryService.get(inquiryId, microId);
		} catch (BusinessServiceException e) {
			logger.error("天平报价接口数据组装查询询价单失败", e);
			throw new ActionException("系统异常");
		}
		
		//查询险种集合	
		List<CoverageItemDTO> coverItems = new ArrayList<CoverageItemDTO>();
		try {
			coverItems = coverService.getCoverageItems(inquiryId,
					microId);
		} catch (BusinessServiceException e) {
			logger.error("天平报价接口数据组装查询险种集合失败", e);
			throw new ActionException("系统异常");
		}
		
		// 处理车牌号
		inquiryDTO.setPlateNo(StringUtils.isBlank(inquiryDTO.getPlateNo())
				|| inquiryDTO.getPlateNo().length() != 7 ? null : inquiryDTO.getPlateNo());
		
		String insId = insuranceDTO.getInsId();
		
		ApplyInfoDTO applyInfoDTO = new ApplyInfoDTO();
		ClientUser clientUser = new ClientUser();
		clientUser.setXiaoweiUserId(userId);
		clientUser.setAgentUserId(CommonUtil.valueOf(configMap.get(insId+"_AgentUserId")));
		clientUser.setAgentUserPwd(CommonUtil.valueOf(configMap.get(insId+"_AgentUserPwd")));
		applyInfoDTO.setClientUser(clientUser);
		
		//904=联合  109=商业 105=交强
		String productCode = "";
		if("1".equals(inquiryDTO.getTciSign()) && "1".equals(inquiryDTO.getVciSign())){
			productCode = "904";
		}else if("1".equals(inquiryDTO.getTciSign())){
			productCode = "105";
		}else{
			productCode = "109";
		}
		applyInfoDTO.setProductCode(productCode); 
		
		List<CvrgDTO> cvrgList = new ArrayList<CvrgDTO>();
		String insId2 = insId.substring(0, 4);
		int count = 1;
		for (CoverageItemDTO coverageItemDTO : coverItems) {
			CvrgDTO cvrg = new CvrgDTO();
			try {
				CorrespondDTO correspondDTO = corrService.get(new CorrespondDTO(insId2, BaseFinal.INSURANCETYPECODE, coverageItemDTO.getCode()));
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
					CorrespondDTO correspondDTO2 = corrService.get(new CorrespondDTO(insId2, BaseFinal.INSURANCETYPECODE, correspondDTO.getRemark()));
					cvrg1.setCvrgCode(correspondDTO2.getIns_code());
					cvrgList.add(cvrg1);
				}
			} catch (BusinessServiceException e) {
				logger.error("天平报价接口数据组装险种代码失败，险种代码为："+coverageItemDTO.getCode(), e);
				throw new ActionException("系统异常");
			}
			count = 1;
			cvrgList.add(cvrg);
		}
		if("1".equals(inquiryDTO.getTciSign())){			
			applyInfoDTO.setJqBeginDate(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getTciStartDate())+" 00:00:00");
			applyInfoDTO.setJqEndDate(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getTciEndDate())+" 23:59:59");
			CvrgDTO cvrg = new CvrgDTO();
			cvrg.setCvrgCode("TPF"); //交强险
			cvrgList.add(cvrg);
			cvrgList.add(cvrg);
		}
		applyInfoDTO.setCvrgList(cvrgList);
		
		if("1".equals(inquiryDTO.getVciSign())){			
			applyInfoDTO.setSyBeginDate(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getVciStartDate())+" 00:00:00");
			applyInfoDTO.setSyEndDate(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getVciEndDate())+" 23:59:59");
		}
		applyInfoDTO.setPlateNo(inquiryDTO.getPlateNo());
		
		applyInfoDTO.setEcdemicVehicleFlag("0"); //是否外地车 1/0
		applyInfoDTO.setEngineNo(inquiryDTO.getEngNo());
		applyInfoDTO.setFirstRegDate(inquiryDTO.getFstRegNoStr());
		applyInfoDTO.setFrameNo(inquiryDTO.getFrmNo());
		
		applyInfoDTO.setOwnerCertNo(inquiryDTO.getOwnerCertNo());
		applyInfoDTO.setOwnerName(inquiryDTO.getOwnerName());
		//过户日期
		if("1".equals(inquiryDTO.getTransferSign())){			
			applyInfoDTO.setTransferVehicleFlag("0");
			applyInfoDTO.setTransferDate(inquiryDTO.getTransferDateStr());
		}
		applyInfoDTO.setVhlModelName(inquiryDTO.getVehicleName()); //车型名称
		applyInfoDTO.setVhlModelPriceNoTax(inquiryDTO.getVehiclePrice());//车价(不含税)
		
		return applyInfoDTO;
	}

	@Override
	public QuotaReturnDTO quotaReturn(QuoteBackMessageDTO result, String insId)
			throws ActionException {
		logger.info("HttpAxatpDataActionImpl   quotaReturn  天平报价数据返回组装    入参   result："+result+"  insId："+insId);
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		//保险公司ID
		quotaReturnDTO.setInsId(insId);
		//返回的code
		quotaReturnDTO.setErrorCode(result.getQtnBase().getErrorCode());
		//返回的信息
		quotaReturnDTO.setErrorMessages(result.getQtnBase().getErrorMsg());
		//报价单号
		quotaReturnDTO.setQuotaId(Common.getUUID());
		
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
			quotaReturnDTO.setTaxBeginDate(DateUtil.dateToString("yyyy-MM-dd", result.getVehicleTaxation().getTaxEffBgnTm()));
			quotaReturnDTO.setTaxEndDate(DateUtil.dateToString("yyyy-MM-dd", result.getVehicleTaxation().getTaxEffEndTm()));
		}
		
		//险种信息
		List<CoverageItemDTO> coverageItems = new ArrayList<CoverageItemDTO>();
		String insId2 = insId.substring(0, 4);
		for(com.zxcl.tianping_http.dto.quoteresult.CoverageItemDTO coverageItemDTO : result.getCoverageItems()){
			try {
				CoverageItemDTO coverageItem = new CoverageItemDTO();
				CorrespondDTO correspondDTO = corrService.getTwo(new CorrespondDTO(insId2, BaseFinal.INSURANCETYPECODE, "",coverageItemDTO.getCoverageCode()));
				if(null != correspondDTO && !"TPF".equals(coverageItemDTO.getCoverageCode())){	
					if(!"AODIRF".equals(coverageItemDTO.getCoverageCode()) && !"ATPIRF".equals(coverageItemDTO.getCoverageCode())
							&& !"ADPLIRF".equals(coverageItemDTO.getCoverageCode()) && !"ADLIRF".equals(coverageItemDTO.getCoverageCode())
							&& !"ATHEFTIRF".equals(coverageItemDTO.getCoverageCode()) && !"NNICKIRF".equals(coverageItemDTO.getCoverageCode())
							&& !"NLOSIIRF".equals(coverageItemDTO.getCoverageCode()) && !"EFLOODIRF".equals(coverageItemDTO.getCoverageCode())){						
						//险种
						coverageItem.setCode(correspondDTO.getCode());
						//保费
						coverageItem.setAmount(coverageItemDTO.getExpectPrm());
						//保额
						coverageItem.setSumLimit(coverageItemDTO.getSumLimit());
						coverageItems.add(coverageItem);
					}
				}
			} catch (BusinessServiceException e) {
				logger.error("天平报价返回数据组装查询险种失败",e);
				return new QuotaReturnDTO("error","系统异常",insId);
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
