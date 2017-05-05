package com.zxcl.webapp.web.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.ActivityService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.PolicySalesActivityFeeService;
import com.zxcl.webapp.dto.ActivityDTO;
import com.zxcl.webapp.dto.MicroDTO;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

	private static Logger logger = Logger.getLogger(ActivityController.class);
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private PolicySalesActivityFeeService policySalesActivityFeeService;
	
	@RequestMapping("/historyActivity.do")
	public String getHistoryActivity(HttpServletRequest req,Model m){
		
		
		return "activity.historyactivity";
	}
	
	@RequestMapping(value="/historyActivityData.do",method=RequestMethod.POST)
	@ResponseBody
	public List<ActivityDTO> getHistoryActivityData(HttpServletRequest req,Integer pageNo){
		
		MicroDTO microDTO = new MicroDTO();
		try {
			microDTO = microService.getMicroByUserId(getAuthenticatedUserId());
		} catch (BusinessServiceException e) {
			logger.error("查询小微失败",e);
		}
		try {
			List<ActivityDTO> list = activityService.getHistoryActivity(microDTO, pageNo);
			if(list != null && list.size() > 0){
				Date now = new Date();
				for(ActivityDTO a : list){
					
					if(a.getStatus() != null && a.getStatus().intValue() == 1 
							&& a.getStartTime().getTime() < now.getTime()
							&& a.getEndTime().getTime() > now.getTime()){
						a.setStatus(1); //进行中
					}else if(a.getStatus() != null && a.getStatus().intValue() == 1 &&
							( a.getEndTime().getTime() < now.getTime() )){
						a.setStatus(-2); //已过期
					}else if(a.getStatus() != null && a.getStatus().intValue() == 1 &&
							(a.getStartTime().getTime() > now.getTime()  )){
						a.setStatus(-3); //未开始
					}
					
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("查询活动历史记录失败", e);
			return null;
		}
		
	}
	
	
	@RequestMapping(value="/getActivityById.do",method=RequestMethod.GET)
	public String getActivityById(HttpServletRequest req,
			HttpServletResponse rep,Integer id ,Model m){
		
		MicroDTO microDTO = new MicroDTO();
		try {
			microDTO = microService.getMicroByUserId(getAuthenticatedUserId());
		} catch (BusinessServiceException e) {
			logger.error("查询小微失败",e);
		}
		
		ActivityDTO dto = null;
		try{
			dto = activityService.getActivityDetailById(id, microDTO);
		}catch(Exception e){
			logger.error("查询活动失败", e);
			return "redirect:/index.do?nocheck=1";
		}
		
		if(dto == null){
			return "redirect:/index.do?nocheck=1";
		}else {
			m.addAttribute("activity", dto);
			return "activity.activitydetail";
		}
		
	}
	
	@RequestMapping("/jumpToJCPlolicySaleCopyWritePage.do")
	public String activityMain(){
		return "activity.jcpolicysalecopywrite";
	}
	
	@RequestMapping("/jumpToJCPolicySaleRewardPage.do")
	public String jumpToJCPolicySaleRewardPage(){
		
		return "activity.jcpolicysalereward";
	}
	
	/**
	 * 获取嘉诚活动的可抽奖次数
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/getUserPolicySalesCount.do",method=RequestMethod.POST)
	@ResponseBody
	public Integer getUserPolicySalesCount(){
		
		try {
			return policySalesActivityFeeService.getUserPolicySalesCount(getAuthenticatedUserId());
		} catch (Exception e) {
			logger.error("用户获取嘉诚出商业险单奖励次数失败。", e);
		}
		
		return 0;
		
	}
	
	/**
	 * 嘉诚活动商业险出单奖励， 开始抽奖
	 * @return
	 */
	@RequestMapping(value="/startLuckyDraw.do",method=RequestMethod.POST)
	@ResponseBody
	public BigDecimal startLuckyDraw(){
		return new BigDecimal(-1);
//		try {
//			return policySalesActivityFeeService.updateUserLuckDraw(getAuthenticatedUserId());
//		} catch (Exception e) {
//			logger.error("嘉诚活动商业险出单，抽奖失败", e);
//			return new BigDecimal(-1);
//		}
	}
	
	@RequestMapping("/20160525tpiccp.do")
	public String tpiccp20160525(){
		return "activity.160525tpiccopy";
	}
}
