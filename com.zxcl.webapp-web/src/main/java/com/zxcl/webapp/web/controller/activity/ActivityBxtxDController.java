package com.zxcl.webapp.web.controller.activity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
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
import com.zxcl.webapp.biz.service.activity.ActivityBxtxRedEnvelopesService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.integration.dao.PolicySalesActivityFeeDAO;
import com.zxcl.webapp.integration.dao.activity.ActivityBxtxRedEnvelopesDAO;

@Controller
@RequestMapping("/activityd")
public class ActivityBxtxDController {

private static Logger logger = Logger.getLogger(ActivityBxtxDController.class);
	
	@Autowired
	private ParamConfigService paramConfigService;
	
	@Autowired
	private SecurityHolder securityHolder;
	
	@Autowired
	private ActivityBxtxRedEnvelopesService activityBxtxRedEnvelopesService;
	
	@Autowired
	private PolicySalesActivityFeeDAO salesActivityFeeDAO;
	
	@Autowired
	private ActivityBxtxRedEnvelopesDAO activityBxtxRedEnvelopesDAO;
	
	@RequestMapping("/toActivityPage.do")
	public String toActivitiCPage(Model m){
		
		m.addAttribute("currentTime", DateUtil.dateToString(DateUtil.DATETIME24_PATTERN_LINE, new Date()));
		//第一波时间
		String startTimeStr = paramConfigService.getValueByKey("ACTIVITY_BXTXD1_STARTTIME");
		String endTimeStr = paramConfigService.getValueByKey("ACTIVITY_BXTXD1_ENDTIME");
		String startTimeLuckStr = paramConfigService.getValueByKey("ACTIVITY_BXTXD1_LUCKDRAW_STARTTIME");
		String endTimeLuckStr = paramConfigService.getValueByKey("ACTIVITY_BXTXD1_LUCKDRAW_ENDTIME");
		m.addAttribute("startTime", startTimeStr);
		m.addAttribute("endTime", endTimeStr);	
		m.addAttribute("startTimeLuckStr", startTimeLuckStr);
		m.addAttribute("endTimeLuckStr", endTimeLuckStr);
		//第二波时间
		String startTimeStr2 = paramConfigService.getValueByKey("ACTIVITY_BXTXD12_STARTTIME");
		String endTimeStr2 = paramConfigService.getValueByKey("ACTIVITY_BXTXD12_ENDTIME");
		String startTimeLuckStr2 = paramConfigService.getValueByKey("ACTIVITY_BXTXD12_LUCKDRAW_STARTTIME");
		String endTimeLuckStr2 = paramConfigService.getValueByKey("ACTIVITY_BXTXD12_LUCKDRAW_ENDTIME");
		
		String timeRemark = paramConfigService.getValueByKey("ACTIVITY_BXTXD1_TIMEREMARK");
		
		m.addAttribute("timeRemark", timeRemark);
		m.addAttribute("startTime2", startTimeStr2);
		m.addAttribute("endTime2", endTimeStr2);	
		m.addAttribute("startTimeLuckStr2", startTimeLuckStr2);
		m.addAttribute("endTimeLuckStr2", endTimeLuckStr2);
		
		return "activity.bxtxdpage";
	}
	
	/**
	 * 获取金额
	 * @return
	 */
	@RequestMapping("/getCurrentMoney.do")
	@ResponseBody
	public String getCurrentMoney(){
		
		String startTime = paramConfigService.getValueByKey("ACTIVITY_BXTXD1_STARTTIME");
		String endTime = paramConfigService.getValueByKey("ACTIVITY_BXTXD12_ENDTIME");
		String fee = paramConfigService.getValueByKey("ACTIVITY_BXTXD1_FEE");
		
		BigDecimal feeDecimal = new BigDecimal(fee);
		
		BigDecimal money = salesActivityFeeDAO.getPolicyFeeCount(startTime, endTime);
		if(money == null ){
			return "0.00";
		}
		int count = money.divide(new BigDecimal(1000)).setScale(0, RoundingMode.HALF_DOWN).intValue();
		BigDecimal amount = feeDecimal.multiply(new BigDecimal(count));
		amount = amount == null ? BigDecimal.ZERO : amount;
		BigDecimal lucked = activityBxtxRedEnvelopesDAO.getLuckdrawedTotal("ACTIVITYD1","1");
		lucked = lucked == null ? BigDecimal.ZERO : lucked;
		String mul = paramConfigService.getValueByKey("ACTIVITY_BXTXD1_MULTIPLE");
		BigDecimal curMoeny = amount.subtract(lucked.multiply(new BigDecimal(mul))).setScale(2, RoundingMode.HALF_DOWN);
		
		return curMoeny.toString();
		
	}
	
