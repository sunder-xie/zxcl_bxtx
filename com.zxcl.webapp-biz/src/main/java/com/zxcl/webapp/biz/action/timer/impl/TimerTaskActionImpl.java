package com.zxcl.webapp.biz.action.timer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zxcl.webapp.biz.action.timer.TimerOrderStatusQueryService;
import com.zxcl.webapp.biz.action.timer.TimerTaskAction;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.util.DateUtils;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.integration.dao.OrderDAO;

@Service
public class TimerTaskActionImpl implements TimerTaskAction{
	
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private TimerOrderStatusQueryService timerOrderStatusQueryService;
	
	@Autowired
	private QuotaService quotaService;
	
	@Override
	public void execute() {
		if(!timerOrderStatusQueryService.hasPermissionCronPremission()){
			return;
		}
		int pageNo = 1;
		int pageSize = 100;
		Date minTime = DateUtils.getAfterDays(new Date(),-11);
		List<OrderDTO> orderList = new ArrayList<OrderDTO>();
		
		do {
			try {
				
				//分页获取10天内的订单
				orderList = orderService.getAllByStage(minTime,(pageNo -1 ) * pageSize,pageSize);
				
				if(orderList == null || orderList.size() == 0){
					return ;
				}
				logger.info("定时刷新订单信息,查询结果数：" + orderList.size());
			} catch (Exception e) {
				logger.error("定时更新订单状态出错", e);
				return ;
			}
			
			for (OrderDTO orderItemDTO : orderList) {

				if(executeBefore(orderItemDTO)){
					timerOrderStatusQueryService.doExecute(orderItemDTO);
				}
			}
			 
			//分页+1，继续更新下一页订单.
			pageNo += 1;
		}while(orderList != null && orderList.size() > 0);
	}

	/**
	 * 这里面的人保爬虫,平安爬虫状态为4的执行前要校验下
	 * 太平人工报价处理
	 * @param orderItemDTO
	 * @return
	 */
	protected boolean executeBefore(OrderDTO orderItemDTO) {
		try {
			
			if("4".equals(orderItemDTO.getQueryStage()) && orderItemDTO.getInsurance().getInsId().equals("TPIC")){
				QuotaDTO quotaDTO = quotaService.getByQuotaId(orderItemDTO.getQuota().getQuotaId());
				logger.info("quotaDTO:"+JSON.toJSONString(quotaDTO));
				if(null == quotaDTO || !"M".equals(quotaDTO.getQuotaType())){
					logger.info("该太平非人工报价任务");
					return false;
				}
			}
			
			if("4".equals(orderItemDTO.getQueryStage()) && 
						(orderItemDTO.getInsurance().getInsId().equals("PICCHTTP") 
							|| orderItemDTO.getInsurance().getInsId().equals("PINGANHTTP")
							|| orderItemDTO.getInsurance().getInsId().equals("TPIC"))){
				String remarkStatus = orderDAO.findRemarkStatusByPrimaryKey(orderItemDTO.getInquiry().getInquiryId(), orderItemDTO.getQuota().getQuotaId(), orderItemDTO.getInsurance().getInsId());
				logger.info("remarkStatus:" + remarkStatus);
				if("1".equals(remarkStatus)){//1为已支付
					logger.info("报价单"+orderItemDTO.getQuota().getQuotaId()+"在营销系统查询到为已支付");
					return true;
				}
				else{
					return false;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
}
