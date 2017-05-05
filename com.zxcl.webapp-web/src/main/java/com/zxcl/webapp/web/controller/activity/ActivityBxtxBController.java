package com.zxcl.webapp.web.controller.activity;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meyacom.fw.um.security.SecurityHolder;
import com.zxcl.webapp.biz.service.activity.ActivityBxtxBService;
import com.zxcl.webapp.dto.activity.ActivityBxtxBWinningListDTO;

@Controller
@RequestMapping("/activityb")
public class ActivityBxtxBController {

	private static Logger logger = Logger.getLogger(ActivityBxtxBController.class);
	
	@Autowired
	private SecurityHolder securityHolder;
	
	@Autowired
	private ActivityBxtxBService activityBxtxBService;
	
	@RequestMapping("/toActivityBxtxBPage.do")
	public String toActivityBxtxBPage(Model m){
		
		String userId = securityHolder.getAuthenticatedUserId();
		
		m.addAttribute("count", activityBxtxBService.getLotteryNumber(userId));
		List<ActivityBxtxBWinningListDTO> lists = activityBxtxBService.getActivityBxtxBWinnings();
		
		Integer wincount = 0;
		Integer result = -1;
		if(lists != null && lists.size() > 0){
			for(ActivityBxtxBWinningListDTO win : lists){
				if(StringUtils.equals(userId, win.getUserId()) && 
						win.getStatus() != null && win.getStatus().intValue() > 1){
					result = win.getLevel();
				}
				if(win.getStatus() != null && win.getStatus().intValue() > 1){
					wincount += 1;
				}
			}
		}
		m.addAttribute("result", result);
		m.addAttribute("wincount", wincount);
		m.addAttribute("lists", lists);
		
		return "activity.bxtxbpage";
	}
	
	@RequestMapping(value="/luckdraw.do",method=RequestMethod.POST)
	@ResponseBody
	public Integer luckdraw(){
		
		String userId = securityHolder.getAuthenticatedUserId();
		
		try {
			return activityBxtxBService.updateLuckdraw(userId);
		} catch (Exception e) {
			logger.error("保行天下活动B-转盘活动抽将失败", e);
			return -1; 
		}
		
	}
	
}
