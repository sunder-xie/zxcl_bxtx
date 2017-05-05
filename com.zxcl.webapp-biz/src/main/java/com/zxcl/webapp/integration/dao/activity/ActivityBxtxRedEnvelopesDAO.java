package com.zxcl.webapp.integration.dao.activity;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.activity.ActivityBxtxRedEnvelopesDTO;

/**
 * 保行天下红包活动DAO
 * @author xiaoxi
 *
 */
public interface ActivityBxtxRedEnvelopesDAO {

	/**
	 * 获取红包记录表的可抽奖才次数 ，
	 * @param userId
	 * @param activityBatchNo 如果有活动批次号，则需要根据活动批次号查询
	 * @return
	 */
	public int getTotalNumberOfDraws(@Param("userId")String userId,
			@Param("activityBatchNo")String activityBatchNo,@Param("openType")String openType);
	
	/**
	 * 获取红包记录表的可抽奖才次数 ，
	 * @param userId
	 * @param activityBatchNo 如果有活动批次号，则需要根据活动批次号查询
	 * @return
	 */
	public int getTotalNumber(@Param("userId")String userId,
			@Param("activityBatchNo")String activityBatchNo,@Param("openType")String openType);
	
	/**
	 * 获取某个批次的活动抽奖总金额.
	 * @param activityBatchNo
	 * @param openType
	 * @return
	 */
	public BigDecimal getLuckdrawedTotal(@Param("activityBatchNo")String activityBatchNo,@Param("openType")String openType);
	
	/**
	 * 获取一条红包记录
	 * @param userId
	 * @param activityBatchNo
	 * @return
	 */
	public ActivityBxtxRedEnvelopesDTO getRedEnvelope(@Param("userId")String userId,
			@Param("activityBatchNo")String activityBatchNo,@Param("openType")String openType);
	
	/**
	 * 更新红包状态.
	 * @param redEnvelopesDTO
	 * @return
	 */
	public Integer updateRedEnvelope(ActivityBxtxRedEnvelopesDTO redEnvelopesDTO) throws Exception;
	
}
