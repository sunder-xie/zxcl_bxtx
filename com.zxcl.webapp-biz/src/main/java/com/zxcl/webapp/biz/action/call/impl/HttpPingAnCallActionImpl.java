package com.zxcl.webapp.biz.action.call.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;




import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bxtx.pingan.intf.biz.action.PAICIntfQuoteAction;
import com.bxtx.pingan.intf.dto.common.ApplicationTCIDTO;
import com.bxtx.pingan.intf.dto.common.ApplicationVCIDTO;
import com.bxtx.pingan.intf.dto.common.CoverageItemDTO;
import com.bxtx.pingan.intf.dto.common.QtnBaseDTO;
import com.bxtx.pingan.intf.dto.common.QuoteBackMessageDTO;
import com.bxtx.pingan.intf.dto.common.VehicleTaxationDTO;
import com.bxtx.pingan.intf.entity.quota.QuotaCvrgReq;
import com.bxtx.pingan.intf.entity.quota.QuotaReqDTO;
import com.zxcl.pingan_http.api.PinganHttpService;
import com.zxcl.pingan_http.api.dto.ApplyInfoDTO;
import com.zxcl.pingan_http.api.dto.ClientUser;
import com.zxcl.pingan_http.api.dto.pavote.AplylicantInfo;
import com.zxcl.pingan_http.api.dto.pavote.BxAttachmentDTO;
import com.zxcl.pingan_http.api.dto.voteresult.BxPayResult;
import com.zxcl.pingan_http.api.dto.voteresult.BxVoteResult;
import com.zxcl.webapp.biz.action.call.HttpPingAnCallAction;
import com.zxcl.webapp.biz.action.data.HttpPingAnDataAction;
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
import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.CorrespondDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.InquiryFileDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.PaFindResultDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.PayReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;
import com.zxcl.webapp.integration.dao.InquiryFileDAO;
import com.zxcl.webapp.integration.dao.OrderDAO;
import com.zxcl.webapp.integration.dao.VoteInsuranceDAO;

/**
 * @author zxj
 *
 */
@Service
public class HttpPingAnCallActionImpl implements HttpPingAnCallAction {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AgencyService agencyService;

	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private VoteInsuranceDAO voteDao;

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
	private PAICIntfQuoteAction pinganhttpQuotaRemoteService;
	
	@Autowired
	private InsuranceCompanyConfigService configService;
	
	@Autowired
	private HttpPingAnDataAction httpPingAnDataAction;
	
	@Autowired
	private PinganHttpService pinganHttpService;
	
	@Autowired
	private InquiryFileDAO inquiryFileDAO;
	
