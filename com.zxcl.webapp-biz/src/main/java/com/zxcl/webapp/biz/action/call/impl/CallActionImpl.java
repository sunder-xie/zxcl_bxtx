package com.zxcl.webapp.biz.action.call.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zxcl.bxtx.call.CallBackAPI;
import com.zxcl.bxtx.call.InsuranceAPI;
import com.zxcl.bxtx.dto.intf.InquiryDTO;
import com.zxcl.bxtx.dto.intf.OrderQueryReturnDTO;
import com.zxcl.bxtx.dto.intf.QuoteReturnDTO;
import com.zxcl.bxtx.dto.intf.UnderwriteReturnDTO;
import com.zxcl.bxtx.dto.intf.VehicleReturnDTO;
import com.zxcl.bxtx.dto.intf.VehicleReturnListDTO;
import com.zxcl.pingan_http.api.PinganFrmQueryService;
import com.zxcl.pingan_http.api.dto.ClientUser;
import com.zxcl.pingan_http.api.dto.frmquery.FrmQueryDetailDTO;
import com.zxcl.pingan_http.api.dto.frmquery.FrmQueryRespDTO;
import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.action.call.HTTPTaiPingAction;
import com.zxcl.webapp.biz.action.call.HttpAlltrustCallAction;
import com.zxcl.webapp.biz.action.call.HttpAxatpCallAction;
import com.zxcl.webapp.biz.action.call.HttpCicCallAction;
import com.zxcl.webapp.biz.action.call.HttpCnoicCallAction;
import com.zxcl.webapp.biz.action.call.HttpHACPCallAction;
import com.zxcl.webapp.biz.action.call.HttpMinanCallAction;
import com.zxcl.webapp.biz.action.call.HttpPiccCallAction;
import com.zxcl.webapp.biz.action.call.HttpPingAnCallAction;
import com.zxcl.webapp.biz.action.call.HttpXdCallAction;
import com.zxcl.webapp.biz.action.call.HttpYingdaCallAction;
import com.zxcl.webapp.biz.action.call.IntfDaDiCallAction;
import com.zxcl.webapp.biz.action.data.ChangeDataAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.exception.QuotaMaxCountException;
import com.zxcl.webapp.biz.exception.SMSTBException;
import com.zxcl.webapp.biz.service.AgencyService;
import com.zxcl.webapp.biz.service.AgentFeeChangeSignService;
import com.zxcl.webapp.biz.service.DistributionService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsuranceCompanyConfigService;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.service.RelyInsuranceService;
import com.zxcl.webapp.biz.service.SmsAlarmConfigService;
import com.zxcl.webapp.biz.service.SmsForAlarmService;
import com.zxcl.webapp.biz.service.SmsSendConfigService;
import com.zxcl.webapp.biz.service.TeamParamMappingService;
import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.biz.util.ConstantsUtill;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.biz.util.SMSTBUtils;
import com.zxcl.webapp.dto.DistributionDTO;
import com.zxcl.webapp.dto.InquiryFileRequireDTO;
import com.zxcl.webapp.dto.IntfMicroInsConfigDTO;
import com.zxcl.webapp.dto.IntfMicroInsConfigDTOKey;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.ParamConfigDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.RelyInsuranceDTO;
import com.zxcl.webapp.dto.SmsForAlarmDTO;
import com.zxcl.webapp.dto.TeamParamMappingDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.InterfaceMsg;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.PayReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.WeChatPayDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.CombinedQueryDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;
import com.zxcl.webapp.integration.dao.InquiryFileRequireDAO;
import com.zxcl.webapp.integration.dao.IntfMicroInsConfigDAO;

@Service
public class CallActionImpl implements CallAction {
	private Logger logger = Logger.getLogger(getClass());

	private static Object initLock = new Object();

	@Autowired
	private InsuranceService insService;

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private QuotaService quotaService;

	@Autowired
	private OrderService odrService;

	@Autowired
	private MicroService microService;

	@Autowired
	private InsuranceCompanyConfigService insuranceCompanyConfigService;

	@Autowired
	private IntfMicroInsConfigDAO mIMICDao;

	@Autowired
	private HttpHACPCallAction httphacpCallAction;
	
	@Autowired
	private HttpYingdaCallAction httpYingdaCallAction;

	@Autowired
	private HTTPTaiPingAction httpTaiPingAction;

	@Autowired
	private HttpPingAnCallAction httpPingAnCallAction;

	@Autowired
	private InsuranceService insuranceService;

	@Autowired
	private InquiryService inquiryService;

	@Autowired
	private DistributionService disService;

	@Autowired
	private HttpAlltrustCallAction httpAlltrustCallAction;

	@Autowired
	private SmsSendConfigService smsSendConfigService;

	@Autowired
	private HttpCicCallAction cicCallAction;

	@Autowired
	private HttpPiccCallAction httpPiccCallAction;

	@Autowired
	private SmsForAlarmService smsForAlarmService;

	@Autowired
	private SmsAlarmConfigService smsAlarmConfigService;

//	@Autowired
//	private EminanCallAction eminanCallAction;

	@Autowired
	private HttpXdCallAction httpXdCallAction;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RelyInsuranceService relyInsuranceService;
	
	@Autowired
	private AgentFeeChangeSignService agentFeeChangeSignService;
	
	@Autowired
	private HttpMinanCallAction httpMinanCallAction;
	
	@Autowired
	private HttpCnoicCallAction httpCnoicCallAction;
	
	@Autowired
	private HttpAxatpCallAction httpAxatpCallAction;
	
	@Autowired
	private IntfDaDiCallAction intfDaDiCallAction;
	
	@Autowired
	private ChangeDataAction changeDataAction;
	
	@Autowired
	private ParamConfigService paramConfigService;
	
	@Autowired
	@Qualifier("insuranceAPIImpl")
	private InsuranceAPI insuranceAPI;
	
	@Autowired
	private CallBackAPI callBackAPI;
	
	@Autowired
	private PinganFrmQueryService pinganFrmQueryService;
	
	
	public static Map<String,String> hhbxCMap ;
	
	@Autowired
	private InquiryFileRequireDAO inquiryFileRequireDAO;
	
	@Autowired
	private TeamParamMappingService teamParamMappingService;
	
	/**
	 * 获取配置信息
	 * 
	 * @param userId
	 *            用户ID
	 * @param insId
	 *            保险公司ID
	 * @return
	 * @throws ActionException
	 */
	public Map<String, Object> getConfigInfo(String configId, String insId) throws ActionException {
//		// 小微
//		MicroDTO microDTO = new MicroDTO();
//		try {
//			microDTO = microService.getMicroByUserId(userId);
//		} catch (BusinessServiceException e) {
//			logger.error("查询小微失败,小微账号：" + userId, e);
//			throw new ActionException("查询小微失败");
//		}
//		// 获取保险公司配置信息
//		String configId = "";
//		try {// 查询保险公司配置信息ID
//			configId = microService.getConfigIdByInsIdAndMicroId(insId, microDTO.getMicro_id());
//		} catch (BusinessServiceException e) {
//			logger.error("太查询保险公司配置信息ID失败,insId:" +insId, e);
//			throw new ActionException("查询保险公司配置信息ID失败");
//		}
		Map<String, Object> configMap = new HashMap<String, Object>();
		try {// 查询保险公司配置信息
			configMap = insuranceCompanyConfigService.getMapByInsId(configId);
		} catch (BusinessServiceException e) {
			logger.error("查询保险公司配置信息失败,configId:" +configId, e);
			throw new ActionException("查询保险公司配置信息失败");
		}
		return configMap;
	}
	
