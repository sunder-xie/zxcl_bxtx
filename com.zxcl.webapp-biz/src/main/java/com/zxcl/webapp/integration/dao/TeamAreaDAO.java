package com.zxcl.webapp.integration.dao;

/**
 * 团队对应的区域
 * @author xiaoxi
 *
 */
public interface TeamAreaDAO {

	/**
	 * 根据团队编码获取一个有效的地区编码
	 * @param teamId
	 * @return
	 */
	public String getAreaCodeByTeamId(String teamId);
	
	/**
	 * 根据用户账号获取一个有效的地区编码
	 * @param teamId
	 * @return
	 */
	public String getAreaCodeByUserId(String userId);
}
