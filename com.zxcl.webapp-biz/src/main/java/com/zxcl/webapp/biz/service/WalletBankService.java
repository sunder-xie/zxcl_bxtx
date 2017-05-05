package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.wallet.WalletBankDTO;

/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */
public interface WalletBankService {
	
	/**
	 * 查询用户银行卡列表
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<WalletBankDTO> selectWalletBankListByUserId(String userId) throws BusinessServiceException;
	
	/**
	 * 查询单个银行卡明细
	 * @param walletBankId
	 * @return
	 * @throws BusinessServiceException
	 */
	public WalletBankDTO selectWalletBankById(String walletBankId) throws BusinessServiceException;
	
	/**
	 * 是否能使用名义代理人==>true能 false否
	 * @param userId
	 * @return
	 */
	public boolean walletCanUseAgent(String userId);
	
	/**
	 * 用户是否第一次新增银行卡，且持卡人与小微姓名不一致
	 * @param userId
	 * @param cardOwnerName
	 * @return 1用户是第一次新增银行卡且持卡人与小微姓名不一致 0非（用户是第一次新增银行卡且持卡人与小微姓名不一致）-1：小微姓名为空
	 */
	public int isOnceWbAndMicNameNotSame(String userId, String cardOwnerName) throws BusinessServiceException;
	
	/**
	 * 用户是否第一次新增银行卡
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isOnceWb(String userId) throws BusinessServiceException;
	
	/**
	 * 添加银行卡
	 * @param walletBankDTO
	 * @throws BusinessServiceException
	 */
	public void addWalletBank(WalletBankDTO walletBankDTO) throws BusinessServiceException;
	
	
	/**
	 * 删除银行卡
	 * @param walletBankDTO
	 * @param userId
	 * @throws BusinessServiceException
	 */
	public void delWalletBankById(String userId, String walletBankId) throws BusinessServiceException;
	
	/**
	 * 更新银行卡
	 * @param walletBankDTO
	 * @throws BusinessServiceException
	 */
	public void updateWalletBank(WalletBankDTO walletBankDTO) throws BusinessServiceException;

	/**
	 * 查询银行卡数量
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer selectWalletBankCountByUserId(String userId) throws BusinessServiceException;
	
	/**
	 * 校验：银行卡账户名与帐号姓名是否一致
	 * @param userId
	 * @param walletBankId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean cashBankCheck(String userId, String walletBankId) throws BusinessServiceException;
	
	/**
	 * 查询某银行下，同一卡号有多少张
	 * @param bankName
	 * @param cardNo
	 * @return
	 * @throws BusinessServiceException
	 */
	public int selectBankCountByBankNameAndCarNo(String bankName, String cardNo) throws BusinessServiceException;
	
}
