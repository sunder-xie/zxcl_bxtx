package com.zxcl.webapp.biz.action.call.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bxtx.ha.intf.biz.action.IPCISQuotaAction;
import com.bxtx.ha.intf.dto.quota.ApplicationTCIDTO;
import com.bxtx.ha.intf.dto.quota.ApplicationVCIDTO;
import com.bxtx.ha.intf.dto.quota.CoverageItemDTO;
import com.bxtx.ha.intf.dto.quota.QtnBaseDTO;
import com.bxtx.ha.intf.dto.quota.QuoteBackMessageDTO;
import com.bxtx.ha.intf.dto.quota.VehicleTaxationDTO;
import com.bxtx.ha.intf.entity.PCISAgentDTO;
import com.bxtx.ha.intf.entity.quota.QuotaCvrgReq;
import com.bxtx.ha.intf.entity.quota.QuotaReqDTO;
import com.bxtx.ha.intf.entity.quota.dto.QuotaReqBBRDTO;
import com.bxtx.ha.intf.entity.quota.dto.QuotaReqCARDTO;
import com.bxtx.ha.intf.entity.quota.dto.QuotaReqCZDTO;
import com.bxtx.ha.intf.entity.quota.dto.QuotaReqTBRDTO;
import com.bxtx.ha.intf.entity.vehicle.VehicleModel;
import com.zxcl.webapp.biz.action.call.HttpHACPCallAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.AgencyService;
import com.zxcl.webapp.biz.service.CorrespondService;
import com.zxcl.webapp.biz.service.CoverageItemService;
import com.zxcl.webapp.biz.service.DriverService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsuranceCompanyConfigService;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.util.BaseFinal;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.CorrespondDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;

/**
 * @author zxj
 *
 */
@Service
public class HttpHACPCallActionImpl implements HttpHACPCallAction {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AgencyService agencyService;

	@Autowired
	private InsuranceService insuranceService;

	@Autowired
	private InquiryService inquiryService;

	@Autowired
	private QuotaService quotaService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private MicroService microService;

	@Autowired
	private CoverageItemService coverService;
	
	@Autowired
	private DriverService driverService;

	@Autowired
	private CorrespondService corrService;

	@Autowired
	private IPCISQuotaAction haQuotaRemoteService;
	
	@Autowired
	private InsuranceCompanyConfigService configService;
	
