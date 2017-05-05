package com.zxcl.webapp.biz.service.activity;

import java.util.Map;

/**
 * 保行天下活动201612月活动。
 * 爆点与导入的红包的数据的统计功能
 * 抢保行天下活动的红包
 * 在钱包界面领取已抢到的红包.
 * 
 * 目前期望他能完成更多的事，至于能不能完成更多的事，得看后面的活动变化咋样
 * @author xiaoxi
 *
 */
public interface ActivityBxtxRedEnvelopesService {

	
	/**
	 * 通过USER_ID在红包记录表查询总的可抽奖次数
	 * @param userId
	 * @return
	 */
	public Integer getWallteCountForRedEnvelope(String userId,String openType);
	
	/**
	 * 通过USER_ID在爆点表里面查询可用的抽奖总次数
	 * @param userId
	 * @return
	 */
	public Integer getWallteCountForPolicyActivityFee(String userId);
	
	/**
	 * 根据批次号获取可用的总抽奖次数
	 * @param userId
	 * @param batchNo
	 * @return
	 */
	public Integer getWallteRedEnvelopeCount(String userId,String batchNo,String openType);
	
	/**
	 * 抢红包。
	 * @param userId
	 * @param type 这里的类型主要是用于区别是打开的红包记录的还是爆点记录的
	 * @return
	 */
	public Map<String, String> updateLuckDraw(String userId,String batchNo,String type,String openType) throws Exception;
}
