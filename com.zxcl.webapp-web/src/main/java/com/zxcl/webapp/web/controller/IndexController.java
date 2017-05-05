/**
 * 
 */
package com.zxcl.webapp.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alibaba.fastjson.JSONObject;
import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WalletCoreService;
import com.zxcl.webapp.biz.service.impl.MicroServiceImpl;
import com.zxcl.webapp.biz.service.openapi.OpenApiManageService;
import com.zxcl.webapp.biz.util.Encoding;
import com.zxcl.webapp.biz.util.resultEntity.ResultMap;
import com.zxcl.webapp.dto.LoginLoggingDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.dto.openapi.ApiAppDTO;
import com.zxcl.webapp.integration.dao.AgencyDAO;
import com.zxcl.webapp.integration.dao.LoginLoggingDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.integration.dao.UserDAO;
import com.zxcl.webapp.web.util.SignUtil;

/**
 * @author zxj
 * @date 2016年9月1日
 * @description 
 */
@Controller
@SessionAttributes(WebAttributes.AUTHENTICATION_EXCEPTION)
public class IndexController extends BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private OpenApiManageService openApiManageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private AgencyDAO agencyDAO;
	
	@Autowired
	private MicroDAO microDao;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private WalletCoreService walletCoreService;
	
	@Autowired
    private LoginLoggingDAO logLoginDAO;
	
	/**
	 * 记录第三方接口用户登录日志
	 * @param app
	 * @param userId
	 */
	private void saveAutoLoginLog(String remoteAddrIp, String localAddrIp,  ApiAppDTO app, String userId){
		try {
			LoginLoggingDTO loginLog = new LoginLoggingDTO();
			if(null != app){
				loginLog.setAppId(app.getAppId());
				loginLog.setAppName(app.getAppName() != null && app.getAppName().length() > 45?app.getAppName().substring(0, 45):app.getAppName());
			}
			loginLog.setRemoteIPaddress(remoteAddrIp);
			loginLog.setServerIPaddress(localAddrIp);
			loginLog.setUserId(userId);
			loginLog.setLoginDateTime(new java.util.Date());
			loginLog.setResult("1");
			loginLog.setReason("第三方接口用户登录成功");
			logger.info("第三方接口用户登录日志记录:"+JSONObject.toJSONString(loginLog));
			logLoginDAO.insertLogLogin(loginLog);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @param user_id
	 * @param invitation
	 * @return
	 */
	@RequestMapping(value = "/logon")
	public String initLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model,String user_id, String invitation) {
		HttpSession session = request.getSession();
		Object openId = session.getAttribute("ropenId");
		logger.debug("openId="+openId);
		model.addAttribute("openId",null == openId?"":openId.toString());
		model.addAttribute("user_id",user_id);
		if(StringUtils.isBlank(invitation)){
			//如果参数中没有查看缓存中是否存在
			if(null != session.getAttribute("rinvitation") && StringUtils.isNotBlank(session.getAttribute("rinvitation").toString())){
				invitation = session.getAttribute("rinvitation").toString();
			}
		}
		model.addAttribute("invitation",invitation);
		return "logon";
	}
	
	
	/**
	 * 第三方登录或注册
	 * @param request
	 * @param response
	 * @param model
	 * @param user_id
	 * @param invitation
	 * @return
	 */
	private String valueOf(String str){
		return null == str ? null : str.toString();
	}
	
	private String objValueOf(Object obj){
		if(null == obj){
			return null;
		}
		if(obj instanceof String){
			return (String)obj;
		}
		if(obj instanceof String[]){
			return ((String[])obj)[0];
		}
		return null;
	}
	
	@RequestMapping(value = "/openapi/login/regist_and_login.do")
	public void openapiLoginRegistAndLogin(HttpServletRequest request, HttpServletResponse response) {
		apiLoginRegistAndLogin(request, response);
	}
	
	@RequestMapping(value = "/api/login/regist_and_login.do")
	public void apiLoginRegistAndLogin(HttpServletRequest request, HttpServletResponse response) {
		boolean checkFlag = true;
		String checkMsg = "";
		
		Map<?, ?> requestParamMap = request.getParameterMap();
		if(requestParamMap == null || requestParamMap.isEmpty()){
			checkFlag = false;
			checkMsg = "参数必传";
		}
		
		HashMap<String, String> signParams = new HashMap<String, String>();
		if(checkFlag){
			for(Map.Entry<?, ?> item : requestParamMap.entrySet()){
				signParams.put(objValueOf(item.getKey()), objValueOf(item.getValue()));
			}
			logger.debug("signParams="+signParams);
		}
		
		/**
		 * 签名验证
		 */
		final String userName = valueOf(request.getParameter("user_name"));
		final String userPhone = valueOf(request.getParameter("user_phone"));
		final String userTeamId = valueOf(request.getParameter("user_team_id"));
		final String appId = valueOf(request.getParameter("appId"));
		final String sign = valueOf(request.getParameter("sign"));
		final String invitedUserPhone = valueOf(request.getParameter("invited_by_user_phone"));//推荐用户手机号
		final String invitedUserName = valueOf(request.getParameter("invited_by_user_name"));//推荐用户姓名
		
		
		if(null == appId || null == sign || null == userPhone){
			checkFlag = false;
			checkMsg = "[appId, sign, user_phone]参数必传";
		}
		
		ApiAppDTO app = null;
		if(checkFlag){
			app = openApiManageService.findApp(appId);
			logger.info("app:"+JSONObject.toJSONString(app));
			if(app == null || !"1".equals(app.getStatus())){
				checkFlag = false;
				checkMsg = "appId["+appId+"]不合法";
			}
		}
//		if(checkFlag){
//			if(!userPhone.matches("^(13|14|15|17|18)\\d{9}$")){
//				checkFlag = false;
//				checkMsg = "手机号["+userPhone+"]不符合规范";
//			}
//		}
		if(checkFlag){
			String signResult = SignUtil.makeSign(signParams, app.getAppKey());
			logger.info("我方签名结果["+signResult+"],第三方签名["+sign+"]");
			if(signResult.equals(sign)){
				checkFlag = true;
				checkMsg = "验签通过";
			}
			else{
				checkFlag = false;
				checkMsg = "签名验证未通过";
			}
		}
		
		logger.info("验签结果: checkFlag["+checkFlag+"],checkMsg["+checkMsg+"]");
		if(!checkFlag){
			returnMsg(response, checkMsg, null);
			return;
		}
		
		/**
		 * 此处增加推荐人流程
		 * 存在推荐人取出推荐人的邀请码
		 * 不存在则自动注册推荐人
		 */
		String invitedCode = null;
		if(StringUtils.isNotBlank(invitedUserPhone)){
			logger.info("参数存在推荐人["+invitedUserName+"]"+invitedUserPhone);
			try {
				UserDTO invitedUserDTO = userService.queryUserByUserId(invitedUserPhone);
				if(null != invitedUserDTO){
					invitedCode = invitedUserDTO.getInvitation();
					logger.info("推荐人存在,邀请码:"+invitedCode);
					
					//是否更新团队id
					String changeTeamIdResult = changeTeamId(app, invitedUserPhone, appId, response, userTeamId, "推荐人"+invitedUserPhone);
					if(!"SUCCESS".equals(changeTeamIdResult)){
						return;
					}
				}
				else{
					logger.info("推荐人不存在,进行注册流程");
					invitedCode = autoRegist(app, request, invitedUserPhone, appId, invitedUserName, userTeamId, response, null, false, "推荐人自动注册");
					if(StringUtils.isBlank(invitedCode)){
						return;
					}
					logger.info("推荐人自动注册成功,邀请码:"+invitedCode);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				returnMsg(response, "查询推荐人失败或推荐人自动注册失败", null);
				return;
			}
		}
		
		
		/**
		 * 业务逻辑,自动登录或注册
		 * 1)系统找到userPhone的用户则自动登录, SESSION中写入 appId 标识, userTeamId不一致时:用户对应的micro_info的团队号修改为新的团队号，
		 *  更新t_user.employee_no , t_micro_info.AGT_TEAM_ID 字段
		 * 2)系统不存在userPhone,自动注册,邀请码和密码随机生成
		 */
		try {
			UserDTO userDTO = userService.queryUserByUserId(userPhone);
			if(null == userDTO){
				logger.info("["+userPhone+"]不存在,自动注册");
				autoRegist(app, request, userPhone, appId, userName, userTeamId, response, invitedCode, true, "用户自动注册");
				return ;
			}
			else{
				logger.info("["+userPhone+"]自动登录");
				autoLogon(app, request, userPhone, appId, response, userTeamId, invitedCode);
				return;
			}
		} catch (Exception e) {
			logger.error("第三方登录或注册业务逻辑执行失败", e);
			returnMsg(response, null, request.getContextPath()+"/logon.do");
			return;
		}
	}


	protected void returnMsg(HttpServletResponse response, String msg, String locationUrl){
		response.setContentType("text/html;charset=utf-8");
		try {
			if(StringUtils.isNotBlank(msg)){
				logger.info("msg:"+msg);
				PrintWriter pw = response.getWriter();
				pw.write("<h1><center>"+msg+"</center></h1>");
			}
			if(StringUtils.isNotBlank(locationUrl)){
				logger.info("locationUrl:"+locationUrl);
				response.sendRedirect(locationUrl);
			}
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * appId和团队ID对应关系校验
	 * @param appId
	 * @param teamId
	 * @return
	 */
	private boolean checkAppIdTeam(ApiAppDTO app, String teamId){
		logger.info("appId["+app.getAppId()+"]和团队ID["+teamId+"]对应关系校验");
		boolean flag = false;
		if(StringUtils.isBlank(app.getCompCode())){
			logger.info(app.getAppId()+"未设置代理代码");
			return false;
		}
		int count = agencyDAO.findCountCompCodeWithTeamId(app.getCompCode(), teamId);
		if(count > 0){
			flag = true;
		}
		logger.info("flag="+flag);
		return flag;
	}
	
	/**
	 * 团队号映射
	 * @param userTeamId
	 * @param compCode
	 * @return
	 */
	private String mappingTeamId(String userTeamId, String compCode){
		return agencyDAO.findTeamIdWithCompCodeAndTeamIdMapping(compCode, userTeamId);
	}
	
	/**
	 * 自动注册
	 * @param request
	 * @param userPhone
	 * @param appId
	 * @param userName
	 * @param userTeamId
	 * @param response
	 * @param relevance
	 * @param isAutoLogon
	 * @param logPre
	 * @return
	 * @throws Exception
	 */
	protected synchronized String autoRegist(ApiAppDTO app, HttpServletRequest request, final String userPhone, final String appId, final String userName, String userTeamId, HttpServletResponse response, String relevance, boolean isAutoLogon, String logPre) throws Exception{
		if(StringUtils.isBlank(userTeamId)){
			returnMsg(response, logPre+": 团队ID必传", null);
			return null;
		}
		if(StringUtils.isBlank(userName)){
			returnMsg(response, logPre+": 真实姓名必传", null);
			return null;
		}
		
		//如果第三方的团队需要映射,则userTeamId不要映射为保行天下的团队id
		if("1".equals(app.getTeamMappingSign())){
			if(StringUtils.isBlank(app.getCompCode())){
				returnMsg(response, logPre+":appId["+app.getAppId()+"]的comp_code未配置", null);
				return null;
			}
			String mappingUserTeamId = mappingTeamId(userTeamId, app.getCompCode());
			if(StringUtils.isBlank(mappingUserTeamId)){
				returnMsg(response, logPre+":appId["+app.getAppId()+"]配置为团队映射,未查询到第三方团队["+userTeamId+"]和保行天下团队的对应关系", null);
				return null;
			}
			userTeamId = mappingUserTeamId;
			logger.info("映射后的团队号为" + userTeamId);
		}
		HashMap<String, String> agtTeamMap = agencyDAO.getAgtTeamByTeamId(userTeamId);
		if(null == agtTeamMap){
			returnMsg(response, logPre+":团队["+userTeamId+"]不合法", null);
			return null;
		}
		if(!checkAppIdTeam(app, userTeamId)){
			returnMsg(response, logPre+":appId["+appId+"]和团队["+userTeamId+"]未查询到关联信息", null);
			return null;
		}
		Integer selectAllMicro = microService.selectAllMicro(userPhone);
		if(selectAllMicro > 0){
			returnMsg(response, logPre+":手机号["+userPhone+"]已被注册", null);
			return null;
		}
		Integer userCount = microService.selectAllMicrobyUser(userName);
		if(userCount > 0){
			returnMsg(response, logPre+":用户ID["+userPhone+"]已被占用", null);
			return null;
		}
		
		Integer maxinvitation = userDAO.getMaxinvitation();
		String invitation = Integer.toString(maxinvitation + 3);
		String encode = Encoding.encode(invitation);
		
		MicroDTO microDTO = new MicroDTO();
		microDTO.setUser_id(userPhone);
		microDTO.setMicro_name(userName);
		microDTO.setPassword(encode);
		microDTO.setMicro_id(null);
		microDTO.setInvitation(invitation);
		microDTO.setRelevance(relevance);
		microDTO.setCrt_cde(userPhone);
		microDTO.setUpd_cde(userPhone);
		if(null != userPhone && userPhone.matches("^(13|14|15|17|18)\\d{9}$")){
			microDTO.setTel(userPhone);
		}
		microDTO.setAgent_id(agtTeamMap.get("AGT_ID"));
		microDTO.setAgt_team_id(userTeamId);
		microDTO.setStatus(1);
		microDTO.setCrtTime(new Date());
		microDTO.setUpdTime(microDTO.getCrtTime());
		microDTO.setMicro_class("0");
		
		logger.info("新增用户");
		userDAO.insertUser(microDTO);
		
		logger.info("新增小微账号");
		microDao.insertMicroDTO(microDTO);
		
//		logger.info("初始化小微钱包");
//		try {
//			walletCoreService.getWalletByUserId(microDTO.getUser_id());
//		} catch (Exception e) {
//			logger.error("初始化小微钱包失败,用户"+microDTO.getUser_id(), e);
//		}
		
		if(isAutoLogon){
			autoLogon(app, request, userPhone, appId, response, null, null);
		}
		
		return invitation;
	}
	
	/**
	 * 更换团队
	 * @param userPhone
	 * @param appId
	 * @param response
	 * @param userTeamId
	 * @param logPre
	 * @return
	 * @throws Exception
	 */
	protected String changeTeamId(ApiAppDTO app, final String userPhone, final String appId, HttpServletResponse response ,String userTeamId, String logPre) throws Exception{
		if(StringUtils.isNotBlank(userTeamId)){
			MicroDTO microDTO = microService.getMicroByUserId(userPhone);
			if(null == microDTO){
				returnMsg(response, logPre+"更换团队失败: 用户["+userPhone+"]未查询到小微信息", null);
				return "FAILED";
			}
			//如果第三方的团队需要映射,则userTeamId不要映射为保行天下的团队id
			if("1".equals(app.getTeamMappingSign())){
				if(StringUtils.isBlank(app.getCompCode())){
					returnMsg(response, logPre+":appId["+app.getAppId()+"]的comp_code未配置", null);
					return "FAILED";
				}
				String mappingUserTeamId = mappingTeamId(userTeamId, app.getCompCode());
				if(StringUtils.isBlank(mappingUserTeamId)){
					returnMsg(response, logPre+":appId["+app.getAppId()+"]配置为团队映射,未查询到第三方团队["+userTeamId+"]和保行天下团队的对应关系", null);
					return "FAILED";
				}
				userTeamId = mappingUserTeamId;
				logger.info("映射后的团队号为" + userTeamId);
			}
			if(!userTeamId.equals(microDTO.getAgt_team_id())){
				logger.info("当前团队["+microDTO.getAgt_team_id()+"],需更新为["+userTeamId+"]");
				if(!checkAppIdTeam(app, userTeamId)){
					returnMsg(response, logPre+"更换团队失败: appId["+appId+"]和团队["+userTeamId+"]未查询到关联信息", null);
					return "FAILED";
				}
				HashMap<String, String> agtTeamMap = agencyDAO.getAgtTeamByTeamId(userTeamId);
				logger.info("agtTeamMap=["+agtTeamMap+"]");
				if(null == agtTeamMap){
					returnMsg(response, logPre+"更换团队失败: 团队["+userTeamId+"]不存在", null);
					return "FAILED";
				}
				
				//更换团队
				String changeTeamId = userTeamId;//团队ID
				String changeAgtId = agtTeamMap.get("AGT_ID");//代理机构ID
				if(!changeAgtId.equals(microDTO.getAgency().getAgent_id())){
					returnMsg(response, logPre+"更换团队失败: 团队["+userTeamId+"]所属代理与当前用户所属代理不一致", null);
					return "FAILED";
				}
				
				int c = microDao.updateTeamIdByUserId(changeTeamId, userPhone);
				logger.info("更新小微表,影响行数"+c);
			}
		}
		return "SUCCESS";
	}
	
	/**
	 * 更新关系码
	 * @param userPhone
	 * @param invitedCode
	 */
	protected void updateRelevanceByUserId(final String userPhone , final String invitedCode){
		if(StringUtils.isNotBlank(invitedCode)){
			try {
				
				UserDTO userDTO = userService.queryUserByUserId(userPhone);
				if(null != userDTO && StringUtils.isBlank(userDTO.getRelevance())){//关系码为空的时候才更新
					logger.info("更新关系码"+invitedCode);
					int c = userDAO.updateRelevanceByUserId(invitedCode, userPhone);
					logger.info("影响行数"+c);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 自动登录
	 * @param request
	 * @param userPhone
	 * @param appId
	 * @param response
	 * @param userTeamId
	 * @throws Exception
	 */
	protected void autoLogon(ApiAppDTO app, HttpServletRequest request, final String userPhone, final String appId, HttpServletResponse response ,final String userTeamId, final String invitedCode) throws Exception{
		
		
		//更换团队
		String changeItemIdResult = changeTeamId(app, userPhone, appId, response, userTeamId, "用户"+userPhone);
		if(!"SUCCESS".equals(changeItemIdResult)){
			return;
		}
		
		//更新关系码
		updateRelevanceByUserId(userPhone, invitedCode);
		
		//自动登录前校验
		try {
			boolean result = microService.microIsAllowLogon(userPhone, null);
			if (!result) {
				returnMsg(response, MicroServiceImpl.tl.get(), null);
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally{
			MicroServiceImpl.tl.remove();
		}
		
		//登录日志记录
		saveAutoLoginLog(request.getRemoteAddr(), request.getLocalAddr(), app, userPhone);
		
		//自动登录
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userPhone, "123456", authorities);  
	    token.setDetails(new WebAuthenticationDetails(request));  
	    UserDetails userDetails = retrieveUser(userPhone, token);
	    Authentication authenticatedUser = createSuccessAuthentication(userPhone, userDetails, token, userDetails);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		request.getSession().setAttribute("USER_APPID", appId);
		
		logger.info("SESSION_ID:"+request.getSession().getId());
		returnMsg(response, null, request.getContextPath()+"/index.do");
	}
	
	protected Authentication createSuccessAuthentication(String weChatId, Object principal,
			Authentication authentication, UserDetails user) {
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
				principal, authentication.getCredentials(),
				user.getAuthorities());
		result.setDetails(authentication.getDetails());
		return result;
	}
	
	@RequestMapping(value = "/outlogin.do", method=RequestMethod.POST)
	@ResponseBody
	public ResultMap outlogin(HttpServletRequest request, HttpServletResponse response) {
		ResultMap resultMap = new ResultMap();
		try {
			
			Object compCode = request.getSession().getAttribute("comp_code");
//			request.getSession().invalidate();
			request.getSession().removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
			SecurityContextHolder.getContext().setAuthentication(null);
			if(null != compCode){
				request.getSession().setAttribute("comp_code", compCode);
			}
			resultMap.setSuccess(true);
			return resultMap;
		} catch (Exception e) {
			logger.error("注销失败");
			resultMap.setSuccess(false);
			return resultMap;
		}
	
	}
	
	
	protected final UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		return new UserDetails() {
			
			private Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isEnabled() {
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				return false;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return false;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return false;
			}
			
			@Override
			public String getUsername() {
				return username;
			}
			
			@Override
			public String getPassword() {
				return String.valueOf(authentication.getCredentials());
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				if(CollectionUtils.isEmpty(auths)){
					auths.add(new GrantedAuthority() {
						private static final long serialVersionUID = 1L;
						@Override
						public String getAuthority() {
							return "ROLE_USER";
						}
					});
				}
				return auths;
			}
		};
	}
}
