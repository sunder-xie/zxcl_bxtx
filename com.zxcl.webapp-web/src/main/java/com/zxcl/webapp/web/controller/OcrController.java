/**
 * 
 */
package com.zxcl.webapp.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.ocr_service.openapi.OcrVehicleLicenseService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WalletCoreService;
import com.zxcl.webapp.biz.service.openapi.OpenApiManageService;
import com.zxcl.webapp.dto.AgentServiceControlDTO;
import com.zxcl.webapp.dto.OcrA1ResultDetailDTO;
import com.zxcl.webapp.integration.dao.AgencyDAO;
import com.zxcl.webapp.integration.dao.AgentServiceControlDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.integration.dao.UserDAO;
import com.zxcl.webapp.web.vo.AjaxResult;

/**
 * @author zxj
 * @date 2016年9月1日
 * @description 
 */
@Controller
@RequestMapping("/ocr")
public class OcrController extends BaseController {
	
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
	
	private @Value("${share.url}")String baseURL;
	
	@Autowired
	private OcrVehicleLicenseService ocrVehicleLicenseService;
	
	@Autowired
	private AgentServiceControlDAO agentServiceControlDAO;
	
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
	
	/**
	 * 识别行驶证
	 * @param request
	 * @param response
	 * @param model
	 * @param user_id
	 * @param invitation
	 * @return
	 */
	@RequestMapping(value = "/identifyVehicleLicense.do")
	@ResponseBody
	public AjaxResult identifyVehicleLicense(HttpServletRequest request, HttpServletResponse response, ModelMap model,String fileId) {
		logger.info("OcrController identifyVehicleLicense.do, fileId="+fileId);
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSucc(false);
		ajaxResult.setMsg("识别失败");
		if(StringUtils.isBlank(fileId)){
			ajaxResult.setMsg("请上传行驶证");
			return ajaxResult;
		}
		if(!canUseVehicleLicenseOcr()){
			ajaxResult.setMsg("代理未开启行驶证识别功能");
			return ajaxResult;
		}
		try {
			
//			String resultBack = ocrVehicleLicenseService.ocrVehicleLicense(new URL(baseURL+"/file_server/files/"+fileId).openStream());
			String resultBack = ocrVehicleLicenseService.ocrVehicleLicenseForUrl(baseURL+"/file_server/files/"+fileId);
			logger.info("识别结果:"+resultBack);
			if(StringUtils.isNotBlank(resultBack) && resultBack.startsWith("{")){
				JSONObject ocrResultDTO = JSONObject.parseObject(resultBack);
				String returnCode = ocrResultDTO.getString("returnCode");//E0200:成功
				JSONObject resultDetail = ocrResultDTO.getJSONObject("resultDetail");//详情
				if("E0200".equals(returnCode) && resultDetail != null){
					OcrA1ResultDetailDTO ocrA1ResultDetailDTO = JSONObject.toJavaObject(resultDetail, OcrA1ResultDetailDTO.class);
					ajaxResult.setSucc(true);
					ajaxResult.setMsg("识别成功");
					ajaxResult.setData(ocrA1ResultDetailDTO);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return ajaxResult;
	}
}
