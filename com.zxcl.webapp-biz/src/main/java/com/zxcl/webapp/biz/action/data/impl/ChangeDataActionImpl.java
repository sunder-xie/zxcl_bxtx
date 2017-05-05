package com.zxcl.webapp.biz.action.data.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zxcl.bxtx.call.InsuranceAPI;
import com.zxcl.bxtx.dto.intf.CustomerDTO;
import com.zxcl.bxtx.dto.intf.CvrgDTO;
import com.zxcl.bxtx.dto.intf.InquiryDTO;
import com.zxcl.bxtx.dto.intf.OrderDTO;
import com.zxcl.bxtx.dto.intf.OrderDistributionDTO;
import com.zxcl.bxtx.dto.intf.OrderQueryReturnDTO;
import com.zxcl.bxtx.dto.intf.QuoteDTO;
import com.zxcl.bxtx.dto.intf.QuoteReturnDTO;
import com.zxcl.bxtx.dto.intf.UnderwriteReturnDTO;
import com.zxcl.bxtx.dto.intf.VehicleDTO;
import com.zxcl.bxtx.dto.intf.VehicleReturnDTO;
import com.zxcl.bxtx.dto.intf.VehicleReturnListDTO;
import com.zxcl.webapp.biz.action.data.ChangeDataAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.AreaService;
import com.zxcl.webapp.biz.service.CarModelRelatedService;
import com.zxcl.webapp.biz.service.CoverageItemService;
import com.zxcl.webapp.biz.service.DistributionService;
import com.zxcl.webapp.biz.service.InquiryCustomer;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.service.RecognizeeService;
import com.zxcl.webapp.biz.service.TaxReliefModelsService;
import com.zxcl.webapp.biz.service.VoteInsuranceService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.AreaDTO;
import com.zxcl.webapp.dto.DistributionDTO;
import com.zxcl.webapp.dto.InquiryCustomerDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.TaxReliefModelsDTO;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.PayReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vehicle.resp.VehicleModelDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;
import com.zxcl.webapp.util.SpringContextUtil;

@Service
public class ChangeDataActionImpl implements ChangeDataAction{
	/**
	 * Logger
	 */
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private QuotaService quotaService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CoverageItemService coverService;
	
	@Autowired
	private RecognizeeService recognizeeService;
	
	@Autowired
	private VoteInsuranceService voteInsuranceService;
	
	@Autowired
	private DistributionService distributionService;
	
	@Autowired
	private InquiryCustomer inquiryCustomer;
	
	@Autowired
	private CarModelRelatedService carModelRelatedService;
	
	@Autowired
	private TaxReliefModelsService taxReliefModelsService;
	
	@Autowired
	private AreaService areaService;
	
	
	/**
	 * 从身份证中获取生日
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Date getBrith(String certNo){
		try {
			if (certNo.length() == 15) {
				if (Integer.valueOf(certNo.substring(6, 8)) > 49) {
					return DateUtil.stringToDate("yyyyMMdd", "19" + certNo.substring(6, 12));
				} else {
					return DateUtil.stringToDate("yyyyMMdd", "20" + certNo.substring(6, 12));
				}
			} else {
				return DateUtil.stringToDate("yyyyMMdd", certNo.substring(6, 14));
			}			
		} catch (Exception e) {
			logger.error("获取生日失败 ,身份证号：" + certNo,e);
			return null;
		}
	}
	
	/**
	 * 从身份证中获取性别位
	 * 
	 * @return
	 */
	public static int getSex(String certNo) {
		String sex = "";
		if (certNo.length() == 15) {
			sex = certNo.substring(14,15);
		} else {
			sex = certNo.substring(16, 17);
		}
		if(Integer.parseInt(sex)%2==0){
			return 2;
		}else{
			return 1;
		}
	}
	
