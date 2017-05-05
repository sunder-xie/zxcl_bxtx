package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.IdentityInfoService;
import com.zxcl.webapp.biz.service.MicroAgentService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.biz.service.WalletBankService;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.BankDTO;
import com.zxcl.webapp.dto.BankOcrDTO;
import com.zxcl.webapp.dto.MicroAgentDTO;
import com.zxcl.webapp.dto.MicroAgentDTOKey;
import com.zxcl.webapp.dto.MicroApproveDTO;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.wallet.WalletBankDTO;
import com.zxcl.webapp.integration.dao.AgencyDAO;
import com.zxcl.webapp.integration.dao.BankDAO;
import com.zxcl.webapp.integration.dao.BankOcrDAO;
import com.zxcl.webapp.integration.dao.MicroDAO;
import com.zxcl.webapp.integration.dao.UserDAO;
import com.zxcl.webapp.integration.dao.wallet.WalletBankDAO;
import com.zxcl.webapp.util.constants.WalletConstant;

/**
 * @ClassName: 
 * @Description: 用户钱包银行卡管理
 * @author zxj
 * @date 
 */

@Service
@Transactional(rollbackFor=Exception.class)
public class WalletBankServiceImpl implements WalletBankService {
	
	private static final String WALLET_BANK_PREID = "BK";
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private WalletBankDAO wbDao;
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private MicroDAO microDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MicroAgentService microAgentService;
	
	@Autowired
	private ParamConfigService configService;
	
	@Autowired
	private AgencyDAO agencyDAO;
	
	@Autowired
	private IdentityInfoService identityInfoService;
	
	@Autowired
	private BankOcrDAO bankOcrDAO;
	
	@Autowired
	private BankDAO bankDAO;
	
	@Override
	@Log("查询用户银行卡列表")
	public List<WalletBankDTO> selectWalletBankListByUserId(String userId) throws BusinessServiceException {
		List<WalletBankDTO> list = wbDao.selectByUserId(userId);
		
		//当前用户标记为非名义代理人银行卡
		if(CollectionUtils.isNotEmpty(list)){
			for(WalletBankDTO item : list){
				item.setIsAgent("0");
			}
		}
		
		if(walletCanUseAgent(userId)){//是否启用名义代理人
			try {
				
				//查询当前用户名义代理人的卡
				MicroDTO microDTO = microService.getMicroByUserId(userId);
				if(null != microDTO){
					List<MicroAgentDTO> microAgentList = microAgentService.selectMicroAgentListByMicroId(microDTO.getMicro_id());
					if(CollectionUtils.isNotEmpty(microAgentList)){
						List<WalletBankDTO> agentList =  new ArrayList<WalletBankDTO>();
						List<WalletBankDTO> itemList = null;
						//遍历所有代理人，查询出所有关联卡
						for(MicroAgentDTO item : microAgentList){
							itemList = wbDao.selectByUserId2(microService.getUserIdByMicId(item.getMicroIdAgent()), userId);
							if(CollectionUtils.isNotEmpty(itemList)){
								agentList.addAll(itemList);
							}
						}
						
						
						
						//添加名义代理人卡到总列表
						if(CollectionUtils.isNotEmpty(agentList)){
							for(WalletBankDTO item : agentList){
								item.setIsAgent("1");
							}
							list.addAll(agentList);
						}
					}
				}
			} catch (Exception e) {
				logger.error("查询名义代理人银行失败", e);
			}
		}
		
		
		
		
		
		//将最后的集合银行卡号部分字符用*号代理
		if(CollectionUtils.isNotEmpty(list)){
			for(WalletBankDTO item : list){
				if(null == item.getCardNo() || item.getCardNo().length() <= 4){
					continue;
				}
				item.setCardNo("**** **** **** " + item.getCardNo().substring(item.getCardNo().length() - 4));
			}
		}
		return list;
	}

	@Override
	@Log("查询银行卡明细")
	public WalletBankDTO selectWalletBankById(String walletBankId) throws BusinessServiceException {
		logger.info("walletBankId=" + walletBankId);
		return wbDao.selectByPrimaryKey(walletBankId);
	}
	
	/**
	 * findBankOcrByCardNo
	 * @param cardNo
	 * @return
	 */
	protected BankOcrDTO findBankOcrByCardNo(String cardNo){
		logger.info("findBankOcrByCardNo, cardNo="+cardNo);
		BankOcrDTO result = null;
		int[] is = {9,8,6,7,5,10,4,3,2};
		for(int i : is){
			result = bankOcrDAO.selectBySelitic(cardNo.length(), cardNo.substring(0, i));
			if(null != result){
				break;
			}
		}
		logger.info("result="+JSONObject.toJSONString(result));
		return result;
	}
	
