package com.zxcl.webapp.integration.dao.wallet;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.dto.wallet.WalletBillDTO;


public interface WalletBillDAO {
//    int deleteByPrimaryKey(String billId);

//    int insertSelective(WalletBillDTO record);

    WalletBillDTO selectByPrimaryKey(String billId);

//    int updateByPrimaryKeySelective(WalletBillDTO record);

	WalletBillDTO selectWalletBillByPayOrderNo(String payOrderNo);

	int billListCount(PageParam pageParam);

	List<WalletBillDTO> billList(PageParam pageParam);

//	List<WalletBillDTO> getBillListByTransType(WalletBillDTO bill);

	BigDecimal selectTotalBillInAmountByUserId(String userId);

//	List<WalletBillDTO> getBillListByParam(@Param("userId")String userId, @Param("transType")String transType, @Param("billType")String billType);

}