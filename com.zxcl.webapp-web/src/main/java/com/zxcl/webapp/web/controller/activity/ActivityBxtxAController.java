package com.zxcl.webapp.web.controller.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxcl.webapp.biz.service.activity.ActivityBxtxAService;

@Controller
@RequestMapping("/activity")
public class ActivityBxtxAController {

	private static Logger logger = Logger.getLogger(ActivityBxtxAController.class);
	
	@Autowired
	private ActivityBxtxAService activityBxtxAService;
	
	@RequestMapping("/toActivityBxtxAPage.do")
	public String toActivityBxtxAPage(){
		
		return "activity.bxtxapage";
	}
	
	@ResponseBody
	@RequestMapping("/bxtxaOpenRedPacket.do")
	public Map<String, Object> bxtxaOpenRedPacket(){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = activityBxtxAService.updateOpenRedPacket();
		} catch (Exception e) {
			logger.error("领取红包失败", e);
			result.put("stutas", -1);
		}
		
		return result;
	}
}
