package com.zxcl.webapp.integration.dao.wallet;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.wallet.WalletBankDTO;


public interface WalletBankDAO {
    int deleteByPrimaryKey(String id);

    int insertSelective(WalletBankDTO record);
    
    WalletBankDTO selectByPrimaryKey(String id);
    
    List<WalletBankDTO> selectByUserId(String userId);
    
    List<WalletBankDTO> selectByUserId2(@Param("userId")String userId, @Param("createUserId")String createUserId);

    int updateByPrimaryKeySelective(WalletBankDTO record);

	int selectWalletBankCountByUserId(String userId);

	int selectBankCountByBankNameAndCarNo(@Param("bankName") String bankName, @Param("cardNo") String cardNo);

	int selectAgentUserCardCount(@Param("userId") String userId, @Param("agentUserId") String agentUserId);

	int hisToryAddCount(String userId);

}