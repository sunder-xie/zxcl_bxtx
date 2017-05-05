package com.zxcl.webapp.integration.dao.wallet;

import com.zxcl.webapp.dto.wallet.WalletStayDTO;
import com.zxcl.webapp.dto.wallet.WalletStayDTOKey;

public interface WalletStayDAO {
    int deleteByPrimaryKey(WalletStayDTOKey key);

    int insert(WalletStayDTO record);

    int insertSelective(WalletStayDTO record);

    WalletStayDTO selectByPrimaryKey(WalletStayDTOKey key);

    int updateByPrimaryKeySelective(WalletStayDTO record);

    int updateByPrimaryKey(WalletStayDTO record);
}