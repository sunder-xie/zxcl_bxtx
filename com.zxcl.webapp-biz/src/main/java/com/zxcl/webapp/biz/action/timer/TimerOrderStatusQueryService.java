package com.zxcl.webapp.biz.action.timer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bxtx.tp.intf.biz.action.TPIntfAction;
import com.bxtx.tp.intf.dto.other.BaoDanInfo;
import com.bxtx.tp.intf.dto.other.PolicyDTO;
import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.service.WalletActiveService;
import com.zxcl.webapp.biz.util.ConstantsUtill;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.InsuranceCompanyConfigDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.integration.dao.InsuranceCompanyConfigDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.integration.dao.OrderDAO;

/**
 * @author zxj
 * @date 2016年12月6日
 * @description 
 */
@Service
public class TimerOrderStatusQueryService {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private QuotaService quotaService;
	
	@Autowired
	private CallAction callAction;
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private WalletActiveService walletActiveService;
	
	@Autowired
	private ParamConfigService paramConfigService;
	
	private @Value("${JAVA_CRONTAB_ENABLE}") String hasPermissionCron;
	
	@Autowired
	private TPIntfAction taipingHttpAction;
	
	@Autowired
	private MicroDAO microDAO;
	
	@Autowired
	private InsuranceCompanyConfigDAO insuranceCompanyConfigDAO;
	
	@Autowired
	private OrderDAO orderDao;
	
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
	
