package com.zxcl.webapp.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import bxtx.intf.weixin.biz.service.WeiXinService;

import com.alibaba.fastjson.JSONObject;
import com.meyacom.fw.app.web.controller.BaseController;
import com.meyacom.fw.um.security.crypto.password.SaltSHAPasswordEncoder;
import com.zxcl.bxtx.dto.intf.VehicleReturnDTO;
import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.exception.QuotaMaxCountException;
import com.zxcl.webapp.biz.service.ActivityService;
import com.zxcl.webapp.biz.service.AgencyService;
import com.zxcl.webapp.biz.service.AreaService;
import com.zxcl.webapp.biz.service.BaseService;
import com.zxcl.webapp.biz.service.CorrespondService;
import com.zxcl.webapp.biz.service.CoverageItemService;
import com.zxcl.webapp.biz.service.DistributionService;
import com.zxcl.webapp.biz.service.DriverService;
import com.zxcl.webapp.biz.service.IdentityInfoService;
import com.zxcl.webapp.biz.service.InquiryFileRequireService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsMsgMatchService;
import com.zxcl.webapp.biz.service.InsXmlService;
import com.zxcl.webapp.biz.service.InsuranceCompanyConfigService;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.service.ManualQuotationTaskService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.OwnerService;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.biz.service.PlatformService;
import com.zxcl.webapp.biz.service.QuotaDetailedService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.service.QuotnConfigService;
import com.zxcl.webapp.biz.service.RecognizeeService;
import com.zxcl.webapp.biz.service.ResourceVehicleCvrgService;
import com.zxcl.webapp.biz.service.ResourceVehicleService;
import com.zxcl.webapp.biz.service.TeamAreaService;
import com.zxcl.webapp.biz.service.TeamParamMappingService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.VehicleModelService;
import com.zxcl.webapp.biz.service.VoteInsuranceService;
import com.zxcl.webapp.biz.service.WalletActiveService;
import com.zxcl.webapp.biz.service.WeChatService;
import com.zxcl.webapp.biz.util.BaseFinal;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.biz.util.model.BaseParam;
import com.zxcl.webapp.biz.util.resultEntity.ResultMap;
import com.zxcl.webapp.dto.ActivityDTO;
import com.zxcl.webapp.dto.AgentServiceControlDTO;
import com.zxcl.webapp.dto.AreaDTO;
import com.zxcl.webapp.dto.BaseDTO;
import com.zxcl.webapp.dto.DistributionDTO;
import com.zxcl.webapp.dto.DriverDTO;
import com.zxcl.webapp.dto.FeeDTO;
import com.zxcl.webapp.dto.FeeRulesInsDateDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.InquiryFileRequireDTO;
import com.zxcl.webapp.dto.ManualQuotationTaskDTO;
import com.zxcl.webapp.dto.MicroApproveDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.ParamConfigDTO;
import com.zxcl.webapp.dto.PlatformDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.QuotaDetailedDTO;
import com.zxcl.webapp.dto.QuoteTrackDTO;
import com.zxcl.webapp.dto.QuotedDTO;
import com.zxcl.webapp.dto.QuotnConfigDTO;
import com.zxcl.webapp.dto.ResourceVehicleCvrgDTO;
import com.zxcl.webapp.dto.TeamParamMappingDTO;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.dto.VoInquiryCustomerDTO;
import com.zxcl.webapp.dto.bizdto.ResourceVehicleDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.OwnerDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.resp.VoteInsuranceReturnDTO;
import com.zxcl.webapp.dto.webdto.InitialDTO;
import com.zxcl.webapp.dto.webdto.VoteReturnDTO;
import com.zxcl.webapp.integration.dao.AgentServiceControlDAO;
import com.zxcl.webapp.integration.dao.FeeRulesDAO;
import com.zxcl.webapp.integration.dao.FeeRulesInsDateDAO;
import com.zxcl.webapp.integration.dao.InquiryCustomerDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.util.Common;
import com.zxcl.webapp.web.vo.AjaxResult;

