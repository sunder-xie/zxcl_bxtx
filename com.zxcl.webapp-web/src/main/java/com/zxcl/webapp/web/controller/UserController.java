package com.zxcl.webapp.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.meyacom.fw.app.dto.PagingResult;
import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.BankService;
import com.zxcl.webapp.biz.service.IdentityInfoService;
import com.zxcl.webapp.biz.service.MessageService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WalletCoreService;
import com.zxcl.webapp.biz.service.WeChatService;
import com.zxcl.webapp.biz.service.impl.MicroServiceImpl;
import com.zxcl.webapp.biz.util.ConstantsUtill;
import com.zxcl.webapp.biz.util.Encoding;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.ResultMap;
import com.zxcl.webapp.dto.AgentServiceControlDTO;
import com.zxcl.webapp.dto.BankDTO;
import com.zxcl.webapp.dto.MicroApproveDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.ReportDTO;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.integration.dao.AgentServiceControlDAO;
import com.zxcl.webapp.integration.dao.ApiAppControlDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.integration.dao.PolicyBaseDAO;
import com.zxcl.webapp.util.WeiXinUtils;
import com.zxcl.webapp.web.util.SMSTBUtils;
import com.zxcl.webapp.web.vo.AjaxResult;

@Controller
@SessionAttributes(WebAttributes.AUTHENTICATION_EXCEPTION)
public class UserController extends BaseController {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private MicroService microService;
    
    @Autowired
    private WeChatService weChatService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private BankService bankService;

    @Autowired
	private WalletCoreService walletCoreService;
    
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private MicroDAO microDAO;
    
    @Autowired
    private PolicyBaseDAO policyBaseDAO;
    
    @Autowired
    private ApiAppControlDAO apiAppControlDAO;
    
    @Value("${share.url}")
    private String currentServerUrl;
    
    @Value("${mp.weixin.bind.url}")
    private String bindUrl;
    
    @Autowired
    private AgentServiceControlDAO agentServiceControlDAO;
    