	@Override
	public InquiryDTO inquiryChange(String inquiryId, String quoteId,
			String orderId, OwnerDTO owner, VoteInsuranceDTO vote,
			RecognizeeDTO rec,String userId,DistributionDTO disJsp,String insId) throws ActionException {
		InquiryDTO inquiryDTO = new InquiryDTO();
		inquiryDTO.setInsId(insId);
		//询价单信息
		if(StringUtils.isNotBlank(inquiryId)){
			com.zxcl.webapp.dto.InquiryDTO inquiryDTO2 = new com.zxcl.webapp.dto.InquiryDTO();
			try {
				inquiryDTO2 = inquiryService.get(inquiryId, null);
			} catch (BusinessServiceException e) {
				logger.error("数据组装查询询价单失败，询价单号：" + inquiryId + "，quoteId:" + quoteId, e);
				throw new ActionException("数据组装查询询价单失败");
			}
			inquiryDTO.setInquiryId(inquiryId);
			inquiryDTO.setTciInsureSign(inquiryDTO2.getTciSign());
			inquiryDTO.setVciInsureSign(inquiryDTO2.getVciSign());
			inquiryDTO.setTciInsureBgnDate(inquiryDTO2.getTciStartDateStr());
			inquiryDTO.setTciInsureEndDate(inquiryDTO2.getTciEndDateStr());
			inquiryDTO.setVciInsureBgnDate(inquiryDTO2.getVciStartDateStr());
			inquiryDTO.setVciInsureEndDate(inquiryDTO2.getVciEndDateStr());
			inquiryDTO.setInsQuoteFailCount(null);
			inquiryDTO.setInsQuoteFailInfo(null);
			inquiryDTO.setRepeatInsuranceInfo(null);
			inquiryDTO.setStatus(inquiryDTO2.getState());
			inquiryDTO.setUserId(userId);
			CustomerDTO ownerDTO  = new CustomerDTO();
			ownerDTO.setName(inquiryDTO2.getOwnerName());
			int sex = ChangeDataActionImpl.getSex(inquiryDTO2.getOwnerCertNo());
			ownerDTO.setSex(sex+"");
			Date date = this.getBrith(inquiryDTO2.getOwnerCertNo());
			ownerDTO.setBirthday(DateUtil.dateToString("yyyy-MM-dd", date));
			ownerDTO.setCertNo(inquiryDTO2.getOwnerCertNo());
			inquiryDTO.setOwner(ownerDTO);

			
			List<CvrgDTO> cvrgList = new ArrayList<CvrgDTO>();
			//查询险种集合	
			List<CoverageItemDTO> coverItems = new ArrayList<CoverageItemDTO>();
			try {
				coverItems = coverService.getCoverageItems(inquiryId,null);
			} catch (BusinessServiceException e) {
				logger.error("数据组装查询险别信息失败,inquiryId:" + inquiryId, e);
				throw new ActionException("数据组装查询险别信息失败");
			}
			for (CoverageItemDTO coverageItemDTO : coverItems) {
				CvrgDTO cvrgDTO = new CvrgDTO();
				cvrgDTO.setQuoteNo(quoteId);
				cvrgDTO.setCvrgNo(coverageItemDTO.getCode());
				//乘客险
				if("030009".equals(coverageItemDTO.getCode()) && inquiryDTO2.getSeatNum() > 0){
					cvrgDTO.setSumLimit(coverageItemDTO.getAmount().divide(new BigDecimal(inquiryDTO2.getSeatNum()-1)));
					cvrgDTO.setAmount(coverageItemDTO.getAmount());                 
				}else{					
					cvrgDTO.setAmount(coverageItemDTO.getAmount());
				}
				cvrgDTO.setNoDduct(coverageItemDTO.getNoDduct());
				cvrgDTO.setGlsType(coverageItemDTO.getGlsType());
				cvrgDTO.setRemark(coverageItemDTO.getRemark());
				cvrgList.add(cvrgDTO);
			}
			inquiryDTO.setCvrgs(cvrgList);	
			
			VehicleDTO vhlDTO = new VehicleDTO();
			vhlDTO.setInquiryId(inquiryId);
			vhlDTO.setPlateNo(inquiryDTO2.getPlateNo());
			vhlDTO.setEngNo(inquiryDTO2.getEngNo());
			vhlDTO.setFrmNo(inquiryDTO2.getFrmNo());
			vhlDTO.setModelCde(inquiryDTO2.getModelCode());
			vhlDTO.setFstRegDate(inquiryDTO2.getFstRegNoStr());
			vhlDTO.setSeatNo(inquiryDTO2.getSeatNum());
			vhlDTO.setDesplacement(inquiryDTO2.getDisplacement());
			vhlDTO.setAreaCode(inquiryDTO2.getCityCode());
			vhlDTO.setVehiclePrice(inquiryDTO2.getVehiclePrice());
			vhlDTO.setNewVehicleFlag(inquiryDTO2.getNewCarSign());
			vhlDTO.setChgOwnerFlag(inquiryDTO2.getTransferSign());
			vhlDTO.setChgOwnerDate(inquiryDTO2.getTransferDateStr());
			vhlDTO.setUserId(userId);
			//该字段后面要修改
			if(true){
				String marketDate = "";
				String remark = inquiryDTO2.getRemark();
				if(StringUtils.isNotBlank(remark)){
					int count = remark.indexOf("款 ");
					if(count > 0){
						marketDate = remark.substring(count-4,count);
					}
				}
				vhlDTO.setMarketDate(marketDate);
			}else{				
				vhlDTO.setMarketDate(inquiryDTO2.getMarketDate());
			}
			vhlDTO.setModelCodeType(inquiryDTO2.getModelCodeType());
			vhlDTO.setVehicleName(inquiryDTO2.getVehicleName());
			inquiryDTO.setVhlDTO(vhlDTO);
			
			try {
				//减免税信息
				TaxReliefModelsDTO trm = taxReliefModelsService.getTaxReliModelByModelNOAndDebut(
						vhlDTO.getModelCde(), DateUtil.stringToDate("yyyy-MM-dd", vhlDTO.getFstRegDate()), vhlDTO.getVehicleName());
				com.zxcl.bxtx.dto.intf.TaxReliefModelsDTO to = new com.zxcl.bxtx.dto.intf.TaxReliefModelsDTO();
				if(trm != null){
					BeanUtils.copyProperties(trm, to);
					to.setTaxOrganization(areaService.get(vhlDTO.getAreaCode()).getName() + "地方税务局");
					inquiryDTO.setTaxRelieDTO(to);
				}
			} catch (Exception e) {
				logger.error("处理减免税信息失败,inquiryId:" + inquiryId, e);
			}
			
			if(StringUtils.equals("A", vhlDTO.getModelCodeType())){
				vhlDTO.setModelCdeB(carModelRelatedService.getCarModelRelated(vhlDTO.getModelCde(), null));
			}else if(StringUtils.equals("B", vhlDTO.getModelCodeType())){
				vhlDTO.setModelCdeB(carModelRelatedService.getCarModelRelated(null, vhlDTO.getModelCde()));
			}	
			//-----------------------
			if(StringUtils.isBlank(vhlDTO.getModelCdeB())){
				if("A".equals(inquiryDTO2.getModelCodeType())){//A套车型，查询太平匹配
					String modelCode = "";
					try {
						String marketTimestamp = "";
						String remark = inquiryDTO2.getRemark();
						if(StringUtils.isNotBlank(remark)){
							int count = remark.indexOf("款 ");
							if(count > 0){
								marketTimestamp = remark.substring(count-4,count);
							}
						}
						InsuranceAPI insuranceAPI = (InsuranceAPI) SpringContextUtil.getBean("TPICInsuranceAPI");
						List<VehicleReturnDTO> vehicles = insuranceAPI.vhlQuery(inquiryDTO2.getVehicleName()).getVehicleReturnList();
						List<String> modelCodeList = new ArrayList<String>();
						for (VehicleReturnDTO vehicleModelDTO : vehicles) {
							if(0 == new BigDecimal(vehicleModelDTO.getVehiclePrice()).compareTo(new BigDecimal(inquiryDTO2.getVehiclePrice()))){
								if(StringUtils.isNotBlank(vehicleModelDTO.getMarketDate()) && StringUtils.isNotBlank(marketTimestamp)){
									if(vehicleModelDTO.getMarketDate().equals(marketTimestamp)){
//										modelCode = vehicleModelDTO.getModelCode();
										modelCodeList.add(vehicleModelDTO.getModelCode());
									}
								}else{
//									modelCode = vehicleModelDTO.getModelCode();
									modelCodeList.add(vehicleModelDTO.getModelCode());
								}
							}
						}
						if(modelCodeList.size() == 1){
							modelCode = modelCodeList.get(0);
						}
					} catch (Exception e) {
						logger.error("太平车型报文失败", e);
					}
					vhlDTO.setModelCdeB(modelCode);
				}else{//B套车型，查询锦泰匹配
					String modelCode = "";
					try {
						String marketTimestamp = "";
						String remark = inquiryDTO2.getRemark();
						if(StringUtils.isNotBlank(remark)){
							int count = remark.indexOf("款 ");
							if(count > 0){
								marketTimestamp = remark.substring(count-4,count);
							}
						}
						InsuranceAPI insuranceAPI = (InsuranceAPI) SpringContextUtil.getBean("JTICInsuranceAPI");
						List<VehicleReturnDTO> vehicles = insuranceAPI.vhlQuery(inquiryDTO2.getVehicleName()).getVehicleReturnList();
						List<String> modelCodeList = new ArrayList<String>();
						for (VehicleReturnDTO vehicleModelDTO : vehicles) {
							if(0 == new BigDecimal(vehicleModelDTO.getVehiclePrice()).compareTo(new BigDecimal(inquiryDTO2.getVehiclePrice()))){
								if(StringUtils.isNotBlank(vehicleModelDTO.getMarketDate()) && StringUtils.isNotBlank(marketTimestamp)){
									if(vehicleModelDTO.getMarketDate().equals(marketTimestamp)){
//										modelCode = vehicleModelDTO.getModelCode();
										modelCodeList.add(vehicleModelDTO.getModelCode());
									}
								}else{
//									modelCode = vehicleModelDTO.getModelCode();
									modelCodeList.add(vehicleModelDTO.getModelCode());
								}
							}
						}
						if(modelCodeList.size() == 1){
							modelCode = modelCodeList.get(0);
						}
					} catch (Exception e) {
						logger.error("锦泰车型报文失败", e);
					}
					vhlDTO.setModelCdeB(modelCode);
				}
			}
			logger.info("inquiryId："+inquiryId+"  modelCode："+inquiryDTO2.getModelCode()+"  modelCodeB："+vhlDTO.getModelCdeB()+"  modelCodeType："+inquiryDTO2.getModelCodeType());
			inquiryDTO.setVhlDTO(vhlDTO);
			//-----------------------
			//投保人
			CustomerDTO applicant  = new CustomerDTO();
			InquiryCustomerDTO applicantDTO = inquiryCustomer.selectByInquiryId(inquiryId, "1");
			if(null != applicantDTO){
				applicant.setCertNo(applicantDTO.getCustomerCardId());
				applicant.setCertType(applicantDTO.getCustomerType()+"");
				applicant.setInquiryId(inquiryId);
				applicant.setName(applicantDTO.getCustomerName());
				applicant.setCustomType("1");
				applicant.setCertType(applicantDTO.getCertfCdeType());
				int sex1 = ChangeDataActionImpl.getSex(applicantDTO.getCustomerCardId());
				Date date1 = this.getBrith(applicantDTO.getCustomerCardId());
				applicant.setBirthday(DateUtil.dateToString("yyyy-MM-dd", date1));
				applicant.setSex(sex1+"");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("isVhlOwner",applicantDTO.getIsVhlOwner());
				applicant.setMap(map);
				inquiryDTO.setApplicant(applicant);
			}
			
			//被保人
			CustomerDTO insured  = new CustomerDTO();
			InquiryCustomerDTO insuredDTO = inquiryCustomer.selectByInquiryId(inquiryId, "2");
			if(null != insuredDTO){
				insured.setCertNo(insuredDTO.getCustomerCardId());
				insured.setCertType(insuredDTO.getCustomerType()+"");
				insured.setInquiryId(inquiryId);
				insured.setName(insuredDTO.getCustomerName());
				insured.setCustomType("2");
				int sex2 = ChangeDataActionImpl.getSex(insuredDTO.getCustomerCardId());
				Date date2 = this.getBrith(insuredDTO.getCustomerCardId());
				insured.setBirthday(DateUtil.dateToString("yyyy-MM-dd", date2));
				insured.setSex(sex2+"");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("isVhlOwner",insuredDTO.getIsVhlOwner());
				insured.setMap(map);
				inquiryDTO.setInsured(insured);
			}
		}
		//报价单信息
		if(StringUtils.isNotBlank(quoteId)){
			QuotaDTO quotaDTO = new QuotaDTO();
			try {
				quotaDTO = quotaService.getByQuotaId(quoteId);
			} catch (BusinessServiceException e) {
				logger.error("数据组装查询报价单失败", e);
				throw new ActionException("数据组装查询报价单失败");
			}
			QuoteDTO quoteDTO = new QuoteDTO();
			quoteDTO.setInquiryId(inquiryId);
			quoteDTO.setQuoteId(quoteId);
			quoteDTO.setInsId(quotaDTO.getInsurance().getInsId());
			quoteDTO.setTciPrm(quotaDTO.getTCIPremTax());
			quoteDTO.setVciPrm(quotaDTO.getVCIPremTax());
			quoteDTO.setTax(quotaDTO.getVehicleTax());
			quoteDTO.setUserId(userId);
			inquiryDTO.setQuote(quoteDTO);
		}
		//订单信息
		if(StringUtils.isNotBlank(orderId)){
			com.zxcl.webapp.dto.OrderDTO orderDTO2 = new com.zxcl.webapp.dto.OrderDTO();
			try {
				orderDTO2 = orderService.getOrderById(null, null, orderId);
			} catch (BusinessServiceException e) {
				logger.error("数据组装查询订单失败", e);
				throw new ActionException("数据组装查询订单失败");
			}
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setInquiryId(inquiryId);
			orderDTO.setQuoteId(quoteId);
			orderDTO.setOrderId(orderId);
			orderDTO.setInsId(insId);
			if(null != orderDTO2){				
				orderDTO.setCarApplyNo(orderDTO2.getCarApplyNo());
				orderDTO.setVciApplyNo(orderDTO2.getVciApplyNo());
				orderDTO.setTciApplyNo(orderDTO2.getTciApplyNo());
				orderDTO.setVciPlyNo(orderDTO2.getVciPlyNo());
				orderDTO.setTciPlyNo(orderDTO2.getTciPlyNo());
				if(null != orderDTO2.getPlyCrtTime()){					
					orderDTO.setPlyCrtTm(DateUtil.dateToString("yyyy-MM-dd HH:mm:ss", orderDTO2.getPlyCrtTime()));
				}
				orderDTO.setStatus(orderDTO2.getStatus());
				orderDTO.setQueryStatus(orderDTO2.getQueryStage());
				orderDTO.setNoticeNo(orderDTO2.getNoticeNo());
			}
			orderDTO.setUserId(userId);
			com.zxcl.webapp.dto.OrderDTO orderDTO3 = new com.zxcl.webapp.dto.OrderDTO();
			try {
				orderDTO3 = orderService.selectNameAndMoney(insId, orderId);
			} catch (BusinessServiceException e) {
				logger.error("数据组装查询订单金额失败", e);
				throw new ActionException("数据组装查询订单金额失败");
			}
			if(null != orderDTO3){				
				orderDTO.setOrderAmount(orderDTO3.getTotMoney());
			}
			inquiryDTO.setOrder(orderDTO);	
		}
		//车主信息
		if(null != owner){
			if(inquiryDTO.getOwner() == null){
				inquiryDTO.setOwner(new CustomerDTO());
			}
			inquiryDTO.getOwner().setTel(owner.getOwnerPhone());
			try {
				AreaDTO areaDTO = new AreaDTO();
				if(StringUtils.isNotBlank(owner.getOwnerCity())){
					areaDTO = areaService.get(owner.getOwnerCity());
				}else{
					areaDTO = areaService.get(owner.getOwnerProvince());
				}
				inquiryDTO.getOwner().setAddress(areaDTO.getName()+owner.getOwnerAddress());
			} catch (BusinessServiceException e) {
				logger.error("地区名称查询失败，地区编码为："+owner.getOwnerCity(),e);
			}
			inquiryDTO.getOwner().setCityCode(owner.getOwnerCity());
			inquiryDTO.getOwner().setProvinceCode(owner.getOwnerProvince());
		}
		//投保人信息
		if(null == vote && StringUtils.isNotBlank(orderId)){
			try {
				vote = voteInsuranceService.getVoteInsuranceByOrderId(orderId);
			} catch (BusinessServiceException e) {
				logger.error("数据转换投保人信息查询失败",e);
			}
		}
		if(null != vote){
			inquiryDTO.getApplicant().setCertNo(vote.getVoteCertNo());
			inquiryDTO.getApplicant().setCertType(vote.getVoteCertType());
			inquiryDTO.getApplicant().setName(vote.getVoteName());
			int sex3 = ChangeDataActionImpl.getSex(vote.getVoteCertNo());
			Date date3 = this.getBrith(vote.getVoteCertNo());
			inquiryDTO.getApplicant().setBirthday(DateUtil.dateToString("yyyy-MM-dd", date3));
			inquiryDTO.getApplicant().setSex(sex3+"");
			try {
				AreaDTO areaDTO = new AreaDTO();
				if(StringUtils.isNotBlank(vote.getVoteCity())){
					areaDTO = areaService.get(vote.getVoteCity());
				}else{
					areaDTO = areaService.get(vote.getVoteProvince());
				}
				inquiryDTO.getApplicant().setAddress(areaDTO.getName()+vote.getVoteAddress());
			} catch (BusinessServiceException e) {
				logger.error("地区名称查询失败，地区编码为："+owner.getOwnerCity(),e);
			}
			inquiryDTO.getApplicant().setCityCode(vote.getVoteCity());
			inquiryDTO.getApplicant().setOrderId(orderId);
			inquiryDTO.getApplicant().setTel(vote.getVotePhone());
			inquiryDTO.getApplicant().setUserId(userId);
			inquiryDTO.getApplicant().setProvinceCode(vote.getVoteProvince());
			inquiryDTO.getApplicant().setQuoteId(quoteId);
		}
		//被保人信息
		if(null == rec && StringUtils.isNotBlank(orderId)){
			try {
				rec = recognizeeService.getRecognizeeByOrderId(orderId);
			} catch (BusinessServiceException e) {
				logger.error("数据转换被保人信息查询失败",e);
			}
		}
		if(null != rec){
			inquiryDTO.getInsured().setCertNo(rec.getRecCertNo());
			inquiryDTO.getInsured().setCertType(rec.getRecCertType());
			inquiryDTO.getInsured().setName(rec.getRecName());
			int sex4 = ChangeDataActionImpl.getSex(rec.getRecCertNo());
			Date date4 = this.getBrith(rec.getRecCertNo());
			inquiryDTO.getInsured().setBirthday(DateUtil.dateToString("yyyy-MM-dd", date4));
			inquiryDTO.getInsured().setSex(sex4+"");
			inquiryDTO.getInsured().setCustomType("2");
			try {
				AreaDTO areaDTO = new AreaDTO();
				if(StringUtils.isNotBlank(rec.getRecCity())){
					areaDTO = areaService.get(rec.getRecCity());
				}else{
					areaDTO = areaService.get(rec.getRecProvince());
				}
				inquiryDTO.getInsured().setAddress(areaDTO.getName()+rec.getRecAddress());
			} catch (BusinessServiceException e) {
				logger.error("地区名称查询失败，地区编码为："+owner.getOwnerCity(),e);
			}
			inquiryDTO.getInsured().setCityCode(rec.getRecCity());
			inquiryDTO.getInsured().setOrderId(orderId);
			inquiryDTO.getInsured().setTel(rec.getRecPhone());
			inquiryDTO.getInsured().setUserId(userId);
			inquiryDTO.getInsured().setProvinceCode(rec.getRecProvince());
			inquiryDTO.getInsured().setQuoteId(quoteId);
		}
		//配置信息
		if(null == disJsp && StringUtils.isNotBlank(orderId)){
			try {
				disJsp = distributionService.getDistributionByOrderId(orderId);
			} catch (BusinessServiceException e) {
				logger.error("数据转换配置信息查询失败",e);
			}
		}
		if(null != disJsp){
			OrderDistributionDTO orderDistributionDTO = new OrderDistributionDTO();
			orderDistributionDTO.setInquiryId(inquiryId);
			orderDistributionDTO.setQuoteId(quoteId);
			orderDistributionDTO.setOrderId(orderId);
			orderDistributionDTO.setLinkName(disJsp.getDisName());
			orderDistributionDTO.setTel(disJsp.getDisPhone());
			try {
				AreaDTO areaDTO = new AreaDTO();
				if(StringUtils.isNotBlank(disJsp.getDisCity())){
					areaDTO = areaService.get(disJsp.getDisCity());
				}else{
					areaDTO = areaService.get(disJsp.getDisProvince());
				}
				orderDistributionDTO.setAddress(areaDTO.getName()+disJsp.getDisAddress());
			} catch (BusinessServiceException e) {
				logger.error("地区名称查询失败，地区编码为："+owner.getOwnerCity(),e);
			}
			orderDistributionDTO.setProvinceCode(disJsp.getDisProvince());
			orderDistributionDTO.setCityCode(disJsp.getDisCity());
			orderDistributionDTO.setDistributeType(disJsp.getDispatchType());
			orderDistributionDTO.setStatus(disJsp.getStatus()+"");
			orderDistributionDTO.setUserId(userId);
			if(null != inquiryDTO.getOrder()){
				inquiryDTO.getOrder().setDistribution(orderDistributionDTO);
			}
		}
		return inquiryDTO;
	}

