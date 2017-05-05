package com.zxcl.webapp.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.dto.MicroDTO;

@Controller
@RequestMapping
public class OtherController extends BaseController{

	private static Logger logger = Logger.getLogger(OtherController.class);
	
	@Autowired
	private MicroService microService;
	
	@RequestMapping("/customtel.do")
	public String customTel(Model model, HttpServletRequest req) {
		Map<String, String> personality= new HashMap<String, String>();
		try {
			MicroDTO microDTO = microService.getMicroByUserId(getAuthenticatedUserId());
			if(null == microDTO){
				return "logon";
			}
			personality = microService.getPersonalityByMicId(microDTO);
			// 如果代理机构或者团队都不存在电话号码 使用保行天下的客服电话
			HttpSession session = req.getSession();
			if (StringUtils.isNotBlank(personality.get("agtTeamTel"))) {
				session.setAttribute("tel", personality.get("agtTeamTel"));
			} else if (StringUtils.isNotBlank(personality.get("agentTel"))) {
				session.setAttribute("tel", personality.get("agentTel"));
			} else {
				session.setAttribute("tel", "4009691365");
			}
			if (StringUtils.isNotBlank(personality.get("agentName"))) {
				session.setAttribute("agentName", personality.get("agentName"));
			}
			
			if(StringUtils.isNotBlank(personality.get("teamAliasName"))){
				session.setAttribute("teamName",personality.get("teamAliasName"));
			}else if(StringUtils.isNotBlank(personality.get("teamName"))){
				session.setAttribute("teamName",personality.get("teamName"));
			}

			model.addAttribute("telMap", personality);
			model.addAttribute("agentName", StringUtils.isNotBlank(personality
					.get("agentName")) ? personality.get("agentName") : "");
		} catch (Exception e) {
			logger.error("查询客服电话失败",e);
		}

		return "other.customtel";
	}
	
	@RequestMapping("/mzsm.do")
	public String mzsm(HttpServletRequest req){
		
		return "other.mzsm";
	}
	
	@RequestMapping("/xtjsz.do")
	public String xtjsz(HttpServletRequest req){
		
		return "other.xtjsz";
	}
	@RequestMapping("/cjwt.do")
	public String cjwt(HttpServletRequest req){
		
		return "other.cjwt";
	}
	@RequestMapping("/gywm.do")
	public String gywm(HttpServletRequest req){
		
		return "other.gywm";
	}
	
	/**
	 * 理赔报案电话
	 * @param req
	 * @return
	 */
	@RequestMapping("/lpbadh.do")
	public String lpbadh(HttpServletRequest req){
		
		return "other.lpbadh";
	}
	
	/**
	 * 附件上传测试页面
	 * @return
	 */
	@RequestMapping("/uploadVideoMaterialTestPage.do")
	public String uploadVideoMasterialTestPage(){
		return "commons.upload_videomaterial";
	}
	
	/**
	 * 系统提示
	 * @return
	 */
	@RequestMapping("/sysalter.do")
	public String sysAlter(){
		return "commons.sysalter";
	}
}
