package com.zxcl.webapp.web.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.webapp.biz.action.call.CallAction;
import com.zxcl.webapp.biz.action.call.HttpPingAnCallAction;
import com.zxcl.webapp.biz.action.call.IntfDaDiCallAction;
import com.zxcl.webapp.biz.action.pay.HuaAnPayAction;
import com.zxcl.webapp.biz.action.pay.PayAction;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.AgencyService;
import com.zxcl.webapp.biz.service.CommonCarInsurance;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsXmlService;
import com.zxcl.webapp.biz.service.InsuranceCompanyConfigService;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.PayService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.service.VoteInsuranceService;
import com.zxcl.webapp.biz.service.WalletActiveService;
import com.zxcl.webapp.biz.util.ConstantsUtill;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.AgentServiceControlDTO;
import com.zxcl.webapp.dto.BankCardInfoDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.InterfaceMsg;
import com.zxcl.webapp.dto.rmi.intf.pay.resp.PayReturnDTO;
import com.zxcl.webapp.integration.dao.AgentServiceControlDAO;
import com.zxcl.webapp.web.util.IoUtil;
import com.zxcl.webapp.web.vo.AjaxResult;

@Controller
@SessionAttributes(WebAttributes.AUTHENTICATION_EXCEPTION)
public class PayController extends BaseController {