	@Override
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId) throws ActionException {
		
		logger.info("华安http报价开始,userId["+userId+"],inquiryid["+inquiryId+"]");
		//报价
		QuoteBackMessageDTO quoteDTO =null;
		QuotaReqDTO req = null;
		try {
			req = getQuotaReq(userId, inquiryId, insId);
			quoteDTO = haQuotaRemoteService.quota(req);
		} catch (Exception e) {
			logger.error("华安报价失败", e);
			return new QuotaReturnDTO("error", "华安报价失败" + e.getMessage(),insId);
		}
		
		return getQuotaRsp(req, userId, inquiryId, insId, quoteDTO);
	}
	@Override
	public VoteInsuranceReturnDTO vote(String userId, String insId, String quotaId, OwnerDTO owner,
			VoteInsuranceDTO vote, RecognizeeDTO rec) throws ActionException {
		return null;
	}

	@Override
	public CombinedQueryDTO combinedQuery(String userId, String insId, String orderId, String status) throws ActionException {
		return null;
	}
	
	/**
	 * 报价请求数据整理
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @return
	 */
	private static final DecimalFormat decimal_format = new DecimalFormat("0.000");
	private QuotaReqDTO getQuotaReq(String userId, String inquiryId, String insId) throws Exception{
		QuotaReqDTO req = new QuotaReqDTO();
		
		
		//代理信息
		MicroDTO microByUserId = microService.getMicroByUserId(userId);
		String configId = microService.getConfigIdByInsIdAndMicroId(insId, microByUserId.getMicro_id());
		Map<String, Object>  configMap = configService.getMapByInsId(configId);
		
		PCISAgentDTO agent = new PCISAgentDTO();
		try {
			agent.setCAgtAgrNo(configMap.get("HACPHTTP_CAGTAGRNO").toString());
			agent.setCBrkrCde(configMap.get("HACPHTTP_CBRKRCDE").toString());
			agent.setCDptCde(configMap.get("HACPHTTP_CDPTCDE").toString());
			agent.setCLicense(configMap.get("HACPHTTP_CLICENSE").toString());
			agent.setCOprNm(configMap.get("HACPHTTP_COPRNM").toString());
			agent.setCSalegrpCde(configMap.get("HACPHTTP_CSAlEGRPCDE").toString());
			agent.setCSlsCde(configMap.get("HACPHTTP_CSLSCDE").toString());
			agent.setCTrueAgtCde(configMap.get("HACPHTTP_CTRUEAGTCDE").toString());
			agent.setMac(configMap.get("HACPHTTP_MAC").toString());
			agent.setUsername(configMap.get("HACPHTTP_USERNAME").toString());
			agent.setPassword(configMap.get("HACPHTTP_PASSWORD").toString());
			agent.setIsPlateCalcVsTax(configMap.get("HACPHTTP_ISPLATECALCVSTAX").toString());
			agent.setDptCde(configMap.get("HACPHTTP_DPTCDE").toString());
		} catch (Exception e) {
			throw new RuntimeException("华安配置信息缺失，请检查配置信息");
		}
		req.setAgent(agent);
		
		//小薇
		MicroDTO microDTO = null;
		try {
			microDTO = microService.getMicroByUserId(userId);
		} catch (BusinessServiceException e) {
			logger.error("华安报价接口数据组装小微查询失败", e);
			throw new Exception("华安报价接口数据组装小微查询失败");
		}
		
		//询价单
		InquiryDTO inquiry = null;
		try {
			inquiry = inquiryService.get(inquiryId, microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("华安报价接口数据组装询价单查询失败", e);
			throw new Exception("华安报价接口数据组装询价单查询失败");
		}
		
		//处理险种
		List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverItems = null;
		try {
			coverItems = coverService.getCoverageItems(inquiryId, microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("华安报价接口数据组装处理险种失败", e);
			throw new Exception("华安报价接口数据组装处理险种失败");
		}
		
		//交强险信息
		if("1".equals(inquiry.getTciSign())){
			req.setIsJQ("Y");
			req.setjQStartTm(inquiry.getTciStartDate());
			req.setjQEndTm(inquiry.getTciEndDate());
		}
		else{
			req.setIsJQ("N");
			req.setjQStartTm(null);
			req.setjQEndTm(null);
		}
		
		//车辆实际价值
		BigDecimal realPrice = null;
		Date startDate = null;
		if("1".equals(inquiry.getVciSign())){
			startDate = inquiry.getVciStartDate();
		}else if("1".equals(inquiry.getTciSign())){
			startDate = inquiry.getTciStartDate();
		}else{
			startDate = new Date();
		}
		String realPriceStr = calcNActualValue(inquiry.getFstRegNoStr(), DateUtil.dateToString(DateUtil.YYYY_MM_DD, startDate), inquiry.getVehiclePrice());
		realPrice = new BigDecimal(realPriceStr);
		
		
		//车辆实际价值不能小于购置价*0.2
		BigDecimal maxRealPrice = new BigDecimal(inquiry.getVehiclePrice()).multiply(new BigDecimal(0.2));
		if(realPrice.compareTo(maxRealPrice) < 0){
			realPrice = maxRealPrice;
		}
		realPrice = new BigDecimal(decimal_format.format(realPrice));
		inquiry.setRealPrice(realPrice.toString());
		
		
		//商业险及险种处理
		if("1".equals(inquiry.getVciSign()) && CollectionUtils.isNotEmpty(coverItems)){
			req.setIsSY("Y");
			req.setsYStartTm(inquiry.getVciStartDate());
			req.setsYEndTm(inquiry.getVciEndDate());
			req.setCvrgList(opetateCoverageItemList(inquiry, insId, coverItems));
		}else{
			req.setIsSY("N");
			req.setsYStartTm(null);
			req.setsYEndTm(null);
			req.setCvrgList(null);
		}
		
		//车型信息
		VehicleModel vehicleModel = new VehicleModel();
		vehicleModel.setVehicleName(inquiry.getVehicleName());
		vehicleModel.setVehiclePrice(inquiry.getVehiclePrice());
		vehicleModel.setModelCode(inquiry.getModelCode());
		vehicleModel.setVehicleSeat(inquiry.getSeatNum()+"");
		vehicleModel.setRealPrice(realPrice.toString());
		req.setModel(vehicleModel);
		
		//车辆信息
		QuotaReqCARDTO car = new QuotaReqCARDTO();
		car.setFstRegTm(inquiry.getFstRegNo());
		car.setEngNo(inquiry.getEngNo());
		car.setFrmNo(inquiry.getFrmNo());
		if("1".equals(inquiry.getTransferSign()) || null != inquiry.getTransferDate()){//是否过户
			car.setDeviceMrk("1");
			car.setDeviceTm(inquiry.getTransferDate());
		}else{
			car.setDeviceMrk("0");
		}
		car.setPlatNo(inquiry.getPlateNo());
		if(null != inquiry.getPlateNo() && inquiry.getPlateNo().indexOf("*") != -1){
			car.setNewFlag("0");//0：新车 1：旧车    选择新车未上牌一定是新车
			car.setPlatNo("*-*");
		}else{
			
			//车的登记日期跟初登日期和保险起期比较，等于或者超过9个月视为旧车
			int useMonths = useMouths(startDate, inquiry.getFstRegNo());
			if(useMonths > 9){
				car.setNewFlag("1"); 
			}else{
				car.setNewFlag("0");
			}
		}
		req.setCar(car);
		
		//车主 投保人 被保人
		req.setTbr(new QuotaReqTBRDTO(inquiry.getOwnerName(), inquiry.getOwnerCertNo(), "四川成都", "18888888888"));
		req.setBbr(new QuotaReqBBRDTO(inquiry.getOwnerName(), inquiry.getOwnerCertNo(), "四川成都", "18888888888"));
		req.setCz(new QuotaReqCZDTO(inquiry.getOwnerName(), inquiry.getOwnerCertNo(), "四川成都", "18888888888"));
		return req;
	}
	/**
	 * 报价返回结果整理
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @param quoteDTO
	 * @return
	 */
	private QuotaReturnDTO getQuotaRsp(QuotaReqDTO req, String userId, String inquiryId, String insId, QuoteBackMessageDTO quoteDTO){

		if(null == quoteDTO || quoteDTO.getQtnBase() == null){
			return new QuotaReturnDTO("error", "华安报价失败",insId);
		}
		
		//PSDERROR
		if("PSDERROR".equals(quoteDTO.getQtnBase().getErrorCode())){
			logger.error("华安登陆失败HACPHTTP-PSDERROR；登陆名为："+quoteDTO.getQtnBase().getUserName());
			return new QuotaReturnDTO("PSDERROR", "华安登录失败：平安登录密码错误", insId, quoteDTO.getQtnBase().getUserName());
		}
		if(!"200".equals(quoteDTO.getQtnBase().getErrorCode())){
			return new QuotaReturnDTO("error", "华安报价失败"+quoteDTO.getQtnBase().getErrorMsg(),insId);
		}
		QtnBaseDTO qtnBaseDTO = quoteDTO.getQtnBase();//基本信息
		ApplicationTCIDTO applicationTCIDTO = quoteDTO.getApplicationTCI();//交强险
		ApplicationVCIDTO applicationVCIDTO  = quoteDTO.getApplicationVCI();//商业险
		VehicleTaxationDTO vehicleTaxationDTO = quoteDTO.getVehicleTaxation();//车船税
		List<CoverageItemDTO> coverageItems = quoteDTO.getCoverageItems();//商业险种
		quoteDTO.getReInsureItem();//重复投保信息
		
		
		
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		BigDecimal totalCost = new BigDecimal("0.00");
		quotaReturnDTO.setInsId(insId);
		quotaReturnDTO.setQuotaId(qtnBaseDTO.getQtnID());
		quotaReturnDTO.setCalcSuccess(true);
		if("Y".equalsIgnoreCase(req.getIsJQ()) && null != vehicleTaxationDTO){
			quotaReturnDTO.setVehicleTax(vehicleTaxationDTO.getSumTax());//车船税
			totalCost = totalCost.add(vehicleTaxationDTO.getSumTax());
		}
		if("Y".equalsIgnoreCase(req.getIsJQ()) && null != applicationTCIDTO){
			quotaReturnDTO.setPremTCITax(applicationTCIDTO.getExpectPrm());//交强险
			totalCost = totalCost.add(applicationTCIDTO.getExpectPrm());
		}
		if("Y".equalsIgnoreCase(req.getIsSY()) && null != applicationVCIDTO){
			quotaReturnDTO.setPremVCITax(applicationVCIDTO.getExpectPrm());//商业险
			totalCost = totalCost.add(applicationVCIDTO.getExpectPrm());
		}
		quotaReturnDTO.setTotalCost(totalCost);//总保费
		if(null != applicationVCIDTO.getDiscount()){//折扣
			quotaReturnDTO.setDiscount(applicationVCIDTO.getDiscount());
		}
		
		
		//处理险别信息
		if(CollectionUtils.isNotEmpty(coverageItems)){
			com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO coverageItem = null;
			CorrespondDTO correspondDTOR = null;
			List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverageItemList = new ArrayList<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO>(coverageItems.size());
			CorrespondDTO correspondDTO = new CorrespondDTO(insId, BaseFinal.INSURANCETYPECODE, null,null);
			for(CoverageItemDTO item : coverageItems){
				coverageItem = new com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO();
				correspondDTO.setIns_code(item.getCoverageCode());//华安保险公司险别码
				try {
					correspondDTOR = corrService.get(correspondDTO);
					if(correspondDTOR != null && null != correspondDTOR.getCode()){
						coverageItem.setCode(correspondDTOR.getCode());//平台保险公司险别码
					}else{
						if("030119".equals(item.getCoverageCode())){//不计免赔险
							coverageItem.setCode("00000");//平台保险公司险别码
						}else{
							continue;
						}
					}
				} catch (BusinessServiceException e) {
					logger.error("华安报价解析查询险种失败，险种编码为："+item.getCoverageCode(),e);
				}
				
				//保额
				coverageItem.setSumLimit(item.getSumLimit());
				
				//保费 
				coverageItem.setAmount(item.getExpectPrm());
				
				coverageItemList.add(coverageItem);
			}
			quotaReturnDTO.setCoverageItems(coverageItemList);
		}
		return quotaReturnDTO;
	}
	//处理险种
	private List<QuotaCvrgReq> opetateCoverageItemList(InquiryDTO inquiry, String insId, List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverItems){
		CorrespondDTO itm = null;
		List<QuotaCvrgReq> coverageItemList = new ArrayList<QuotaCvrgReq>(coverItems.size());
		QuotaCvrgReq cvrgReq = null;
		String glsType = null;
		for (com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO item : coverItems) {
			itm = new CorrespondDTO(insId,BaseFinal.INSURANCETYPECODE,item.getCode());//初始化保险公司代码
			try {
				
				//保险公司代码
				itm = corrService.get(itm);
				
				if(null != itm){
					cvrgReq = new QuotaCvrgReq();
					cvrgReq.setRiskCode(itm.getIns_code());//险种代码
					if("030107".equals(cvrgReq.getRiskCode())){//防弹玻璃  0：否 1：是
						glsType = item.getGlsType();//玻璃类型  1：国产 2进口
						if(null != glsType){
							if("1".equals(glsType)){
								cvrgReq.setGlsType("0");
							}
							if("2".equals(glsType)){
								cvrgReq.setGlsType("1");
							}
						}
					}
					if(null != item.getAmount()){
						cvrgReq.setBaseAmount(item.getAmount().toString());//险种金额
					}else{
						cvrgReq.setBaseAmount(inquiry.getVehiclePrice());//险种金额
					}
					
					if("030105".equals(cvrgReq.getRiskCode())){//乘客险
						cvrgReq.setNYl3((inquiry.getSeatNum() - 1)+"");
						cvrgReq.setAmount(item.getSumLimit().divide(new BigDecimal(inquiry.getSeatNum() - 1),1, BigDecimal.ROUND_UP)+"");//保额
					}else{
						if(null != item.getSumLimit()){
							cvrgReq.setAmount(item.getSumLimit().toString());//保额
						}else{
							cvrgReq.setAmount(inquiry.getRealPrice());//保额
						}
					}
					
					cvrgReq.setDuctMrk(item.getNoDduct()+"");//不计免赔
					coverageItemList.add(cvrgReq);
				}
			} catch (BusinessServiceException e) {
				logger.error("华安报价处理处理险种" + e);
			}
		}
		
		return coverageItemList;
	}
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
			int day1 = calendar.get(Calendar.DAY_OF_YEAR);
			
			calendar.setTime(date);
			int year2 = calendar.get(Calendar.YEAR);
			int mouth2 = calendar.get(Calendar.MONTH);
			int day2 = calendar.get(Calendar.DAY_OF_YEAR);
			
			/*如果 保险起期-初登日期 <= 7天，则不需折旧;*/
			if(year1 == year2 && day2 - day1 <= 7){
				return 0;
			}
			useMouths = (year1 - year2)*12 + (mouth1-mouth2);
		}
		return useMouths;
	}
	
	private static boolean notEmpty(String s) {
		return s != null && s.length() != 0;
	}
	
	public static String calcNActualValue(String CFstRegYm, String SYTInsrncBgnTm, String NNewPurchaseValue) {
			
			//相差的月份
			int monthDiff = getSubMonth( CFstRegYm, SYTInsrncBgnTm);
			if(monthDiff < 0){
				monthDiff = 0;
			}
			
			//新车购置价
			double newPurchaseVal = Double.parseDouble(NNewPurchaseValue);
			
			//根据折旧代码取折旧率值
			Double despRateVal = 0.6;
			
			double rate = monthDiff * despRateVal / 100;
			if(rate > 0.8)
				rate = 0.8;
			
			//折旧后的实际价值
			double actualVal = newPurchaseVal - rate * newPurchaseVal;
			
			return new BigDecimal(actualVal).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}
	
	/**
	 * 计算初登日期离保险起期相差的月数
	 * 算法参考保险公司vhl_offer_app.js文件中的getSubMonth
	 * @param CFstRegYm
	 * @param SYTInsrncBgnTm
	 * @return
	 */
	public static int getSubMonth(String CFstRegYm, String SYTInsrncBgnTm) {
		
		//初登日期离保险起期相差的月数
		int monthNum = 0; 
		
		if(notEmpty( SYTInsrncBgnTm) && notEmpty(CFstRegYm) ) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date t1 = fmt.parse(CFstRegYm); //初登日期 <= 保险起期
				Date t2 = fmt.parse(SYTInsrncBgnTm); //保险起期
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(t1);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(t2);
				
				int y1 = cal1.get(Calendar.YEAR);
				int y2 = cal2.get(Calendar.YEAR);
				int m1 = cal1.get(Calendar.MONTH);
				int m2 = cal2.get(Calendar.MONTH);
				int d1 = cal1.get(Calendar.DATE);
				int d2 = cal2.get(Calendar.DATE);
				monthNum = (y2 - y1) * 12 + m2 - m1;
				
				//如果保险起期不是当月最后一天,且日期比初登日期的日期小,则月数差-1
				//这个算法其实是有问题的,比如2016-02-29 到 2016-03-30其实不满一个月,依然能算出结果为1
				//但保险公司就是这样算的,以其为准
				int lastDay = cal2.getActualMaximum(Calendar.DATE);
				if(!(d2 == lastDay) && (d2 - d1) < 0 ) {
					monthNum -=1;
				}
				
			} catch (ParseException e) {
				throw new IllegalArgumentException(e.getMessage(),e);
			}
		}
		
		return monthNum;
	}
//	private static int useMouths(Date now, Date date){
//		
//		int useMouths = 0;
//		long curr = System.currentTimeMillis();
//		if(null != now){
//			curr = now.getTime();
//		}
//		if(null != date){
//			long base = date.getTime();
//			long result = Math.abs(curr - base);
//			useMouths = (int) (result/(2592000000l));
//		}
//		return useMouths;
//	}
}
