package com.zxcl.webapp.biz.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxcl.bxtxmanage.biz.service.WalletSettleService;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.MicroAgentService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WalletBankService;
import com.zxcl.webapp.biz.service.WalletBillService;
import com.zxcl.webapp.biz.service.WalletCoreService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.biz.util.model.PageParam;
import com.zxcl.webapp.biz.util.resultEntity.PageBean;
import com.zxcl.webapp.dto.PolicyBaseDTO;
import com.zxcl.webapp.dto.wallet.WalletBillDTO;
import com.zxcl.webapp.integration.dao.PlatformDAO;
import com.zxcl.webapp.integration.dao.PolicyBaseDAO;
import com.zxcl.webapp.integration.dao.wallet.WalletBillDAO;
import com.zxcl.webapp.integration.dao.wallet.WalletDAO;



/**
 * @ClassName: 
 * @Description:  钱包账单
 * @author zxj
 * @date 
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class WalletBillServiceImpl implements WalletBillService {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public static final String WALLET_BILL_PREID = "WB";
	
	@Autowired
	private PlatformDAO baseDao;
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WalletCoreService walletService;
	
	@Autowired
	private WalletDAO walletDao;
	
	@Autowired
	private WalletBillDAO walletBillDao;
	
	@Autowired
	private WalletSettleService walletSettleService;
	
	@Autowired
	private WalletBankService cardService;
	
	@Autowired
	private MicroAgentService microAgentService;
	
	@Autowired
	private PolicyBaseDAO policyBaseDAO;

	/**
	 * 格式: 佣金，川A43422，张飞
	 * @param id
	 * @return
	 */
	protected String getPolicyShowStr(String id){
		String r = "佣金";
		if(StringUtils.isNotBlank(id)){
			try {
				PolicyBaseDTO policyBaseDTO = policyBaseDAO.selectByPrimaryKey(id);
				if(null != policyBaseDTO){
					if(StringUtils.isNotBlank(policyBaseDTO.getPlateNo())){
						r += ","+policyBaseDTO.getPlateNo();
					}
					if(StringUtils.isNotBlank(policyBaseDTO.getOwnerName())){
						r += ","+policyBaseDTO.getOwnerName();
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return r;
	}
	
	@Override
	@Log("获取账单列表")
	public PageBean<WalletBillDTO> getBillListByPage(PageParam pageParam) throws BusinessServiceException {
		PageBean<WalletBillDTO> page = new PageBean<WalletBillDTO>();
		if(null == pageParam.getPageSize()){
			pageParam.setPageSize(30);
		}
		if(null == pageParam.getCurrentPage()){
			pageParam.setCurrentPage(1);
		}
		int recordCount = 0;
		List<WalletBillDTO> dataList = null;
		pageParam.setStart(pageParam.getCurrentPage()==1?0:(pageParam.getCurrentPage()-1)*pageParam.getPageSize());
		try {
			
			recordCount = walletBillDao.billListCount(pageParam);
			dataList = walletBillDao.billList(pageParam);
		} catch (Exception e) {
			logger.error("获取账单列表失败", e);
		}
//		if(CollectionUtils.isNotEmpty(dataList)){
//			List<PlatformDTO> baseList = null;
//			try {
//				baseList = baseDao.getByCodeClass(BaseFinal.WALLET_TRS_NAME);
//			} catch (Exception e) {
//				logger.error("查询钱包状态基础数据失败", e);
//			}
//			if(CollectionUtils.isNotEmpty(baseList)){
//				for(WalletBillDTO item : dataList){
//					item.setShowName(getNameByCode(baseList, item.getTransType()+"_"+item.getStatus()));
//				}
//			}
//		}
		if(CollectionUtils.isNotEmpty(dataList)){
			for(WalletBillDTO item : dataList){
				String showName = "";
				if("I1".equals(item.getAmountType())){//佣金,billAmount可能为负值,页面也显示负值
					 showName = getPolicyShowStr(item.getAmountTargetId());//格式: 佣金，川A43422，张飞
				}
				if("I2".equals(item.getAmountType())){//分销
					showName = "分销";
				}
				if("I3".equals(item.getAmountType())){//活动
					showName = "活动";
				}
				if("I4".equals(item.getAmountType())){//充值
					showName = "充值";
				}
				if("O1".equals(item.getAmountType())){//提现
					showName = "提现";//提现中(1) 提现成功(6) 提现失败(4)
					if("1".equals(item.getStatus())){
						showName = "提现中";
					}
					if("6".equals(item.getStatus())){
						showName = "提现成功";
					}
					if("4".equals(item.getStatus())){
						showName = "提现失败";
					}
					item.setBillAmount(item.getBillAmount().abs().negate());//提现只能显示负值
				}
				if("O2".equals(item.getAmountType())){//消费
					showName = "消费";
					item.setBillAmount(item.getBillAmount().abs().negate());//消费只能显示负值
				}
				item.setShowName(showName);
			}
			
		}
		
		page.setPageSize(pageParam.getPageSize());
		page.setCurrentPage(pageParam.getCurrentPage());
		page.setRecordCount(recordCount);
		page.setPageCount(recordCount);
		page.setDataList(dataList);
	    return page;
	}
	
	public static void main(String[] args) {
		System.out.println(new BigDecimal("-9").abs().negate());
	}
	
//	private String getNameByCode(List<PlatformDTO> baseList, String tys){
//		String result = "";
//		for(PlatformDTO item : baseList){
//			if(tys.equals(item.getCode())){
//				result += item.getName();
//				break;
//			}
//		}
//		return result;
//	}

	@Override
	@Log("通过账单号获取账单")
	public WalletBillDTO getWalletBillById(String billId) throws BusinessServiceException {
		return walletBillDao.selectByPrimaryKey(billId);
	}
	
	@Log("通过结算单号获取账单")
	@Override
	public WalletBillDTO getWalletBillByPayOrderNo(String payOrderNo) throws BusinessServiceException {
		return walletBillDao.selectWalletBillByPayOrderNo(payOrderNo);
	}

//	@Override
//	@Log("通过账单号更新账单状态-提现流程")
//	public void updWalletBillStatusWithCash(WalletBillDTO walletBillDTO, final String versionId) throws BusinessServiceException {
//		if(null == walletBillDTO || null == walletBillDTO.getBillId()){
//			logger.info("账单号不能为空");
//			throw new BusinessServiceException("账单号不能为空");
//		}
//		if(null == walletBillDTO.getStatus()){
//			logger.info("账单状态不能为空");
//			throw new BusinessServiceException("账单状态不能为空");
//		}
//		if(null == walletBillDTO.getUserId()){
//			logger.info("操作人不能为空");
//			throw new BusinessServiceException("操作人不能为空");
//		}
//		BigDecimal amount = walletBillDTO.getBillAmount();
//		walletBillDao.updateByPrimaryKeySelective(walletBillDTO);
//		
//		//更新账户余额
//		//提现
//		if(WalletConstant.TRANS_TYPE_TIXIAN.equals(walletBillDTO.getTransType())){
//			logger.info("oldStatus="+walletBillDTO.getOldStatus());
//			//支付失败
//			WalletDTO walletUpd = new WalletDTO();
//			walletUpd.setVersionId(versionId);
//			walletUpd.setUserId(walletBillDTO.getUserId());
//			walletUpd.setUpdCde(walletBillDTO.getUserId());
//			
//			//提现失败
//			if(WalletConstant.TRANS_TYPE_TIXIAN_STATUS_PAYFAIL.equals(walletBillDTO.getStatus())){
//				walletUpd.setAmount(amount);
//				walletUpd.setAmountCashStay(amount.negate());
//			}
//			//提现成功
//			else if(WalletConstant.TRANS_TYPE_TIXIAN_STATUS_PAYSUCC.equals(walletBillDTO.getStatus())){
//				walletUpd.setAmountCashStay(amount.negate());
//				walletUpd.setAmountCash(amount);
//			}
//			//审核失败
//			else if(WalletConstant.TRANS_TYPE_TIXIAN_STATUS_CHECKFAIL.equals(walletBillDTO.getStatus())){
//				walletUpd.setAmount(amount);
//				walletUpd.setAmountCashStay(amount.negate());
//			}
//			//审核成功
//			else if(WalletConstant.TRANS_TYPE_TIXIAN_STATUS_CHECKSUCC.equals(walletBillDTO.getStatus())){
//			}
//			
//			walletService.updWalletWithAmountByUserId(walletUpd);
//		}
//	} 
//	@Override
//	@Log("删除账单")
//	public void delWalletBill(WalletBillDTO walletBillDTO) throws BusinessServiceException {
//		if(null == walletBillDTO || null == walletBillDTO.getBillId()){
//			logger.info("账单号不能为空");
//			throw new BusinessServiceException("账单号不能为空");
//		}
//		if(null == walletBillDTO.getUserId()){
//			logger.info("操作人不能为空");
//			throw new BusinessServiceException("操作人不能为空");
//		}
//		walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_TIXIAN_STATUS_DEL);
//		walletBillDao.deleteByPrimaryKey(walletBillDTO.getBillId());
//	}

//	@Override
//	@Log("根据交易类型获取账单列表")
//	public List<WalletBillDTO> getBillListByTransType(String userId, String transType) throws BusinessServiceException {
//		if(null == userId || null == transType){
//			logger.info("参数不能为空");
//			throw new BusinessServiceException("参数不能为空");
//		}
//		WalletBillDTO bill = new WalletBillDTO();
//		bill.setUserId(userId);
//		bill.setTransType(transType);
//		return walletBillDao.getBillListByTransType(bill);
//	}

//	@Override
//	@Log("提现新增账单记录")
//	public void addWalletCashBill(WalletBillDTO walletBillDTO, String walletBankId, CalcSettleAmountRespDTO respreqCalc) throws BusinessServiceException, Exception {
//		
//		if(null == respreqCalc){
//			throw new BusinessServiceException("佣金结算记录必传");
//		}
//		
//		
//		WalletBankDTO card = cardService.selectWalletBankById(walletBankId);
//		if(null == card){
//			throw new BusinessServiceException("该银行卡不存在");
//		}
//		logger.info("card="+card);
//		
//		//调用后端提现申请接口
//		logger.info("调用后端计算结算金额接口");
//		logger.info("名义代理人,walletBankId="+walletBankId);
//		
//		String agentUserId = null;
//		if(!walletBillDTO.getUserId().equals(card.getUserId())){
//			agentUserId = card.getUserId();
//			logger.info("非本人卡，是否名义代理人卡");
//			
//			//未关联抛业务异常
//			MicroDTO microDTO = microService.getMicroByUserId(walletBillDTO.getUserId());
//			MicroDTO agentMicroDTO = microService.getMicroByUserId(agentUserId);
//			MicroAgentDTO microAgentDTO = microAgentService.selectByPrimaryKey(new MicroAgentDTOKey(null != microDTO?microDTO.getMicro_id():null, null != agentMicroDTO?agentMicroDTO.getMicro_id():null));
//			
//			if(null == microAgentDTO){
//				throw new BusinessServiceException("提现申请失败：你未与该名义代理人关联");
//			}
//		}
//		String payOrderNo = null;
//		WalletBathPayRequestDTO req = new WalletBathPayRequestDTO();
//			
//		req.setCarUserName(card.getCardOwner());
//		req.setBankName(card.getBankName());
//		req.setBankNo(card.getBankNo());
//		req.setCarType(card.getCardType());
//		req.setCarNo(card.getCardNo());
//		req.setAmount(respreqCalc.getSettleAmount());//结算金额
//		req.setUserId(StringUtils.isNotBlank(agentUserId)?agentUserId:walletBillDTO.getUserId());
//		req.setUseType("2");
//		req.setRemark(null);
//		req.setWithdrawDetailId(respreqCalc.getWithdrawDetailId());// 提现记录ID 
//		
//		logger.info("调用后端生成结算单接口开始==>req="+ToStringBuilder.reflectionToString(req, ToStringStyle.SHORT_PREFIX_STYLE));
//		payOrderNo = walletSettleService.insertWalletSettleService(req);
//		logger.info("调用后端生成结算单接口结束<==payOrderNo=" + payOrderNo);
//		
//		
//		if(StringUtils.isBlank(payOrderNo)){
//			throw new BusinessServiceException("提交审核失败,申请结算单失败");
//		}
//		
//		walletBillDTO.setBillId(WALLET_BILL_PREID +  DateUtil.primaryKey());
//		walletBillDTO.setCashBankNo(card.getBankName());
//		walletBillDTO.setCashCardNo(card.getCardNo());
//		walletBillDTO.setCashCardOwner(card.getCardOwner());
//		walletBillDTO.setPayOrderNo(payOrderNo);
//		
//		if(null == walletBillDTO.getBillAmount() || walletBillDTO.getBillAmount().compareTo(BigDecimal.ZERO) <= 0){
//			logger.info("账单金额不正确");
//			throw new BusinessServiceException("账单金额不正确");
//		}
//		walletBillDao.insertSelective(walletBillDTO);
//	}

//	@Override
//	@Log("佣金结算新增账单记录")
//	
	//return 1:成功  0:失败
//	public int addWalletPolicyFeeBill(PolicyFeeSettleDTO policyFeeSettleDTO) throws BusinessServiceException, WalletRuningLowException {
//		logger.info("结算单号PolicyFeeSettleId="+policyFeeSettleDTO.getPolicyFeeSettleId());
//		if(StringUtils.isBlank(policyFeeSettleDTO.getUserId())){
//			logger.info("处理失败,用户ID不能为空,结算单号PolicyFeeSettleId="+policyFeeSettleDTO.getPolicyFeeSettleId());
//			return 0;
//		}
//		MicroDTO micro = microService.getMicroByUserId(policyFeeSettleDTO.getUserId());
//		if(null == micro){
//			logger.info("处理失败,用户 "+policyFeeSettleDTO.getUserId()+" 不存在,结算单号PolicyFeeSettleId="+policyFeeSettleDTO.getPolicyFeeSettleId());
//			return 0;
//		}
//		WalletDTO walletDTO = walletService.getWalletByUserId(policyFeeSettleDTO.getUserId());
//		if(policyFeeSettleDTO.getAmount() == null){
//			logger.info("处理失败,结算金额不能为空,结算单号PolicyFeeSettleId="+policyFeeSettleDTO.getPolicyFeeSettleId());
//			return 0;
//		}
//		if((walletDTO.getAmount().add(policyFeeSettleDTO.getAmount())).compareTo(BigDecimal.ZERO) < 0){
//			logger.info("处理失败,钱包余额不足,无法结算,钱包余额="+walletDTO.getAmount().toString()+",需结算金额="+policyFeeSettleDTO.getAmount().toString()+",结算单号PolicyFeeSettleId="+policyFeeSettleDTO.getPolicyFeeSettleId());
//			return 0;
//		}
//		
//		logger.info("新增结算账单信息");
//		Date date = new Date();
//		WalletBillDTO walletBillDTO = new WalletBillDTO();
//		walletBillDTO.setBillAmount(policyFeeSettleDTO.getAmount());
//		walletBillDTO.setBillId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
//		
//		//update by zxj 2016年9月2日10:29:28  相对于财务，负数也是进钱包
//		walletBillDTO.setBillType(WalletConstant.WALLET_BILL_TYPE_IN);
////		walletBillDTO.setBillType((policyFeeSettleDTO.getAmount().compareTo(BigDecimal.ZERO) >= 0) ? WalletConstant.WALLET_BILL_TYPE_IN : WalletConstant.WALLET_BILL_TYPE_OUT);
////		walletBillDTO.setCashBankNo(policyFeeSettleDTO.getBankName());
////		walletBillDTO.setCashCardNo(policyFeeSettleDTO.getCarNo());
////		walletBillDTO.setCashCardOwner(policyFeeSettleDTO.getOwnerName());
//		walletBillDTO.setCrtCde(policyFeeSettleDTO.getUserId());
//		walletBillDTO.setCrtTm(date);
//		walletBillDTO.setPayChannel(null);
//		walletBillDTO.setPayOrderNo(policyFeeSettleDTO.getPolicyFeeSettleId());
//		walletBillDTO.setRemark("佣金结算,保单基础ID号="+policyFeeSettleDTO.getPolicyBaseId());
//		walletBillDTO.setResultMsg("已结算");
//		walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_WALLET_POLICY_FEE_STATUS6);
//		walletBillDTO.setTransType(WalletConstant.TRANS_TYPE_WALLET_POLICY_FEE);
//		walletBillDTO.setUpdCde("system");
//		walletBillDTO.setUpdTm(date);
//		walletBillDTO.setUserId(policyFeeSettleDTO.getUserId());
//		walletBillDTO.setWalletId(walletDTO.getWalletId());
//		int c = walletBillDao.insertSelective(walletBillDTO);
//		logger.info("插入bill成功,影响行数"+c+",walletBillDTO="+ToStringBuilder.reflectionToString(walletBillDTO));
//		
//		
//		logger.info("新增结算账单信息===>计算钱包余额");
//		WalletDTO walletUpdateDTO = new WalletDTO();
//		walletUpdateDTO.setUserId(policyFeeSettleDTO.getUserId());
//		walletUpdateDTO.setAmount(policyFeeSettleDTO.getAmount());
//		walletUpdateDTO.setAmountIncome(policyFeeSettleDTO.getAmount());
//		walletUpdateDTO.setVersionId(walletDTO.getVersionId());
//		walletService.updWalletWithAmountByUserId(walletUpdateDTO);
//		
//		logger.info("结算单号PolicyFeeSettleId="+policyFeeSettleDTO.getPolicyFeeSettleId()+"处理结束");
//		return 1;
//	}
	
//	@Override
//	@Log("佣金结算新增账单记录")
//	@Transactional(rollbackFor=Exception.class)
//	public void addWalletPolicySaleActivityFeeBill(PolicySalesActivityFeeDTO policySalesActivityFeeDTO) throws BusinessServiceException{
//		logger.info("结算嘉诚出单员已出的商业险奖励，活动奖励单号：="+policySalesActivityFeeDTO.getPolicySalesActivityFeeId());
//		WalletDTO walletDTO = walletService.getWalletByUserId(policySalesActivityFeeDTO.getUserId());
//		if(policySalesActivityFeeDTO.getAmount() == null){
//			throw new BusinessServiceException("结算嘉诚金额不能为空,活动奖励单号="+policySalesActivityFeeDTO.getPolicySalesActivityFeeId());
//		}
//		
//		Date date = new Date();
//		WalletBillDTO walletBillDTO = new WalletBillDTO();
//		walletBillDTO.setBillAmount(policySalesActivityFeeDTO.getAmount().setScale(2, RoundingMode.HALF_DOWN));
//		walletBillDTO.setBillId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
//		
//		//update by zxj 2016年9月2日10:29:28  相对于财务，负数也是进钱包
//		walletBillDTO.setBillType(WalletConstant.WALLET_BILL_TYPE_IN);
////		walletBillDTO.setBillType((policySalesActivityFeeDTO.getAmount().compareTo(BigDecimal.ZERO) >= 0) ? WalletConstant.WALLET_BILL_TYPE_IN : WalletConstant.WALLET_BILL_TYPE_OUT);
////		walletBillDTO.setCashBankNo(policyFeeSettleDTO.getBankName());
////		walletBillDTO.setCashCardNo(policyFeeSettleDTO.getCarNo());
////		walletBillDTO.setCashCardOwner(policyFeeSettleDTO.getOwnerName());
//		walletBillDTO.setCrtCde(policySalesActivityFeeDTO.getUserId());
//		walletBillDTO.setCrtTm(date);
//		walletBillDTO.setPayChannel(null);
//		walletBillDTO.setPayOrderNo(policySalesActivityFeeDTO.getPolicySalesActivityFeeId());
//		walletBillDTO.setRemark("嘉诚出商业险活动奖励,活动奖励ID号="+policySalesActivityFeeDTO.getPolicySalesActivityFeeId());
//		walletBillDTO.setResultMsg("已结算");
//		walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_WALLET_POLICY_SALE_ACTIVITY_FEE_STATUS6);
//		walletBillDTO.setTransType(WalletConstant.TRANS_TYPE_POLICY_SALE_ACTIVITY_FEE);
//		walletBillDTO.setUpdCde("system");
//		walletBillDTO.setUpdTm(date);
//		walletBillDTO.setUserId(policySalesActivityFeeDTO.getUserId());
//		walletBillDTO.setWalletId(walletDTO.getWalletId());
//		walletBillDao.insertSelective(walletBillDTO);
//		
//		logger.info("新增结算账单信息===>计算钱包余额");
//		WalletDTO walletUpdateDTO = new WalletDTO();
//		walletUpdateDTO.setUserId(policySalesActivityFeeDTO.getUserId());
//		walletUpdateDTO.setAmount(policySalesActivityFeeDTO.getAmount());
//		walletUpdateDTO.setAmountIncome(policySalesActivityFeeDTO.getAmount());
//		walletUpdateDTO.setVersionId(walletDTO.getVersionId());
//		walletService.updWalletWithAmountByUserId(walletUpdateDTO);
//		
//		logger.info("嘉诚商业险出单奖励活动处理完成，奖励活动ID="+policySalesActivityFeeDTO.getPolicySalesActivityFeeId());
//	}

//	@Log("保险天下红包发放A新增账单记录")
//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public void addWalletActivityBxtxAOpenRedPickets(Integer id,String userId,
//			BigDecimal amount) throws BusinessServiceException {
//		logger.info("保行天下红包发放A，活动奖励单号：="+ id);
//		WalletDTO walletDTO = walletService.getWalletByUserId(userId);
//		if(amount == null){
//			throw new BusinessServiceException("红包发放奖励的金额不能null,活动奖励单号="+id );
//		}
//		
//		Date date = new Date();
//		WalletBillDTO walletBillDTO = new WalletBillDTO();
//		walletBillDTO.setBillAmount(amount.setScale(2, RoundingMode.HALF_DOWN));
//		walletBillDTO.setBillId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
//		
//		//update by zxj 2016年9月2日10:29:28  相对于财务，负数也是进钱包
//		walletBillDTO.setBillType(WalletConstant.WALLET_BILL_TYPE_IN);
////		walletBillDTO.setBillType((amount.compareTo(BigDecimal.ZERO) >= 0) ? WalletConstant.WALLET_BILL_TYPE_IN : WalletConstant.WALLET_BILL_TYPE_OUT);
////		walletBillDTO.setCashBankNo(policyFeeSettleDTO.getBankName());
////		walletBillDTO.setCashCardNo(policyFeeSettleDTO.getCarNo());
////		walletBillDTO.setCashCardOwner(policyFeeSettleDTO.getOwnerName());
//		walletBillDTO.setCrtCde(userId);
//		walletBillDTO.setCrtTm(date);
//		walletBillDTO.setPayChannel(null);
//		walletBillDTO.setPayOrderNo(id.toString());
//		walletBillDTO.setRemark("保行天下发放红包A,活动奖励ID号="+id);
//		walletBillDTO.setResultMsg("已结算");
//		walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_WALLET_ACTIVITY_BXTXA_STATUS6);
//		walletBillDTO.setTransType(WalletConstant.TRANS_TYPE_ACTIVITY_BXTXA_REDPACKET);
//		walletBillDTO.setUpdCde("system");
//		walletBillDTO.setUpdTm(date);
//		walletBillDTO.setUserId(userId);
//		walletBillDTO.setWalletId(walletDTO.getWalletId());
//		walletBillDao.insertSelective(walletBillDTO);
//		
//		logger.info("新增结算账单信息===>计算钱包余额");
//		WalletDTO walletUpdateDTO = new WalletDTO();
//		walletUpdateDTO.setUserId(userId);
//		walletUpdateDTO.setAmount(amount);
//		walletUpdateDTO.setAmountIncome(amount);
//		walletUpdateDTO.setVersionId(walletDTO.getVersionId());
//		walletService.updWalletWithAmountByUserId(walletUpdateDTO);
//		
//		logger.info("保行天下红包发放A发放完成，奖励活动ID="+id);
//		
//	}

	
	
//	@Log("保险天下红包发放C新增账单记录")
//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public void addWalletActivityBxtxCOpenRedPickets(String id, String userId,
//			BigDecimal amount) throws BusinessServiceException {
//		logger.info("保行天下红包发放C，活动奖励单号：="+ id);
//		WalletDTO walletDTO = walletService.getWalletByUserId(userId);
//		if(amount == null){
//			throw new BusinessServiceException("红包发放奖励的金额不能null,活动奖励单号="+id );
//		}
//		
//		Date date = new Date();
//		WalletBillDTO walletBillDTO = new WalletBillDTO();
//		walletBillDTO.setBillAmount(amount.setScale(2, RoundingMode.HALF_DOWN));
//		walletBillDTO.setBillId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
//		
//		//update by zxj 2016年9月2日10:29:28  相对于财务，负数也是进钱包
//		walletBillDTO.setBillType(WalletConstant.WALLET_BILL_TYPE_IN);
////		walletBillDTO.setBillType((amount.compareTo(BigDecimal.ZERO) >= 0) ? WalletConstant.WALLET_BILL_TYPE_IN : WalletConstant.WALLET_BILL_TYPE_OUT);
////		walletBillDTO.setCashBankNo(policyFeeSettleDTO.getBankName());
////		walletBillDTO.setCashCardNo(policyFeeSettleDTO.getCarNo());
////		walletBillDTO.setCashCardOwner(policyFeeSettleDTO.getOwnerName());
//		walletBillDTO.setCrtCde(userId);
//		walletBillDTO.setCrtTm(date);
//		walletBillDTO.setPayChannel(null);
//		walletBillDTO.setPayOrderNo(id);
//		walletBillDTO.setRemark("保行天下发放红包C,活动奖励ID号="+id);
//		walletBillDTO.setResultMsg("已结算");
//		walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_WALLET_ACTIVITY_BXTXA_STATUS6);
//		walletBillDTO.setTransType(WalletConstant.TRANS_TYPE_ACTIVITY_BXTXA_REDPACKET);
//		walletBillDTO.setUpdCde("system");
//		walletBillDTO.setUpdTm(date);
//		walletBillDTO.setUserId(userId);
//		walletBillDTO.setWalletId(walletDTO.getWalletId());
//		walletBillDao.insertSelective(walletBillDTO);
//		
//		logger.info("新增结算账单信息===>计算钱包余额");
//		WalletDTO walletUpdateDTO = new WalletDTO();
//		walletUpdateDTO.setUserId(userId);
//		walletUpdateDTO.setAmount(amount);
//		walletUpdateDTO.setAmountIncome(amount);
//		walletUpdateDTO.setVersionId(walletDTO.getVersionId());
//		walletService.updWalletWithAmountByUserId(walletUpdateDTO);
//		
//		logger.info("保行天下红包发放C发放完成，奖励活动ID="+id);
//		
//	}
//
//	@Override
//	@Log("自营出单奖励|嘉诚出单奖励")
//	public void addPolicySelfActivityWallet(PolicySalesActivityFeeDTO policySalesActivityFeeDTO) throws Exception {
//		logger.info("自营出单奖励|嘉诚出单奖励开始,PolicySalesActivityFeeDTO"+ToStringBuilder.reflectionToString(policySalesActivityFeeDTO, ToStringStyle.SHORT_PREFIX_STYLE));
//		WalletDTO walletDTO = walletService.getWalletByUserId(policySalesActivityFeeDTO.getUserId());
//		if(walletDTO == null){
//			throw new BusinessServiceException("钱包不存在,FeeID="+policySalesActivityFeeDTO.getPolicySalesActivityFeeId()+",userId="+policySalesActivityFeeDTO.getUserId()+",PolicyBaseId="+policySalesActivityFeeDTO.getPolicyBaseId());
//		}
//		if(policySalesActivityFeeDTO.getAmount() == null){
//			throw new BusinessServiceException("出单奖励金额不能为空,FeeID="+policySalesActivityFeeDTO.getPolicySalesActivityFeeId()+",userId="+policySalesActivityFeeDTO.getUserId()+",PolicyBaseId="+policySalesActivityFeeDTO.getPolicyBaseId());
//		}
//		if(StringUtils.isBlank(policySalesActivityFeeDTO.getTransType())){
//			throw new BusinessServiceException("出单奖励TransType不能为空,FeeID="+policySalesActivityFeeDTO.getPolicySalesActivityFeeId()+",userId="+policySalesActivityFeeDTO.getUserId()+",PolicyBaseId="+policySalesActivityFeeDTO.getPolicyBaseId());
//		}
//		if(StringUtils.isBlank(policySalesActivityFeeDTO.getBillType())){
//			throw new BusinessServiceException("出单奖励BillType不能为空,FeeID="+policySalesActivityFeeDTO.getPolicySalesActivityFeeId()+",userId="+policySalesActivityFeeDTO.getUserId()+",PolicyBaseId="+policySalesActivityFeeDTO.getPolicyBaseId());
//		}
//		Date date = new Date();
//		WalletBillDTO walletBillDTO = new WalletBillDTO();
//		walletBillDTO.setBillAmount(policySalesActivityFeeDTO.getAmount().setScale(2, RoundingMode.HALF_DOWN));
//		walletBillDTO.setBillId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
//		walletBillDTO.setBillType(policySalesActivityFeeDTO.getBillType());//update by zhaoxijun
//		walletBillDTO.setCrtCde(policySalesActivityFeeDTO.getUserId());
//		walletBillDTO.setCrtTm(date);
//		walletBillDTO.setPayChannel(null);
//		walletBillDTO.setPayOrderNo(policySalesActivityFeeDTO.getPolicySalesActivityFeeId());
//		walletBillDTO.setRemark("出单奖励,FeeID="+policySalesActivityFeeDTO.getPolicySalesActivityFeeId()+",PolicyBaseId="+policySalesActivityFeeDTO.getPolicyBaseId());
//		walletBillDTO.setResultMsg("已结算");
//		walletBillDTO.setTransType(policySalesActivityFeeDTO.getTransType());//update by zhaoxijun
//		
//		//11-6已结算  自营出单之后
//		//11-0已结算  自营出单之前
//		//11-1待激活 
//		//12-0待激活
//		//12-6已结算
//		if("12".equals(policySalesActivityFeeDTO.getTransType()) && policySalesActivityFeeDTO.getBillType().startsWith("W")){
//			walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_WALLET_POLICY_SEAL_ACTIVITY_FEE_STATUS0);
//		}
//		else if("11".equals(policySalesActivityFeeDTO.getTransType()) && policySalesActivityFeeDTO.getBillType().startsWith("W")){
//			walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_WALLET_POLICY_SEAL_ACTIVITY_FEE_STATUS1);
//		}
//		else{
//			walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_WALLET_POLICY_SEAL_ACTIVITY_FEE_STATUS6);
//		}
//		walletBillDTO.setUpdCde("system");
//		walletBillDTO.setUpdTm(date);
//		walletBillDTO.setUserId(policySalesActivityFeeDTO.getUserId());
//		walletBillDTO.setWalletId(walletDTO.getWalletId());
//		walletBillDao.insertSelective(walletBillDTO);
//		
//		logger.info("出单奖励===>更新钱包金额");
//		if(policySalesActivityFeeDTO.getBillType().startsWith("W")){//待激活
//			logger.info("待激活更新");
//			WalletDTO walletUpdateDTO = new WalletDTO();
//			walletUpdateDTO.setUserId(policySalesActivityFeeDTO.getUserId());
//			walletUpdateDTO.setAmountStay(policySalesActivityFeeDTO.getAmount());//待激活
//			walletUpdateDTO.setVersionId(walletDTO.getVersionId());
//			walletService.updWalletWithAmountByUserId(walletUpdateDTO);
//		}
//		else{//余额
//			logger.info("余额更新");
//			WalletDTO walletUpdateDTO = new WalletDTO();
//			walletUpdateDTO.setUserId(policySalesActivityFeeDTO.getUserId());
//			walletUpdateDTO.setAmount(policySalesActivityFeeDTO.getAmount());//余额
//			walletUpdateDTO.setAmountIncome(policySalesActivityFeeDTO.getAmount());
//			walletUpdateDTO.setVersionId(walletDTO.getVersionId());
//			walletService.updWalletWithAmountByUserId(walletUpdateDTO);
//		}
//		
//		logger.info("出单奖励结束,FeeID="+policySalesActivityFeeDTO.getPolicySalesActivityFeeId()+",userId="+policySalesActivityFeeDTO.getUserId()+",PolicyBaseId="+policySalesActivityFeeDTO.getPolicyBaseId());
//	}

//	@Override
//	public List<WalletBillDTO> getBillListByParam(String userId, String transType, String billType) {
//		logger.info("根据条件获取账单列表==>userId="+userId+",transType="+transType+",billType="+billType);
//		List<WalletBillDTO> list = null;
//		list = walletBillDao.getBillListByParam(userId, transType, billType);
//		logger.info("根据条件获取账单列表<==list=" + list);
//		return list;
//	}
	
	
}
