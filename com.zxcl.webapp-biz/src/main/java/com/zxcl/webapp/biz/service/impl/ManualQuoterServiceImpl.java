package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bxtx.intf.weixin.biz.service.WeiXinService;
import bxtx.intf.weixin.dto.SendTemplateMessageDTO;

import com.zxcl.webapp.biz.service.ManualQuoterService;
import com.zxcl.webapp.biz.util.DateUtils;
import com.zxcl.webapp.dto.ManualQuotaNotfiyToQuoterDTO;
import com.zxcl.webapp.integration.dao.ManualQuotationTaskDAO;
import com.zxcl.webapp.integration.dao.ManualQuoterInfoDAO;

/**
 * 人工报价通知报价员实现
 * @author xiaoxi
 *
 */
@Service
public class ManualQuoterServiceImpl implements ManualQuoterService {

	private static Logger logger = Logger.getLogger(ManualQuoterServiceImpl.class);
	
	@Autowired
	private ManualQuoterInfoDAO manualQuoterInfoDAO;
	
	@Autowired
	private ManualQuotationTaskDAO manualQuotationTaskDAO;
	
	@Autowired
	private WeiXinService weiXinService;
	
	@Override
	public void notifyQuoter(String teamId, String insId) {
		Date now = new Date();
		ManualQuotaNotfiyToQuoterDTO dto = manualQuoterInfoDAO.getNotifyQuoter(teamId, insId);
		if(dto == null ){
			return ;
		}
		if(!DateUtils.isInDate(now, dto.getStartWorkTime(), dto.getStopWorkTime())){
			logger.info("当前报价员的工作时间是：" + dto.getStartWorkTime() + "-" + dto.getStopWorkTime() + ",不进行通知" + ",quoterId:" + dto.getQuoterId());
		}
		
		if(dto != null && now.getTime() - dto.getLastSendTime().getTime() > 600 * 1000){
			//大于10分钟、通知
			List<SendTemplateMessageDTO> messages = new ArrayList<>();
			SendTemplateMessageDTO a = new SendTemplateMessageDTO();
			a.setToUser(dto.getWxOpenId());
			a.setValue(new String[]{"亲爱的" + dto.getQuoterName(),dto.getCount()+"项","待办事项",DateUtils.getDateToStr(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS),"请您尽快处理！"});
			messages.add(a);
			String result = weiXinService.sendMessageByTemplate("BXTX_MQ_2WORKER", messages);
			if(StringUtils.isNotBlank(result)){
				logger.info("人工报价通知业务员失败,错误信息：" + result + ",quoterId:" + dto.getQuoterId() + ",openId:" + dto.getWxOpenId());
			}else{
				//通知成功，更新最后通知时间
				try {
					int row = manualQuoterInfoDAO.updateLastSendTime(dto);
					if(row < 1){
						logger.info("人工报价通知业务员成功,但更新最后发送时间失败：" + result + ",quoterId:" + dto.getQuoterId() + ",openId:" + dto.getWxOpenId());
					}
				} catch (Exception e) {
					logger.error("人工报价通知业务成功，但更新最后发送时间失败" + result + ",quoterId:" + dto.getQuoterId() + ",openId:" + dto.getWxOpenId(), e );
				}
			}
		}
	}

}
