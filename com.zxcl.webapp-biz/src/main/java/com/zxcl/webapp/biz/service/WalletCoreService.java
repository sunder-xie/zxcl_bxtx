package com.zxcl.webapp.biz.service;

import java.math.BigDecimal;

import com.zxcl.bxtxmanage.dto.resp.rmi.CalcSettleAmountRespDTO;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.wallet.WalletDTO;

/**
 * @author zxj
 * @category 钱包service
 */
public interface WalletCoreService {
	
	/**
	 * 获取钱包账户
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public WalletDTO getWalletByUserId(String userId) throws BusinessServiceException;
	
	/**
	 * 获取钱包账户
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public WalletDTO getWalletByWalletId(String walletId) throws BusinessServiceException;
	
	/**
	 * 提现申请前
	 * @param userId 用户 ID
	 * @param amount  提现金额
	 * @param pwd 账户密码
	 * @return 
	 * @throws BusinessServiceException
	 */
	public CalcSettleAmountRespDTO cashWalletPre(String walletBankId, String userId, BigDecimal amount, String pwd) throws Exception;
	public void cashWallet(String walletBankId, String userId, BigDecimal amount, String pwd, CalcSettleAmountRespDTO respreqCalc) throws BusinessServiceException, Exception;
	
	/**
	 * 修改钱包账户密码
	 * @param userId
	 * @param pwd 密码
	 * @throws BusinessServiceException
	 */
//	public void updWalletPwdByUserId(String userId, String pwd) throws BusinessServiceException;
	
	/**
	 * 更新钱包金额(更新的金额有正负之分，相对的)
	 * @param wallet
	 * @throws BusinessServiceException
	 */
//	public void updWalletWithAmountByUserId(WalletDTO wallet) throws BusinessServiceException;
	
}
