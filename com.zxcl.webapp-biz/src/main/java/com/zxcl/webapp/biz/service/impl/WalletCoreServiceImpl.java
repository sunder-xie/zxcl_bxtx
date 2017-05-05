package com.zxcl.webapp.biz.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zxcl.bxtxmanage.biz.service.WalletSettleService;
import com.zxcl.bxtxmanage.dto.req.rmi.CalcSettleAmountReqDTO;
import com.zxcl.bxtxmanage.dto.resp.rmi.CalcSettleAmountRespDTO;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MicroAgentService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WalletBankService;
import com.zxcl.webapp.biz.service.WalletBillService;
import com.zxcl.webapp.biz.service.WalletCoreService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.biz.util.StringUtil;
import com.zxcl.webapp.dto.MicroAgentDTO;
import com.zxcl.webapp.dto.MicroAgentDTOKey;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.wallet.WalletBankDTO;
import com.zxcl.webapp.dto.wallet.WalletDTO;
import com.zxcl.webapp.integration.dao.wallet.WalletDAO;
import com.zxcl.webapp.util.constants.WalletConstant;


/**
 * @ClassName: 
 * @Description: 钱包service
 * @author zxj
 * @date 
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class WalletCoreServiceImpl implements WalletCoreService {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WalletBillService billService;
	
	@Autowired
	private WalletBankService bankService;
	
	@Autowired
	private WalletDAO walletDao;
	
	@Autowired
	private WalletSettleService walletSettleService;
	
	@Autowired
	private WalletBankService cardService;
	
	@Autowired
	private MicroAgentService microAgentService;

	private static final Object initLock = new Object();
	
	@Override
	@Log("获取钱包账户")
	@Transactional (propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false,noRollbackFor=BusinessServiceException.class)
	public WalletDTO getWalletByUserId(String userId) throws BusinessServiceException {
		WalletDTO wallet = null;
		wallet = walletDao.selectByUserId(userId);
		
		//bxtx没有钱包写权限,要么不初始化,要么营销开接口,
		//这里假设有写的权限
		if(null == wallet){
//			synchronized(initLock){
//				wallet = walletDao.selectByUserId(userId);
//				if(null == wallet){
					BigDecimal decimal = new BigDecimal("0.00");
					Date date = new Date();
					wallet = new WalletDTO();
					wallet.setWalletId(StringUtil.getIdByPrimaryName(userId));
					wallet.setUserId(userId);
					wallet.setAmount(decimal);
					wallet.setAmountCash(decimal);
					wallet.setAmountIncome(decimal);
					wallet.setAmountCashStay(decimal);
					wallet.setAmountStay(decimal);
					wallet.setCrtCde(userId);
					wallet.setCrtTm(date);
					wallet.setStatus("1");
					wallet.setUpdTm(date);
					wallet.setUpdCde(userId);
//					walletDao.insertSelective(wallet);
//				}
//			}
//			
//			logger.info("添加默认银行卡");
//			MicroDTO micro = microService.getMicroByUserId(userId);
//			if(null != micro && StringUtils.isNotBlank(micro.getBank()) && StringUtils.isNotBlank(micro.getBank_name()) &&StringUtils.isNotBlank(micro.getBank_no()) && micro.getBank_no().matches("[\\u4e00-\\u9fa5]+")){
//				WalletBankDTO walletBankDTO = new WalletBankDTO();
//				walletBankDTO.setBankName(micro.getBank());
//				walletBankDTO.setCardOwner(micro.getBank_name());
//				walletBankDTO.setCardNo(micro.getBank_no());
//				walletBankDTO.setTel(micro.getTel());
//				walletBankDTO.setUserId(userId);
//				try {
//					bankService.addWalletBank(walletBankDTO);
//				} catch (Exception e) {
//					logger.info("添加默认银行卡失败:"+e.getMessage(),e);
//				}
//			}
		}
		logger.info(wallet);
		return wallet;
	}
	
	@Override
	@Log("钱包账户提现前")
	public CalcSettleAmountRespDTO cashWalletPre(String walletBankId, String userId, BigDecimal amount, String pwd) throws Exception {
		
		//校验
		if(null == amount){
			logger.info("提现金额不能为空");
			throw new BusinessServiceException("提现金额不能为空");
		}
		if(amount.compareTo(new BigDecimal("1")) < 0){
			logger.info("输入的金额不正确,最低提现额度1元");
			throw new BusinessServiceException("最低提现额度1元");
		}
		if(amount.scale() > 2){
			logger.info("输入的金额不正确,提现金额小数位最多保留两位");
			throw new BusinessServiceException("输入的金额不正确,提现金额小数位最多保留两位");
		}
		WalletDTO wallet = getWalletByUserId(userId);
		if(wallet.getAmount().compareTo(amount) < 0){
			logger.info("账户余额不足");
			throw new BusinessServiceException("账户余额不足");
		}
		if(!bankService.cashBankCheck(userId, walletBankId)){
			logger.info("校验失败：银行卡账户名与帐号姓名不一致");
			throw new BusinessServiceException("校验失败：银行卡账户名与帐号姓名不一致");
		}
		
		//调用后端提现申请接口
		logger.info("调用后端计算结算金额接口");
		logger.info("名义代理人,walletBankId="+walletBankId);
		
		String agentUserId = null;
		
		WalletBankDTO card = cardService.selectWalletBankById(walletBankId);
		if(null == card){
			throw new BusinessServiceException("该银行卡不存在");
		}
		logger.info("card="+card);
		
		
		if(!userId.equals(card.getUserId())){
			agentUserId = card.getUserId();
			logger.info("非本人卡，是否名义代理人卡");
			
			//未关联抛业务异常
			MicroDTO microDTO = microService.getMicroByUserId(userId);
			MicroDTO agentMicroDTO = microService.getMicroByUserId(agentUserId);
			MicroAgentDTO microAgentDTO = microAgentService.selectByPrimaryKey(new MicroAgentDTOKey(null != microDTO?microDTO.getMicro_id():null, null != agentMicroDTO?agentMicroDTO.getMicro_id():null));
			
			if(null == microAgentDTO){
				throw new BusinessServiceException("提现申请失败：你未与该名义代理人关联");
			}
			
			if(!userId.equals(card.getCrtCde())){
				throw new BusinessServiceException("提现申请失败：该名义代理人卡不是由你创建");
			}
		}
		CalcSettleAmountReqDTO  reqCalc = new CalcSettleAmountReqDTO();
		reqCalc.setUserId(StringUtils.isNotBlank(agentUserId)?agentUserId:userId);
		reqCalc.setWithdrawAmount(amount);
		
		
		CalcSettleAmountRespDTO respreqCalc = null;
		logger.info("调用后端计算结算金额接口开始==>reqCalc="+ToStringBuilder.reflectionToString(reqCalc, ToStringStyle.SHORT_PREFIX_STYLE));
		respreqCalc = walletSettleService.calcSettleAmountService(reqCalc);
		logger.info("调用后端计算结算金额接口结束<==respreqCalc="+ToStringBuilder.reflectionToString(respreqCalc, ToStringStyle.SHORT_PREFIX_STYLE));
		if(null == respreqCalc){
			throw new BusinessServiceException("调用后端计算结算金额接口失败：接口返回空");
		}
		if(!"0".equals(respreqCalc.getErrorCode())){
			throw new BusinessServiceException("调用后端计算结算金额接口失败，失败原因："+respreqCalc.getErrorMsg());
		}
		return respreqCalc;
	}
	
	
	@Override
	@Log("钱包账户提现")
	public void cashWallet(String walletBankId, String userId, BigDecimal amount, String pwd, CalcSettleAmountRespDTO respreqCalc) throws BusinessServiceException, Exception {
		
		//校验
		if(null == amount){
			logger.info("提现金额不能为空");
			throw new BusinessServiceException("提现金额不能为空");
		}
		if(amount.compareTo(new BigDecimal("1")) < 0){
			logger.info("输入的金额不正确,最低提现额度1元");
			throw new BusinessServiceException("最低提现额度1元");
		}
		if(amount.scale() > 2){
			logger.info("输入的金额不正确,提现金额小数位最多保留两位");
			throw new BusinessServiceException("输入的金额不正确,提现金额小数位最多保留两位");
		}
		WalletDTO wallet = getWalletByUserId(userId);
//		if(StringUtils.isBlank(wallet.getWalletPwd())){
//			logger.info("你尚未设置提现密码");
//			throw new WalletPwdNullException("你尚未设置提现密码");
//		}
//		if(null == pwd){
//			logger.info("提现密码不能为空");
//			throw new BusinessServiceException("提现密码不能为空");
//		}
//		if(!pwd.equals(wallet.getWalletPwd())){
//			logger.info("提现密码错误");
//			throw new BusinessServiceException("提现密码错误");
//		}
		if(wallet.getAmount().compareTo(amount) < 0){
			logger.info("账户余额不足");
			throw new BusinessServiceException("账户余额不足");
		}
		
		if(!bankService.cashBankCheck(userId, walletBankId)){
			logger.info("校验失败：银行卡账户名与帐号姓名不一致");
			throw new BusinessServiceException("校验失败：银行卡账户名与帐号姓名不一致");
		}
		
		//add by zhaoxiju 2016年11月2日15:10:21
		
		if(null == respreqCalc){
			throw new BusinessServiceException("佣金结算记录必传");
		}
		
		
		WalletBankDTO card = cardService.selectWalletBankById(walletBankId);
		if(null == card){
			throw new BusinessServiceException("该银行卡不存在");
		}
		logger.info("card="+card);
		
		//调用后端提现申请接口
		logger.info("调用后端计算结算金额接口");
		logger.info("名义代理人,walletBankId="+walletBankId);
		
		String agentUserId = null;
		if(!userId.equals(card.getUserId())){
			agentUserId = card.getUserId();
			logger.info("非本人卡，是否名义代理人卡");
			
			//未关联抛业务异常
			MicroDTO microDTO = microService.getMicroByUserId(userId);
			MicroDTO agentMicroDTO = microService.getMicroByUserId(agentUserId);
			MicroAgentDTO microAgentDTO = microAgentService.selectByPrimaryKey(new MicroAgentDTOKey(null != microDTO?microDTO.getMicro_id():null, null != agentMicroDTO?agentMicroDTO.getMicro_id():null));
			
			if(null == microAgentDTO){
				throw new BusinessServiceException("提现申请失败：你未与该名义代理人关联");
			}
		}
		////////////////////////
//		String payOrderNo = null;
//		WalletBathPayRequestDTO req = new WalletBathPayRequestDTO();
//		
//		req.setCarUserName(card.getCardOwner());
//		req.setBankName(card.getBankName());
//		req.setBankNo(card.getBankNo());
//		req.setCarType(card.getCardType());
//		req.setCarNo(card.getCardNo());
//		req.setAmount(respreqCalc.getSettleAmount());//结算金额
//		req.setUserId(StringUtils.isNotBlank(agentUserId)?agentUserId:userId);
//		req.setUseType("2");
//		req.setRemark(null);
//		req.setWithdrawDetailId(respreqCalc.getWithdrawDetailId());// 提现记录ID 
//		
//		logger.info("调用后端生成结算单接口开始==>req="+ToStringBuilder.reflectionToString(req, ToStringStyle.SHORT_PREFIX_STYLE));
//		payOrderNo = walletSettleService.insertWalletSettleService(req);
//		logger.info("调用后端生成结算单接口结束<==payOrderNo=" + payOrderNo);
//		
//		if(StringUtils.isBlank(payOrderNo)){
//			throw new BusinessServiceException("提交审核失败,申请结算单失败");
//		}
		
		//提现
//		WalletDTO insertWallet = new WalletDTO();
//		insertWallet.setAmountCashStay(amount.abs());
//		insertWallet.setUserId(userId);
//		insertWallet.setAmount(amount.abs().negate());
//		updWalletWithAmountByUserId(insertWallet);
		
		
		//新增账单
		Date date = new Date();
		com.zxcl.bxtxmanage.dto.bean.WalletBillDTO walletBillDTO = new com.zxcl.bxtxmanage.dto.bean.WalletBillDTO();
		walletBillDTO.setBillAmount(amount);
//		walletBillDTO.setTransType(WalletConstant.TRANS_TYPE_TIXIAN);
		walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_TIXIAN_STATUS_CHECKING);
		walletBillDTO.setCrtCde(userId);
		walletBillDTO.setCrtTm(date);
		walletBillDTO.setPayChannel(WalletConstant.TRANS_TYPE_PAY_CHANNEL_TL);
		walletBillDTO.setUpdCde(userId);
		walletBillDTO.setBillType(WalletConstant.WALLET_BILL_TYPE_OUT);
		walletBillDTO.setUpdTm(date);
		walletBillDTO.setUserId(userId);
		walletBillDTO.setWalletId(wallet.getWalletId());
		walletBillDTO.setResultMsg("提现中...");
		walletBillDTO.setRemark("用户提现"+amount.toString()+"元");
//		billService.addWalletCashBill(walletBillDTO, walletBankId, respreqCalc);
		
		String result = walletSettleService.withdrawService(walletBillDTO, walletBankId, respreqCalc);
		logger.info("result:"+result);
	}

//	@Override
//	@Log("修改钱包账户密码")
//	public void updWalletPwdByUserId(String userId, String pwd) throws BusinessServiceException {
//		if(StringUtils.isBlank(pwd)){
//			logger.info("钱包密码不能为空");
//			throw new BusinessServiceException("钱包密码不能为空");
//		}
//		if(pwd.length() < 6){
//			logger.info("至少输入6位字符");
//			throw new BusinessServiceException("至少输入6位字符");
//		}
//		
//		UserDTO user = userService.queryUserByUserId(userId);
//		if(null == user){
//			logger.error("获取用户信息失败");
//			throw new BusinessServiceException("获取用户信息失败");
//		}
//		String encodePwd = Encoding.encode(pwd);
//		
//		//提现密码不能和登录密码一致 
//		if(encodePwd.equals(user.getLoginPassword())){
//			logger.info("提现密码不能和登录密码一致");
//			throw new BusinessServiceException("提现密码不能和登录密码一致");
//		}
//		
//		//更新
//		WalletDTO wallet = new WalletDTO();
//		wallet.setUserId(userId);
//		wallet.setWalletPwd(encodePwd);
//		wallet.setUpdCde(userId);
//		
//		walletDao.updateByPrimaryKeySelective(wallet);
//	}


	@Override
	@Log("getWalletByWalletId")
	public WalletDTO getWalletByWalletId(String walletId) throws BusinessServiceException {
		return walletDao.selectByPrimaryKey(walletId);
	}

//	@Override
//	@Log("更新钱包金额")
//	public void updWalletWithAmountByUserId(WalletDTO wallet) throws BusinessServiceException {
//		logger.info("更新钱包金额==>WalletDTO="+wallet);
//		if(null == wallet || null == wallet.getUserId()){
//			throw new BusinessServiceException("操作人不能为空");
//		}
//		logger.info("入参：" + wallet.toString());
//		synchronized (initLock) {
//			WalletDTO selectWallet = walletDao.selectByUserId(wallet.getUserId());
//			WalletDTO insertWallet = new WalletDTO();
//			insertWallet.setVersionId(selectWallet.getVersionId());
//			if(null != wallet.getAmount()){
//				insertWallet.setAmount(selectWallet.getAmount().add(wallet.getAmount()));
//				if(insertWallet.getAmount().compareTo(BigDecimal.ZERO) < 0){
//					throw new BusinessServiceException("钱包余额不足");
//				}
//			}
//			if(null != wallet.getAmountIncome()){
//				insertWallet.setAmountIncome(selectWallet.getAmountIncome().add(wallet.getAmountIncome()));
//			}
//			if(null != wallet.getAmountCash()){
//				insertWallet.setAmountCash(selectWallet.getAmountCash().add(wallet.getAmountCash()));
//			}
//			if(null != wallet.getAmountCashStay()){
//				insertWallet.setAmountCashStay(selectWallet.getAmountCashStay().add(wallet.getAmountCashStay()));
//			}
//			if(null != wallet.getAmountStay()){
//				insertWallet.setAmountStay(selectWallet.getAmountStay().add(wallet.getAmountStay()));
//			}
//			insertWallet.setUserId(wallet.getUserId());
//			insertWallet.setUpdCde(wallet.getUserId());
//			int updCount = walletDao.updateByPrimaryKeySelectiveBusi(insertWallet);
//			logger.info("更新钱包金额<==影响行数="+updCount);
//			if(updCount <= 0){
//				throw new BusinessServiceException("更新钱包金额失败，更新记录="+updCount+",当前版号="+insertWallet.getVersionId());
//			}
//		}
//	}

}
