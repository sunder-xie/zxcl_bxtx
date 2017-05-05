package com.zxcl.webapp.biz.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.AgencyService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.InsuranceService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.biz.service.QuotaService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WalletActiveService;
import com.zxcl.webapp.biz.service.WalletBillService;
import com.zxcl.webapp.biz.service.WalletCoreService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.UserDTO;
import com.zxcl.webapp.dto.wallet.WalletBillDTO;
import com.zxcl.webapp.dto.wallet.WalletDTO;
import com.zxcl.webapp.integration.dao.ActivityDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.integration.dao.QuotaDAO;
import com.zxcl.webapp.integration.dao.UserDAO;
import com.zxcl.webapp.integration.dao.wallet.WalletActiveDAO;
import com.zxcl.webapp.integration.dao.wallet.WalletBillDAO;
import com.zxcl.webapp.integration.dao.wallet.WalletDAO;
import com.zxcl.webapp.util.constants.WalletConstant;

/**
 * @ClassName: 
 * @Description:活动service
 * @author zxj
 * @date 
 */
@Service
@Transactional(rollbackFor=Exception.class, noRollbackFor={})
public class WalletActiveServiceImpl implements WalletActiveService {

	@Autowired
	private WalletActiveDAO walletActiveDao;
	
	@Autowired
	private MicroService microService;
	
	
	@Autowired
	private QuotaService quotaService;
	
	@Autowired	
	private ActivityDAO activityDAO;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private InsuranceService insService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MicroDAO microDao;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private WalletBillService walletBillService;
	
	@Autowired
	private WalletBillDAO walletBillDao;
	
	@Autowired
	private QuotaDAO quotaDao;
	
	@Autowired
	private WalletCoreService walletCoreService;
	
	@Autowired
	private WalletDAO walletDao;
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private ParamConfigService paramConfigService;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
//	@Override
//	@Log("注册活动奖励")
//	public void activeWithRegist(String userId) throws BusinessServiceException {
//		
//		//校验
//		int count = activityDAO.selectCountBetwByTitle("嘉诚太平出单奖励");
//		if(count <= 0){
//			logger.info("活动已过期, return.");
//			return ;
//		}
//		UserDTO userDTO = userService.queryUserByUserId(userId);
//		if(null == userDTO){
//			logger.info("用户【"+userId+"】不存在, return.");
//			return ;
//		}
//		String relevance = userDTO.getRelevance();
//		if(StringUtils.isBlank(relevance)){
//			logger.info("用户【"+userId+"】对应的关系码不存在, return.");
//			return ;
//		}
//		
//		//用户的邀请人的小微信息
//		MicroDTO reMicroDTO = microService.getMicroInvitation(relevance);
//		if(null == reMicroDTO){
//			logger.info("邀请码【"+relevance+"】小微不存在, return.");
//			return ;
//		}
//		
//		//用户的邀请人的用户信息
//		String reUserId = reMicroDTO.getUser_id();
//		UserDTO reUserDTO = userService.queryUserByUserId(reUserId);
//		if(null == reUserDTO){
//			logger.info("用户【"+reUserId+"】不存在, return.");
//			return ;
//		}
//		
//		//获取用户嘉诚太平出单待激活数据
//		List<WalletBillDTO> billList12 = walletBillService.getBillListByParam(reUserId, "12", "WI");
//		if(CollectionUtils.isEmpty(billList12)){
//			logger.info("用户【"+reUserId+"】嘉诚太平出单待激活数据为空, return.");
//			return ;
//		}
//		
//		//获取用户嘉诚太平出单待已激活到账的数据
//		List<WalletBillDTO> billList12_1 = walletBillService.getBillListByParam(reUserId, "12_1", "I");
//		
//		//当两个集合数据size相等时，认为用户嘉诚太平出单待激活已经结算完
//		if(CollectionUtils.isNotEmpty(billList12_1)){
//			if(billList12.size() == billList12_1.size()){
//				logger.info("当两个集合数据size相等时，认为用户嘉诚太平出单待激活已经结算完,return.");
//				return ;
//			}
//		}
//		
//		/**
//		 * 开始激活
//		 */
//		
//		//用户的邀请人的钱包
//		WalletDTO reWalletDTO = walletCoreService.getWalletByUserId(reUserId);
//		if(null == reWalletDTO){
//			logger.info("用户【"+reUserId+"】钱包不存在, return.");
//			return ;
//		}
//				
//		BigDecimal jiHuoAmount = new BigDecimal("20.00");
//		
//		//校验用户钱包待激活的是否足够
//		if(reWalletDTO.getAmountStay().compareTo(jiHuoAmount) < 0){
//			logger.info("用户【"+reUserId+"】钱包待激活金额不足20RMB, return.");
//			return ;
//		}
//		
//		//新增账单记录
//		Date date = new Date();
//		WalletBillDTO walletBillDTO = new WalletBillDTO();
//		walletBillDTO.setBillAmount(jiHuoAmount);
//		walletBillDTO.setBillId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
//		walletBillDTO.setBillType("I");
//		walletBillDTO.setCrtCde(reUserId);
//		walletBillDTO.setCrtTm(date);
//		walletBillDTO.setPayChannel(null);
//		walletBillDTO.setPayOrderNo(null);
//		walletBillDTO.setRemark("嘉诚太平出单激活,注册用户="+userId);
//		walletBillDTO.setResultMsg("已结算");
//		walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_JIACHENGTAIPINGCHUDAN_1_ACTIVITY_FEE_STATUS6);
//		walletBillDTO.setTransType(WalletConstant.TRANS_TYPE_JIACHENGTAIPINGCHUDAN_1_ACTIVITY_FEE);
//		walletBillDTO.setUpdCde("system");
//		walletBillDTO.setUpdTm(date);
//		walletBillDTO.setUserId(reUserId);
//		walletBillDTO.setWalletId(reWalletDTO.getWalletId());
//		walletBillDao.insertSelective(walletBillDTO);
//		
//		//钱包余额更新
//		WalletDTO walletUpdateDTO = new WalletDTO();
//		walletUpdateDTO.setUserId(reUserId);//用户
//		walletUpdateDTO.setAmount(jiHuoAmount);//余额
//		walletUpdateDTO.setAmountIncome(jiHuoAmount);//总收益
//		walletUpdateDTO.setAmountStay(jiHuoAmount.negate());//待激活
//		walletUpdateDTO.setVersionId(reWalletDTO.getVersionId());
//		walletCoreService.updWalletWithAmountByUserId(walletUpdateDTO);
//		
//		logger.info("用户【"+userId+"】的邀请人【"+reUserId+"】已激活20RMB至钱包余额完成.");
//	}

}