	@RequestMapping("/getLettoryNum.do")
	@ResponseBody
	public String getLettoryCount(){
		
		String userId = securityHolder.getAuthenticatedUserId();
		
		if(StringUtils.isBlank(userId)){
			return "-2";
		}
		
		try {
			Integer count = activityBxtxRedEnvelopesService.getWallteRedEnvelopeCount(userId, "ACTIVITYD1","1");
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

			String startTimeLuckStr = paramConfigService.getValueByKey("ACTIVITY_BXTXD1_LUCKDRAW_STARTTIME");
			String endTimeLuckStr = paramConfigService.getValueByKey("ACTIVITY_BXTXD1_LUCKDRAW_ENDTIME");
			
			String startTimeLuckStr2 = paramConfigService.getValueByKey("ACTIVITY_BXTXD12_LUCKDRAW_STARTTIME");
			String endTimeLuckStr2 = paramConfigService.getValueByKey("ACTIVITY_BXTXD12_LUCKDRAW_ENDTIME");
			
			Date now = new Date();
			Date st = DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, startTimeLuckStr);
			Date et = DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, endTimeLuckStr);
			Date st2 = DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, startTimeLuckStr2);
			Date et2 = DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, endTimeLuckStr2);
			
			if(!(st.getTime() < now.getTime() && et.getTime() > now.getTime()) && 
					!(st2.getTime() < now.getTime() && et2.getTime() > now.getTime())){
				result.put("result", "-2");
				return result;
			}
			
			result = activityBxtxRedEnvelopesService.updateLuckDraw(userId,"ACTIVITYD1", "BXTX","1");
			return result;
		} catch (Exception e) {
			logger.error("领取红包失败", e);
			result.put("result", "-2"); //未知原因
			return result;
		}
	}
	
	

	
	@RequestMapping("/getWallteLettoryNum.do")
	@ResponseBody
	public Map<String,String> getWallteLettoryCount(){
		
		String userId = securityHolder.getAuthenticatedUserId();
		
		if(StringUtils.isBlank(userId)){
			return null;
		}
		
		try {
			Integer redenvcount = activityBxtxRedEnvelopesService.getWallteRedEnvelopeCount(userId, null,"2");
			Integer feeCount = activityBxtxRedEnvelopesService.getWallteCountForPolicyActivityFee(userId);

			Map<String, String> result = new HashMap<>();
			result.put("BXTX", redenvcount+"");
			result.put("BD", feeCount + "");
			return result;
		} catch (Exception e) {
			logger.error("获取可领奖次数失败", e);
			return null;
		}
	}
	
	@RequestMapping("/luckdrawWallte.do")
	@ResponseBody
	public Map<String,String> luckdrawWallte(String type){
		
		Map<String,String> result = new HashMap<String, String>();
		
		String userId = securityHolder.getAuthenticatedUserId();
		
		if(StringUtils.isBlank(userId)){
			result.put("result", "-1");
			return result;
		}
		
		try {
			
			result = activityBxtxRedEnvelopesService.updateLuckDraw(userId,null, type,"2");
			return result;
		} catch (Exception e) {
			logger.error("领取红包失败", e);
			result.put("result", "-2"); //未知原因
			return result;
		}
	}
	
	@RequestMapping("/toActivity3Page.do")
	public String toActivitiD3Page(Model m){
		
		String namelists = paramConfigService.getValueByKey("ACTIVITY_BXTXD3_JSON");
		m.addAttribute("namelists", namelists);
		
		return "activity.bxtxd3page";
	}
}
