package com.zxcl.webapp.integration.dao.wallet;

import com.zxcl.webapp.dto.wallet.WalletTransferDTO;

public interface WalletTransferDAO {
    int deleteByPrimaryKey(String transId);

    int insert(WalletTransferDTO record);

    int insertSelective(WalletTransferDTO record);

    WalletTransferDTO selectByPrimaryKey(String transId);

    int updateByPrimaryKeySelective(WalletTransferDTO record);

    int updateByPrimaryKey(WalletTransferDTO record);
}