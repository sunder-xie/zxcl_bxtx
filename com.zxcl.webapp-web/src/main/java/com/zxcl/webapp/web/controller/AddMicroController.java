/**
 * 
 */
package com.zxcl.webapp.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import bxtx.intf.weixin.biz.service.WeiXinService;
import bxtx.intf.weixin.dto.WxJsapiSignatureDTO;

import com.meyacom.fw.app.web.controller.BaseController;
import com.meyacom.fw.um.security.core.userdetails.MycSecurityUser;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.exception.SMSTBException;
import com.zxcl.webapp.biz.service.MicroAgentService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WalletBankService;
import com.zxcl.webapp.biz.util.ConstantsUtill;
import com.zxcl.webapp.biz.util.resultEntity.ResultMap;
import com.zxcl.webapp.dto.MicroAgentDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.util.HttpClientUtil;
import com.zxcl.webapp.util.WeiXinUtils;
import com.zxcl.webapp.web.util.CacheUtil;
import com.zxcl.webapp.web.util.SHA1;
import com.zxcl.webapp.web.util.SMSTBUtils;

/**
 * @author MA Xiaoqiang
 * 
 */
@Controller
@SessionAttributes(WebAttributes.AUTHENTICATION_EXCEPTION)
public class AddMicroController extends BaseController {
	/**
	 * Logger
	 */
	private static Logger logger = Logger.getLogger(AddMicroController.class);
	
	@Value("${kefu_url}")
	private String kefuUrl;

    @Value("${save_user_url}")
    private String saveUserUrl;
	
    @Value("${share.url}")
    private String currentServerUrl;
    
	@Autowired
	private MicroService microService;
	
	@Autowired
	private SMSTBUtils sms;
	
	@Autowired
	private MicroAgentService microAgentService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private WalletBankService walletBankService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private WeiXinService weiXinService;
	
	/**
	 * 第一步;手机验证
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addMicro.do")
	public String addMicro(Model model, String invitation, HttpServletRequest request, String regType, String openId) {
		logger.info("addMicro==>regType="+regType+",openId="+openId+",invitation="+invitation);
		if("2".equals(regType)){//注册名义代理人
			String userId = null;
			try {
				SecurityContextImpl token = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
				try {
					MycSecurityUser details = (MycSecurityUser) token.getAuthentication().getPrincipal();
					userId = details.getUserId();
				} catch (Exception e) {
				}
				if(null == userId || userId.isEmpty()){
					Object obj = token.getAuthentication().getPrincipal();
					if(null != obj && obj instanceof String){
						userId = (String) obj;
					}
					else{
						UserDetails details = (UserDetails) obj;
						userId = details.getUsername();
					}
				}
				
				MicroDTO micro = microService.getMicroByUserId(userId);
				if(null != micro){
					List<MicroAgentDTO> list = microAgentService.selectMicroAgentListByMicroId(micro.getMicro_id());
					if(CollectionUtils.isNotEmpty(list) && list.size() >= 2){
						logger.info("名义代理人最多关联2个");
						return "redirect:/wallet_bank/index.do?rac=N";
					}
				}
				
				UserDTO userDTO = userService.queryUserByUserId(userId);
				logger.info("userDTO="+userDTO);
				if(null == userDTO || StringUtils.isBlank(userDTO.getInvitation())){
					regType = "1";
				}
				else{
					invitation = userDTO.getInvitation();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				regType = "1";
			}
		}
		if(StringUtils.isNotBlank(openId) && StringUtils.isBlank(invitation)){
			invitation = CacheUtil.getIviFromMap(openId);
			logger.info("从缓存中获取的邀请码为"+invitation);
		}
		model.addAttribute("regType", regType);
		model.addAttribute("invitation", invitation);
		logger.info("regType="+regType+",invitation="+invitation);
		return "user.addMicro";
	}

	/**
	 * 短信验证和图片限制验证
	 * 
	 * @param request
	 * @param tel
	 * @param vidate
	 * @return
	 */
	@RequestMapping(value = "/phoneVidation.do")
	@ResponseBody
	public ResultMap phoneVidation(HttpServletRequest request, String tel, String vidate, String invitation) {
		logger.info("AddMicroController phoneVidation.do");
		ResultMap resultMap = new ResultMap();
		boolean checkSMSCode = false;
		MicroDTO parentmicro = new MicroDTO();
		try {
			parentmicro = microService.getMicroInvitation(invitation);
		} catch (BusinessServiceException e) {
			logger.error("查询小微失败，tel:" + tel, e);
		}
		Integer selectAllMicro = 0;
		try {
			selectAllMicro = microService.selectAllMicro(tel);
		} catch (BusinessServiceException e) {
			logger.error("查找是否有注册电话失败,tel:" +tel, e);
		}
		if (selectAllMicro == 0) {
			if (parentmicro != null) {
				try {
					logger.info("进行手机验证码验证开始" + checkSMSCode);
					checkSMSCode = sms.checkSMSCode(request, tel, vidate);
					logger.info("进行手机验证码验证完成" + checkSMSCode);
					resultMap.setMessage("验证成功");
					resultMap.setSuccess(checkSMSCode);
					if (!checkSMSCode) {
						resultMap.setMessage("短信验证码有误");
					}

				} catch (Exception e) {
					resultMap.setMessage("短信验证码有误");
					logger.error("短信验证码有误" + e.getStackTrace());
					return resultMap;
				}
			} else {
				resultMap.setMessage("请填写有效的邀请码");
				resultMap.setSuccess(false);
			}
		} else {
			resultMap.setMessage("该手机号已注册");
			resultMap.setSuccess(false);
		}

		logger.info("AddMicroController phoneVidation.end");
		return resultMap;
	}