	/**
	 * getTlBankNoByOcrBankNo
	 * @param ocrBankNo
	 * @return
	 */
	protected String getTlBankNoByOcrBankNo(String ocrBankNo){
		logger.info("getTlBankNoByOcrBankNo==>ocrBankNo="+ocrBankNo);
		String tlBankNo = null;
		tlBankNo = bankOcrDAO.getTlBankNoByOcrBankNo(ocrBankNo);
		logger.info("getTlBankNoByOcrBankNo<==tlBankNo="+tlBankNo);
		return tlBankNo;
	}

	@Override
	@Log("添加银行卡")
	public void addWalletBank(WalletBankDTO walletBankDTO) throws BusinessServiceException {
		logger.info("walletBankDTO=" + walletBankDTO.toString());
		
		// walletBankDTO.getBankNo();  以后要做成配置
		if(null == walletBankDTO || null == walletBankDTO.getUserId()){
			throw new BusinessServiceException("操作人不能为空");
		}
//		if(StringUtils.isBlank(walletBankDTO.getBankName())){
//			throw new BusinessServiceException("银行卡名称不能为空");
//		}
		if(StringUtils.isBlank(walletBankDTO.getCardNo())){
			throw new BusinessServiceException("银行卡号不能为空");
		}
		walletBankDTO.setCardNo(walletBankDTO.getCardNo().replace(" ", ""));
		
		if(StringUtils.isBlank(walletBankDTO.getCardOwner())){
			throw new BusinessServiceException("银行卡持卡人不能为空");
		}
		if(!walletBankDTO.getCardOwner().matches("[\\u4e00-\\u9fa5]+")){
			throw new BusinessServiceException("持卡人为中文名称，不能包含字母数字等特殊字符");
		}
		if(!walletBankDTO.getCardNo().matches("^\\d{15,19}$")){
			throw new BusinessServiceException("银行卡号格式不正确,请输入16-19位数字的银行卡号");
		}
		if(selectBankCountByBankNameAndCarNo(walletBankDTO.getBankName(), walletBankDTO.getCardNo()) >= 1){
			throw new BusinessServiceException("平台已存在该银行卡，请勿重复添加");
		}
		
		//通过银行卡号确认银行名称及银行编号
		BankOcrDTO ocr = findBankOcrByCardNo(walletBankDTO.getCardNo());
		if(null == ocr){
			throw new BusinessServiceException("系统暂不支持该卡所属银行");
		}
		String tlBankNo = getTlBankNoByOcrBankNo(ocr.getOcrBankNo());
		
		if(StringUtils.isBlank(tlBankNo)){
			throw new BusinessServiceException("系统暂不支持["+ocr.getBankName()+"-"+ocr.getCardType()+"]");
		}
		walletBankDTO.setBankNo(tlBankNo);
		
		BankDTO bank = bankDAO.findBankByCode(tlBankNo);
		if(null == bank || StringUtils.isBlank(bank.getName())){
			throw new BusinessServiceException("系统错误: t_bank_ocr_tl存在["+tlBankNo+"], t_bank未找到相关配置,请联系管理员");
		}
		walletBankDTO.setBankName(bank.getName());
		
		
		
		
		walletBankDTO.setUpdCde(walletBankDTO.getUserId());
		walletBankDTO.setCrtCde(walletBankDTO.getUserId());
		String agentMicroId = walletBankDTO.getAgentMicroId();
		if(StringUtils.isNotBlank(agentMicroId) && !agentMicroId.trim().toLowerCase().equals("null")){
			
			//名义代理人校验
			if(!walletCanUseAgent(walletBankDTO.getUserId())){
				throw new BusinessServiceException("系统暂停名义代理人服务");
			}
			MicroAgentDTO microAgentDTO = microAgentService.selectByPrimaryKey(new MicroAgentDTOKey(microService.getMicroByUserId(walletBankDTO.getUserId()).getMicro_id(), agentMicroId));
			if(null == microAgentDTO){
				throw new BusinessServiceException("该名义代理人未与你关联");
			}
			else{
				
				//得到代理人userId
				String agentUserId  = microService.getUserIdByMicId(agentMicroId);
				
				//代理人下面最多只能添加一张银行卡
				int cardCount = wbDao.selectAgentUserCardCount(walletBankDTO.getUserId(), agentUserId);
				if(cardCount >= 1){
					throw new BusinessServiceException("名义代理人最多只能添加一张银行卡");
				}
				walletBankDTO.setUserId(agentUserId);
			}
		}
		else{
			
			//认证通过的用户: 持卡人需和认证姓名一致
			MicroApproveDTO ma = identityInfoService.findConfirm(walletBankDTO.getUserId(), false);
			if(null != ma && "2".equals(ma.getApproveState()+"")){//认证通过
				if(!walletBankDTO.getCardOwner().equals(ma.getMicroRealName())){
					throw new BusinessServiceException("银行卡持卡人姓名必须和实名认证通过后的姓名一致");
				}
			}
			else{
				//未认证通过的: 如果银行卡姓名与小微表姓名不一致的，且是第一次新增银行 || 小微姓名为空的，则更新小微表姓名为该银行卡姓名
				int r = isOnceWbAndMicNameNotSame(walletBankDTO.getUserId(), walletBankDTO.getCardOwner());
				if(r == 1 || r == -1){
					logger.info("修改小微表用户表姓名为持卡人姓名");
					microDAO.updateMicroName(walletBankDTO.getUserId(), walletBankDTO.getCardOwner());
					userDAO.updateUserName(walletBankDTO.getUserId(), walletBankDTO.getCardOwner());
				}
			}
		}
		
		
		walletBankDTO.setId(WALLET_BANK_PREID + DateUtil.primaryKey());
		wbDao.insertSelective(walletBankDTO);
	}
	
	
	@Override
	@Log("用户是否第一次新增银行卡")
	public boolean isOnceWb(String userId) throws BusinessServiceException {
		boolean result = false;
		int count = wbDao.hisToryAddCount(userId);
		logger.info("count="+count);
		if(count == 0){
			result = true;//第一次新增
		}
		logger.info("result="+result);
		return result;
	}
	
	
	@Override
	@Log("用户是否第一次新增银行卡，且持卡人与小微姓名不一致")
	public int isOnceWbAndMicNameNotSame(String userId, String cardOwnerName) throws BusinessServiceException {
		int result = 0;
		if(null == userId){
			throw new BusinessServiceException("用户ID必传");
		}
		
		if(StringUtils.isBlank(cardOwnerName)){
			throw new BusinessServiceException("持卡人姓名不能为空");
		}
		MicroDTO micro = microService.getMicroByUserId(userId);
		if(null == micro){
			throw new BusinessServiceException("小微不存在,userId="+userId);
		}
		if(StringUtils.isBlank(micro.getMicro_name())){
			logger.info("小微姓名为空");
			result = -1;//小微姓名为空
			return result;
		}
		
		//认证通过的用户: 持卡人需和认证姓名一致
		MicroApproveDTO ma = identityInfoService.findConfirm(userId, false);
		if(null != ma && "2".equals(ma.getApproveState()+"")){//认证通过
			if(!cardOwnerName.equals(ma.getMicroRealName())){
				return -1;
			}
		}
		
		//用户是否第一次新增银行卡
		if(isOnceWb(userId)){
			if(!micro.getMicro_name().equals(cardOwnerName)){
				logger.info("持卡人与小微姓名不一致");
				result = 1;//第一次新增银行卡&姓名不一致
			}
		}
		logger.info("result="+result);
		return result;
	}

