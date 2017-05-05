package com.zxcl.webapp.biz.action.data.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.bxtx.call.InsuranceAPI;
import com.zxcl.bxtx.dto.intf.VehicleReturnDTO;
import com.zxcl.minan_intf.dto.ApplyInfoDTO;
import com.zxcl.minan_intf.dto.ClientUser;
import com.zxcl.minan_intf.dto.GuItemKind0802;
import com.zxcl.minan_intf.dto.quoteresult.QuoteBackMessageDTO;
import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.action.data.HttpMinanDataAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.CorrespondService;
import com.zxcl.webapp.biz.service.CoverageItemService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsXmlService;
import com.zxcl.webapp.biz.service.InsuranceService;
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
import com.zxcl.webapp.util.SpringContextUtil;

@Service
public class HttpMinanDataActionImpl implements HttpMinanDataAction{
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MicroService microService;

	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private InsXmlService insXmlService;
	
//	@Autowired
//	private CntaipingAction cntaipingAction;
	
	@Autowired
	private CoverageItemService coverService;
	
	@Autowired
	private CorrespondService corrService;
	@Autowired
	private CallAction callAction;
	
	@Override
	public ApplyInfoDTO quota(String userId, InsuranceDTO insuranceDTO,
			String inquiryId, Map<String, Object> configMap)
			throws ActionException {
		logger.info("HttpMinanDataActionImpl  quota  民安报价数据组装   入参  userId："+userId+"   insuranceDTO："+insuranceDTO+"  inquiryId："+inquiryId+"   configMap："+configMap);
		ApplyInfoDTO apply = new ApplyInfoDTO();
		//小微
		MicroDTO microDTO = new MicroDTO();
		try {
			microDTO = microService.getMicroByUserId(userId);
		} catch (BusinessServiceException e) {
			logger.error("中华报价接口数据组装查询小微失败", e);
			throw new ActionException("系统异常");
		}
		
		//询价单
		InquiryDTO inquiryDTO = new InquiryDTO();
		try {
			inquiryDTO = inquiryService.get(inquiryId, microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("中华报价接口数据组装查询询价单失败", e);
			throw new ActionException("系统异常");
		}
		// 处理车牌号
		inquiryDTO.setPlateNo(StringUtils.isBlank(inquiryDTO.getPlateNo())
				|| inquiryDTO.getPlateNo().length() != 7 ? null : inquiryDTO.getPlateNo());
		
//		//查询车型返回报文
//		String[] xml = null;
//		try {
//			xml = insXmlService.getBackXml(inquiryDTO.getInquiryId(), "TPIC","1");
//		} catch (BusinessServiceException e) {
//			logger.error("太平投保接口数据组装查询车型报文失败", e);
//			throw new ActionException("查询车型报文失败");
//		}
//		//解析报文
//		Map<String, Object> map = new HashMap<String,Object>();
//		if(null != xml && xml.length > 0){//如果不为空，则解析		
		String modelCode = "";	
			try {
				String marketTimestamp = "";
				String remark = inquiryDTO.getRemark();
				if(StringUtils.isNotBlank(remark)){
					int count = remark.indexOf("款 ");
					if(count > 0){
						marketTimestamp = remark.substring(count-4,count);
					}
				}
				InsuranceAPI insuranceAPI = (InsuranceAPI) SpringContextUtil.getBean("TPICInsuranceAPI");
				List<VehicleReturnDTO> vehicles = insuranceAPI.vhlQuery(inquiryDTO.getVehicleName()).getVehicleReturnList();
				for (VehicleReturnDTO vehicleModelDTO : vehicles) {
					if(0 == new BigDecimal(vehicleModelDTO.getVehiclePrice()).compareTo(new BigDecimal(inquiryDTO.getVehiclePrice()))){
						if(StringUtils.isNotBlank(vehicleModelDTO.getMarketDate()) && StringUtils.isNotBlank(marketTimestamp)){
							if(vehicleModelDTO.getMarketDate().equals(marketTimestamp)){
								modelCode = vehicleModelDTO.getModelCode();
							}
						}else{
							modelCode = vehicleModelDTO.getModelCode();
						}
					}
				}
//				map = cntaipingAction.analysisVehicle(xml[0],inquiryDTO.getVehiclePrice(),marketTimestamp);
			} catch (Exception e) {
				logger.error("太平投保解析车型报文失败", e);
				throw new ActionException("太平投保解析车型报文失败");
			}
//		}
		//查询险种集合	
		List<CoverageItemDTO> coverItems = new ArrayList<CoverageItemDTO>();
		try {
			coverItems = coverService.getCoverageItems(inquiryId,
					microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("太平报价接口数据组装查询险种集合失败", e);
		}
		
		String productCode = "";
		if("1".equals(inquiryDTO.getTciSign()) && "1".equals(inquiryDTO.getVciSign())){//交强商业险都不为空
			productCode = "1366";
		}else if("1".equals(inquiryDTO.getTciSign())){//单交强
			productCode = "0806";
		}else if("1".equals(inquiryDTO.getVciSign())){//单商业
			productCode = "0812";
		}
		apply.setProductCode(productCode);//0806 交强;  0812 商业;  1366 交强+商业;
		
		//保险公司ID
		String insId = insuranceDTO.getInsId();
		
		//该部分数据,从登录界面上可以直接获取,需配置到账号上
		apply.setGuMainAgreementNo(CommonUtil.valueOf(configMap.get(insId+"_GuMainAgreementNo")));//中介协议号
		apply.setGuMainBusinessSource(CommonUtil.valueOf(configMap.get(insId+"_GuMainBusinessSource")));//业务来源,0020专业代理 0030兼业代理
		apply.setGuMainIntermediaryCode(CommonUtil.valueOf(configMap.get(insId+"_GuMainIntermediaryCode"))); //中介代码
		
		apply.setGuMainSolutionCode(CommonUtil.valueOf(configMap.get(insId+"_GuMainSolutionCode"))); //分配方案代码 相当于选择业务员
		apply.setGuMainHandlerCompany(CommonUtil.valueOf(configMap.get(insId+"_GuMainHandlerCompany"))); //业务归属机构
		apply.setGuMainHandlerCompanyName(CommonUtil.valueOf(configMap.get(insId+"_GuMainHandlerCompanyName")));
		apply.setGuMainCreditPeriod(CommonUtil.valueOf(configMap.get(insId+"_GuMainCreditPeriod")));//账期
		apply.setGuMainChannelDetailCode(CommonUtil.valueOf(configMap.get(insId+"_GuMainChannelDetailCode")));//渠道细类 重点经纪
		apply.setGuMainHandlerCode(CommonUtil.valueOf(configMap.get(insId+"_GuMainHandlerCode")));//业务员
		apply.setGuMainHandlerName(CommonUtil.valueOf(configMap.get(insId+"_GuMainHandlerName")));//业务员名字
		
		//交强险保险期限
		if("1".equals(inquiryDTO.getTciSign())){
			apply.setGuMainStartDate0801(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getTciStartDate())+" 00:00:00");//交强险起期
			apply.setGuMainEndDate0801(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getTciEndDate())+" 23:59:59"); //交强险止期
		}
		//商业险保险期限
		if("1".equals(inquiryDTO.getVciSign())){			
			apply.setGuMainStartDate(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getVciStartDate())+" 00:00:00");//商业险起期
			apply.setGuMainEndDate(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getVciEndDate())+" 23:59:59"); //商业险止期
		}
		
		//-------车主 车型信息--------
		apply.setGuItemMotorCarOwner(inquiryDTO.getOwnerName());
		apply.setGuItemMotorOwnerIdNo(inquiryDTO.getOwnerCertNo());
		apply.setGuItemMotorLicenseNo(inquiryDTO.getPlateNo());
		apply.setGuItemMotorFrameNo(inquiryDTO.getFrmNo());//车架号
		apply.setGuItemMotorEngineNo(inquiryDTO.getEngNo());//发动机号
		apply.setGuItemMotorEnrollDate(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getFstRegNo()));//初登日期
		apply.setGuItemMotorGlassTypeCode("1");//玻璃类型 1-国产,2-进口		
		apply.setModelCode(modelCode); //车型代码,非锦泰的那套 如果有代码,厂牌车型和价格可以不填
		apply.setModelName(""); //厂牌车型
		apply.setPurchasePriceNoTax(""); //新车购置价,不含税
		
		
		//-------险别信息---------
		List<GuItemKind0802> guItemKind0802List = new ArrayList<>();
		
		apply.setGuItemKind0802List(guItemKind0802List);
		if("1".equals(inquiryDTO.getVciSign())){			
			for (CoverageItemDTO coverageItemDTO : coverItems) {
				GuItemKind0802 item = new GuItemKind0802();
				try {
					CorrespondDTO correspondDTO = corrService.get(new CorrespondDTO(insId, BaseFinal.INSURANCETYPECODE, coverageItemDTO.getCode()));
					//险别
					item.setGuItemKind0802KindCode(correspondDTO.getIns_code());
					if(!"030004".equals(coverageItemDTO.getCode()) && !"032618".equals(coverageItemDTO.getCode()) && !"033008".equals(coverageItemDTO.getCode())){
						//不计免赔
						item.setGuItemKind0802RelatedInd(String.valueOf(coverageItemDTO.getNoDduct()));
					}
					//费率 国产:10%-30%
					if("ZC".equals(correspondDTO.getIns_code()) && StringUtils.isNotBlank(inquiryDTO.getFrmNo())){
						String str = inquiryDTO.getFrmNo().substring(0, 1);
						if("L".equals(str)){//国产					
							item.setGuItemKind0802Rate("10");
						}else{
							item.setGuItemKind0802Rate("15");
						}
					}
					if("F".equals(correspondDTO.getIns_code())){//玻璃单独破碎险
						apply.setGuItemMotorGlassTypeCode(coverageItemDTO.getGlsType());//玻璃类型 1-国产,2-进口	
					}
					//保额
					java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.0");
					if("D1".equals(correspondDTO.getIns_code())){//司机险
						item.setGuItemKind0802UnitInsured(coverageItemDTO.getSumLimit() == null ? "0" : myformat.format(coverageItemDTO.getSumLimit()));
					}else if("D2".equals(correspondDTO.getIns_code())){//乘客险
						item.setGuItemKind0802UnitInsured(coverageItemDTO.getSumLimit() == null ? "" : myformat.format(coverageItemDTO.getSumLimit().intValue()/(inquiryDTO.getSeatNum()-1)));
					}else if("B".equals(correspondDTO.getIns_code()) || "L".equals(correspondDTO.getIns_code())){//商三险和划痕险
						item.setGuItemKind0802SumInsured(coverageItemDTO.getSumLimit() == null ? "0" : myformat.format(coverageItemDTO.getSumLimit()));
					}
				} catch (BusinessServiceException e) {
					logger.error("民安报价接口数据组装险种代码失败，险种代码为："+coverageItemDTO.getCode(), e);
				}
				
				guItemKind0802List.add(item);
			}
		}
		
		ClientUser client = new ClientUser();
		client.setAgentUserId(CommonUtil.valueOf(configMap.get(insId+"_AgentUserId")));
		client.setAgentUserPwd(CommonUtil.valueOf(configMap.get(insId+"_AgentUserPwd")));
		client.setXiaoweiUserId(userId);
		apply.setClientUser(client);
		return apply;
	}

	@Override
	public QuotaReturnDTO quotaReturn(QuoteBackMessageDTO result, String insId)
			throws ActionException {
		logger.info("HttpMinanDataActionImpl  quotaReturn  民安报价返回的数据组装   入参  result："+result+"   insId："+insId);
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		//保险公司ID
		quotaReturnDTO.setInsId(insId);
		//返回的code
		quotaReturnDTO.setErrorCode(result.getQtnBase().getErrorCode());
		//返回的信息
		quotaReturnDTO.setErrorMessages(result.getQtnBase().getErrorMsg());
		//报价单号
		quotaReturnDTO.setQuotaId("MACNHTTP"+DateUtil.dateToString("yyyyMMddHHmmssSSS",new Date()));
		if(null != result.getApplicationTCI()){			
			//交强险费用
			quotaReturnDTO.setPremTCITax(result.getApplicationTCI().getExpectPrm());
		}
		if(null != result.getApplicationVCI()){			
			//商业险费用
			quotaReturnDTO.setPremVCITax(result.getApplicationVCI().getExpectPrm());
			//商业险折扣
			Double i = result.getApplicationVCI().getDiscount().doubleValue()/100;
			java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.0000");
			quotaReturnDTO.setDiscount(new BigDecimal(myformat.format(i)));
		}
		if(null != result.getVehicleTaxation()){			
			//车船税费用
			quotaReturnDTO.setVehicleTax(result.getVehicleTaxation().getSumTax());
			//车船税计算是否成功
			quotaReturnDTO.setCalcSuccess(result.getVehicleTaxation().isCalcSuccess());
		}
		//险种信息
		List<CoverageItemDTO> coverageItems = new ArrayList<CoverageItemDTO>();
		for(com.zxcl.minan_intf.dto.quoteresult.CoverageItemDTO coverageItemDTO : result.getCoverageItems()){
			try {
				CoverageItemDTO coverageItem = new CoverageItemDTO();
				CorrespondDTO correspondDTO = corrService.getTwo(new CorrespondDTO(insId, BaseFinal.INSURANCETYPECODE, "",coverageItemDTO.getCoverageCode()));
				if(null != correspondDTO){					
					//险种
					coverageItem.setCode(correspondDTO.getCode());
					//保费
					coverageItem.setAmount(coverageItemDTO.getExpectPrm());
					//保额
					coverageItem.setSumLimit(coverageItemDTO.getSumLimit());
					coverageItems.add(coverageItem);
				}
			} catch (BusinessServiceException e) {
				logger.error("中华报价返回数据组装查询险种失败",e);
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
