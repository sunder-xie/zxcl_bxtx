package com.zxcl.webapp.integration.dao.wallet;

import java.math.BigDecimal;
import java.util.List;

import com.zxcl.webapp.dto.wallet.WalletDTO;


public interface WalletDAO {
//    int deleteByPrimaryKey(String walletId);
    
    WalletDTO selectByUserId(String userId);

//    int insertSelective(WalletDTO record);

    WalletDTO selectByPrimaryKey(String walletId);

//    int updateByPrimaryKeySelective(WalletDTO record);
    
//    int updateByPrimaryKeySelectiveBusi(WalletDTO record);
    
	List<WalletDTO> selectAllWallet();

	BigDecimal selectTotalIncomeAndStay(String userId);

	BigDecimal selectTotalBillInAmountByUserId(String userId);
}