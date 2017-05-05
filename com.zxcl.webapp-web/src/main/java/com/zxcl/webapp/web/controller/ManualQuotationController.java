package com.zxcl.webapp.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.webapp.biz.action.data.ChangeDataAction;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.ManualQuotationTaskService;
import com.zxcl.webapp.biz.service.ManualQuoterService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.service.QuotnConfigService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.ManualQuotationTaskDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.QuoteTrackDTO;
import com.zxcl.webapp.dto.QuotnConfigDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.web.vo.AjaxResult;

/**
 * 人工报价
 * @author zx
 *
 */
@Controller
@RequestMapping(value="manualQuotation")
public class ManualQuotationController extends BaseController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ChangeDataAction changeDataAction;
	
	@Autowired
	private ManualQuotationTaskService manualQuotationTaskService;
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private QuotaService quotaService;
	
	@Autowired
	private QuotnConfigService quotnConfigService;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private ManualQuoterService manualQuoterService;
	
	/**
	 * 人工报价
	 * @param request
	 * @param response
	 * @param inquiryId 询价单号
	 * @param insId 保险公司ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="manualQuotation.do")
	public AjaxResult manualQuotation(HttpServletRequest request,HttpServletResponse response, String inquiryId,
			String insId){
		try {
			Integer i = quotaService.queryManualQuotationByInquiryId(inquiryId);
			if(i >= 2){
				return new AjaxResult(false,"最多提交两个人工报价服务");
			}
			String userId = getAuthenticatedUserId();
			MicroDTO microDTO = microService.getMicroByUserId(userId);
			//报价单号
			String quoteId = insId+DateUtil.dateToString("yyyyMMddHHmmssSSS", new Date())+((int)(Math.random()*900)+100);
			ManualQuotationTaskDTO manualQuotationTaskDTO = manualQuotationTaskService.createTaskDataAssembly(inquiryId, userId, quoteId, insId, microDTO.getAgt_team_id());
			manualQuotationTaskService.insert(manualQuotationTaskDTO);
			QuotaDTO quotaDTO = new QuotaDTO();
			quotaDTO.setQuotaId(quoteId);
			InquiryDTO inquiryDTO = new InquiryDTO();
			inquiryDTO.setInquiryId(inquiryId);
			quotaDTO.setInquiry(inquiryDTO);
			InsuranceDTO insuranceDTO = new InsuranceDTO();
			insuranceDTO.setInsId(insId);
			quotaDTO.setInsurance(insuranceDTO);
			quotaDTO.setMicro(microDTO);
			quotaDTO.setQuotaType("M");
			quotaDTO.setCrtCode(userId);
			quotaDTO.setUpdCode(userId);
			quotaService.insertDetailed(quotaDTO, null);
			manualQuoterService.notifyQuoter(microDTO.getAgt_team_id(), insId);
			return new AjaxResult(true,"正在人工报价中");
		} catch (Exception e) {
			logger.error("用户："+getAuthenticatedUserId()+"人工报价保险公司："+insId+"失败",e);
			return new AjaxResult(false,"系统异常");
		}
	}
	
	/**
	 * 判断该询价单下是否有人工报价的单子
	 * @param request
	 * @param response
	 * @param inquiryId 询价单号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryManualQuotationByInquiryId.do")
	public AjaxResult queryManualQuotationByInquiryId(HttpServletRequest request,HttpServletResponse response,String inquiryId){
		try {
			Integer quoteCount = quotaService.queryManualQuotationByInquiryId(inquiryId);
			if(quoteCount > 0){
				return new AjaxResult(true,"",true);
			}else{
				return new AjaxResult(true,"",false);
			}
		} catch (Exception e) {
			logger.error("用户："+getAuthenticatedUserId()+"判断询价单下是否有人工报价单子失败");
			return new AjaxResult(false,"");
		}
	}
	
	/**
	 * 撤回人工报价任务
	 * @param request
	 * @param response
	 * @param inquiryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="withdrawQuotn.do")
	public AjaxResult withdrawQuotn(HttpServletRequest request,HttpServletResponse response,String inquiryId){
		try {
			//修改人工报价任务状态并添加轨迹信息
			manualQuotationTaskService.withdrawQuotn(inquiryId, getAuthenticatedUserId());
			return new AjaxResult(true,"");
		} catch (Exception e) {
			logger.error("用户："+getAuthenticatedUserId()+"撤回人工报价任务失败",e);
			return new AjaxResult(false,"");
		}
	}
	
	/**
	 * 查看人工报价失败原因
	 * @param request
	 * @param response
	 * @param quoteId 报价单号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="viewManualQuotationFailureReason.do")
	public AjaxResult viewManualQuotationFailureReason(HttpServletRequest request,HttpServletResponse response,String quoteId,String type){
		try {
			String manualQuotationFailureReason = "";
			ManualQuotationTaskDTO manualQuotationTaskDTO = manualQuotationTaskService.queryByQuoteId(quoteId);
			for (QuoteTrackDTO quoteTrackDTO : manualQuotationTaskDTO.getQuoteTrackList()) {
				if(type.equals(quoteTrackDTO.getOperatStatus())){
					manualQuotationFailureReason = quoteTrackDTO.getRemark();
				}
			}
			return new AjaxResult(true,"",manualQuotationFailureReason);
		} catch (Exception e) {
			logger.error("用户："+getAuthenticatedUserId()+"查看人工报价失败原因失败",e);
			return new AjaxResult(false,"获取人工报价失败原因失败");
		}
	}
	
	/**
	 * 查看支付方式
	 * @param request
	 * @param response
	 * @param quoteId 报价单号
	 * @param insId 保险公司ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="viewPayType.do")
	public AjaxResult viewPayType(HttpServletRequest request,HttpServletResponse response,String insId,String inquiryId){
		try {
			MicroDTO micro = microService.getMicroByUserId(getAuthenticatedUserId());
			InquiryDTO inquiryDTO = inquiryService.get(inquiryId, null);
			QuotnConfigDTO quotnConfigDTO = quotnConfigService.queryByTeamIdAndQuotnTypeAndInsId(micro.getAgt_team_id(), "M", insId,inquiryDTO.getUsageCode());
			return new AjaxResult(true,"",quotnConfigDTO.getPayTipMessage());
		} catch (Exception e) {
			logger.error("用户："+getAuthenticatedUserId()+"获取支付方式失败",e);
			return new AjaxResult(false,"获取支付方式失败");
		}
	}
	
}
