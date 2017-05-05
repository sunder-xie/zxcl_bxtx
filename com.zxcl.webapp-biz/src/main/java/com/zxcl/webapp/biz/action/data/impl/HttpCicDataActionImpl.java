package com.zxcl.webapp.biz.action.data.impl;

import java.math.BigDecimal;
import java.text.ParseException;
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
import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.action.data.HttpCicDataAction;
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
import com.zxcl.zhonghua_intf.dto.Applicant;
import com.zxcl.zhonghua_intf.dto.ApplyInfo;
import com.zxcl.zhonghua_intf.dto.Base;
import com.zxcl.zhonghua_intf.dto.ClientUser;
import com.zxcl.zhonghua_intf.dto.Cvrg;
import com.zxcl.zhonghua_intf.dto.Insured;
import com.zxcl.zhonghua_intf.dto.Vhl;
import com.zxcl.zhonghua_intf.dto.Vhlowner;
import com.zxcl.zhonghua_intf.dto.quoteresult.QuoteBackMessageDTO;

@Service
public class HttpCicDataActionImpl implements HttpCicDataAction{

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
	
//	@Autowired
//	private CntaipingAction cntaipingAction;

	@Autowired
	private CorrespondService corrService;
	
	@Autowired
	private CallAction callAction;
	