@Controller
@SessionAttributes(WebAttributes.AUTHENTICATION_EXCEPTION)
public class OrderController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	// 车型
	@Autowired
	private VehicleModelService vehicleService;
	// 验证密码
	@Autowired
	private SaltSHAPasswordEncoder saltSHA1;
	// 平台基础信息和保险公司对照
	@Autowired
	private CorrespondService corrService;
	// 小微
	@Autowired
	private MicroService microService;
	@Autowired
	private FeeRulesDAO feeRulesDAO;
	@Autowired
	private FeeRulesInsDateDAO feeRulesInsDateDAO;
	// 询价单
	@Autowired
	private InquiryService inquiryService;
	// 驾驶员
	@Autowired
	private DriverService driverService;
	// 报价单
	@Autowired
	private QuotaService quotaService;
	// 保险公司
	@Autowired
	private InsuranceService insuranceService;
	// 报价明细
	@Autowired
	private QuotaDetailedService detailedService;
	// 地区
	@Autowired
	private AreaService areaService;
	// 平台基础信息
	@Autowired
	private PlatformService platformService;
	// 订单
	@Autowired
	private OrderService orderService;
	// 车主
	@Autowired
	private OwnerService ownerService;
	// 配送
	@Autowired
	private DistributionService disService;
	// 被保人
	@Autowired
	private RecognizeeService recService;
	// 投保人
	@Autowired
	private VoteInsuranceService voteService;
	// 微信
	@Autowired
	private WeChatService weChatService;
	// 用户
	@Autowired
	private UserService userService;
	// 保险公司接口
	@Autowired
	private CallAction callAction;
	// 中介
	@Autowired
	private AgencyService agencyService;

	@Autowired
	private TeamAreaService teamAreaService;
	// 车辆信息
	@Autowired
	private ResourceVehicleService resourceVehicleService;
	// 险别
	@Autowired
	private CoverageItemService coverItemService;

	@Autowired
	private InsXmlService insXmlService;
	
	@Autowired
	private InquiryCustomerDAO custDAO;
	
	@Autowired
	private AgentServiceControlDAO agentServiceControlDAO;
	
	@Autowired
	private WalletActiveService walletActiveService;
	
	@Autowired
	private ResourceVehicleCvrgService resourceVehicleCvrgService;
	
	@Autowired
	private ParamConfigService paramConfigService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private MicroDAO microDAO;
	
	private static final String IS_SEARCH_RIM_VECLEIL = "IS_SEARCH_RIM_VECLEIL";
	
	@Value("${vehicleQueryInsId}")
	private String vehicleQueryInsId;

	@Value("${fileserverUrl}")
	private String fileserverUrl;
	
	@Value("${share.url}")
    private String currentServerUrl;
	
	@Value("${mp.weixin.bind.url}")
	private String bindUrl;
	
	@Autowired
	private IdentityInfoService identityInfoService;
	
	@Autowired
	private InquiryFileRequireService inquiryFileRequireService;

	@Autowired
	private InsMsgMatchService insMsgMatchService;
	
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private QuotnConfigService quotnConfigService;
	
	@Autowired
	private ManualQuotationTaskService manualQuotationTaskService;
	
	@Autowired
	private InsuranceCompanyConfigService insuranceCompanyConfigService;
	
	@Autowired
	private TeamParamMappingService teamParamMappingService;
	
	@Autowired
	private WeiXinService weiXinService;
	
	public OrderController() {
	}

	
	/**
	 * 
	 */
	@RequestMapping(value = "/index.do")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		if (logger.isInfoEnabled()) {
			logger.info("当前登录用户为" + this.getAuthenticatedUserId());
		}
		
		UserDTO userDTO = null;

		MicroDTO microDTO = null;
		
		//带nocheck参数不会走微信绑定验证,不带nocheck会走微信绑定验证
		if(StringUtils.isBlank(request.getParameter("nocheck"))){
			boolean isBindWeixin = true;//默认绑定
			try {
				userDTO = weChatService.queryUserByUserId(this.getAuthenticatedUserId());
				if (StringUtils.isBlank(userDTO.getWechartId())) {
					logger.info("该用户未绑定微信");
					isBindWeixin = false;
				}else{
					logger.info("该用户绑定了微信，微信号为" + userDTO.getWechartId());
					isBindWeixin = true;
				}
			}catch (Exception e) {
				logger.error("查询用户信息失败", e);
			}
			
			
			if(!isBindWeixin){
				String userAgent = request.getHeader("User-Agent");
				try {
					if(StringUtils.isNotBlank(userAgent) && userAgent.toLowerCase().indexOf("micromessenger") != -1){
						logger.debug("确定为微信浏览器");
						
						/*private static String redrurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
								+ WeixinUtil.appid
								+ "&redirect_uri="
								+ WeixinUtil.bind_url
								+ "&response_type=code&scope=snsapi_userinfo&state=1&connect_redirect=1#wechat_redirect";
						*/
						String redrurl = weiXinService.oauth2buildAuthorizationUrl(bindUrl, "snsapi_userinfo", "1");
						
						response.sendRedirect(redrurl);
						return null;
					}
				} catch (Exception e) {
					logger.error("微信绑定==>重定向微信失败", e);
				}
			}
		}
		
		try {
			microDTO = microService.getMicroByUserId(getAuthenticatedUserId());
		} catch (BusinessServiceException e) {
			logger.error("查询小微失败",e);
		}
		if(null == microDTO){
			return "logon";
		}
		
		Map<String, String> personality= new HashMap<String, String>();
		try {
			personality=microService.getPersonalityByMicId(microDTO);
			//如果代理机构或者团队都不存在电话号码  使用保行天下的客服电话
			HttpSession session = request.getSession();
			if(StringUtils.isNotBlank(personality.get("agtTeamTel"))){
				session.setAttribute("tel", personality.get("agtTeamTel"));
			}else if(StringUtils.isNotBlank(personality.get("agentTel"))){
				session.setAttribute("tel", personality.get("agentTel"));
			}else{
				session.setAttribute("tel", "4009691365");
			}
			
			if(StringUtils.isNotBlank(personality.get("agentName"))){
				session.setAttribute("agentName",personality.get("agentName"));
			}
			if(StringUtils.isNotBlank(personality.get("teamAliasName"))){
					session.setAttribute("teamName",personality.get("teamAliasName"));
			}else if(StringUtils.isNotBlank(personality.get("teamName"))){
				session.setAttribute("teamName",personality.get("teamName"));
			}
			
			model.addAttribute("teamSwitch", agencyService.getSwitchByTeamId(microDTO.getAgt_team_id()));
			model.addAttribute("telMap", personality);
			model.addAttribute("agentName",StringUtils.isNotBlank(personality.get("agentName"))?personality.get("agentName"):"");
			
		} catch (BusinessServiceException e) {
			logger.error("查询信息失败",e);
		}
		

		try {
			String areaCode = teamAreaService.getAreaCodeByUserId(getAuthenticatedUserId());
			if(StringUtils.isNotBlank(areaCode)){
				model.addAttribute("protocolArea", areaService.getCityByCode(areaCode));
			}else{
				return "redirect:/sysalter.do?msg=%E8%AF%B7%E8%81%94%E7%B3%BB%E7%BB%B4%E6%8A%A4%E4%BA%BA%E5%91%98%E8%AE%BE%E7%BD%AE%E5%9B%A2%E9%98%9F%E5%8C%BA%E5%9F%9F%E3%80%82";
			}
		} catch (Exception e) {
			logger.error("获取用户protocol失败", e);
		}
		
		try {
			List<ActivityDTO> activitys = activityService.getOngoingActivity(microDTO);
			model.addAttribute("activitys", activitys);
		} catch (Exception e) {
			logger.error("查询活动失败", e);
		}
		
		model.addAttribute("dynamicCotent", paramConfigService.getValueByKey("BXTX_INDEX_DYNAMICCONTENT_CONFIG"));
		
		return "flows.index";
	}


	/**
	 * 近期录入查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/closet_inquiry.do")
	@ResponseBody
	public ResultMap peoplePrice(HttpServletRequest request,HttpServletResponse response, String plateNo) {
		plateNo = plateNo.toUpperCase();
		List<InquiryDTO> inquiryList = null;
		List<InquiryDTO> inquiryList2 = new ArrayList<InquiryDTO>();
		ResultMap resultMap = new ResultMap();
		BaseParam baseParam = new BaseParam(getAuthenticatedUserId());
		baseParam.setWd(plateNo);
		try {
			inquiryList = inquiryService.getclosetInquiry(baseParam);
			boolean i = true;
			boolean y = true;
			if(CollectionUtils.isNotEmpty(inquiryList)){
				for(InquiryDTO item : inquiryList){
					//判断状态
					String [] queryStages = orderService.getQueryStageByInquiryId(item.getInquiryId());
					for (String queryStage : queryStages) {
						if("5".equals(queryStage) || "6".equals(queryStage)){//已支付过就不能修改信息
							i = false;
						}
					}
					if(i){
						//判断时间是否过期
						MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
						InquiryDTO inquiryDTO = null;
						if(null != microDTO){				
							inquiryDTO = inquiryService.get(item.getInquiryId(), microDTO.getMicro_id());
						}
						if(null != inquiryDTO){
							//起保时间是否已经过期
							Date nowDate=DateUtil.dateToDate("yyyy-MM-dd", new Date());
							if(null!=inquiryDTO.getTciStartDate()&&inquiryDTO.getTciStartDate().getTime()<=nowDate.getTime()){//交强
								y = false;
							}
							if(null!=inquiryDTO.getVciStartDate()&&inquiryDTO.getVciStartDate().getTime()<=nowDate.getTime()){//商业
								y = false;
							}
							if(y){
								item.setState(item.getState());
							}else{//过期了状态都为1（报价暂存）
								item.setState("1");
							}
						}
						item.setOwnerName(StringUtils.isNotBlank(item.getOwnerName()) ? item.getOwnerName() : "");
						inquiryList2.add(item);
					}
					i = true;
					y = true;
				}
			}
			resultMap.setData(inquiryList2);
			resultMap.setSuccess(true);
		} catch (Exception e) {
			resultMap.setSuccess(false);
			resultMap.setMessage("近期录入查询失败");
			logger.error("近期录入查询失败",e);
		}
		return resultMap;
	}
	
	/**
	 * 根据车牌号查询车辆信息
	 * @param request
	 * @param response
	 * @param plateNo
	 * @return
	 */
	@RequestMapping(value = "/getResVehicleByModelName.do")
	@ResponseBody
	public ResultMap getResVehicleByModelName(HttpServletRequest request,HttpServletResponse response, String modelName) {
		ResultMap resultMap = new ResultMap();
		try {
			resultMap.setData(resourceVehicleService.getResVehicleByModelName(modelName));
			resultMap.setSuccess(true);
		} catch (Exception e) {
			resultMap.setSuccess(false);
			resultMap.setMessage("查询失败");
			logger.error("查询失败",e);
		}
		return resultMap;
	}
	
	protected boolean canUseVehicleLicenseOcr(){
		try {
			String userId = this.getAuthenticatedUserId();
			AgentServiceControlDTO obj = agentServiceControlDAO.selectByAgentIdAndServiceType(microService.getMicroByUserId(userId).getAgency().getAgent_id(), 1);
			if(null != obj && null != obj.getIsOn() && obj.getIsOn().equals(1)){
				return true;
			}
			else{
				logger.info(userId+"当前代理未开启行驶证识别功能");
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return false;
	}
	
	protected boolean canUseGetAutoInsureDate(){
		try {
			String userId = this.getAuthenticatedUserId();
			AgentServiceControlDTO obj = agentServiceControlDAO.selectByAgentIdAndServiceType(microService.getMicroByUserId(userId).getAgency().getAgent_id(), 4);
			if(null != obj && null != obj.getIsOn() && obj.getIsOn().equals(1)){
				return true;
			}
			else{
				logger.info(userId+"当前代理未开启自动带出保险期限功能");
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return false;
	}
	
	/**
	 * 进入保险要素（车辆信息、险别信息）录入页
	 */
	@RequestMapping(value = "/insuranceType.do")
	public String insuranceType(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, InitialDTO init,
			String useType, VoInquiryCustomerDTO customer,String panduan,String haveOrder) {
		request.setAttribute("panduan", panduan);
		String autoFindDateSign = "0";
		// 平台定义的驾驶证类型
		List<PlatformDTO> driverType = null;
		List<PlatformDTO> drivingRegs = null;
		List<CoverageItemDTO> coverageItems = null;
		InquiryDTO inquiry = new InquiryDTO();
		model.addAttribute("ownerName", init.getOwnerName());
		model.addAttribute("baseURL", currentServerUrl);
		model.addAttribute("canUseVehicleLicenseOcr", canUseVehicleLicenseOcr() == true?"1":"0");
		try {
			String inquiryId = request.getParameter("inquiryId");
			if("1".equals(haveOrder)){
				InquiryDTO inquiryDTO = inquiryService.getAllInto(inquiry.getInquiryId());
				if (null != inquiryDTO && (null == inquiryDTO.getQuotaList() || inquiryDTO.getQuotaList().size() < 1)) {				
					//更新状态
					manualQuotationTaskService.withdrawQuotn(inquiry.getInquiryId(), getAuthenticatedUserId());
				}else{					
					// 更新所有的报价单、订单状态为无效
					quotaService.updateQuotaStatusByInquiryId("0", inquiryId,this.getAuthenticatedUserId());
					orderService.updateOrderStatusByInquiryId("0", inquiryId);
					//更新询价单为暂存
					inquiryService.updateInquiryStatusByInquiryId("1", inquiryId, getAuthenticatedUserId());
				}
			}
			MicroDTO micro = microService.getMicroByUserId(getAuthenticatedUserId());
			model.addAttribute("teamId", micro.getAgt_team_id());
			List<QuotnConfigDTO> quotnConfigList = quotnConfigService.queryByTeamId(micro.getAgt_team_id());
			Set<String> set = new HashSet<String>();
			for (QuotnConfigDTO quotnConfigDTO : quotnConfigList) {
				BaseDTO baseDTO = baseService.queryByCode("使用性质细分", quotnConfigDTO.getServiceType());
				set.add(baseDTO.getParCode());
			}
			List<String> strs = new ArrayList<String>();
			strs.addAll(set);
			model.addAttribute("businessTypeList", strs);
			// 其他页面进入	
			if(StringUtils.isNotBlank(inquiryId)){
				inquiry.setInquiryId(inquiryId);
				String areaCode = null;
				if ("N".equals(useType)) {
					if (null != init) {// 是否从输入车牌页进入:替换相应信息
						areaCode=StringUtils.isNotBlank(init.getCityCode())?init.getCityCode():init.getProvinceCode();
						inquiry.setPlateNo(init.getPlateNo().toUpperCase());
						inquiry.setCityCode(areaCode);
						inquiry.setNewCarSign(String.valueOf(init.getNewCarSign()));
						if(StringUtils.isNotBlank(inquiryId)){
							//更新输入数据
							inquiryService.updateInquiryByInquiryId(getAuthenticatedUserId(), inquiry,null, null);
						}else{
							logger.info("更新输入数据失败，询价单号为空");
						}
					} else {
						InquiryDTO inqDto = inquiryService.getAllInto(inquiryId);
						areaCode = StringUtils.isNotBlank(inqDto.getCityCode()) ? inqDto.getCityCode() : inqDto.getProvinceCode();
					}
				}
				else if ("Z".equals(useType)) {//报价暂存或正常状态
					
				}
				else if ("C".equals(useType)) {//已报价-未支付-未出单 复制询价单
				}
				// 有询价单编码
				inquiry = inquiryService.get(inquiryId, micro.getMicro_id());
				if(CollectionUtils.isEmpty(coverageItems)){
					coverageItems = coverItemService.getCoverageItems(inquiryId, micro.getMicro_id());
				}
//				List<DriverDTO> driversList = driverService.getDrivers(inquiryId, micro.getMicro_id());
//				if (driversList.size() > 0) {
//					model.addAttribute("drivers", driversList);
//				}
				if (null != coverageItems && coverageItems.size() > 0) {
					model.addAttribute("coverageItems",JSONObject.toJSON(coverageItems));
				}
				areaCode = StringUtils.isNotBlank(inquiry.getCityCode()) ? inquiry.getCityCode() : inquiry.getProvinceCode();
				//起保时间是否已经过期
				Date nowDate=DateUtil.dateToDate("yyyy-MM-dd", new Date());
				if(null!=inquiry.getTciStartDate()&&inquiry.getTciStartDate().getTime()<=nowDate.getTime()){
					Calendar reset = Calendar.getInstance();
					reset.add(Calendar.DATE, 1);
					inquiry.setTciStartDate(reset.getTime());
					reset.add(Calendar.YEAR, 1);
					reset.add(Calendar.DATE, -1);
					inquiry.setTciEndDate(reset.getTime());
				}
				if(null!=inquiry.getVciStartDate()&&inquiry.getVciStartDate().getTime()<=nowDate.getTime()){
					Calendar reset = Calendar.getInstance();
					reset.add(Calendar.DATE, 1);
					inquiry.setVciStartDate(reset.getTime());
					reset.add(Calendar.YEAR, 1);
					reset.add(Calendar.DATE, -1);
					inquiry.setVciEndDate(reset.getTime());
				}
				if(1 == init.getNewCarSign()){
					inquiry.setFstRegNo(nowDate);
					model.addAttribute("fstRegNo", 1);
				}
				model.addAttribute("areaDTO", areaService.get(areaCode));
				model.addAttribute("inquiry", inquiry);
				model.addAttribute("sign", 1);
			}else {
					Date nowDate=DateUtil.dateToDate("yyyy-MM-dd", new Date());
					// 生成询价单编码
					String areaCode =null;
					inquiryId = DateUtil.dateToString(DateUtil.YYYYMMDDHHMMSSSSS, new Date())+(int)(Math.random()*1000);
					inquiry.setInquiryId(inquiryId);
					if(null!=init){
						inquiry.setPlateNo(init.getPlateNo().toUpperCase());
						inquiry.setNewCarSign(String.valueOf(init.getNewCarSign()));
						// 获取城市编码
						areaCode = StringUtils.isNotBlank(init.getCityCode()) ? init.getCityCode() : init.getProvinceCode();
						if(StringUtils.isBlank(areaCode)){
							areaCode = teamAreaService.getAreaCodeByUserId(getAuthenticatedUserId());
						}
						inquiry.setCityCode(areaCode);
					}
					// 显示的城市
					model.addAttribute("areaDTO", areaService.get(areaCode));
					// 插入询价单信息
//					try {
//						InquiryDTO orgInquiry = inquiryService.organizeInquiry(getAuthenticatedUserId(), inquiry, customer);
//						inquiryService.insertInquiry(getAuthenticatedUserId(),orgInquiry, null, null);
//						inquiryService.updateInquiryStatusByInquiryId("-1", inquiryId,this.getAuthenticatedUserId());
//					} catch (Exception e) {
//						logger.error("用户:" + getAuthenticatedUserId()+ "插入询价单:" + inquiryId + "失败:" , e);
//					}
					
					//日期状态，0（可以续保且日期为终报日期第二天，时间大于等于当天），1（可以续保，日期为当前日期第二天，小于当前时间且月份和天数不在90天内），
					//2（可以续保，日期为终保日期的第二天，小于等于当天且月份和天数在90天内），3（不能续保，时间大于当前时间90天）
					String vciDateType = "";
					String tciDateType = "";
					//续保用，交强险终保日期
					Date tciInsureEndDate = null;
					boolean ticSin = true;
					//续保用，商业险终保日期
					Date vciInsureEndDate = null;
					boolean vicSin = true;
					//如果不能续保的提示消息
					String msg = "";
					
					//判断有没有输入车牌号，如果输入了，直接带出车辆信息
					if (StringUtils.isNotBlank(init.getPlateNo())&&init.getPlateNo().indexOf("*") < 0 ) {
						ResourceVehicleDTO resourceVehicle = null;
						List<ResourceVehicleCvrgDTO> resourceVehicleCvrg = null;
						
						//modify by zhaoxijun 2016年7月11日09:19:58  去掉本地车牌查询
						if("1".equals(paramConfigService.getValueByKey(IS_SEARCH_RIM_VECLEIL))){//是否启动远程查询
							
							logger.info("使用车牌远程调用，cityCode:" + inquiry.getCityCode() + "; plateNo:" + inquiry.getPlateNo());
							resourceVehicle = resourceVehicleService.getVehicleByRMI(inquiry.getCityCode(), inquiry.getPlateNo(), 18000, this.getAuthenticatedUserId(),init.getOwnerName());//调用远程接口  10s
							logger.info("使用车牌远程调用，cityCode:" + inquiry.getCityCode() + "; plateNo:" + inquiry.getPlateNo()+" 返回的数据："+resourceVehicle);
							
							//modify by zhaoxijun 2016年7月26日13:50:45
							if(null != resourceVehicle){
								resourceVehicleCvrg = resourceVehicle.getCvrgList();
								if(StringUtils.isNotBlank(resourceVehicle.getModelNme())){
									resourceVehicle.setModelNme(resourceVehicle.getModelNme().trim());
								}
							}
						}
						model.addAttribute("xubao","1");
						if (null != resourceVehicle) {
							boolean ownerCount = false;
							if(StringUtils.isNotBlank(init.getOwnerName())){
								if(init.getOwnerName().equals(resourceVehicle.getOwnerNme())){									
									ownerCount = true;
								}else{
									ownerCount = false;
								}
							}else{
								ownerCount = true;
							}
							if(null != resourceVehicleCvrg && resourceVehicleCvrg.size() > 0 && ownerCount){								
								model.addAttribute("resourceVehicleCvrg", JSONObject.toJSON(resourceVehicleCvrg));
								coverageItems = new ArrayList<>();
								for (ResourceVehicleCvrgDTO resourceVehicleCvrgDTO : resourceVehicleCvrg) {
									CoverageItemDTO coverageItemDTO = new CoverageItemDTO();
									coverageItemDTO.setCode(resourceVehicleCvrgDTO.getCvrgId());
									coverageItemDTO.setGlsType(resourceVehicleCvrgDTO.getGlsType());
									coverageItemDTO.setSumLimit(resourceVehicleCvrgDTO.getGvrgAmount());
									coverageItemDTO.setNoDduct(Integer.parseInt(StringUtils.isNotBlank(resourceVehicleCvrgDTO.getExcldDeductible()) ? resourceVehicleCvrgDTO.getExcldDeductible() : "0"));
									coverageItems.add(coverageItemDTO);
								}
								model.addAttribute("coverageItems",JSONObject.toJSON(coverageItems));
							}
							request.getSession().setAttribute("ResourceVehicleDTO", resourceVehicle);
							model.addAttribute("isResourceVehicleDTO", "1");
							model.addAttribute("remark", resourceVehicle.getRemark());
							inquiry.setPlateNo(init.getPlateNo().toUpperCase());
							if(null != resourceVehicle.getEngNo() && resourceVehicle.getEngNo().length() > 3){
								inquiry.setEngNo("***" + resourceVehicle.getEngNo().substring(3));
							}
							else{
								inquiry.setEngNo(resourceVehicle.getEngNo());
							}
							
							if(null != resourceVehicle.getFrmNo() && resourceVehicle.getFrmNo().length() > 10){
								inquiry.setFrmNo(resourceVehicle.getFrmNo().substring(0, 5) + "*****" + resourceVehicle.getFrmNo().substring(10));
							}
							else{
								inquiry.setFrmNo(resourceVehicle.getFrmNo());
							}
							
							if(StringUtils.isNotBlank(resourceVehicle.getFstRegYm())){								
								inquiry.setFstRegNo(DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getFstRegYm()));
							}
							
							inquiry.setVehicleName(resourceVehicle.getModelNme());
							if(StringUtils.isNotBlank(resourceVehicle.getOwnerNme()) && resourceVehicle.getOwnerNme().length() > 1 && resourceVehicle.getOwnerNme().length() <= 3 && ownerCount){
								inquiry.setOwnerName(resourceVehicle.getOwnerNme().substring(0, 1) + "**");								
							}
							else{
								inquiry.setOwnerName(null);
							}
							
							if(StringUtils.isNotBlank(resourceVehicle.getCertfCde()) && (resourceVehicle.getCertfCde().length() == 15 || resourceVehicle.getCertfCde().length() == 18) && !resourceVehicle.getCertfCde().contains(",") && !resourceVehicle.getCertfCde().contains("，") && ownerCount){
								inquiry.setCertfCde(resourceVehicle.getCertfCde().substring(0, 6) + "*****" + resourceVehicle.getCertfCde().substring(11));
								inquiry.setOwnerCertNo(inquiry.getCertfCde());
							}
							else{
								inquiry.setCertfCde(null);
								inquiry.setOwnerCertNo(null);
							}
							
							if(StringUtils.isNotBlank(resourceVehicle.getTciInsureEnd()) && ownerCount){//交强险终保日期不为空
								logger.info("上年交强险投保止期："+resourceVehicle.getTciInsureEnd());
								if(resourceVehicle.getTciInsureEnd().indexOf("00:00:00") > 0){
									Date dateYear = DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getTciInsureEnd());
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(dateYear);
									calendar.add(Calendar.DATE, -1);
									resourceVehicle.setTciInsureEnd(DateUtil.dateToString("yyyy-MM-dd", calendar.getTime())+" 23:59:59");
								}
								ticSin = true;
								tciDateType = compareMonthAndDay(DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getTciInsureEnd()));
								if("0".equals(tciDateType)){
									String str = (resourceVehicle.getTciInsureEnd()).substring(4, 10);
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(new Date());
									str = calendar.getWeekYear()+str;
									tciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd", str);
								}else if("1".equals(tciDateType)){
									//保险起期为当前时间的第二天
								}else if("2".equals(tciDateType)){//当前年，保险总期的第二天
									tciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getTciInsureEnd());									
								}else if("4".equals(tciDateType)){
//									String str = (resourceVehicle.getTciInsureEnd()).substring(4, 10);
//									Calendar calendar = Calendar.getInstance();
//									calendar.setTime(new Date());
//									calendar.add(Calendar.YEAR, 1);
//									str = calendar.getWeekYear()+str;
//									tciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd", str);
								}else{
									ticSin = false;
									msg = "交强险提前投保超过三个月，不能投保";
								}
								model.addAttribute("ticSin", ticSin);
							}
							if(StringUtils.isNotBlank(resourceVehicle.getVciInsureEnd()) && ownerCount){//商业险终保日期不为空
								logger.info("上年商业险投保止期："+resourceVehicle.getVciInsureEnd());
								if(resourceVehicle.getVciInsureEnd().indexOf("00:00:00") > 0){
									Date dateYear = DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getVciInsureEnd());
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(dateYear);
									calendar.add(Calendar.DATE, -1);
									resourceVehicle.setVciInsureEnd(DateUtil.dateToString("yyyy-MM-dd", calendar.getTime())+" 23:59:59");
								}
								vicSin = true;
								vciDateType = compareMonthAndDay(DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getVciInsureEnd()));
								if("0".equals(vciDateType)){	
									String str = (resourceVehicle.getVciInsureEnd()).substring(4, 10);
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(new Date());
									str = calendar.getWeekYear()+str;
									vciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd",str);	
								}else if("1".equals(vciDateType)){
									//保险起期为当前时间的第二天
								}else if("2".equals(vciDateType)){//当前年，保险总期的第二天
									vciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getVciInsureEnd());		
								}else if("4".equals(vciDateType)){
//									String str = (resourceVehicle.getVciInsureEnd()).substring(4, 10);
//									Calendar calendar = Calendar.getInstance();
//									calendar.setTime(new Date());
//									calendar.add(Calendar.YEAR, 1);
//									str = calendar.getWeekYear()+str;
//									vciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd", str);
								}else{
									vicSin = false;
									if(StringUtils.isNotBlank(msg)){
										msg += "；";
									}
									msg += "商业险提前投保超过三个月，不能投保";
								}
								model.addAttribute("vicSin", vicSin);
							}
						}
					}
					//如果是续保，且不能续保
					if(StringUtils.isNotBlank(msg)){
						model.addAttribute("msg", msg);
					}
					
					//设置保险期限
					Calendar cld = Calendar.getInstance();
					if(null != tciInsureEndDate || null != vciInsureEndDate){//续保
						//交强续保
						if(null != tciInsureEndDate){
							cld.setTime(tciInsureEndDate);
							cld.add(Calendar.DATE, 1);
							inquiry.setTciStartDate(cld.getTime());
							cld.add(Calendar.YEAR, 1);
							cld.add(Calendar.DATE, -1);
							inquiry.setTciEndDate(cld.getTime());
						}else{
							cld.setTime(new Date());
							cld.add(Calendar.DATE, 1);
							inquiry.setTciStartDate(cld.getTime());
							cld.add(Calendar.YEAR, 1);
							cld.add(Calendar.DATE, -1);
							inquiry.setTciEndDate(cld.getTime());
						}
						//商业续保
						if(null != vciInsureEndDate){
							cld.setTime(vciInsureEndDate);
							cld.add(Calendar.DATE, 1);
							inquiry.setVciStartDate(cld.getTime());
							cld.add(Calendar.YEAR, 1);
							cld.add(Calendar.DATE, -1);
							inquiry.setVciEndDate(cld.getTime());
						}else{
							cld.setTime(new Date());
							cld.add(Calendar.DATE, 1);
							inquiry.setVciStartDate(cld.getTime());
							cld.add(Calendar.YEAR, 1);
							cld.add(Calendar.DATE, -1);
							inquiry.setVciEndDate(cld.getTime());
						}
					}else{
						cld.add(Calendar.DATE, 1);
						inquiry.setVciStartDate(cld.getTime());
						inquiry.setTciStartDate(cld.getTime());
						cld.add(Calendar.YEAR, 1);
						cld.add(Calendar.DATE, -1);
						inquiry.setVciEndDate(cld.getTime());
						inquiry.setTciEndDate(cld.getTime());		
						
						//显示自动获取保险期限
						//前提:私家车(为空默认私家车)&非新车(不带*号)&交强商业期限都为空
						if(ticSin && vicSin){
							if((StringUtils.isBlank(inquiry.getUsageCode()) || "A1".equals(inquiry.getUsageCode())) && !inquiry.getPlateNo().contains("*")){
								autoFindDateSign = "1";
							}
						}
					}
					
					if("1".equals(inquiry.getNewCarSign()) && StringUtils.isBlank(inquiry.getFstRegNoStr())){
						inquiry.setFstRegNo(nowDate);
						model.addAttribute("fstRegNo", 1);
					}
					model.addAttribute("inquiry", inquiry);
			}
			
			try {
				driverType = platformService.getCodeClass(BaseFinal.DRIVERTYPE);
			} catch (BusinessServiceException e) {
				logger.error("用户:" + getAuthenticatedUserId() + "获取汽车类型失败:" , e);
				driverType = new ArrayList<PlatformDTO>();
			}
			try {
				drivingRegs = platformService
						.getCodeClass(BaseFinal.DRIVING_AREA);
			} catch (BusinessServiceException e) {
				logger.error("用户:" + getAuthenticatedUserId() + "获取行驶区域失败:" , e);
				drivingRegs = new ArrayList<PlatformDTO>();
			}
			BaseDTO base = baseService.queryByCode("使用性质细分", inquiry.getUsageCode());
			if(null != base){				
				model.addAttribute("usageCodeParCode", base.getParCode());
			}
		} catch (Exception e) {
			logger.error("用户:" + getAuthenticatedUserId() + "进入险种页面失败:"
							+ e.getMessage(), e);
		}
		
		//自动获取投保期限,按钮显示开关
		if("1".equals(autoFindDateSign) && !canUseGetAutoInsureDate()){
			autoFindDateSign = "0";
		}
		//canUseGetAutoInsureDate
		model.addAttribute("drivingRegs", drivingRegs);
		model.addAttribute("dirverType", driverType);
		model.addAttribute("autoFindDateSign", autoFindDateSign);
		return "flows.insurance";
	}
	
	/**
	 * 自动获取车辆信息,及保险期限
	 * autoFindInsureDate
	 * @param plateNo
	 * @param frmNO
	 * @param enginNo
	 * @param ownerName
	 * @param certNo
	 * @return
	 */
	@RequestMapping("/autoFindInsureDate.do")
	@ResponseBody
	public HashMap<String, Object> autoFindInsureDate(String plateNo, String frmNO, String enginNo, String ownerName, String certNo){
		logger.info("autoFindInsureDate,plateNo:"+plateNo+",frmNO:"+frmNO+",enginNo:"+enginNo+",ownerName:"+ownerName+",certNo:"+certNo);
		HashMap<String, Object> resultMap = new HashMap<>();
		if(StringUtils.isBlank(plateNo) || plateNo.contains("*")){
			resultMap.put("returnCode", "4001");
			resultMap.put("returnMessage", "车牌号必传且已上牌");
			return resultMap;
		}
		frmNO = frmNO!=null&!frmNO.contains("*")?frmNO.toUpperCase().trim():"";
		enginNo = enginNo!=null&!enginNo.contains("*")?enginNo.toUpperCase().trim():"";
		certNo = certNo!=null&!certNo.contains("*")?certNo.toUpperCase().trim():"";
		ownerName = ownerName!=null&!ownerName.contains("*")?ownerName.trim():"";
		try {
			ResourceVehicleDTO resourceVehicle = resourceVehicleService.getVehicleByRMI(plateNo, frmNO, enginNo, ownerName, certNo, this.getAuthenticatedUserId());
			resultMap.put("resourceVehicle", resourceVehicle);
			if(null != resourceVehicle){
				Date tciInsureEndDate = null;
				Date vciInsureEndDate = null;
				if(StringUtils.isNotBlank(resourceVehicle.getTciInsureEnd())){//交强险终保日期不为空
					logger.info("上年交强险投保止期："+resourceVehicle.getTciInsureEnd());
					if(resourceVehicle.getTciInsureEnd().indexOf("00:00:00") > 0){
						Date dateYear = DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getTciInsureEnd());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(dateYear);
						calendar.add(Calendar.DATE, -1);
						resourceVehicle.setTciInsureEnd(DateUtil.dateToString("yyyy-MM-dd", calendar.getTime())+" 23:59:59");
					}
					String tciDateType = compareMonthAndDay(DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getTciInsureEnd()));
					if("0".equals(tciDateType)){
						String str = (resourceVehicle.getTciInsureEnd()).substring(4, 10);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(new Date());
						str = calendar.getWeekYear()+str;
						tciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd", str);
					}else if("1".equals(tciDateType)){
						//保险起期为当前时间的第二天
					}else if("2".equals(tciDateType)){//当前年，保险总期的第二天
						tciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getTciInsureEnd());									
					}else if("4".equals(tciDateType)){
						
					}else{
						logger.info("交强险提前投保超过三个月，不能投保");
						resultMap.put("returnCode", "0");
						resultMap.put("returnMessage", "交强险提前投保超过三个月，不能投保");
						return resultMap;
					}
				}
				if(StringUtils.isNotBlank(resourceVehicle.getVciInsureEnd())){//商业险终保日期不为空
					logger.info("上年商业险投保止期："+resourceVehicle.getVciInsureEnd());
					if(resourceVehicle.getVciInsureEnd().indexOf("00:00:00") > 0){
						Date dateYear = DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getVciInsureEnd());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(dateYear);
						calendar.add(Calendar.DATE, -1);
						resourceVehicle.setVciInsureEnd(DateUtil.dateToString("yyyy-MM-dd", calendar.getTime())+" 23:59:59");
					}
					String vciDateType = compareMonthAndDay(DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getVciInsureEnd()));
					if("0".equals(vciDateType)){	
						String str = (resourceVehicle.getVciInsureEnd()).substring(4, 10);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(new Date());
						str = calendar.getWeekYear()+str;
						vciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd",str);	
					}else if("1".equals(vciDateType)){
						//保险起期为当前时间的第二天
					}else if("2".equals(vciDateType)){//当前年，保险总期的第二天
						vciInsureEndDate = DateUtil.stringToDate("yyyy-MM-dd", resourceVehicle.getVciInsureEnd());		
					}else if("4".equals(vciDateType)){
						
					}else{
						logger.info("商业险提前投保超过三个月，不能投保");
						resultMap.put("returnCode", "0");
						resultMap.put("returnMessage", "商业险提前投保超过三个月，不能投保");
						return resultMap;
					}
				}
				
				//设置保险期限
				Calendar cld = Calendar.getInstance();
				if(null != tciInsureEndDate || null != vciInsureEndDate){//续保
					//交强续保
					if(null != tciInsureEndDate){
						cld.setTime(tciInsureEndDate);
						cld.add(Calendar.DATE, 1);
						resultMap.put("tciStartDate", DateUtil.dateToString(DateUtil.YYYY_MM_DD, cld.getTime()));
						cld.add(Calendar.YEAR, 1);
						cld.add(Calendar.DATE, -1);
						resultMap.put("tciEndDate", DateUtil.dateToString(DateUtil.YYYY_MM_DD, cld.getTime()));
					}else{
						cld.setTime(new Date());
						cld.add(Calendar.DATE, 1);
						resultMap.put("tciStartDate", DateUtil.dateToString(DateUtil.YYYY_MM_DD, cld.getTime()));
						cld.add(Calendar.YEAR, 1);
						cld.add(Calendar.DATE, -1);
						resultMap.put("tciEndDate", DateUtil.dateToString(DateUtil.YYYY_MM_DD, cld.getTime()));
					}
					
					//商业续保
					if(null != vciInsureEndDate){
						cld.setTime(vciInsureEndDate);
						cld.add(Calendar.DATE, 1);
						resultMap.put("vciStartDate", DateUtil.dateToString(DateUtil.YYYY_MM_DD, cld.getTime()));
						cld.add(Calendar.YEAR, 1);
						cld.add(Calendar.DATE, -1);
						resultMap.put("vciEndDate", DateUtil.dateToString(DateUtil.YYYY_MM_DD, cld.getTime()));
					}else{
						cld.setTime(new Date());
						cld.add(Calendar.DATE, 1);
						resultMap.put("vciStartDate", DateUtil.dateToString(DateUtil.YYYY_MM_DD, cld.getTime()));
						cld.add(Calendar.YEAR, 1);
						cld.add(Calendar.DATE, -1);
						resultMap.put("vciEndDate", DateUtil.dateToString(DateUtil.YYYY_MM_DD, cld.getTime()));
					}
					
					resultMap.put("returnCode", "0");
					resultMap.put("returnMessage", "系统已自动调整保险期限");
					return resultMap;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("returnCode", "5001");
			resultMap.put("returnMessage", "系统繁忙");
			return resultMap;
		}
		resultMap.put("returnCode", "5002");
		resultMap.put("returnMessage", "未自动获取到保险期限");
		return resultMap;
	}
	
	/**
	 * 续保时间判断
	 * @param date
	 * @return 日期状态，0（可以续保且日期为终报日期第二天，时间大于等于当天），1（可以续保，日期为当前日期第二天，小于当前时间且月份和天数不在90天内），
	 * 2（可以续保，日期为终保日期的第二天，小于等于当天且月份和天数在90天内），3（不能续保，时间大于当前时间90天），
	 */
	public static String compareMonthAndDay(Date date){//传过来的日期为止期，比起期晚一天，所以时间比较要少一天
		String i = "";
		Calendar cld1 = Calendar.getInstance();
		cld1.setTime(new Date());
		cld1.add(Calendar.DATE, 89);
		if(date.after(cld1.getTime())){//时间大于当前时间90天，不能续保
			i = "3";
		}else{
			Calendar cld2 = Calendar.getInstance();
			cld2.setTime(new Date());
			if(date.before(cld2.getTime())){//时间小于当前时间
				Calendar cld3 = Calendar.getInstance();
				cld3.setTime(date);
				//当前时间是这一年的多少天
				int dayOfYear1 = cld2.get(Calendar.DAY_OF_YEAR); 
				cld2.add(Calendar.DATE, 89);
				int dayOfYear3 = cld2.get(Calendar.DAY_OF_YEAR);
				//传入的时间是哪一年的多少天
				int dayOfYear2 = cld3.get(Calendar.DAY_OF_YEAR);
				if(dayOfYear2 >= dayOfYear1 && dayOfYear2 <= (dayOfYear1+89)){//可以续保且日期为终报日期第二天，时间大于等于当天
					i = "0";
				}else if(dayOfYear2 < dayOfYear1 && dayOfYear2 <= dayOfYear3){
					i = "4";
				}else{
					i = "1";
				}
				
			}else{//时间大于等于当前时间，日期为终保日期的第二天
				i = "2";
			}
		}
		return i;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/comPrice.do")
	public ModelAndView calculatePrice(HttpServletRequest request,
			HttpServletResponse response, Model model, String items,
			InquiryDTO inquiry, String drivers, String isResourceVehicleDTO, VoInquiryCustomerDTO customer,String panduan) {
		logger.info("OrderController comPrice.do --> 险别:" + items + ",驾驶员：" + drivers + ",询价信息:" + inquiry);
		if(inquiry != null && inquiry.getOwnerCertNo() != null){
			inquiry.setOwnerCertNo(inquiry.getOwnerCertNo().toUpperCase());
		}
		//是否车型库查询
		if("1".equals(isResourceVehicleDTO)){
			ResourceVehicleDTO resourceVehicle = (ResourceVehicleDTO) request.getSession().getAttribute("ResourceVehicleDTO");
			if(null != resourceVehicle){
				if(null != resourceVehicle.getEngNo() && resourceVehicle.getEngNo().length() > 3){
					inquiry.setEngNo(resourceVehicle.getEngNo());
				}
				
				if(null != resourceVehicle.getFrmNo() && resourceVehicle.getFrmNo().length() > 10){
					inquiry.setFrmNo(resourceVehicle.getFrmNo());
				}
				
				if(StringUtils.isNotBlank(resourceVehicle.getOwnerNme()) && resourceVehicle.getOwnerNme().length() > 1 && resourceVehicle.getOwnerNme().length() <= 3){
					inquiry.setOwnerName(resourceVehicle.getOwnerNme());
				}
				
				if(StringUtils.isNotBlank(resourceVehicle.getCertfCde()) && (resourceVehicle.getCertfCde().length() == 15 || resourceVehicle.getCertfCde().length() == 18) && !resourceVehicle.getCertfCde().contains(",") && !resourceVehicle.getCertfCde().contains("，")){
					inquiry.setOwnerCertNo(resourceVehicle.getCertfCde());
					inquiry.setCertfCde(resourceVehicle.getCertfCde());
				}
			}
		}
		
		// 获取合作的保险公司
		try {
			List<DriverDTO> driversList = null;
			if (StringUtils.isNotBlank(drivers)) {
				driversList = JSONObject.parseArray(drivers, DriverDTO.class);
			}
			List<CoverageItemDTO> coverageItemsJsp = null;
			if (StringUtils.isNotBlank(items)) {
				coverageItemsJsp = JSONObject.parseArray(items, CoverageItemDTO.class);
			}
			String userId = this.getAuthenticatedUserId();
			// 将初始信息转换为询价单信息
			InquiryDTO orgInquiry = inquiryService.organizeInquiry(userId, inquiry, customer);
			// 更新询价信息到数据库
			orgInquiry.setState("0");
			if(null != orgInquiry && StringUtils.isNotBlank(orgInquiry.getInquiryId())){				
				inquiryService.updateInquiryByInquiryId(userId, orgInquiry, coverageItemsJsp, driversList);
			}else{
				logger.info("更新询价信息失败，询价单号为空");
			}
		} catch (Exception e) {
			logger.error("用户:" + getAuthenticatedUserId() + "输入信息完成,进去报价页面失败:", e);
		}
		return new ModelAndView("redirect:/toquota.do?inquiryId=" + inquiry.getInquiryId() + "&panduan="+panduan);
	}
	
	
	/**
	 * 报价暂存
	 */
	@RequestMapping(value = "/informationTempSave.do")
	@ResponseBody
	public AjaxResult informationTempSave(HttpServletRequest request,
			HttpServletResponse response, Model model, String isResourceVehicleDTO,
			InquiryDTO inquiry, VoInquiryCustomerDTO customer) {
		logger.info("OrderController informationTempSave.do" + inquiry.getInquiryId());
		try {
			InquiryDTO inquiryDTO = inquiryService.getAllInto(inquiry.getInquiryId());
			if (null != inquiryDTO && (null == inquiryDTO.getQuotaList() || inquiryDTO.getQuotaList().size() < 1)) {				
				//更新状态
				manualQuotationTaskService.withdrawQuotn(inquiry.getInquiryId(), getAuthenticatedUserId());
			}
			if(null == inquiryDTO){//如果不为空就添加询价单			
				InquiryDTO orgInquiry = inquiryService.organizeInquiry(getAuthenticatedUserId(), inquiry, customer);
				inquiryService.insertInquiry(getAuthenticatedUserId(),orgInquiry, null, null);
				inquiryService.updateInquiryStatusByInquiryId("-1", inquiry.getInquiryId(),this.getAuthenticatedUserId());
			}
		} catch (Exception e) {
			logger.error("用户:" + getAuthenticatedUserId()+ "插入询价单:" + inquiry.getInquiryId() + "失败:" , e);
			return new AjaxResult(false,"保存询价单信息失败");
		}
		
		if(inquiry != null && inquiry.getOwnerCertNo() != null){
			inquiry.setOwnerCertNo(inquiry.getOwnerCertNo().toUpperCase());
		}
		//是否车型库查询
		if("1".equals(isResourceVehicleDTO)){
			ResourceVehicleDTO resourceVehicle = (ResourceVehicleDTO) request.getSession().getAttribute("ResourceVehicleDTO");
			if(null != resourceVehicle){
				if(null != resourceVehicle.getEngNo() && resourceVehicle.getEngNo().length() > 3){
					inquiry.setEngNo(resourceVehicle.getEngNo());
				}
				
				if(null != resourceVehicle.getFrmNo() && resourceVehicle.getFrmNo().length() > 10){
					inquiry.setFrmNo(resourceVehicle.getFrmNo());
				}
				
				if(StringUtils.isNotBlank(resourceVehicle.getOwnerNme()) && resourceVehicle.getOwnerNme().length() > 1 && resourceVehicle.getOwnerNme().length() <= 3){
					inquiry.setOwnerName(resourceVehicle.getOwnerNme());
				}
				
				if(StringUtils.isNotBlank(resourceVehicle.getCertfCde()) && (resourceVehicle.getCertfCde().length() == 15 || resourceVehicle.getCertfCde().length() == 18) && !resourceVehicle.getCertfCde().contains(",") && !resourceVehicle.getCertfCde().contains("，")){
					inquiry.setOwnerCertNo(resourceVehicle.getCertfCde());
					inquiry.setCertfCde(resourceVehicle.getCertfCde());
				}
			}
		}
				
				
		//TODO
		// 获取合作的保险公司
		try {
			String items=request.getParameter("items");
			String drivers=request.getParameter("drivers");
			// 基础数据
			String userId = this.getAuthenticatedUserId();
			// 询价单信息
			List<DriverDTO> driversList = new ArrayList<DriverDTO>();
			if (StringUtils.isNotBlank(drivers)) {
				driversList = JSONObject.parseArray(drivers, DriverDTO.class);
			}
			List<CoverageItemDTO> coverageItemsJsp = new ArrayList<CoverageItemDTO>();
			if (StringUtils.isNotBlank(items)) {
				coverageItemsJsp = JSONObject.parseArray(items, CoverageItemDTO.class);
			}
			// 将初始信息转换为询价单信息
			InquiryDTO orgInquiry = inquiryService.organizeInquiry(userId, inquiry, customer);
			// 判断询价单是否已经存在
			if (null != inquiry.getInquiryId()
					&& !"".equals(inquiry.getInquiryId())) {
				// 更新询价单主表
				inquiryService.updateInquiryByInquiryId(
						getAuthenticatedUserId(), inquiry, coverageItemsJsp,
						driversList);
				inquiryService.updateInquiryStatusByInquiryId("1",
						inquiry.getInquiryId(),this.getAuthenticatedUserId());

				// 更新所有的报价单、订单状态为无效
				quotaService.updateQuotaStatusByInquiryId("0", inquiry.getInquiryId(),this.getAuthenticatedUserId());
				orderService.updateOrderStatusByInquiryId("0", inquiry.getInquiryId());
				//更新询价单为暂存
				inquiryService.updateInquiryStatusByInquiryId("1", inquiry.getInquiryId(), getAuthenticatedUserId());
				
			} else {
				String inquiryId = DateUtil.dateToString("yyyyMMddHHmmssSSS",
						new Date());
				inquiry.setInquiryId(inquiryId);
				inquiry.setState("1");
				// 添加询价信息到数据库
				inquiryService.insertInquiry(getAuthenticatedUserId(), inquiry,
						coverageItemsJsp, driversList);
			}
		} catch (Exception e) {
			
			logger.error("用户:" + getAuthenticatedUserId()
					+ "输入信息完成,报价暂存数据现在失败:" , e);
		}
		return new AjaxResult(true, "报价暂存成功", "");
	}
	
	/**
	 * 查询当前用户在该保险公司的商业险费率
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/getFeeVCIRatioByInsId.do")
	@ResponseBody
	public AjaxResult getFeeVCIRatioByInsId(HttpServletRequest request,HttpServletResponse response, Model model, String insId, String quotaId) {
		//如果需要查询顶级的保险公司CODE写在return之前改变insId
		logger.info("quotaId="+quotaId);
		AjaxResult result = new AjaxResult();
		result.setSucc(true);
		QuotaDTO quotaDTO = null;
		try {
			quotaDTO = quotaService.getByQuotaId(quotaId);
			if(null != quotaDTO){
				String tciInsureStartDate = null;
				String vciInsureStartDate = null;
				InquiryDTO inquiryDTO = inquiryService.get(quotaDTO.getInquiry().getInquiryId(), null);
				if(null != inquiryDTO){
					if("1".equals(inquiryDTO.getTciSign())){
						tciInsureStartDate = inquiryDTO.getTciStartDateStr();
					}
					if("1".equals(inquiryDTO.getVciSign())){
						vciInsureStartDate = inquiryDTO.getVciStartDateStr();
					}
				}
				result.setData(getFeeRatioByInsId(insId, quotaDTO.getTCIPremTax(), quotaDTO.getVCIPremTax(), tciInsureStartDate, vciInsureStartDate));
			}
		} catch (BusinessServiceException e) {
			logger.error("getByQuotaId failed", e);
			result.setSucc(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 通过用户ID获取用户所属团队的地址
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/selectMicroTeamAddressByUserId.do")
	@ResponseBody
	public AjaxResult selectMicroTeamAddressByUserId(HttpServletRequest request,HttpServletResponse response, Model model, String insId) {
		
		AjaxResult result = new AjaxResult();
		try {
			result.setData(microDAO.selectMicroTeamAddressByUserId(this.getAuthenticatedUserId()));
			result.setSucc(true);
		} catch (Exception e) {
			result.setSucc(false);
			result.setMsg("通过用户ID获取用户所属团队的地址失败: "+e.getMessage());
			logger.info("通过用户ID获取用户所属团队的地址失败", e);
		}
		return result;
	}
	
	

	@RequestMapping(value = "/toquota.do")
	public String calculatePrice(HttpServletRequest request,
			HttpServletResponse response, Model model,String panduan) {
		logger.info("进入报价页面判断："+panduan);
		List<InsuranceDTO> insurances = new ArrayList<InsuranceDTO>();
		String insurancesJson = "";
		InquiryDTO inquiry = null;
		String inquiryId = request.getParameter("inquiryId");
		try {
			//判断该询价单下的订单情况
			if(StringUtils.isNotBlank(inquiryId)){
				//先判断是否存在5或6状态的订单
				OrderDTO orderDTO = orderService.getOrderIdByInquiryIdAndStatus(inquiryId);
				if(null != orderDTO && ("5".equals(orderDTO.getQueryStage()) || "6".equals(orderDTO.getQueryStage()))){
					orderService.updateOrderStatusByInquiryId("1", inquiryId);
					quotaService.updateQuotaStatusByInquiryId("1", inquiryId, this.getAuthenticatedUserId());
					if("5".equals(orderDTO.getQueryStage())){
						inquiryService.updateInquiryStatusByInquiryId("2", inquiryId, this.getAuthenticatedUserId());
					}else{
						inquiryService.updateInquiryStatusByInquiryId("3", inquiryId, this.getAuthenticatedUserId());
					}
					
				}//存在5或6状态的订单
				if(null != orderDTO){
					return "redirect:/detail.do?quotaId="+orderDTO.getQuota().getQuotaId()+"&orderId="+orderDTO.getOrderId()+"&insId="+orderDTO.getInsurance().getInsId();
				}
				InquiryDTO inquiryDTO = inquiryService.getAllInto(inquiryId);
				if(inquiryDTO != null && inquiryDTO.getOrderList() != null && inquiryDTO.getOrderList().size() > 0){
					request.setAttribute("haveOrder", "1");
				}
				if(!"1".equals(panduan)){					
					//询价单存在订单
					if(inquiryDTO != null && inquiryDTO.getOrderList() != null && inquiryDTO.getOrderList().size() > 0){
						//判断投保单是否过期
						boolean i = true;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String ticStartDateStr = inquiryDTO.getTciStartDateStr();
						String vicStartDateStr = inquiryDTO.getVciStartDateStr();
						//当前时间加1天
						Calendar cld = Calendar.getInstance();
						cld.setTime(DateUtil.dateToDate("yyyy-MM-dd", new Date()));
						cld.add(Calendar.DATE, 1);
						//交强
						if(StringUtils.isNotEmpty(ticStartDateStr) && "1".equals(inquiryDTO.getTciSign())){
							Calendar cld1 = Calendar.getInstance();
							cld1.setTime(sdf.parse(ticStartDateStr));
							if(cld1.before(cld)){//保险起期小于当前时间+1天	，已过期		
								i = false;
							}
						}
						//商业
						if(StringUtils.isNotEmpty(vicStartDateStr) && "1".equals(inquiryDTO.getVciSign())){
							Calendar cld1 = Calendar.getInstance();
							cld1.setTime(sdf.parse(vicStartDateStr));
							if(cld1.before(cld)){//保险起期小于当前时间+1天，已过期
								i = false;
							}
						}
						//没过期
						if(i){
							for(OrderDTO ord : inquiryDTO.getOrderList()){
								if("4".equals(ord.getQueryStage()) || "14".equals(ord.getQueryStage())){
									return opetateVote(model, request,response,inquiryId);
								}
							}
//						return "opetateVote.do?inquiryId="+inquiryId;
						}else{
							return "redirect:/insuranceType.do?inquiryId="+inquiryId+"&useType=1";
						}
					}				
				}
			}
			model.addAttribute("inquiryId", inquiryId);
			MicroDTO microDTO = microService
					.getMicroByUserId(getAuthenticatedUserId());
			inquiry = inquiryService.get(inquiryId, microDTO.getMicro_id());
			// 是否调用接口
			String useType = request.getParameter("useType");
			model.addAttribute("useType", useType);
//			//获取人工报价保险公司和网点信息
//			List<com.zxcl.webapp.dto.AgentServiceSiteDTO> agentServiceSiteDTOlist = new ArrayList<com.zxcl.webapp.dto.AgentServiceSiteDTO>();
//			agentServiceSiteDTOlist = mAgentServiceSiteService.findSiteList(microDTO.getMicro_id());
//			model.addAttribute("agentServiceSiteDTOlist", agentServiceSiteDTOlist);
//			List<TempPrivilegeDTO> privilege = tempPrivilegeService.getPrivilegeByMicroIdService(microDTO.getMicro_id());
//			if(privilege.size()>0){
//				model.addAttribute("onOff", privilege.get(0).getOnOff());
//			}else{
//				model.addAttribute("onOff", "0");
//			}
			
			//判断是否存在有效的报价单
			InquiryDTO inqu = inquiryService.getAllInto(inquiryId);
			if (null != inqu && (null == inqu.getQuotaList() || inqu.getQuotaList().size() < 1)) {
				logger.info("该询价单下无有效报价单，询价单号："+inquiryId);
				if (null != inquiry) {
					//更新状态
					manualQuotationTaskService.withdrawQuotn(inquiryId, getAuthenticatedUserId());
//					// 更新所有的报价单、订单状态为无效
//					quotaService.updateQuotaStatusByInquiryId("0", inquiryId,this.getAuthenticatedUserId());
//					orderService.updateOrderStatusByInquiryId("0", inquiryId);
//					//更新询价单为暂存
//					inquiryService.updateInquiryStatusByInquiryId("1", inquiryId, getAuthenticatedUserId());
					
					
//					// 设置可以报价的保险公司
//					//获取小薇可以报价的公司
//					List<InsuranceDTO> insByMicro = agencyService
//							.getInsByMicroId(microService.getMicroByUserId(
//									getAuthenticatedUserId()).getMicro_id());
//					
//					for (InsuranceDTO ins : insByMicro) {
//						InsuranceDTO insurance = insuranceService.get(ins.getInsId());
//						if(null == insurance || "0".equals(insurance.getStatus())){
//							continue;//保险公司停止服务
//						}
//						insurance.setQuotnType("A");
//						insurances.add(insurance);
//						
//					}
//					
//					// 传入保险公司：显示
//					// 传入保险公司JSON：报价
//					insurancesJson =JSONObject.toJSONString(insurances);
//					logger.info("小微可以报价的公司(json)："+insurancesJson);

//					if("A1".equals(inqu.getUsageCode())){//自动报价
//						String [] insIds = quotnConfigService.queryInsIdByTeamIdAndQuotnType(microDTO.getAgt_team_id(), "A");
//						for (String insId : insIds) {
//							InsuranceDTO insurance = insuranceService.get(insId);
//							if(null == insurance || "0".equals(insurance.getStatus())){
//								continue;//保险公司停止服务
//							}
//							insurance.setQuotnType("A");
//							insurances.add(insurance);
//						}
//					}else{//人工报价
//						String [] insIds = quotnConfigService.queryInsIdByTeamIdAndQuotnType(microDTO.getAgt_team_id(), "M");
//						for (String insId : insIds) {
//							InsuranceDTO insurance = insuranceService.get(insId);
//							if(null == insurance || "0".equals(insurance.getStatus())){
//								continue;//保险公司停止服务
//							}
//							insurance.setQuotnType("M");
//							insurances.add(insurance);
//						}
//					}
					List<TeamParamMappingDTO> teamParamMappingList = teamParamMappingService.getTeamParamMappingInfo(this.getAuthenticatedUserId(), inquiry.getUsageCode());
					for (TeamParamMappingDTO teamParamMappingDTO : teamParamMappingList) {
						InsuranceDTO insurance = insuranceService.get(teamParamMappingDTO.getInsId());
						if(null == insurance || "0".equals(insurance.getStatus())){
							continue;//保险公司停止服务
						}
						insurance.setQuotnType(teamParamMappingDTO.getQuotnType());
						insurances.add(insurance);
					}
					// 传入保险公司JSON：报价
					insurancesJson =JSONObject.toJSONString(insurances);
					logger.info("小微可以报价的公司(json)："+insurancesJson);
					
				}
				model.addAttribute("insurances", insurances);
				model.addAttribute("insurancesJson", insurancesJson);
				logger.info("用户:" + getAuthenticatedUserId() + "报价的保险公司："+ insurancesJson);
				model.addAttribute("failIns", "0");
				model.addAttribute("message", "");
			} else {
				logger.info("该询价单下存在有效报价单，询价单号："+inquiryId);
//				/***
//				 * tdong投保结果需要显示未报价公司
//				 */
//				List<InsuranceDTO> insByMicro = agencyService
//						.getInsByMicroId(microService.getMicroByUserId(
//								getAuthenticatedUserId()).getMicro_id());
//				logger.info("小微可以报价的公司(list1)："+insByMicro);
//				for (InsuranceDTO ins : insByMicro) {
//					if(null == ins || "0".equals(ins.getStatus())){
//						continue;//保险公司停止服务
//					}
//					InsuranceDTO insuranceDTO = insuranceService.get(ins.getInsId());
//					ins.setInsName(insuranceDTO.getInsPetName());
//					ins.setQuotnType("A");
//					insurances.add(ins);
//				}
				
//				if("A1".equals(inqu.getUsageCode())){//自动报价
//					String [] insIds = quotnConfigService.queryInsIdByTeamIdAndQuotnType(microDTO.getAgt_team_id(), "A");
//					for (String insId : insIds) {
//						InsuranceDTO insurance = insuranceService.get(insId);
//						if(null == insurance || "0".equals(insurance.getStatus())){
//							continue;//保险公司停止服务
//						}
//						insurance.setQuotnType("A");
//						insurances.add(insurance);
//					}
//				}else{//人工报价
//					String [] insIds = quotnConfigService.queryInsIdByTeamIdAndQuotnType(microDTO.getAgt_team_id(), "M");
//					for (String insId : insIds) {
//						InsuranceDTO insurance = insuranceService.get(insId);
//						if(null == insurance || "0".equals(insurance.getStatus())){
//							continue;//保险公司停止服务
//						}
//						insurance.setQuotnType("M");
//						insurances.add(insurance);
//					}
//				}
				
				List<TeamParamMappingDTO> teamParamMappingList = teamParamMappingService.getTeamParamMappingInfo(this.getAuthenticatedUserId(), inquiry.getUsageCode());
				for (TeamParamMappingDTO teamParamMappingDTO : teamParamMappingList) {
					InsuranceDTO insurance = insuranceService.get(teamParamMappingDTO.getInsId());
					if(null == insurance || "0".equals(insurance.getStatus())){
						continue;//保险公司停止服务
					}
					insurance.setQuotnType(teamParamMappingDTO.getQuotnType());
					insurances.add(insurance);
				}
				
//				String quotaId = "";
				List<QuotaDTO> quotasDTO = quotaService.getQuotasByInqueryId(inquiryId);
				if (null != quotasDTO && quotasDTO.size() > 0) {
					String tciInsureStartDate = null;
					String vciInsureStartDate = null;
					InquiryDTO inquiryDTO = inquiryService.get(inquiryId, null);
					if(null != inquiryDTO){
						if("1".equals(inquiryDTO.getTciSign())){
							tciInsureStartDate = inquiryDTO.getTciStartDateStr();
						}
						if("1".equals(inquiryDTO.getVciSign())){
							vciInsureStartDate = inquiryDTO.getVciStartDateStr();
						}
					}
					
					// 页面显示
					List<QuotedDTO> quotedDTOs = new ArrayList<QuotedDTO>();
					InsuranceDTO  ins = null;
					for (QuotaDTO quotaDTO : quotasDTO) {
						ins = insuranceService.get(quotaDTO.getInsurance().getInsId());
						if(null == ins || "0".equals(ins.getStatus())){//保险公司设置为0无效时即使之前报价成功了也不要显示 add by zhaoxijun
							continue;
						}
						QuotedDTO quoted = new QuotedDTO();
						// 处理保险公司显示名称
						quoted.setInquiryId(inquiryId);
						quotaDTO.getInsurance().setInsName(insuranceService.get(quotaDTO.getInsurance().getInsId()).getInsPetName());
						//保险公司类型
						quotaDTO.getInsurance().setInsType(insuranceService.get(quotaDTO.getInsurance().getInsId()).getInsType());
						quoted.setInsurance(quotaDTO.getInsurance());
						quoted.setQuotaId(quotaDTO.getQuotaId());
						quoted.setAmount(quotaDTO.getTotalAmountBig());
						quoted.setQuotaType(quotaDTO.getQuotaType());
						quoted.setOrderStatus(quotaDTO.getOrderStatus());
						quoted.setDiscount(quotaDTO.getDiscount());
						quoted.setRatio(getFeeRatioByInsId(quotaDTO.getInsurance().getInsId(),quotaDTO.getTCIPremTax(), quotaDTO.getVCIPremTax() ,tciInsureStartDate, vciInsureStartDate));//佣金
						quoted.setLastYearClaimNum(quotaDTO.getLastYearClaimNum());
						if("M".equals(quotaDTO.getQuotaType())){//人工报价
							ManualQuotationTaskDTO manualQuotationTaskDTO = manualQuotationTaskService.queryByQuoteId(quotaDTO.getQuotaId());
							quoted.setManualQuotationTaskDTO(manualQuotationTaskDTO);
						}
						
						//**为报价结果提供支付做准备，报价结果支付需要订单id和询价单id
						quoted.setOrderId(quotaDTO.getOrderId());						
						InquiryDTO inquirydtos = new InquiryDTO();
						inquirydtos.setInquiryId(quotaDTO.getInquiry().getInquiryId());
						quoted.setInquiry(inquirydtos);
						quoted.setWarns(JSONObject.parseArray(quotaDTO.getWarns(), String.class));
						if (Double.parseDouble(quoted.getAmount() + "") == 0) {
							quoted.setShowType("0");
						}else if (Double.parseDouble(quoted.getAmount() + "") > 0) {
							quoted.setShowType("1");
						}
						quotedDTOs.add(quoted);
					}
					List<InsuranceDTO> insurancesTwo = new ArrayList<InsuranceDTO>();
					
					//遍历报价后的公司，相同的公司不做重新报价
					for(int i=0;i<insurances.size();i++){
						boolean isLike=false;
						for(int j=0;j<quotedDTOs.size();j++){
							if(insurances.get(i).getInsId().equals(quotedDTOs.get(j).getInsurance().getInsId())){
								isLike=true;
							}	
						}
						if(!isLike){
							insurancesTwo.add(insurances.get(i));
						}
					}	
					//设置未报价的保险公司
					model.addAttribute("insurancesTwo", insurancesTwo);
					logger.info(quotedDTOs);
					model.addAttribute("quoteds", quotedDTOs);
					
				}
				InquiryDTO inquiryDTO = inquiryService.getInsQuotaFailInfo(inquiryId);
				if(null != inquiryDTO){
					model.addAttribute("failIns", StringUtils.isNotBlank(inquiryDTO.getInsQuotaFailCount()) ? inquiryDTO.getInsQuotaFailCount() : "0");
					model.addAttribute("message", inquiryDTO.getInsQuotaFailInfo());
				}else{
					model.addAttribute("failIns", "0");
					model.addAttribute("message", "");
				}
				
				InquiryDTO inquiryDTO1 = inquiryService.selectByInquiryId(inquiryId);
				if(null != inquiryDTO1){
					if(StringUtils.isNotBlank(inquiryDTO1.getReInsureInfo())){
						model.addAttribute("reInsureInfo", inquiryDTO1.getReInsureInfo());
						model.addAttribute("reInsureInfoType", "1");
					}
				}
			}
			
			
			
			List<CoverageItemDTO> coverItems = new ArrayList<CoverageItemDTO>();
				coverItems = coverItemService.getCoverageItems(inquiryId,
						microDTO.getMicro_id());
			
			List<PlatformDTO> platforms = null;
			if(null != coverItems && coverItems.size() > 0){				
				platforms = platformService.getByCode(BaseFinal.INSURANCETYPECODE,coverItems);
				for (CoverageItemDTO coverDetailed : coverItems) {
					for (PlatformDTO plate : platforms) {
						if (coverDetailed.getCode().equals(plate.getCode())) {
							plate.setAmount(coverDetailed.getAmount());
							if(coverDetailed.getSumLimit() != null){							
								BigDecimal sumLimit = coverDetailed.getSumLimit().divide(new BigDecimal("10000")); 
								plate.setSumLimit(sumLimit);
							}
							coverDetailed.setRemark(plate.getRemark());
						}
					}
				}
				boolean isNoDduct = false;
				if(coverItems != null && coverItems.size() > 0){
					for(int i  = 0 ;i<coverItems.size();i++){
						if(coverItems.get(i).getNoDduct() == 1){
							isNoDduct = true;
							break;
						}
					}
				}
				
				model.addAttribute("platforms", platforms);
				model.addAttribute("isNoDduct", isNoDduct);
			}
			
			InquiryDTO inquiryDTO = inquiryService.get(inquiryId, microDTO.getMicro_id());
			model.addAttribute("inquiryDTO", inquiryDTO);
		} catch (Exception e) {
			logger.error("用户:" + getAuthenticatedUserId() + "重定向到报价页面失败:", e);
		}
		return "flows.comPrice";
	}
	

	/**
	 * 报价
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getInsured.do")
	@ResponseBody
	public AjaxResult getInsured(HttpServletRequest request,
			HttpServletResponse response, Model model, String inquiryId,
			String insId) {
		logger.info("用户:" + getAuthenticatedUserId() + "保险公司:" + insId
				+ ",报价--->开始");
		try {
			
			model.addAttribute("inquiryId", inquiryId);
			// 组织数据
			String userId = this.getAuthenticatedUserId();
			InsuranceDTO insurance = insuranceService.get(insId);
			MicroDTO micro = microService.getMicroByUserId(userId);
			//报价之前 add by zhaoxijun
			if(insurance == null || "0".equals(insurance.getStatus())){
				return new AjaxResult(false, "保险公司不存在或停止业务。", new QuotaReturnDTO("error","保险公司不存在或停止业务。",insId));
			}
			if("2".equals(insurance.getStatus())){
				return new AjaxResult(false, "系统维护中，暂停业务。", new QuotaReturnDTO("error","系统维护中，暂停业务。",insId));
			}
//			String mappingStatus = microDAO.getStatusByInsIdAndMicroId(insId, micro.getMicro_id());
			InquiryDTO inquiryDTO = inquiryService.get(inquiryId, null);
			String mappingStatus = microService.getStatusByInsIdAndMicroId(insId, micro.getMicro_id(), userId, inquiryDTO.getUsageCode());
			if("0".equals(mappingStatus)){
				return new AjaxResult(false, "该保险公司暂不提供报价，请咨询你所在团队管理人员", new QuotaReturnDTO("error","该保险公司暂不提供报价，请咨询你所在团队管理人员",insId));
			}
			
			QuotaReturnDTO quotaResp = callAction.quotas(userId, inquiryId,
					insurance.getInsId());
			if (null == quotaResp) {
				logger.warn("用户:" + getAuthenticatedUserId() + "，保险公司:" + insId
						+ "，报价失败.返回的报价对象为"+quotaResp+".");
				return new AjaxResult(false, null, new QuotaReturnDTO("","",insId));
			}else if("PSDERROR".equals(quotaResp.getErrorCode()) || "ALERT".equals(quotaResp.getErrorCode()) || "ERROR".equals(quotaResp.getErrorCode()) 
					|| "error".equals(quotaResp.getErrorCode())){//全换成新版的就不要“error”了
				//记录报价失败错误信息
				try {
					Map<String,String> map = insMsgMatchService.matchMsg(insId, quotaResp.getErrorMessages());
					String msg = "";
					if(null != map){
						msg = map.get("msg");
					}
					inquiryService.insertInsQuotaFailInfo(inquiryId, insId, "1", quotaResp.getErrorMessages(),msg
					,getAuthenticatedUserId(), getAuthenticatedUserId());
				} catch (Exception e2) {
					logger.error("记录报价失败错误信息失败", e2);
				}
				Map<String,String> map = insMsgMatchService.matchMsg(insId, quotaResp.getErrorMessages());
				if(null != map){					
					quotaResp.setManualQuotn(map.get("manual_quotn"));
					quotaResp.setErrorMessages(map.get("msg"));
				}
				return new AjaxResult(false, null, quotaResp);
			}
			
			// 插入查询成功后的数据
			if (StringUtils.isNotBlank(quotaResp.getQuotaId())) {
				QuotaDTO quota = quotaService.organizeQuota(userId, inquiryId,
						quotaResp, micro, insurance);
				Map<String, BigDecimal> amounts = new HashMap<String, BigDecimal>();
				// 订单详情
				QuotaDetailedDTO detailed = null;
				if (null != quotaResp.getCoverageItems()
						&& quotaResp.getCoverageItems().size() > 0) {
					for (CoverageItemDTO cover : quotaResp
							.getCoverageItems()) {
						//保费
						amounts.put(cover.getCode(), cover.getAmount());
					}
					// 订单详情主要是险别信息.
					detailed = detailedService.organizeQuotaDetaileds(userId,
							inquiryId, insurance, quota, quotaResp);
				}
				quota.setDiscount(quotaResp.getDiscount());
				quota.setLastYearClaimNum(quotaResp.getLastYearClaimNum());
				// 插入报价单信息
				inquiryService.updateInquiryStatusByInquiryId("2", inquiryId,this.getAuthenticatedUserId());
				quotaService.insertDetailed(quota, detailed);
				// 处理信息，返回给页面
				// 页面显示对象
				QuotedDTO insured = new QuotedDTO();
				insured.setInquiryId(inquiryId);
				insured.setQuotaId(quotaResp.getQuotaId());
				insurance.setInsName(insurance.getInsPetName());
				insured.setInsurance(insurance);
				insured.setMsg(quotaResp.getReInsureInfo());
				if(StringUtils.isNotBlank(quotaResp.getReInsureInfo())){//更新重复投保信息
					inquiryService.updateReInsureInfo(quotaResp.getReInsureInfo(), inquiryId,this.getAuthenticatedUserId());
				}
				insured.setCarCheckStatus(quotaResp.getCarCheckStatus());
				insured.setLastYearClaimNum(quotaResp.getLastYearClaimNum());
				insured.setDiscount(quotaResp.getDiscount());
				
				insured.setIsOrNoUpdateStartDateSign(quotaResp.getIsOrNoUpdateStartDateSign());
				insured.setTicStartQuotaDate(quotaResp.getTicStartQuotaDate());
				insured.setVicStartQuotaDate(quotaResp.getVicStartQuotaDate());
				// 总价格
				BigDecimal total = new BigDecimal(0);
				if (null != quotaResp.getPremVCITax()) {
					total = total.add(quotaResp.getPremVCITax());
				}
				if (null != quotaResp.getPremTCITax()) {
					total = total.add(quotaResp.getPremTCITax());
				}
				if (null != quotaResp.getVehicleTax()) {
					total = total.add(quotaResp.getVehicleTax());
				}
				/**金额最后保留两位小数点 2015年12月24日14:26:39 赵晋改*/
				DecimalFormat df=new DecimalFormat("0.00");
				String fromatTotal = df.format(total);
				insured.setAmount(BigDecimal.valueOf(Double.parseDouble(fromatTotal)));
				
				insured.setWarns(quotaResp.getWarns());
				
				return new AjaxResult(true, "", insured);
			}
			return new AjaxResult(false, "", new QuotaReturnDTO("","报价失败",insId));
		} catch(QuotaMaxCountException qme){
			logger.info("用户:" + getAuthenticatedUserId() + "调用保险公司:" + insId
					+ "报价接口失败:" , qme);

			return new AjaxResult(false, null, new QuotaReturnDTO("error", qme.getMessage(), insId));
		} catch (Exception e) {
			logger.error("用户:" + getAuthenticatedUserId() + "调用保险公司:" + insId
					+ "报价接口失败:" , e);
			Map<String,String> map = insMsgMatchService.matchMsg(insId, e.getMessage());
			String msg = "";
			if(null != map){
				msg = map.get("msg");
			}
			return new AjaxResult(false, null, new QuotaReturnDTO("error", msg, insId));
		}
	}

	/**
	 * 跳转到确认报价页面,
	 * @return
	 */
	@RequestMapping(value="/confirmQuotaInfo.do")
	public String confirmQuotaInfo(HttpServletRequest request,
			HttpServletResponse response, Model model, String insId,
			String quotaId,String inquiryId){
		InquiryDTO inquiry=new InquiryDTO();
		// 报价单
		QuotaDTO quota = null;
		// 报价明细
		QuotaDetailedDTO detailed = null;
		// 险别
		List<PlatformDTO> platforms = null;
		 
		try {
			InsuranceDTO insurance = insuranceService.get(insId);
			MicroDTO microDTO = microService.getMicroByUserId(getAuthenticatedUserId());
			inquiry = inquiryService.get(inquiryId, microDTO.getMicro_id());
			if(inquiry == null){
				return "redirect:/index.do";
			}
			quota=quotaService.getByQuotaId(quotaId);
			// 显示的险别
			detailed = detailedService.getDetailed(quotaId, insId);
			if (null != detailed) {
				platforms = platformService.getByCode(
						BaseFinal.INSURANCETYPECODE,
						detailed.getCoverageItems());
				for (CoverageItemDTO coverDetailed : detailed
						.getCoverageItems()) {
					for (PlatformDTO plate : platforms) {
						if (coverDetailed.getCode().equals(plate.getCode())) {
							plate.setAmount(coverDetailed.getAmount());
							if(coverDetailed.getSumLimit() != null){							
								BigDecimal sumLimit = coverDetailed.getSumLimit().divide(new BigDecimal("10000")); 
								plate.setSumLimit(sumLimit);
							}
							coverDetailed.setRemark(plate.getRemark());
						}
					}
				}
				model.addAttribute("riskCheck", "1");
			} else {
				model.addAttribute("riskCheck", "2");
				platforms = new ArrayList<PlatformDTO>();
			}
			
			InsuranceDTO insuranceDTO = insuranceService.get(insId);
			
			Integer flowControl = agencyService.getTeamType(microDTO.getMicro_id(), insId,quota.getQuotaType(),inquiry.getUsageCode());
			if("M".equals(quota.getQuotaType())){
				ManualQuotationTaskDTO manualQuotationTaskDTO = manualQuotationTaskService.queryByQuoteId(quotaId);
				for (QuoteTrackDTO quoteTrackDTO : manualQuotationTaskDTO.getQuoteTrackList()) {
					//报价返回的备注
					if("3".equals(quoteTrackDTO.getOperatStatus())){
						model.addAttribute("manualQuotationRemark", quoteTrackDTO.getRemark());
					}
				}
				model.addAttribute("manualQuotationTaskDTO", manualQuotationTaskDTO);
			}
			
			if(StringUtils.isNotBlank(quota.getWarns())){
				List<String> warns = JSONObject.parseArray(quota.getWarns(), String.class);
				model.addAttribute("warns", warns);
			}
			
			model.addAttribute("flowControl", flowControl);
			model.addAttribute("insuranceDTO", insuranceDTO);
			model.addAttribute("inquiry", inquiry);
			model.addAttribute("quota", quota);
			model.addAttribute("insurance", insurance);
			model.addAttribute("platforms", platforms);
		} catch (Exception e) {
			logger.error("跳转到确认报价页面失败", e);
		}
		
		return "flows.confirmquota";
	}
	
	/**
	 * 进入核保页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/premium.do")
	public String insure(HttpServletRequest request,
			HttpServletResponse response, Model model, String insId,
			String quotaId) {
		logger.info("OrderController premium.do --> 保险公司:" + insId + "报价单:"
				+ quotaId);
		List<PlatformDTO> platformDTOList = null;
		// 地区
		List<AreaDTO> provinces = null;
		List<AreaDTO> areaCitys = null;
		AreaDTO areaProvince=null;
		// 报价单
		QuotaDTO quota = null;
		// 报价明细
		QuotaDetailedDTO detailed = null;
		// 险别
		List<PlatformDTO> platforms = null;
		InquiryDTO inquiry=new InquiryDTO();
		String ownerName = "";
		//车主省份代码
		String ownerProvinceCode="";
		//车主城市代码
		String owmerCityCode="";
		//配送开关
		String disPanDuan = "0";
		try {
			String orderId = request.getParameter("orderId");
			String inquiryId = request.getParameter("inquiryId");
			
			//判断该询价单下的订单情况
			if(StringUtils.isNotBlank(inquiryId)){
				//先判断是否存在5或6状态的订单
				OrderDTO orderDTO = orderService.getOrderIdByInquiryIdAndStatus(inquiryId);
				if(null != orderDTO && ("5".equals(orderDTO.getQueryStage()) || "6".equals(orderDTO.getQueryStage()))){
					orderService.updateOrderStatusByInquiryId("1", inquiryId);
					quotaService.updateQuotaStatusByInquiryId("1", inquiryId, this.getAuthenticatedUserId());
					if("5".equals(orderDTO.getQueryStage())){
						inquiryService.updateInquiryStatusByInquiryId("2", inquiryId, this.getAuthenticatedUserId());
					}else{
						inquiryService.updateInquiryStatusByInquiryId("3", inquiryId, this.getAuthenticatedUserId());
					}
					
				}
				//存在5或6状态的订单
				if(null != orderDTO){
					return "redirect:/detail.do?quotaId="+quotaId+"&orderId="+orderDTO.getOrderId()+"&insId="+orderDTO.getInsurance().getInsId();
				}
				InquiryDTO inquiryDTO = inquiryService.getAllInto(inquiryId);
				//询价单存在订单
				if(inquiryDTO != null && inquiryDTO.getOrderList() != null && inquiryDTO.getOrderList().size() > 0){
					//判断投保单是否过期
					boolean i = true;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String ticStartDateStr = inquiryDTO.getTciStartDateStr();
					String vicStartDateStr = inquiryDTO.getVciStartDateStr();
					//当前时间加1天
					Calendar cld = Calendar.getInstance();
					cld.setTime(DateUtil.dateToDate("yyyy-MM-dd", new Date()));
					cld.add(Calendar.DATE, 1);
					//交强
					if(StringUtils.isNotEmpty(ticStartDateStr) && "1".equals(inquiryDTO.getTciSign())){
						Calendar cld1 = Calendar.getInstance();
						cld1.setTime(sdf.parse(ticStartDateStr));
							if(cld1.before(cld)){//保险起期小于当前时间+1天	，已过期		
								i = false;
							}
					}
					//商业
					if(StringUtils.isNotEmpty(vicStartDateStr) && "1".equals(inquiryDTO.getVciSign())){
						Calendar cld1 = Calendar.getInstance();
						cld1.setTime(sdf.parse(vicStartDateStr));
						if(cld1.before(cld)){//保险起期小于当前时间+1天，已过期
							i = false;
						}
					}
					//没过期
					if(i){
						for (OrderDTO ord : inquiryDTO.getOrderList()) {
							if(insId.equals(ord.getInsurance().getInsId())){								
								return "redirect:/detail.do?quotaId="+quotaId+"&orderId="+ord.getOrderId()+"&insId="+insId;
							}
						}
					}else{
						// 更新所有的报价单、订单状态为无效
						quotaService.updateQuotaStatusByInquiryId("0", inquiryId,this.getAuthenticatedUserId());
						orderService.updateOrderStatusByInquiryId("0", inquiryId);
						//更新询价单为暂存
						inquiryService.updateInquiryStatusByInquiryId("1", inquiryId, getAuthenticatedUserId());
						return "redirect:/insuranceType.do?inquiryId="+inquiryId+"&useType=1";
					}
				}				
			}
			
			model.addAttribute("inquiryId", inquiryId);
			model.addAttribute("quotaId", quotaId);
			MicroDTO microDTO = microService.getMicroByUserId(getAuthenticatedUserId());
			AgentServiceControlDTO agentServiceControlDTO = agentServiceControlDAO.selectByAgentIdAndServiceType(microDTO.getAgency().getAgent_id(), 3);
			if(null != agentServiceControlDTO && 1 == agentServiceControlDTO.getIsOn()){
				disPanDuan = "1";
			}
			inquiry = inquiryService.get(inquiryId, microDTO.getMicro_id());
			model.addAttribute("inquiry", inquiry);
			if (StringUtils.isNotBlank(orderId)) {
				OrderDTO order = orderService.getOrderById(microDTO.getMicro_id(), insId, orderId);
				quotaId = order.getQuota().getQuotaId();
				OwnerDTO owner = ownerService.getOwnerByOrderId(orderId);
				
				ownerName = owner.getOwnerName();
				model.addAttribute("owner", owner);
				model.addAttribute("rec",recService.getRecognizeeByOrderId(orderId));
				model.addAttribute("dis",disService.getDistributionByOrderId(orderId));
				model.addAttribute("vote",voteService.getVoteInsuranceByOrderId(orderId));
				model.addAttribute("sign", 1);
			} else if (StringUtils.isNotBlank(quotaId)) {
				OrderDTO order = orderService.getOrderByQuotaId(quotaId,microDTO.getMicro_id(), null);
				if (null != order) {
					orderId = order.getOrderId();
					OwnerDTO owner = ownerService.getOwnerByOrderId(orderId);
					ownerName = owner.getOwnerName();
					model.addAttribute("owner", owner);
					model.addAttribute("rec",recService.getRecognizeeByOrderId(orderId));
					model.addAttribute("dis",
							disService.getDistributionByOrderId(orderId));
					model.addAttribute("vote",
							voteService.getVoteInsuranceByOrderId(orderId));
					model.addAttribute("sign", 1);
				}
			}
			model.addAttribute("orderId", orderId);
			if (StringUtils.isBlank(ownerName)) {
				// 车主姓名
		
				ownerName = inquiry.getOwnerName();
				String ownerCertNo=inquiry.getOwnerCertNo();
				model.addAttribute("ownerName", ownerName);
				model.addAttribute("ownerCertNo", ownerCertNo);
			}
			// 证件类型
			platformDTOList = platformService.getCodeClass(BaseFinal.IDTYPE);
			// 地区
			provinces = areaService.getProvinces();
			//根据询价单获取车主城市信息
			 owmerCityCode=inquiry.getCityCode();
			 //数据库没有省的存储字段，根据城市代码查询出相关的省份信息
			 areaProvince= areaService.getProvinceByCityCode(owmerCityCode);
			 //根据省份获取城市集合
			 areaCitys = areaService.getCitysByProvinceCode(areaProvince.getCode());
			 quota=quotaService.getByQuotaId(quotaId);
			// 显示的险别
			detailed = detailedService.getDetailed(quotaId, insId);
			if (null != detailed) {
				platforms = platformService.getByCode(
						BaseFinal.INSURANCETYPECODE,
						detailed.getCoverageItems());
				for (CoverageItemDTO coverDetailed : detailed
						.getCoverageItems()) {
					for (PlatformDTO plate : platforms) {
						if (coverDetailed.getCode().equals(plate.getCode())) {
							plate.setAmount(coverDetailed.getAmount());
							if(coverDetailed.getSumLimit() != null){							
								BigDecimal sumLimit = coverDetailed.getSumLimit().divide(new BigDecimal("10000")); 
								plate.setSumLimit(sumLimit);
							}
						}
					}
				}
			} else {
				platforms = new ArrayList<PlatformDTO>();
			}
			Integer flowControl = agencyService.getTeamType(microDTO.getMicro_id(), insId,quota.getQuotaType(),inquiry.getUsageCode());
			model.addAttribute("flowControl", flowControl);
			
			//省份控制
			// 获取保险公司配置信息
			String configId = "";
			// 查询保险公司配置信息ID
//			configId = microService.getConfigIdByInsIdAndMicroId(insId, microDTO.getMicro_id());
			configId = teamParamMappingService.getConfigId(insId, inquiry.getUsageCode(), quota.getQuotaType(), this.getAuthenticatedUserId());
			// 查询保险公司配置信息
			Map<String,Object> configMap = insuranceCompanyConfigService.getMapByInsId(configId);
			if(null != configMap){
				model.addAttribute("plate_no_control", Common.valueOf(configMap.get("PLATE_NO_CONTROL")));
			}
			String plate1 = inquiry.getPlateNo().substring(0, 1)+"*";
			String plate2 = inquiry.getPlateNo().substring(0, 2);
			model.addAttribute("plate1", plate1);
			model.addAttribute("plate2", plate2);
		} catch (Exception e) {
			logger.error(
					"用户:" + getAuthenticatedUserId() + "进去核保页面错误:"
							+ e.getMessage(), e);
			platformDTOList = new ArrayList<PlatformDTO>();
			provinces = new ArrayList<AreaDTO>();
			quota = new QuotaDTO();
			detailed = new QuotaDetailedDTO();
			platforms = new ArrayList<PlatformDTO>();
		}
		
		model.addAttribute("disPanDuan", disPanDuan);
		model.addAttribute("quota", quota);
		model.addAttribute("detailed", detailed);
		model.addAttribute("platforms", platforms);
		model.addAttribute("provinces", provinces);
		model.addAttribute("platformDTOList", platformDTOList);
		model.addAttribute("ownerProvinceCode", ownerProvinceCode);
		model.addAttribute("owmerCityCode", owmerCityCode);
		model.addAttribute("areaProvince", areaProvince);
		model.addAttribute("areaCitys", areaCitys);
		
		return "flows.premium";
	}

	/**
	 * 核保
	 */
	@RequestMapping(value = "/accounting.do")
	@ResponseBody
	public AjaxResult accounting(HttpServletRequest request,
			HttpServletResponse response, OwnerDTO ownerJsp,
			DistributionDTO disJsp, VoteInsuranceDTO voteJsp,
			RecognizeeDTO recJsp,String quotnType) {
		logger.info("  ownerJsp:" + ownerJsp + "  disJsp:" + disJsp
				+ "  voteJsp:" + voteJsp + "  recJsp:" + recJsp);
		
		// 投保显示
		VoteReturnDTO voteRtrnWeb = new VoteReturnDTO();
		voteRtrnWeb.setQuotaType("A");
		
		if(ownerJsp != null && ownerJsp.getOwnerCertNo() != null){
			ownerJsp.setOwnerCertNo( ownerJsp.getOwnerCertNo().toUpperCase());
		}
		if(voteJsp != null && voteJsp.getVoteCertNo() != null){
			voteJsp.setVoteCertNo(voteJsp.getVoteCertNo().toUpperCase());
		}
		if(recJsp != null && recJsp.getRecCertNo() != null){
			recJsp.setRecCertNo(recJsp.getRecCertNo().toUpperCase());
		}
		
		String insId = request.getParameter("insId");
		try {
			if(StringUtils.isNotBlank(insId)){
				
				//公司是否可用必须为1
				InsuranceDTO insurance = insuranceService.get(insId);
				//核保之前 add by zhaoxijun
				if(insurance == null || "0".equals(insurance.getStatus())){
					voteRtrnWeb.setReturnMsg("保险公司不存在或停止业务。");
					voteRtrnWeb.setInsId(insId);
					voteRtrnWeb.setReturnCode("3");//3核保异常
					return new AjaxResult(true, "保险公司不存在或停止业务。", voteRtrnWeb);
				}
				if("2".equals(insurance.getStatus())){
					voteRtrnWeb.setReturnMsg("系统维护中，暂停业务。");
					voteRtrnWeb.setInsId(insId);
					voteRtrnWeb.setReturnCode("3");//3核保异常
					return new AjaxResult(true, "系统维护中，暂停业务。", voteRtrnWeb);
				}
			}
			
			//提交核保时，做个判断：认证通过的用户，业务员和车主相同（用身份证号判断），则此单不能提交核保
			MicroApproveDTO ma = identityInfoService.findConfirm(this.getAuthenticatedUserId(), false);
			if(null != ma && "2".equals(ma.getApproveState()+"") && StringUtils.isNotBlank(ma.getMicroCardId()) && null != ownerJsp && StringUtils.isNotBlank(ownerJsp.getOwnerCertNo())){//认证通过,身份证号不为空
				if(ma.getMicroCardId().toUpperCase().equals(ownerJsp.getOwnerCertNo().toUpperCase())){
					voteRtrnWeb.setReturnMsg("认证通过的用户不能投保自己的车");
					voteRtrnWeb.setInsId(insId);
					voteRtrnWeb.setReturnCode("3");//3核保异常
					return new AjaxResult(true, "认证通过的用户不能投保自己的车", voteRtrnWeb);
				}
			}
			// 基础数据
			String quotaId = request.getParameter("quotaId");
			String userId = this.getAuthenticatedUserId();
			QuotaDTO quota = quotaService.getByQuotaId(quotaId);
			// 2015年11月30日15:51:09 直接取巡检单险别数据 赵晋
			// 调用接口
			//是否有订单编码标识
			String orderSign = null;
			String orderId = request.getParameter("orderId");
			if (StringUtils.isBlank(orderId)) {
				orderId = DateUtil.primaryKey();
				orderSign = orderId;
			}

			// 处理车主信息
			OwnerDTO owner = ownerService.organizeOwner(userId, quota,
					ownerJsp);
			// 被保人
			RecognizeeDTO rec = recService.organizeRecognizee(userId,
					quota, recJsp);
			// 配送
			DistributionDTO dis = disService.organizeDistribution(userId,
					quota, disJsp);
			// 投保
			VoteInsuranceDTO vote = voteService.organizeVoteInsurance(
					userId, quota, voteJsp);
			
			if("M".equals(quotnType)){//人工报价
				// 组装order的信息
				OrderDTO order = orderService.organizeOrder(userId, orderId,
						quota, null);
				owner.setOrder(order);
				rec.setOrder(order);
				dis.setOrder(order);
				vote.setOrder(order);
				if (StringUtils.isNotBlank(orderSign)) {
					orderService.insertOrder(order, dis, owner, rec,vote);
				} else {
					orderService.updateOrderAll(userId, insId, order, dis, owner, rec, vote);
				}
				// 处理核保显示信息
				voteRtrnWeb.setReturnMsg("核保中");
				voteRtrnWeb.setReturnCode("2");
				voteRtrnWeb.setInsId(insId);
				voteRtrnWeb.setOrderId(orderId);
				voteRtrnWeb.setQuotaType(quotnType);
				//修改任务状态并添加轨迹信息
				manualQuotationTaskService.underwrite(quota.getQuotaId(), userId);
				return new AjaxResult(true, "", voteRtrnWeb);
			}else{//自动报价
				VoteInsuranceReturnDTO voteResult = callAction.vote(userId, insId, quotaId, orderId, ownerJsp, voteJsp, recJsp, disJsp);
				if (null != voteResult&&voteResult.isInsureSucc()) {
					// 组装order的信息
					OrderDTO order = orderService.organizeOrder(userId, orderId,
							quota, voteResult);
					order.setQueryStage(voteResult.getStatus());
					// 设置投保人，被保人，配送，车主信息
					owner.setOrder(order);
					rec.setOrder(order);
					dis.setOrder(order);
					vote.setOrder(order);
					if (StringUtils.isNotBlank(orderSign)) {
						orderService.insertOrder(order, dis, owner, rec,vote);
					} else {
						orderService.updateOrderAll(userId, insId, order, dis, owner, rec, vote);
					}
					Map<String,String> msgMap = insMsgMatchService.matchMsg(insId, voteResult.getErrorMessage());
					if(null != msgMap){						
						// 处理核保显示信息
						voteRtrnWeb.setReturnMsg(msgMap.get("msg"));
					}
					voteRtrnWeb.setReturnCode(voteResult.getStatus());
					voteRtrnWeb.setInsId(voteResult.getInsId());
					voteRtrnWeb.setOrderId(orderId);
					voteRtrnWeb.setQuotaType(quotnType);
					logger.warn("核保结果:" + voteRtrnWeb.getReturnMsg());
					//记录核保失败错误信息
					try {
						if(!StringUtils.equals("4", voteResult.getStatus())){
							Map<String,String> map = insMsgMatchService.matchMsg(insId, voteRtrnWeb.getReturnMsg());
							String msg = "";
							if(null != map){
								msg = map.get("msg");
							}
							inquiryService.insertInsQuotaFailInfo(order.getInquiry().getInquiryId(), insId, "2", voteRtrnWeb.getReturnMsg(),
									msg,getAuthenticatedUserId(), getAuthenticatedUserId());	
						}
					} catch (Exception e2) {
						logger.error("记录报价失败错误信息失败", e2);
					}
					return new AjaxResult(true, "", voteRtrnWeb);
				}else{
					Map<String,String> map = insMsgMatchService.matchMsg(insId, voteResult.getErrorMessage());
					if(null != map){						
						voteRtrnWeb.setReturnMsg(map.get("msg"));
					}
					if("err".equals(voteResult.getErrorCode())){//平安flowid过期失效
						voteRtrnWeb.setReturnCode(voteResult.getErrorCode());
					}else{					
						voteRtrnWeb.setReturnCode(voteResult.getStatus());
					}
					voteRtrnWeb.setQuotaType(quotnType);
					return new AjaxResult(true, "", voteRtrnWeb);
				}
			}
		} catch (Exception e) {
			logger.error("用户:" + getAuthenticatedUserId() + ",核保错误:" + e.getMessage(), e);
			return new AjaxResult(false, "", voteRtrnWeb);
		}
	}

	/**
	 * 详情
	 */
	@RequestMapping(value = "/detail.do")
	public String detail(HttpServletRequest request,
			HttpServletResponse response, Model model, String insId,
			String orderId,String quotaId,String type) {
		logger.info("OrderController insure.do");
		logger.info("insId:" + insId + "  orderId:" + orderId);
		OrderDTO order = null;
		InsuranceDTO insurance = null;
		VoteInsuranceDTO vote = null;
		RecognizeeDTO rec = null;
		DistributionDTO dis = null;
		InquiryDTO inquiryVehicle = null;
		List<DriverDTO> drivers = null;
		OwnerDTO owner = null;
		// 报价单
		QuotaDTO quota = null;
		// 报价明细
	    QuotaDetailedDTO detailed = null;
	    // 险别
	    List<PlatformDTO> platforms = null;
	    
		try {
			String userId = this.getAuthenticatedUserId();
			String microId = microService.getMicroByUserId(userId).getMicro_id();
			// 订单
			order = orderService.getOrderById(microId, insId,orderId);
			// 保险公司
			insurance = insuranceService.get(order.getInsurance().getInsId());
			// // 车主信息
			owner = ownerService.getByOrder(order);
			owner.setOwnerCertTypeName(platformService.getPlatByCode(
					BaseFinal.IDTYPE, certTypeChange(owner.getOwnerCertType())).getName());
			// 投保人信息
			vote = voteService.getByOrder(order);
			vote.setVoteCertTypeName(platformService.getPlatByCode(
					BaseFinal.IDTYPE, certTypeChange(vote.getVoteCertType())).getName());
			// 被保人信息
			rec = recService.getByOrder(order);
			rec.setRecCertTypeName(platformService.getPlatByCode(
					BaseFinal.IDTYPE, certTypeChange(rec.getRecCertType())).getName());
			// // 配送信息
			dis = disService.getByOrder(order);
			// 车辆信息
			inquiryVehicle = inquiryService.get(order
					.getInquiry().getInquiryId(),microId);
			// 驾驶员信息
			drivers = driverService.getDrivers(order.getInquiry()
					.getInquiryId(),
					microService.getMicroByUserId(getAuthenticatedUserId())
							.getMicro_id());
			for (DriverDTO driver : drivers) {
				driver.setDriverTypeName(platformService.getPlatByCode(
						BaseFinal.DRIVERTYPE, driver.getDriverType()).getName());
			}

		     quota=quotaService.getByQuotaId(quotaId);
		     if("M".equals(quota.getQuotaType())){
					ManualQuotationTaskDTO manualQuotationTaskDTO = manualQuotationTaskService.queryByQuoteId(quotaId);
					for (QuoteTrackDTO quoteTrackDTO : manualQuotationTaskDTO.getQuoteTrackList()) {
						//报价返回的备注
						if("3".equals(quoteTrackDTO.getOperatStatus())){
							model.addAttribute("manualQuotationRemark", quoteTrackDTO.getRemark());
						}
					}
					model.addAttribute("manualQuotationTaskDTO", manualQuotationTaskDTO);
				}
		    // 显示的险别
		      detailed = detailedService.getDetailed(quotaId, insId);
		      if (null != detailed) {
		        platforms = platformService.getByCode(
		            BaseFinal.INSURANCETYPECODE,
		            detailed.getCoverageItems());
		        for (CoverageItemDTO coverDetailed : detailed
		            .getCoverageItems()) {
		          for (PlatformDTO plate : platforms) {
		            if (coverDetailed.getCode().equals(plate.getCode())) {
		              plate.setAmount(coverDetailed.getAmount());
		              if(coverDetailed.getSumLimit() != null){              
		                BigDecimal sumLimit = coverDetailed.getSumLimit().divide(new BigDecimal("10000")); 
		                plate.setSumLimit(sumLimit);
		              }
		            }
		          }
		        }
		      } else {
		        platforms = new ArrayList<PlatformDTO>();
		      }
		      
		      if(null != dis){
		    	  String address = "";
		    	  if(StringUtils.isNotBlank(dis.getDisCity())){
		    		  AreaDTO areaDTO = areaService.get(dis.getDisCity());
		    		  address = areaDTO.getName() + dis.getDisAddress();
		    	  }else{
		    		  AreaDTO areaDTO = areaService.get(dis.getDisProvince());
		    		  address = areaDTO.getName() + dis.getDisAddress();
		    	  }
		    	  dis.setDisAddress(address);
		      }
		} catch (Exception e) {
			
			logger.error("查询保险公司:" + insId + "订单:" + orderId + "详细信息失败:" , e);
			rec = new RecognizeeDTO();
			vote = new VoteInsuranceDTO();
			dis = new DistributionDTO();
			owner = new OwnerDTO();
			insurance = new InsuranceDTO();
			drivers = new ArrayList<DriverDTO>();
			inquiryVehicle = new InquiryDTO();
			order = new OrderDTO();
		}
		model.addAttribute("quota", quota);
		model.addAttribute("detailed", detailed);
		model.addAttribute("platforms", platforms);
		model.addAttribute("rec", rec);
		model.addAttribute("vote", vote);
		model.addAttribute("dis", dis);
		model.addAttribute("owner", owner);
		model.addAttribute("ins", insurance);
		model.addAttribute("drivers", drivers);
		model.addAttribute("vehicle", inquiryVehicle);
		model.addAttribute("order", order);
		model.addAttribute("type",StringUtils.isNotBlank(type) ? type : "0");
		return "flows.details";
	}
	
	private static String certTypeChange(String certType){
		if("1".equals(certType)){
			return "1";
		}else if("4".equals(certType)){
			return "10";
		}else if("5".equals(certType)){
			return "11";
		}else{
			return "1";
		}
	}
	
	/**
	 * 核保页面退回
	 */
	@RequestMapping("/orderBaseInfoBack.do")
	public String addOrderBaseInfo(HttpServletRequest request,
			HttpServletResponse response, OwnerDTO ownerJsp,
			DistributionDTO disJsp, VoteInsuranceDTO voteJsp,
			RecognizeeDTO recJsp, String quotaId) {
		String inqiryId = null;
		try {
			QuotaDTO quota = quotaService.getByQuotaId(quotaId);
			inqiryId = quota.getInquiry().getInquiryId();
			String orderId = request.getParameter("orderId");
			if (StringUtils.isNotBlank(orderId)) {
				String status = orderService.getOrderStatusByOrderId(orderId);
				if(StringUtils.isNotBlank(status)&&!"1".equals(status)){
					return "redirect:toquota.do?inquiryId=" + inqiryId + "&useType=Q";
				}
			}
			if(StringUtils.isBlank(orderId)) {
				orderId = DateUtil.dateToString(DateUtil.YYYYMMDDHHMMSSSSS,new Date());
			}
		} catch (Exception e) {
			logger.error("用户 :" + getAuthenticatedUserId() + "插入报价单:" + quotaId+ "的订单暂存单失败:" , e);
		}
		return "redirect:toquota.do?inquiryId=" + inqiryId + "&panduan=1";
	}

	@RequestMapping(value = "/selectsByInquiryId.do")
	@ResponseBody
	public AjaxResult selectsByInquiryId(String inquiryId) {
		AjaxResult r = new AjaxResult();
		try {
			r.setSucc(true);
			r.setMsg("查询成功");
			r.setData(custDAO.selectsByInquiryId(inquiryId));
		} catch (Exception e) {
			r.setSucc(true);
			r.setMsg(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return r;
	}
	
	@ResponseBody
	@RequestMapping(value =  "/vhlQuery.do")
	public AjaxResult vhlQuery(HttpServletRequest req,String carName,String vehicleFrameNo){
		logger.info("入参  carName："+carName+"  vehicleFrameNo："+vehicleFrameNo);
		try {
			ResourceVehicleDTO resourceVehicleDTO = req.getSession().getAttribute("ResourceVehicleDTO") == null ? null : (ResourceVehicleDTO) req.getSession().getAttribute("ResourceVehicleDTO");
			if(resourceVehicleDTO != null && StringUtils.isNotBlank(vehicleFrameNo) && vehicleFrameNo.indexOf("*") > -1){
				logger.info("检测到根据车架号查询有*号，从session中获取车架号. vehicleFrameNo : " + vehicleFrameNo + ",vin:" + resourceVehicleDTO.getFrmNo());
				vehicleFrameNo = resourceVehicleDTO.getFrmNo();
			}
			
			List<VehicleReturnDTO> vehiclesShow = new ArrayList<VehicleReturnDTO>();
			ParamConfigDTO isOrNoPingan = paramConfigService.getParamConfigByKey("IS_OR_NO_PINGAN");
			if(null != isOrNoPingan && "1".equals(isOrNoPingan.getValue()) && StringUtils.isBlank(carName)){//平安车型查询
				vehiclesShow = callAction.vehicleQuery(vehicleFrameNo,this.getAuthenticatedUserId());
			}else{//手动搜索查询
				ParamConfigDTO vehicleQueryInsId = paramConfigService.getParamConfigByKey("VEHICLE_QUERY_INSID");
				if(null != vehicleQueryInsId && StringUtils.isNotBlank(carName)){
					InsuranceDTO insuranceDTO = new InsuranceDTO();
					insuranceDTO.setInsId(vehicleQueryInsId.getValue());
					vehiclesShow = callAction.vehicleQuery(this.getAuthenticatedUserId(),null,carName, insuranceDTO);
				}else{
					return new AjaxResult(false);
				}
			}
			
			//车型查询排序
			Collections.sort(vehiclesShow, new Comparator<VehicleReturnDTO>() {
				@Override
				public int compare(VehicleReturnDTO o1, VehicleReturnDTO o2) {
					BigDecimal o1Price = new BigDecimal(o1.getVehiclePrice()).setScale(2, RoundingMode.HALF_DOWN);
					BigDecimal o2Price = new BigDecimal(o2.getVehiclePrice()).setScale(2, RoundingMode.HALF_DOWN);
					return o1Price.compareTo(o2Price);
				}
			});
			
			return new AjaxResult(true, "", vehiclesShow);
			
		} catch (Exception e) {
			logger.error("车型查询失败，车型名称："+carName+"  车架号："+vehicleFrameNo,e);
			return new AjaxResult(false);
		}
		
	}
	

	/**
	 * 根据传入的省编码直接查询到城市信息
	 * 
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getCityByShe.do")
	@ResponseBody
	public AjaxResult getCity(HttpServletRequest request,
			HttpServletResponse response, String code) {
		logger.info("code:" + code);
		List<AreaDTO> citysByProvince = null;
		try {
			citysByProvince = areaService.getCitysByProvinceCode(code);
		} catch (Exception e) {
			logger.error("用户:" + getAuthenticatedUserId() + "从地区中市级信息失败:" , e);
			citysByProvince = new ArrayList<AreaDTO>();
		}
		return new AjaxResult(citysByProvince);
	}
	/**
	 * 查询保险公司状态
	 * @param request
	 * @param response
	 * @param insId
	 * @return
	 */
	@RequestMapping(value = "/query_ins_status.do")
	@ResponseBody
	public AjaxResult queryInsStatus(HttpServletRequest request, HttpServletResponse response, String insId ) {
		if(null == insId || insId.isEmpty()){
			return new AjaxResult(true, "保险公司为空");
		}
		InsuranceDTO insurance;
		try {
			insurance = insuranceService.get(insId);
		} catch (Exception e) {
			logger.error("获取保险公司异常，insId="+insId, e);
			return new AjaxResult(false, "查询保险公司异常.");
		}
		if(insurance == null){
			return new AjaxResult(false, "保险公司不存在。");
		}
		if("0".equals(insurance.getStatus())){
			return new AjaxResult(false, "保险公司停止业务。",insurance.getStatus());
		}
		if("2".equals(insurance.getStatus())){
			return new AjaxResult(false, "保险公司系统维护中，暂停业务。",insurance.getStatus());
		}
		return new AjaxResult(true, "服务中", insurance.getStatus());
	}
//	/**
//	 * 确认支付
//	 * 
//	 * @param code
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/manuConfirmPay.do")
//	public ModelAndView manuConfirmPay(HttpServletRequest request,
//			HttpServletResponse response, String code,String orderNo,String insID,String quotaId,String useType ) {
//		logger.info("IndexController manuConfirmPay.do");
//		logger.info("code:" + code);
//		String Url="";
//		try {
//			BaseParam baseParam=new BaseParam();
//			baseParam.setOperateUser(getAuthenticatedUserId());
//			baseParam.setOpetateDate(new Date());
//			baseParam.setRequest(request);
//			mquotaTaskService.hasPaid(baseParam, quotaId);
//			// 改成支付确认中
//			orderService.updateOrderStatusByOrderId(ConstantsUtill.ORDERSTATUS_PAYFAILING_MANU, orderNo);
//			//1-综合页面确认支付 2-支付页面确认支付
//			if("1".equals(useType)){
//				Url="redirect:/infoQuery.do";
//			}else if("2".equals(useType)){
//				Url="redirect:/list2.do";
//			}
//		} catch (Exception e) {
//			
//			logger.error("用户:" + getAuthenticatedUserId() + "人工报价确认支付失败:" , e);
//		}
//		return new ModelAndView(Url);
//	}
	
	/**
	 * 通过保险公司CODE获取当前用户商业费率
	 * @param insId
	 * @return
	 */
	private static final DecimalFormat decimalFormat = new DecimalFormat("0");
	@ResponseBody
	@RequestMapping(value="/getFeeList.do")
	public AjaxResult getFeeList(String type,String inquiryId){
		AjaxResult result = new AjaxResult();
		try {
			String teamId = null;
			String agentId = null;
			String userId = this.getAuthenticatedUserId();
			MicroDTO micro = microDAO.getMicroByUserId(userId);
			if(null == micro || null == micro.getAgency() || StringUtils.isBlank(micro.getAgency().getAgent_id())){
				logger.error("用户【"+this.getAuthenticatedUserId()+"】小微信息不存在，或未关联中介。");
				return null;
			}
			teamId = micro.getAgt_team_id();
			agentId = micro.getAgency().getAgent_id();
			logger.info("teamId=" + teamId+",agentId="+agentId);
			
			
			List<InsuranceDTO> insurances = new ArrayList<InsuranceDTO>();
			
//			//可以报价的公司
//			List<InsuranceDTO> insByMicro = agencyService
//					.getInsByMicroId(microService.getMicroByUserId(
//							getAuthenticatedUserId()).getMicro_id());
//			if(CollectionUtils.isNotEmpty(insByMicro)){
//				for (InsuranceDTO ins : insByMicro) {
//					InsuranceDTO insurance = insuranceService.get(ins.getInsId());
//					if(null == insurance){
//						continue;//保险公司停止服务
//					}
//					insurances.add(insurance);
//				}
//			}
			InquiryDTO inquiry = inquiryService.get(inquiryId, null);
			List<TeamParamMappingDTO> teamParamMappingList = teamParamMappingService.getTeamParamMappingInfo(this.getAuthenticatedUserId(), inquiry.getUsageCode());
			if(CollectionUtils.isNotEmpty(teamParamMappingList)){				
				for (TeamParamMappingDTO teamParamMappingDTO : teamParamMappingList) {
					InsuranceDTO insurance = insuranceService.get(teamParamMappingDTO.getInsId());
					if(null == insurance){
						continue;//保险公司停止服务
					}
					insurances.add(insurance);
				}
			}
			//获取费率
			if(CollectionUtils.isNotEmpty(insurances)){
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> map = null;
				for (InsuranceDTO ins : insurances) {
					FeeDTO tcifee = getFee(ins.getInsId(), "TCI", micro.getAgt_team_id(), userId, null);
					FeeDTO vcifee = getFee(ins.getInsId(), "VCI", micro.getAgt_team_id(), userId, null);
					if(null == vcifee && null == tcifee){
						logger.info(ins.getInsId()+"未查询出任何费率");
					}
					else {
						String insName = ins.getInsPetName();//保险公司简称
						String tciRatio = "";//交强佣金费率
						String vciRatio = "";//商业佣金费率
						if(null != tcifee && null != tcifee.getRatio()){
							if("0".equals(tcifee.getIsShow())){
								tciRatio = "";//隐藏
							}
							else{
								tciRatio = tcifee.getRatio()+"％";
							}
						}
						if(null != vcifee && null != vcifee.getRatio()){
							if("0".equals(vcifee.getIsShow())){
								vciRatio = "";//隐藏
							}
							else{
								vciRatio = vcifee.getRatio()+"％";
							}
						}
						
						if(StringUtils.isNotBlank(tciRatio) || StringUtils.isNotBlank(vciRatio)){
							map = new HashMap<String, String>(3);
							map.put("insName", insName);
							map.put("tciRatio", StringUtils.isNotBlank(tciRatio)?tciRatio:"-");
							map.put("vciRatio", StringUtils.isNotBlank(vciRatio)?vciRatio:"-");
							list.add(map);
						}
					}
				}
				if(CollectionUtils.isNotEmpty(list)){
					result.setData(list);
				}
			}
			result.setSucc(true);
			result.setMsg("查询成功");
		} catch (Exception e) {
			logger.error("费率列表查询失败", e);
			result.setSucc(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	public final String getFeeRatioByInsId(String insId, BigDecimal tciAmount, BigDecimal vciAmount, String tciInsureStartDate, String vciInsureStartDate){
		if(StringUtils.isBlank(insId)){
			return "";
		}
		insId = insId.toUpperCase();
		try {
			MicroDTO micro = microDAO.getMicroByUserId(this.getAuthenticatedUserId());
			if(null == micro || StringUtils.isBlank(micro.getAgt_team_id())){
				logger.error("用户【"+this.getAuthenticatedUserId()+"】小微信息不存在，或团队号不存在。");
				return null;
			}
			logger.info("Agt_team_id=" + micro.getAgt_team_id());
			//查询交强险费率
			logger.info("计算交强险佣金");
			FeeDTO fee = null;
			BigDecimal totalAmount = new BigDecimal("0.00");
			if(null != tciAmount && tciAmount.compareTo(BigDecimal.ZERO) > 0){
				fee = getFee(insId, "TCI", micro.getAgt_team_id(), this.getAuthenticatedUserId(), tciInsureStartDate);
				if(null != fee && null != fee.getRatio() && !"0".equals(fee.getIsShow())){
					BigDecimal tciDecimalRatio = tciAmount.divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
					tciDecimalRatio = tciDecimalRatio.multiply(fee.getRatio());
					logger.info("tciDecimalRatio="+tciDecimalRatio);
					totalAmount = totalAmount.add(tciDecimalRatio);
				}
			}
			
			//查询商业险费率
			logger.info("计算商业险佣金");
			if(null != vciAmount && vciAmount.compareTo(BigDecimal.ZERO) > 0){
				fee = getFee(insId, "VCI", micro.getAgt_team_id(), this.getAuthenticatedUserId(), vciInsureStartDate);
				if(null != fee && null != fee.getRatio() && !"0".equals(fee.getIsShow())){
					BigDecimal vciDecimalRatio = vciAmount.divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
					vciDecimalRatio = vciDecimalRatio.multiply(fee.getRatio());
					logger.info("vciDecimalRatio="+vciDecimalRatio);
					totalAmount = totalAmount.add(vciDecimalRatio);
				}
			}
			
			if(totalAmount.compareTo(BigDecimal.ZERO) > 0){
				return decimalFormat.format(totalAmount);
			}
		} catch (Exception e) {
			logger.error("计算获取费率佣金失败", e);
		}
		return "";
	}
	
	private FeeDTO getFee(String insId, String productCode, String teamId, String userId, String insureStartDate){
		FeeDTO fee = null;
		String baseStr = "[保险公司:"+insId+",产品代码:"+productCode+",团队:"+teamId+"]";
		if("TCI".equals(productCode)){
			logger.debug(baseStr+"查询当前团队当前代理交强险佣金费率");
			fee = feeRulesDAO.selectRatioNowWith2(insId, this.getAuthenticatedUserId(), "TCI", teamId);
			if(null != fee){
				logger.info(baseStr+"fee="+fee);
				if("0".equals(fee.getIsShow())){
					logger.debug("当前团队当前代理交强险佣金不显示");
				}
			}
		}
		else if("VCI".equals(productCode)){
			logger.debug("查询当前团队当前代理商业险佣金费率");
			fee = feeRulesDAO.selectRatioNowWith2(insId, this.getAuthenticatedUserId(), "VCI", teamId);
			if(null != fee){
				logger.info(baseStr+"fee="+fee);
				if("0".equals(fee.getIsShow())){
					logger.debug("当前团队当前代理商业险佣金不显示");
				}
			}
		}
		
		if(null != fee && StringUtils.isNotBlank(insureStartDate)){
			if(null == fee.getRatio() || fee.getRatio().compareTo(new BigDecimal("0.00")) == 0){
				//这个时候不算增量佣金
			}
			else{
				logger.info("userId="+userId+",insureStartDate="+insureStartDate);
				List<FeeRulesInsDateDTO> feeRulesInsDateList = feeRulesInsDateDAO.findFeeByParam(userId, insId, productCode, insureStartDate);
				if(!CollectionUtils.isEmpty(feeRulesInsDateList)){
					for(FeeRulesInsDateDTO item : feeRulesInsDateList){
						if(null == item.getRatio()){
							continue;
						}
						//累加
						fee.setRatio(fee.getRatio().add(item.getRatio()));
					}
				}
			}
		}
		return fee;
	}

	
	/**
	 * 综合查询点击投保验证
	 * @param inquiryId 询价单号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/goQuotaVerify.do")
	public AjaxResult goQuotaVerify(String inquiryId,String quoteId){
		try {
			//判断状态
			String [] queryStages = orderService.getQueryStageByInquiryId(inquiryId);
			for (String queryStage : queryStages) {
				if("5".equals(queryStage) || "6".equals(queryStage)){//已支付过就不能修改信息
					return new AjaxResult(false,"1");
				}
			}
			//判断报价单报价时间是否超过5天
			Date date = quotaService.getCreateTimeByQuoteId(quoteId);
			logger.info("报价单时间："+date);
			Calendar cld = Calendar.getInstance();
			cld.setTime(date);
			cld.add(Calendar.DATE, 5);
			Date date2 = new Date();
			if(date2.getTime() > cld.getTime().getTime()){
				return new AjaxResult(true,"1","报价时间超过5天，请重新报价");
			}
			
			//判断时间是否过期
			MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
			InquiryDTO inquiryDTO = null;
			if(null != microDTO){				
				inquiryDTO = inquiryService.get(inquiryId, microDTO.getMicro_id());
			}
			if(null != inquiryDTO){
				//起保时间是否已经过期
				Date nowDate=DateUtil.dateToDate("yyyy-MM-dd", new Date());
				if("1".equals(inquiryDTO.getTciSign()) && null != inquiryDTO.getTciStartDate() && inquiryDTO.getTciStartDate().getTime() <= nowDate.getTime()){//交强
					return new AjaxResult(true,"1","起保日期已过期，请重新报价");
				}
				if("1".equals(inquiryDTO.getVciSign()) && null != inquiryDTO.getVciStartDate() && inquiryDTO.getVciStartDate().getTime() <= nowDate.getTime()){//商业
					return new AjaxResult(true,"1","起保日期已过期，请重新报价");
				}
			}
		} catch (Exception e) {
			logger.error("综合查询点击投保验证失败",e);
			return new AjaxResult(false);
		}
		
		return new AjaxResult();
	}
	
	@ResponseBody
	@RequestMapping(value="/renewalInsurance.do")
	public AjaxResult renewalInsurance(HttpServletRequest request,HttpServletResponse response,String inquiryId){
		InitialDTO init = new InitialDTO();
		try {
			InquiryDTO inquiryDTO = inquiryService.getInquiryVehicleByInquiryId(inquiryId);
			init.setPlateNo(inquiryDTO.getPlateNo());
			//省份code
			String code = areaService.getProvinceCity(inquiryDTO.getCityCode());
			init.setCityCode(inquiryDTO.getCityCode());
			init.setProvinceCode(code);
			return new AjaxResult(true,"",init);
		} catch (Exception e) {
			logger.error("续保跳转失败",e);
			return new AjaxResult(false,"续保失败");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/payDateCheck.do")
	public Integer payDateCheck(String inquiryId,String insId,String quoteId){
		//支付时间校验，保险起期-当前时间<30分钟时，为fales
		Integer i = 0;
		try {
			MicroDTO microDTO = microService.getMicroByUserId(getAuthenticatedUserId());
			InquiryDTO inquiryDTO = inquiryService.get(inquiryId, microDTO.getMicro_id());
			QuotaDTO quotaDTO = quotaService.getByQuotaId(quoteId);
			Integer flowControl = agencyService.getTeamType(microDTO.getMicro_id(), insId,quotaDTO.getQuotaType(),inquiryDTO.getUsageCode());
			if(3 != flowControl){
				return 3;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ticStartDateStr = inquiryDTO.getTciStartDateStr()+" 00:00:00";
			String vicStartDateStr = inquiryDTO.getVciStartDateStr()+" 00:00:00";
			//当前时间加30分钟
			Calendar cld = Calendar.getInstance();
			cld.setTime(new Date());
			cld.add(Calendar.MINUTE, 30);
			Calendar cld2 = Calendar.getInstance();
			cld2.setTime(new Date());
			//交强
			if(StringUtils.isNotEmpty(ticStartDateStr) && "1".equals(inquiryDTO.getTciSign())){
				Calendar cld1 = Calendar.getInstance();
				cld1.setTime(sdf.parse(ticStartDateStr));
					if(cld1.before(cld)){//保险起期小于当前时间+30分钟						
						i = 1;
						if(cld1.before(cld2)){	
							i = 2;
						}
					}
			}
			//商业
			if(StringUtils.isNotEmpty(vicStartDateStr) && "1".equals(inquiryDTO.getVciSign())){
				Calendar cld1 = Calendar.getInstance();
				cld1.setTime(sdf.parse(vicStartDateStr));
				if(cld1.before(cld)){//保险起期小于当前时间+30分钟
					i = 1;
					if(cld1.before(cld2)){	
						i = 2;
					}
				}
			}
			return i;
		} catch (Exception e) {
			logger.error("支付时间校验失败",e);
			return i;
		}
	}
	
	@ResponseBody
	@RequestMapping("policyQuery.do")
	public AjaxResult policyQuery(Model model,HttpServletRequest request,HttpServletResponse response,String inquiryId,String panduan){
		logger.info("点击保费计算判断："+panduan);
		logger.info("OrderController  policyQuery  入参    inquiryId："+inquiryId);
		OrderDTO orderDTO = new OrderDTO();
		try {
			orderDTO = orderService.getOrderIdByInquiryIdAndStatus(inquiryId);
			if(null != orderDTO && ("5".equals(orderDTO.getQueryStage()) || "6".equals(orderDTO.getQueryStage()))){
				orderService.updateOrderStatusByInquiryId("1", inquiryId);
				quotaService.updateQuotaStatusByInquiryId("1", inquiryId, this.getAuthenticatedUserId());
				if("5".equals(orderDTO.getQueryStage())){
					inquiryService.updateInquiryStatusByInquiryId("2", inquiryId, this.getAuthenticatedUserId());
				}else{
					inquiryService.updateInquiryStatusByInquiryId("3", inquiryId, this.getAuthenticatedUserId());
				}
				
			}
			InquiryDTO inquiryDTO = inquiryService.getAllInto(inquiryId);
			//询价单存在订单
			if(inquiryDTO != null && inquiryDTO.getOrderList() != null && inquiryDTO.getOrderList().size() > 0 && !"1".equals(panduan)){
				//判断投保单是否过期
				boolean i = true;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String ticStartDateStr = inquiryDTO.getTciStartDateStr();
				String vicStartDateStr = inquiryDTO.getVciStartDateStr();
				//当前时间加1天
				Calendar cld = Calendar.getInstance();
				cld.setTime(DateUtil.dateToDate("yyyy-MM-dd", new Date()));
				cld.add(Calendar.DATE, 1);
				//交强
				if(StringUtils.isNotEmpty(ticStartDateStr) && "1".equals(inquiryDTO.getTciSign())){
					Calendar cld1 = Calendar.getInstance();
					cld1.setTime(sdf.parse(ticStartDateStr));
						if(cld1.before(cld)){//保险起期小于当前时间+1天	，已过期		
							i = false;
						}
				}
				//商业
				if(StringUtils.isNotEmpty(vicStartDateStr) && "1".equals(inquiryDTO.getVciSign())){
					Calendar cld1 = Calendar.getInstance();
					cld1.setTime(sdf.parse(vicStartDateStr));
					if(cld1.before(cld)){//保险起期小于当前时间+1天，已过期
						i = false;
					}
				}
				if(!i){//过期了失效
					// 更新所有的报价单、订单状态为无效
					quotaService.updateQuotaStatusByInquiryId("0", inquiryId,this.getAuthenticatedUserId());
					orderService.updateOrderStatusByInquiryId("0", inquiryId);
					return new AjaxResult(true,"",null);
				}else{
					if(null == orderDTO){
						for(OrderDTO ord : inquiryDTO.getOrderList()){
							if("4".equals(ord.getQueryStage()) || "14".equals(ord.getQueryStage())){
								return new AjaxResult(true, "opetateVote.do?inquiryId="+inquiryId, inquiryDTO.getOrderList().get(0));
							}
						}
//						response.sendRedirect(request.getContextPath()+"/opetateQuote.do?inquiryId="+inquiryId);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new AjaxResult(false);
		}
		return new AjaxResult(true,"",orderDTO);
	}
	
	/**
	 * 报价操作
	 * @return
	 */
	@RequestMapping("/opetateQuote.do")
	public String operateQuote(HttpServletRequest request,HttpServletResponse response,String inquiryId){
		try {
			InquiryDTO inquiryDTO = inquiryService.getAllInto(inquiryId);
			request.setAttribute("inquiryDTO", inquiryDTO);
			List<QuotaDTO> QuotaDTOList = null;
	        List<QuotaDTO> newQuotaDTOList = new ArrayList<QuotaDTO>();
	        QuotaDTOList = quotaService.infoViewService(inquiryId);
            for (QuotaDTO dto : QuotaDTOList) {
                InsuranceDTO insurancedto = insuranceService.get(dto.getInsurance().getInsId());
                dto.setInsCompanyName(insurancedto.getInsPetName());
                if ("4".equals(dto.getStatus())) {
                    dto.setStatusName("已核保");
                } else if ("5".equals(dto.getStatus())) {
                    dto.setStatusName("已支付");
                } else if ("2".equals(dto.getStatus())) {
                    dto.setStatusName("人工核保");
                } else if ("3".equals(dto.getStatus())) {
                    dto.setStatusName("核保失败");
                }
                if(StringUtils.isNotBlank(dto.getStatusName())){                	
                	newQuotaDTOList.add(dto);
                }
            }
            request.setAttribute("newQuotaDTOList", newQuotaDTOList);
            
            MicroDTO microDTO = microService
					.getMicroByUserId(getAuthenticatedUserId());
            List<CoverageItemDTO> coverItems = new ArrayList<CoverageItemDTO>();
			coverItems = coverItemService.getCoverageItems(inquiryId,
					microDTO.getMicro_id());
            List<PlatformDTO> platforms = null;
			if(null != coverItems && coverItems.size() > 0){				
				platforms = platformService.getByCode(BaseFinal.INSURANCETYPECODE,coverItems);
				for (CoverageItemDTO coverDetailed : coverItems) {
					for (PlatformDTO plate : platforms) {
						if (coverDetailed.getCode().equals(plate.getCode())) {
							plate.setAmount(coverDetailed.getAmount());
							if(coverDetailed.getSumLimit() != null){							
								BigDecimal sumLimit = coverDetailed.getSumLimit().divide(new BigDecimal("10000")); 
								plate.setSumLimit(sumLimit);
							}
							coverDetailed.setRemark(plate.getRemark());
						}
					}
				}
				boolean isNoDduct = false;
				if(coverItems != null && coverItems.size() > 0){
					for(int i  = 0 ;i<coverItems.size();i++){
						if(coverItems.get(i).getNoDduct() == 1){
							isNoDduct = true;
							break;
						}
					}
				}
				request.setAttribute("platforms", platforms);
				request.setAttribute("isNoDduct", isNoDduct);
			}	
		} catch (BusinessServiceException e) {
			logger.error(e.getMessage(),e);
		}
		return "flows.opetateQuote";
	}
	
	/**
	 * 投保操作
	 * @return
	 */
	@RequestMapping("/opetateVote.do")
	public String opetateVote(Model model, HttpServletRequest request,HttpServletResponse response,String inquiryId){
		logger.info("opetateVote==>inquiryId="+inquiryId);
		if(StringUtils.isBlank(inquiryId)){
			return "redirect:/index.do?nocheck=1";
		}
		InquiryDTO inquiry = null;
		String has2 = "0";//是否有人工核保的订单
		String has4 = "0";//是否有待支付订单
		try {
			inquiry = inquiryService.getAllInto(inquiryId);
			if(null != inquiry && CollectionUtils.isNotEmpty(inquiry.getOrderList())){
				List<OrderDTO> orderList = new ArrayList<OrderDTO>();
				for(OrderDTO item : inquiry.getOrderList()){
					
					if("5".equals(item.getQueryStage()) || "6".equals(item.getQueryStage())){
						logger.info("状态["+item.getQueryStage()+"],订单号["+item.getOrderId()+"]");
						return "redirect:/detail.do?quotaId="+item.getQuota().getQuotaId()+"&orderId="+item.getOrderId()+"&insId="+item.getInsurance().getInsId();
					}
					
					if("2".equals(item.getQueryStage())){
						has2 = "1";
						item.setInsurance(insuranceService.get(item.getInsurance().getInsId()));
						item.setQuota(inquiry.getQuotaByQuotaId(item.getQuota().getQuotaId()));
						orderList.add(item);
					}
					if("4".equals(item.getQueryStage()) || "14".equals(item.getQueryStage())){
						has4 = "1";
						item.setInsurance(insuranceService.get(item.getInsurance().getInsId()));
						item.setQuota(inquiry.getQuotaByQuotaId(item.getQuota().getQuotaId()));
						orderList.add(item);
					}
				}
				inquiry.setOrderList(orderList);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e );
		}
		model.addAttribute("inquiry", inquiry);
		model.addAttribute("has2", has2);
		model.addAttribute("has4", has4);
		return "flows.opetateVote";
	}
	
	@RequestMapping("/getInquiryFileRequires.do")
	@ResponseBody
	public List<InquiryFileRequireDTO> getInquiryFileRequires(String inquiryId,String insId){
		try {
			MicroDTO microDTO = microService
					.getMicroByUserId(getAuthenticatedUserId());
			InquiryDTO inquiry = inquiryService.get(inquiryId, microDTO.getMicro_id());
			
			List<InquiryFileRequireDTO> ifrs = inquiryFileRequireService.getInquiryFileRequires(inquiry.getInquiryId(), insId);
			
			for(InquiryFileRequireDTO ifr : ifrs){
				if(!StringUtils.isBlank(ifr.getFileId())){
					ifr.setFileId(fileserverUrl + ifr.getFileId()) ;
				}
			}
			
			return ifrs;
		} catch (Exception e) {
			logger.error("查询失败", e);
			return null;
		}
	}
	
	@RequestMapping("/clearInquiryFileRequires.do")
	@ResponseBody
	public int clearInquiryFileRequires(String inquiryId,String insId,String fileType){
		try {
			MicroDTO microDTO = microService
					.getMicroByUserId(getAuthenticatedUserId());
			InquiryDTO inquiry = inquiryService.get(inquiryId, microDTO.getMicro_id());
			InquiryFileRequireDTO inquiryFileRequireDTO = new InquiryFileRequireDTO();
			inquiryFileRequireDTO.setInquiryId(inquiry.getInquiryId());
			inquiryFileRequireDTO.setInsId(insId);
			inquiryFileRequireDTO.setFileType(fileType);
			inquiryFileRequireDTO.setFileId("");
			
			return inquiryFileRequireService.updateInquiryFileRequires(inquiryFileRequireDTO);
		} catch (Exception e) {
			logger.error("更新失败", e);
			return -1;
		}
	}
}