package com.zxcl.webapp.integration.dao.wallet;

import com.zxcl.webapp.dto.wallet.WalletCheckDTO;
import com.zxcl.webapp.dto.wallet.WalletCheckDTOKey;

public interface WalletCheckDAO {
    int deleteByPrimaryKey(WalletCheckDTOKey key);

    int insert(WalletCheckDTO record);

    int insertSelective(WalletCheckDTO record);

    WalletCheckDTO selectByPrimaryKey(WalletCheckDTOKey key);

    int updateByPrimaryKeySelective(WalletCheckDTO record);

    int updateByPrimaryKey(WalletCheckDTO record);
}