	@Override
	public ApplyInfo quota(String userId, InsuranceDTO insuranceDTO,
			String inquiryId,Map<String,Object> configMap) throws ActionException {
		logger.info("中华报价入参   用户ID："+userId+"  InsuranceDTO："+insuranceDTO+"   询价单号："+inquiryId);
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
		
		
//		//顶级保险公司
//		InsuranceDTO insTop = new InsuranceDTO();
//		try {
//			insTop = insuranceService.getTop(insuranceDTO.getInsId());
//		} catch (BusinessServiceException e) {
//			logger.error("中华报价接口数据组装查询顶级保险公司失败", e);
//			throw new ActionException("系统异常");
//		}
//		Map<String,Object> map = new HashMap<String, Object>();
//		String[] xml = new String[]{};
//		try {
//			xml = insXmlService.getBackXml(inquiryId, "TPIC", "1");
//		} catch (BusinessServiceException e) {
//			logger.error("中华报价接口数据组装查询车型报文失败", e);
//			throw new ActionException("获取车型失败");
//		}
		String modelCode = "";
		try {
//			if(xml.length > 0){
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
//				map = cntaipingAction.analysisVehicle(xml[0], inquiryDTO.getVehiclePrice(),marketTimestamp);
//			}else{
//				logger.error("太平车型查询未得到报文");
//			}
		} catch (Exception e) {
			logger.error("中华报价接口数据组装解析车型报文失败", e);
			throw new ActionException("获取车型失败");
		}
		
		//查询险种集合	
		List<CoverageItemDTO> coverItems = new ArrayList<CoverageItemDTO>();
		try {
			coverItems = coverService.getCoverageItems(inquiryId,
					microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("中华报价接口数据组装查询险种集合失败", e);
			throw new ActionException("系统异常");
		}
		// 处理车牌号
		inquiryDTO.setPlateNo(StringUtils.isBlank(inquiryDTO.getPlateNo())
				|| inquiryDTO.getPlateNo().length() != 7 ? null : inquiryDTO.getPlateNo());
		
		//保险公司ID
		String insId = insuranceDTO.getInsId();
		
		
		ApplyInfo info = new ApplyInfo();
		
//		info.setFrontQtnId(null != configMap.get(insId+"_FrontQtnId") ? configMap.get(insId+"_FrontQtnId").toString():""); //前端的报价ID,必填; 传过来做数据关联使用
		info.setFrontQtnId("CICP"+DateUtil.dateToString("yyyyMMddHHmmssSSS",new Date()));
		
		ClientUser user = new ClientUser();//必填
		user.setAgentUserId(CommonUtil.valueOf(configMap.get(insId+"_AgentUserId")));//必填
		user.setAgentUserPwd(CommonUtil.valueOf(configMap.get(insId+"_AgentUserPwd")));//必填
//		user.setXiaoweiUserId(null != configMap.get(insId+"_XiaoweiUserId") ? configMap.get(insId+"_XiaoweiUserId").toString():"");//必填
		user.setXiaoweiUserId(userId);
		info.setClientUser(user);
		
		//基本信息
		Base base = new Base();
		info.setBase(base);
		
		//基本信息,必填
		String cprodNoV = "";//商业
		String cprodNoT = "";//交强
		String cprodNo = "";//联合投保中间连接符
		if("1".equals(inquiryDTO.getVciSign())){//商业险
			cprodNoV = "0360";
		}
		if("1".equals(inquiryDTO.getTciSign())){//交强险
			cprodNoT = "0332";
		}
		if("1".equals(inquiryDTO.getVciSign()) && "1".equals(inquiryDTO.getTciSign())){//联合投保
			cprodNo = "_";
		}
		base.setCProdNo(cprodNoV+cprodNo+cprodNoT); //产品代码,必填, 商业险0360,交强险0332,联合投保 0360_0332
		
		base.setCDptCde(CommonUtil.valueOf(configMap.get(insId+"_CDptCde"))); //机构,账号相关
		base.setCNewChaType(CommonUtil.valueOf(configMap.get(insId+"_CNewChaType"))); //渠道类型,账号相关, 当前测试账号为 A01综合渠道
		base.setCNewBsnsTyp(CommonUtil.valueOf(configMap.get(insId+"_CNewBsnsTyp"))); //新业务来源,账号相关, 看出单员具体是哪种代理类型; A0106为专业代理
//		base.setCOprCde(CommonUtil.valueOf(configMap.get(insId+"_COprCde"))); //账号相关
		base.setCOprCde(CommonUtil.valueOf(configMap.get(insId+"_AgentUserId")));
		
		//代理相关,如果业务来源为代理,则需要提供代理信息; 如果是直接业务,无需填写
		base.setCAgtAgrNo(CommonUtil.valueOf(configMap.get(insId+"_CAgtAgrNo"))); // 协议号,账号相关 
		base.setCBrkrCde(CommonUtil.valueOf(configMap.get(insId+"_CBrkrCde"))); //代理人编码,账号相关
		base.setCBrkrName(CommonUtil.valueOf(configMap.get(insId+"_CBrkrName")));//代理人名称,账号相关
		base.setCSlsId(CommonUtil.valueOf(configMap.get(insId+"_CSlsId"))); //业务员工号,代理业务需要,账号相关
		base.setCSlsNme(CommonUtil.valueOf(configMap.get(insId+"_CSlsNme"))); //业务员名称,代理业务需要,账号相关
		base.setCTeamCode(CommonUtil.valueOf(configMap.get(insId+"_CTeamCode"))); //团队代码,账号相关
		base.setCTeamName(CommonUtil.valueOf(configMap.get(insId+"_CTeamName"))); //团队名称,账号相关
		base.setCServiceCode(CommonUtil.valueOf(configMap.get(insId+"_CServiceCode"))); //服务代码,账号相关,代理需要
		//代理相关结束
		
		//投保日期yyyy-mm-dd,可取当前时间 必填
		base.setTAppTm(DateUtil.dateToString("yyyy-MM-dd", new Date())); 
		//签单日期,yyyy-mm-dd,取当前时间 必填
		base.setTOprTm(DateUtil.dateToString("yyyy-MM-dd", new Date())); 
		
		
		//车辆信息,必填
		Vhl vhl = new Vhl();
		info.setVhl(vhl);
		

		Date date = new Date();
		if(StringUtils.isNotBlank(inquiryDTO.getVciStartDateStr())){
			date = inquiryDTO.getVciStartDate();
		}else{
			date = inquiryDTO.getTciStartDate();
		}
		
		int carAge = CommonUtil.getYearAge(inquiryDTO.getFstRegNo(), date);
		String cCarAge = "";
		if(carAge < 1){
			cCarAge = "306001";
		}else if(1 <= carAge && carAge < 2){
			cCarAge = "306002";
		}else if(2 <= carAge && carAge < 3){
			cCarAge = "306003";
		}else if(3 <= carAge && carAge < 4){
			cCarAge = "306004";
		}else if(4 <= carAge && carAge < 6){
			cCarAge = "306005";
		}else if(6 <= carAge){
			cCarAge = "306007";
		}
		vhl.setCCarAge(cCarAge); //车龄,参考文档                            
//		vhl.setCRegVhlTyp("K33"); //交管车辆种类 K33轿车  这个地方暂时由人保接口写死为K33
		vhl.setCDevice1Mrk(StringUtils.isNotBlank(inquiryDTO.getTransferSign()) ? inquiryDTO.getTransferSign() : "0"); //车辆过户 1/0
		vhl.setCRegVhlTyp(inquiryDTO.getTransferDateStr());//这个地方临时处理,就不更新jar了
		vhl.setCEcdemicMrk("0"); //是否外地车标志 0否, 1跨省 2跨地市
		vhl.setCEngNo(inquiryDTO.getEngNo()); //发动机号
		vhl.setCFrmNo(inquiryDTO.getFrmNo()); //车架号 17位
		vhl.setCFstRegYm(inquiryDTO.getFstRegNoStr()); //初登日期
		vhl.setCNewMrk(inquiryDTO.getNewCarSign()); //是否新车 1/0
		vhl.setCNewVhlFlag(StringUtils.isNotBlank(inquiryDTO.getPlateNo()) ? "1" : "0"); //是否上牌 1/0
		vhl.setCPlateNo(StringUtils.isNotBlank(inquiryDTO.getPlateNo()) ? inquiryDTO.getPlateNo() : "*-*"); //车牌,如果未上牌,传"*-*"
		vhl.setCPlateTyp("02"); //号牌种类 固定小型汽车 02
		vhl.setCResvTxt6("11");//11 非跑车, 12 跑车
		
//		vhl.setCBillDate("LFMAU92A880008650");
		
		//车型信息,必填
		vhl.setCModelCde(modelCode); //车型编码-精友
		vhl.setCLoanVehicleFlag("0"); //车贷投保多年标志 0/1
		vhl.setCModelNme(inquiryDTO.getVehicleName()); //车型名称-精友
		
		//投保人信息
		Applicant app = new Applicant();
		info.setApplicant(app);
		app.setCAppNme(inquiryDTO.getOwnerName());
		
		Insured ins = new Insured();
		ins.setCInsuredNme(inquiryDTO.getOwnerName());
		info.setInsured(ins);
		
		//车主信息
		Vhlowner owner = new Vhlowner();
		info.setVhlowner(owner);
		int y = CommonUtil.getSex(inquiryDTO.getOwnerCertNo());
		owner.setCGender(y%2 == 1 ? "1061" : "1062");//性别 1061男  1062女
		//车主年龄
		Date ownerBirthday = new Date();
		try {
			ownerBirthday = CommonUtil.getBrith(inquiryDTO.getOwnerCertNo());
		} catch (ParseException e) {
			logger.error("获取车主出生年月失败",e);
			throw new ActionException("系统异常");
		}
		
		int ownerAge = CommonUtil.getBetweenYear(ownerBirthday, date);
		//车主年龄档次
		String cOwnerAge = "";
		if(ownerAge < 18){
			cOwnerAge = "341060";
		}else if(18 <= ownerAge && ownerAge < 20){
			cOwnerAge = "341061";
		}else if(20 <= ownerAge && ownerAge < 25){
			cOwnerAge = "341062";
		}else if(25 <= ownerAge && ownerAge < 30){
			cOwnerAge = "341063";
		}else if(30 <= ownerAge && ownerAge < 35){
			cOwnerAge = "341064";
		}else if(35 <= ownerAge && ownerAge < 40){
			cOwnerAge = "341065";
		}else if(40 <= ownerAge && ownerAge < 45){
			cOwnerAge = "341066";
		}else if(45 <= ownerAge && ownerAge < 50){
			cOwnerAge = "341067";
		}else if(50 <= ownerAge && ownerAge < 55){
			cOwnerAge = "341068";
		}else if(55 <= ownerAge && ownerAge < 60){
			cOwnerAge = "341069";
		}else if(60 < ownerAge){
			cOwnerAge = "341070";
		}else{
			cOwnerAge = "341071";
		}
		owner.setCOwnerAge(cOwnerAge); //车主年龄档次
		owner.setCOwnerNme(inquiryDTO.getOwnerName()); //车主名称
		
		//险别信息
		List<Cvrg> cvrgList = new ArrayList<Cvrg>();
		info.setCvrgList(cvrgList);
		int count = 1;
		if("1".equals( inquiryDTO.getVciSign())){//商业险
			
			//商业期限,如果投保了商业险,必填
			base.setSYTInsrncBgnTm(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getVciStartDate())+" 00:00:00");
			base.setSYTInsrncEndTm(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getVciEndDate())+" 23:59:59");
				for (CoverageItemDTO coverageItemDTO: coverItems) {
					Cvrg cvrg = new Cvrg();
					try {
						CorrespondDTO correspondDTO = corrService.get(new CorrespondDTO(insId, BaseFinal.INSURANCETYPECODE, coverageItemDTO.getCode()));
						//险别代码
						cvrg.setCCvrgNo(correspondDTO.getIns_code());
						java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");
						if("030009".equals(coverageItemDTO.getCode())){//乘客险
							count = inquiryDTO.getSeatNum() - 1;
							//车上人员责任险,司机/乘客 人数;  修理期间费用补偿 天数
							cvrg.setNLiabDaysLmt(count+"");
							cvrg.setNPerAmt(coverageItemDTO.getSumLimit() == null ? "" : myformat.format(coverageItemDTO.getSumLimit().intValue()/count));
						}
						if("030001".equals(coverageItemDTO.getCode())){//司机险
							//车上人员责任险,司机/乘客 人数;  修理期间费用补偿 天数
							cvrg.setNLiabDaysLmt("1");
							cvrg.setNPerAmt(coverageItemDTO.getSumLimit() == null ? "" : myformat.format(coverageItemDTO.getSumLimit().intValue()/count));
						}
						if("030006".equals(coverageItemDTO.getCode())){//车损险
							//车损险专用, 车损险免赔额
							cvrg.setNDeductible("0");
						}
						if("030018".equals(coverageItemDTO.getCode())){//三者险
							int sumLimit = coverageItemDTO.getSumLimit().intValue();
							//三者险保额,等于选择的档次的保额
							String cIndemLmtLvl = "";
							if(50000 == sumLimit){
								cIndemLmtLvl = "306006004";
							}else if(10000 == sumLimit){
								cIndemLmtLvl = "306006005";
							}else if(150000 == sumLimit){
								cIndemLmtLvl = "306006010";
							}else if(200000 == sumLimit){
								cIndemLmtLvl = "306006006";
							}else if(300000 == sumLimit){
								cIndemLmtLvl = "306006007";
							}else if(500000 == sumLimit){
								cIndemLmtLvl = "306006009";
							}else if(1000000 == sumLimit){
								cIndemLmtLvl = "306006014";
							}else if(1500000 == sumLimit){
								cIndemLmtLvl = "306006016";
							}else if(2000000 == sumLimit){
								cIndemLmtLvl = "306006017";
							}else if(2000000 < sumLimit){
								cIndemLmtLvl = "306006018";
							}
							cvrg.setCIndemLmtLvl(cIndemLmtLvl);
							cvrg.setNIndemLmt(coverageItemDTO.getSumLimit() == null ? "" : myformat.format(coverageItemDTO.getSumLimit().intValue()/count));
						}
						if("030004".equals(coverageItemDTO.getCode())){//玻璃险		
							cvrg.setCResvTxt30("1".equals(coverageItemDTO.getGlsType()) ? "303011001":"303011002");
						}
						if("032618".equals(coverageItemDTO.getCode())){//指定修理厂险		
							String str = inquiryDTO.getFrmNo().substring(0, 1);
							cvrg.setNRate("L".equals(str) ? "0.1":"0.15");
						}
						//保额
						cvrg.setNAmt(coverageItemDTO.getSumLimit() == null ? "" : myformat.format(coverageItemDTO.getSumLimit().intValue()/count));
						if("030012".equals(coverageItemDTO.getCode()) || "030006".equals(coverageItemDTO.getCode()) || "030061".equals(coverageItemDTO.getCode())){//自然险，车损险，全车盗抢险
							cvrg.setNAmt("");
						}
						if("032601".equals(coverageItemDTO.getCode())){//划痕险	
							
							int sumLimit = coverageItemDTO.getSumLimit().intValue();
							//三者险保额,等于选择的档次的保额
							String cIndemLmtLvl = "";
							if(2000 == sumLimit){
								cIndemLmtLvl = "N03001001";
							}else if(5000 == sumLimit){
								cIndemLmtLvl = "N03001002";
							}else if(10000 == sumLimit){
								cIndemLmtLvl = "N03001003";
							}else if(20000 == sumLimit){
								cIndemLmtLvl = " N03001004";
							}
							
							cvrg.setCIndemLmtLvl(cIndemLmtLvl);
							cvrg.setNIndemLmt(coverageItemDTO.getSumLimit() == null ? "" : myformat.format(coverageItemDTO.getSumLimit().intValue()/count));
						}
						//是否不计免赔
						cvrg.setCDductMrk("1".equals(coverageItemDTO.getNoDduct()+"") ? "369003":"369004"); 
						cvrgList.add(cvrg);
					} catch (BusinessServiceException e) {
						logger.error("中华报价接口数据组装险种代码失败，险种代码为："+coverageItemDTO.getCode(), e);
						throw new ActionException("系统异常");
					}
					count = 1;
				}
		}
		if("1".equals( inquiryDTO.getTciSign())){//交强险	
			
			//交强期限,如果投保了交强险,必填
			base.setJQTInsrncBgnTm(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getTciStartDate())+" 00:00:00"); //交强起期
			base.setJQTInsrncEndTm(DateUtil.dateToString("yyyy-MM-dd", inquiryDTO.getTciEndDate())+" 23:59:59"); //交强止期		
			//交强险
			Cvrg cvrg = new Cvrg();
			cvrg.setCCvrgNo("033201"); //险别代码
			cvrg.setNAmt("122000"); //保额,交强险固定
			cvrgList.add(cvrg);
		}
		return info;
	}

	@Override
	public QuotaReturnDTO quotaReturn(QuoteBackMessageDTO result,String insId)
			throws ActionException {
		logger.info("中华报价返回入参   QuoteBackMessageDTO："+result+"   保险公司ID："+insId);
//		//顶级保险公司信息
//		InsuranceDTO insTopDTO = new InsuranceDTO();
//		try {
//			insTopDTO = insuranceService.getTop(insId);
//		} catch (BusinessServiceException e) {
//			logger.error("中华报价返回数据组装获取顶级保险公司失败",e);
//			return new QuotaReturnDTO("error","系统异常",insId);
//		}
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		//保险公司ID
		quotaReturnDTO.setInsId(insId);
		//返回的code
		quotaReturnDTO.setErrorCode(result.getQtnBase().getErrorCode());
		//返回的信息
		quotaReturnDTO.setErrorMessages(result.getQtnBase().getErrorMsg());
//		//报价单号
//		quotaReturnDTO.setQuotaId("CICP"+DateUtil.dateToString("yyyyMMddHHmmssSSS",new Date()));
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
		for(com.zxcl.zhonghua_intf.dto.quoteresult.CoverageItemDTO coverageItemDTO : result.getCoverageItems()){
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
