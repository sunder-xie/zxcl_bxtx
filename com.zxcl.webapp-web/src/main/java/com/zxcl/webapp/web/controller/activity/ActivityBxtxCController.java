package com.zxcl.webapp.web.controller.activity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meyacom.fw.um.security.SecurityHolder;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.biz.service.activity.ActivityBxtxCService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.activity.ActivityBxtxCOrderDTO;
import com.zxcl.webapp.integration.dao.ActivityDAO;
import com.zxcl.webapp.integration.dao.activity.ActivityBxtxCDAO;

@Controller
@RequestMapping("/activityc")
public class ActivityBxtxCController {

	private static Logger logger = Logger.getLogger(ActivityBxtxCController.class);
	
	@Autowired
	private ParamConfigService paramConfigService;
	
	@Autowired
	private ActivityDAO activityDAO;
	
	@Autowired
	private ActivityBxtxCService activityBxtxCService;
	
	@Autowired
	private SecurityHolder securityHolder;
	
	@Autowired
	private ActivityBxtxCDAO activityBxtxCDAO;
	
	@RequestMapping("/toActivityPage.do")
	public String toActivitiCPage(Model m){
		
		m.addAttribute("currentTime", DateUtil.dateToString(DateUtil.DATETIME24_PATTERN_LINE, new Date()));
		String startTimeStr = paramConfigService.getValueByKey("ACTIVITY_BXTXC2_LUCKDRAW_STARTTIME");
		String endTimeStr = paramConfigService.getValueByKey("ACTIVITY_BXTXC2_LUCKDRAW_ENDTIME");
		m.addAttribute("startTime", startTimeStr);
		m.addAttribute("endTime", endTimeStr);		
		
		return "activity.bxtxcpage";
	}
	

	
	/**
	 * 获取金额
	 * @return
	 */
	@RequestMapping("/getCurrentMoney.do")
	@ResponseBody
	public String getCurrentMoney(){
		
		String startTime = paramConfigService.getValueByKey("ACTIVITY_BXTXC2_STARTTIME");
		String endTime = paramConfigService.getValueByKey("ACTIVITY_BXTXC2_ENDTIME");
		String fee = paramConfigService.getValueByKey("ACTIVITY_BXTXC2_FEE");
		
		Integer count = activityDAO.getActivityBxtxCOrderCount(startTime, endTime);
		
		count = count == null ?  0 :count;
		
		BigDecimal money = new BigDecimal(count).multiply(new BigDecimal(fee)).setScale(2, RoundingMode.HALF_DOWN);
		
		BigDecimal lotteriedTotal = activityBxtxCDAO.getActivityBxtxCLotteriedTotal();
		lotteriedTotal = lotteriedTotal == null ? BigDecimal.ZERO : lotteriedTotal.multiply(new BigDecimal(2));
		money =  money.subtract(lotteriedTotal).setScale(2, RoundingMode.HALF_UP);
//		Map<String, String> result = new HashMap<String, String>();
//		
//		Integer crtDayCount = activityDAO.getActivityBxtxcUsersCount(
//				DateUtil.dateToString("yyyy-MM-dd", new Date()) + " 00:00:00", 
//				DateUtil.dateToString("yyyy-MM-dd", new Date()) + " 23:59:59");
//		
//		crtDayCount = crtDayCount == null ? 0 : crtDayCount;
//		
//		result.put("money", money.toString());
//		result.put("count", crtDayCount+"");
//		
//		return result;
		
		return money.toString();
		
	}
	
	@RequestMapping("/lotteryPage.do")
	public String lotteryPage(){
		
		return "activity.bxtxclettrypage";
	}
	
	
	@RequestMapping("/getLettoryNum.do")
	@ResponseBody
	public String getLettoryCount(){
		
		String userId = securityHolder.getAuthenticatedUserId();
		
		if(StringUtils.isBlank(userId)){
			return "-2";
		}
		
		try {
			Integer count = activityBxtxCService.getLotteryNumber(userId);
			return count + "";
		} catch (Exception e) {
			logger.error("获取可领奖次数失败", e);
			return "-3";
		}
	}
	
	@RequestMapping("/luckdraw.do")
	@ResponseBody
	public Map<String,String> luckdraw(){
		
		Map<String,String> result = new HashMap<String, String>();
		
		String userId = securityHolder.getAuthenticatedUserId();
		
		if(StringUtils.isBlank(userId)){
			result.put("result", "-1");
			return result;
		}
		
		try {
			result = activityBxtxCService.updateLuckdraw(userId);
			return result;
		} catch (Exception e) {
			logger.error("领取红包失败", e);
			result.put("result", "-2"); //未知原因
			return result;
		}
	}
	
	/**
	 * 获取中奖前10名单
	 * @return
	 */
	@RequestMapping("/getNamelists.do")
	@ResponseBody
	public List<ActivityBxtxCOrderDTO> getOrders(){
		try {
			
			List<ActivityBxtxCOrderDTO> lists = activityBxtxCDAO.getActivityBxtxCOrders();
			if(lists != null && lists.size() > 0){
				for(ActivityBxtxCOrderDTO  o : lists){
					String name = "*";
					if(o.getName().length() > 1){
						name += o.getName().substring(1, o.getName().length());
					}else{
						name += o.getName();
					}
					o.setName(name);
				}
			}
			return lists;
		} catch (Exception e) {
			logger.error("获取名单失败", e);
			return null;
		}
	}
}
