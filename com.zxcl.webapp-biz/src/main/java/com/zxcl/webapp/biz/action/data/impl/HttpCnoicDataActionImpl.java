package com.zxcl.webapp.biz.action.data.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.huahai_http.dto.ApplyInfoDTO;
import com.zxcl.huahai_http.dto.Base;
import com.zxcl.huahai_http.dto.ClientUser;
import com.zxcl.huahai_http.dto.Cvrg;
import com.zxcl.huahai_http.dto.Vhl;
import com.zxcl.huahai_http.dto.Vhlowner;
import com.zxcl.huahai_http.dto.quoteresult.QuoteBackMessageDTO;
import com.zxcl.webapp.biz.action.data.HttpCnoicDataAction;
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

@Service
public class HttpCnoicDataActionImpl implements HttpCnoicDataAction{

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private MicroService microService;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private InsuranceService insuranceService;
	
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
		logger.info("HttpCnoicDataActionImpl   quota  华海报价数据组装   userId："+userId+"  insuranceDTO："+insuranceDTO+"   inquiryId："+inquiryId+"  configMap："+configMap);
		//小微
		MicroDTO microDTO = new MicroDTO();
		try {
			microDTO = microService.getMicroByUserId(userId);
		} catch (BusinessServiceException e) {
			logger.error("华海报价接口数据组装查询小微失败", e);
			throw new ActionException("系统异常");
		}
		