	@Autowired
	private OrderDAO orderDao;
	
//	@Override
//	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId) throws ActionException {
//		
//		//报价
//		QuoteBackMessageDTO quoteDTO =null;
//		QuotaReqDTO req = null;
//		try {
//			req = getQuotaReq(userId, inquiryId, insId);
//			quoteDTO = pinganhttpQuotaRemoteService.quote(req);
//		} catch (Exception e) {
//			logger.error("平安报价失败", e);
//			return new QuotaReturnDTO("error", "平安报价失败" + e.getMessage(),insId);
//		}
//		
//		return getQuotaRsp(req, userId, inquiryId, insId, quoteDTO);
//	}
	@Override
	public VoteInsuranceReturnDTO vote(String inquiryId, String userId, String insId, String quotaId, OwnerDTO owner,
			VoteInsuranceDTO vote, RecognizeeDTO rec, Map<String, Object> configMap) throws ActionException {
		List<BxAttachmentDTO> bxAttachmenList = new ArrayList<BxAttachmentDTO>();
		ClientUser clientUser = new ClientUser();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> userConfigMap = new HashMap<String, String>();
		userConfigMap.put("PINGANHTTP_PARTNERWORKNETCODE", CommonUtil.valueOf(configMap.get("PINGANHTTP_PARTNERWORKNETCODE")));//合作网点代码必传
		userConfigMap.put("ALL_CONFIG", JSONObject.toJSONString(configMap, SerializerFeature.WriteMapNullValue));
		clientUser.setMap(userConfigMap);
		
		clientUser.setAgentUserId(CommonUtil.valueOf(configMap.get("PINGANHTTP_USERNAME")));
		clientUser.setAgentUserPwd(CommonUtil.valueOf(configMap.get("PINGANHTTP_PASSOWRD")));
		
		
		clientUser.setXiaoweiUserId(userId);
		if(null != configMap.get("PINGANHTTP_MAC")){
			map.put("mac", configMap.get("PINGANHTTP_MAC")+"");
		}
		
		
		//附件处理
		if(StringUtils.isNotBlank(inquiryId)){
			try {
				List<InquiryFileDTO>  fileList = inquiryFileDAO.getListByInquiryId(inquiryId);
				if(null != fileList && fileList.size() > 0){
					boolean first = true;
					BxAttachmentDTO attach = null;
					for(InquiryFileDTO file : fileList){
						attach = new BxAttachmentDTO();
						attach.setFileId(file.getFileId());
						attach.setName(file.getFileId());
						if(first){
							first = false;
							attach.setType("TAR04");
						}
						else{
							attach.setType("001");
						}
						attach.setCertificateEndDate("9999-12-31");
						bxAttachmenList.add(attach);
					}
				}
			} catch (Exception e) {
				//ignore
				logger.error("获取["+inquiryId+"]附件失败", e);
			}
		}
		
		
		map.put("quoteId", quotaId);
		map.put("uploadType", bxAttachmenList.size() > 0 ?"0" : "1");//1缓存  0要上传
		map.put("bxAttachmenList", bxAttachmenList);//附件
		
		//投保人
		AplylicantInfo aplylicantInfo = new AplylicantInfo();
		if(null != vote){
			aplylicantInfo.setName(vote.getVoteName());
			aplylicantInfo.setCertificateNo(vote.getVoteCertNo());
			String birthStr;
			try {
				birthStr = DateUtil.dateToString(DateUtil.YYYY_MM_DD, CommonUtil.getBrith(vote.getVoteCertNo()));
				aplylicantInfo.setBirthday(StringUtils.isNotBlank(vote.getVoteBirthdayStr())?vote.getVoteBirthdayStr():birthStr);
			} catch (ParseException e) {
				aplylicantInfo.setBirthday("1995-01-02");
				logger.error(e.getMessage(), e);
			}
			aplylicantInfo.setAddress(vote.getVoteAddress());
			aplylicantInfo.setMobileTelephone(vote.getVotePhone());
			aplylicantInfo.setPostcode(null);
		}
		else{
			throw new ActionException("投保人必传");
		}
		map.put("aplylicantInfo", aplylicantInfo);
		
		//被保人
		AplylicantInfo voteInfo = new AplylicantInfo();
		if(null != rec){
			voteInfo.setName(rec.getRecName());
			voteInfo.setCertificateNo(rec.getRecCertNo());
			String birthStr;
			try {
				birthStr = DateUtil.dateToString(DateUtil.YYYY_MM_DD, CommonUtil.getBrith(rec.getRecCertNo()));
				voteInfo.setBirthday(StringUtils.isNotBlank(rec.getRecBirthdayStr())?rec.getRecBirthdayStr():birthStr);
			} catch (ParseException e) {
				voteInfo.setBirthday("1995-01-02");
				logger.error(e.getMessage(), e);
			}
			voteInfo.setAddress(rec.getRecAddress());
			voteInfo.setMobileTelephone(rec.getRecPhone());
			voteInfo.setPostcode(null);
		}
		else{
			throw new ActionException("被保人必传");
		}
		map.put("voteInfo", voteInfo);
		
		VoteInsuranceReturnDTO vr = new VoteInsuranceReturnDTO();
		vr.setInsId(insId);
		vr.setInsureSucc(false);
		vr.setStatus("3");
		vr.setErrorCode("3");
		vr.setErrorMessage("核保失败");
		BxVoteResult r = null;
		try {
			logger.info("平安爬虫核保请求clientUser:"+JSONObject.toJSONString(clientUser)+",map:"+JSONObject.toJSONString(map));
			r = pinganHttpService.vote(clientUser, map);
			logger.info("平安爬虫核保返回:"+JSONObject.toJSONString(r));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if(null != r){
			if("200".equals(r.getResultCode())){
				vr.setTciApplyNo(r.getJqApplyNo());
				vr.setVciApplyNo(r.getSyApplyNo());
				vr.setInsureSucc(true);
				//B1待审核 B2待缴费
				//存在B1则认为待人工审核
				if("B1".equals(r.getJqApplyStatus()) || "B1".equals(r.getSyApplyStatus())){
					logger.info("待人工审核");
					vr.setStatus("2");//待人工审核
				}
				else{
					logger.info("自动审核通过");
					vr.setStatus("4");//自动审核通过
				}
			}
			else{
				vr.setErrorMessage(r.getResultMsg());
			}
		}
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
	protected CombinedQueryDTO queryPlyNo(ClientUser clientUser, OrderDTO orderDTO, String orderId) throws Exception{

		logger.info("保单查询");
		BigDecimal bxtxTciAmount = orderDTO.getQuota().getTCIPremTax();//交强险保费
		BigDecimal bxtxVciAmount = orderDTO.getQuota().getVCIPremTax();//商业险保费
		BigDecimal bxtxTaxAmount = null == orderDTO.getQuota().getVehicleTax()?new BigDecimal("0"):orderDTO.getQuota().getVehicleTax();//车船税
		BigDecimal httpTciAmount = null;
		BigDecimal httpVciAmount = null;
		BigDecimal httpTaxAmount = null;
		CombinedQueryDTO result = new CombinedQueryDTO();
		String tciApplyNo = orderDTO.getTciApplyNo();//交强险投保单号
		String vciApplyNo = orderDTO.getVciApplyNo();//商业险投保单号
		if(StringUtils.isNotBlank(tciApplyNo)){
			String httpResult = pinganHttpService.queryPlyByApplyNo(clientUser, tciApplyNo);
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
			String httpResult = pinganHttpService.queryPlyByApplyNo(clientUser, vciApplyNo);
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
		
		result.setInsId("PINGANHTTP");
		return result;
	
	}
	
	@Override
	public CombinedQueryDTO combinedQuery(String userId, String orderId, Map<String, Object> configMap) throws Exception {
		ClientUser clientUser = new ClientUser();
		clientUser.setAgentUserId(CommonUtil.valueOf(configMap.get("PINGANHTTP_USERNAME")));
		clientUser.setAgentUserPwd(CommonUtil.valueOf(configMap.get("PINGANHTTP_PASSOWRD")));
		clientUser.setXiaoweiUserId(userId);
		HashMap<String, String> userConfigMap = new HashMap<String, String>();
		userConfigMap.put("PINGANHTTP_PARTNERWORKNETCODE", CommonUtil.valueOf(configMap.get("PINGANHTTP_PARTNERWORKNETCODE")));//合作网点代码必传
		userConfigMap.put("ALL_CONFIG", JSONObject.toJSONString(configMap, SerializerFeature.WriteMapNullValue));
		clientUser.setMap(userConfigMap);
		OrderDTO orderDTO = orderService.getOrderById(null, null, orderId);
		if(null == orderDTO){
			logger.info("订单["+orderId+"]不存在");
			return null;
		}
		logger.info("queryStage:"+orderDTO.getQueryStage());
		
		//人工核保查询
		/** BE:待审批，B1：待审核，BH：待验车，B2：待缴费，B3：待承保，B5：已承保
		 *
		 *  待缴费B2 
			已承保B5 待质检B51 质检通过B52 质检下发B53
			异常单YC 承保失败B0 其他异常单BZ 拒保B9
			待处理AA 待修改B4 待承保B3 待验车BH 待审核B1 待审批BE 待投保A2 待报价A1
		 */
		if("2".equals(orderDTO.getQueryStage())){
			logger.info("人工核保查询");
			CombinedQueryDTO result = new CombinedQueryDTO();
			result.setInsId("PINGANHTTP");
			
			String tciApplyNo = orderDTO.getTciApplyNo();//交强险投保单号
			String vciApplyNo = orderDTO.getVciApplyNo();//商业险投保单号
			if(StringUtils.isNotBlank(tciApplyNo)){
				String httpResult = pinganHttpService.queryPlyByApplyNo(clientUser, tciApplyNo);
				logger.info(tciApplyNo+"结果:"+httpResult);
				httpResult = httpResult == null?null:httpResult.trim();
				if(StringUtils.isBlank(httpResult) || !httpResult.startsWith("{")){
					logger.info("交强投保单号["+tciApplyNo+"]未查询出结果");
					return null;
				}
				PaFindResultDTO paResult = JSONObject.parseObject(httpResult, PaFindResultDTO.class);
				if("TCI".equals(paResult.getProductCode())) {
					logger.info("ApplyStatus:"+paResult.getApplyStatus());
					if("B1".equals(paResult.getApplyStatus()) || "BE".equals(paResult.getApplyStatus()) || "BH".equals(paResult.getApplyStatus())){//人工审核中
						result.setTciApplyStatus("2");
					}
					else if("B2".equals(paResult.getApplyStatus())){//核保通过
						result.setTciApplyStatus("4");
					}
					else if("B5".equals(paResult.getApplyStatus()) && StringUtils.isNotBlank(paResult.getPlyNo())){//已承保
						logger.info("检测到交强险已承保,进入保单查询");
						return queryPlyNo(clientUser, orderDTO, orderId);
					}
					else if("YC".equals(paResult.getApplyStatus())
							||"B0".equals(paResult.getApplyStatus())
							||"BZ".equals(paResult.getApplyStatus())
							||"B9".equals(paResult.getApplyStatus())){//核保退回 :人工核保失败和自动核保失败
						result.setTciApplyStatus("3");
					}
					else{
						logger.info("未知状态"+paResult.getApplyStatus());
					}
				}
				else{
					logger.info("ProductCode isEmpty!");
					return null;
				}
			}
			if(StringUtils.isNotBlank(vciApplyNo)){
				String httpResult = pinganHttpService.queryPlyByApplyNo(clientUser, vciApplyNo);
				logger.info(vciApplyNo+"结果:"+httpResult);
				httpResult = httpResult == null?null:httpResult.trim();
				if(StringUtils.isBlank(httpResult) || !httpResult.startsWith("{")){
					logger.info("商业投保单号["+tciApplyNo+"]未查询出结果");
					return null;
				}
				PaFindResultDTO paResult = JSONObject.parseObject(httpResult, PaFindResultDTO.class);
				if("VCI".equals(paResult.getProductCode())) {
					if("B1".equals(paResult.getApplyStatus()) || "BE".equals(paResult.getApplyStatus()) || "BH".equals(paResult.getApplyStatus())){//人工审核中
						result.setTciApplyStatus("2");
					}
					else if("B2".equals(paResult.getApplyStatus())){//核保通过
						result.setTciApplyStatus("4");
					}
					else if("B5".equals(paResult.getApplyStatus()) && StringUtils.isNotBlank(paResult.getPlyNo())){//已承保
						logger.info("检测到商业险已承保,进入保单查询");
						return queryPlyNo(clientUser, orderDTO, orderId);
					}
					else if("YC".equals(paResult.getApplyStatus())
							||"B0".equals(paResult.getApplyStatus())
							||"BZ".equals(paResult.getApplyStatus())
							||"B9".equals(paResult.getApplyStatus())){//核保退回 :人工核保失败和自动核保失败
						result.setTciApplyStatus("3");
					}
					else{
						logger.info("未知状态"+paResult.getApplyStatus());
					}
				}
				else{
					logger.info("ProductCode isEmpty!");
					return null;
				}
						
			}
			return result;
		}
		//保单查询
		if("4".equals(orderDTO.getQueryStage()) || "5".equals(orderDTO.getQueryStage())){
			return queryPlyNo(clientUser, orderDTO, orderId);
		}
		return null;
	}
	
	@Override
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId,
			Map<String, Object> configMap) throws ActionException {
		logger.info("HttpPingAnCallActionImpl    quotas  平安报价   入参   userId："+userId+"   inquiryId："+inquiryId+"  insId："+insId+"  configMap："+configMap);
		QuotaReturnDTO quotaReturnDTO = new QuotaReturnDTO();
		quotaReturnDTO.setInsId(insId);
		//保险公司信息
		InsuranceDTO insuranceDTO = new InsuranceDTO();
		try {
			insuranceDTO = insuranceService.get(insId);
		} catch (BusinessServiceException e) {
			logger.error("平安报价接口查询保险公司失败", e);
			return new QuotaReturnDTO("error","系统异常",insId);
		}
		ApplyInfoDTO applyInfoDTO = new ApplyInfoDTO();
		try {			
			applyInfoDTO = httpPingAnDataAction.quota(userId, insuranceDTO, inquiryId,configMap);
		} catch (Exception e) {
			logger.error("平安数据组装失败",e);
			return new QuotaReturnDTO("error",e.getMessage(),insId);
		}
		
		com.zxcl.pingan_http.api.dto.quoteresult.QuoteBackMessageDTO result = new com.zxcl.pingan_http.api.dto.quoteresult.QuoteBackMessageDTO();
		result = pinganHttpService.quote(applyInfoDTO);
		//处理返回数据
		if(null != result && null != result.getReInsureItem()){//重复投保
			boolean i = false;
			String reInsureInfo = "";
			//判断商业险是否重复投保
			if (result.getReInsureItem().isTciReInsure()) {
				if(null != result.getReInsureItem().getTciEndDate()){
					reInsureInfo += "交强险重复投保，最新保单止期：" + DateUtil.dateToString("yyyy-MM-dd", result.getReInsureItem().getTciEndDate()) + "。";
				}
				else{
					reInsureInfo += "交强险重复投保。";
				}
				i = true;
			}
			//判断交强险是否重复投保
			if (result.getReInsureItem().isVciReInsure()) {
				if(null != result.getReInsureItem().getExpireDate()){
					reInsureInfo += "商业险重复投保，最新保单止期：" + DateUtil.dateToString("yyyy-MM-dd", result.getReInsureItem().getExpireDate()) + "。";
				}
				else{
					reInsureInfo += "商业险重复投保。";
				}
				i = true;
			}
			if(i){
				quotaReturnDTO = new QuotaReturnDTO("error",reInsureInfo,insId);
				quotaReturnDTO.setTciReInsureSign(result.getReInsureItem().isTciReInsure() ? "1" : "0");
				quotaReturnDTO.setVciReInsureSign(result.getReInsureItem().isVciReInsure() ? "1" : "0");
				
				if(result.getReInsureItem().getTciBeginDate() != null) {
					if(null != result.getReInsureItem().getTciBeginDate()){
						quotaReturnDTO.setTciReInsureBeginDate(DateUtil.dateToString("yyyy-MM-dd",result.getReInsureItem().getTciBeginDate()));
					}
				}
				if(result.getReInsureItem().getTciEndDate() != null) {
					if(null != result.getReInsureItem().getTciEndDate()){
						quotaReturnDTO.setTciReInsureEndDate(DateUtil.dateToString("yyyy-MM-dd",result.getReInsureItem().getTciEndDate()));
					}
				}
				if(result.getReInsureItem().getEffectiveDate() != null) {
					if(null != result.getReInsureItem().getEffectiveDate()){
						quotaReturnDTO.setVciReInsureEndDate(DateUtil.dateToString("yyyy-MM-dd",result.getReInsureItem().getEffectiveDate()));
					}
				}
				if(result.getReInsureItem().getExpireDate() != null) {
					if(null != result.getReInsureItem().getExpireDate()){
						quotaReturnDTO.setVciReInsureEndDate(DateUtil.dateToString("yyyy-MM-dd",result.getReInsureItem().getExpireDate()));
					}
				}
				return quotaReturnDTO;
			}
		}
		if(null != result && null != result.getQtnBase() && "SUCCESS".equals(result.getQtnBase().getErrorCode())){
			logger.info("平安报价返回成功");
			quotaReturnDTO = httpPingAnDataAction.quotaReturn(result,insId);
		}else if(null != result && null != result.getQtnBase() && "FAIL".equals(result.getQtnBase().getErrorCode())){
			logger.info("平安报价返回失败");
			logger.info("平安报价失败原因："+result.getQtnBase().getErrorMsg());
			return new QuotaReturnDTO("error",result.getQtnBase().getErrorMsg(),insId);
		}else if(null != result && null != result.getQtnBase() && "PSDERROR".equals(result.getQtnBase().getErrorCode())){
			logger.info("平安报价账号密码错误");
			return new QuotaReturnDTO("PSDERROR",result.getQtnBase().getErrorMsg(),insId,applyInfoDTO.getClientUser().getAgentUserId());
		}else{
			logger.info("平安报价系统异常");
			return new QuotaReturnDTO("error","系统异常",insId);
		}
		return quotaReturnDTO;
	}

	@Override
	public PayReturnDTO pay(String userId, String insId, String orderId, String callBackUrl, Map<String, Object> configMap) {
		logger.info("平安支付orderId="+orderId+",configMap="+configMap);
		PayReturnDTO pr = new PayReturnDTO();
		ClientUser clientUser = new ClientUser();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> userConfigMap = new HashMap<String, String>();
		userConfigMap.put("PINGANHTTP_PARTNERWORKNETCODE", CommonUtil.valueOf(configMap.get("PINGANHTTP_PARTNERWORKNETCODE")));//合作网点代码必传
		userConfigMap.put("ALL_CONFIG", JSONObject.toJSONString(configMap, SerializerFeature.WriteMapNullValue));
		
		clientUser.setMap(userConfigMap);
		clientUser.setAgentUserId(CommonUtil.valueOf(configMap.get("PINGANHTTP_USERNAME")));
		clientUser.setAgentUserPwd(CommonUtil.valueOf(configMap.get("PINGANHTTP_PASSOWRD")));
		
		clientUser.setXiaoweiUserId(userId);
		if(null != configMap.get("PINGANHTTP_MAC")){
			map.put("mac", configMap.get("PINGANHTTP_MAC")+"");
		}
		OrderDTO o = null;
		try {
			o = orderService.getOrderById(null, null, orderId);
			
			//map.put 报价单号(quoteId), 投保单号(applyNoList),客户名称申请人名称(applyCustomerName),通知单号noticeNo
			ArrayList<String> noticeNoList =new ArrayList<String>();  
			noticeNoList.add(o.getNoticeNo());
			ArrayList<String> applyNoList = new ArrayList<String>();
			if(StringUtils.isNotBlank(o.getTciApplyNo())){
				applyNoList.add(o.getTciApplyNo());
			}
			if(StringUtils.isNotBlank(o.getVciApplyNo())){
				applyNoList.add(o.getVciApplyNo());
			}
			String quoteId = o.getQuota().getQuotaId();
			String applyCustomerName = "";
			
			VoteInsuranceDTO voteInsure = voteDao.getVoteInsuranceByOrderId(o.getOrderId());
			if(null != voteInsure){
				applyCustomerName = voteInsure.getVoteName();
			}
			map.put("quoteId", quoteId);
			map.put("noticeNoList", noticeNoList);
			map.put("applyNoList", applyNoList);
			map.put("applyCustomerName", applyCustomerName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		BxPayResult paR = pinganHttpService.pay(clientUser, map);
		logger.info("平安HTTP支付返回="+paR);
		if(null != paR && "200".equals(paR.getResultCode())){
			pr.setPayUrl(paR.getPayUrl());
			if(StringUtils.isNotBlank(paR.getPayNoticeNo())){
				logger.info("保存通知单号["+paR.getPayNoticeNo()+"]");
				try {
					orderService.updateNoticeNoByOrderId(orderId, paR.getPayNoticeNo(), "system");
				} catch (BusinessServiceException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return pr;
	}

	/**
	 * 报价请求数据整理
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @return
	 */
	@SuppressWarnings("unused")
	private static final DecimalFormat decimal_format = new DecimalFormat("0.000");
	/**
	 * 报价返回结果整理
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @param quoteDTO
	 * @return
	 */
	@SuppressWarnings("unused")
	private QuotaReturnDTO getQuotaRsp(QuotaReqDTO req, String userId, String inquiryId, String insId, QuoteBackMessageDTO quoteDTO){
		logger.info("平安报价结果处理......");
		if(null == quoteDTO || quoteDTO.getQtnBase() == null){
			return new QuotaReturnDTO("error", "平安报价失败：",insId);
		}
		//PSDERROR
		if("PSDERROR".equals(quoteDTO.getQtnBase().getErrorCode())){
			logger.error("平安登录失败PINGANHTTP-PSDERROR；登陆名为："+quoteDTO.getQtnBase().getUserName());
			return new QuotaReturnDTO("PSDERROR", "平安登录失败：平安登录密码错误", insId, quoteDTO.getQtnBase().getUserName());
		}
		if(!"200".equals(quoteDTO.getQtnBase().getErrorCode())){
			return new QuotaReturnDTO("error", "平安报价失败："+quoteDTO.getQtnBase().getErrorMsg(),insId);
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
		logger.info("平安报价处理险别信息......");
		if(CollectionUtils.isNotEmpty(coverageItems)){
			com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO coverageItem = null;
			CorrespondDTO correspondDTOR = null;
			List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverageItemList = new ArrayList<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO>(coverageItems.size());
			CorrespondDTO correspondDTO = new CorrespondDTO(insId, BaseFinal.INSURANCETYPECODE, null,null);
			for(CoverageItemDTO item : coverageItems){
				coverageItem = new com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO();
				correspondDTO.setIns_code(item.getCoverageCode());//平安保险公司险别码
				try {
					correspondDTOR = corrService.get(correspondDTO);
					if(correspondDTOR != null && null != correspondDTOR.getCode()){
						coverageItem.setCode(correspondDTOR.getCode());//平台保险公司险别码
					}else{
						if("00000".equals(item.getCoverageCode())){//不计免赔险
							coverageItem.setCode("00000");//平台保险公司险别码
						}else{
							continue;
						}
					}
				} catch (BusinessServiceException e) {
					logger.error("平安报价解析查询险种失败，险种编码为："+item.getCoverageCode(),e);
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
	@SuppressWarnings("unused")
	private List<QuotaCvrgReq> opetateCoverageItemList(InquiryDTO inquiry, String insId, List<com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO> coverItems){
		CorrespondDTO itm = null;
		List<QuotaCvrgReq> coverageItemList = new ArrayList<QuotaCvrgReq>(coverItems.size());
		QuotaCvrgReq cvrgReq = null;
		QuotaCvrgReq cvrgReqBJMP = null;
		for (com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO item : coverItems) {
			itm = new CorrespondDTO(insId,BaseFinal.INSURANCETYPECODE,item.getCode());//初始化保险公司代码
			try {
				
				//保险公司代码
				itm = corrService.get(itm);
				
				if(null != itm){
					cvrgReq = new QuotaCvrgReq();
					cvrgReq.setRiskCode(itm.getIns_code());//平安保险险种代码
					
					if("amount05".equals(cvrgReq.getRiskCode())){//乘客险
						cvrgReq.setAmount(item.getSumLimit().divide(new BigDecimal(inquiry.getSeatNum() - 1),1, BigDecimal.ROUND_UP)+"");//保额
					}else{
						if(null != item.getSumLimit()){
							cvrgReq.setAmount(item.getSumLimit().toString());//保额
						}else{
							cvrgReq.setAmount(inquiry.getRealPrice());//保额
						}
					}
					if("amount08".equals(itm.getIns_code())){//玻璃
						cvrgReq.setAmount(null == item.getGlsType()? "1":item.getGlsType());//1和2 
					}
					if("amount41".equals(itm.getIns_code())){//涉水
						cvrgReq.setAmount("1");
					}
					if("amount57".equals(itm.getIns_code())){//修理厂
						cvrgReq.setAmount("1");
					}
					if("amount63".equals(itm.getIns_code())){//车辆损失无法找到第三方险
						cvrgReq.setAmount("1");
					}
					if(1 == item.getNoDduct()){//不计免赔
						cvrgReqBJMP = new QuotaCvrgReq();
						if("amount04".equals(itm.getIns_code())){//司机
							cvrgReqBJMP.setRiskCode("amount49");
							cvrgReqBJMP.setAmount("1");
							coverageItemList.add(cvrgReqBJMP);
						}
						if("amount05".equals(itm.getIns_code())){//乘客
							cvrgReqBJMP.setRiskCode("amount80");
							cvrgReqBJMP.setAmount("1");
							coverageItemList.add(cvrgReqBJMP);
						}
						if("amount01".equals(itm.getIns_code())){//车损
							cvrgReqBJMP.setRiskCode("amount27");
							cvrgReqBJMP.setAmount("1");
							coverageItemList.add(cvrgReqBJMP);
						}
						if("amount18".equals(itm.getIns_code())){//自燃
							cvrgReqBJMP.setRiskCode("amount77");
							cvrgReqBJMP.setAmount("1");
							coverageItemList.add(cvrgReqBJMP);
						}
						if("amount02".equals(itm.getIns_code())){//三者
							cvrgReqBJMP.setRiskCode("amount28");
							cvrgReqBJMP.setAmount("1");
							coverageItemList.add(cvrgReqBJMP);
						}
						if("amount03".equals(itm.getIns_code())){//盗抢
							cvrgReqBJMP.setRiskCode("amount48");
							cvrgReqBJMP.setAmount("1");
							coverageItemList.add(cvrgReqBJMP);
						}
						if("amount17".equals(itm.getIns_code())){//划痕
							cvrgReqBJMP.setRiskCode("amount75");
							cvrgReqBJMP.setAmount("1");
							coverageItemList.add(cvrgReqBJMP);
						}
						if("amount41".equals(itm.getIns_code())){//涉水
							cvrgReqBJMP.setRiskCode("amount79");
							cvrgReqBJMP.setAmount("1");
							coverageItemList.add(cvrgReqBJMP);
						}
					}
					coverageItemList.add(cvrgReq);
				}
			} catch (BusinessServiceException e) {
				logger.error("平安报价处理处理险种" + e);
			}
		}
		
		return coverageItemList;
	}
	@SuppressWarnings("unused")
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
	
}
