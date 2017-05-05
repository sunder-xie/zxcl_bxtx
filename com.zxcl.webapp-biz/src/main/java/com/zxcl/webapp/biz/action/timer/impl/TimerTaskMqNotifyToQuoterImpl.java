package com.zxcl.webapp.biz.action.timer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bxtx.intf.weixin.biz.service.WeiXinService;
import bxtx.intf.weixin.dto.SendTemplateMessageDTO;

import com.zxcl.webapp.biz.action.timer.TimerOrderStatusQueryService;
import com.zxcl.webapp.biz.action.timer.TimerTaskAction;
import com.zxcl.webapp.biz.util.DateUtils;
import com.zxcl.webapp.dto.ManualQuotaNotfiyToQuoterDTO;
import com.zxcl.webapp.integration.dao.ManualQuotationTaskDAO;
import com.zxcl.webapp.integration.dao.ManualQuoterInfoDAO;

@Service
public class TimerTaskMqNotifyToQuoterImpl implements TimerTaskAction {

	private static Logger logger = Logger.getLogger(TimerTaskMqNotifyToQuoterImpl.class);
	
	private static final int pageSize = 10;
	
	@Autowired
	private TimerOrderStatusQueryService timerOrderStatusQueryService;
	
	@Autowired
	private ManualQuotationTaskDAO manualQuotationTaskDAO;
	
	@Autowired
	private ManualQuoterInfoDAO manualQuoterInfoDAO;
	
	@Autowired
	private WeiXinService weiXinService;
	
	@Override
	public void execute() throws Exception {
		
		if(!timerOrderStatusQueryService.hasPermissionCronPremission()){
			logger.info("没有权限运行人工报价定时任务");
			return;
		}
		
		int index = 0;
		
		Date now = new Date();
		boolean isContinue = false;
		do{
			isContinue = false;
			
			List<ManualQuotaNotfiyToQuoterDTO> notifies =manualQuotationTaskDAO.getNotifyQuoters(index, index + pageSize);
			if(notifies != null && notifies.size() > 0){
				for(int i = 0;i<notifies.size();i++){
					if(!DateUtils.isInDate(now, notifies.get(i).getStartWorkTime(), notifies.get(i).getStopWorkTime())){
						logger.info("当前报价员的工作时间是：" + notifies.get(i).getStartWorkTime() + "-" + notifies.get(i).getStopWorkTime() + ",不进行通知" + ",quoterId:" + notifies.get(i).getQuoterId());
						continue;
					}
					if(now.getTime() - notifies.get(i).getLastSendTime().getTime() > 600 * 1000){
						//大于10分钟、通知
						List<SendTemplateMessageDTO> messages = new ArrayList<>();
						SendTemplateMessageDTO a = new SendTemplateMessageDTO();
						a.setToUser(notifies.get(i).getWxOpenId());
						a.setValue(new String[]{"亲爱的" + notifies.get(i).getQuoterName(),notifies.get(i).getCount()+"项","待办事项",DateUtils.getDateToStr(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS),"请您尽快处理！"});
						messages.add(a);
						String result = weiXinService.sendMessageByTemplate("BXTX_MQ_2WORKER", messages);
						if(StringUtils.isNotBlank(result)){
							logger.info("人工报价通知业务员失败,错误信息：" + result + ",quoterId:" + notifies.get(i).getQuoterId() + ",openId:" + notifies.get(i).getWxOpenId());
						}else{
							//通知成功，更新最后通知时间
							try {
								int row = manualQuoterInfoDAO.updateLastSendTime(notifies.get(i));
								if(row < 1){
									logger.info("人工报价通知业务员成功,但更新最后发送时间失败：" + result + ",quoterId:" + notifies.get(i).getQuoterId() + ",openId:" + notifies.get(i).getWxOpenId());
								}
							} catch (Exception e) {
								logger.error("人工报价通知业务成功，但更新最后发送时间失败" + result + ",quoterId:" + notifies.get(i).getQuoterId() + ",openId:" + notifies.get(i).getWxOpenId(), e );
							}
						}
						
					}
				}
			}
			
			if(notifies != null && notifies.size() == pageSize){
				//判断每次的查询总数，如果都等于200条，表示可能还有下一页，否则就没有了
				isContinue = true;
				index += pageSize;
			}
			
		}while(isContinue);
		
	}
	

}