	public Map<String,String> getConfigInfo(String configId) throws ActionException{
		Map<String,String> configMap = new HashMap<String,String>();
		try {
			configMap = insuranceCompanyConfigService.getMapByInsIdTwo(configId);
		} catch (BusinessServiceException e) {
			logger.error("查询保险公司配置信息失败,configId:" +configId, e);
			throw new ActionException("查询保险公司配置信息失败");
		}
		return configMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleReturnDTO> vehicleQuery(String userId, String inquiryId, String carName, InsuranceDTO ins) throws ActionException {
		List<VehicleReturnDTO> vehicles = new ArrayList<VehicleReturnDTO>();
		try {
			
			//最终要用该保险公司查询车型
			String insId = ins.getInsId();
			List<RelyInsuranceDTO> relyInsuranceList = relyInsuranceService.getAll();
			for (RelyInsuranceDTO relyInsuranceDTO : relyInsuranceList) {
				if(insId.equals(relyInsuranceDTO.getInsId())){
					insId = relyInsuranceDTO.getRelyInsId();
				}
			}
			logger.info("用该保险公司查询查询，insId="+insId);
			switch (insId) {
			case "JTIC":// 锦泰保险
				vehicles = insuranceAPI.vhlQuery(carName).getVehicleReturnList();
				break;
			case "TPIC":// 太平保险
				VehicleReturnListDTO vehicleReturnListDTO1 = insuranceAPI.vhlQuery(carName);
				vehicles = vehicleReturnListDTO1.getVehicleReturnList();
				break;
			case "YGBX":// 阳光保险
				VehicleReturnListDTO vehicleReturnListDTO2 = insuranceAPI.vhlQuery(carName);
				vehicles = vehicleReturnListDTO2.getVehicleReturnList();
				break;
			case "FDCP":// 富德保险
				Map<String, Object> configMap = getConfigInfo(userId, insId);
				break;
			case "TPBX":// 天平保险
				VehicleReturnListDTO vehicleReturnListDTO = insuranceAPI.vhlQuery(carName);
				vehicles = vehicleReturnListDTO.getVehicleReturnList();
				break;
			case "HHBX":// 华海保险
				// 配置信息
				Map<String, Object> configMap1 = getConfigInfo(userId, insId);
				break;
			case "HTIC":// 华泰保险
				break;
			case "CPIC"://太平洋
				VehicleReturnListDTO vehicleReturnListDTO3 = insuranceAPI.vhlQuery(carName);
				vehicles = vehicleReturnListDTO3.getVehicleReturnList();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("用户:" + userId + "调用保险公司:" + ins.getInsId() + "车型查询接口失败:", e);
		}
		return vehicles;
	}

	@Override
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId) throws ActionException {
		com.zxcl.webapp.dto.InquiryDTO inqu = new com.zxcl.webapp.dto.InquiryDTO();
		try {
			inqu = inquiryService.get(inquiryId, null);
		} catch (BusinessServiceException e2) {
			logger.error(e2.getMessage(),e2);
			throw new ActionException(e2.getMessage());
		}
		TeamParamMappingDTO teamParamMappingDTO = new TeamParamMappingDTO();
		try {
			teamParamMappingDTO = teamParamMappingService.getTeamParamMappingInfoByInsId(userId, inqu.getUsageCode(), insId,"A");
		} catch (BusinessServiceException e1) {
			logger.error(e1.getMessage(),e1);
			throw new ActionException(e1.getMessage());
		}
		// 配置信息
		Map<String, Object> configMap = new HashMap<String,Object>();
		Map<String, String> configMapTwo = new HashMap<String,String>();
		if(insId.length() == 4){
			configMapTwo = getConfigInfo(teamParamMappingDTO.getConfigId());
		}else{
			configMap = getConfigInfo(teamParamMappingDTO.getConfigId(), insId);
		}
		if (configMap.isEmpty() && configMapTwo.isEmpty()) {
			return new QuotaReturnDTO("error", "配置信息缺失，请联系保行天下工作人员核实处理", insId);
		}
		String sysName = "";

		// 并发控制
		synchronized (initLock) {
			try {
				quotasPreWithHTTP(getUserNameByInsId(insId, configMap), inquiryId, insId, "2");// 保险公司报价次数控制
			} catch(QuotaMaxCountException e){
				logger.info("今日保险公司总次数已用完，账户：" + userId + "，保险公司ID：" + insId);
				return new QuotaReturnDTO("error", "今日保险公司总次数已用完", insId);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return new QuotaReturnDTO("error", "系统繁忙,请稍候再试.", insId);
			}
			
			try {
				quotasPreWithHTTP(userId, inquiryId, insId, "1");// 小微报价次数控制
			} catch(QuotaMaxCountException e){
				logger.info("当前账户今日报价次数已用完，账户：" + userId + "，保险公司ID：" + insId);
				return new QuotaReturnDTO("error", "当前账户今日报价次数已用完", insId);
			} catch (Exception e) {
				logger.error("控制用户报价次数失败，"+e.getMessage(), e);
				return new QuotaReturnDTO("error", "系统繁忙,请稍候再试.", insId);
			}
		}
		// }

		try {
//			InsuranceDTO insTop = insService.getTop(insId);
			QuotaReturnDTO request = new QuotaReturnDTO();
//			if (null == insTop || StringUtils.isBlank(insTop.getInsId())) {
//				return null;
//			}
			switch (insId) {
			case "JTIC":// 锦泰保险
//				Map<String, String> mapjtic = new HashMap<String, String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//					if (entry.getValue() instanceof String) {
//						mapjtic.put(entry.getKey(), (String) entry.getValue());
//					}
//				}
				request = changeDataAction.quoteReturnChange(insuranceAPI.quote(changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null, insId), configMapTwo));

				sysName = "bxtx";
				break;
			case "TPIC":// 太平保险
				InquiryDTO inquiryDTO = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
				/*Map<String,String> map1 = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   map1.put(entry.getKey(), (String) entry.getValue());
				          }
				 }*/
				QuoteReturnDTO quoteReturnDTO1 = insuranceAPI.quote(inquiryDTO, configMapTwo);
				request = changeDataAction.quoteReturnChange(quoteReturnDTO1);
				sysName = "bxtx";
				break;
			case "YGBX":// 阳光保险
				InquiryDTO inquiryDTO3 = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String,String> map3 = new HashMap<String,String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//				       if(entry.getValue() instanceof String){
//				    	   map3.put(entry.getKey(), (String) entry.getValue());
//				          }
//				 }
				QuoteReturnDTO quoteReturnDTO3 = insuranceAPI.quote(inquiryDTO3, configMapTwo);
				request = changeDataAction.quoteReturnChange(quoteReturnDTO3);
				sysName = "bxtx";
				break;
			case "PAIC":// 平安保险
				InquiryDTO inquiryDTO2 = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String,String> map2 = new HashMap<String,String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//				       if(entry.getValue() instanceof String){
//				    	   map2.put(entry.getKey(), (String) entry.getValue());
//				          }
//				 }
				QuoteReturnDTO quoteReturnDTO2 = insuranceAPI.quote(inquiryDTO2, configMapTwo);
				request = changeDataAction.quoteReturnChange(quoteReturnDTO2);
				sysName = "bxtx";
				break;
			case "AICS":// 永城保险
				
				InquiryDTO aicsinquiryDTO = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String,String> acicmap = new HashMap<String,String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//				       if(entry.getValue() instanceof String){
//				    	   acicmap.put(entry.getKey(), (String) entry.getValue());
//				          }
//				 }
				request = changeDataAction.quoteReturnChange(insuranceAPI.quote(aicsinquiryDTO, configMapTwo));
				
				sysName = "bxtx";
				break;
			case "FDCP":// 富德保险
				InquiryDTO fudeInquiryDTO = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String,String> fudeMap = new HashMap<String,String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//			       if(entry.getValue() instanceof String){
//			    	   fudeMap.put(entry.getKey(), (String) entry.getValue());
//			       }
//				}
				QuoteReturnDTO fudeQuoteReturnDTO = insuranceAPI.quote(fudeInquiryDTO, configMapTwo);
				request = changeDataAction.quoteReturnChange(fudeQuoteReturnDTO);
				sysName = "fude_intf";
				break;
			case "TPBX":// 天平保险
				InquiryDTO inquiryDTOtpbx = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String,String> map = new HashMap<String,String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//				       if(entry.getValue() instanceof String){
//				    	   map.put(entry.getKey(), (String) entry.getValue());
//				          }
//				 }
				QuoteReturnDTO quoteReturnDTO = insuranceAPI.quote(inquiryDTOtpbx, configMapTwo);
				request = changeDataAction.quoteReturnChange(quoteReturnDTO);
				sysName = "bxtx";
				break;
			case "CICPHTTP":// 中华保险
				request = cicCallAction.quotas(userId, inquiryId, insId, configMap);
				sysName = "zhonghua_intf";
				break;
			case "HACP":// 华安保险
				InquiryDTO hainquiryDTO = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String, String> hamap = new HashMap<String, String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//				       if(entry.getValue() instanceof String){
//				    	   hamap.put(entry.getKey(), (String) entry.getValue());
//				          }
//				 }
				request = changeDataAction.quoteReturnChange(insuranceAPI.quote(hainquiryDTO, configMapTwo));
				sysName = "huaan_intf";
				break;
			case "YINGDAHTTP":// 英大保险
				request = httpYingdaCallAction.quotas(userId, inquiryId, insId);
				sysName = "yingda_http";
				break;
			case "TPICHTTP":// 太平保险
				request = httpTaiPingAction.quotas(userId, inquiryId, insId);
				sysName = "taiping_http";
				break;
			case "HACPHTTP":// 华安保险
				request = httphacpCallAction.quotas(userId, inquiryId, insId);
				sysName = "huaan_http";
				break;
			case "AICSHTTP":// 永城保险Http
				request = httpAlltrustCallAction.quotas(userId, inquiryId, insId);
				sysName = "yongcheng_http";
				break;
			case "PICCHTTP":// 人保http
				request = httpPiccCallAction.quotas(userId, inquiryId, insId);
				sysName = "renbao_http";
				break;
			case "PINGANHTTP":// 平安http
				request = httpPingAnCallAction.quotas(userId, inquiryId, insId, configMap);
				sysName = "pingan_http";
				break;
			case "HTIC":// 华泰保险
//				Map<String, String> maphtic = new HashMap<String, String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//					if (entry.getValue() instanceof String) {
//						maphtic.put(entry.getKey(), (String) entry.getValue());
//					}
//				}
				request = changeDataAction.quoteReturnChange(insuranceAPI.quote(changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null, insId), configMapTwo));
				sysName = "huatai_intf";
				break;
			case "MACN":// 民安保险
//				request = eminanCallAction.quotas(userId, inquiryId, insId, configMap);
//				sysName = "bxtx";
				break;
			case "XDCPHTTP":// 信达保险
				request = httpXdCallAction.quotas(userId, inquiryId, insId);
				sysName = "xinda_http";
				break;
			case "XDCPHTTP01":// 信达保险
				request = httpXdCallAction.quotas(userId, inquiryId, insId);
				sysName = "xd_intf";
				break;
			case "MACNHTTP"://民安保险
				request = httpMinanCallAction.quotas(userId, inquiryId, insId, configMap);
				sysName = "minan_http";
				break;
			case "HHBXHTTP"://华海保险
				request = httpCnoicCallAction.quotas(userId, inquiryId, insId, configMap);
				sysName = "huahai_http";
				break;
			case "HHBX"://华海保险intf
				InquiryDTO hhbxInquiryDTO = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String,String> hhbxMap = new HashMap<String,String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//			       if(entry.getValue() instanceof String){
//			    	   hhbxMap.put(entry.getKey(), (String) entry.getValue());
//			       }
//				}
				hhbxCMap = configMapTwo;
				QuoteReturnDTO hhbxQuoteReturnDTO = insuranceAPI.quote(hhbxInquiryDTO, configMapTwo);
				request = changeDataAction.quoteReturnChange(hhbxQuoteReturnDTO);
				sysName = "huahai_intf";
				break;
			case "LIBAO"://利宝保险intf
				InquiryDTO libaoInquiryDTO = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String,String> libaoMap = new HashMap<String,String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//			       if(entry.getValue() instanceof String){
//			    	   libaoMap.put(entry.getKey(), (String) entry.getValue());
//			       }
//				}
				QuoteReturnDTO libaoQuoteReturnDTO = insuranceAPI.quote(libaoInquiryDTO, configMapTwo);
				request = changeDataAction.quoteReturnChange(libaoQuoteReturnDTO);
				sysName = "libao_intf";
				break;
			case "CPIC"://太平洋保险
				InquiryDTO inquiryDTOCpic = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String,String> mapCpic = new HashMap<String,String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//				       if(entry.getValue() instanceof String){
//				    	   mapCpic.put(entry.getKey(), (String) entry.getValue());
//				          }
//				 }
				QuoteReturnDTO quoteReturnDTOCpic = insuranceAPI.quote(inquiryDTOCpic, configMapTwo);
				request = changeDataAction.quoteReturnChange(quoteReturnDTOCpic);
				sysName = "taipingyang_intf";
				break;
			case "CICP"://中华保险
				InquiryDTO inquiryDTOCicp = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String,String> mapCicp = new HashMap<String,String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//				       if(entry.getValue() instanceof String){
//				    	   mapCicp.put(entry.getKey(), (String) entry.getValue());
//				          }
//				 }
				QuoteReturnDTO quoteReturnDTOCicp = insuranceAPI.quote(inquiryDTOCicp, configMapTwo);
				request = changeDataAction.quoteReturnChange(quoteReturnDTOCicp);
				sysName = "zhonghua_intf";
				break;
			case "TPBXHTTP":
				request = httpAxatpCallAction.quotas(userId, inquiryId, insId, configMap);
				sysName = "tianping_http";
				break;
				
			case "CCIC"://大地保险
//				Map<String, String> mapccic = new HashMap<String, String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//					if (entry.getValue() instanceof String) {
//						mapccic.put(entry.getKey(), (String) entry.getValue());
//					}
//				}
				request = changeDataAction.quoteReturnChange(insuranceAPI.quote(changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null, insId), configMapTwo));
				sysName = "dadi_intf";
				break;
			case "ACIC" :
				InquiryDTO acicinquiryDTO = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String,String> parammap = new HashMap<String,String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//				       if(entry.getValue() instanceof String){
//				    	   parammap.put(entry.getKey(), (String) entry.getValue());
//				          }
//				 }
				QuoteReturnDTO acicQuoteReturn = insuranceAPI.quote(acicinquiryDTO, configMapTwo);
				//处理需上传的附件信息
				if(acicQuoteReturn.getRequireFiles() != null && acicQuoteReturn.getRequireFiles().size() > 0){
					List<InquiryFileRequireDTO> rfs = new ArrayList<>();
					for(String type : acicQuoteReturn.getRequireFiles()){
						InquiryFileRequireDTO ifr = new InquiryFileRequireDTO();
						ifr.setCrtBy(userId);
						ifr.setCrtTm(new Date());
						ifr.setFileId("");
						ifr.setFileType(type);
						ifr.setInquiryId(inquiryId);
						ifr.setInsId("ACIC");
						ifr.setStatus(0);
						ifr.setUpdBy(userId);
						ifr.setUpdTm(new Date());
						rfs.add(ifr);
					}
					inquiryFileRequireDAO.deleteInquiryFileRequire(inquiryId, "ACIC");
					for(InquiryFileRequireDTO ifr : rfs){
						inquiryFileRequireDAO.insertInquiryFileRequire(ifr);
					}
					
				}
				
				request = changeDataAction.quoteReturnChange(acicQuoteReturn);
				
				break;
			case "PICC"://中国人保
				InquiryDTO inquiryDTOPicc = changeDataAction.inquiryChange(inquiryId, null, null, null, null, null, userId, null,insId);
//				Map<String,String> mapPicc = new HashMap<String,String>();
//				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
//				       if(entry.getValue() instanceof String){
//				    	   mapPicc.put(entry.getKey(), (String) entry.getValue());
//				          }
//				 }
				QuoteReturnDTO quoteReturnDTOPicc = insuranceAPI.quote(inquiryDTOPicc, configMapTwo);
				request = changeDataAction.quoteReturnChange(quoteReturnDTOPicc);
				sysName = "renbap_intf";
				break;
			default:
				break;
			}
			String errorCode = request.getErrorCode();
			if ("PSDERROR".equals(errorCode)) {// 用户密码错误
				String keyword = request.getUserName();
				logger.info("保险公司：" + insId + "  用户密码错误，用户名为：" + keyword);
				// 当天时间
				String date = DateUtil.dateToString("yyyy-MM-dd", new Date());
				// 获取当天发送短信数量
				Integer i = smsForAlarmService.getCount(insId, "1", date);
				boolean boo = false;
				if (i < 2) {
					String content = "保险公司" + insId + "账号" + keyword + "登录失败";
					// 模板配置信息
					Map<String, String> map = new HashMap<String, String>();
					map.put("sysName", sysName);
					map.put("level", "紧急");
					map.put("content", content);

					// 获取配置的电话号码
					String[] phones = smsAlarmConfigService.getPhones(insId, "1");
					SMSTBUtils s = new SMSTBUtils();
					String phonesStr = toString(phones, ",");
					try {
						logger.info("发送短信，手机号码：" + phones );
						boo = s.sendSMSTBCode(phonesStr, map, ConstantsUtill.SMS_TB_TEMPLATE_ALARM);
					} catch (SMSTBException e) {
						logger.error("发送短信失败，保险公司：" + insId + " 用户密码错误，发送短信失败", e);
					}
					if (boo) {
						// 保存短信信息
						SmsForAlarmDTO smsForAlarmDTO = new SmsForAlarmDTO();
						smsForAlarmDTO.setId(DateUtil.dateToString("yyyyMMddHHmmssSSS", new Date()));
						smsForAlarmDTO.setContent(content);
						smsForAlarmDTO.setCrtCde(userId);
						smsForAlarmDTO.setUpdCde(userId);
						smsForAlarmDTO.setErrorType("1");
						smsForAlarmDTO.setInsId(insId);
						smsForAlarmDTO.setKeyWord(keyword);
						smsForAlarmDTO.setNoteTime(date);
						smsForAlarmService.insert(smsForAlarmDTO);
						logger.info("保险公司：" + insId + " 用户密码错误，发送短信成功");
					} else {
						logger.info("保险公司：" + insId + " 用户密码错误，发送短信失败");
					}
				} else {
					logger.info("保险公司：" + insId + " 用户密码错误，今天已发送2次短信");
				}
			}

