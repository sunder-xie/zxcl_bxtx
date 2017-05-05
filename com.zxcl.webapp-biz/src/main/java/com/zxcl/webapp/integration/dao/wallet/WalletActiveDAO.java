package com.zxcl.webapp.integration.dao.wallet;

import org.apache.ibatis.annotations.Param;


import com.zxcl.webapp.dto.wallet.WalletActiveDTO;


public interface WalletActiveDAO {
    int deleteByPrimaryKey(String activeId);

    int insertSelective(WalletActiveDTO record);

    WalletActiveDTO selectByPrimaryKey(String activeId);

    int updateByPrimaryKeySelective(WalletActiveDTO record);
    /**
	 * 根据代理公司ID和日期得到活动信息
	 * @param agentId 代理公司ID
	 * @param date 日期（YYYY-MM-DD）
	 * @return
	 * @throws Exception
	 */
	public WalletActiveDTO getActivityInfoByAgentIdAndDate(@Param("agentId")String agentId,@Param("date")String date) throws Exception;

	void updateActiveWithAct04();
}