	@Override
	public QuotaReturnDTO quoteReturnChange(QuoteReturnDTO quoteReturnDTO)
			throws ActionException {
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		if(null != quoteReturnDTO){
			quotaReturnDTO.setInsId(quoteReturnDTO.getInsId());
			quotaReturnDTO.setErrorCode(quoteReturnDTO.getErrorCode());
			quotaReturnDTO.setErrorMessages(quoteReturnDTO.getErrorMessage());
			quotaReturnDTO.setQuotaId(quoteReturnDTO.getQuoteId());
			quotaReturnDTO.setPremTCITax(quoteReturnDTO.getPremTCITax());
			quotaReturnDTO.setPremVCITax(quoteReturnDTO.getPremVCITax());
			quotaReturnDTO.setVehicleTax(quoteReturnDTO.getVehicleTax());
			quotaReturnDTO.setCalcSuccess(quoteReturnDTO.isCalcSuccess());
			quotaReturnDTO.setVciReInsure(quoteReturnDTO.getVciReInsure());
			quotaReturnDTO.setTciReInsure(quoteReturnDTO.getTciReInsure());
			quotaReturnDTO.setTotalCost(quoteReturnDTO.getTotalCost());
			quotaReturnDTO.setReInsureInfo(quoteReturnDTO.getReInsureInfo());
			quotaReturnDTO.setCarCheckStatus(quoteReturnDTO.getCarCheckStatus());
			quotaReturnDTO.setLastYearClaimNum(quoteReturnDTO.getLastYearClaimNum());
			quotaReturnDTO.setDiscount(quoteReturnDTO.getDiscount());
			quotaReturnDTO.setIsOrNoUpdateStartDateSign(quoteReturnDTO.getIsOrNoUpdateStartDateSign());
			quotaReturnDTO.setTicStartQuotaDate(quoteReturnDTO.getTciStartQuotaDate());
			quotaReturnDTO.setVicStartQuotaDate(quoteReturnDTO.getVciStartQuotaDate());
			quotaReturnDTO.setErrorCode(quoteReturnDTO.getErrorCode());
			quotaReturnDTO.setErrorMessages(quoteReturnDTO.getErrorMessage());
			quotaReturnDTO.setIsAllowReQuote(quoteReturnDTO.getIsAllowReQuote());
			quotaReturnDTO.setWarns(quoteReturnDTO.getWarns());
			if(quoteReturnDTO.getCvrgList() != null && quoteReturnDTO.getCvrgList() .size() > 0){
				//险种信息
				List<CoverageItemDTO> coverageItems = new ArrayList<CoverageItemDTO>();
				for (CvrgDTO cvrgDTO : quoteReturnDTO.getCvrgList()) {
					CoverageItemDTO coverageItemDTO = new CoverageItemDTO();
					coverageItemDTO.setCode(cvrgDTO.getCvrgNo());
					coverageItemDTO.setAmount(cvrgDTO.getPrm());
					coverageItemDTO.setSumLimit(cvrgDTO.getAmount());
					coverageItems.add(coverageItemDTO);
				}
				quotaReturnDTO.setCoverageItems(coverageItems);
			}
			
		}
		return quotaReturnDTO;
	}

