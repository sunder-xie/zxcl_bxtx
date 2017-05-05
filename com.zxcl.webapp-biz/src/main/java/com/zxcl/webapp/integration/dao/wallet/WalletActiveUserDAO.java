package com.zxcl.webapp.integration.dao.wallet;

import com.zxcl.webapp.dto.wallet.WalletActiveUserDTO;
import com.zxcl.webapp.dto.wallet.WalletActiveUserDTOKey;

public interface WalletActiveUserDAO {
    int deleteByPrimaryKey(WalletActiveUserDTOKey key);

    int insert(WalletActiveUserDTO record);

    int insertSelective(WalletActiveUserDTO record);

    WalletActiveUserDTO selectByPrimaryKey(WalletActiveUserDTOKey key);

    int updateByPrimaryKeySelective(WalletActiveUserDTO record);

    int updateByPrimaryKey(WalletActiveUserDTO record);
}