	/**
	 * 第二步用户登录和密码设置
	 * 
	 * @param request
	 * @param tel
	 * @param vidate
	 * @return
	 */
	@RequestMapping(value = "/phoneVidation2.do")
	public String phoneVidation2(HttpServletRequest request, Model model, String tel, String vidate, String invitation, String regType) {
		logger.info("AddMicroController phoneVidation2.do ==> regType="+regType);
		model.addAttribute("tel", tel);
		model.addAttribute("regType", regType);
		model.addAttribute("invitation", invitation);
		MicroDTO parentmicro = new MicroDTO();
		//根据邀请码获取小薇
		try {
			parentmicro = microService.getMicroInvitation(invitation);
		} catch (BusinessServiceException e) {
			logger.error("查询小微失败", e);
		}
		//获取到小薇的团队名
		try {
			parentmicro = microService.getMicroByUserId(parentmicro.getUser_id());
		} catch (BusinessServiceException e) {
			logger.error("查询小微失败", e);
		}
		model.addAttribute("parentmicro", parentmicro);
		return "user.addMicro2";
	}
	
	@RequestMapping(value = "/redirectUserXW.do")
	@ResponseBody
	public ResultMap redirectUserXW(HttpServletRequest request, Model model, MicroDTO microDTO, String regType, String vidate) {
		String userId = null;
		ResultMap resultMap = new ResultMap();
		MicroDTO myMicroDTO = null;
		if("2".equals(regType)){
			if(StringUtils.isBlank(microDTO.getMicro_name())){
				resultMap.setSuccess(false);
				resultMap.setMessage("请填写姓名");
				return resultMap;
			}
			microDTO.setMicro_name(microDTO.getMicro_name().trim());
			if(!microDTO.getMicro_name().matches("[\\u4e00-\\u9fa5]+")){
				resultMap.setSuccess(false);
				resultMap.setMessage("姓名为中文名称，不能包含字母数字等特殊字符");
				return resultMap;
			}
			try {
				SecurityContextImpl token = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
				try {
					MycSecurityUser details = (MycSecurityUser) token.getAuthentication().getPrincipal();
					userId = details.getUserId();
				} catch (Exception e) {
				}
				if(null == userId || userId.isEmpty()){
					Object obj = token.getAuthentication().getPrincipal();
					if(null != obj && obj instanceof String){
						userId = (String) obj;
					}
					else{
						UserDetails details = (UserDetails) obj;
						userId = details.getUsername();
					}
				}
				
				if(null == userId || userId.isEmpty()){
					throw new Exception("用户未登陆");
				}
				myMicroDTO = microService.getMicroByUserId(userId);
				if(null == myMicroDTO){
					resultMap.setSuccess(false);
					resultMap.setMessage("查询当前用户小微信息失败，对象为空");
					return resultMap;
				}
			} catch (Exception e) {
				logger.info("regType=2注册名义代理人的当前用户必须登录,msg="+e.getMessage());
				resultMap.setSuccess(false);
				resultMap.setMessage("注册名义代理人的当前用户必须登录");
				return resultMap;
			}
			
			if(!walletBankService.walletCanUseAgent(userId)){
				resultMap.setSuccess(false);
				resultMap.setMessage("注册失败：系统暂停名义代理人服务");
				return resultMap;
			}
		}
		
		//姓名验证
		if(null == microDTO.getMicro_name()){
			resultMap.setSuccess(false);
			resultMap.setMessage("请填写姓名");
			return resultMap;
		}
		
		microDTO.setMicro_name(microDTO.getMicro_name().trim());
		if(!microDTO.getMicro_name().matches("[\\u4e00-\\u9fa5]+")){
			resultMap.setSuccess(false);
			resultMap.setMessage("姓名为中文名称，不能包含字母数字等特殊字符");
			return resultMap;
		}
		
		
		//密码验证
		if(StringUtils.isBlank(microDTO.getPassword())){
			resultMap.setSuccess(false);
			resultMap.setMessage("请填写密码");
			return resultMap;
		}
		if(microDTO.getPassword().indexOf(" ") != -1){
			resultMap.setSuccess(false);
			resultMap.setMessage("密码不能包含空格");
			return resultMap;
		}
		if(!microDTO.getPassword().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$")){
			resultMap.setSuccess(false);
			resultMap.setMessage("密码校验不通过(6位以上的字母数字组合)");
			return resultMap;
		}
		
		//手机验证
		logger.info("进行手机验证码验证开始,vidate="+vidate+",tel="+microDTO.getTel());
		boolean checkSMSCode = sms.checkSMSCode(request, microDTO.getTel(), vidate);
		if(!checkSMSCode){
			resultMap.setSuccess(false);
			resultMap.setMessage("手机验证码不正确");
			logger.info("手机验证码不正确");
			return resultMap;
		}
		logger.info("手机验证码验证通过");
		logger.info("AddMicroController redirectUserXW.do ==> regType="+regType);
		String invitation = microDTO.getInvitation();
		
		/**
		 * 查询手机号是否被注册
		 */
		Integer selectAllMicro = 0;
		try {
			selectAllMicro = microService.selectAllMicro(microDTO.getTel());
		} catch (BusinessServiceException e) {
			logger.error("查询是否有注册电话失败", e);
		}
		if (selectAllMicro > 0) {
			resultMap.setSuccess(false);
			resultMap.setMessage("该手机号已注册");
			return resultMap;
		}

		Integer user = 0;
		try {
			user = microService.selectAllMicrobyUser(microDTO.getUser_id());
		} catch (BusinessServiceException e) {
			logger.error("查询时候有该用户名失败", e);
		}
		if (user > 0) {
			resultMap.setSuccess(false);
			resultMap.setMessage("该用户名已被占用");
			return resultMap;
		}
		if (StringUtils.isBlank(invitation)) {
			resultMap.setSuccess(false);
			resultMap.setMessage("邀请码为空");
			return resultMap;
		}
		
		/**
		 * 查询邀请是否存在
		 */
		MicroDTO parentmicro = new MicroDTO();
		try {
			parentmicro = microService.getMicroInvitation(invitation);
		} catch (BusinessServiceException e) {
			logger.error("查询小微失败", e);
		}
		if (parentmicro == null) {
			resultMap.setSuccess(false);
			resultMap.setMessage("邀请码有误");
			return resultMap;
		}
		
		
		
		/**
		 * 注册
		 */
		try {
			String pwd = new String(microDTO.getPassword());
			
			microService.addMicroInvitation(invitation, microDTO);
			if("2".equals(regType)){
				//添加名义代理人关联关系
				MicroAgentDTO microAgentDTO = new MicroAgentDTO();
				microAgentDTO.setCrtBy(userId);
				microAgentDTO.setUpdBy(userId);
				microAgentDTO.setMicroId(myMicroDTO.getMicro_id());
				microAgentDTO.setMicroIdAgent(microDTO.getMicro_id());
				microAgentDTO.setStatus(1);
				microAgentService.insertSelective(microAgentDTO);
			}
			resultMap.setSuccess(true);
			resultMap.setMessage("恭喜注册成功");
			
			//注册成功，自动登陆
			 Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(microDTO.getUser_id(), pwd,authorities);  
		        
		    token.setDetails(new WebAuthenticationDetails(request));  
		      
		    Authentication authenticatedUser = authenticationManager.authenticate(token);  
		   
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

			request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
			
		} catch (BusinessServiceException e) {
			logger.error(e.getMessage(), e);
			resultMap.setSuccess(false);
			resultMap.setMessage("注册失败,"+e.getMessage());
		}
		return resultMap;



	}