	/**
	 * 处理太平人工报价
	 * @throws Exception 
	 * 4001:订单不存在
	 * 4002:太平报价单非人工报价任务
	 * 4003:报价单不存在投保单
	 * 4004:configList为空
	 * 4005:config参数配置缺失
	 * 4006:远程查询结果为空,或非合法json
	 * 4007:保单列表为空
	 * 4008:未解析到交强险保单
	 * 4009:未解析到商业险保单
	 * 4010:configId不存在
	 * 4011:交强险保费线上与线下不一致
	 * 4012:交强险车船税线上与线下不一致
	 * 4013:商业险保费线上与线下不一致
	 * 2006:已生成保单
	 * 4000:系统繁忙
	 */
	public String excueteTPICM(String orderId){
		logger.info("excueteTPICM,orderId:"+orderId);
		try {
			
			OrderDTO tpicOrderDTO = orderDAO.getOrderById(null, null, orderId);
			logger.info("tpicOrderDTO:"+JSON.toJSONString(tpicOrderDTO));
			if(null == tpicOrderDTO){
				return "4001";
			}
			QuotaDTO quotaDTO = quotaService.getByQuotaId(tpicOrderDTO.getQuota().getQuotaId());
			logger.info("quotaDTO:"+JSON.toJSONString(quotaDTO));
			if(null == quotaDTO || !"M".equals(quotaDTO.getQuotaType())){
				logger.info("该太平报价单非人工报价任务");
				return "4002";
			}
			
			logger.info("开始处理太平人工报价任务");
			HashMap<String, String> hashMap = new HashMap<String ,String >();
			hashMap.put("companyCode", "taiping");
			if(StringUtils.isBlank(tpicOrderDTO.getTciApplyNo()) && StringUtils.isBlank(tpicOrderDTO.getVciApplyNo())){
				logger.info("投保单不存在");
				return "4003";
			}
			
			//投保单号
			//insuranceType
			//0  商业,  1 交强
			String tciApplyNo = tpicOrderDTO.getTciApplyNo();
			String vciApplyNo = tpicOrderDTO.getVciApplyNo();
			List<Map<String, String>> applyList = new ArrayList<>();
			if(StringUtils.isNotBlank(vciApplyNo)){
				Map<String, String> vciMap = new HashMap<>();
				vciMap.put("insuranceType", "0");
				vciMap.put("appPolicyNo", vciApplyNo);
				applyList.add(vciMap);
			}
			
			if(StringUtils.isNotBlank(tciApplyNo)){
				Map<String, String> tciMap = new HashMap<>();
				tciMap.put("insuranceType", "1");
				tciMap.put("appPolicyNo", tciApplyNo);
				applyList.add(tciMap);
			}
			
			//configId
			String configId = microDAO.findConfigIdByParam("TPIC", tpicOrderDTO.getMicro().getMicro_id(), "M");
			logger.info("configId:"+configId);
			if(StringUtils.isBlank(configId)){
				logger.info("configId不存在");
				return "4010";
			}
			
			//configMap
			List<InsuranceCompanyConfigDTO> configList = insuranceCompanyConfigDAO.getInfoByConfigId(configId);
			logger.info("configList:"+JSON.toJSONString(configList));
			if(null == configList || configList.isEmpty()){
				logger.info("configList为空");
				return "4004";
			}
			Map<String, String> configMap = new HashMap<>();
			for(InsuranceCompanyConfigDTO item : configList){
				configMap.put(item.getKey(), item.getValue());
			}
			logger.info("configMap:"+JSON.toJSONString(configMap));
			
			String taipingUsername;
			String taipingPassword;
			String macAddress;
			try {
				taipingUsername = configMap.get("TPICHTTP_userName").toString();
				taipingPassword = configMap.get("TPICHTTP_password").toString();
				if(configMap.get("TPICHTTP_mac") != null){
					macAddress = configMap.get("TPICHTTP_mac").toString();
				}else{
					macAddress = "";
				}
				hashMap.put("userName", taipingUsername);
				hashMap.put("password", taipingPassword);
				hashMap.put("macAddress", macAddress);
			} catch (Exception e) {
				logger.info("配置参数缺失");
				return "4005";
			}
			
			//解析保单
			String tciPlolicyNo = null;
			String vciPlolicyNo = null;
			
			BigDecimal bxtxTciPrem = null != quotaDTO.getTCIPremTax()?quotaDTO.getTCIPremTax():new BigDecimal(0);
			BigDecimal bxtxTciTax = null != quotaDTO.getVehicleTax()?quotaDTO.getVehicleTax():new BigDecimal(0);
			BigDecimal bxtxVciPrem = null != quotaDTO.getVCIPremTax()?quotaDTO.getVCIPremTax():new BigDecimal(0);
			BigDecimal taipingTciPrem = new BigDecimal(0);
			BigDecimal taipingTciTax = new BigDecimal(0);
			BigDecimal taipingVciPrem = new BigDecimal(0);
			
			for(Map<String, String> item : applyList){
				hashMap.put("appPolicyNo", item.get("appPolicyNo"));
				hashMap.put("insuranceType", item.get("insuranceType"));
				//查询保单
				logger.info("请求参数,policyParamDTO:"+JSON.toJSONString(hashMap));
				String queryResult = taipingHttpAction.parsePolicyByNo(hashMap);
				logger.info("响应结果,queryResult:"+queryResult);
				
				if(StringUtils.isBlank(queryResult) || (!queryResult.startsWith("[") && !queryResult.startsWith("{"))){
					logger.info("结果为空,或非合法json");
//					return "4006";
					continue;
				}
				
				
				try {
					List<PolicyDTO> policyDTOList = JSONArray.parseArray(queryResult, PolicyDTO.class);
					logger.info("解析结果:"+JSON.toJSONString(policyDTOList));
					if(null == policyDTOList || policyDTOList.isEmpty()){
//						return "4007";
						continue;
					}
					
					for(PolicyDTO policyItem : policyDTOList){
						BaoDanInfo baodan = policyItem.getBaoDanInfo();
						if(null == baodan || StringUtils.isBlank(baodan.getInsuranceType())){
							continue;
						}
						//0  商业,  1 交强
						if("0".equals(baodan.getInsuranceType())){
							vciPlolicyNo = baodan.getBaoDanNo();
							if(null != baodan.getTotalBaoFei()){
								taipingVciPrem = baodan.getTotalBaoFei();
							}
						}
						if("1".equals(baodan.getInsuranceType())){
							tciPlolicyNo = baodan.getBaoDanNo();
							if(null != baodan.getTotalBaoFei()){
								taipingTciPrem = baodan.getTotalBaoFei();
							}
							if(null != baodan.getTravelTax()){
								taipingTciTax = baodan.getTravelTax();
							}
						}
					}
					
				} catch (Exception e) {
					logger.error("解析失败", e);
				}
			}
			logger.info(String.format("保行天下,交强保费[%s]-交强车船税[%s]-商业保费[%s]", bxtxTciPrem, bxtxTciTax, bxtxVciPrem));
			logger.info(String.format("太平响应,交强保费[%s]-交强车船税[%s]-商业保费[%s]", taipingTciPrem, taipingTciTax, taipingVciPrem));
			if(StringUtils.isNotBlank(tciApplyNo)){
				if(StringUtils.isBlank(tciPlolicyNo)){
					logger.info("未解析到交强险保单");
					return "4008";
				}
				if(taipingTciPrem.compareTo(bxtxTciPrem) != 0){
					logger.info("交强险保费线上与线下不一致");
					this.updateOrderStatusWithDel(orderId);
					return "4011";
				}
				if(bxtxTciTax.compareTo(taipingTciTax) != 0){
					logger.info("交强险车船税线上与线下不一致");
					this.updateOrderStatusWithDel(orderId);
					return "4012";
				}
			}
			
			if(StringUtils.isNotBlank(vciApplyNo)){
				if(StringUtils.isBlank(vciPlolicyNo)){
					logger.info("未解析到商业险保单");
					return "4009";
				}
				if(bxtxVciPrem.compareTo(taipingVciPrem) != 0){
					logger.info("商业险保费线上与线下不一致");
					this.updateOrderStatusWithDel(orderId);
					return "4013";
				}
			}
			
			CombinedQueryDTO combinedQueryDTO = new CombinedQueryDTO();
			combinedQueryDTO.setErrorCode("OK");
			combinedQueryDTO.setErrorMessage(null);
			combinedQueryDTO.setInsId("TPIC");
			combinedQueryDTO.setTciApplyStatus(StringUtils.isNotBlank(tciApplyNo)?"6":null);
			combinedQueryDTO.setTciPolicyNO(tciPlolicyNo);
			combinedQueryDTO.setVciApplyStatus(StringUtils.isNotBlank(vciApplyNo)?"6":null);
			combinedQueryDTO.setVciPolicyNO(vciPlolicyNo);
			OrderDTO updateOrderDTO = this.fullOrderDTO(combinedQueryDTO, tpicOrderDTO.getOrderId(), "6", "TPIC");
			logger.info("updateOrderDTO:"+JSON.toJSONString(updateOrderDTO));
			orderService.createPolicyOperation(tpicOrderDTO.getOrderId(), tciPlolicyNo, vciPlolicyNo, updateOrderDTO);
			return "2006";
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return "4000";
	}
	
	/**
	 * 处理订单
	 * 容易混淆的ORDER变量
	 * orderItemDTO:循环中的订单,传入订单
	 * orderDTO:从数据库中实时查询出的订单
	 * order:更新订单的数据
	 * @param orderList
	 */
	public void doExecute(OrderDTO orderItemDTO){
		
		logger.info("开始处理订单"+orderItemDTO.getOrderId());
		try{
			
			//处理太平人工报价
			if("TPIC".equals(orderItemDTO.getInsurance().getInsId())){
				excueteTPICM(orderItemDTO.getOrderId());
				return;
			}
			
			//判断是否使用永城新接口，如果是，就不使用定时任务查询，通过回调接收永城通知
			if("AICS".equals(orderItemDTO.getInsurance().getInsId()) && StringUtils.equals("1", paramConfigService.getValueByKey("IS_USE_YONGCHENG_NEW_INTERFACE"))){
				return;
			}
			
			//平安核保通过待缴费不调用承保接口 2016年4月22日 13:55:24 zxj改
			if("PAIC".equals(orderItemDTO.getInsurance().getInsId()) && ConstantsUtill.ORDERSTATUS_AUDITSUCCESS.equals(orderItemDTO.getQueryStage())){
				return;
			}
			
			//过了保险期限的订单，要更新状态为待修改;状态为5的为已支付,已支付的即使过期也不校验
			if(!"5".equals(orderItemDTO.getQueryStage()) && hasExpire(orderItemDTO.getInquiry().getInquiryId())){
				return;
			}
			
			//前端是否已主动更新;当前从数据库查出的状态如果和循环中的状态不一样,则视为前端已更新,无需执行下面的逻辑
			OrderDTO orderDTO = orderService.selectByMicroIdAndOrderId(null, orderItemDTO.getOrderId(), null);
			if(null == orderItemDTO.getQueryStage() || null == orderDTO || orderDTO.getQueryStage() == null || !orderItemDTO.getQueryStage().equals(orderDTO.getQueryStage())){
				return;
			}
			
			//调用保险公司接口
			String userId = microService.getUserIdByMicId(orderItemDTO.getMicro().getMicro_id());
			CombinedQueryDTO combinedQuery = callAction.combinedQuery(userId, orderItemDTO.getInsurance().getInsId(),orderItemDTO.getOrderId(), orderItemDTO.getQueryStage());
			logger.info("保险公司综合查询结果combinedQuery:"+JSONObject.toJSONString(combinedQuery));
			if(null == combinedQuery || StringUtils.isBlank(combinedQuery.getInsId())){
				return;
			}
			
			//处理结果 
			String statusQuery = calcuteOrderStatus(combinedQuery);
			if(StringUtils.isBlank(statusQuery)){
				logger.info("statusQuery is Empty!");
				return;
			}
			if("2".equals(statusQuery) ){
				logger.info("2:人工核保中");
				return;
			}
			
			//前端是否已主动更新;保险公司接口查询的状态如果和当前数据库中的状态一致,则视为前端已更新,无需执行下面的逻辑
			orderDTO = orderService.selectByMicroIdAndOrderId(null, orderItemDTO.getOrderId(), null);
			if(null == orderItemDTO.getQueryStage() || null == orderDTO || orderDTO.getQueryStage() == null || statusQuery.equals(orderDTO.getQueryStage())){
				logger.info("前端已主动更新");
				if("HHBX".equals(orderItemDTO.getInsurance().getInsId())){
					
				}
				else{
					return;
				}
			}
			
			//更新
			OrderDTO order = fullOrderDTO(combinedQuery, orderItemDTO.getOrderId(), statusQuery, orderItemDTO.getInsurance().getInsId());
			if("5".equals(orderDTO.getQueryStage()) && ("4" == statusQuery || "3".equals(statusQuery) || "2".equals(statusQuery))){
				logger.info("当前订单状态为已支付5,查询出的状态为"+statusQuery);
			}
			else if("4".equals(orderDTO.getQueryStage()) && "2" == statusQuery){
				logger.info("当前订单状态为核保通过4,查询出的状态为人工核保中2");
			}
			else{
				if("6".equals(order.getQueryStage()) && (!"6".equals(orderDTO.getQueryStage()) || "HHBX".equals(order.getInsurance().getInsId()))){//之前没有更新为6
					orderService.createPolicyOperation(order.getOrderId(), order.getTciPlyNo(), order.getVciPlyNo(), order);
				}else{
					logger.info("当前订单状态为"+orderDTO.getQueryStage()+",查询出的状态为"+statusQuery);
					orderService.updatePolicyNoAndQueryState(order);
				}
			}
		}catch(Exception e){
			logger.error("定时更新订单状态出错,订单号：" + orderItemDTO.getOrderId(), e);
		}
	}


	/**
	 * 是否有权限运行定时任务
	 * @return
	 */
	public boolean hasPermissionCronPremission(){
		logger.info("定时刷新订单信息");
		if(!"Y".equals(hasPermissionCron)){
			logger.info("该机器上无权限运行定时任务");
			return false;
		}
		return true;
	}
	
	
	/**
	 * 过了保险期限的订单，要更新状态为待修改
	 * @param inquiryId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public boolean hasExpire(String inquiryId){
		logger.info("校验询价单["+inquiryId+"]投保起期是否过期");
		boolean flag = false;//未过期
		try {
			if(StringUtils.isNotBlank(inquiryId)){
				InquiryDTO inquiryDTO = inquiryService.get(inquiryId, null);
				if(null != inquiryDTO){
					//交强起期校验
					Date date = new Date();
					if("1".equals(inquiryDTO.getTciSign()) && null != inquiryDTO.getTciStartDate()){
						if(date.getTime() > inquiryDTO.getTciStartDate().getTime()){
							logger.info("交强险起期已过期");
							flag = true;
						}
					}
					//商业起期校验
					if(!flag && "1".equals(inquiryDTO.getVciSign()) && null != inquiryDTO.getVciStartDate()){
						if(date.getTime() > inquiryDTO.getVciStartDate().getTime()){
							logger.info("商业险起期已过期");
							flag = true;
						}
					}
				}
			}
			if(flag){
				
				//更新所有的报价单、订单状态为无效
				quotaService.updateQuotaStatusByInquiryId("0", inquiryId, "timer_system");
				orderService.updateOrderStatusByInquiryId("0", inquiryId);
				
				//更新询价单为暂存
				inquiryService.updateInquiryStatusByInquiryId("1", inquiryId, "timer_system");
			}
		} catch (BusinessServiceException e) {
			logger.error("校验询价单["+inquiryId+"]投保起期是否过期失败", e);
		}
		return flag;
		
	}


	/**
	 * 综合查询后处理订单状态
	 * @param combinedQuery
	 * @return
	 */
	private String calcuteOrderStatus(CombinedQueryDTO combinedQuery){
		String statusQuery = "";
		if(StringUtils.isNotBlank(combinedQuery.getVciApplyStatus()) && StringUtils.isNotBlank(combinedQuery.getTciApplyStatus())){
			if(combinedQuery.getVciApplyStatus().equals(combinedQuery.getTciApplyStatus())){
				statusQuery = combinedQuery.getVciApplyStatus();
			}else{
				if("3".equals(combinedQuery.getVciApplyStatus()) || "3".equals(combinedQuery.getTciApplyStatus())){
					statusQuery = "3";
				}else if("2".equals(combinedQuery.getVciApplyStatus()) || "2".equals(combinedQuery.getTciApplyStatus())){
					statusQuery = "2";
				}else if("5".equals(combinedQuery.getVciApplyStatus()) || "5".equals(combinedQuery.getTciApplyStatus())){
					statusQuery = "5";
				}else{
					logger.info("定时刷新订单信息状态错误；交强险状态："+combinedQuery.getTciApplyStatus()+" 商业险状态:"+combinedQuery.getVciApplyStatus());
				}
			}
		}else{
			statusQuery = StringUtils.isNotBlank(combinedQuery.getVciApplyStatus())?combinedQuery.getVciApplyStatus():combinedQuery.getTciApplyStatus();
		}
		return statusQuery;
	}
	
	
	/**
	 * 综合查询更新订单数据
	 * @param combinedQuery
	 * @param orderId
	 * @param statusQuery
	 * @param insId
	 * @return
	 */
	private OrderDTO fullOrderDTO(CombinedQueryDTO combinedQuery, String orderId, String statusQuery, String insId){
		OrderDTO order = new OrderDTO();
		order.setMap(combinedQuery.getMap());
		if(StringUtils.isNotBlank(combinedQuery.getTciPolicyNO())){
			order.setTciPlyNo(combinedQuery.getTciPolicyNO());
		}
		if(StringUtils.isNotBlank(combinedQuery.getVciPolicyNO())){
			order.setVciPlyNo(combinedQuery.getVciPolicyNO());
		}
		order.setInsurance(new InsuranceDTO(insId));
		order.setOrderId(orderId);
		order.setQueryStage(statusQuery);
		order.setUpdCode("bxtx_timer");
		return order;
	}
	
	
	
}
