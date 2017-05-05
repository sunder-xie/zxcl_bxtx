package com.zxcl.webapp.biz.action.call.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bxtx.tp.intf.biz.action.TPIntfAction;
import com.bxtx.tp.intf.dto.initDTO.AgentDTO;
import com.bxtx.tp.intf.dto.initDTO.QuotnCoverageItemDTO;
import com.bxtx.tp.intf.dto.initDTO.QuotnDTO;
import com.bxtx.tp.intf.dto.initDTO.QuotnInsuredDTO;
import com.bxtx.tp.intf.dto.initDTO.QuotnPolicyDTO;
import com.bxtx.tp.intf.dto.initDTO.QuotnVhlDTO;
import com.bxtx.tp.intf.dto.initDTO.QuotnVhlOwnerDTO;
import com.bxtx.tp.intf.dto.initDTO.QuotnlicantDTO;
import com.bxtx.tp.intf.dto.responseDTO.ApplicationTCIDTO;
import com.bxtx.tp.intf.dto.responseDTO.ApplicationVCIDTO;
import com.bxtx.tp.intf.dto.responseDTO.CoverageItemDTO;
import com.bxtx.tp.intf.dto.responseDTO.QtnBaseDTO;
import com.bxtx.tp.intf.dto.responseDTO.QuoteBackMessageDTO;
import com.bxtx.tp.intf.dto.responseDTO.ReInsureItemDTO;
import com.bxtx.tp.intf.dto.responseDTO.VehicleTaxationDTO;
import com.zxcl.bxtx.call.InsuranceAPI;
import com.zxcl.bxtx.dto.intf.VehicleReturnDTO;
import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.action.call.HTTPTaiPingAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.CorrespondService;
import com.zxcl.webapp.biz.service.CoverageItemService;
import com.zxcl.webapp.biz.service.DriverService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsXmlService;
import com.zxcl.webapp.biz.service.InsuranceCompanyConfigService;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.util.BaseFinal;
import com.zxcl.webapp.dto.CorrespondDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.util.SpringContextUtil;
/**
 * 
 * @author daitao
 *
 */
@Service()
public class HTTPTaiPingActionImpl implements HTTPTaiPingAction {
	
	Logger logger=Logger.getLogger(this.getClass());

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
	private TPIntfAction tpIntfService_remote;
	
	@Autowired
	private InsXmlService insXmlService;
	
//	@Autowired
//	private CntaipingAction cntaipingAction;
	
	
	@Autowired
	private InsuranceCompanyConfigService configService;
	
	@Autowired
	private CallAction callAction;
	
