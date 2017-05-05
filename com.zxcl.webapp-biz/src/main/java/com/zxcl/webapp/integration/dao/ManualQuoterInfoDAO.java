package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.ManualQuotaNotfiyToQuoterDTO;

/**
 * 人工报价员信息
 */
public interface ManualQuoterInfoDAO {

	/**
	 * 更新最后报价时间
	 * @param manualQuotaNotfiyToQuoterDTO
	 * @return
	 */
	public int updateLastSendTime(ManualQuotaNotfiyToQuoterDTO manualQuotaNotfiyToQuoterDTO);
	
	/**
	 * 根据teamId和insId判断是否需要发送通知的到业务员.
	 * @param teamId
	 * @param insId
	 * @return
	 */
	public ManualQuotaNotfiyToQuoterDTO getNotifyQuoter(@Param("teamId")String teamId,@Param("insId")String insId);
}