    /**
     * 图片验证码
     * @param request
     * @param response
     * @param microDTO
     */
    @RequestMapping(value = "/call/random_code.do")
    public void randomCode(HttpServletRequest request, HttpServletResponse response) {
    	String code = SMSTBUtils.randomCode(4);
    	logger.info("图片验证码：" + code);
    	request.getSession().setAttribute(SMSTBUtils.USERRANDOMCODE, code);
		try {
			ImageIO.write(getImageBuffer(code), "JPEG", response.getOutputStream());
		} catch (IOException e) {
			logger.error("发送图片验证码失败", e);
		}
    }
    
//    @RequestMapping(value = "/checkUserAuth.do")
//    @ResponseBody
//    public String checkUserAuth(HttpServletRequest request, HttpServletResponse response) {
//    	logger.info("UserController checkUserAuth.do");
//    	try {
//    		if(microService.microIsAllowLogon(this.getAuthenticatedUserId(), null)){
//    			return "1";
//    		}
//    		else{
//    			request.getSession().invalidate();
//    			request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new Exception(MicroServiceImpl.tl.get()));
//    			return "0";
//    		}
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		} finally{
//			MicroServiceImpl.tl.remove();
//		}
//    	return "0";
//    }
    
    @RequestMapping(value = "/call/to_upload.do")
    public String toUpload(HttpServletRequest request, HttpServletResponse response) {
    	return "user.upload";
    }
    private static Random r = new Random();
	private static BufferedImage getImageBuffer(String code){
		BufferedImage bi = new BufferedImage(80, 24, BufferedImage.TYPE_INT_BGR);
		Graphics g = bi.getGraphics();
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, 80, 24);
		
		for (int i = 0; i < code.length(); i++) {
			g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));
			g.setFont(new Font("Arial", Font.BOLD , 18));
			g.drawString(code.charAt(i)+"" , (i * 15) + 3, 18);
		}
		return bi;
	}
    /**
     * 微信回调
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/bind.do")
    public String bind(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        
        String userId = null;
        String openid = null;
        try {
        	openid = (String)request.getAttribute("openid");
			userId = this.getAuthenticatedUserId();
			if(StringUtils.isBlank(userId) || StringUtils.isBlank(openid)){
				response.sendRedirect(request.getContextPath() + "/index.do?nocheck=1");
				return null;
			}
		} catch (Exception e) {
			logger.error("微信绑定回调失败", e);
			try {
				response.sendRedirect(request.getContextPath() + "/index.do?nocheck=1");
			} catch (IOException e1) {
			}
			return null;
		}
        try {
            UserDTO userDTO = weChatService.selectUserByWeChatid(openid);
            if (null == userDTO || StringUtils.isBlank(userDTO.getWechartId())) {
                weChatService.updateWeChatId(openid, null, userId);
                logger.info("当前用户:" + userId + "绑定微信成功:已绑定微信:" + openid);
            } else {
            	if(userDTO.getUserId().equals(userId) && userDTO.getWechartId().equals(openid)){
            		logger.info("当前用户:" + userId + "已绑定微信:" + openid);
            	}
            	else{
	                logger.info("用户:" + userDTO.getUserId() + "已绑定微信:" + userDTO.getWechartId()+",自动解绑开始");
	                weChatService.deleteWeChatId(userDTO.getUserId());
	                logger.info("用户:" + userDTO.getUserId() + ",自动解绑成功openid="+userDTO.getWechartId()+",自动绑定openid="+openid+"开始");
	                weChatService.updateWeChatId(openid, null, userId);
	                logger.info("当前用户:" + userId + "绑定微信成功:已绑定微信:" + openid);
            	}
            }
            response.sendRedirect(request.getContextPath() + "/index.do?nocheck=1");
        } catch (Exception e1) {
            logger.error("用户:" + userId + "绑定微信失败:",e1);
        }
        return null;
    }

    /**
     * 修改用户信息
     * 
     * @return
     */
    @RequestMapping(value = "/editPersonal.do")
    public String editPersonal(HttpServletRequest request, HttpServletResponse response, MicroDTO microDTO) {
        logger.info("microDTO:" + microDTO);
        String userId = this.getAuthenticatedUserId();
        microDTO.setUpdTime(new Date());
        microDTO.setUpdCode(userId);
        try {
            UserDTO user = new UserDTO();
            user.setUserId(userId);
            microDTO.setUser(user);
            microService.update(microDTO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "redirect:/personal.do";
    }

    /**
     * 修改密码
     * 
     * @param request
     * @param response
     * @param password
     * @return
     */
    @RequestMapping(value = "/editPwd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultMap editPwd(HttpServletRequest request, HttpServletResponse response, String oldpwd, String pwd) {
        ResultMap resultMap = new ResultMap();
        try {
            microService.editPwd(this.getAuthenticatedUserId(), oldpwd, pwd);
            resultMap.setSuccess(true);
            resultMap.setMessage("操作成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultMap.setSuccess(false);
            resultMap.setMessage(e.getMessage());
        }
        return resultMap;
    }

    /**
     * 验证原密码
     * 
     * @param request
     * @param response
     * @param oldpwd
     * @return
     */
    @RequestMapping(value = "/editPwd2.do", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult editPwd2(HttpServletRequest request, HttpServletResponse response, String oldpwd) {
        if (StringUtils.isBlank(oldpwd)) {
            return new AjaxResult(false, "原密码不能为空");
        }
        UserDTO user = new UserDTO();
        try {
            user = userService.queryUserByUserId(this.getAuthenticatedUserId());
        } catch (BusinessServiceException e) {
            logger.error("查询用户失败", e);
        }
        if (!Encoding.matches(oldpwd, user.getLoginPassword())) {
            return new AjaxResult(false, "原密码输入错误");
        } else {
            return new AjaxResult(true);
        }
    }

    @RequestMapping("/myself.do")
    public String myself(HttpServletRequest request, HttpServletResponse response, Model model){
    	
    	if(StringUtils.isEmpty(this.getAuthenticatedUserId())){
    		return "redirect:/index.do";
    	}
    	
    	try {
    		model.addAttribute("unreadMessageCount", messageService.getUnreadMessageCount(getAuthenticatedUserId()));
    		
    		model.addAttribute("microDTO", microService.getMicroByUserId(getAuthenticatedUserId()));
    		model.addAttribute("wallet", walletCoreService.getWalletByUserId(this.getAuthenticatedUserId()));
    		Object appId = request.getSession().getAttribute("USER_APPID");
    		if(appId != null && StringUtils.isNotBlank(appId.toString())){
    			model.addAttribute("INVITE", apiAppControlDAO.selectByAppId(appId.toString(), "INVITE") != null ? "1":"0");
    		}
    		
    		MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
			AgentServiceControlDTO agentServiceControlDTO = agentServiceControlDAO.selectByAgentIdAndServiceType(microDTO.getAgency().getAgent_id(), 5);
			model.addAttribute("agentServiceControlDTO", agentServiceControlDTO);
    		
		} catch (Exception e) {
			logger.error("加载用户信息失败", e);
			return "redirect:/index.do";
		}
    		
    	return "user.myself";
    }
    
    
    /**
     * 我的团队
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/myTeam.do")
    public String myTeam(HttpServletRequest request, HttpServletResponse response, Model model) {
        
        try {
    		MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
    		if(!"0".equals(microDTO.getDuty())){
    			logger.info(this.getAuthenticatedUserId()+"非团队管理员，无权限");
    			response.setContentType("text/html; charset=utf-8");
    			response.getWriter().write("非团队管理员，无权限浏览");
    			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    			return null;
    		}
    		model.addAttribute("userId", this.getAuthenticatedUserId());
    		model.addAttribute("baseURL", currentServerUrl);
    		model.addAttribute("microDTO", microDTO);
		} catch (Exception e) {
			logger.info("我的团队", e);
			return "redirect:/index.do";
		}
        return "user.myTeam";
    }
    
    /**
     * 团员信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/myTeamPerson/{userId}.do")
    public String myTeamPerson(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String userId) {
        
        try {
        	
        	//当前用户小微信息
    		MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
    		if(!"0".equals(microDTO.getDuty())){
    			logger.info(this.getAuthenticatedUserId()+"非团队管理员，无权限");
    			response.setContentType("text/html; charset=utf-8");
    			response.getWriter().write("非团队管理员，无权限浏览");
    			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    			return null;
    		}
    		
    		//团员小微信息
    		microDTO = microService.getMicroByUserId(userId);
    		if(null != microDTO && StringUtils.isNotBlank(microDTO.getTel())){
    			microDTO.setTel(microDTO.getTel().substring(0,3)+"****"+microDTO.getTel().substring(7,11));
    		}
    		model.addAttribute("microDTO", microDTO);
		} catch (Exception e) {
			logger.info("我的团队", e);
			return "redirect:/index.do";
		}
        return "user.myTeamPerson";
    }
    
    @RequestMapping(value = "/myTeamList.do", method=RequestMethod.GET)
    public String myTeamList(HttpServletRequest request, HttpServletResponse response) {
        logger.info("UserController myTeamList.do");
        return "user.myTeamList";
    }
    
    /**
     * 团队列表
     * @param request
     * @param response
     * @param model
     * @param currentPage
     * @return
     */
    @RequestMapping(value = "/myTeamList.do", method=RequestMethod.POST)
    @ResponseBody
    public AjaxResult myTeamList(HttpServletRequest request, HttpServletResponse response, Model model, Integer currentPage, Integer pageSize) {
        AjaxResult r = new AjaxResult();
        currentPage = null == currentPage || currentPage <= 0?1:currentPage;
        try {
        	
        	
        	MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
    		if(!"0".equals(microDTO.getDuty())){
    			r.setSucc(false);
    			r.setMsg("非团队管理员，无权限浏览");
    			return r;
    		}
    		
    		PageParam p = new PageParam();
    		p.setCurrentPage(currentPage);
    		p.setPageSize(pageSize);
    		p.setWd(microDTO.getAgt_team_id());
    		
        	r.setData(microService.findTeamPeopleByPage(p));
        	r.setSucc(true);
			r.setMsg("查询成功");
		} catch (Exception e) {
			logger.error("获取团队列表", e);
			r.setSucc(false);
			r.setMsg("查询失败:"+e.getMessage());
		}
        return r;
    }
    
    /**
     * 团队总数
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/myTeamAllCount.do")
    @ResponseBody
    public AjaxResult myTeamAllCount(HttpServletRequest request, HttpServletResponse response) {
        AjaxResult r = new AjaxResult();
        try {
        	
        	MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
    		if(!"0".equals(microDTO.getDuty())){
    			r.setSucc(false);
    			r.setMsg("非团队管理员，无权限浏览");
    			return r;
    		}
    		
        	r.setData(microDAO.findTeamPeopleCount(microDTO.getAgt_team_id()));
        	r.setSucc(true);
			r.setMsg("查询成功");
		} catch (Exception e) {
			logger.error("获取团队列表", e);
			r.setSucc(false);
			r.setMsg("查询失败:"+e.getMessage());
		}
        return r;
    }
    
    /**
     * 团员邀请总人数
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/myTeamPersonAllCount.do")
    @ResponseBody
    public AjaxResult myTeamPersonAllCount(HttpServletRequest request, HttpServletResponse response, String userId) {
        AjaxResult r = new AjaxResult();
        int c = 0;
        try {
        	
        	MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
    		if(!"0".equals(microDTO.getDuty())){
    			r.setSucc(false);
    			r.setMsg("非团队管理员，无权限浏览");
    			return r;
    		}
    		
    		UserDTO userDTO = microService.getUserIDyInvitation(userId);
    		c = microService.getMyfrend(userDTO.getInvitation());
        	r.setData(c);
        	r.setSucc(true);
			r.setMsg("查询成功");
		} catch (Exception e) {
			logger.error("获取团队列表", e);
			r.setSucc(false);
			r.setMsg("查询失败:"+e.getMessage());
		}
        return r;
    }
    
    /**
     * 查询保费
     * @param request
     * @param response
     * @param type 1:总保费 2:月保费 3:日保费
     * @return
     */
    @RequestMapping(value = "/myTeamPolicyFee.do")
    @ResponseBody
    public AjaxResult myTeamPolicyFee(HttpServletRequest request, HttpServletResponse response, String type, String userId) {
        AjaxResult r = new AjaxResult();
        if(type == null){
        	r.setSucc(false);
			r.setMsg("参数类型必传");
			return r;
        }
        try {
        	
        	MicroDTO microDTO = microService.getMicroByUserId(this.getAuthenticatedUserId());
    		if(!"0".equals(microDTO.getDuty())){
    			r.setSucc(false);
    			r.setMsg("非团队管理员，无权限浏览");
    			return r;
    		}
    		String teamId = microDTO.getAgt_team_id();
//    		String startTm = "";
//    		
//    		Calendar c = Calendar.getInstance();
//    		c.setTime(new Date());
//    		int year = c.get(Calendar.YEAR);
//    		int month = c.get(Calendar.MONTH)+1;
//    		int day = c.get(Calendar.DAY_OF_MONTH);
//    		if(type == 1){
//    			
//    		}
//    		else if(type == 2){
//    			startTm += year + "-" + (month<10?"0"+month:month) + "-" + "01" + " 00:00:00";
//    		}
//    		else if(type == 3){
//    			startTm += year + "-" + (month<10?"0"+month:month) + "-" + (day<10?"0"+day:day) + " 00:00:00";
//    		}
//    		else{
//    			r.setSucc(false);
//    			r.setMsg("传入类型错误");
//    			return r;
//    		}
    		
    		//1总保费
    		//2月爆粉
    		//3日保费
    		String fee = policyBaseDAO.teamPolicyFee(teamId, type, userId);
        	r.setData(fee);
        	r.setSucc(true);
			r.setMsg("查询成功");
			logger.info("团队["+teamId+"],类型["+type+"],保费:"+fee);
		} catch (Exception e) {
			logger.error("查询保费", e);
			r.setSucc(false);
			r.setMsg("查询失败:"+e.getMessage());
		}
        return r;
    }
    
    
    /**
     * 更多
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/more.do", method = RequestMethod.GET)
    public String more(HttpServletRequest request, HttpServletResponse response, Model model) {
        String redrurl = WeiXinUtils.getOauthUserInfoToBind(bindUrl);
        logger.info("UserController more.do");
        return "user.more";
    }

    @RequestMapping(value = "/check_wechat_bind.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultMap checkWechatBind(HttpServletRequest request, HttpServletResponse response) {
        logger.info("UserController checkWechatBind.do");
        ResultMap resultMap = new ResultMap();
        try {
            UserDTO userDTO = weChatService.queryUserByUserId(this.getAuthenticatedUserId());
            if (null == userDTO || StringUtils.isBlank(userDTO.getWechartId())) {
                resultMap.setMessage("未绑定微信");
                resultMap.setData(0);
            } else {
                resultMap.setMessage("你已绑定微信");
                resultMap.setData(1);
            }
            resultMap.setSuccess(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultMap.setSuccess(false);
            resultMap.setMessage(e.getMessage());
        }
        return resultMap;
    }
    
    
    @RequestMapping(value = "/remove_bind_wechat.do", method = RequestMethod.POST)
    @ResponseBody
    @Log("解除绑定微信")
    public ResultMap removeBindWechat(HttpServletRequest request, HttpServletResponse response) {
        ResultMap resultMap = new ResultMap();
        try {
            weChatService.deleteWeChatId(this.getAuthenticatedUserId());
            resultMap.setMessage("操作成功：已解除绑定微信");
            resultMap.setSuccess(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultMap.setSuccess(false);
            resultMap.setMessage(e.getMessage());
        }
        return resultMap;
    }

    /**
     * 关于我们
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/about.do", method = RequestMethod.GET)
    public String about(HttpServletRequest request, HttpServletResponse response) {
        return "user.about";
    }

    /**
     * 修改密码
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/revisedCode.do", method = RequestMethod.GET)
    public String revisedCode(HttpServletRequest request, HttpServletResponse response) {
        return "user.revisedCode";
    }

    /**
     * 保险客服
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/insureSeviceTel.do", method = RequestMethod.GET)
    public String insureSeviceTel(HttpServletRequest request, HttpServletResponse response) {
        return "user.insureSeviceTel";
    }

    /**
     * 配送信息管理
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/manageAddress.do", method = RequestMethod.GET)
    public String manageAddress(HttpServletRequest request, HttpServletResponse response) {
        return "user.manageAddress";
    }

    /**
     * 比较密码
     * 
     * @param request
     * @param response
     * @param possword
     * @return
     */
    @RequestMapping(value = "/confirmPassword.do")
    @ResponseBody
    public AjaxResult confirmPossword(HttpServletRequest request, HttpServletResponse response, String oldPassword) {
        try {
            UserDTO user = userService.queryUserByUserId(getAuthenticatedUserId());
            boolean i = Encoding.matches(oldPassword, user.getLoginPassword());
            return new AjaxResult(i);
        } catch (Exception e) {
            logger.error("用户:" + getAuthenticatedUserId() + "比较失败:" , e);
        }
        return new AjaxResult(false);
    }

    /**
     * 业绩报表
     * 
     * @return
     */
    @RequestMapping(value = "/performance.do")
    public String performance(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> condition = new HashMap<String, Object>();
        String beginTime = null;
        try {
            condition.put("userId", getAuthenticatedUserId());
            PagingResult<ReportDTO> reportList = orderService.selectAllMicroReportByPage(condition);
            for (ReportDTO reportDTO : reportList) {
                StringBuffer str = new StringBuffer();
                str.append((reportDTO.getCarApplyNo()).substring(0, 7));
                str.append("...");
                str.append((reportDTO.getCarApplyNo()).substring(14, 21));
                reportDTO.setCarApplyNo(str.toString());
            }
            request.setAttribute("reportList", reportList);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            beginTime = simpleDateFormat.format(new Date());

        } catch (Exception e) {
            logger.error("用户:" + getAuthenticatedUserId() + "查询业绩报表信息失败:" , e);
            beginTime = "";
        }
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", beginTime);
        return "user.performance";
    }

    /**
     * 根据条件查询业绩报表
     * 
     * @param request
     * @param response
     * @param orderId
     * @param name
     * @param beginTime
     * @param endTime
     * @param timeType
     * @return
     */
    @RequestMapping(value = "/getPerFormanceByItem.do")
    public String getPerFormanceByItem(HttpServletRequest request, HttpServletResponse response, String queryCondition,
            String beginTime, String endTime, String timeType) {
        logger.info("queryCondition:" + queryCondition + "  beginTime:" + beginTime + "  endTime:" + endTime
                + "  timeType:" + timeType);
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("queryCondition", queryCondition);
        condition.put("userId", getAuthenticatedUserId());
        Date date = new Date();
        if (StringUtils.isBlank(beginTime)) {
            // 如果开始时间为空，给开始时间设置一个初始值，默认为当天
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            beginTime = simpleDateFormat.format(date);
        }
        if (StringUtils.isBlank(endTime)) {
            // 如果结束时间为空，给开始时间设置一个初始值，默认为当天
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            endTime = simpleDateFormat.format(date);
        }
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        PagingResult<ReportDTO> reportList = null;
        try {
            reportList = orderService.selectAllMicroReportByPage(condition);
            for (ReportDTO reportDTO : reportList) {
                StringBuffer str = new StringBuffer();
                str.append((reportDTO.getCarApplyNo()).substring(0, 7));
                str.append("...");
                str.append((reportDTO.getCarApplyNo()).substring(14, 21));
                reportDTO.setCarApplyNo(str.toString());
            }
        } catch (Exception e) {
            logger.error("用户:" + getAuthenticatedUserId() + "查询自己的业绩信息失败:" , e);
            reportList = new PagingResult<ReportDTO>(new ArrayList<ReportDTO>());
        }
        request.setAttribute("reportList", reportList);
        request.setAttribute("queryCondition", queryCondition);
        // request.setAttribute("timeType", timeType);
        return "user.performance";
    }

    /**
     * 业绩报表详情
     * 
     * @return
     */
    @RequestMapping(value = "/performanceInfo.do")
    public String performanceInfo(HttpServletRequest request, HttpServletResponse response, String orderId) {
        logger.info("orderId:" + orderId);
        ReportDTO report = null;
        try {
            report = orderService.selectDetailReport(orderId);
        } catch (Exception e) {
            logger.error("用户:" + getAuthenticatedUserId() + "查询业绩信息详情失败:" , e);
            report = new ReportDTO();
        }
        String str = "2012-12-10 12:12";
        report.setPlyCrtTm((report.getPlyCrtTm()).substring(0, str.length()));
        request.setAttribute("report", report);
        return "user.performanceInfo";
    }

    /**
     * 
     * 
     * @return
     */
    @RequestMapping(value = "/install.do")
    public String install(HttpServletRequest request, HttpServletResponse response) {
        return "user.install";
    }

    /**
     * 常见问题
     * 
     * @return
     */
    @RequestMapping(value = "/question.do")
    public String question(HttpServletRequest request, HttpServletResponse response) {
        return "user.question";
    }

    /**
     * 使用指南
     * 
     * @return
     */
    @RequestMapping(value = "/used.do")
    public String used(HttpServletRequest request, HttpServletResponse response) {
        return "user.used";
    }

    @Autowired
    private IdentityInfoService identityInfoService;
    
    /**
     * 我的信息
     * 
     * @return
     */
    @RequestMapping(value = "/personal.do")
    public String personal(HttpServletRequest request, HttpServletResponse response, Model model) {
        MicroDTO micro = null;
        String userId = this.getAuthenticatedUserId();
        String cardId = null;
        try {
            micro = microService.getMicroByUserId(userId);
            
            MicroApproveDTO  ma = identityInfoService.findConfirm(userId, false);
            if(null != ma && "2".equals(ma.getApproveState()+"")){//认证通过
            	cardId = ma.getMicroCardId();
            }
        } catch (Exception e) {
            logger.error("用户:" + userId + "查询自己的信息失败:" , e);
            micro = new MicroDTO();
        }
        model.addAttribute("userId", userId);
        model.addAttribute("cardId", cardId);
        request.setAttribute("micro", micro);
        return "user.personal";
    }
    
    
    /**
     * 银行列表
     * 
     * @param request
     * @param response
     * @param user_id
     * @return
     */
    @RequestMapping(value = "/getBank.do")
    @ResponseBody
    public AjaxResult getBank(HttpServletRequest request, HttpServletResponse response, String user_id) {
        logger.info("user_id:" + user_id);
        try {
            List<BankDTO> bankList = bankService.queryBankService();
            return new AjaxResult(bankList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new AjaxResult(false, e.getMessage());
        }
    }
    
    
    /**
     * 密码丢失找回页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/passport/index.do", method=RequestMethod.GET)
    public String passportIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "passport.index";
    }
    
    
    /**
     * 更新密码
     * @param request
     * @param response
     * @param phoneCode
     * @param password
     * @return
     */
    @RequestMapping(value = "/passport/update_password.do")
    @ResponseBody
    public AjaxResult passportUpdatePassword(HttpServletRequest request, HttpServletResponse response, String phoneCode, String password) {
        try {
        	if(!smsTBUtil.checkSMSCode(request, request.getSession().getAttribute(PASSPORT_TEL).toString(), phoneCode)){
        		logger.info("手机验证码错误");
            	return new AjaxResult(false, "手机验证码错误");
        	}
        	if(StringUtils.isBlank(password)){
        		logger.info("密码不能为空");
            	return new AjaxResult(false, "密码不能为空");
        	}
        	microService.updatePwd(request.getSession().getAttribute(PASSPORT_USER_ID).toString(), password);
        } catch (Exception e) {
            logger.error("找回密码，修改密码失败：" + e.getMessage() ,e);
            return new AjaxResult(false, "找回密码，修改密码失败：" + e.getMessage());
        }
        logger.info("找回密码,修改密码操作成功");
        return new AjaxResult(true, "操作成功");
    }
    
    
    
    /**
     * 发送手机验证码pre
     * @param request
     * @param response
     * @param oldPassword
     * @return
     */
    
    @Autowired
    private SMSTBUtils smsTBUtil;
    
    @RequestMapping(value = "/passport/send_phone_code.do")
    @ResponseBody
    public AjaxResult passportSendPhoneCodePre(HttpServletRequest request, HttpServletResponse response, String accountName) {
        String userId;
        String result = null;
        if(StringUtils.isBlank(accountName)){
        	logger.info("账户名不能为空");
        	return new AjaxResult(false, "账户名不能为空");
        }
        try {
            if(accountName.matches("^(13|14|15|17|18)\\d{9}$")){
            	Integer count = microService.getMicroCountByTel(accountName);
            	if(null != count && count > 1){
            		logger.info("该手机号绑定了多个帐号,TEL："+accountName);
            		throw new Exception("该手机号绑定了多个帐号");
            	}
            	MicroDTO micro = microService.getMicroByTel(accountName);
            	if(null == micro){
            		logger.info("该小微用户不存在");
            		throw new Exception("该小微用户不存在");
            	}
            	userId = micro.getUser_id();
            	smsTBUtil.sendSMSTB(request, accountName, ConstantsUtill.SMS_TB_TEMPLATE_PASSPORT);
            	logger.info("手机验证码已发送至" + accountName);
            	result = "手机验证码已发送至" + accountName;
            	request.getSession().setAttribute(PASSPORT_TEL, accountName);
            }else{
            	MicroDTO micro = microService.getMicroByUserId(accountName);
            	if(null == micro){
            		logger.error("该小微用户不存在");
            		throw new Exception("该小微用户不存在");
            	}
            	String tel = micro.getTel();
            	if(StringUtils.isBlank(tel)){
            		logger.info("该小微用户未绑定手机号，用户为："+accountName);
            		throw new Exception("该小微用户未绑定手机号");
            	}
            	userId = micro.getUser_id();
            	smsTBUtil.sendSMSTB(request, tel, ConstantsUtill.SMS_TB_TEMPLATE_PASSPORT);
            	logger.info("手机验证码已发送至" + tel);
            	result = "手机验证码已发送至" + tel;
            	request.getSession().setAttribute(PASSPORT_TEL, tel);
            }
        } catch (Exception e) {
        	logger.error("校验失败:"+e.getMessage(), e);
        	return new AjaxResult(false, "校验失败:" + e.getMessage());
        }
        request.getSession().setAttribute(PASSPORT_USER_ID, userId);
        return new AjaxResult(true, result, userId);
    }
    
    @RequestMapping("/set.do")
    public String set(HttpServletRequest request, HttpServletResponse response){
    	
    	return "user.set";
    }
    
    @RequestMapping(value = "/suggest.do", method = RequestMethod.GET)
	public String suggest(HttpServletRequest request, HttpServletResponse response) {
		return "user.suggest";
	}

	@RequestMapping(value = "/suggest.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult suggestSave(HttpServletRequest request, HttpServletResponse response, String suggest) {
		String userId = this.getAuthenticatedUserId();
		try {
			userService.addSuggest(userId, suggest);
		} catch (Exception e) {
			logger.error("用户:" + userId + "保存反馈信息失败:" , e);
			return new AjaxResult(false, "保存反馈信息失败");
		}
		return new AjaxResult(true);
	}
    
    private static final String PASSPORT_USER_ID = "PASSPORT_USER_ID";
    private static final String PASSPORT_TEL = "PASSPORT_TEL";
}