			return request;
		} catch (BusinessServiceException e) {
			logger.error("用户:" + userId + "调用保险公司:" + insId + "报价接口失败:",e);
		}
		return null;
	}

	@Override
	public VoteInsuranceReturnDTO vote(String userId, String insId, String quotaId, String orderId, OwnerDTO owner, VoteInsuranceDTO vote, RecognizeeDTO rec, DistributionDTO disJsp) throws ActionException {
		com.zxcl.webapp.dto.InquiryDTO inqu = new com.zxcl.webapp.dto.InquiryDTO();
		try {
			inqu = inquiryService.get(owner.getInquiry().getInquiryId(), null);
		} catch (BusinessServiceException e2) {
			logger.error(e2.getMessage(),e2);
			throw new ActionException(e2.getMessage());
		}
		QuotaDTO quota = new QuotaDTO();
		try {
			quota = quotaService.getByQuotaId(quotaId);
		} catch (BusinessServiceException e1) {
			logger.error(e1.getMessage(),e1);
			throw new ActionException(e1.getMessage());
		}
		String configId = "";
		try {
			configId = teamParamMappingService.getConfigId(insId, inqu.getUsageCode(), quota.getQuotaType(), userId);
		} catch (BusinessServiceException e1) {
			logger.error(e1.getMessage(),e1);
			throw new ActionException(e1.getMessage());
		}
		// 配置信息
		Map<String, Object> configMap = getConfigInfo(configId, insId);
		if (configMap.isEmpty()) {
			logger.info("用户" + userId + "没有保险公司" + insId + "的配置信息");
			throw new ActionException("用户" + userId + "没有保险公司" + insId + "的配置信息");
		}
		try {
			VoteInsuranceReturnDTO voteReturn = new VoteInsuranceReturnDTO();
//			// 获取数据
//			InsuranceDTO insTop = insService.getTop(insId);
//			if (null == insTop || StringUtils.isBlank(insTop.getInsId())) {
//				return null;
//			}
			switch (insId) {
			case "JTIC":
				InquiryDTO inquiryDTOJTIC = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String, String> mapJTIC = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						mapJTIC.put(entry.getKey(), (String) entry.getValue());
					}
				}
				UnderwriteReturnDTO underwriteReturnDTOJTIC = insuranceAPI.underwrite(inquiryDTOJTIC, mapJTIC);
				voteReturn = changeDataAction.underwriteReturnChange(underwriteReturnDTOJTIC);
				break;
			case "TPIC":
				InquiryDTO inquiryDTO1 = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> map1 = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   map1.put(entry.getKey(), (String) entry.getValue());
				          }
				 }
				UnderwriteReturnDTO underwriteReturnDTO1 = insuranceAPI.underwrite(inquiryDTO1, map1);
				voteReturn = changeDataAction.underwriteReturnChange(underwriteReturnDTO1);
				break;
			case "YGBX":
				InquiryDTO inquiryDTO3 = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> map3 = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   map3.put(entry.getKey(), (String) entry.getValue());
				          }
				 }
				UnderwriteReturnDTO underwriteReturnDTO3 = insuranceAPI.underwrite(inquiryDTO3, map3);
				voteReturn = changeDataAction.underwriteReturnChange(underwriteReturnDTO3);
				if(voteReturn.isInsureSucc()){
					OrderQueryReturnDTO queryReturnDTO = new OrderQueryReturnDTO();
					queryReturnDTO.setOrderId(orderId);
					queryReturnDTO.setInsId("YGBX");
					queryReturnDTO.setQueryState(underwriteReturnDTO3.getStatus());;
					queryReturnDTO.setTciApplyNo(underwriteReturnDTO3.getTciApplyNo());
					queryReturnDTO.setTciApplyStatus(underwriteReturnDTO3.getTciApplyStatus());
					queryReturnDTO.setVciApplyNo(underwriteReturnDTO3.getVciApplyNo());
					queryReturnDTO.setVciApplyStatus(underwriteReturnDTO3.getVciApplyStatus());
					
					callBackAPI.callBack(queryReturnDTO);
				}
				break;
			case "PAIC":
				InquiryDTO inquiryDTO2 = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> map2 = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   map2.put(entry.getKey(), (String) entry.getValue());
				          }
				 }
				UnderwriteReturnDTO underwriteReturnDTO2 = insuranceAPI.underwrite(inquiryDTO2, map2);
				voteReturn = changeDataAction.underwriteReturnChange(underwriteReturnDTO2);
				break;
			case "AICS":
				
				//因为永城需要处理投保单状态，所以需要在投保完成后，保存投保单的状态信息。
				//旧版根据订单状态统一保存。
				//新版根据返回的对象进行保存.
				InquiryDTO aicsinquiryDTO = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp ,insId);
				Map<String,String> acicmap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   acicmap.put(entry.getKey(), (String) entry.getValue());
				          }
				 }
				
				UnderwriteReturnDTO returnDTO = insuranceAPI.underwrite(aicsinquiryDTO, acicmap);
				voteReturn = changeDataAction.underwriteReturnChange(returnDTO);
				if(voteReturn.isInsureSucc()){
					OrderQueryReturnDTO queryReturnDTO = new OrderQueryReturnDTO();
					queryReturnDTO.setOrderId(orderId);
					queryReturnDTO.setInsId("AICS");
					queryReturnDTO.setQueryState(returnDTO.getStatus());;
					queryReturnDTO.setTciApplyNo(returnDTO.getTciApplyNo());
					queryReturnDTO.setTciApplyStatus(returnDTO.getTciApplyStatus());
					queryReturnDTO.setVciApplyNo(returnDTO.getVciApplyNo());
					queryReturnDTO.setVciApplyStatus(returnDTO.getVciApplyStatus());
					
					callBackAPI.callBack(queryReturnDTO);
				}
				
				break;
			case "FDCP":
				InquiryDTO fudeInquiryDTO = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> fudeMap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   fudeMap.put(entry.getKey(), (String) entry.getValue());
				          }
				 }
				UnderwriteReturnDTO fudeUnderwriteReturnDTO = insuranceAPI.underwrite(fudeInquiryDTO, fudeMap);
				voteReturn = changeDataAction.underwriteReturnChange(fudeUnderwriteReturnDTO);
				break;
			case "TPBX":
				InquiryDTO inquiryDTO = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> map = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   map.put(entry.getKey(), (String) entry.getValue());
				          }
				 }
				UnderwriteReturnDTO underwriteReturnDTO = insuranceAPI.underwrite(inquiryDTO, map);
				voteReturn = changeDataAction.underwriteReturnChange(underwriteReturnDTO);
				break;
			case "HTIC":// 华泰保险
				InquiryDTO inquiryDTOHTIC = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String, String> mapHTIC = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						mapHTIC.put(entry.getKey(), (String) entry.getValue());
					}
				}
				UnderwriteReturnDTO underwriteReturnDTOHTIC = insuranceAPI.underwrite(inquiryDTOHTIC, mapHTIC);
				voteReturn = changeDataAction.underwriteReturnChange(underwriteReturnDTOHTIC);
				break;
			case "HACP":// 华安保险
				InquiryDTO huaanInquiryDTO = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> huaanMap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   huaanMap.put(entry.getKey(), (String) entry.getValue());
		           }
				 }
				UnderwriteReturnDTO huaanUnderwriteReturnDTO = insuranceAPI.underwrite(huaanInquiryDTO, huaanMap);
				voteReturn = changeDataAction.underwriteReturnChange(huaanUnderwriteReturnDTO);
				break;
			case "HHBX":// 华海保险
				InquiryDTO hhbxInquiryDTO = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> hhbxMap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	  hhbxMap.put(entry.getKey(), (String) entry.getValue());
		           }
				 }
				UnderwriteReturnDTO hhbxUnderwriteReturnDTO = insuranceAPI.underwrite(hhbxInquiryDTO, hhbxMap);
				voteReturn = changeDataAction.underwriteReturnChange(hhbxUnderwriteReturnDTO);
				break;
			case "LIBAO":// 利宝保险
				InquiryDTO libaoInquiryDTO = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> libaoMap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   libaoMap.put(entry.getKey(), (String) entry.getValue());
		           }
				 }
				UnderwriteReturnDTO libaoUnderwriteReturnDTO = insuranceAPI.underwrite(libaoInquiryDTO, libaoMap);
				voteReturn = changeDataAction.underwriteReturnChange(libaoUnderwriteReturnDTO);
				break;
			case "CPIC"://太平洋保险	
				InquiryDTO inquiryDTOCpic = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> mapCpic = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   mapCpic.put(entry.getKey(), (String) entry.getValue());
				          }
				 }
				UnderwriteReturnDTO underwriteReturnDTOCpic = insuranceAPI.underwrite(inquiryDTOCpic, mapCpic);
				voteReturn = changeDataAction.underwriteReturnChange(underwriteReturnDTOCpic);
				break;
			case "CICP"://中华保险	
				InquiryDTO inquiryDTOCicp = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> mapCicp = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   mapCicp.put(entry.getKey(), (String) entry.getValue());
				          }
				 }
				UnderwriteReturnDTO underwriteReturnDTOCicp = insuranceAPI.underwrite(inquiryDTOCicp, mapCicp);
				voteReturn = changeDataAction.underwriteReturnChange(underwriteReturnDTOCicp);
				break;
				
			case "CCIC"://大地保险
				InquiryDTO inquiryDTOCCIC = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String, String> mapccic = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						mapccic.put(entry.getKey(), (String) entry.getValue());
					}
				}
				UnderwriteReturnDTO underwriteReturnDTOCCIC = insuranceAPI.underwrite(inquiryDTOCCIC, mapccic);
				voteReturn = changeDataAction.underwriteReturnChange(underwriteReturnDTOCCIC);
				break;
			case "PINGANHTTP":// 平安http
				voteReturn = httpPingAnCallAction.vote(owner.getInquiry().getInquiryId(), userId, insId, quotaId, owner, vote, rec, configMap);
				break;
			case "PICCHTTP":// 人保http
				voteReturn = httpPiccCallAction.vote(owner.getInquiry().getInquiryId(), userId, insId, quotaId, owner, vote, rec, configMap);
				break;
			case "ACIC" :
				InquiryDTO acicInquiryDTO = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> acicMap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   acicMap.put(entry.getKey(), (String) entry.getValue());
				          }
				 }
				UnderwriteReturnDTO acicUnderwriteReturnDTO = insuranceAPI.underwrite(acicInquiryDTO, acicMap);
				voteReturn = changeDataAction.underwriteReturnChange(acicUnderwriteReturnDTO);
				
				break;
			case "PICC":
				InquiryDTO piccInquiryDTO = changeDataAction.inquiryChange(owner.getInquiry().getInquiryId(), quotaId, orderId, owner, vote, rec, userId, disJsp, insId);
				Map<String,String> piccMap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   piccMap.put(entry.getKey(), (String) entry.getValue());
				          }
				 }
				UnderwriteReturnDTO piccUnderwriteReturnDTO = insuranceAPI.underwrite(piccInquiryDTO, piccMap);
				voteReturn = changeDataAction.underwriteReturnChange(piccUnderwriteReturnDTO);
				break;
			default:
				break;
			}
			return voteReturn;
		} catch (Exception e) {
			logger.error("用户:" + userId + "调用保险公司:" + insId + "投保接口失败:", e);
		}
		return null;
	}

	@Override
	public InterfaceMsg payCheck(String userId, String insId, String orderId) throws ActionException {
		try {
//			InsuranceDTO insTop = insService.getTop(insId);
			InterfaceMsg interfaceMsg = new InterfaceMsg();
//			if (null == insTop || StringUtils.isBlank(insTop.getInsId())) {
//				return null;
//			}
			switch (insId) {
			case "PAIC":
				OrderDTO orderDTO = orderService.getOrderById(microService.getMicroByUserId(userId)
					.getMicro_id(), insId, orderId);
				interfaceMsg.setErrorCode("0000");
				break;
			default:
				interfaceMsg.setErrorCode("0000");
				break;
			}
			return interfaceMsg;
		} catch (BusinessServiceException e) {
			logger.error("用户:" + userId + "调用保险公司:" + insId + "支付校验接口失败:", e);
		}
		return null;
	}

	@Override
	public CombinedQueryDTO combinedQuery(String userId, String insId, String orderId, String status) throws ActionException {
		logger.info("投保单综合查询==>userId:"+userId+",insId:"+insId+",orderId:"+orderId+",status:"+status);
		// 获取数据
		OrderDTO orderDTO = new OrderDTO();
		try {
			orderDTO = odrService.selectOrderByOrderIdAndInsId(orderId, insId);
		} catch (BusinessServiceException e) {
			logger.error("锦泰投保单综合查询接口查询订单失败", e);
			throw new ActionException("查询订单失败");
		}
		com.zxcl.webapp.dto.InquiryDTO inqu = new com.zxcl.webapp.dto.InquiryDTO();
		try {
			inqu = inquiryService.get(orderDTO.getInquiry().getInquiryId(), null);
		} catch (BusinessServiceException e2) {
			logger.error(e2.getMessage(),e2);
			throw new ActionException(e2.getMessage());
		}
		QuotaDTO quota = new QuotaDTO();
		try {
			quota = quotaService.getByQuotaId(orderDTO.getQuota().getQuotaId());
		} catch (BusinessServiceException e1) {
			logger.error(e1.getMessage(),e1);
			throw new ActionException(e1.getMessage());
		}
		String configId = "";
		try {
			configId = teamParamMappingService.getConfigId(insId, inqu.getUsageCode(), quota.getQuotaType(), userId);
		} catch (BusinessServiceException e1) {
			logger.error(e1.getMessage(),e1);
			throw new ActionException(e1.getMessage());
		}
		// 配置信息
		Map<String, Object> configMap = getConfigInfo(configId, insId);
		try {
//			InsuranceDTO insTop = insService.getTop(insId);
//			if (null == insTop || StringUtils.isBlank(insTop.getInsId())) {
//				return null;
//			}
			CombinedQueryDTO combined = new CombinedQueryDTO();
			switch (insId) {
			case "JTIC":
				InquiryDTO inquiryDTOJTIC = changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(), null, orderId, null, null, null, userId, null, insId);
				Map<String, String> mapJTIC = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						mapJTIC.put(entry.getKey(), (String) entry.getValue());
					}
				}
				OrderQueryReturnDTO orderQueryReturnDTO = insuranceAPI.orderQuery(inquiryDTOJTIC, mapJTIC, status);
				combined.setTciApplyStatus(orderQueryReturnDTO.getTciApplyStatus());
				combined.setTciPolicyNO(orderQueryReturnDTO.getTciPlyNo());
				combined.setVciApplyStatus(orderQueryReturnDTO.getVciApplyStatus());
				combined.setVciPolicyNO(orderQueryReturnDTO.getVciPlyNo());
				combined.setUndrOpinion(orderQueryReturnDTO.getUndrOpinion());
				combined.setInsId(insId);
				break;
			case "AICS":
				Map<String,String> acicmap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
				       if(entry.getValue() instanceof String){
				    	   acicmap.put(entry.getKey(), (String) entry.getValue());
				          }
				 }
				OrderQueryReturnDTO returnDTO = insuranceAPI.orderQuery(changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(),
						orderDTO.getQuota().getQuotaId(), orderId, null, null, null, userId, null, insId),
						acicmap, orderDTO.getQueryStage());
				if(StringUtils.equals(InsuranceAPI.OK, returnDTO.getErrorCode()) && "2".equals(returnDTO.getSupport())){
					//不支持查询.读取数据库的数据...
					combined.setErrorCode(InsuranceAPI.OK);
					combined.setErrorMessage("");
					combined.setInsId(insId);
					combined.setTciApplyStatus(orderDTO.getQueryStage());
					combined.setTciPolicyNO(orderDTO.getTciPlyNo());
					combined.setVciApplyStatus(orderDTO.getQueryStage());
					combined.setVciPolicyNO(orderDTO.getVciPlyNo());
				}else{
					combined = changeDataAction.orderQuery(returnDTO);
				}
				
				break;
			case "PAIC":
				InquiryDTO inquiryDTOPAIC = changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(), null, orderId, null, null, null, userId, null, insId);
				Map<String, String> mapPAIC = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						mapPAIC.put(entry.getKey(), (String) entry.getValue());
					}
				}
				OrderQueryReturnDTO orderQueryReturnDTO1 = insuranceAPI.orderQuery(inquiryDTOPAIC, mapPAIC, status);
				combined.setInsId(insId);
				combined.setTciApplyStatus(orderQueryReturnDTO1.getTciApplyStatus());
				combined.setTciPolicyNO(orderQueryReturnDTO1.getTciPlyNo());
				combined.setVciApplyStatus(orderQueryReturnDTO1.getVciApplyStatus());
				combined.setVciPolicyNO(orderQueryReturnDTO1.getVciPlyNo());
				combined.setUndrOpinion(orderQueryReturnDTO1.getUndrOpinion());
				break;
			case "PINGANHTTP":
				combined = httpPingAnCallAction.combinedQuery(userId, orderId, configMap);
				break;
			case "PICCHTTP":
				combined = httpPiccCallAction.combinedQuery(userId, orderId, configMap);
				break;
			case "FDCP":
				InquiryDTO fudeInquiryDTO = changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(), null, orderId, null, null, null, userId, null, insId);
				Map<String, String> fudeMap = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						fudeMap.put(entry.getKey(), (String) entry.getValue());
					}
				}
				
				OrderQueryReturnDTO fudeOrderQueryReturnDTO = insuranceAPI.orderQuery(fudeInquiryDTO, fudeMap, status);
				combined.setInsId(insId);
				combined.setTciApplyStatus(fudeOrderQueryReturnDTO.getTciApplyStatus());
				combined.setTciPolicyNO(fudeOrderQueryReturnDTO.getTciPlyNo());
				combined.setVciApplyStatus(fudeOrderQueryReturnDTO.getVciApplyStatus());
				combined.setVciPolicyNO(fudeOrderQueryReturnDTO.getVciPlyNo());
				combined.setUndrOpinion(fudeOrderQueryReturnDTO.getUndrOpinion());
				break;
			case "CPIC":
				InquiryDTO inquiryDTOCpic = changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(), null, orderId, null, null, null, userId, null, insId);
				Map<String, String> mapCpic = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						mapCpic.put(entry.getKey(), (String) entry.getValue());
					}
				}
				OrderQueryReturnDTO orderQueryReturnDTOCpic = insuranceAPI.orderQuery(inquiryDTOCpic, mapCpic, status);
				combined.setInsId(insId);
				combined.setTciApplyStatus(orderQueryReturnDTOCpic.getTciApplyStatus());
				combined.setTciPolicyNO(orderQueryReturnDTOCpic.getTciPlyNo());
				combined.setVciApplyStatus(orderQueryReturnDTOCpic.getVciApplyStatus());
				combined.setVciPolicyNO(orderQueryReturnDTOCpic.getVciPlyNo());
				combined.setUndrOpinion(orderQueryReturnDTOCpic.getUndrOpinion());
				break;
			case "HHBX":
				InquiryDTO hhbxInquiryDTO = changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(), null, orderId, null, null, null, userId, null, insId);
				Map<String,String> hhbxMap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   hhbxMap.put(entry.getKey(), (String) entry.getValue());
			       }
				}
				OrderQueryReturnDTO hhbxOrderQueryReturnDTO = insuranceAPI.orderQuery(hhbxInquiryDTO, hhbxMap, status);
				combined.setInsId(insId);
				combined.setTciApplyStatus(hhbxOrderQueryReturnDTO.getTciApplyStatus());
				combined.setTciPolicyNO(hhbxOrderQueryReturnDTO.getTciPlyNo());
				combined.setVciApplyStatus(hhbxOrderQueryReturnDTO.getVciApplyStatus());
				combined.setVciPolicyNO(hhbxOrderQueryReturnDTO.getVciPlyNo());
				combined.setUndrOpinion(hhbxOrderQueryReturnDTO.getUndrOpinion());
				break;
			case "LIBAO":
				InquiryDTO libaoInquiryDTO = changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(), null, orderId, null, null, null, userId, null, insId);
				Map<String,String> libaoMap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   libaoMap.put(entry.getKey(), (String) entry.getValue());
			       }
				}
				OrderQueryReturnDTO libaoOrderQueryReturnDTO = insuranceAPI.orderQuery(libaoInquiryDTO, libaoMap, status);
				combined.setInsId(insId);
				combined.setTciApplyStatus(libaoOrderQueryReturnDTO.getTciApplyStatus());
				combined.setTciPolicyNO(libaoOrderQueryReturnDTO.getTciPlyNo());
				combined.setVciApplyStatus(libaoOrderQueryReturnDTO.getVciApplyStatus());
				combined.setVciPolicyNO(libaoOrderQueryReturnDTO.getVciPlyNo());
				combined.setUndrOpinion(libaoOrderQueryReturnDTO.getUndrOpinion());
				break;
			case "HTIC":
				InquiryDTO inquiryDTOHTIC = changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(), null, orderId, null, null, null, userId, null, insId);
				Map<String, String> maphtic = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						maphtic.put(entry.getKey(), (String) entry.getValue());
					}
				}
				OrderQueryReturnDTO orderQueryReturnDTOHTIC = insuranceAPI.orderQuery(inquiryDTOHTIC, maphtic, status);
				combined.setInsId(insId);
				combined.setTciApplyStatus(orderQueryReturnDTOHTIC.getTciApplyStatus());
				combined.setTciPolicyNO(orderQueryReturnDTOHTIC.getTciPlyNo());
				combined.setVciApplyStatus(orderQueryReturnDTOHTIC.getVciApplyStatus());
				combined.setVciPolicyNO(orderQueryReturnDTOHTIC.getVciPlyNo());
				combined.setUndrOpinion(orderQueryReturnDTOHTIC.getUndrOpinion());
				break;
			case "YGBX":
				InquiryDTO inquiryDTOYGBX = changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(), null, orderId, null, null, null, userId, null, insId);
				Map<String, String> mapYGBX = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						mapYGBX.put(entry.getKey(), (String) entry.getValue());
					}
				}
				OrderQueryReturnDTO orderQueryReturnDTO2 = insuranceAPI.orderQuery(inquiryDTOYGBX, mapYGBX, status);
				combined.setInsId(insId);
				combined.setTciApplyStatus(orderQueryReturnDTO2.getTciApplyStatus());
				combined.setTciPolicyNO(orderQueryReturnDTO2.getTciPlyNo());
				combined.setVciApplyStatus(orderQueryReturnDTO2.getVciApplyStatus());
				combined.setVciPolicyNO(orderQueryReturnDTO2.getVciPlyNo());
				combined.setUndrOpinion(orderQueryReturnDTO2.getUndrOpinion());
				break;
			case "ACIC":
				InquiryDTO inquiryDTOACIC = changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(), null, orderId, null, null, null, userId, null, insId);
				Map<String, String> mapACIC = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						mapACIC.put(entry.getKey(), (String) entry.getValue());
					}
				}
				OrderQueryReturnDTO acicOrderQueryReturnDTO = insuranceAPI.orderQuery(inquiryDTOACIC, mapACIC, status);
				//因为安诚的投保单号有可能是等综合查询才有，所以需要这里更新下.
				if(StringUtils.equals(acicOrderQueryReturnDTO.getErrorCode(), InsuranceAPI.OK)){
					if(StringUtils.equals("1", inquiryDTOACIC.getTciInsureSign()) 
							&& StringUtils.equals("1", inquiryDTOACIC.getVciInsureSign()) 
							&& ( StringUtils.isBlank(inquiryDTOACIC.getOrder().getTciApplyNo()) || StringUtils.isBlank(inquiryDTOACIC.getOrder().getVciApplyNo()) )){
						orderDTO.setTciApplyNo(acicOrderQueryReturnDTO.getTciApplyNo());
						orderDTO.setVciApplyNo(acicOrderQueryReturnDTO.getVciApplyNo());
					}else if(StringUtils.equals("1", inquiryDTOACIC.getTciInsureSign())  && StringUtils.isBlank(inquiryDTOACIC.getOrder().getTciApplyNo())){
						orderDTO.setTciApplyNo(acicOrderQueryReturnDTO.getTciApplyNo());
					}else if(StringUtils.equals("1", inquiryDTOACIC.getVciInsureSign())  && StringUtils.isBlank(inquiryDTOACIC.getOrder().getVciApplyNo())){
						orderDTO.setVciApplyNo(acicOrderQueryReturnDTO.getVciApplyNo());
					}
					
					orderService.updateOrder(orderDTO);
				}
				combined.setTciApplyStatus(acicOrderQueryReturnDTO.getTciApplyStatus());
				combined.setTciPolicyNO(acicOrderQueryReturnDTO.getTciPlyNo());
				combined.setVciApplyStatus(acicOrderQueryReturnDTO.getVciApplyStatus());
				combined.setVciPolicyNO(acicOrderQueryReturnDTO.getVciPlyNo());
				combined.setUndrOpinion(acicOrderQueryReturnDTO.getUndrOpinion());
				combined.setInsId(insId);
				break;
			case "CICP":
				InquiryDTO inquiryDTOCicp = changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(), null, orderId, null, null, null, userId, null, insId);
				Map<String, String> mapCicp = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						mapCicp.put(entry.getKey(), (String) entry.getValue());
					}
				}
				OrderQueryReturnDTO orderQueryReturnDTOCicp = insuranceAPI.orderQuery(inquiryDTOCicp, mapCicp, status);
				combined.setInsId(insId);
				combined.setTciApplyStatus(orderQueryReturnDTOCicp.getTciApplyStatus());
				combined.setTciPolicyNO(orderQueryReturnDTOCicp.getTciPlyNo());
				combined.setVciApplyStatus(orderQueryReturnDTOCicp.getVciApplyStatus());
				combined.setVciPolicyNO(orderQueryReturnDTOCicp.getVciPlyNo());
				combined.setUndrOpinion(orderQueryReturnDTOCicp.getUndrOpinion());
				break;
			case "PICC":
				InquiryDTO inquiryDTOPicc = changeDataAction.inquiryChange(orderDTO.getInquiry().getInquiryId(), null, orderId, null, null, null, userId, null, insId);
				Map<String, String> mapPicc = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
					if (entry.getValue() instanceof String) {
						mapPicc.put(entry.getKey(), (String) entry.getValue());
					}
				}
				OrderQueryReturnDTO orderQueryReturnDTOPicc = insuranceAPI.orderQuery(inquiryDTOPicc, mapPicc, status);
				combined.setInsId(insId);
				combined.setTciApplyStatus(orderQueryReturnDTOPicc.getTciApplyStatus());
				combined.setTciPolicyNO(orderQueryReturnDTOPicc.getTciPlyNo());
				combined.setVciApplyStatus(orderQueryReturnDTOPicc.getVciApplyStatus());
				combined.setVciPolicyNO(orderQueryReturnDTOPicc.getVciPlyNo());
				combined.setUndrOpinion(orderQueryReturnDTOPicc.getUndrOpinion());
				break;
			default:
				break;
			}

			return combined;
		} catch (Exception e) {
			logger.error("用户:" + userId + "调用保险公司:" + insId + "支付校验接口失败:", e);
		}
		return null;
	}

	@Override
	public WeChatPayDTO weChatPay(String userId, String insId, String orderId, String callBackUrl) throws ActionException {

		try {
//			InsuranceDTO insTop = insService.getTop(insId);
//			if (null == insTop || StringUtils.isBlank(insTop.getInsId())) {
//				return null;
//			}
			WeChatPayDTO packet = new WeChatPayDTO();
			switch (insId) {
			default:
				break;
			}
			return packet;
		} catch (Exception e) {
			logger.error("用户:" + userId + "调用保险公司:" + insId + "支付校验接口失败:", e);
		}
		return null;
	}

	@Override
	public InterfaceMsg paySueccessInform(String userId, String insId, String orderId) throws ActionException {

		try {
//			InsuranceDTO insTop = insService.getTop(insId);
//			if (null == insTop || StringUtils.isBlank(insTop.getInsId())) {
//				return null;
//			}
			InterfaceMsg interfaceMsg = new InterfaceMsg();
			switch (insId) {
			default:
				break;
			}
			return interfaceMsg;
		} catch (Exception e) {
			logger.error("用户:" + userId + "调用保险公司:" + insId + "支付校验接口失败:", e);
		}
		return null;
	}

	@Override
	public PayReturnDTO pay(String callBackUrl, String requestSource, String quotaId, String insId, String userId, String inquiryId, String orderId) throws ActionException {
		com.zxcl.webapp.dto.InquiryDTO inqu = new com.zxcl.webapp.dto.InquiryDTO();
		try {
			inqu = inquiryService.get(inquiryId, null);
		} catch (BusinessServiceException e2) {
			logger.error(e2.getMessage(),e2);
			throw new ActionException(e2.getMessage());
		}
		QuotaDTO quota = new QuotaDTO();
		try {
			quota = quotaService.getByQuotaId(quotaId);
		} catch (BusinessServiceException e1) {
			logger.error(e1.getMessage(),e1);
			throw new ActionException(e1.getMessage());
		}
		String configId = "";
		try {
			configId = teamParamMappingService.getConfigId(insId, inqu.getUsageCode(), quota.getQuotaType(), userId);
		} catch (BusinessServiceException e1) {
			logger.error(e1.getMessage(),e1);
			throw new ActionException(e1.getMessage());
		}
		// 配置信息
		Map<String, Object> configMap = getConfigInfo(configId, insId);
		PayReturnDTO payReturnDTO = new PayReturnDTO();
		try {
//			InsuranceDTO insTop = insService.getTop(insId);
			switch (insId) {
			case "TPIC":
				InquiryDTO inquiryDTO1 = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> map1 = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   map1.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTO1 = insuranceAPI.pay(inquiryDTO1, map1);
				payReturnDTO = changeDataAction.payReturnChange(payReturnDTO1);
				break;
			case "PAIC":
				InquiryDTO inquiryDTO3 = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> map3 = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   map3.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTO3 = insuranceAPI.pay(inquiryDTO3, map3);
				payReturnDTO = changeDataAction.payReturnChange(payReturnDTO3);
				break;
			case "TPBX":
				InquiryDTO inquiryDTO = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> map = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   map.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTO2 = insuranceAPI.pay(inquiryDTO, map);
				payReturnDTO = changeDataAction.payReturnChange(payReturnDTO2);
				break;
			case "HACP":
				InquiryDTO huaaninquiryDTO = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> huaanmap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   huaanmap.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO huaanpayReturnDTO = insuranceAPI.pay(huaaninquiryDTO, huaanmap);
				payReturnDTO = changeDataAction.payReturnChange(huaanpayReturnDTO);
				break;
			case "HTIC"://华泰保险
				InquiryDTO huataiInquiryDTO = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> huataiMap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   huataiMap.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO huataiPayReturnDTO = insuranceAPI.pay(huataiInquiryDTO, huataiMap);
				payReturnDTO = changeDataAction.payReturnChange(huataiPayReturnDTO);
				break;	
			case "HHBX"://华海保险
				InquiryDTO hhbxInquiryDTO = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> hhbxMap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   hhbxMap.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO hhbxPayReturnDTO = insuranceAPI.pay(hhbxInquiryDTO, hhbxMap);
				payReturnDTO = changeDataAction.payReturnChange(hhbxPayReturnDTO);
				break;
			case "LIBAO"://利宝保险
				InquiryDTO libaoInquiryDTO = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> libaoMap = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   libaoMap.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO libaoPayReturnDTO = insuranceAPI.pay(libaoInquiryDTO, libaoMap);
				payReturnDTO = changeDataAction.payReturnChange(libaoPayReturnDTO);
				break;
			case "CCIC"://大地保险
				payReturnDTO = intfDaDiCallAction.pay(userId, insId, orderId, callBackUrl, configMap);
				break;	
			case "PINGANHTTP"://平安HTTP保险
				payReturnDTO = httpPingAnCallAction.pay(userId, insId, orderId, callBackUrl, configMap);
				break;
			case "CPIC"://太平洋保险
				InquiryDTO inquiryDTOCpic = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> mapCpic = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   mapCpic.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTOCpic = insuranceAPI.pay(inquiryDTOCpic, mapCpic);
				payReturnDTO = changeDataAction.payReturnChange(payReturnDTOCpic);
				break;
			case "YGBX"://阳光保险
				InquiryDTO inquiryDTOYgbx = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> mapYgbx = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   mapYgbx.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTOYgbx = insuranceAPI.pay(inquiryDTOYgbx, mapYgbx);
				payReturnDTO = changeDataAction.payReturnChange(payReturnDTOYgbx);
				break;
			case "ACIC":
				InquiryDTO inquiryDTOacic = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> mapacic = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   mapacic.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTOAcic = insuranceAPI.pay(inquiryDTOacic, mapacic);
				payReturnDTO = changeDataAction.payReturnChange(payReturnDTOAcic);
				break;
			case "CICP"://中华保险
				InquiryDTO inquiryDTOCicp = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> mapCicp = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   mapCicp.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTOCicp = insuranceAPI.pay(inquiryDTOCicp, mapCicp);
				payReturnDTO = changeDataAction.payReturnChange(payReturnDTOCicp);
				break;
			case "PICC"://人保
				InquiryDTO inquiryDTOPicc = changeDataAction.inquiryChange(inquiryId, quotaId, orderId, null, null, null, userId, null, insId);
				Map<String,String> mapPicc = new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   mapPicc.put(entry.getKey(), (String) entry.getValue());
			        }
				 }
				com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTOPicc = insuranceAPI.pay(inquiryDTOPicc, mapPicc);
				payReturnDTO = changeDataAction.payReturnChange(payReturnDTOPicc);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			if("true".equals(e.getMessage())){
				payReturnDTO.setType("1");
				return payReturnDTO;
			}
			logger.error("用户:" + userId + "调用保险公司:" + insId + "支付校验接口失败:", e);
		}
		return payReturnDTO;
	}


	public String toString(String[] str1, String str2) {
		String str = "";
		for (int i = 0; i < str1.length; i++) {
			str += str1[i];
			if (i < (str1.length - 1)) {
				str += str2;
			}
		}
		return str;
	}

	/**
	 * 获取保险公司登录用户名
	 * 
	 * @param insId
	 * @return
	 */
	private String getUserNameByInsId(String insId, Map<String, Object> configMap) throws ActionException {
		if (null != insId && insId.toUpperCase().indexOf("HTTP") == -1) {
			return insId;
		}
//		// 配置信息
//		Map<String, Object> configMap = getConfigInfo(userId, insId);
//		if (MapUtils.isEmpty(configMap)) {
//			logger.error("获取保险公司配置信息失败");
//			throw new ActionException("获取保险公司配置信息失败");
//		}

		switch (insId) {
		case "CICPHTTP":// 中华保险
			return configMap.get("CICPHTTP_AgentUserId").toString();
		case "HACPHTTP":// 华安保险
			return configMap.get("HACPHTTP_USERNAME").toString();
		case "TPICHTTP":// 太平保险
			return configMap.get("TPICHTTP_userName").toString();
		case "AICSHTTP":// 永城保险Http
			return configMap.get("AICS_OPERATORCODE").toString();
		case "PICCHTTP":// 人保http
			return configMap.get("PICCHTTP_LOGIN_NAME").toString();
		case "PINGANHTTP":// 平安http
			return configMap.get("PINGANHTTP_USERNAME").toString();
		case "XDCPHTTP":// 信达保险
			return configMap.get("XDCPHTTP_USERNAME").toString();
		case "XDCPHTTP01":// 信达保险
			return configMap.get("XDCPHTTP_USERNAME").toString();
		case "MACNHTTP":// 民安保险
			return configMap.get("MACNHTTP_GuMainHandlerCode").toString();	
		case "HHBXHTTP":// 华海保险
			return configMap.get("HHBXHTTP_LoginUser").toString();	
		case "YINGDAHTTP":// 英大保险
			return configMap.get("YINGDAHTTP_USERNAME").toString();	
		case "TPBXHTTP":// 英大保险
			return configMap.get("TPBXHTTP_AgentUserId").toString();	
		}
		return null;
	}

	/**
	 * 报价次数统计
	 * 
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @throws ActionException
	 * @param userType
	 *            用户类型 1：保行天下，2保险公司
	 */
	private void quotasPreWithHTTP(String userId, String inquiryId, String insId, String userType) throws ActionException {
		try {
			// 报价统计
			IntfMicroInsConfigDTO config = null;
			IntfMicroInsConfigDTOKey configKey = null;
			if ("2".equals(userType)) {

				logger.info("保险公司报价次数限制开始.");
				configKey = new IntfMicroInsConfigDTOKey(userId, insId, userType);
				config = mIMICDao.selectByPrimaryKey(configKey);
				if (null == config) {

					// 第一次报价
					logger.info("保险公司用户名" + userId + "第一次保险公司" + insId + "报价");
					config = new IntfMicroInsConfigDTO(userId, insId, 1, 1, userType);
					mIMICDao.insertSelective(config);
				} else {
					logger.info(config.toString());
					Calendar calendar = Calendar.getInstance();

					// 报价未限制次数
					if (config.getTodayMaxCount().equals(-1)) {
						logger.info("保险公司用户名" + userId + ",保险公司" + insId + "未限制报价次数");
						config.setTotalCount(config.getTotalCount() + 1);

						int nowDay = calendar.get(Calendar.DAY_OF_YEAR);
						calendar.setTime(config.getUpdTm());
						int uptDay = calendar.get(Calendar.DAY_OF_YEAR);

						// 是否在当前天
						if (nowDay == uptDay) {
							config.setTodayCount(config.getTodayCount() + 1);
						} else {
							config.setTodayCount(1);
						}
						mIMICDao.updateByPrimaryKeySelective(config);
					} else {

						// 报价限制次数开始
						int nowDay = calendar.get(Calendar.DAY_OF_YEAR);
						calendar.setTime(config.getUpdTm());
						int uptDay = calendar.get(Calendar.DAY_OF_YEAR);
						config.setTotalCount(config.getTotalCount() + 1);

						// 新的一天重置次数
						if (nowDay != uptDay) {
							config.setTodayCount(1);
							mIMICDao.updateByPrimaryKeySelective(config);
						} else {

							// 当前天限制次数
							if (config.getTodayCount() >= config.getTodayMaxCount()) {
								logger.info("当前保险公司用户名当日报价次数已达上限.userID=" + config.getUserId() + ",TodayMaxCount=" + config.getTodayMaxCount());
								throw new QuotaMaxCountException("当前保险公司今日报价次数已达上限");
							} else {
								config.setTodayCount(config.getTodayCount() + 1);
								mIMICDao.updateByPrimaryKeySelective(config);
							}
						}
					}
				}
				logger.info("报价次数限制结束.");
			}

			if ("1".equals(userType)) {
				logger.info("小微报价次数统计开始.");
				configKey = new IntfMicroInsConfigDTOKey(userId, insId, userType);
				config = mIMICDao.selectByPrimaryKey(configKey);
				if (null == config) {

					// 第一次报价
					logger.info("用户" + userId + "第一次保险公司" + insId + "报价");
					config = new IntfMicroInsConfigDTO(userId, insId, 1, 1, userType);
					config.setTodayMaxCount(30);// 小微默认今日最大报价次数为10
					mIMICDao.insertSelective(config);
				} else {
					logger.info(config.toString());
					Calendar calendar = Calendar.getInstance();

					// 报价未限制次数
					if (config.getTodayMaxCount().equals(-1)) {
						logger.info("用户" + userId + ",保险公司" + insId + "未限制报价次数");
						config.setTotalCount(config.getTotalCount() + 1);

						int nowDay = calendar.get(Calendar.DAY_OF_YEAR);
						calendar.setTime(config.getUpdTm());
						int uptDay = calendar.get(Calendar.DAY_OF_YEAR);

						// 是否在当前天
						if (nowDay == uptDay) {
							config.setTodayCount(config.getTodayCount() + 1);
						} else {
							config.setTodayCount(1);
						}
						mIMICDao.updateByPrimaryKeySelective(config);
					} else {

						// 报价限制次数开始
						int nowDay = calendar.get(Calendar.DAY_OF_YEAR);
						calendar.setTime(config.getUpdTm());
						int uptDay = calendar.get(Calendar.DAY_OF_YEAR);
						config.setTotalCount(config.getTotalCount() + 1);

						// 新的一天重置次数
						if (nowDay != uptDay) {
							config.setTodayCount(1);
							mIMICDao.updateByPrimaryKeySelective(config);
						} else {

							// 当前天限制次数
							if (config.getTodayCount() >= config.getTodayMaxCount()) {
								logger.info("当前用户当日报价次数已达上限.userID=" + config.getUserId());
								throw new QuotaMaxCountException("当前用户今日报价次数已达上限");
							} else {
								config.setTodayCount(config.getTodayCount() + 1);
								mIMICDao.updateByPrimaryKeySelective(config);
							}
						}
					}
				}
				logger.info("报价统计结束，继续报价.");
			}
			// }

		} catch(QuotaMaxCountException e){
			throw e;
		} catch (Exception e) {
			logger.error("报价次数统计方法失败", e);
			throw new ActionException("报价次数统计方法失败");
		}
	}

	@Override
	public WeChatPayDTO unionPay(String userId, String insId, String orderId, String callBackUrl) throws ActionException {

		try {
//			InsuranceDTO insTop = insService.getTop(insId);
//			if (null == insTop || StringUtils.isBlank(insTop.getInsId())) {
//				return null;
//			}
			WeChatPayDTO packet = new WeChatPayDTO();
			switch (insId) {
			default:
				break;
			}
			return packet;
		} catch (Exception e) {
			logger.error("用户:" + userId + "调用保险公司:" + insId + "支付校验接口失败:", e);
		}
		return null;
	}

	@Override
	public com.zxcl.bxtx.dto.intf.PayReturnDTO pay(String quoteId,String insId, String userId, String inquiryId, String orderId)
			throws ActionException {
		com.zxcl.webapp.dto.InquiryDTO inqu = new com.zxcl.webapp.dto.InquiryDTO();
		try {
			inqu = inquiryService.get(inquiryId, null);
		} catch (BusinessServiceException e2) {
			logger.error(e2.getMessage(),e2);
			throw new ActionException(e2.getMessage());
		}
		QuotaDTO quota = new QuotaDTO();
		try {
			quota = quotaService.getByQuotaId(quoteId);
		} catch (BusinessServiceException e1) {
			logger.error(e1.getMessage(),e1);
			throw new ActionException(e1.getMessage());
		}
		String configId = "";
		try {
			configId = teamParamMappingService.getConfigId(insId, inqu.getUsageCode(), quota.getQuotaType(), userId);
		} catch (BusinessServiceException e1) {
			logger.error(e1.getMessage(),e1);
			throw new ActionException(e1.getMessage());
		}
		// 配置信息
		Map<String, Object> configMap = getConfigInfo(configId, insId);
		com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTO = new com.zxcl.bxtx.dto.intf.PayReturnDTO();
		try {
			InquiryDTO inquiryDTO = changeDataAction.inquiryChange(inquiryId, quoteId, orderId, null, null, null, userId, null, insId);
			Map<String,String> map = new HashMap<String,String>();
			for (Map.Entry<String, Object> entry : configMap.entrySet()) {
			       if(entry.getValue() instanceof String){
			    	   map.put(entry.getKey(), (String) entry.getValue());
			       }
			 }
			payReturnDTO = insuranceAPI.pay(inquiryDTO, map);
		} catch (Exception e) {
			logger.error(insId+"调用支付接口失败",e);
			throw new ActionException(insId+"调用支付接口失败");
		}
		return payReturnDTO;
	}

	@Override
	public Map<String, Object> voteQuery(String userId, String inquiryId,
			String insId) throws ActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleReturnDTO> vehicleQuery(String vehicleFrameNo,String userId) {
		List<VehicleReturnDTO> vehicleReturnDTOs = new ArrayList<VehicleReturnDTO>();
		//小微信息
		String microId = "";
		MicroDTO microDTO = new MicroDTO();
		try {
			microDTO = microService.getMicroByUserId(userId);
			microId = microDTO.getMicro_id();
		} catch (BusinessServiceException e) {
			logger.error("查询小微失败", e);
		}
		ClientUser clientUser = new ClientUser();
		clientUser.setXiaoweiUserId(microId);
		try {
			ParamConfigDTO pinganConfigId = paramConfigService.getParamConfigByKey("PINGAN_CAR_CONFIG_ID");
			Map<String,Object> configMap = insuranceCompanyConfigService.getMapByInsId(pinganConfigId.getValue());
			HashMap<String, String> userConfigMap = new HashMap<String, String>();
			userConfigMap.put("PINGANHTTP_PARTNERWORKNETCODE", CommonUtil.valueOf(configMap.get("PINGANHTTP_PARTNERWORKNETCODE")));//合作网点代码必传
			clientUser.setMap(userConfigMap);
			clientUser.setAgentUserId(CommonUtil.valueOf(configMap.get("PINGAN_CAR_AGENTUSERID")));
			clientUser.setAgentUserPwd(CommonUtil.valueOf(configMap.get("PINGAN_CAR_AGENTUSERPWD")));
		} catch (BusinessServiceException e) {
			logger.error("根据车架号获取车型信息查询配置信息失败",e);
		}
		FrmQueryRespDTO frmQueryRespDTO = pinganFrmQueryService.queryModelByFrm(clientUser, vehicleFrameNo);
		if(null != frmQueryRespDTO && frmQueryRespDTO.isSuccess() && null != frmQueryRespDTO.getQueryList() && frmQueryRespDTO.getQueryList().size() > 0){
			for (FrmQueryDetailDTO frmQueryDetailDTO : frmQueryRespDTO.getQueryList()) {				
				VehicleReturnDTO vehicleReturnDTO = new VehicleReturnDTO();
				vehicleReturnDTO.setVehicleName(frmQueryDetailDTO.getAutoModelName());
				BigDecimal bd = new BigDecimal(frmQueryDetailDTO.getExhaustMeasure());
				bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
				vehicleReturnDTO.setConfigDesc(frmQueryDetailDTO.getFirstSaleDate()+"款 "+bd+"L "+frmQueryDetailDTO.getPurchasePrice()+"元");
				
				vehicleReturnDTO.setModelCode(frmQueryDetailDTO.getAutoModelCode());
				vehicleReturnDTO.setMarketDate(frmQueryDetailDTO.getFirstSaleDate());
				vehicleReturnDTO.setVehiclePrice(frmQueryDetailDTO.getPurchasePrice());
				vehicleReturnDTO.setModelCodeType("B");
				vehicleReturnDTO.setSeatNum(frmQueryDetailDTO.getSeats());
				vehicleReturnDTO.setRemark(frmQueryDetailDTO.getAutoModelName() +" "+ frmQueryDetailDTO.getRemark() +" "+frmQueryDetailDTO.getSeats()+"座 "+vehicleReturnDTO.getConfigDesc());
				vehicleReturnDTO.setDisplacement(frmQueryDetailDTO.getExhaustMeasure());
				vehicleReturnDTOs.add(vehicleReturnDTO);
			}
		}
		return vehicleReturnDTOs;
	}

}