	@Override
	@Log("删除银行卡")
	public void delWalletBankById(String userId, String walletBankId) throws BusinessServiceException {
		logger.info("walletBankId=" + walletBankId);
		WalletBankDTO card = selectWalletBankById(walletBankId);
		if(null == card){
			throw new BusinessServiceException("该卡号不存在");
		}
		boolean flag = false;//是否自己的银行卡
		if(card.getUserId().equals(userId)){
			flag = true;
		}
		else{
			List<MicroAgentDTO> list = microAgentService.selectMicroAgentListByMicroId(microService.getMicroByUserId(userId).getMicro_id());
			if(CollectionUtils.isNotEmpty(list)){
				for(MicroAgentDTO item : list){
					if(card.getUserId().equals(microService.getUserIdByMicId(item.getMicroIdAgent()))){
						flag = true;
						break;
					}
				}
			}
		}
		
		if(!flag){
			throw new BusinessServiceException("删除失败, 非本人银行卡或非本人名义代理人银行卡。");
		}
		wbDao.deleteByPrimaryKey(walletBankId);
	}

	@Override
	@Log("更新银行卡")
	public void updateWalletBank(WalletBankDTO walletBankDTO) throws BusinessServiceException {
		logger.info("walletBankDTO=" + walletBankDTO.toString());
		if(null == walletBankDTO || null == walletBankDTO.getUserId()){
			throw new BusinessServiceException("操作人不能为空");
		}
		if(StringUtils.isBlank(walletBankDTO.getBankName())){
			throw new BusinessServiceException("银行卡名称不能为空");
		}
		if(StringUtils.isBlank(walletBankDTO.getCardNo())){
			throw new BusinessServiceException("银行卡号不能为空");
		}
		if(StringUtils.isBlank(walletBankDTO.getCardOwner())){
			throw new BusinessServiceException("银行卡持卡人不能为空");
		}
		walletBankDTO.setUpdCde(walletBankDTO.getUserId());
		wbDao.updateByPrimaryKeySelective(walletBankDTO);
	}

