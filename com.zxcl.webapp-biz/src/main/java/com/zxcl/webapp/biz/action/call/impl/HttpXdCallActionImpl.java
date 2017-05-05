package com.zxcl.webapp.biz.action.call.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.alibaba.fastjson.JSONObject;
import com.bxtx.xd.intf.biz.service.XDService;
import com.bxtx.xd.intf.dto.req.AuthDTO;
import com.bxtx.xd.intf.dto.req.BaseDTO;
import com.bxtx.xd.intf.dto.req.CustomerDTO;
import com.bxtx.xd.intf.dto.req.ItemKindDetail;
import com.bxtx.xd.intf.dto.req.RequestParams;
import com.bxtx.xd.intf.dto.req.VehicleDTO;
import com.bxtx.xd.intf.dto.res.ApplicationTCIDTO;
import com.bxtx.xd.intf.dto.res.ApplicationVCIDTO;
import com.bxtx.xd.intf.dto.res.QtnBaseDTO;
import com.bxtx.xd.intf.dto.res.QuoteBackMessageDTO;
import com.bxtx.xd.intf.dto.res.ReInsureItemDTO;
import com.bxtx.xd.intf.dto.res.VehicleTaxationDTO;
import com.zxcl.webapp.biz.action.call.HttpXdCallAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.exception.ThirdInsurNotFoundException;
import com.zxcl.webapp.biz.service.CorrespondService;
import com.zxcl.webapp.biz.service.CoverageItemService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsXmlService;
import com.zxcl.webapp.biz.service.InsuranceCompanyConfigService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.util.BaseFinal;
import com.zxcl.webapp.dto.CorrespondDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

/**
 * 信达调用接口
 * @author Li xiaokang
 *
 */
@Service
public class HttpXdCallActionImpl implements HttpXdCallAction{
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private InquiryService inquiryService;

	@Autowired
	private MicroService microService;

	@Autowired
	private CoverageItemService coverService;
	
	@Autowired
	private CorrespondService corrService;
	
	@Autowired
	private XDService xdQuotaRemoteService;
	