	/**
	 * 获取短信验证码
	 * 
	 * @param request
	 * @param response
	 * @param user_id
	 * @param vidate
	 * @return
	 * @throws SMSTBException
	 */
	@RequestMapping(value = "/sms.do")
	@ResponseBody
	public ResultMap sms(HttpServletRequest request, HttpServletResponse response, String tel) throws SMSTBException {
		ResultMap resultMap = new ResultMap();
		if (StringUtils.isNotBlank(tel)) {
			Pattern p = Pattern.compile("^(13|14|15|17|18)\\d{9}$");
			Matcher m = p.matcher(tel);
			if (m.matches()) {
				Integer selectAllMicro = 0;
				try {
					selectAllMicro = microService.selectAllMicro(tel);
				} catch (BusinessServiceException e) {
					logger.error(e.getMessage(), e);
				}
				if (selectAllMicro == 0) {
					resultMap.setSuccess(true);
					try {
						sms.sendSMSTB(request, tel, ConstantsUtill.SMS_TB_TEMPLATE_REGIST);
					} catch (SMSTBException e) {
						logger.error(e.getMessage());
						resultMap.setMessage(e.getMessage());
						resultMap.setSuccess(false);
					}

				} else {
					resultMap.setMessage("该手机号已注册");
					resultMap.setSuccess(false);
				}
				return resultMap;

			} else {
				resultMap.setMessage("请输入正确的手机号");
				resultMap.setSuccess(false);
				return resultMap;
			}
		} else {
			resultMap.setSuccess(false);
			return resultMap;
		}
	}

