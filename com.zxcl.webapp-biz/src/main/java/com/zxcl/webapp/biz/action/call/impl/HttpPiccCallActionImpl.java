package com.zxcl.webapp.biz.action.call.impl;

import com.alibaba.fastjson.JSONObject;
import com.bxtx.picc.intf.biz.service.IPiccQuoteService;
import com.bxtx.picc.intf.dto.req.*;
import com.bxtx.picc.intf.dto.res.*;
import com.zxcl.bxtx.call.InsuranceAPI;
import com.zxcl.bxtx.dto.intf.VehicleReturnDTO;
import com.zxcl.pingan_http.api.dto.ClientUser;
import com.zxcl.pingan_http.api.dto.pavote.BxAttachmentDTO;
import com.zxcl.webapp.biz.action.call.HttpPiccCallAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.exception.ThirdInsurNotFoundException;
import com.zxcl.webapp.biz.service.*;
import com.zxcl.webapp.biz.util.BaseFinal;
import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.biz.util.StringUtil;
import com.zxcl.webapp.dto.*;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;
import com.zxcl.webapp.integration.dao.InquiryFileDAO;
import com.zxcl.webapp.integration.dao.OrderDAO;
import com.zxcl.webapp.util.SpringContextUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 人保调用接口
 * @author Li xiaokang
 *
 */
@Service
public class HttpPiccCallActionImpl implements HttpPiccCallAction{
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
	private IPiccQuoteService piccQuoteService;
	@Autowired
	private InsuranceCompanyConfigService insuranceCompanyConfigService;
	@Autowired
	private InquiryCustomer inquiryCustomer;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDAO orderDao;
	@Autowired
	private InquiryFileDAO inquiryFileDAO;
	
