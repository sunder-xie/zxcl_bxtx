package com.zxcl.webapp.integration.dao.activity;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.activity.ActivityBxtxCLettriedDetailed;
import com.zxcl.webapp.dto.activity.ActivityBxtxCNameListDTO;
import com.zxcl.webapp.dto.activity.ActivityBxtxCOrderDTO;

/**
 * 保行天下活动C-2016双11活动
 * @author xiaoxi
 *
 */
public interface ActivityBxtxCDAO {

	/**
	 * 根据用户编号获取中奖名单信息
	 * @param userId
	 * @return
	 */
	public ActivityBxtxCNameListDTO getActivityBxtxCNamingByUserId(@Param("userId")String userId,@Param("activitySeqId")String activitySeqId);
	
	/**
	 * 更新中奖名单信息.
	 * @param activityBxtxCNameListDTO
	 * @return
	 */
	public int updateActivityBxtxCNaming(ActivityBxtxCNameListDTO activityBxtxCNameListDTO);
	
	/**
	 * 添加中奖明细
	 * @param activityBxtxCLettriedDetailed
	 * @return
	 */
	public int insertActivityBxtxCLotteriedDetailed(ActivityBxtxCLettriedDetailed activityBxtxCLettriedDetailed);
	
	/**
	 * 获取已抽金额
	 * @return
	 */
	public BigDecimal getActivityBxtxCLotteriedTotal();
	
	/**
	 * 获取保行天下活动C的中奖前10的名单.
	 * @return
	 */
	public List<ActivityBxtxCOrderDTO> getActivityBxtxCOrders();
}