	@RequestMapping(value = "/userIdValidation.do")
	@ResponseBody
	public ResultMap userXWValidate(String tel) {
		ResultMap resultMap = new ResultMap();
		logger.info("AgencyController userXWValidate.do");

		Integer selectAllMicro = 0;
		try {
			selectAllMicro = microService.selectAllMicro(tel);
		} catch (BusinessServiceException e) {
			logger.error(e.getMessage(), e);
		}
		if (selectAllMicro == 0) {
			resultMap.setSuccess(true);
		} else {
			resultMap.setMessage("该手机号已注册");
			resultMap.setSuccess(false);
		}
		return resultMap;
	}

	/**
	 * 获取邀请码
	 * 
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/invite.do")
	public String invite(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info(" AddMicroController invite  requestURL:" + currentServerUrl);
		String url = currentServerUrl + "/bxtx/invite.do";
		WxJsapiSignatureDTO sign = weiXinService.createJsapiSignature(url);
		String jsapi_ticket = weiXinService.getJsapi_ticket();
		Map<String, String> config = new HashMap<String, String>();
		config.put("url", url);
		config.put("jsapi_ticket", jsapi_ticket);
		config.put("nonceStr", sign.getNoncestr());
		config.put("timestamp", sign.getTimestamp()+"");
		config.put("signature", sign.getSignature());
		
		/**
		 * 查询当前邀请用户数量
		 */
		UserDTO userDTO = new UserDTO();
		try {
			userDTO = microService.getUserIDyInvitation(this.getAuthenticatedUserId());
		} catch (BusinessServiceException e) {
			logger.error(e.getMessage(), e);
		}
		/**
		 * 查询当前用户的邀请码
		 */
		config.put("appId", WeiXinUtils.getAppId());
		Integer mynum = 0;
		try {
			mynum = microService.getMyfrend(userDTO.getInvitation());
		} catch (BusinessServiceException e) {
			logger.error(e.getMessage(), e);
		}
		model.addAttribute("userDTO", userDTO);
		model.addAttribute("mynum", mynum);
		model.addAttribute("config", config);
		model.addAttribute("url", currentServerUrl);
		model.addAttribute("ticket", gettTicket(request, userDTO.getInvitation(), response));
		logger.info("AddMicroController  invite  config:" + config.toString());
		return "user.invite";
	}
	
	public String gettTicket(HttpServletRequest request, String invitation, HttpServletResponse response) throws Exception{
		String ticket = "";
		String token = weiXinService.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
		String json = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\":\"yq" + invitation + "\"}}}";
		String ticketJson = HttpClientUtil.post2(url, json);
		JSONObject fromObject = JSONObject.fromObject(ticketJson);
		ticket = (String) fromObject.get("ticket");
		logger.info("得到ticket" + ticket);
		return ticket;
	}

	// 个人临时二维码生成
	@RequestMapping(value = "/towCode.do")
	public String towCode(Model model, HttpServletRequest request, String invitation, HttpServletResponse response) {
		logger.info("towCode  Star");
		try {
			
			model.addAttribute("ticket", gettTicket(request, invitation, response));
			logger.info("towCode  end");
			return "user.towCode";

		} catch (Exception e) {
			logger.error("发送失败" + e.getMessage());
			return null;
		}
	}
	
	

	/**
	 * 配置 TOKEN 配置成功 后 启动的话 菜单会失效 需要post请求配置菜单.
	 */
	private String TOKEN = "845C2550903CE6FA54CACDB82EAD4348";

	@RequestMapping(value = "/token.do")
	public String token(HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		SAXReader saxReader = new SAXReader();  
		Document document = saxReader.read(request.getInputStream());
		Element root = document.getRootElement();
		
		logger.info("token" + "微信消息推送" + root.asXML());
		
		String yq =  getElementText(document,"//xml/EventKey");;
		if (StringUtils.isNotBlank(yq) && "yq".equals(yq.substring(0, 2))) {
			
			String invitation = yq.substring(2);
			MicroDTO parentmicro = microService.getMicroInvitation(invitation);
			String openid = getElementText(document,"//xml/FromUserName");
			String url = currentServerUrl + "/bxtx/addMicro.do?invitation=" + invitation;
			String imageurl = currentServerUrl + "/bxtx/images/bxtx_03log.png";

			// 消息内容
			String messger1 = "{\"touser\":\"" + openid + "\",\"msgtype\":\"news\",\"news\":{\"articles\": [{\"title\":\"" + "您的好友邀请你加入保行天下" + "\",\"description\":\"" + "点击图片进入注册保行天下" + "\",\"url\":\"" + url + "\",\"picurl\":\"" + imageurl + "\"}]}}";
			String maeurl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + weiXinService.getAccessToken();
			logger.info("token" + "微信消息" + maeurl);

			if (parentmicro != null & StringUtils.isNotEmpty(parentmicro.getMicro_name())) {
				String messger2 = "{\"touser\":\"" + openid + "\",\"msgtype\":\"news\",\"news\":{\"articles\": [{\"title\":\"" + "您的好友" + parentmicro.getMicro_name() + "邀请你加入保行天下" + "\",\"description\":\"" + "点击图片进入注册保行天下:邀请号为" + invitation + "\",\"url\":\"" + url + "\",\"picurl\":\"" + imageurl + "\"}]}}";
				CacheUtil.addOpenIdIvi(openid, invitation);
				HttpClientUtil.post3(maeurl, messger2);
			} else {
				HttpClientUtil.post3(maeurl, messger1);
			}
		} else if (StringUtils.isNotBlank(yq) && "yq".equals(yq.substring(8, 10))) {
			String invitation = yq.substring(10);
			MicroDTO parentmicro = microService.getMicroInvitation(invitation);
			String openid = getElementText(document,"//xml/FromUserName");
			String url = currentServerUrl + "/bxtx/addMicro.do?invitation=" + invitation;
			String imageurl = currentServerUrl + "/bxtx/images/bxtx_03log.png";
			
			// 消息内容
			String messger1 = "{\"touser\":\"" + openid + "\",\"msgtype\":\"news\",\"news\":{\"articles\": [{\"title\":\"" + "您的好友邀请你加入保行天下" + "\",\"description\":\"" + "点击图片进入注册保行天下" + "\",\"url\":\"" + url + "\",\"picurl\":\"" + imageurl + "\"}]}}";
			String maeurl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + weiXinService.getAccessToken();
			logger.info("token" + "微信消息" + maeurl);

			if (parentmicro != null & StringUtils.isNotEmpty(parentmicro.getMicro_name())) {
				String messger2 = "{\"touser\":\"" + openid + "\",\"msgtype\":\"news\",\"news\":{\"articles\": [{\"title\":\"" + "您的好友" + parentmicro.getMicro_name() + "邀请你加入保行天下" + "\",\"description\":\"" + "点击图片进入注册保行天下:邀请号为" + invitation + "\",\"url\":\"" + url + "\",\"picurl\":\"" + imageurl + "\"}]}}";
				CacheUtil.addOpenIdIvi(openid, invitation);
				HttpClientUtil.post3(maeurl, messger2);
			} else {
				HttpClientUtil.post3(maeurl, messger1);
			}

		}else {
			logger.info("微信普通消息推送内容：" + root.asXML());

            String result = "";

            // 如果消息内容是"bxtx*",那么将就转发到红包用户存储处理, 否则就转客服
            Map<String, String> xmlMap = parseXml(root.asXML());
            if (MapUtils.isNotEmpty(xmlMap) && StringUtils.isNotBlank(MapUtils.getString(xmlMap, "Content"))
                    && MapUtils.getString(xmlMap, "Content").startsWith("bxtx")) {
                result = post(saveUserUrl + "saveUser.do", root.asXML());
            } else {
                // 客服
                result = post(kefuUrl + "token.do", root.asXML());
            }
			
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.println(result);
			pw.flush();
			pw.close();
		}

		return null;
	}

    /**
     * 解析xml
     * @param xmlStr
     * @return
     * @throws Exception
     */
    private static Map<String, String> parseXml(String xmlStr) throws Exception {
        if (StringUtils.isBlank(xmlStr)) {
            return null;
        }

        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        Document document = DocumentHelper.parseText(xmlStr);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());
        return map;
    }

	private String post(String url, String xml) throws IOException, SocketTimeoutException {
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
			os.write(xml.getBytes("utf-8"));
			os.close();

			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				return toText(in, "utf-8");
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
	 * 根据path获取document的单一的值
	 * @param document
	 * @param path
	 * @return
	 */
	private String getElementText(Document document,String path){
		if(document.selectSingleNode(path) == null){
			return "";
		}
		return document.selectSingleNode(path).getText();
	}

}