	@Override
	@Log("查询银行卡数量")
	public Integer selectWalletBankCountByUserId(String userId) throws BusinessServiceException {
		if(null == userId){
			throw new BusinessServiceException("操作人不能为空");
		}
		Integer count = wbDao.selectWalletBankCountByUserId(userId);
		logger.info("count=" + count);
		return null == count ? 0 : count;
	}

	
	@Override
	@Log("校验：银行卡账户名与帐号姓名是否一致")
	public boolean cashBankCheck(String userId, String walletBankId) throws BusinessServiceException {
		if(null == userId){
			logger.info("用户名不能为空");
			throw new BusinessServiceException("用户名不能为空");
		}
		if(StringUtils.isBlank(walletBankId)){
			logger.info("校验失败：银行卡不能为空");
			throw new BusinessServiceException("校验失败：银行卡不能为空");
		}
		WalletBankDTO wbDTO = wbDao.selectByPrimaryKey(walletBankId);
		if(null == wbDTO){
			logger.info("校验失败：银行卡不存在");
			throw new BusinessServiceException("校验失败：银行卡不存在");
		}
		
		MicroDTO microDTO = null;
		if(userId.equals(wbDTO.getUserId())){
			logger.info("本人卡");
			
			//本人卡,如果实名认证通过的要和实名认证通过姓名比较
			MicroApproveDTO ma = identityInfoService.findConfirm(userId, false);
			if(null != ma && "2".equals(ma.getApproveState()+"")){//认证通过
				if(!wbDTO.getCardOwner().equals(ma.getMicroRealName())){
					return false;
				}
				else{
					return true;//验证通过
				}
			}
			microDTO = microService.getMicroByUserId(userId);
		}
		else{
			logger.info("名义代理人卡");
			if(!walletCanUseAgent(userId)){
				throw new BusinessServiceException("校验失败：系统暂停名义代理人服务");
			}
			microDTO = microService.getMicroByUserId(wbDTO.getUserId());
		}
		
		if(null == microDTO){
			logger.info("校验失败：小微账户不存在");
			throw new BusinessServiceException("校验失败：小微账户不存在");
		}
		if(StringUtils.isBlank(microDTO.getMicro_name())){
			logger.info("校验失败：小微账户真实姓名为空");
			throw new BusinessServiceException("校验失败：小微账户真实姓名为空");
		}
		if(microDTO.getMicro_name().equals(wbDTO.getCardOwner())){
			return true;
		}
		return false;
	}

	@Override
	public int selectBankCountByBankNameAndCarNo(String bankName, String cardNo) throws BusinessServiceException {
		return wbDao.selectBankCountByBankNameAndCarNo(bankName , cardNo);
	}

	@Override
	@Log("是否能使用名义代理人")
	public boolean walletCanUseAgent(String userId) {
		
		//总控
		if(!"Y".equals(configService.getValueByKey(WalletConstant.KEY_WALLET_CAN_USE_AGENT))){
			logger.info("总控校验结果：停止钱包名义代理人使用。");
			return false;
		}
		
		//代理控制
		String taxRule = agencyDAO.selectAgencyTaxRuleByUserId(userId);
		if(!"1".equals(taxRule) && !"2".equals(taxRule)){
			logger.info("代理校验结果：停止钱包名义代理人使用。");
			return false;
		}
		
		logger.info("校验通过");
		return true;
	}


}
