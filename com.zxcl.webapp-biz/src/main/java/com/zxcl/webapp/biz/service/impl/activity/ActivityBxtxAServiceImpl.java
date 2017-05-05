package com.zxcl.webapp.biz.service.impl.activity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meyacom.fw.um.security.SecurityHolder;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.biz.service.WalletBillService;
import com.zxcl.webapp.biz.service.activity.ActivityBxtxAService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.activity.ActivityBxtxADTO;
import com.zxcl.webapp.integration.dao.activity.ActivityBxtxADAO;

/**
 * 保行天下活动的实现 <br />
 * 保行天下每周一20：00-24：00发红包活动，活动时间2016-05-16至2016-07-01
 * @author xiaoxi
 *
 */
@Service
public class ActivityBxtxAServiceImpl implements ActivityBxtxAService{

	/**
	 * 活动开放开始时间
	 */
	private static final String ACTIVITYBXTXA_ACTIVITYSTARTTIME = "BXTX_ACTIVITYBXTXA_ACTIVITYSTARTTIME";
	/**
	 * 活动开放结束时间
	 */
	private static final String ACTIVITYBXTXA_ACTIVITYENDTIME = "BXTX_ACTIVITYBXTXA_ACTIVITYENDTIME";
	/**
	 * 允许抽奖的时间（周几？？）
	 */
	private static final String ACTIVITYBXTXA_WEEKDAY = "BXTX_ACTIVITYBXTXA_WEEKDAY";
	/**
	 * 允许抽奖的开始时间()
	 */
	private static final String ACTIVITYBXTXA_STARTTIME = "BXTX_ACTIVITYBXTXA_STARTTIME";
	/**
	 * 允许抽奖的结束时间
	 */
	private static final String ACTIVITYBXTXA_ENDTIME = "BXTX_ACTIVITYBXTXA_ENDTIME";
	
	private static Logger logger = Logger.getLogger(ActivityBxtxAServiceImpl.class);
	
	@Autowired
	private SecurityHolder securityHolder;
	
	@Autowired
	private ActivityBxtxADAO activityBxtxADAO;

	@Autowired
	private WalletBillService walletBillService;
	
	@Autowired
	private ParamConfigService paramConfigService;
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public Map<String, Object> updateOpenRedPacket()
			throws BusinessServiceException {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		String activityStartTime = paramConfigService.getValueByKey(ACTIVITYBXTXA_ACTIVITYSTARTTIME);
		String activityEndTime = paramConfigService.getValueByKey(ACTIVITYBXTXA_ACTIVITYENDTIME);
		String weekday  = paramConfigService.getValueByKey(ACTIVITYBXTXA_WEEKDAY);
		String startTimeStr = paramConfigService.getValueByKey(ACTIVITYBXTXA_STARTTIME);
		String endTimeStr = paramConfigService.getValueByKey(ACTIVITYBXTXA_ENDTIME);
		
		if(StringUtils.isBlank(activityStartTime) || StringUtils.isBlank(activityEndTime) || 
				StringUtils.isBlank(weekday) || StringUtils.isBlank(startTimeStr) || 
				StringUtils.isBlank(endTimeStr) ){
			logger.info("保行天下活动A参数配置不齐全.");
			result.put("status", -1);
			return result;
		}
		
		Date now = new Date();
		
		try {
			Date st = DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, activityStartTime);
			Date et = DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, activityEndTime);
			
			
			if(now.before(st)){
				result.put("status", -10);
				return result;
			}
			if(now.after(et)){
				result.put("status", -11);
				return result;
			}
		} catch (Exception e) {
			logger.error("保行天下活动A，活动有效时间转换失败.", e);
			result.put("status", -12); //因为转化时间失败，所以直接返回...
			return result;
		}
		
		
		if(weekday.equals(DateUtil.getWeekOfDate(now))){
			try {
				Date startTime = DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, DateUtil.dateToString("yyyy-MM-dd", now) + " 00:00:00" );
				Date endTime =  DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, DateUtil.dateToString("yyyy-MM-dd", now) + " " + startTimeStr);
				
				if(startTime.getTime() < now.getTime() && endTime.getTime() > now.getTime()){
					result.put("status", -13); //时间在固定的周一的00:00-20:00之间，不能抽奖.
					return result;
				}
			} catch (Exception e) {
				logger.error("转化时间错误", e);
				result.put("status", -14); //因为转化时间失败，所以直接返回...
				return result;
			}
			
		}
		
//		try {
//			Date startTime = DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, DateUtil.dateToString("yyyy-MM-dd", now) + " " + startTimeStr);
//			Date endTime =  DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, DateUtil.dateToString("yyyy-MM-dd", now) + " " + endTimeStr);
//		
//			if(now.before(startTime) || now.after(endTime)){
//				result.put("status", -13); //不在20:00 - 23:59之间
//				return result;
//			}
//		} catch (Exception e) {
//			logger.error("转化时间错误", e);
//			result.put("status", -14); //因为转化时间失败，所以直接返回...
//			return result;
//		}
		
		List<ActivityBxtxADTO> list = activityBxtxADAO.getAllRedPackets(securityHolder.getAuthenticatedUserId());
		
		if(list == null || list.size() < 1){
			result.put("status", -15);//下周继续努力.
			return result; 
		}
		
		boolean isHas = false; //判断是否有有效拆开红包数据.
		ActivityBxtxADTO temp = null;
		for(ActivityBxtxADTO activityBxtxADTO : list){
			if(activityBxtxADTO.getStatus() != null &&
					activityBxtxADTO.getStatus().intValue() == 1){
				isHas = true;
				temp = activityBxtxADTO;
				break;
			}
		}
		
		if(!isHas){
			//没有数据正常情况下是已经抽奖.s
			result.put("status", -16);//您已抽奖.下周继续努力.
			return result; 
		}
		
		//更新时间,更新人
		temp.setUpdCde(securityHolder.getAuthenticatedUserId());
		temp.setUpdTm(new Date());
		
		Integer rows = activityBxtxADAO.updateOpenRedPacket(temp);
		if(rows < 1){
			logger.info("奖励已被领取..id:" + temp.getId() + ", userid:" + temp.getUserId() );
			result.put("status", -17);//红包已被领取..
			return result; 
		}
		
		result.put("prizeType", temp.getPrizeType());
		
		if(temp.getPrizeType() != null && temp.getPrizeType().intValue() == 1){
			//现金
			logger.info("开始发放红包奖励.id:" + temp.getId() + ",userId:" + temp.getUserId());
//			walletBillService.addWalletActivityBxtxAOpenRedPickets(temp.getId(), temp.getUserId(), new BigDecimal(temp.getPrize()));
			result.put("amount", new BigDecimal(temp.getPrize()).setScale(2, RoundingMode.HALF_DOWN));
			
			logger.info("已完成发放红包奖励.id:" + temp.getId() + ",userId:" + temp.getUserId());
		}
		
		logger.info("已完成发放保行天下A活动奖励.id:" + temp.getId() + ",userId:" + temp.getUserId());
		
		result.put("status", 1);
		return result; 
	}
	
	 
}
