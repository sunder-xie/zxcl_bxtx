package com.zxcl.webapp.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.IdentityInfoService;
import com.zxcl.webapp.dto.MicroApproveDTO;
import com.zxcl.webapp.web.vo.AjaxResult;

@Controller
@RequestMapping("/identity")
public class IdentityController extends BaseController{

	protected static Logger logger = Logger.getLogger(IdentityController.class);
	
	private @Value("${share.url}")String baseURL;
	
	@Autowired
	private IdentityInfoService identityInfoService;
	
	@RequestMapping("index.do")
	public String index(HttpServletRequest req,HttpServletResponse rep, Model model){
		model.addAttribute("baseURL", baseURL);
		MicroApproveDTO ma = null;
		try {
			ma = identityInfoService.findConfirm(this.getAuthenticatedUserId(), false);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		model.addAttribute("ma", null == ma ? new MicroApproveDTO():ma);
		return "identity.index";
	}
	
	@RequestMapping("xieyi.do")
	public String xieyi(HttpServletRequest req,HttpServletResponse rep){
		return "identity.xieyi";
	}
	
	
	/**
	 * 身份是否确认(是否实名认证通过)
	 * @param req
	 * @param rep
	 * @return
	 */
	@RequestMapping("is_confirmed.do")
	@ResponseBody
	public AjaxResult isConfirmed(HttpServletRequest req,HttpServletResponse rep){
		AjaxResult r = new AjaxResult();
		try {
			boolean b = identityInfoService.isConfirmed(this.getAuthenticatedUserId());
			r.setSucc(true);
			r.setMsg(b ? "1":"0");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			r.setSucc(false);
			r.setMsg("FAIL");
		}
		return r;
		
	}
	
	/**
	 * find_confirm
	 * @param req
	 * @param rep
	 * @return
	 */
	@RequestMapping("find_confirm.do")
	@ResponseBody
	public AjaxResult findConfirm(HttpServletRequest req,HttpServletResponse rep){
		AjaxResult r = new AjaxResult();
		try {
			r.setData(identityInfoService.findConfirm(getAuthenticatedUserId(), false));
			r.setSucc(true);
			r.setMsg("OK");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			r.setSucc(false);
			r.setMsg("FAIL");
		}
		return r;
		
	}
	
	/**
	 * 代理是否要求实名认证
	 * @param req
	 * @param rep
	 * @return
	 */
	@RequestMapping("is_need_approve.do")
	@ResponseBody
	public AjaxResult isNeedApporve(HttpServletRequest req,HttpServletResponse rep){
		AjaxResult r = new AjaxResult();
		try {
			boolean b = identityInfoService.isNeedApprove(this.getAuthenticatedUserId());
			r.setSucc(true);
			r.setMsg(b ? "1":"0");
		} catch (BusinessServiceException be) {
			logger.info(be.getMessage());
			r.setSucc(false);
			r.setMsg("FAIL");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			r.setSucc(false);
			r.setMsg("FAIL");
		}
		return r;
		
	}
	
	
	/**
	 * 提交认证
	 * @param req
	 * @param rep
	 * @param fileIds
	 * @param icardid
	 * @param iname
	 * @return
	 */
	@RequestMapping("confirm.do")
	@ResponseBody
	public AjaxResult confirm(HttpServletRequest req,HttpServletResponse rep,String fileIds, String icardid ,String iname){
		logger.info("confirm ==> fileIds="+fileIds+",icardid="+icardid+",iname"+iname);
		AjaxResult r = new AjaxResult();
		if(StringUtils.isBlank(icardid)){
			r.setSucc(false);
			r.setMsg("身份证号必传");
			return r;
		}
		if(StringUtils.isBlank(iname)){
			r.setSucc(false);
			r.setMsg("姓名必传");
			return r;
		}
		
		if(StringUtils.isBlank(fileIds)){
			r.setSucc(false);
			r.setMsg("附件必传");
			return r;
		}
		try {
			identityInfoService.confirmIndentityInfo(this.getAuthenticatedUserId(), fileIds, iname, icardid);
			r.setSucc(true);
			r.setMsg("OK");
		} catch (BusinessServiceException be) {
			logger.info(be.getMessage());
			r.setSucc(false);
			r.setMsg("FAIL: "+be.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			r.setSucc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}
	
	
}
