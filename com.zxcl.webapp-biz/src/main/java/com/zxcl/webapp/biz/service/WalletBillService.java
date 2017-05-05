package com.zxcl.webapp.biz.service;

import java.math.BigDecimal;
import java.util.List;

import com.zxcl.bxtxmanage.dto.resp.rmi.CalcSettleAmountRespDTO;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.PageBean;
import com.zxcl.webapp.dto.PolicySalesActivityFeeDTO;
import com.zxcl.webapp.dto.wallet.WalletBillDTO;

/**
 * @ClassName: 
 * @Description: 钱包账单
 * @author zxj
 * @date 2016年3月11日
 */
public interface WalletBillService {
	
	/**
	 * 分页获取钱包账单
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public PageBean<WalletBillDTO> getBillListByPage(PageParam pageParam) throws BusinessServiceException;
	
	
	/**
	 * 根据交易类型获取账单列表
	 * @param userId
	 * @param transType
	 * @return
	 * @throws BusinessServiceException
	 */
//	public List<WalletBillDTO> getBillListByTransType(String userId, String transType) throws BusinessServiceException;
	
	/**
	 * 获取账单
	 * @param walletBillDTO
	 * @return
	 * @throws BusinessServiceException
	 */
	public WalletBillDTO getWalletBillById(String billId) throws BusinessServiceException;
	
	/**
	 * 通过结算单号获取账单
	 * @param walletBillDTO
	 * @return
	 * @throws BusinessServiceException
	 */
	public WalletBillDTO getWalletBillByPayOrderNo(String payOrderNo) throws BusinessServiceException;
	
	/**
	 * 提现新增账单记录
	 * @param walletBillDTO
	 * @throws BusinessServiceException
	 */
//	public void addWalletCashBill(WalletBillDTO walletBillDTO, String walletBankId, CalcSettleAmountRespDTO respreqCalc) throws BusinessServiceException, Exception;
	
	/**
	 * 佣金结算新增账单记录
	 * @param walletBillDTO
	 * @param walletBankId
	 * @throws BusinessServiceException
	 */
//	public int addWalletPolicyFeeBill(PolicyFeeSettleDTO policyFeeSettleDTO) throws BusinessServiceException, WalletRuningLowException;
	
	/**
	 * 通过账单号更新账单状态-提现流程
	 * @param walletBillDTO
	 * @throws BusinessServiceException
	 */
//	public void updWalletBillStatusWithCash(WalletBillDTO walletBillDTO, String versionId) throws BusinessServiceException;
	
	/**
	 * 删除账单（逻辑删除）
	 * @param walletBillDTO
	 * @throws BusinessServiceException
	 */
//	public void delWalletBill(WalletBillDTO walletBillDTO) throws BusinessServiceException;
	
	/**
	 * 增加嘉诚活动奖励的账单信息.
	 * @param policySalesActivityFeeDTO
	 */
//	public void addWalletPolicySaleActivityFeeBill(PolicySalesActivityFeeDTO policySalesActivityFeeDTO) throws BusinessServiceException;
	
	
	/**
	 * 自营出单奖励
	 * @param policySalesActivityFeeDTO
	 * @throws Exception
	 */
//	public void  addPolicySelfActivityWallet(PolicySalesActivityFeeDTO policySalesActivityFeeDTO) throws Exception;
	
	/**
	 * 增加保行天下红包发放的账单信息
	 * @param id
	 * @param userId
	 * @param amount
	 */
//	public void addWalletActivityBxtxAOpenRedPickets(Integer id,String userId,BigDecimal amount) throws BusinessServiceException;

	/**
	 * 增加保行天下红包发放的账单信息
	 * @param id
	 * @param userId
	 * @param amount
	 */
//	public void addWalletActivityBxtxCOpenRedPickets(String id,String userId,BigDecimal amount) throws BusinessServiceException;

	/**
	 * 根据条件获取账单列表
	 * @param userId
	 * @param transType
	 * @param billType
	 * @return
	 */
//	public List<WalletBillDTO> getBillListByParam(String userId, String transType, String billType);
}