	/**
	 * Logger
	 */
	private static Logger logger = Logger.getLogger(PayController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private MicroService microService;

	@Autowired
	private QuotaService quotaService;

	@Autowired
	private InsuranceService insuranceService;

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private PayService payService;

	@Autowired
	private CallAction callAction;

	@Autowired
	@Qualifier("fdcpwapAction")
	private PayAction fdcpwapAction;
	
	@Autowired
	@Qualifier("paicwapAction")
	private PayAction paicwapAction;
	
	@Autowired
	@Qualifier("hhbxwapAction")
	private PayAction hhbxwapAction;
	
	@Autowired
	private InsuranceCompanyConfigService insuranceCompanyConfigService;

	@Value("${cntaiping.wap.return}")
	private String Url;

	@Value("${fdcp.wap.callback.url}")
	private String fdcpPayBackUrl;

	@Value("${tpbx.wap.pay.url}")
	private String tpbxPayUrl;

	@Value("${mp.weixin.bind.url}")
	private String wechatBindUrl;
	
	@Value("${hacp.callback.url}")
	private String hacpCallBackUrl;

	@Autowired
	private VoteInsuranceService voteInsuranceService;
	
	@Autowired
	private HttpPingAnCallAction httpPingAnCallAction;

	@Autowired
	private InquiryService inquiryService;

	@Autowired
	@Qualifier("jticAction")
	private PayAction jticAction;


	@Autowired
	private HuaAnPayAction hacpAction;
	
	@Autowired
	private CommonCarInsurance commonCarInsurance;
	
	@Autowired
	private WalletActiveService walletActiveService;
	
	@Autowired
	private InsXmlService insXmlService;
	
	@Autowired
	private IntfDaDiCallAction intfDaDiCallAction;

	@Value("${yongcheng_intf}")
	private String yongchengIntfUrl;

	@Value("${jintai_intf_url}")
	private String jintaiIntfUrl;
	
	@Value("${taiping_url}")
	private String taiping_url;
	
	@Value("${yangguang_undrResult_url}")
	private String yangguang_undrResult_url;
	
	@Value("${yangguang_plyResult_url}")
	private String yangguang_plyResult_url;
	
	@Autowired
	private AgentServiceControlDAO agentServiceControlDAO;
	/**
	 * 
	 */
	public PayController() {
	}

	private static String rootURL(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String url = request.getHeader("Referer");
		int index = url.indexOf(contextPath);
		return url.substring(0, index) + contextPath;
	}

	/**
	 * 缴费校验
	 */
	@RequestMapping("/payCheck.do")
	@ResponseBody
	public AjaxResult payCheck(String insId, String orderId, String inquiryId, String quotaId) {
		logger.info("insId:" + insId + "   orderId:" + orderId);
		try {

//			InsuranceDTO insTop = insuranceService.getTop(insId);

			String userId = this.getAuthenticatedUserId();
			OrderDTO order = orderService.getOrderById(microService.getMicroByUserId(userId).getMicro_id(), insId, orderId);
			// 组装报文信息
			if (order != null) {
				InterfaceMsg interfaceMsg = callAction.payCheck(userId, insId, orderId);
				// 调用接口
				if (!"0000".equals(interfaceMsg.getErrorCode())) {
					return new AjaxResult(false, "当前订单不允许缴费！");
				}
				if (null != insId && "TPBX".equals(insId)) {

					boolean y = false;

					return new AjaxResult(y, "TPBX", new OrderDTO(orderId, new InsuranceDTO(insId)));
				} else {
					return new AjaxResult(true, "", new OrderDTO(orderId, new InsuranceDTO(insId)));
				}
			} else {
				return new AjaxResult(false, "查询不到订单的相关信息！");
			}
		} catch (Exception e) {
			logger.error("对保险公司:" + insId + "订单:" + orderId + "订单支付校验失败:" , e);
		}
		return new AjaxResult(false, "查询不到订单的相关信息！");
	}

	/**
	 * 插入支付信息
	 */
	@RequestMapping("/payer.do")
	@ResponseBody
	public void payer(HttpServletRequest request, HttpServletResponse response, String orderId, String insId, String payWay) {
		String userId = this.getAuthenticatedUserId();
		logger.info("orderId:" + orderId + "  insId:" + insId + "   payWay:" + payWay + "  userId:" + userId);
		try {
			MicroDTO micro = microService.getMicroByUserId(userId);
			OrderDTO payer = orderService.getPay(micro.getMicro_id(), insId, orderId);
			OrderDTO order = orderService.getOrderById(micro.getMicro_id(), insId, orderId);
			// 公用信息
			order.setPayTime(new Date());
			order.setUpdCode(userId);
			order.setUpdTime(new Date());
			if (payer != null && ConstantsUtill.ORDERSTATUS_PAYFAIL.equals(order.getQueryStage())) {// 更新支付数据
				logger.info("更新支付信息");
				order.setMicro(micro);
				order.setInsurance(new InsuranceDTO(insId));
				orderService.updatePay(order);
			} else if (payer == null) { // 新增支付数据
				logger.info("新增支付信息");
				order.setPayId(DateUtil.dateToString("yyyyMMddHHmmssSSS", new Date()));
				order.setCrtCode(userId);
				order.setStatus("1");
				order.setPayWay(payWay);
				orderService.insertOrderPay(order);
			}
		} catch (Exception e) {
			logger.error("用户:" + getAuthenticatedUserId() + ",插入订单:" + orderId + "支付信息失败:" , e);
		}
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/allinpayContro.do")
	public String allinpayContro(HttpServletRequest request, HttpServletResponse response, String insId, String orderId) {
		logger.info("AllinpayController allinpayContro.do ==>参数：insId:" + insId + ",orderId:" + orderId);
		try {
			InsuranceDTO insuranceDTO = insuranceService.get(insId);
			request.setAttribute("insDTO", insuranceDTO);
			String userId = getAuthenticatedUserId();
			OrderDTO order = orderService.getOrderById(microService.getMicroByUserId(userId).getMicro_id(), insId, orderId);
			// 获取支付的方式
			List<String> payWay = payService.getPayWay(userId);
			for (String way : payWay) {
				PayAction payAction = null;
				try {
					payAction = (PayAction) WebApplicationContextUtils.getRequiredWebApplicationContext(
							request.getSession().getServletContext()).getBean(
									insId.toLowerCase() + "Action");
				} catch (NoSuchBeanDefinitionException e) {
					logger.warn("用户:" + userId + "生成保险公司:" + insId + ",支付方式:" + way + "的支付Action失败." , e);
					continue;
				}
				payAction.pay(request, userId, order);
			}
			//配送开关
			String disPanDuan = "0";
			MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
			AgentServiceControlDTO agentServiceControlDTO = agentServiceControlDAO.selectByAgentIdAndServiceType(microDTO.getAgency().getAgent_id(), 3);
			if(null != agentServiceControlDTO && 1 == agentServiceControlDTO.getIsOn()){
				disPanDuan = "1";
			}
			request.setAttribute("disPanDuan", disPanDuan);
			request.setAttribute("order", order);
		} catch (Exception e) {
			logger.error("用户:" + getAuthenticatedUserId() + "生成订单:" + orderId + "的支付方式失败:" , e);
			request.setAttribute("order", new OrderDTO());
		}
		return "pay.pay";
	}

	
	/**
	 * 富德支付
	 * 
	 * @param request
	 * @param response
	 * @param inquiryId
	 * @param orderId
	 * @param insId
	 * @return
	 */
	@RequestMapping(value = "fdcp/pay.do")
	@Log("富德支付")
	public String fdcpPay(HttpServletRequest request, HttpServletResponse response, String inquiryId, String orderId, String insId) {

		// 小微
		MicroDTO microDTO = null;
		try {
			microDTO = microService.getMicroByUserId(getAuthenticatedUserId());
		} catch (BusinessServiceException e) {
			logger.error("查询小微失败", e);
		}

		// 订单
		OrderDTO orderDTO = null;
		QuotaDTO quotaDTO = null;
		String totalAmount = "0.00";
		PayReturnDTO payReturnDTO = null;
		String url = "";
		try {
			orderDTO = orderService.getOrderById(microDTO.getMicro_id(), insId, orderId);
			if (null != orderDTO && null != orderDTO.getQuota().getQuotaId()) {
				quotaDTO = quotaService.getByQuotaId(orderDTO.getQuota().getQuotaId());
			}
			if (null != quotaDTO) {
				totalAmount = quotaDTO.getTotalAmount();
			}

			String rootUrl = rootURL(request);
			url = fdcpPayBackUrl.replace("#orderNo#", orderDTO.getNoticeNo());
			url = url.replace("#bgUrl#", rootUrl);
			url = url.replace("#orderAmount#", totalAmount);

			request.setAttribute("payReturnDTO", payReturnDTO);
			request.setAttribute("url", url);
			request.setAttribute("order", orderDTO);
			request.setAttribute("insId", insId);
			request.setAttribute("urlType", 1);
			
			InsuranceDTO insDTO = insuranceService.get(insId);
			request.setAttribute("insDTO", insDTO);
			//配送开关
			String disPanDuan = "0";
			AgentServiceControlDTO agentServiceControlDTO = agentServiceControlDAO.selectByAgentIdAndServiceType(microDTO.getAgency().getAgent_id(), 3);
			if(null != agentServiceControlDTO && 1 == agentServiceControlDTO.getIsOn()){
				disPanDuan = "1";
			}
			request.setAttribute("disPanDuan", disPanDuan);
		} catch (BusinessServiceException e) {
			logger.error("富德支付数据组织失败", e);
		}
		return "pay.pay";
	}
	
	@RequestMapping(value = "pinganHttp/pay.do")
	@Log("平安HTTP支付")
	public String pinganHttpPay(HttpServletRequest request, HttpServletResponse response, String inquiryId, String orderId, String insId) {

		// 小微
		MicroDTO microDTO = null;
		try {
			microDTO = microService.getMicroByUserId(getAuthenticatedUserId());
		} catch (BusinessServiceException e) {
			logger.error("查询小微失败", e);
		}

		// 订单
		OrderDTO orderDTO = null;
		PayReturnDTO payReturnDTO = null;
		String url = "";
		try {
			String ui = this.getAuthenticatedUserId();
			orderDTO = orderService.getOrderById(microDTO.getMicro_id(), insId, orderId);
			payReturnDTO = httpPingAnCallAction.pay(ui, insId, orderId, null, getConfigInfo(ui, insId));
			if(null != payReturnDTO){
				url = payReturnDTO.getPayUrl();
			}
			request.setAttribute("payReturnDTO", payReturnDTO);
			request.setAttribute("url", url);
			request.setAttribute("order", orderDTO);
			request.setAttribute("insId", insId);
			request.setAttribute("urlType", 1);
			
			InsuranceDTO insDTO = insuranceService.get(insId);
			request.setAttribute("insDTO", insDTO);
			//配送开关
			String disPanDuan = "0";
			AgentServiceControlDTO agentServiceControlDTO = agentServiceControlDAO.selectByAgentIdAndServiceType(microDTO.getAgency().getAgent_id(), 3);
			if(null != agentServiceControlDTO && 1 == agentServiceControlDTO.getIsOn()){
				disPanDuan = "1";
			}
			request.setAttribute("disPanDuan", disPanDuan);
		} catch (Exception e) {
			logger.error("平安HTTP支付数据组织失败", e);
		}
		return "pay.pay";
	}
	
	public Map<String, Object> getConfigInfo(String userId, String insId) throws ActionException {
		// 小微
		MicroDTO microDTO = new MicroDTO();
		try {
			microDTO = microService.getMicroByUserId(userId);
		} catch (BusinessServiceException e) {
			logger.error("查询小微失败", e);
			throw new ActionException("查询小微失败");
		}
		// 获取保险公司配置信息
		String configId = "";
		try {// 查询保险公司配置信息ID
			configId = microService.getConfigIdByInsIdAndMicroId(insId, microDTO.getMicro_id());
		} catch (BusinessServiceException e) {
			logger.error("太查询保险公司配置信息ID失败", e);
			throw new ActionException("查询保险公司配置信息ID失败");
		}
		Map<String, Object> configMap = new HashMap<String, Object>();
		try {// 查询保险公司配置信息
			configMap = insuranceCompanyConfigService.getMapByInsId(configId);
		} catch (BusinessServiceException e) {
			logger.error("查询保险公司配置信息失败", e);
			throw new ActionException("查询保险公司配置信息失败");
		}
		return configMap;
	}

	/**
	 * 富德支付回调
	 * 
	 * @param request
	 * @param response
	 * @param companyName
	 * @param payName
	 * @param callbackType
	 */
	@RequestMapping("fdcp/wap/cp.do")
	public void fdcpPayCallback(HttpServletRequest request, HttpServletResponse response) {
		logger.info("富德支付回调开始");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = null;
		try {
			
			writer = response.getWriter();
			if(fdcpwapAction.payedCallback(request, null)){
				writer.write("{\"noticeCode\":\"Y\",\"noticeMsg\":\"ok\"}");
			}
			else{
				writer.write("{\"noticeCode\":\"N\",\"noticeMsg\":\"failed\"}");
			}
		} catch (Exception e) {
			writer.write("{\"noticeCode\":\"N\",\"noticeMsg\":\"failed:"+e.getMessage()+"\"}");
			logger.error("富德支付回调失败", e);
		}
		writer.flush();
		writer.close();
		logger.info("富德支付回调结束");
	}

	/**
	 * 锦泰支付页面回调
	 * 
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("jtic/payCallbackPortal/{orderId}")
	public String payCallbackPortal(@PathVariable String orderId, HttpServletRequest request, HttpServletResponse response) {
		logger.info("锦泰支付页面回调==>开始");
		try {
			logger.info(request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
			jticAction.payedCallback2(request, orderId);
			logger.info("锦泰支付页面回调==>结束");
		} catch (Exception e) {
			logger.error("锦泰支付通知出现错误", e);
		}

		return "pay.pay";
	}
	
	

	/**
	 * 锦泰支付后台回调
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("jtic/payCallback.do")
	public void payCallback(HttpServletRequest request, HttpServletResponse response) {
		logger.info("锦泰支付后台回调==>开始");
		String respXml = "";
		try {
			logger.info(request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
			String xml = IoUtil.stream2text(request.getInputStream(), "utf-8");
			logger.info("锦泰支付后台回调收到报文:" + xml);
			if (StringUtils.isNotBlank(xml)) {
				// 锦泰支付后台处理
				respXml = post(jintaiIntfUrl.replace("/remote","")+"/payCallback.do",xml,"utf-8");
				logger.info("锦泰支付后台回调通知==>结束.");
			}
		} catch (Exception e) {
			logger.error("锦泰支付后台回调通知出现错误", e);
		}
		writeResp(response, respXml);

	}


	/**
	 * 平安支付回调
	 * 
	 * @param request
	 * @param response
	 */
	@Log("平安支付回调")
	@RequestMapping("/call/paic/payCallback.do")
	public void paicPayCallback(HttpServletRequest request, HttpServletResponse response) {
		logger.info("平安支付回调开始");
		try {
			paicwapAction.payedCallback(request, null);
		} catch (BusinessServiceException e1) {
			logger.info("平安支付回调失败", e1);
		}
		logger.info("平安支付回调结束");
		
		try {
			response.sendRedirect(wechatBindUrl.replace("bind.do", "list2.do"));
		} catch (IOException e) {
			logger.info("平安支付回调结束<==重定向失败");
		}
	}
	
	
	/**
	 * 华海支付回调
	 * 
	 * @param request
	 * @param response
	 */
	@Log("华海支付回调")
	@RequestMapping("/call/hhbx/payCallBack.do")
	public void hhbxPayCallback(HttpServletRequest request, HttpServletResponse response) {
		logger.info("华海支付回调开始");
		try {
			hhbxwapAction.payedCallback(request, null);
		} catch (BusinessServiceException e1) {
			logger.info("华海支付回调失败", e1);
		}
		logger.info("华海支付回调结束");
		
		try {
			response.sendRedirect(wechatBindUrl.replace("bind.do", "list2.do"));
		} catch (IOException e) {
			logger.info("华海支付回调结束<==重定向失败");
		}
	}

	@RequestMapping("/hhbx/pay.do")
	@Log("华海支付")
	public String hhbxPay(HttpServletRequest request, HttpServletResponse response, String quotaId, String insId, String inquiryId, String orderId) {
		logger.info("PayController hhbxPay.do");
		try {

			OrderDTO orderDTO = orderService.getOrderById(microService.getMicroByUserId(getAuthenticatedUserId()).getMicro_id(), insId, orderId);

			PayReturnDTO payReturnDTO = callAction.pay("", "", orderDTO.getQuota().getQuotaId(), insId, getAuthenticatedUserId(), orderDTO.getInquiry().getInquiryId(), orderId);

			request.setAttribute("order", orderDTO);
			request.setAttribute("payReturnDTO", payReturnDTO);
			request.setAttribute("insId", insId);
			request.setAttribute("urlType", 1);
			request.setAttribute("url", payReturnDTO.getPayUrl());
			
			InsuranceDTO insDTO = insuranceService.get(insId);
			request.setAttribute("insDTO", insDTO);
			//配送开关
			String disPanDuan = "0";
			MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
			AgentServiceControlDTO agentServiceControlDTO = agentServiceControlDAO.selectByAgentIdAndServiceType(microDTO.getAgency().getAgent_id(), 3);
			if(null != agentServiceControlDTO && 1 == agentServiceControlDTO.getIsOn()){
				disPanDuan = "1";
			}
			request.setAttribute("disPanDuan", disPanDuan);
		} catch (Exception e) {
			logger.error("华海支付", e);
		}
		return "pay.pay";
	}
	
	
	/**
	 * 太平支付回调
	 * 
	 * @param request
	 * @param response
	 * @param orderId
	 * @return
	 */
	@RequestMapping("tpic/payedCallback.do")
	public String tpicPayedCallback(HttpServletRequest request, HttpServletResponse response, String bxtxOrderId) {
		logger.info("AllinpayController payedCallback.do");
		try {
			OrderDTO order = orderService.getOrderById("", "", bxtxOrderId);
			InsuranceDTO insDTO = insuranceService.get(order.getInsurance().getInsId());
			request.setAttribute("insDTO", insDTO);
			request.setAttribute("order", order);
			request.setAttribute("success", "1");
			request.setAttribute("msg", "正在生成保单中...");
			request.setAttribute("info", "1");
		} catch (Exception e) {
			logger.error("太平支付回调", e);
		}
		return "pay.pay";
	}

	/**
	 * 输出返回信息.
	 * 
	 * @param resp
	 * @param content
	 */
	public void writeResp(HttpServletResponse resp, String content) {
		try {
			resp.setContentType("text/xml");
			resp.setCharacterEncoding("utf-8");
			resp.getOutputStream().write(content.getBytes("utf-8"));
		} catch (Exception e) {
			logger.error("输出响应报文时出现错误", e);
		}
	}

	/**
	 * 承保确认通知
	 * 
	 * @return
	 */
	@RequestMapping("tpic/underwriteAffirmInform.do")
	public void tpicUnderwriteAffirmInform(HttpServletRequest request, HttpServletResponse response) {
		logger.info("太平承保成功回调！");

		String respXml = "";
		try {
			logger.info(request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
			String xml = IoUtil.stream2text(request.getInputStream(), "utf-8");
			logger.info("收到报文:" + xml);
			if (StringUtils.isNotBlank(xml)) {
				respXml = post(taiping_url, xml,"utf-8");
				logger.info("太平承保成功通知--结束.");
			}
		} catch (Exception e) {
			logger.error("太平承保成功通知出现错误", e);
		}

		writeResp(response, respXml);
	}

	/**
	 * 阳光回调
	 * 
	 * @param request
	 * @param response
	 * @param orderId
	 */
	@RequestMapping(value = "payedCallback.do")
	public String payedCallback(HttpServletRequest request, HttpServletResponse response) {
		logger.info("AllinpayController payedCallback.do");

		return "redirect:list2.do";
	}

	/**
	 * 阳光核保
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "ygbx/undrResult.do")
	public void ygbxUndrResult(HttpServletRequest request, HttpServletResponse response) {
		logger.info("阳光核保");
		String type = "0";
		try {
			logger.info(request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
			String xml = IoUtil.stream2text(request.getInputStream(), "GBK");
			logger.info("收到阳光核保的报文:" + xml);
			if (StringUtils.isNotBlank(xml)) {
				type = post(yangguang_undrResult_url, xml,"GBK");
				logger.info("阳光核保--结束");
			}
		} catch (Exception e) {
			logger.error("阳光核保成功出现错误", e);
		} finally {
			writeResp(response, type);
		}
	}

	/**
	 * 阳光支付成功
	 */
	@RequestMapping(value = "ygbx/plyResult.do")
	public void ygbxPlyResult(HttpServletRequest request, HttpServletResponse response) {
		logger.info("阳光支付回调成功");
		String type = "0";
		try {
			logger.info(request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
			String xml = IoUtil.stream2text(request.getInputStream(), "GBK");
			logger.info("收到阳光支付通知的报文:" + xml);
			if (StringUtils.isNotBlank(xml)) {
				type = post(yangguang_plyResult_url, xml,"GBK");
				logger.info("阳光支付回调--结束");
			}
		} catch (Exception e) {
			logger.error("阳光支付出现错误", e);
		} finally {
			writeResp(response, type);
		}
	}

	/**
	 * 保险公司直接给出路径用的支付方法
	 * 
	 * @param request
	 * @param response
	 * @param inquiryId
	 *            询价单号
	 * @param orderId
	 *            保单号
	 * @param insId
	 *            保险公司ID
	 * @return
	 */
	@RequestMapping(value = "pay.do")
	public String pay(HttpServletRequest request, HttpServletResponse response, String inquiryId, String orderId, String insId) {
		logger.info("AllinpayController pay.do");
		// 顶级保险公司
		InsuranceDTO insDTO = new InsuranceDTO();
		String userId = getAuthenticatedUserId();
		try {
			insDTO = insuranceService.get(insId);
		} catch (BusinessServiceException e) {

			logger.error("查询顶级保险公司失败", e);
		}

		// 小微
		MicroDTO microDTO = new MicroDTO();
		try {
			microDTO = microService.getMicroByUserId(getAuthenticatedUserId());
		} catch (BusinessServiceException e) {

			logger.error("查询小微失败", e);
		}

		// 订单
		OrderDTO orderDTO = null;
		QuotaDTO quotaDTO = null;
		String totalAmount = "0.00";
		try {
			orderDTO = orderService.getOrderById(microDTO.getMicro_id(), insId, orderId);
			if (null != orderDTO && null != orderDTO.getQuota().getQuotaId()) {
				quotaDTO = quotaService.getByQuotaId(orderDTO.getQuota().getQuotaId());
			}
			if (null != quotaDTO) {
				totalAmount = quotaDTO.getTotalAmount();
			}
		} catch (BusinessServiceException e) {

			logger.error("查询订单失败", e);
		}
		PayReturnDTO payReturnDTO = null;
		// 路径
		String url = "";
		try {
			switch (insId) {
			case "YGBX":// 阳光
//				url = "http://testservicebus.sinosig.com/WebContent/PayWithForSeeFee.action?ProposalNo=" + applyNo + "&flag=DSZF&pageFlag=fivePayPage";
				payReturnDTO = callAction.pay("", "", orderDTO.getQuota().getQuotaId(), insId, userId, orderDTO.getInquiry().getInquiryId(), orderId);
				url = payReturnDTO.getPayUrl();
				break;
			case "PAIC":// 平安
				payReturnDTO = callAction.pay("", "", orderDTO.getQuota().getQuotaId(), insId, userId, orderDTO.getInquiry().getInquiryId(), orderId);
				url = payReturnDTO.getPayUrl();
				break;
			case "FDCP":// 富德
				String rootUrl = rootURL(request);
				url = fdcpPayBackUrl.replace("#orderNo#", orderDTO.getNoticeNo());
				url = url.replace("#bgUrl#", rootUrl);
				url = url.replace("#orderAmount#", totalAmount);
				url = url.replace("#pageUrl#", rootUrl);
				break;
			case "TPIC":
				payReturnDTO = callAction.pay(Url + "?bxtxOrderId=" + orderId, "wapPay1", quotaDTO.getQuotaId(), insId, getAuthenticatedUserId(), inquiryId, orderId);
				if(null != payReturnDTO){
					if("1".equals(payReturnDTO.getType())){
						request.setAttribute("type", "1");
						request.setAttribute("msgInfo", "该订单已支付，请勿重新支付");
					}else{						
						url = payReturnDTO.getPayUrl();
						try {
							orderService.updateNoticeNoByOrderId(orderId, payReturnDTO.getTradeNo(), this.getAuthenticatedUserId());
						} catch (BusinessServiceException e) {
							logger.error("太平根据订单编码更新订单号失败",e);
						}
					}
				}else{
					logger.info("太平获取支付路径失败，订单号为："+orderId);
				}
				break;
			case "HACP":
				payReturnDTO = callAction.pay(hacpCallBackUrl + "?orderId=" + orderId, "wapPay1", quotaDTO.getQuotaId(), insId, getAuthenticatedUserId(), inquiryId, orderId);
				url = payReturnDTO.getPayUrl();
				break;
			case "CPIC":
				payReturnDTO = callAction.pay("", "", quotaDTO.getQuotaId(), insId, getAuthenticatedUserId(), inquiryId, orderId);
				url = payReturnDTO.getPayUrl();
				break;
			case "CCIC":
				payReturnDTO = callAction.pay(hacpCallBackUrl + "?orderId=" + orderId, "wapPay1", quotaDTO.getQuotaId(), insId, getAuthenticatedUserId(), inquiryId, orderId);
				url = payReturnDTO.getPayUrl();
				break;
			case "ACIC":
				payReturnDTO = callAction.pay(hacpCallBackUrl + "?orderId=" + orderId, "wapPay1", quotaDTO.getQuotaId(), insId, getAuthenticatedUserId(), inquiryId, orderId);
				url = payReturnDTO.getPayUrl();
				break;
			case "HTIC"://华泰保险
				payReturnDTO = callAction.pay("", "", quotaDTO.getQuotaId(), insId, getAuthenticatedUserId(), inquiryId, orderId);
				url = payReturnDTO.getPayUrl();
				if(StringUtils.isBlank(url)){
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/html");
					String reason = StringUtils.isBlank(payReturnDTO.getPayResult())?"获取支付地址失败":payReturnDTO.getPayResult();
					response.getWriter().write("<script>alert('"+reason+"');window.location.href='"+request.getContextPath()+"/index.do';</script>");
					return null;
				}
				break;
			case "LIBAO"://利宝保险
				payReturnDTO = callAction.pay("", "", quotaDTO.getQuotaId(), insId, getAuthenticatedUserId(), inquiryId, orderId);
				url = payReturnDTO.getPayUrl();
				if(StringUtils.isBlank(url) || StringUtils.isBlank(payReturnDTO.getPayResult())){
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/html");
					String reason = StringUtils.isBlank(payReturnDTO.getPayResult())?"获取支付地址失败":payReturnDTO.getPayResult();
					response.getWriter().write("<script>alert('"+reason+"');window.location.href='"+request.getContextPath()+"/index.do';</script>");
					return null;
				}
				@SuppressWarnings("unchecked")
				Map<String, String> payFormMap = JSONObject.parseObject(payReturnDTO.getPayResult(), Map.class);
				request.setAttribute("libaoPayFormMap", payFormMap);
				break;
			default:
				break;
			}
			//配送开关
			String disPanDuan = "0";
			AgentServiceControlDTO agentServiceControlDTO = agentServiceControlDAO.selectByAgentIdAndServiceType(microDTO.getAgency().getAgent_id(), 3);
			if(null != agentServiceControlDTO && 1 == agentServiceControlDTO.getIsOn()){
				disPanDuan = "1";
			}
			request.setAttribute("disPanDuan", disPanDuan);
		} catch (Exception e) {
			logger.error("用户:" + userId + "调用保险公司:" + insId + "支付接口失败:", e);
		}
		request.setAttribute("payReturnDTO", payReturnDTO);
		request.setAttribute("url", url);
		request.setAttribute("order", orderDTO);
		request.setAttribute("insId", insId);
		request.setAttribute("insDTO", insDTO);
		// 路径标志，通知页面该路径是页面直接给出，有且只有一条路径
		request.setAttribute("urlType", 1);
		
		return "pay.pay";
	}
	

	/**
	 * 支付完成
	 * 
	 * @param request
	 * @param response
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "tpbx/payOver.do")
	public String tpbxPayOver(HttpServletRequest request, HttpServletResponse response, String orderId, BankCardInfoDTO bankCardInfoDTO, String quotaId) {
		logger.info("AllinpayController tpbxPayOver.do");
		try {
			OrderDTO order = orderService.getOrderById("", "", orderId);
			if ("5".equals(order.getQueryStage()) || "6".equals(order.getQueryStage())) {
				request.setAttribute("success", "1");
			} else {
				request.setAttribute("success", "0");
			}
			InsuranceDTO insDTO = insuranceService.get(order.getInsurance().getInsId());
			request.setAttribute("insDTO", insDTO);
			request.setAttribute("order", order);
		} catch (Exception e) {
			logger.error("天平支付回调", e);
		}
		return "pay.pay";
	}
	
	@RequestMapping("hacp/payCallbackPage.do")
	public String hacpPayedCallback(String orderId, HttpServletRequest request, HttpServletResponse response) {
		logger.info("华安支付页面回调==>开始");
		logger.info("接收到的参数: " + " orderId:" + orderId);

		try {
			logger.info(request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
			hacpAction.payedCallbackPage(request, orderId.split(",")[0]);
			logger.info("华安支付页面回调==>结束");
		} catch (Exception e) {
			logger.error("华安支付通知出现错误", e);
		}
		return "pay.pay";
	}

	@RequestMapping("hacp/payCallback.do")
	public void hacpPayCallback(HttpServletRequest request, HttpServletResponse response) {
		logger.info("华安支付后台回调==>开始");

		try {
			logger.info(request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
			hacpAction.payedCallback(request);
			logger.info("华安支付后台回调通知==>结束.");
		} catch (Exception e) {
			logger.error("华安支付后台回调通知出现错误", e);
		}
	}
	@RequestMapping("cpic/wxpayResult.do")
	public String cpicWxpayResult(HttpServletRequest request, HttpServletResponse response,String orderNo,String statue,String id){
		logger.info("AllinpayController cpicWxpayResult.do 太平洋支付回调   orderNo："+orderNo+"  statue："+statue);
		try {
			OrderDTO order = orderService.getOrderById("", "", orderNo);
			if("0".equals(statue) && !"6".equals(order.getQueryStage())){//支付成功
				orderService.updateOrderStatusByOrderId("5", orderNo);
			}else if("1".equals(statue) && !"6".equals(order.getQueryStage())){
				orderService.updateOrderStatusByOrderId("8", orderNo);
			}
			request.setAttribute("success", "0".equals(statue) ? "1" : "0");
			InsuranceDTO insDTO = insuranceService.get(order.getInsurance().getInsId());
			request.setAttribute("insDTO", insDTO);
			request.setAttribute("order", order);
		} catch (Exception e) {
			logger.error("太平洋支付回调失败", e);
		}
		return "pay.pay";
	}
	

	/**
	 * 大地核保回调
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("ccic/undwrtResult.do")
	public void ccicUndrResult(HttpServletRequest request, HttpServletResponse response) {
		logger.info("大地核保回调");
		String respXml = "";
		try {
			logger.info(request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
			
			InputStream input =  request.getInputStream();  
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			byte[] buffer = new byte[1024];  
			int len;  
			while ((len = input.read(buffer)) > -1 ) {  
			    baos.write(buffer, 0, len);  
			}  
			baos.flush();                
			InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());  
			InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());
			
			String xml2 = IoUtil.stream2text(stream1, "GBK");
			logger.info("收到大地核保回调的报文GBK:" + xml2);
			String xml = IoUtil.stream2text(stream2, "utf-8");
			logger.info("收到大地核保回调的报文utf-8:" + xml);
			
			if (StringUtils.isNotBlank(xml)) {
				try{
					respXml=intfDaDiCallAction.undrResult(xml2);
				}catch(Exception e){
					respXml=intfDaDiCallAction.undrResult(xml);
				}
				
				logger.info("大地核保回调--结束");
			}
			
			
		} catch (Exception e) {
			logger.error("大地核保回调成功出现错误", e);
		} finally {
			writeResp(response, respXml);
		}
	}

	@RequestMapping("ccic/payCallback.do")
	public void ccicPayCallback(HttpServletRequest request, HttpServletResponse response) {
		logger.info("大地支付后台回调==>开始");
		String respXml = "";
		try {
			logger.info(request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
			// hacpAction.payedCallback(request);
			logger.info("大地支付后台回调通知==>结束.");
		} catch (Exception e) {
			logger.error("大地支付后台回调通知出现错误", e);
		} finally {
			writeResp(response, respXml);
		}
	}
	

	@RequestMapping("yongcheng/callback.do")
	public void aicsCallback(HttpServletRequest request, HttpServletResponse response){
		
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			logger.debug("永诚回调的base64字符串：" + sb.toString());
			post(yongchengIntfUrl+"yongcheng/callback.do",sb.toString(),"utf-8");
			
		} catch (Exception e) {
			logger.error("读取回调信息失败", e);
		}
		
		//判断
	}
	
	private String post(String url, String xml,String code) throws IOException, SocketTimeoutException {
		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream in = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(60 * 1000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			os = conn.getOutputStream();
			os.write(xml.getBytes(code));
			os.close();

			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				return toText(in, code);
			} else {
				throw new RuntimeException("HTTP ERROR:" + conn.getResponseCode() + " " + conn.getResponseMessage());
			}
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (Exception e) {
			}
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {
			}
			if (conn != null)
				conn.disconnect();
		}
	}
	
	/**
	 * 输入流按照指定字符集转换为文本
	 */
	private String toText(InputStream in, String charset) throws IOException {
		byte[] buf = new byte[1024];
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int c = -1;
		while ((c = in.read(buf)) != -1) {
			bout.write(buf, 0, c);
		}
		String s = bout.toString(charset);
		bout.close();
		return s;
	}
	
	/**
	 * 支付（新接口使用）
	 * @param request
	 * @param response
	 * @param quoteId 报价单号
	 * @param insId 保险公司ID
	 * @param inquiryId 询价单号
	 * @param orderId 订单号
	 * @return
	 */
	@RequestMapping("payController.do")
	public String payController(HttpServletRequest request, HttpServletResponse response,String quoteId,String insId, String inquiryId, String orderId){
		try {
			com.zxcl.bxtx.dto.intf.PayReturnDTO payReturnDTO = callAction.pay(quoteId,insId, getAuthenticatedUserId(), inquiryId, orderId);
			request.setAttribute("payReturnDTO", payReturnDTO);
			request.setAttribute("url", payReturnDTO.getPayUrl());
			com.zxcl.webapp.dto.OrderDTO orderDTO = orderService.getOrderById(null, insId, orderId);
			request.setAttribute("order", orderDTO);
			request.setAttribute("insId", insId);
			InsuranceDTO insDTO = insuranceService.get(insId);
			request.setAttribute("insDTO", insDTO);
			// 路径标志，通知页面该路径是页面直接给出，有且只有一条路径
			request.setAttribute("urlType", 1);
			//配送开关
			String disPanDuan = "0";
			MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
			AgentServiceControlDTO agentServiceControlDTO = agentServiceControlDAO.selectByAgentIdAndServiceType(microDTO.getAgency().getAgent_id(), 3);
			if(null != agentServiceControlDTO && 1 == agentServiceControlDTO.getIsOn()){
				disPanDuan = "1";
			}
			request.setAttribute("disPanDuan", disPanDuan);
		} catch (Exception e) {
			logger.error("获取支付路径失败",e);
		}
		return "pay.pay";
	}
	
}