	@Override
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId)
			throws ActionException {
		
		QuoteBackMessageDTO calcPremium =null;
			try {
				 AgentDTO configByAgentDTO = getConfigByAgentDTO(insId, userId);
				 calcPremium = tpIntfService_remote.calcPremium(getQuotaReq(userId, inquiryId, insId), configByAgentDTO);
				 logger.info("得到报价结果"+calcPremium);
			} catch (Exception e) {
				logger.error("太平报价失败", e);
				return new QuotaReturnDTO("error", "太平报价失败:" + e.getMessage(),insId);
			}
		QuotaReturnDTO quotaRsp = getQuotaRsp(userId, inquiryId, insId, calcPremium);
		return quotaRsp ;
	}



	/**
	 * 报价返回结果整理
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @param quoteDTO
	 * @return
	 */
	private QuotaReturnDTO getQuotaRsp(String userId, String inquiryId, String insId, QuoteBackMessageDTO quoteDTO){
		logger.info("太平报价结果处理......");
		if(null == quoteDTO || quoteDTO.getQtnBase() == null){
			return new QuotaReturnDTO("error", "太平报价失败",insId);
		}
		//PSDERROR
		if("PSDERROR".equals(quoteDTO.getQtnBase().getErrorCode())){
			//TODO 短信提示
			logger.error("太平登录失败HACPHTTP-PSDERROR；登陆名为："+quoteDTO.getQtnBase().getUserName());
			return new QuotaReturnDTO("PSDERROR","密码错误",insId,quoteDTO.getQtnBase().getUserName());
		}
		if(!"200".equals(quoteDTO.getQtnBase().getErrorCode())){
			return new QuotaReturnDTO("error", "太平报价失败"+quoteDTO.getQtnBase().getErrorMsg(),insId);
		}
		
		
		QtnBaseDTO qtnBaseDTO = quoteDTO.getQtnBase();//基本信息
		ApplicationTCIDTO applicationTCIDTO = quoteDTO.getApplicationTCI();//交强险
		ApplicationVCIDTO applicationVCIDTO  = quoteDTO.getApplicationVCI();//商业险
		VehicleTaxationDTO vehicleTaxationDTO = quoteDTO.getVehicleTaxation();//车船税
		List<CoverageItemDTO> coverageItems = quoteDTO.getCoverageItems();//商业险种
		ReInsureItemDTO reInsureItem = quoteDTO.getReInsureItem();//重复投保信息
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		//重复投保信息
		String reInsureInfo="";
		if (reInsureItem.isVciReInsure()) {
			SimpleDateFormat simp=new SimpleDateFormat("yyyy-MM-dd");
			String victiem = simp.format(reInsureItem.getExpireDate());
			reInsureInfo += "商业险重复投保，最新保单止期：" + victiem+ "。";
		}
		if (reInsureItem.isTciReInsure()) {
			SimpleDateFormat simp=new SimpleDateFormat("yyyy-MM-dd");
			String victiem = simp.format(reInsureItem.getExpireDate());
			reInsureInfo += "交强险重复投保，最新保单止期：" +victiem  + "。";
		}
		quotaReturnDTO.setReInsureInfo(reInsureInfo);
		logger.info("折扣率============="+applicationVCIDTO.getDiscount());
		quotaReturnDTO.setDiscount(applicationVCIDTO.getDiscount());
		BigDecimal totalCost = new BigDecimal(0.00);
		quotaReturnDTO.setInsId(insId);
		quotaReturnDTO.setQuotaId(qtnBaseDTO.getQtnID());
		quotaReturnDTO.setCalcSuccess(true);
		if(null != vehicleTaxationDTO){
			quotaReturnDTO.setVehicleTax(vehicleTaxationDTO.getSumTax());//车船税
			totalCost.add(vehicleTaxationDTO.getSumTax());
		}
		if(null != applicationTCIDTO){
			quotaReturnDTO.setPremTCITax(applicationTCIDTO.getExpectPrm());//交强险
			totalCost.add(applicationTCIDTO.getExpectPrm());
		}
		if(null != applicationVCIDTO){
			quotaReturnDTO.setPremVCITax(applicationVCIDTO.getExpectPrm());//商业险
			totalCost.add(applicationVCIDTO.getExpectPrm());
		}
		quotaReturnDTO.setTotalCost(totalCost);//总保费
		
		//处理险别信息
		logger.info("太平报价处理险别信息......"+coverageItems);
		if(CollectionUtils.isNotEmpty(coverageItems)){
			com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO coverageItem = null;
			CorrespondDTO correspondDTOR = null;
			List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverageItemList = new ArrayList<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO>(coverageItems.size());
			CorrespondDTO correspondDTO = new CorrespondDTO(insId, BaseFinal.INSURANCETYPECODE, null,null);
			for(CoverageItemDTO item : coverageItems){
				coverageItem = new com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO();
				correspondDTO.setIns_code(item.getCoverageCode());//太平保险公司险别码
				try {
					correspondDTOR = corrService.get(correspondDTO);
					if(correspondDTOR != null){
						coverageItem.setCode(correspondDTOR.getCode());//平台保险公司险别码
						if("63".equals(item.getCoverageCode())){//不计免赔险
							coverageItem.setCode("00000");//平台保险公司险别码
						}
					}
				} catch (BusinessServiceException e) {
					logger.error("太平报价解析查询险种失败，险种编码为："+item.getCoverageCode(),e);
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


	private QuotnDTO getQuotaReq(String userId, String inquiryId, String insId) throws Exception {
		// TODO Auto-generated method stub
		QuotnDTO req = new QuotnDTO();
		//小薇
		MicroDTO microDTO = null;
		try {
			microDTO = microService.getMicroByUserId(userId);
		} catch (BusinessServiceException e) {
			logger.error("太平报价接口数据组装小微查询失败", e);
			throw new ActionException("太平报价接口数据组装小微查询失败");
		}
		
		//询价单
		InquiryDTO inquiry = null;
		try {
			inquiry = inquiryService.get(inquiryId, microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("太平报价接口数据组装询价单查询失败", e);
			throw new ActionException("太平报价接口数据组装询价单查询失败");
		}
		
		List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverItems = null;
		try {
			coverItems = coverService.getCoverageItems(inquiryId, microDTO.getMicro_id());
			
		} catch (BusinessServiceException e) {
			logger.error("太平报价接口数据组装处理险种失败", e);
			throw new ActionException("太平报价接口数据组装处理险种失败");
		}
		
		
//		Map<String,Object> map = new HashMap<String, Object>();
//		String[] xml = new String[]{};
//		try {
//			xml = insXmlService.getBackXml(inquiryId, "TPIC", "1");
//			logger.debug("xml:"+xml);
//		} catch (BusinessServiceException e) {
//			logger.error("太平报价接口数据组装查询车型报文失败", e);
//			throw new ActionException("太平查询车型报文失败");
//		}
		String modelCode = "";
		try {
			String marketTimestamp = "";
			String remark = inquiry.getRemark();
			if(StringUtils.isNotBlank(remark)){
				int count = remark.indexOf("款 ");
				if(count > 0){
					marketTimestamp = remark.substring(count-4,count);
				}
			}
			InsuranceAPI insuranceAPI = (InsuranceAPI) SpringContextUtil.getBean("TPICInsuranceAPI");
			List<VehicleReturnDTO> vehicles = insuranceAPI.vhlQuery(inquiry.getVehicleName()).getVehicleReturnList();
			for (VehicleReturnDTO vehicleModelDTO : vehicles) {
				if(0 == new BigDecimal(vehicleModelDTO.getVehiclePrice()).compareTo(new BigDecimal(inquiry.getVehiclePrice()))){
					if(StringUtils.isNotBlank(vehicleModelDTO.getMarketDate()) && StringUtils.isNotBlank(marketTimestamp)){
						if(vehicleModelDTO.getMarketDate().equals(marketTimestamp)){
							modelCode = vehicleModelDTO.getModelCode();
						}
					}else{
						modelCode = vehicleModelDTO.getModelCode();
					}
				}
			}
//			map = cntaipingAction.analysisVehicle(xml[0], inquiry.getVehiclePrice(),marketTimestamp);
		} catch (Exception e) {
			logger.error("太平报价接口数据组装解析车型报文失败", e);
			throw new ActionException("解析车型报文失败");
		}
		
//		String modelCode = null != map.get("modelCode") ? map.get("modelCode").toString() : "";
		
		if(StringUtils.isBlank(modelCode)){
			logger.error("车型编码解析为空");
			throw  new  ActionException("太平车型编码解析为空");
		}
		logger.debug("modelCode:"+modelCode);
	// 保单信息	
		QuotnPolicyDTO quotnPolicyDTO = new QuotnPolicyDTO();
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		//操作日期
		quotnPolicyDTO.setInputTime(sFormat.format(new Date()));
		quotnPolicyDTO.setOperateDate(sFormat.format(new Date()));
		//交强险起止日期
		quotnPolicyDTO.setTciStartDate(sFormat.format(inquiry.getTciStartDate()));
		quotnPolicyDTO.setTciEndDate(sFormat.format(inquiry.getTciEndDate()));
		//商业险起止日期
		quotnPolicyDTO.setVciStartDate(sFormat.format(inquiry.getVciStartDate()));
		quotnPolicyDTO.setVciEndDate(sFormat.format(inquiry.getVciEndDate()));
		
		//商业险及险种处理
		if("1".equals(inquiry.getVciSign()) && CollectionUtils.isNotEmpty(coverItems)){
			req.setQuotnCoverageItemDTO(opetateCoverageItemList(insId, coverItems));
		}else{
			logger.info("报价失败:商业险为空");
			throw new ActionException("太平综合险必须报机动车损失保险");
		}
		
		//车辆信息
		QuotnVhlDTO vhl = new  QuotnVhlDTO();
		vhl.setModelCName(inquiry.getVehicleName());
		vhl.setModelCode(modelCode);
		vhl.setActualValue(inquiry.getVehiclePrice());
		vhl.setEngNo(inquiry.getEngNo());
		vhl.setFrameNo(inquiry.getFrmNo());
		vhl.setFistRegDate(sFormat.format(inquiry.getFstRegNo()));
		vhl.setEnrollDate(sFormat.format(inquiry.getFstRegNo()));
		vhl.setCarNewOldFlag(inquiry.getNewCarSign());
		vhl.setPlateNo(inquiry.getPlateNo());
		// 被保人
		QuotnInsuredDTO quotnInsuredDTO = new QuotnInsuredDTO();
		quotnInsuredDTO.setInsuredName(inquiry.getOwnerName());
		quotnInsuredDTO.setInsuredPhone("18030696647");
		quotnInsuredDTO.setInsuredAddress("成都");
		quotnInsuredDTO.setInsuredBirthDay(inquiry.getOwnerBirthday());
		quotnInsuredDTO.setInsuredIdCode(inquiry.getOwnerCertNo());
		// 投保人
		QuotnlicantDTO quotnlicantDTO = new QuotnlicantDTO();
		quotnlicantDTO.setApplicantName(inquiry.getOwnerName());
		quotnlicantDTO.setAppMobilePhone("18030696647");
		quotnlicantDTO.setApplicantBirthDay(inquiry.getOwnerBirthday());
		quotnlicantDTO.setApplicantIdCode(inquiry.getOwnerCertNo());
		//车主
		QuotnVhlOwnerDTO quotnVhlOwnerDTO = new QuotnVhlOwnerDTO();
		quotnVhlOwnerDTO.setVhlOwnerName(inquiry.getOwnerName());
		quotnVhlOwnerDTO.setVhlOwnerIdCode(inquiry.getOwnerCertNo());
		quotnVhlOwnerDTO.setVhlOwnerBirthDay(inquiry.getOwnerBirthday());
		
		req.setQuotnVhlDTO(vhl);
		req.setQuotnInsuredDTO(quotnInsuredDTO);
		req.setQuotnlicantDTO(quotnlicantDTO);
		req.setQuotnPolicyDTO(quotnPolicyDTO);
		req.setQuotnVhlOwnerDTO(quotnVhlOwnerDTO);
		return req;
	}

	/**
	 * 
	 * @param inquiry  保单号
	 * @param insId    险别代码
	 * @param coverItems 商业险详情
	 * @return
	 * @throws ActionException 
	 */

	private List<QuotnCoverageItemDTO> opetateCoverageItemList( String insId,
			List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverItems) throws ActionException {
		CorrespondDTO itm = null;
		List<QuotnCoverageItemDTO> coverageItemList = new ArrayList<QuotnCoverageItemDTO>();
		logger.info("解析之前的的coverItems"+coverItems);
		QuotnCoverageItemDTO quotnCoverageItemDTO=null;
		for (com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO item : coverItems) {
			itm = new CorrespondDTO(insId,BaseFinal.INSURANCETYPECODE,item.getCode());//初始化保险公司代码
			logger.info("太平报险种接口转换之前" + itm);
			
			if(itm != null && "033008".equals(itm.getCode())) {// 无法找到第三方险
				throw new ActionException("太平不能报价,无法找到第三方险");
			}	
			//保险公司代码
			try {
				itm = corrService.get(itm);
				logger.info("太平报险种接口转换之后" + itm);
				if(null != itm){
					quotnCoverageItemDTO = new QuotnCoverageItemDTO();
					quotnCoverageItemDTO.setSumLimit(item.getSumLimit());
					quotnCoverageItemDTO.setNoDduct(item.getNoDduct());
					quotnCoverageItemDTO.setCode(itm.getIns_code());
					quotnCoverageItemDTO.setGlsType(item.getGlsType());
				}
				coverageItemList.add(quotnCoverageItemDTO);
			} catch (BusinessServiceException e){
				logger.error("太平报价处理险种" + e);
				throw new ActionException("太平报价处理险种失败");
			}
		}
		
		logger.info("解析之后的coverItems"+coverageItemList);
		return coverageItemList;
		// TODO Auto-generated method stub
	}

	/**
	 * 查询代理信息
	 * @param insId
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	private AgentDTO getConfigByAgentDTO(String  insId,String userId) throws BusinessServiceException{
		try {
			logger.info("查询代理配置信息开始:insId"+insId+"userId"+userId);
			AgentDTO agentDTO = new AgentDTO();
			MicroDTO microByUserId = microService.getMicroByUserId(userId);
			String configId = microService.getConfigIdByInsIdAndMicroId(insId, microByUserId.getMicro_id());
			Map<String, Object> mapConfig = configService.getMapByInsId(configId);
			
			logger.info("太平配置信息"+mapConfig);
			
			String guMainBusinessMode =mapConfig.get("TPICHTTP_GuMainBusinessMode").toString();
			String guMainAgreementNo=mapConfig.get("TPICHTTP_GuMainAgreementNo").toString();
			String guMainBusinessSource=mapConfig.get("TPICHTTP_GuMainBusinessSource").toString();
			String guMainChannelDetailCode=mapConfig.get("TPICHTTP_GuMainChannelDetailCode").toString();;
			String guMainSolutionCode=mapConfig.get("TPICHTTP_GuMainSolutionCode").toString();
			String guMainCompanyCode=mapConfig.get("TPICHTTP_GuMainCompanyCode").toString();
			String guMainSalesmanCode=mapConfig.get("TPICHTTP_GuMainSalesmanCode").toString();
			String guMainSalesmanRegisterNo=mapConfig.get("TPICHTTP_GuMainSalesmanRegisterNo").toString();
			String guMainApplyNo=mapConfig.get("TPICHTTP_GuMainApplyNo").toString();
			String guMainOperatorCode=mapConfig.get("TPICHTTP_GuMainOperatorCode").toString();
			String guMainOperatorName=mapConfig.get("TPICHTTP_GuMainOperatorName").toString();
			String guMainCooperateSiteCode=mapConfig.get("TPICHTTP_GuMainCooperateSiteCode").toString();
			String username=mapConfig.get("TPICHTTP_userName").toString();
			String password=mapConfig.get("TPICHTTP_password").toString();
			agentDTO.setGuMainAgreementNo(guMainAgreementNo);
			agentDTO.setGuMainApplyNo(guMainApplyNo);
			agentDTO.setGuMainBusinessMode(guMainBusinessMode);
			agentDTO.setGuMainBusinessSource(guMainBusinessSource);
			agentDTO.setGuMainCooperateSiteCode(guMainCooperateSiteCode);
			agentDTO.setUserName(username);
			agentDTO.setPassword(password);
			agentDTO.setGuMainCompanyCode(guMainCompanyCode);
			agentDTO.setGuMainSalesmanRegisterNo(guMainSalesmanRegisterNo);
			agentDTO.setGuMainChannelDetailCode(guMainChannelDetailCode);;
			agentDTO.setGuMainSolutionCode(guMainSolutionCode);
			agentDTO.setGuMainSalesmanCode(guMainSalesmanCode);
			agentDTO.setGuMainOperatorCode(guMainOperatorCode);
			agentDTO.setGuMainOperatorName(guMainOperatorName);
			logger.info("查询代理信息结束:"+agentDTO);
			return agentDTO;
		} catch (BusinessServiceException e) {
			// TODO: handle exception
			throw new BusinessServiceException("代理配置信息查询失败");
		}
		
	}

}