		//询价单
		InquiryDTO inquiryDTO = new InquiryDTO();
		try {
			inquiryDTO = inquiryService.get(inquiryId, microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("华海报价接口数据组装查询询价单失败", e);
			throw new ActionException("系统异常");
		}
//		//顶级保险公司
//		InsuranceDTO insTop = new InsuranceDTO();
//		try {
//			insTop = insuranceService.get(insuranceDTO.getInsId());
//		} catch (BusinessServiceException e) {
//			logger.error("中华报价接口数据组装查询顶级保险公司失败", e);
//			throw new ActionException("系统异常");
//		}
		
		//查询险种集合	
		List<CoverageItemDTO> coverItems = new ArrayList<CoverageItemDTO>();
		try {
			coverItems = coverService.getCoverageItems(inquiryId,
					microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("华海报价接口数据组装查询险种集合失败", e);
			throw new ActionException("系统异常");
		}
		// 处理车牌号
		inquiryDTO.setPlateNo(StringUtils.isBlank(inquiryDTO.getPlateNo())
				|| inquiryDTO.getPlateNo().length() != 7 ? null : inquiryDTO.getPlateNo());
		
		//保险公司ID
		String insId = insuranceDTO.getInsId();
		
		ApplyInfoDTO apply = new ApplyInfoDTO();
		//用户信息
		ClientUser clientUser = new ClientUser();
		clientUser.setAgentUserId(CommonUtil.valueOf(configMap.get(insId+"_LoginUser")));
		clientUser.setAgentUserPwd(CommonUtil.valueOf(configMap.get(insId+"_LoginPwd")));
		clientUser.setXiaoweiUserId(userId);
		apply.setClientUser(clientUser);
		
		Base base = new Base();
		base.setCDptCde(CommonUtil.valueOf(configMap.get(insId+"_CDptCde"))); //承保机构, 账号相关
		//投保标志
		String quotaSign = "";
		if("1".equals( inquiryDTO.getVciSign())){//商业
			quotaSign += "033011";
		}
		if("1".equals( inquiryDTO.getTciSign()) && "1".equals( inquiryDTO.getVciSign())){//联合
			quotaSign += "_";
		}
		if("1".equals( inquiryDTO.getTciSign())){//交强
			quotaSign += "030001";
		}
		base.setCProdNo(quotaSign);
		
		base.setCAgtAgrNo(CommonUtil.valueOf(configMap.get(insId+"_CAgtAgrNo"))); //代理协议
		base.setCBrkrCde(CommonUtil.valueOf(configMap.get(insId+"_CBrkrCde"))); //代理/经纪人 编码
		
		if("1".equals( inquiryDTO.getTciSign())){			
			base.setJQTInsrncBgnTm(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getTciStartDate())+" 00:00:00");
			base.setJQTInsrncEndTm(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getTciEndDate())+" 23:59:59");
		}
		if("1".equals( inquiryDTO.getVciSign())){			
			base.setSYTInsrncBgnTm(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getVciStartDate())+" 00:00:00");
			base.setSYTInsrncEndTm(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getVciEndDate())+" 23:59:59");
		}
		base.setCAreaFlag(""); //保单归属地市 济南
		base.setCCountryFlag(""); //保单归属区县 历下区
		base.setJQCTmSysCde("");
		base.setSYCTmSysCde("");
		apply.setBase(base);
		
		
		Vhlowner owner = new Vhlowner();
		apply.setVhlowner(owner);
		owner.setCCertfCde(inquiryDTO.getOwnerCertNo());
		owner.setCMobile("");
		owner.setCOwnerNme(inquiryDTO.getOwnerName());
		
		Vhl vhl = new Vhl();
		apply.setVhl(vhl);
		vhl.setVhlModelCode(inquiryDTO.getModelCode());
		vhl.setVhlModelName(inquiryDTO.getVehicleName());
		vhl.setCEngNo(inquiryDTO.getEngNo());
		vhl.setCFstRegYm(inquiryDTO.getFstRegNoStr());
		vhl.setCVin(inquiryDTO.getFrmNo());
		vhl.setCNewMrk(StringUtils.isNotBlank(inquiryDTO.getPlateNo()) ? "0":"1"); //是否未上牌
		int montCOunt = CommonUtil.getYearAge(inquiryDTO.getFstRegNo(), null != inquiryDTO.getTciEndDate() ? inquiryDTO.getTciEndDate():inquiryDTO.getTciEndDate());
		
		Calendar begin = Calendar.getInstance();
        begin.setTime(inquiryDTO.getFstRegNo());

        Calendar end = Calendar.getInstance();
        end.setTime(null != inquiryDTO.getTciStartDate() ? inquiryDTO.getTciStartDate():inquiryDTO.getVciStartDate());
        int ys = end.get(Calendar.YEAR) - begin.get(Calendar.YEAR);
		int ym = (end.get(Calendar.MONTH)+1) - (begin.get(Calendar.MONTH)+1);
		montCOunt = ys*12+ym;
		vhl.setCNewVhlFlag(montCOunt <= 9 ? "1" : "0"); //是否新车
		vhl.setCPlateNo(StringUtils.isNotBlank(inquiryDTO.getPlateNo()) ? inquiryDTO.getPlateNo():""); //车牌
		vhl.setCLoanVehicleFlag("0"); //是否车贷投保多年标志
		vhl.setCDevice1Mrk("1".equals(inquiryDTO.getTransferSign()) ? "1" : "0");//是否过户
		vhl.setCEcdemicMrk("0");//是否外地车
		
		//座位数
		int count = 1;
		List<Cvrg> cvrgList = new ArrayList<Cvrg>();
		apply.setCvrgList(cvrgList);
		if("1".equals( inquiryDTO.getVciSign())){//商业险
			java.text.DecimalFormat myformat=new java.text.DecimalFormat("0");
			for (CoverageItemDTO coverageItemDTO: coverItems) {
				Cvrg c = new Cvrg();
				try {
					CorrespondDTO correspondDTO = corrService.get(new CorrespondDTO(insId, BaseFinal.INSURANCETYPECODE, coverageItemDTO.getCode()));
					if(null != correspondDTO){
						c.setCCvrgNo(correspondDTO.getIns_code());
						//玻璃，指定修理厂，第三方特约
						if(!"033006".equals(correspondDTO.getIns_code()) && !"033013".equals(correspondDTO.getIns_code()) && !"033012".equals(correspondDTO.getIns_code())){							
							c.setCDductMrk(1 == coverageItemDTO.getNoDduct() ? "369003":"369004");
						}
						//乘客
						if("033004".equals(correspondDTO.getIns_code())){
							count = inquiryDTO.getSeatNum() - 1;
							c.setNPerAmt(coverageItemDTO.getSumLimit() == null ? "" : myformat.format(coverageItemDTO.getSumLimit().intValue()/count));
						}
						//司机
						if("033003".equals(correspondDTO.getIns_code())){
							c.setNPerAmt(coverageItemDTO.getSumLimit() == null ? "" : myformat.format(coverageItemDTO.getSumLimit()));
						}
						//划痕，三者
						if("033002".equals(correspondDTO.getIns_code()) || "033014".equals(correspondDTO.getIns_code())){
							c.setNAmt(coverageItemDTO.getSumLimit() == null ? "" : myformat.format(coverageItemDTO.getSumLimit()));
						}
					}
				} catch (Exception e) {
					logger.error("华海报价接口数据组装险种代码失败，险种代码为："+coverageItemDTO.getCode(), e);
					throw new ActionException("系统异常");
				}
				cvrgList.add(c);
			}
		}
		
		if("1".equals( inquiryDTO.getTciSign())){//交强险    030000,机动车交通事故强制责任险
			Cvrg c = new Cvrg();
			c.setCCvrgNo("030000"); //交强
			cvrgList.add(c);
		}
		
		return apply;
	}

	@Override
	public QuotaReturnDTO quotaReturn(QuoteBackMessageDTO result, String insId)
			throws ActionException {
		logger.info("HttpCnoicDataActionImpl  quotaReturn  华海报价返回数据组装     入参    result："+result+"   insId："+insId);
//		//顶级保险公司信息
//		InsuranceDTO insTopDTO = new InsuranceDTO();
//		try {
//			insTopDTO = insuranceService.getTop(insId);
//		} catch (BusinessServiceException e) {
//			logger.error("华海报价返回数据组装获取顶级保险公司失败",e);
//			return new QuotaReturnDTO("error","系统异常",insId);
//		}
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		//报价单号
		quotaReturnDTO.setQuotaId("HHBX"+DateUtil.dateToString("yyyyMMddHHmmssSSS",new Date()));
		//保险公司ID
		quotaReturnDTO.setInsId(insId);
		//返回的code
		quotaReturnDTO.setErrorCode(result.getQtnBase().getErrorCode());
		//返回的信息
		quotaReturnDTO.setErrorMessages(result.getQtnBase().getErrorMsg());
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
		}
		
		//险种信息
		List<CoverageItemDTO> coverageItems = new ArrayList<CoverageItemDTO>();
		for(com.zxcl.huahai_http.dto.quoteresult.CoverageItemDTO coverageItemDTO : result.getCoverageItems()){
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
				logger.error("华海报价返回数据组装查询险种失败，险种code："+coverageItemDTO.getCoverageCode(),e);
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