	@Autowired
	private InsuranceCompanyConfigService insuranceCompanyConfigService;
	@Autowired
	private InsXmlService insXmlService;
	@Autowired
	private InsuranceCompanyConfigService configService;
	@Override
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId) {
		
		/**
		 * 报价
		 */
		QuoteBackMessageDTO quoteDTO = null;
		try {
			quoteDTO = xdQuotaRemoteService.xdQuote(getQuotaReq(userId, inquiryId, insId));
			logger.info("信达报价远端传回的结果QuoteBackMessageDTO : " + JSONObject.toJSONString(quoteDTO));
			System.err.println("信达报价远端传回的结果QuoteBackMessageDTO : " + JSONObject.toJSONString(quoteDTO));
		}catch (Exception e) {
			logger.error("信达报价失败4",e);
			return new QuotaReturnDTO("error", "信达报价失败4",insId);
		}
		return getQuotaRsp(userId, inquiryId, insId, quoteDTO);
	}

	/**
	 * 报价请求数据整理
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @return
	 */
	private RequestParams getQuotaReq(String userId, String inquiryId, String insId) throws Exception{
		RequestParams params = new RequestParams();
		
		//小薇
		MicroDTO microDTO = null;
		try {
			microDTO = microService.getMicroByUserId(userId);
		} catch (BusinessServiceException e) {
			logger.error("信达报价接口数据组装小微查询失败", e);
			throw new ActionException("信达报价接口数据组装小微查询失败",e);
		}
		
		//询价单
		InquiryDTO inquiry = null;
		try {
			inquiry = inquiryService.get(inquiryId, microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("信达报价接口数据组装询价单查询失败", e);
			throw new ActionException("信达报价接口数据组装询价单查询失败",e);
		}
		
//		//获取保险公司配置信息
//		String configId = "";
//		try {//查询保险公司配置信息ID
//			configId = microService.getConfigIdByInsIdAndMicroId(insId, microDTO.getMicro_id());
//		} catch (BusinessServiceException e) {
//			logger.error("信达报价接口数据组装查询保险公司配置信息ID失败", e);
//			throw new ActionException("查询保险公司配置信息ID失败");
//		}
//		
//		Map<String,Object> configMap = new HashMap<String,Object>();
//		try {//查询保险公司配置信息
//			configMap = insuranceCompanyConfigService.getMapByInsId(configId);
//		} catch (BusinessServiceException e) {
//			logger.error("信达报价接口数据组装查询保险公司配置信息失败", e);
//			throw new ActionException("查询保险公司配置信息失败");
//		}
		
		// 登录认证信息
		AuthDTO authDTO=getConfigByAgentDTO(userId,insId);
		logger.info("认证信息 : " + JSONObject.toJSONString(authDTO));
		
		// 配置信息
		// 其他必要信息
		BaseDTO baseDTO=new BaseDTO();
		//页面获取
		baseDTO.setStartDateCI(inquiry.getTciStartDateStr());
		baseDTO.setEndDateCI(inquiry.getTciEndDateStr());
		baseDTO.setImmeValidStartDateCI(inquiry.getTciStartDateStr());
		baseDTO.setImmeValidEndDateCI(inquiry.getTciEndDateStr());
		baseDTO.setStartDateBI(inquiry.getVciStartDateStr());
		baseDTO.setEndDateBI(inquiry.getVciEndDateStr());
		baseDTO.setImmeValidStartDateBI(inquiry.getVciStartDateStr());
		baseDTO.setImmeValidEndDateBI(inquiry.getVciEndDateStr());
		
		//默认配置
		baseDTO.setIsCIInsure("1");
		baseDTO.setIsBIInsure("1");
		// 交强险
		baseDTO.setRiskCodeCI("0507");
		// 01:POS机交易
		baseDTO.setPayMethodCI("01");
		// 商业险
		baseDTO.setRiskCodeBI("0508");
		baseDTO.setPayMethodBI("01");
		// 交强险投保情况,1:以投保, 0:未投保
		baseDTO.setCiProposalType("1");
		// 不投保交强险原因, 01:军警车辆,02:场内车辆,03,挂车
		baseDTO.setUnProposalReason("02");
		// 交强承保公司
		baseDTO.setCiInsurerType("1");
		baseDTO.setCiinsurerCompany("信达财产保险股份有限公司");
		// 续保标识,0:非续保,1:续保
		baseDTO.setRenewalFlagCI("0");
		baseDTO.setRenewalFlagBI("0");
		// 股东业务标识,0:否,1,是
		baseDTO.setShareHolderFlagBC("0");
		baseDTO.setShareHolderNameBC("0");
		
		// 归属部门
		baseDTO.setComCodeBC("15111000");
		baseDTO.setComNameBC("沧州中心支公司渠道业务一部");
		// 归属业务员
		baseDTO.setHandlerCodeBCOffset("沧州中心支公司渠道业务一部");
		baseDTO.setHandler1CodeBC("15113021");
		baseDTO.setHandler1NameBC("张书伟");
		// 经办人
		baseDTO.setHandlerCodeBC("15113021");
		baseDTO.setHandlerNameBC("张书伟");
		// 业务来源
		baseDTO.setAgentClerkFlagDel("0");
		// select : 兼业代理-车行代理
		baseDTO.setBusinessNatureBC("B");
		// 是否公司业务
		baseDTO.setIsPersonal("1");
		// 代理/经纪/渠道
		baseDTO.setAgentCodeBC("U15203000029");
		baseDTO.setAgentNameBC("沧州市鼎鑫汽车贸易有限公司临西县分公司");
		// 合同/协议号
		baseDTO.setAgreementNoBC("U15203000029-04");
		// 是否上门投保 1:是,0:否
		baseDTO.setIsInDoorBC("0");
		// 农业性质
		baseDTO.setAgriFlagBC("0");
		// // 单证类型
		baseDTO.setVisaCodeCI("AJ0507A42009C河北");
		baseDTO.setVisaCodeBI("ABC100A32009A");
		// 代理人code
		baseDTO.setAgentCodeTempBC("U15203000029");
		baseDTO.setAgentNameTempBC("沧州市鼎鑫汽车贸易有限公司临西县分公司");
		// 合同协议号
		baseDTO.setAgreementNoBC("U15203000029-04");
		baseDTO.setComLevel("8");
		
		// 车辆信息
		VehicleDTO vehicleDTO = new VehicleDTO();
		//新车购置价
		vehicleDTO.setPurchasePrice(inquiry.getVehiclePrice());
		//发动机号码
		vehicleDTO.setEngineNo(inquiry.getEngNo());
		//初登日期：格式(String : yyyy-MM-dd)
		vehicleDTO.setEnrollDate(getEnrollDate(inquiry.getFstRegNo()).toString());
		//车架号
		vehicleDTO.setFrameNo(inquiry.getFrmNo());
		//车牌号码
		vehicleDTO.setLicenseNoBC(inquiry.getPlateNo());
		logger.info("过户日期===> " + inquiry.getTransferDateStr());
		System.err.println("过户日期=====> " + inquiry.getTransferDateStr());
		if(null != inquiry.getPlateNo() && inquiry.getPlateNo().indexOf("*") != -1){
			vehicleDTO.setIsFullPolicyNoBI("1");
			vehicleDTO.setNoLicenseFlagBI("");
			vehicleDTO.setLicenseNoBC("新车");
		}else{
			//车的登记日期跟当前时间比较，等于或者超过9个月视为旧车
			int useMonths = useMouths(new Date(), inquiry.getFstRegNo());
			logger.info("车辆使用月数" + useMonths);
			if(useMonths > 9){
				vehicleDTO.setIsFullPolicyNoBI("0");
			}else{
				vehicleDTO.setIsFullPolicyNoBI("1");
			}
		}
		
//		vehicleDTO.setModelCode("MSBBHI0006");
		vehicleDTO.setModelCode(inquiry.getModelCode());
		logger.info("车型编码: " + inquiry.getModelCode());
		System.err.println("车型编码: " + inquiry.getModelCode());
		vehicleDTO.setEcdemicVehicleFlagBI("0");// 是否为本地车辆：0:否,1:是
		vehicleDTO.setChgOwnerFlagBC(inquiry.getTransferSign());
		if(inquiry.getVciStartDate().getMonth()-inquiry.getFstRegNo().getMonth()<-3 && inquiry.getVciStartDate().getDate()-inquiry.getFstRegNo().getDate()<0){
			vehicleDTO.setUseYears((inquiry.getVciStartDate().getYear() - inquiry.getFstRegNo().getYear()-1)+"");
		}else{
			vehicleDTO.setUseYears((inquiry.getVciStartDate().getYear() - inquiry.getFstRegNo().getYear()) + "");
		}
		// 是否为本地车辆：0:否,1:是
		vehicleDTO.setEcdemicVehicleFlagBI("0");
		// 过户车辆标识：0:否,1:是 
		vehicleDTO.setChgOwnerFlagBC("0");
		//过户日期
		vehicleDTO.setChgOwnerDateBC(inquiry.getTransferDateStr());
		// 机构的新车购置价上浮
		vehicleDTO.setNewCarPriceRiseBC("20");
		// 机构的新车购置价下浮
		vehicleDTO.setNewCarPriceFallBC("10");
		// 使用性质：8A:家庭自用
		vehicleDTO.setUseNatureCodeBC("8A");
		// 号牌种类：02-小型汽车号牌
		vehicleDTO.setLicenseKindCodeBC("02");
		// 车主性质,0:个人
		vehicleDTO.setCarOwnerNature1BC("0");
		// 号牌颜色：01:蓝
		vehicleDTO.setLicenseColorCodeBC("01");
		// 车辆大类：A:客车
		vehicleDTO.setMainCarKindCodeBC("A");
		// 车辆种类：A0:客车
		vehicleDTO.setCarKindCodeBC("A0");
		// 车辆类型描述：K31-小型普通客车,K33-小型轿车
		vehicleDTO.setLicenseCategoryBC("K33");
		// 车身颜色,01:蓝
		vehicleDTO.setColorCodeBC("01");
		// 车辆类型,1:6座以下
		vehicleDTO.setCarTypeCodeBC("1");
		// 车船税减免标识
		vehicleDTO.setNewFuelModelBC("正常缴税");
		// 年平均行驶里程：
		vehicleDTO.setRunMilesAverageBI("40000");
		// 行驶区域： 04:中国境内
		vehicleDTO.setRunAreaCodeBC("04");
		vehicleDTO.setRunAreaNameBC("中国境内（不含港、澳、台）");
		// // 无赔优系数：14-新保或上年赔款次数在3次以下
		vehicleDTO.setDamagedFactorGradeBI("14");
		// 单双号标识：00:正常车辆
		vehicleDTO.setRestricFlagLCI("00");
		// 上年度是否<br>在本公司承保：0:否 ,1:是
		vehicleDTO.setOtherNature2BI("0");
		// 无赔折扣
		vehicleDTO.setNoClaimFavorTypeBC("1");
		// 上一保险年度有责任交通事故或有责任赔款记录：4:四级
		vehicleDTO.setDamagedFactorGradeCI("4");
		// 上一保险年度道路交通安全违法行为记录(随车/随人)：轻微0次
		vehicleDTO.setlViolatedTimesCI("0");
		// 上一保险年度道路交通安全违法行为记录(随车/随人)：严重0次
		vehicleDTO.setsViolatedTimesCI("0");
		// 无赔优不浮动原因
		vehicleDTO.setNoClaimAdjustReasonBI("没有上年度保单，不浮动");
		// // 客户忠诚度不浮动原因
		vehicleDTO.setNoLoyaltyAdjustReasonBI("没有上年度保单，不浮动");
		// 免税车型标识s
		vehicleDTO.setRelifTaxBrandFlagBC("0");
		//排量
		vehicleDTO.setDisplacement(inquiry.getDisplacement().toString());
		
		// 关系人信息
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setAppliCustomerTypeBC("1");
		//3:自然人
		customerDTO.setAppliNatureBC("3");
		// 投保人名称
		customerDTO.setAppliNameBC(inquiry.getOwnerName());
		// 证件类型,01:身份证
		customerDTO.setAppliIdentifyTypeBC("01");
		//证件号码
		customerDTO.setAppliIdNumBC(inquiry.getOwnerCertNo());
		customerDTO.setAppliLinkerNameBC(inquiry.getOwnerName());
		customerDTO.setAppliMobileBC("13800138000");
		customerDTO.setAppliAddressBC("四川成都");
		customerDTO.setAppliPostCodeBC("610100");
		
		
		//处理险种
		List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverItems = null;
		try {
			coverItems = coverService.getCoverageItems(inquiryId, microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("信达报价接口数据组装处理险种失败", e);
			throw new ActionException("信达报价接口数据组装处理险种失败");
		}
		
		// 商业险   
		Map<String, ItemKindDetail> map = opetateCoverageItemList(inquiry, insId, coverItems);
		
		params.setReqAuthDTO(authDTO);
		params.setVehicleDTO(vehicleDTO);
		params.setCustomerDTO(customerDTO);
		params.setBaseDTO(baseDTO);
		params.setMap(map);
		
		logger.info("手机端传入的参数: " + JSONObject.toJSON(params));
		System.err.println("手机端传入的参数: " + JSONObject.toJSONString(params));
		
		return params;
	}
	
	/**
	 * 报价返回结果整理
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @param quoteDTO
	 * @return
	 */
	private QuotaReturnDTO getQuotaRsp(String userId, String inquiryId, String insId, QuoteBackMessageDTO quoteDTO) {
		logger.info("信达报价结果处理......");
		if(null == quoteDTO){
			logger.info("null == quoteDTO");
			return new QuotaReturnDTO("error", "信达报价失败1",insId);
		}
		if(null == quoteDTO.getQtnBase()) {
			logger.info("quoteDTO.getQtnBase() == null)");
			return null;
		}
		
		QtnBaseDTO qtnBaseDTO = quoteDTO.getQtnBase();//基本信息
		ApplicationTCIDTO applicationTCIDTO = quoteDTO.getApplicationTCI();//交强险
		ApplicationVCIDTO applicationVCIDTO  = quoteDTO.getApplicationVCI();//商业险
		VehicleTaxationDTO vehicleTaxationDTO = quoteDTO.getVehicleTaxation();//车船税
		// 请求报价成功, 封装反参
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		logger.info("===> " + quoteDTO.getQtnBase().getErrorCode());
		// 重复投保
		if("REPEAT".equals(quoteDTO.getQtnBase().getErrorCode())) {
			return new QuotaReturnDTO("error", getReInsureInfo(quoteDTO),insId, qtnBaseDTO.getUserName());
		}
		
		if(!"SUCCESS".equals(quoteDTO.getQtnBase().getErrorCode())){
			return new QuotaReturnDTO("error", qtnBaseDTO.getErrorMsg(),insId, qtnBaseDTO.getUserName());
		}else {
			quotaReturnDTO.setCalcSuccess(true); // 报价成功标准
		}
		
		List<com.bxtx.xd.intf.dto.res.CoverageItemDTO> coverageItems = quoteDTO.getCoverageItems();//商业险种
		quoteDTO.getReInsureItem();//重复投保信息
		
		BigDecimal totalCost = new BigDecimal(0.00); // 总保费
		if(applicationTCIDTO != null) {
			quotaReturnDTO.setTciReInsure(1);
			quotaReturnDTO.setPremTCITax(applicationTCIDTO.getExpectPrm());// 交强险
			totalCost = totalCost.add(applicationTCIDTO.getExpectPrm());// 加交强险保费
		}
		if(applicationVCIDTO != null) { // 商业险
			quotaReturnDTO.setVciReInsure(1);
			quotaReturnDTO.setDiscount(applicationVCIDTO.getDiscount());// 折扣率
//			quotaReturnDTO.setLastYearClaimNum(applicationVCIDTO.getLastYearClaimNum());// 上年出险次数
			quotaReturnDTO.setPremVCITax(applicationVCIDTO.getExpectPrm());//商业险
			totalCost = totalCost.add(applicationVCIDTO.getExpectPrm());// 加商业险保费
		}
		if(qtnBaseDTO != null) { // 基础信息
			quotaReturnDTO.setQuotaId(qtnBaseDTO.getQtnID());
			quotaReturnDTO.setErrorCode(qtnBaseDTO.getErrorCode());
			quotaReturnDTO.setErrorMessages(qtnBaseDTO.getErrorMsg());
		}
		
		quotaReturnDTO.setInsId(insId); // 保险公司ID
		quotaReturnDTO.setQuotaId(System.currentTimeMillis() + ""); // 报价单号
		quotaReturnDTO.setReInsureInfo(getReInsureInfo(quoteDTO));// 重复投保信息
		if(vehicleTaxationDTO != null){
			quotaReturnDTO.setVehicleTax(vehicleTaxationDTO.getSumTax());//车船税
			totalCost = totalCost.add(vehicleTaxationDTO.getSumTax());// 加车船税
		}
		
		quotaReturnDTO.setTotalCost(totalCost);//总保费
		logger.info("总保费 : " + totalCost);
		System.err.println("总保费 : " + totalCost);
		
		// 处理险别信息
		logger.info("信达报价处理险别信息......");
		List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverageItemList = null;
		if(CollectionUtils.isNotEmpty(coverageItems)){
			com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO coverageItem = null;
			CorrespondDTO correspondDTOR = null;
			coverageItemList = new ArrayList<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO>(coverageItems.size());
			CorrespondDTO correspondDTO = new CorrespondDTO(insId, BaseFinal.INSURANCETYPECODE, null,null);
			for(com.bxtx.xd.intf.dto.res.CoverageItemDTO item : coverageItems){
				coverageItem = new com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO();
				correspondDTO.setIns_code(item.getCoverageCode());// 信达保险公司险别码
				try {
					correspondDTOR = corrService.get(correspondDTO);
					if(correspondDTOR != null){
						coverageItem.setCode(correspondDTOR.getCode());// 平台保险公司险别码
					}
				} catch (BusinessServiceException e) {
					logger.error("信达报价解析查询险种失败，险种编码为："+item.getCoverageCode(),e);
					throw new RuntimeException("信达报价解析查询险种失败，险种编码为：" + item.getCoverageCode(), e);
				}
				
				// 保额
				coverageItem.setSumLimit(item.getSumLimit());
				
				// 保费 
				coverageItem.setAmount(item.getExpectPrm());
				
				coverageItemList.add(coverageItem);
			}
			quotaReturnDTO.setCoverageItems(coverageItemList);//
		}
		
		logger.info("封装完成，返回到前端的结果 : " + JSONObject.toJSON(quotaReturnDTO));
		
		return quotaReturnDTO;
	}

	
	/**
	 *  商业险险种处理
	 * @param inquiry
	 * @param insId
	 * @param coverItems
	 * @return
	 */
	private Map<String, ItemKindDetail> opetateCoverageItemList(InquiryDTO inquiry, String insId, List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverItems) throws Exception{
		Map<String, ItemKindDetail> prpCitemKindsTemps = new HashMap<String, ItemKindDetail>();
		CorrespondDTO itm = null;
		// 险种信息
		System.err.println("险别数量: ====> " + coverItems.size());
		for (CoverageItemDTO item : coverItems) {
			// 创建每一个险种 
			ItemKindDetail kind = new ItemKindDetail();
			
			itm = new CorrespondDTO(insId,BaseFinal.INSURANCETYPECODE,item.getCode());//初始化保险公司代码
			if(itm != null && "033008".equals(itm.getCode())) {// 无法找到第三方险
				throw new ThirdInsurNotFoundException("信达不能报价,无法找到第三方险");
			}
			if(itm != null && "032608".equals(itm.getCode())) {// 涉水险
				throw new ThirdInsurNotFoundException("信达不能报价,涉水险不能报价");
			}
			try {
				// 保险公司代码
				itm = corrService.get(itm);
				if(itm != null) {
					System.err.println("险别代码: ===> " + itm.getIns_code());
					kind.setChooseFlag("on");
					if(item.getNoDduct() == 1) {// 选择不计免赔
						kind.setSpecialFlag("on");
					}else {
						kind.setSpecialFlag("off");
					}
					// 将前端传入的险种设置到报价参数里面
					prpCitemKindsTemps.put(itm.getIns_code(), kind);
					if("B".equals(itm.getIns_code())) {// 三者险
						ItemKindDetail kindsTemp = new ItemKindDetail();
						kindsTemp.setChooseFlag("on");
						kindsTemp.setAmount(item.getSumLimit().toString());
						if(item.getNoDduct() == 1) {// 选择不计免赔
							kindsTemp.setSpecialFlag("on");
						}else {
							kindsTemp.setSpecialFlag("off");
						}
						prpCitemKindsTemps.put(itm.getIns_code(), kindsTemp);
					}
					if("D3".equals(itm.getIns_code())) {// 司机险
						ItemKindDetail kindsTemp = new ItemKindDetail();
						kindsTemp.setChooseFlag("on");
						kindsTemp.setAmount(item.getSumLimit().toString());
						if(item.getNoDduct() == 1) {// 选择不计免赔
							kindsTemp.setSpecialFlag("on");
						}else {
							kindsTemp.setSpecialFlag("off");
						}
						prpCitemKindsTemps.put(itm.getIns_code(), kindsTemp);
					}
					if("D4".equals(itm.getIns_code())) {// 乘客险
						ItemKindDetail kindsTemp = new ItemKindDetail();
						kindsTemp.setChooseFlag("on");
						kindsTemp.setUnitAmount(item.getSumLimit().divide(new BigDecimal(inquiry.getSeatNum() - 1),1, BigDecimal.ROUND_UP)+"");
						kindsTemp.setAmount(item.getSumLimit() + "");
						if(item.getNoDduct() == 1) {// 选择不计免赔
							kindsTemp.setSpecialFlag("on");
						}else {
							kindsTemp.setSpecialFlag("off");
						}
						prpCitemKindsTemps.put(itm.getIns_code(), kindsTemp);
						System.out.println("====> " + item.getSumLimit().divide(new BigDecimal(inquiry.getSeatNum() - 1),1, BigDecimal.ROUND_UP)+"   ====>" +  item.getSumLimit() + "");
					}
					if("L".equals(itm.getIns_code())) {// 车身划损险
						ItemKindDetail kindsTemp = new ItemKindDetail();
						kindsTemp.setChooseFlag("on");
						kindsTemp.setAmount(item.getSumLimit().toString());
						if(item.getNoDduct() == 1) {// 选择不计免赔
							kindsTemp.setSpecialFlag("on");
						}else {
							kindsTemp.setSpecialFlag("off");
						}
						prpCitemKindsTemps.put(itm.getIns_code(), kindsTemp);
						logger.info("050211carcarcar" + JSONObject.toJSONString(item));
					}
					if("F".equals(itm.getIns_code())) {// 玻璃单独破碎险
						ItemKindDetail kindsTemp = new ItemKindDetail();
						kindsTemp.setChooseFlag("on");
						if("1".equals(item.getGlsType())) {// 前端传入的玻璃类型,1:国产,2:进口
							kindsTemp.setGlsType("1");// 保险公司的玻璃类型,1:国产, 2:进口
						}else if("2".equals(item.getGlsType())) {
							kindsTemp.setGlsType("2");
						}
						prpCitemKindsTemps.put(itm.getIns_code(), kindsTemp);
						System.out.println("玻璃类型====> " + item.getGlsType());
					}
				}
			} catch (BusinessServiceException e) {
				logger.error("信达报价处理处理险种出错" + e);
				throw new RuntimeException("信达报价处理处理险种出错" ,e);
			}
		}
		return prpCitemKindsTemps;
	}
	
	/**
	 * 使用车辆使用月数是否大于9个月来判断该车是否是新车
	 * @param now
	 * @param date
	 * @return
	 */
	private static int useMouths(Date now, Date date){
			int useMouths = 0;
			if(null == now){
				now = new Date();
			}
			if(null != date){
				Calendar calendar = Calendar.getInstance(Locale.CHINA);
				calendar.setTime(now==null?new Date():now);
				int year1 = calendar.get(Calendar.YEAR);
				int mouth1 = calendar.get(Calendar.MONTH);
				
				calendar.setTime(date);
				int year2 = calendar.get(Calendar.YEAR);
				int mouth2 = calendar.get(Calendar.MONTH);
				useMouths = (year1 - year2)*12 + (mouth1-mouth2);
			}
			return useMouths;
		}
	
	
	/**
	 * 获取重复投保信息
	 */
	private String getReInsureInfo(QuoteBackMessageDTO quoteDTO) {
		ReInsureItemDTO reInsureItem = quoteDTO.getReInsureItem();
		String reInsureInfo = "";
		try {
			if(reInsureItem != null) {
				// 商业险重复投保
				if(reInsureItem.isVciReInsure()) {
					String policyNo = reInsureItem.getPolicyNo();
//					String biBeginDate = dateToStrFormat(reInsureItem.getEffectiveDate());
					String biEndDate = dateToStrFormat(reInsureItem.getExpireDate());
					reInsureInfo = "商业险重复投保, 商业险单号:"+policyNo + ",商业险重复投保止期:" + biEndDate;
				}
				// 商业险重复投保 + 交强险重复投保
				if(reInsureItem.isTciReInsure()) {
					String tciPlyNo = reInsureItem.getTciPlyNo();
//					String ciBeginDate = dateToStrFormat(reInsureItem.getTciBeginDate());
					String ciEndDate = dateToStrFormat(reInsureItem.getTciEndDate());
					reInsureInfo = reInsureInfo + "交强险重复投保, 交强险单号:" + tciPlyNo + ",交强险重复投保止期:" + ciEndDate;
				}
			}
		} catch (Exception e) {
			logger.error("前端封装重复投保信息异常",e);
			throw new RuntimeException("前端封装重复投保信息异常", e);
		}
		logger.info("重复投保信息: " + reInsureInfo);
		return reInsureInfo;
	}
	private AuthDTO getConfigByAgentDTO(String userId, String insId) throws BusinessServiceException {
		logger.info("信达查询代理配置信息开始:insId"+insId+"userId"+userId);
		try{
		AuthDTO authDTO;
		MicroDTO microByUserId = microService.getMicroByUserId(userId);
		String configId = microService.getConfigIdByInsIdAndMicroId(insId, microByUserId.getMicro_id());
		Map<String, Object> mapConfig = configService.getMapByInsId(configId);
		//代理信息
		String username=mapConfig.get("XDCPHTTP_USERNAME").toString();
		String password=mapConfig.get("XDCPHTTP_PASSWORD").toString();
		String proxyUrl=mapConfig.get("XDCPHTTP_PROXYURL").toString();
		Integer proxyPort=Integer.parseInt(mapConfig.get("XDCPHTTP_PROXYPORT").toString());
		String riskCode=mapConfig.get("XDCPHTTP_RISKCODE").toString();
		String classCodeSelect=mapConfig.get("XDCPHTTP_CLASSCODESELECT").toString();
		authDTO=new AuthDTO(username, password, proxyUrl, proxyPort, "", riskCode, classCodeSelect);
		logger.info("信达查询代理信息结束:"+authDTO);
		return authDTO;
		}catch(BusinessServiceException e){
			logger.error("信达代理配置信息查询失败",e);
			throw new BusinessServiceException("信达代理配置信息查询失败");
		}
		
	}
	 
	 /**
	  * @return返回字符串格式 yyyyMMddHHmmss
	  */
	  private String dateToStrFormat(Date date) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	  String dateString = formatter.format(date);
	  return dateString;
	 }
	  
	 /**
	 * 将Date转成yyyy-MM-dd
	 * @param date
	 * @return
	 * @throws Exception 
	 * @throws ParseException 
	 */
	private String getEnrollDate(Date date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.format(date);
		} catch (Exception e) {
			logger.error("时间格式转换出错",e);
			throw new ActionException("时间格式转换出错");
		}
	}
}



