package com.zxcl.webapp.biz.action.timer.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.action.timer.TimerOrderStatusQueryService;
import com.zxcl.webapp.biz.action.timer.TimerTaskAction;
import com.zxcl.webapp.biz.util.DateUtils;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.integration.dao.OrderDAO;

@Service
public class TimerTaskActionImpl4 implements TimerTaskAction{
	
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private TimerOrderStatusQueryService timerOrderStatusQueryService;
	
	@Override
	public void execute() {
		if(!timerOrderStatusQueryService.hasPermissionCronPremission()){
			return;
		}
		int pageNo = 1;
		int pageSize = 100;
		Date minTime = DateUtils.getAfterDays(new Date(),-11);
		List<OrderDTO> orderList = null;
		logger.info("定时刷新http爬虫状态为4的订单信息");
		do {
			try {
				
				//分页获取10天内的订单
				orderList = orderDAO.getAllHttpByStage(minTime,(pageNo -1 ) * pageSize,pageSize);
				if(orderList == null || orderList.size() == 0){
					return ;
				}
				logger.info("定时刷新订单信息,查询结果数：" + orderList.size());
			} catch (Exception e) {
				logger.error("定时更新订单状态出错", e);
				return ;
			}
			
			for (OrderDTO orderItemDTO : orderList) {
				timerOrderStatusQueryService.doExecute(orderItemDTO);
			}
			 
			//分页+1，继续更新下一页订单.
			pageNo += 1;
		}while(orderList != null && orderList.size() > 0);
	}
}
