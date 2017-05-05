package com.zxcl.webapp.biz.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.ActivityService;
import com.zxcl.webapp.dto.ActivityDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.integration.dao.ActivityDAO;

@Service
public class ActivityServiceImpl implements ActivityService{

	private static Logger logger = Logger.getLogger(ActivityServiceImpl.class);

	@Autowired
	private ActivityDAO activityDAO;

	@Override
	public List<ActivityDTO> getOngoingActivity(MicroDTO microDTO) {
		
		Integer type = null;
		String agentId = null;
		if(microDTO == null){
			type = 2;
		}
		if(microDTO != null && microDTO.getAgency() != null && 
				StringUtils.isNotBlank(microDTO.getAgency().getAgent_id())){
			type = 1;
			agentId = microDTO.getAgency().getAgent_id();
		}
		
		return activityDAO.getOngoingActivity(type, agentId, new Date());
	}

	@Override
	public List<ActivityDTO> getHistoryActivity(MicroDTO microDTO,
			Integer pageNo) {
		Integer type = null;
		String agentId = null;
		if(microDTO == null){
			type = 2;
		}
		if(microDTO != null && microDTO.getAgency() != null && 
				StringUtils.isNotBlank(microDTO.getAgency().getAgent_id())){
			type = 1;
			agentId = microDTO.getAgency().getAgent_id();
		}
		
		return activityDAO.getHistoryActivitys(type, agentId, 10, (pageNo - 1 ) * 10);
		
	}

	@Override
	public ActivityDTO getActivityDetailById(Integer id,MicroDTO microDTO) {
		
		if(id == null || id < 1){
			return null;
		}
		
		
		ActivityDTO  param = new ActivityDTO();
		param.setId(id);
		
		if(microDTO == null){
			param.setActivityType(2);
		}
		if(microDTO != null && microDTO.getAgency() != null && 
				StringUtils.isNotBlank(microDTO.getAgency().getAgent_id())){
			
			param.setActivityType(1);
			param.setAgentId(microDTO.getAgency().getAgent_id());
		}
		
		ActivityDTO dto = activityDAO.getActivityDetailById(param);  
		

//		ActivityDTO dto =  activityDAO.getActivityDetailById(id);;
//		if(dto == null){
//			return null; 
//		}
//		//如果是保行天下的活动，就直接返回。因为保行天下的活动是整站的
//		if(dto.getActivityType() != null && dto.getActivityType().intValue() == 2){
//			return dto;
//		}
//		
//		Integer type = null;
//		String agentId = null;
//		if(microDTO == null){
//			type = 2;
//		}
//		if(microDTO != null && microDTO.getAgency() != null && 
//				StringUtils.isNotBlank(microDTO.getAgency().getAgent_id())){
//			type = 1;
//			agentId = microDTO.getAgency().getAgent_id();
//		}
//		
//		
//		
//		//活动类型不匹配，不返回对象
//		if(dto.getActivityType() != null &&
//				dto.getActivityType().intValue() != type){
//			return null;
//		}
//		//活动的代理ID不匹配，不返回
//		if(type.intValue() == 1 && !StringUtils.equals(agentId, dto.getAgentId())){
//			return null;
//		}
//		
		
		return dto;
	}
	
	
}

