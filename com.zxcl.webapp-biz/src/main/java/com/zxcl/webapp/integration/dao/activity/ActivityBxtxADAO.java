package com.zxcl.webapp.integration.dao.activity;

import java.util.List;

import com.zxcl.webapp.dto.activity.ActivityBxtxADTO;

public interface ActivityBxtxADAO {

	/**
	 * 获取用户所有的红包信息，包含未抽奖、已抽奖等。。。
	 * @param userId
	 * @return
	 */
	public List<ActivityBxtxADTO> getAllRedPackets(String userId);
	
	/**
	 * 用户拆分一个红包.
	 * @param id
	 * @param userId
	 * @return
	 */
	public Integer updateOpenRedPacket(ActivityBxtxADTO activityBxtxADTO);
}
