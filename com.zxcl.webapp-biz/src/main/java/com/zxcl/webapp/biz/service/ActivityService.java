package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.dto.ActivityDTO;
import com.zxcl.webapp.dto.MicroDTO;

/**
 * 活动service
 * @author xiaoxi
 *
 */
public interface ActivityService {

	/**
	 * 根据小微账号信息获取活动信息<br />
	 * 如果未登陆获取保行天下的活动,如已登陆，判断用户类型，根据是否代理获取活动内容<br/>
	 * @param microDTO
	 * @return
	 */
	public List<ActivityDTO> getOngoingActivity(MicroDTO microDTO);
	
	/**
	 * 根据小微账号信息、分页号获取历史记录.
	 * @param microDTO
	 * @param pageNo
	 * @return
	 */
	public List<ActivityDTO> getHistoryActivity(MicroDTO microDTO,Integer pageNo);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @param microDTO
	 * @return
	 */
	public ActivityDTO getActivityDetailById(Integer id,MicroDTO microDTO);
	
}