	@Override
	public VoteInsuranceReturnDTO underwriteReturnChange(
			UnderwriteReturnDTO underwriteReturnDTO) throws ActionException {
		VoteInsuranceReturnDTO voteInsuranceReturnDTO = new VoteInsuranceReturnDTO();
		if(null != underwriteReturnDTO){
			voteInsuranceReturnDTO.setErrorCode(underwriteReturnDTO.getErrorCode());
			voteInsuranceReturnDTO.setErrorMessage(underwriteReturnDTO.getErrorMessage());
			voteInsuranceReturnDTO.setCarApplyNo(underwriteReturnDTO.getCarApplyNo());
			voteInsuranceReturnDTO.setTciApplyNo(underwriteReturnDTO.getTciApplyNo());
			voteInsuranceReturnDTO.setVciApplyNo(underwriteReturnDTO.getVciApplyNo());
			voteInsuranceReturnDTO.setVciPlyNo(underwriteReturnDTO.getVciPlyNo());
			voteInsuranceReturnDTO.setTciPlyNo(underwriteReturnDTO.getTciPlyNo());
			voteInsuranceReturnDTO.setNoticeNo(underwriteReturnDTO.getNoticeNo());
			voteInsuranceReturnDTO.setStatus(underwriteReturnDTO.getStatus());
			voteInsuranceReturnDTO.setOrderId(underwriteReturnDTO.getOrderId());
			voteInsuranceReturnDTO.setOrderAmount(underwriteReturnDTO.getOrderAmount());
			voteInsuranceReturnDTO.setInsureSucc(underwriteReturnDTO.isInsureSucc());
		}
		return voteInsuranceReturnDTO;
	}

