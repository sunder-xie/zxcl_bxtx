package com.zxcl.webapp.biz.service.impl.activity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MessageService;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.biz.service.WalletBillService;
import com.zxcl.webapp.biz.service.activity.ActivityBxtxCService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.MessageDTO;
import com.zxcl.webapp.dto.MessageTargetDTOWithBLOBs;
import com.zxcl.webapp.dto.activity.ActivityBxtxBDrawListDTO;
import com.zxcl.webapp.dto.activity.ActivityBxtxBWinningListDTO;
import com.zxcl.webapp.dto.activity.ActivityBxtxCLettriedDetailed;
import com.zxcl.webapp.dto.activity.ActivityBxtxCNameListDTO;
import com.zxcl.webapp.integration.dao.activity.ActivityBxtxBDAO;
import com.zxcl.webapp.integration.dao.activity.ActivityBxtxCDAO;
import com.zxcl.webapp.util.RedEnvelope2;
import com.zxcl.webapp.util.constants.MessageConstant;

@Service
public class ActivityBxtxCServiceImpl implements ActivityBxtxCService {

	private static Logger logger = Logger.getLogger(ActivityBxtxCServiceImpl.class);
	
	@Autowired
	private ActivityBxtxCDAO activityBxtxCDAO;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ParamConfigService paramConfigService;
	
	@Autowired
	private WalletBillService walletBillService;
	
	@Override
	public Integer getLotteryNumber(String userId) {
		
		ActivityBxtxCNameListDTO c = activityBxtxCDAO.getActivityBxtxCNamingByUserId(userId, paramConfigService.getValueByKey("ACTIVITY_BXTXC2_SEQID"));
		if(c == null){
			return -1;
		}
		
		return c.getLotteryNum() - c.getLotteriedNum();
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String,String> updateLuckdraw(String userId) throws BusinessServiceException, Exception {
		
		Map<String, String> result = new HashMap<String, String>();
		
		String startTimeStr = paramConfigService.getValueByKey("ACTIVITY_BXTXC2_LUCKDRAW_STARTTIME");
		String endTimeStr = paramConfigService.getValueByKey("ACTIVITY_BXTXC2_LUCKDRAW_ENDTIME");
		if(StringUtils.isBlank(startTimeStr) || StringUtils.isBlank(endTimeStr)){
			result.put("result", "-2");
			logger.info("保行天下活动3尚未配置抽奖开始或结束时间.");
			return result;
		}
		
		Date startTime = DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, startTimeStr);
		Date endTime = DateUtil.stringToDate(DateUtil.DATETIME24_PATTERN_LINE, endTimeStr);
		Date now = new Date();
		if(now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()){
			result.put("result", "-3");
			logger.info("保行天下活动3还未开始抽奖，请稍等...");
			return result;
		}
		
		/**
		 * 1. 获取可抽奖次数
		 * 2. 获取抽奖名单是否有自己
		 * 	2.1 如果有自己，更新会中奖的名字数据的状态
		 * 3.插入抽奖记录
		 */
		Integer count = getLotteryNumber(userId);
		
		
		
		if(count != null && count.intValue() == -1){
			logger.info("用户编号：" + userId + " 没有抽奖机会.");
			result.put("result", "-10");
			return result;
		}
		
		if(count == null || count.intValue() < 1){
			logger.info("用户编号：" + userId + " 抽奖次数已用完.");
			result.put("result", "-11");
			return result;
		}
		
		ActivityBxtxCNameListDTO c = activityBxtxCDAO.getActivityBxtxCNamingByUserId(userId, paramConfigService.getValueByKey("ACTIVITY_BXTXC2_SEQID"));

		BigDecimal total = c.getLotteryTotal().subtract(c.getLotteriedTotal()).setScale(0, BigDecimal.ROUND_HALF_UP);
		
		RedEnvelope2 e = new RedEnvelope2(
				total, //奖金总金额 
				count, //预计有多少抽奖次数,
				new BigDecimal(1), //最小金额,
				total //最大金额
				); 
		
		BigDecimal money = e.nextMoney();
		
		logger.info("保行天下活动C，用户抽奖，编号：" + userId + ",可领取总金额：" + c.getLotteryTotal() + ",已领取" + c.getLotteriedTotal() + ",本次领取：" + money + ",已领取次数：" + c.getLotteriedNum());
		
		ActivityBxtxCLettriedDetailed detail = new ActivityBxtxCLettriedDetailed();
		detail.setCrtCde(userId);
		detail.setCrtTm(new Date());
		detail.setLetteriedMoney(money);
		detail.setSeqId(DateUtil.dateToString(DateUtil.YYYYMMDDHHMMSSSSS, new Date())+(int)(Math.random()*1000));
		detail.setUpdCde(userId);
		detail.setUpdTm(new Date());
		detail.setUserId(userId);
		
		activityBxtxCDAO.insertActivityBxtxCLotteriedDetailed(detail);
		
		//更新抽奖记录
		ActivityBxtxCNameListDTO p = new ActivityBxtxCNameListDTO();
		BeanUtils.copyProperties(c, p);
		p.setUpdTm(new Date());
		p.setUpdCde(userId);
		p.setLotteriedTotal(money);
		
		Integer row = activityBxtxCDAO.updateActivityBxtxCNaming(p);
		
		if(row != null && row.intValue() > 0){
//			walletBillService.addWalletActivityBxtxCOpenRedPickets(detail.getSeqId(), userId, money);
		}else {
			//更新失败，
			throw new BusinessServiceException("数据更新失败，回退事务");
		}
		
		result.put("result", "1");
		result.put("money", money.toString());
		result.put("num", getLotteryNumber(userId)+"");
		return result;
	}

	@Override
	public List<ActivityBxtxCNameListDTO> getActivityBxtxBWinnings() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