	@Override
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId) {
		
		/**
		 * 报价
		 */
		QuoteBackMessageDTO quoteDTO = null;
		try {
			quoteDTO = piccQuoteService.piccQuote(getQuotaReq(userId, inquiryId, insId));
			logger.info("人保报价远端传回的结果QuoteBackMessageDTO : " + JSONObject.toJSONString(quoteDTO));
		}catch(ThirdInsurNotFoundException e){
			logger.error("人保报价失败,暂不支持<无法找到第三方险>",e);
			return new QuotaReturnDTO("error", "人保报价失败,暂不支持<无法找到第三方险>",insId);
		}catch (Exception e) {
			logger.error("人保报价失败4",e);
			return new QuotaReturnDTO("error", "人保报价失败4",insId);
		}
		return getQuotaRsp(userId, inquiryId, insId, quoteDTO);
	}

	@Override
	public VoteInsuranceReturnDTO vote(String inquiryId, String userId, String insId, String quotaId, OwnerDTO owner,
									   VoteInsuranceDTO vote, RecognizeeDTO rec, Map<String, Object> configMap) throws ActionException {
		VoteInsuranceReturnDTO vr = new VoteInsuranceReturnDTO();
		List<String> bxAttachmenList = new ArrayList<String>();
		vr.setInsId(insId);
		vr.setInsureSucc(false);
		vr.setStatus("3");
		vr.setErrorCode("3");
		vr.setErrorMessage("核保失败");
		BxVoteResult r = null;
		try {
			/* 设置uuid */
			configMap.put("UUID", quotaId);

			//附件处理
			if(StringUtils.isNotBlank(inquiryId)){
				try {
					List<InquiryFileDTO>  fileList = inquiryFileDAO.getListByInquiryId(inquiryId);
					if(null != fileList && fileList.size() > 0){
						boolean first = true;
						BxAttachmentDTO attach = null;
						for(InquiryFileDTO file : fileList){
							bxAttachmenList.add(file.getFileId());
						}
					}
				} catch (Exception e) {
					//ignore
					logger.error("获取["+inquiryId+"]附件失败", e);
				}
			}

			configMap.put("quoteId", quotaId);
			configMap.put("uploadType", bxAttachmenList.size() > 0 ?"0" : "1");//1缓存  0要上传
			configMap.put("bxAttachmenList", bxAttachmenList);//附件

			logger.info("人保核保参数:" + configMap);
			r = piccQuoteService.piccVote(configMap);
			logger.info("核保返回结果:" + JSONObject.toJSONString(r));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if(null != r){
			if("200".equals(r.getResultCode())){
				vr.setTciApplyNo(r.getJqApplyNo());
				vr.setVciApplyNo(r.getSyApplyNo());
				vr.setInsureSucc(true);
				vr.setStatus("1".equals(r.getVoteStatus()) ? "4" : "2");// 4:核保通过, 2:人工核保
			}
			else{
				vr.setErrorMessage(r.getResultMsg());
			}
		}
		logger.info("人保核保结果：" + JSONObject.toJSONString(vr));
		return vr;
	}

	/**
	 * 将订单失效
	 * @param orderId
	 * @throws Exception
	 */
	protected void updateOrderStatusWithDel(String orderId) throws Exception{
		if(StringUtils.isBlank(orderId)){
			return;
		}
		logger.info("更新订单["+orderId+"]为失效");
		int c = orderDao.updateStatus(orderId, "0");
		logger.info("影响行数"+c);
	}

	//线上或线下支付之后，要把保单号和保费查回来
	//保单号回写时，比较保费是否一致，一致就回写，不一致，就更新线上状态为失效
	//做成只去查5（已支付状态）的单子
	//到时候我们验证的时候就可以在支付后，手动修改成5，让程序去跑一次，把保单查回来
	//保行天下的查询增加保险公司就行了，在http里面控制只查5状态的，到时候验证过了，再给http发个版本，放开其它状态的
	//这个后面也有可能是人工在营销系统里面把订单置为已支付状态（人保的就是这样处理），这样我们就都可以支持

	//投保单全部对应保单,否则返回空
	//投保单与保单都对应,如果保费不对应,将订单置为失效并返回空
	//该方法除了更新t_order.status='0'的更新操作,只有查询操作
	/**
	 * 投保单综合查询
	 *
	 * @param userId    用户ID
	 * @param orderId   报价单ID
	 * @param configMa p
	 * @return
	 * @throws Exception
	 * @author lee
	 */
	@Override
	public CombinedQueryDTO combinedQuery(String userId, String orderId, Map<String, Object> configMap) throws Exception {
		ReqAuth auth = new ReqAuth();
		auth.setUsername(MapUtils.getString(configMap, "PICCHTTP_LOGIN_NAME"));
		auth.setPassword(MapUtils.getString(configMap, "PICCHTTP_LOGIN_PWD"));
		auth.setAccessUrl(MapUtils.getString(configMap, "PICCHTTP_ACCESSURL"));
		auth.setProxyCompCode(MapUtils.getString(configMap, "PICCHTTP_AGENTCODE"));
		auth.setProxyurl(MapUtils.getString(configMap, "PICCHTTP_PROXY_URL"));
		auth.setProxyport(MapUtils.getIntValue(configMap, "PICCHTTP_PROXY_PORT"));
		OrderDTO orderDTO = orderService.getOrderById(null, null, orderId);
		if(null == orderDTO){
			logger.info("订单["+orderId+"]不存在");
			return null;
		}

		//人工核保查询
		if ("2".equals(orderDTO.getQueryStage())) {
			CombinedQueryDTO result = new CombinedQueryDTO();
			result.setInsId("PICCHTTP");

			String tciApplyNo = orderDTO.getTciApplyNo();//交强险投保单号
			String vciApplyNo = orderDTO.getVciApplyNo();//商业险投保单号
            logger.info("bxtx交强险投保单号:" + tciApplyNo);
            logger.info("bxtx商业险投保单号:" + vciApplyNo);

			String proposalNo = "";// 传入renbao_http查询的投保单号
			if (StringUtils.isNotBlank(vciApplyNo)) {
				proposalNo = vciApplyNo;
			} else if (StringUtils.isNotBlank(tciApplyNo)) {
				proposalNo = tciApplyNo;
			} else {
				logger.info("投保单号为空");
			}
			// 开始请求人保系统查询
			String httpResult = piccQuoteService.queryHeBaoStateByProposalNo(auth, proposalNo, configMap);
			logger.info("投保单状态查询结果:" + httpResult);
			if(StringUtils.isBlank(httpResult) || !httpResult.startsWith("{")){
				logger.info("投保单号["+proposalNo+"]未查询出结果");
				return null;
			}
			JSONObject resultObj = JSONObject.parseObject(httpResult);
			// 交强险核保状态
            if (StringUtils.isNotBlank(tciApplyNo)) {
                String tciApplyStatus = resultObj.getString("tciApplyStatus");
                if (StringUtils.isNotBlank(tciApplyStatus)) {
                    if ("待核保".equals(tciApplyStatus) || "待同步".equals(tciApplyStatus) || "预核保转人工".equals(tciApplyStatus)) {
                        result.setTciApplyStatus("2");
                    } else if ("见费出单待缴费".equals(tciApplyStatus)) {
                        result.setTciApplyStatus("4");
                    } else if ("不通过".equals(tciApplyStatus)) {
                        result.setTciApplyStatus("3");
                        result.setUndrOpinion(resultObj.getString("errorMsg"));
                    } else if (tciApplyStatus.contains("通过")) {
                        logger.info("检测到交强险已承保,进行保单查询");
                        return queryPlyNo(auth, orderDTO, orderId, configMap);
                    } else {
                        logger.info("未知状态:" + tciApplyStatus);
                    }
                } else {
                    logger.info("交强险核保状态为空");
                    return null;
                }
            }

			// 商业险核保状态
            if (StringUtils.isNotBlank(vciApplyNo)) {
                String vciApplyStatus = resultObj.getString("vciApplyStatus");
                if (StringUtils.isNotBlank(vciApplyStatus)) {
                    if ("待核保".equals(vciApplyStatus) || "待同步".equals(vciApplyStatus) || "预核保转人工".equals(vciApplyStatus)) {
                        result.setVciApplyStatus("2");
                    } else if ("见费出单待缴费".equals(vciApplyStatus)) {
                        result.setVciApplyStatus("4");
                    } else if ("不通过".equals(vciApplyStatus)) {
                        result.setVciApplyStatus("3");
                        result.setUndrOpinion(resultObj.getString("errorMsg"));
                    } else if (vciApplyStatus.contains("通过")) {
                        logger.info("检测到商业险已承保,进行保单查询");
                        return queryPlyNo(auth, orderDTO, orderId, configMap);
					} else {
                       logger.info("未知状态:" + vciApplyStatus);
                    }
                } else {
                    logger.info("商业险核保状态为空");
                    return null;
                }
            }
			return result;
		}

        //保单查询
        if("4".equals(orderDTO.getQueryStage()) || "5".equals(orderDTO.getQueryStage())){
            return queryPlyNo(auth, orderDTO, orderId, configMap);
        }
        return null;
	}

    /**
     * 通过投保单号查询保单号
     * @param auth
     * @param orderDTO
     * @param orderId
     * @param configMap
     * @return
     * @throws Exception
     * @auth lee
     * @date 2016-12-14 11:10:20
     */
	protected CombinedQueryDTO queryPlyNo(ReqAuth auth, OrderDTO orderDTO, String orderId, Map<String, Object> configMap) throws Exception {

        BigDecimal bxtxTciAmount = orderDTO.getQuota().getTCIPremTax();//交强险保费
        BigDecimal bxtxVciAmount = orderDTO.getQuota().getVCIPremTax();//商业险保费
        BigDecimal bxtxTaxAmount = null == orderDTO.getQuota().getVehicleTax()?new BigDecimal("0"):orderDTO.getQuota().getVehicleTax();//车船税
        BigDecimal httpTciAmount = null;
        BigDecimal httpVciAmount = null;
        BigDecimal httpTaxAmount = null;
        CombinedQueryDTO result = new CombinedQueryDTO();
        String tciApplyNo = orderDTO.getTciApplyNo();//交强险投保单号
        String vciApplyNo = orderDTO.getVciApplyNo();//商业险投保单号
        // 交强险投保单查询
        if(StringUtils.isNotBlank(tciApplyNo)){
            String httpResult = piccQuoteService.queryPolicyByProposalNo(auth, tciApplyNo, configMap);
            logger.info(tciApplyNo+"结果:"+httpResult);
            httpResult = httpResult == null?null:httpResult.trim();
            if(StringUtils.isBlank(httpResult) || !httpResult.startsWith("{")){
                logger.info("交强投保单号["+tciApplyNo+"]未查询出结果");
                return null;
            }
            PaFindResultDTO paResult = JSONObject.parseObject(httpResult, PaFindResultDTO.class);
            if(!"TCI".equals(paResult.getProductCode())
                    || StringUtils.isBlank(paResult.getTotalActualPremium())
                    || StringUtils.isBlank(paResult.getPlyNo())){
                logger.info("交强投保单号["+tciApplyNo+"]返回保单数据不正确");
                return null;
            }
            httpTciAmount = new BigDecimal(paResult.getTotalActualPremium());
            if(httpTciAmount.compareTo(bxtxTciAmount) != 0){
                logger.info("交强投保单号["+tciApplyNo+"]保费比对不一致,将订单失效");
                updateOrderStatusWithDel(orderId);
                result.setTciApplyStatus("3");
                return result;
            }
            httpTaxAmount = null == paResult.getTotalTaxMoney()?new BigDecimal("0"):new BigDecimal(paResult.getTotalTaxMoney());
            if(httpTaxAmount.compareTo(bxtxTaxAmount) != 0){
                logger.info("交强投保单号["+tciApplyNo+"]车船税比对不一致,将订单失效");
                updateOrderStatusWithDel(orderId);
                result.setTciApplyStatus("3");
                return result;
            }
            result.setTciApplyStatus("6");
            result.setTciPolicyNO(paResult.getPlyNo());
        }
        if(StringUtils.isNotBlank(vciApplyNo)){
            String httpResult = piccQuoteService.queryPolicyByProposalNo(auth, vciApplyNo, configMap);
            logger.info(vciApplyNo+"结果:"+httpResult);
            httpResult = httpResult == null?null:httpResult.trim();
            if(StringUtils.isBlank(httpResult) || !httpResult.startsWith("{")){
                logger.info("商业投保单号["+vciApplyNo+"]未查询出结果");
                return null;
            }
            PaFindResultDTO paResult = JSONObject.parseObject(httpResult, PaFindResultDTO.class);
            if(!"VCI".equals(paResult.getProductCode())
                    || StringUtils.isBlank(paResult.getTotalActualPremium())
                    || StringUtils.isBlank(paResult.getPlyNo())){
                logger.info("商业投保单号["+vciApplyNo+"]返回保单数据不正确");
                return null;
            }
            httpVciAmount = new BigDecimal(paResult.getTotalActualPremium());
            if(httpVciAmount.compareTo(bxtxVciAmount) != 0){
                logger.info("商业投保单号["+vciApplyNo+"]保费比对不一致,将订单失效");
                updateOrderStatusWithDel(orderId);
                result.setVciApplyStatus("3");
                return result;
            }
            result.setVciApplyStatus("6");
            result.setVciPolicyNO(paResult.getPlyNo());
        }

        result.setInsId("PICCHTTP");
        return result;
    }

	/**
	 * 报价请求数据整理
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private ReqPiccParams getQuotaReq(String userId, String inquiryId, String insId) throws Exception{
		ReqPiccParams params = new ReqPiccParams();
		
		//小薇
		MicroDTO microDTO = null;
		try {
			microDTO = microService.getMicroByUserId(userId);
		} catch (BusinessServiceException e) {
			logger.error("人保报价接口数据组装小微查询失败", e);
			throw new ActionException("人保报价接口数据组装小微查询失败",e);
		}
		
		//询价单
		InquiryDTO inquiry = null;
		try {
			inquiry = inquiryService.get(inquiryId, microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("人保报价接口数据组装询价单查询失败", e);
			throw new ActionException("人保报价接口数据组装询价单查询失败",e);
		}
		
		//获取保险公司配置信息
		String configId = "";
		try {//查询保险公司配置信息ID
			configId = microService.getConfigIdByInsIdAndMicroId(insId, microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("人保报价接口数据组装查询保险公司配置信息ID失败", e);
			throw new ActionException("查询保险公司配置信息ID失败");
		}
		
		Map<String,Object> configMap = new HashMap<String,Object>();
		try {//查询保险公司配置信息
			configMap = insuranceCompanyConfigService.getMapByInsId(configId);
		} catch (BusinessServiceException e) {
			logger.error("人保报价接口数据组装查询保险公司配置信息失败", e);
			throw new ActionException("查询保险公司配置信息失败");
		}
		
		// 登录认证信息
		ReqAuth auth = new ReqAuth();
		auth.setAccessUrl(null != configMap.get(insId+"_ACCESSURL") ? configMap.get(insId+"_ACCESSURL").toString():"");
		auth.setUsername(null != configMap.get(insId+"_LOGIN_NAME") ? configMap.get(insId+"_LOGIN_NAME").toString():"");
		auth.setPassword(null != configMap.get(insId+"_LOGIN_PWD") ? configMap.get(insId+"_LOGIN_PWD").toString():"");
		auth.setProxyurl(null != configMap.get(insId+"_PROXY_URL") ? configMap.get(insId+"_PROXY_URL").toString():"");
		auth.setProxyport(null != configMap.get(insId+"_PROXY_PORT") ? Integer.parseInt(configMap.get(insId+"_PROXY_PORT").toString()):0);
		auth.setProxyCompCode(null != configMap.get(insId+"_AGENTCODE") ? configMap.get(insId+"_AGENTCODE").toString():"");
		logger.info("认证信息 : " + JSONObject.toJSONString(auth));
		
		// 配置信息
		// 其他必要信息
		ReqQuotePrpInfo prpInfo = new ReqQuotePrpInfo();
		prpInfo.setBi_falg(inquiry.getVciSign());
		prpInfo.setCi_falg(inquiry.getTciSign());
		prpInfo.setPrpCmain_startDate(inquiry.getVciStartDateStr());
		prpInfo.setPrpCmain_endDate(inquiry.getVciEndDateStr());
		prpInfo.setPrpCmainCI_startDate(inquiry.getTciStartDateStr());
		prpInfo.setPrpCmainCI_endDate(inquiry.getTciEndDateStr());
		// 从数据库中获取配置
		prpInfo.setHandlerCode(null != configMap.get(insId+"_HANDLERCODE") ? configMap.get(insId+"_HANDLERCODE").toString():"");
		prpInfo.setHandlerCodeDes(null != configMap.get(insId+"_HANDLERCODEDES") ? configMap.get(insId+"_HANDLERCODEDES").toString():"");
		prpInfo.setHomePhone(null != configMap.get(insId+"_HOMEPHONE") ? configMap.get(insId+"_HOMEPHONE").toString():"");
		prpInfo.setOfficePhone(null != configMap.get(insId+"_OFFICEPHONE") ? configMap.get(insId+"_OFFICEPHONE").toString():"");
		prpInfo.setCheckHandler1Code(null != configMap.get(insId+"_CHECKHANDLER1CODE") ? configMap.get(insId+"_CHECKHANDLER1CODE").toString():"");
		prpInfo.setHandler1CodeDesFlag(null != configMap.get(insId+"_HANDLER1CODEDESFLAG") ? configMap.get(insId+"_HANDLER1CODEDESFLAG").toString():"");
		prpInfo.setHandlercode_uni(null != configMap.get(insId+"_HANDLERCODE_UNI") ? configMap.get(insId+"_HANDLERCODE_UNI").toString():"");
		prpInfo.setComCode(null != configMap.get(insId+"_COMCODE") ? configMap.get(insId+"_COMCODE").toString():"");
		prpInfo.setComCodeDes(null != configMap.get(insId+"_COMCODEDES") ? configMap.get(insId+"_COMCODEDES").toString():"");
		prpInfo.setBusinessNature(null != configMap.get(insId+"_BUSINESSNATURE") ? configMap.get(insId+"_BUSINESSNATURE").toString():"");
		prpInfo.setBusinessNatureTranslation(null != configMap.get(insId+"_BUSINESSNATURETRANSLATION") ? configMap.get(insId+"_BUSINESSNATURETRANSLATION").toString():"");
		prpInfo.setAgentCode(null != configMap.get(insId+"_AGENTCODE") ? configMap.get(insId+"_AGENTCODE").toString():"");
		prpInfo.setAgentName(null != configMap.get(insId+"_AGENTNAME") ? configMap.get(insId+"_AGENTNAME").toString():"");
		prpInfo.setAgentType(null != configMap.get(insId+"_AGENTTYPE") ? configMap.get(insId+"_AGENTTYPE").toString():"");
		prpInfo.setTempAgentCode(null != configMap.get(insId+"_TEMPAGENTCODE") ? configMap.get(insId+"_TEMPAGENTCODE").toString():"");
		prpInfo.setMakeCom(null != configMap.get(insId+"_MAKECOM") ? configMap.get(insId+"_MAKECOM").toString():"");
		prpInfo.setMakeComDes(null != configMap.get(insId+"_MAKECOMDES") ? configMap.get(insId+"_MAKECOMDES").toString():"");
		logger.info("ReqQuotePrpInfo : " + JSONObject.toJSONString(prpInfo));
		
		// 车辆信息
		ReqCarInfo carInfo = new ReqCarInfo();
		carInfo.setActualValue(inquiry.getVehiclePrice());
		carInfo.setEngineNo(inquiry.getEngNo());
		carInfo.setEnrollDate(getEnrollDate(inquiry.getFstRegNo()).toString());
		carInfo.setFrameNo(inquiry.getFrmNo());
		carInfo.setLicenseNo(inquiry.getPlateNo());
		carInfo.setTransferDate(inquiry.getTransferDateStr());
		logger.info("过户日期===> " + inquiry.getTransferDateStr());
		if(null != inquiry.getPlateNo() && inquiry.getPlateNo().indexOf("*") != -1){
			carInfo.setNewCarFlag("1");//选择新车未上牌一定是新车
			carInfo.setLicenseFlag("0");
		}else{
			//车的登记日期跟当前时间比较，等于或者超过9个月视为旧车
			int useMonths = useMouths(new Date(), inquiry.getFstRegNo());
			logger.info("车辆使用月数" + useMonths);
			if(useMonths > 9){
				carInfo.setNewCarFlag("0");
			}else{
				carInfo.setNewCarFlag("1");
			}
		}
		
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
			InsuranceAPI insuranceAPI = (InsuranceAPI) SpringContextUtil.getBean("JTICInsuranceAPI");
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
		} catch (Exception e) {
			logger.error("锦泰报价接口数据组装解析车型报文失败", e);
		}
		
		carInfo.setModelCode(modelCode);
		logger.info("车型编码: " + inquiry.getModelCode());
		carInfo.setNoNlocalFlag("0");//
		carInfo.setTransferVehicleFlag(inquiry.getTransferSign());
		if(inquiry.getVciStartDate().getMonth()-inquiry.getFstRegNo().getMonth()<-3 && inquiry.getVciStartDate().getDate()-inquiry.getFstRegNo().getDate()<0){
			carInfo.setUseYears((inquiry.getVciStartDate().getYear() - inquiry.getFstRegNo().getYear()-1)+"");
		}else{
			carInfo.setUseYears((inquiry.getVciStartDate().getYear() - inquiry.getFstRegNo().getYear()) + "");
		}
		
		// 关系人信息
		ReqInsuredInfo insuredInfo = new ReqInsuredInfo();
		insuredInfo.setInsuredName(inquiry.getOwnerName());
		insuredInfo.setIdentifyNumber(inquiry.getOwnerCertNo());
		insuredInfo.setAge(inquiry.getOwnerAge());
		
		//处理险种
		List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverItems = null;
		try {
			coverItems = coverService.getCoverageItems(inquiryId, microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("人保报价接口数据组装处理险种失败", e);
			throw new ActionException("人保报价接口数据组装处理险种失败");
		}
		
		// 商业险   
		Map<String, ReqPrpCitemKindsTemp> prpCitemKindsTemps = opetateCoverageItemList(inquiry, insId, coverItems);
		
		params.setReqAuth(auth);
		params.setReqCarInfo(carInfo);
		params.setReqInsuredInfo(insuredInfo);
		params.setReqPrpCitemKindsTemps(prpCitemKindsTemps);
		params.setReqQuotePrpInfo(prpInfo);
		// 封装其他参数
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.putAll(configMap);
		map.put("modelCode", inquiry.getModelCode());
		map.put("vehicleName", inquiry.getVehicleName());
		map.put("vehiclePrice", inquiry.getVehiclePrice());
		map.put("marketDate", inquiry.getMarketDate());
		map.put("comCode", prpInfo.getComCode());

		InquiryCustomerDTO toubao = inquiryCustomer.selectByInquiryId(inquiryId, "1");// 投保人信息
		InquiryCustomerDTO beibao = inquiryCustomer.selectByInquiryId(inquiryId, "2");// 被保人信息

		map.put("toubaoIdentifyNo", toubao.getCustomerCardId());// 投保人身份证号
		map.put("toubaoName", toubao.getCustomerName());// 投保人名称
		map.put("toubaoIsVhlOwner", toubao.getIsVhlOwner());// 是否同车主,0:否,1:shi
		map.put("beibaoIdentifyNo", beibao.getCustomerCardId());// 被保人身份证号
		map.put("beibaoName", beibao.getCustomerName());// 被保人名称
		map.put("beibaoIsVhlOwner", beibao.getIsVhlOwner());// 是否同车主,0:否,1:是
		
		logger.info("手机端传入的参数: " + JSONObject.toJSON(params));
		logger.info("map:" + JSONObject.toJSONString(map));
		return new ReqPiccParams(auth, carInfo, insuredInfo, prpCitemKindsTemps, prpInfo,map);
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
		logger.info("人保报价结果处理......");
		if(null == quoteDTO){
			logger.info("null == quoteDTO");
			return new QuotaReturnDTO("error", "人保报价失败1",insId);
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
		List<String> warns = new ArrayList<String>();
		logger.info("===> " + quoteDTO.getQtnBase().getErrorCode());
		// 重复投保
		if("REPEAT".equals(quoteDTO.getQtnBase().getErrorCode())) {
			return new QuotaReturnDTO("ERROR", getReInsureInfo(quoteDTO),insId, qtnBaseDTO.getUserName());
		}
		// 密码错误
		if ("PSDERROR".equals(quoteDTO.getQtnBase().getErrorCode())) {
			return new QuotaReturnDTO("PSDERROR", qtnBaseDTO.getErrorMsg(),insId, qtnBaseDTO.getUserName());
		}
		if(!"200".equals(quoteDTO.getQtnBase().getErrorCode())){
			return new QuotaReturnDTO("ERROR", qtnBaseDTO.getErrorMsg(),insId, qtnBaseDTO.getUserName());
		}else {
			quotaReturnDTO.setCalcSuccess(true); // 报价成功标准
            // 封装警告信息
			if(StringUtils.isNotBlank(qtnBaseDTO.getErrorMsg())){
				 warns.add(qtnBaseDTO.getErrorMsg());
		         quotaReturnDTO.setWarns(warns);
			}
           
		}
		
		List<com.bxtx.picc.intf.dto.res.CoverageItemDTO> coverageItems = quoteDTO.getCoverageItems();//商业险种
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
			quotaReturnDTO.setLastYearClaimNum(applicationVCIDTO.getLastYearClaimNum());// 上年出险次数
			quotaReturnDTO.setPremVCITax(applicationVCIDTO.getExpectPrm());//商业险
			totalCost = totalCost.add(applicationVCIDTO.getExpectPrm());// 加商业险保费
		}
		if(qtnBaseDTO != null) { // 基础信息
			quotaReturnDTO.setQuotaId(qtnBaseDTO.getQtnID());
			quotaReturnDTO.setErrorCode(qtnBaseDTO.getErrorCode());
			quotaReturnDTO.setErrorMessages(qtnBaseDTO.getErrorMsg());
		}
		
		quotaReturnDTO.setInsId(insId); // 保险公司ID
		quotaReturnDTO.setQuotaId(StringUtils.isNotBlank(quotaReturnDTO.getQuotaId())?quotaReturnDTO.getQuotaId():(System.currentTimeMillis() + "")); // 报价单号
		quotaReturnDTO.setReInsureInfo(getReInsureInfo(quoteDTO));// 重复投保信息
		if(vehicleTaxationDTO != null){
			quotaReturnDTO.setVehicleTax(vehicleTaxationDTO.getSumTax());//车船税
			totalCost = totalCost.add(vehicleTaxationDTO.getSumTax());// 加车船税
		}
		
		quotaReturnDTO.setTotalCost(totalCost);//总保费
		logger.info("总保费 : " + totalCost);
		
		// 处理险别信息
		logger.info("人保报价处理险别信息......");
		List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverageItemList = null;
		if(CollectionUtils.isNotEmpty(coverageItems)){
			com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO coverageItem = null;
			CorrespondDTO correspondDTOR = null;
			coverageItemList = new ArrayList<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO>(coverageItems.size());
			CorrespondDTO correspondDTO = new CorrespondDTO(insId, BaseFinal.INSURANCETYPECODE, null,null);
			for(com.bxtx.picc.intf.dto.res.CoverageItemDTO item : coverageItems){
				coverageItem = new com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO();
				correspondDTO.setIns_code(item.getCoverageCode());// 人保保险公司险别码
				try {
					correspondDTOR = corrService.get(correspondDTO);
					if(correspondDTOR != null){
						coverageItem.setCode(correspondDTOR.getCode());// 平台保险公司险别码
					}
				} catch (BusinessServiceException e) {
					logger.error("人保报价解析查询险种失败，险种编码为："+item.getCoverageCode(),e);
					throw new RuntimeException("人保报价解析查询险种失败，险种编码为：" + item.getCoverageCode(), e);
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
	private Map<String, ReqPrpCitemKindsTemp> opetateCoverageItemList(InquiryDTO inquiry, String insId, List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverItems) throws Exception{
		Map<String, ReqPrpCitemKindsTemp> prpCitemKindsTemps = new HashMap<String, ReqPrpCitemKindsTemp>();
		CorrespondDTO itm = null;
		// 险种信息
		for (CoverageItemDTO item : coverItems) {
			// 创建每一个险种 
			ReqPrpCitemKindsTemp kind = new ReqPrpCitemKindsTemp();
			
			itm = new CorrespondDTO(insId,BaseFinal.INSURANCETYPECODE,item.getCode());//初始化保险公司代码
			try {
				// 保险公司代码
				itm = corrService.get(itm);
				if(itm != null) {
					if(item.getNoDduct() == 1) {// 选择不计免赔
						kind.setSpecialFlag("on");
					}else {
						kind.setSpecialFlag("off");
					}
					// 将前端传入的险种设置到报价参数里面
					prpCitemKindsTemps.put(itm.getIns_code(), kind);
					if("050602".equals(itm.getIns_code())) {// 三者险
						ReqPrpCitemKindsTemp kindsTemp = new ReqPrpCitemKindsTemp(item.getSumLimit().toString());
						if(item.getNoDduct() == 1) {// 选择不计免赔
							kindsTemp.setSpecialFlag("on");
						}else {
							kindsTemp.setSpecialFlag("off");
						}
						prpCitemKindsTemps.put(itm.getIns_code(), kindsTemp);
					}
					if("050711".equals(itm.getIns_code())) {// 司机险
						ReqPrpCitemKindsTemp kindsTemp = new ReqPrpCitemKindsTemp(item.getSumLimit().toString());
						if(item.getNoDduct() == 1) {// 选择不计免赔
							kindsTemp.setSpecialFlag("on");
						}else {
							kindsTemp.setSpecialFlag("off");
						}
						prpCitemKindsTemps.put(itm.getIns_code(), kindsTemp);
					}
					if("050712".equals(itm.getIns_code())) {// 乘客险
						ReqPrpCitemKindsTemp kindsTemp = new ReqPrpCitemKindsTemp(item.getSumLimit().divide(new BigDecimal(inquiry.getSeatNum() - 1),1, BigDecimal.ROUND_UP)+"", item.getSumLimit() + "");
						if(item.getNoDduct() == 1) {// 选择不计免赔
							kindsTemp.setSpecialFlag("on");
						}else {
							kindsTemp.setSpecialFlag("off");
						}
						prpCitemKindsTemps.put(itm.getIns_code(), kindsTemp);
						logger.info("====> " + item.getSumLimit().divide(new BigDecimal(inquiry.getSeatNum() - 1),1, BigDecimal.ROUND_UP)+"   ====>" +  item.getSumLimit() + "");
					}
					if("050211".equals(itm.getIns_code())) {// 车身划损险
						ReqPrpCitemKindsTemp kindsTemp = new ReqPrpCitemKindsTemp(item.getSumLimit().toString());
						if(item.getNoDduct() == 1) {// 选择不计免赔
							kindsTemp.setSpecialFlag("on");
						}else {
							kindsTemp.setSpecialFlag("off");
						}
						prpCitemKindsTemps.put(itm.getIns_code(), kindsTemp);
						logger.info("050211carcarcar" + JSONObject.toJSONString(item));
					}
					if("050232".equals(itm.getIns_code())) {// 玻璃单独破碎险
						ReqPrpCitemKindsTemp kindsTemp = new ReqPrpCitemKindsTemp();
						if("1".equals(item.getGlsType())) {// 前端传入的玻璃类型,1:国产,2:进口
							kindsTemp.setGlsType("10");// 保险公司的玻璃类型,10:国产, 20:进口
						}else if("2".equals(item.getGlsType())) {
							kindsTemp.setGlsType("20");
						}
						prpCitemKindsTemps.put(itm.getIns_code(), kindsTemp);
						logger.info("玻璃类型====> " + item.getGlsType());
					}
				}
			} catch (BusinessServiceException e) {
				logger.error("人保报价处理处理险种出错" + e);
				throw new RuntimeException("人保报价处理处理险种出错" ,e);
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
					reInsureInfo = "商业险重复投保";
					if (StringUtils.isNotBlank(policyNo)) {
						reInsureInfo += ", 商业险单号:"+policyNo;
					}
					if (reInsureItem.getExpireDate() != null) {
						String biEndDate = dateToStrFormat(reInsureItem.getExpireDate());
						reInsureInfo += ",商业险重复投保止期:" + biEndDate;
					}
				}
				// 商业险重复投保 + 交强险重复投保
				if(reInsureItem.isTciReInsure()) {
					String tciPlyNo = reInsureItem.getTciPlyNo();
					reInsureInfo = reInsureInfo + "交强险重复投保";
					if (StringUtils.isNotBlank(tciPlyNo)) {
						reInsureInfo += ", 交强险单号:" + tciPlyNo;
					}
					if (reInsureItem.getTciEndDate() != null) {
						String ciEndDate = dateToStrFormat(reInsureItem.getTciEndDate());
						reInsureInfo += ",交强险重复投保止期:" + ciEndDate;
					}
				}
			}
		} catch (Exception e) {
			logger.error("前端封装重复投保信息异常",e);
			throw new RuntimeException("前端封装重复投保信息异常", e);
		}
		logger.info("重复投保信息: " + reInsureInfo);
		return reInsureInfo;
	}
	 
	 /**
	  * @return返回字符串格式 yyyyMMddHHmmss
	  */
	  private String dateToStrFormat(Date date) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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