	@Override
	public PayReturnDTO payReturnChange(
			com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTO)
			throws ActionException {
		logger.info("payReturnChange,payReturnDTO:"+JSON.toJSONString(payReturnDTO));
		PayReturnDTO payReturn = new PayReturnDTO();
		if(null != payReturnDTO){
			payReturn.setTradeNo(payReturnDTO.getTradeNo());
			payReturn.setBillId(payReturnDTO.getBillId());
			payReturn.setPayAmount(payReturnDTO.getPayAmount());
			payReturn.setPayResult(payReturnDTO.getPayResult());
			payReturn.setPayUrl(payReturnDTO.getPayUrl());
			payReturn.setPayTime(payReturnDTO.getPayTime());
			payReturn.setType(payReturnDTO.getType());
		}
		return payReturn;
	}

	@Override
	public List<VehicleModelDTO> vhlReturnChange(
			VehicleReturnListDTO vehicleReturnListDTO) throws ActionException {
		List<VehicleModelDTO> vehicleList = new ArrayList<VehicleModelDTO>();
		if(null != vehicleReturnListDTO && null != vehicleReturnListDTO.getVehicleReturnList()){
			for(VehicleReturnDTO vehicleReturnDTO : vehicleReturnListDTO.getVehicleReturnList()){
				VehicleModelDTO vehicleModelDTO = new VehicleModelDTO();
				vehicleModelDTO.setInsId(vehicleReturnDTO.getInsId());
				vehicleModelDTO.setModelCode(vehicleReturnDTO.getModelCode());
				vehicleModelDTO.setVehicleName(vehicleReturnDTO.getVehicleName());
				vehicleModelDTO.setVehicleAlias(vehicleReturnDTO.getVehicleAlias());
				vehicleModelDTO.setMarketaDate(vehicleReturnDTO.getMarketDate());
				vehicleModelDTO.setSeatNum(Integer.parseInt(StringUtils.isNotBlank(vehicleReturnDTO.getSeatNum())?vehicleReturnDTO.getSeatNum():"0"));
				vehicleModelDTO.setDisplacement(new BigDecimal(StringUtils.isNotBlank(vehicleReturnDTO.getDisplacement()) ? vehicleReturnDTO.getDisplacement() : "0"));
				vehicleModelDTO.setVehiclePrice(new BigDecimal(StringUtils.isNotBlank(vehicleReturnDTO.getVehiclePrice()) ? vehicleReturnDTO.getVehiclePrice() : "0"));
				vehicleModelDTO.setConfigDesc(vehicleReturnDTO.getConfigDesc());
				vehicleModelDTO.setRemark(vehicleReturnDTO.getRemark());
				vehicleList.add(vehicleModelDTO);
			}
		}
		return vehicleList;
	}

	@Override
	public CombinedQueryDTO orderQuery(OrderQueryReturnDTO orderQueryReturnDTO)
			throws ActionException {
		
		
		CombinedQueryDTO queryDTO = new CombinedQueryDTO();
		
		if(orderQueryReturnDTO == null ){
			return null;
		}
		
		queryDTO.setErrorCode(orderQueryReturnDTO.getErrorCode());
		queryDTO.setErrorMessage(orderQueryReturnDTO.getErrorMessage());
		queryDTO.setInsId(orderQueryReturnDTO.getInsId());
		queryDTO.setTciApplyStatus(orderQueryReturnDTO.getTciApplyStatus());
		queryDTO.setTciPolicyNO(orderQueryReturnDTO.getTciPlyNo());
		queryDTO.setVciApplyStatus(orderQueryReturnDTO.getVciApplyStatus());
		queryDTO.setVciPolicyNO(orderQueryReturnDTO.getVciPlyNo());
		
		return queryDTO;
	}
	
	
	
}
