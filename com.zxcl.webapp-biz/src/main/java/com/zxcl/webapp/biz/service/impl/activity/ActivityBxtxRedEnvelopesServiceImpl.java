package com.zxcl.webapp.biz.service.impl.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxcl.bxtxmanage.biz.service.WalletSettleService;
import com.zxcl.bxtxmanage.dto.req.feeManage.WalletBillInsertRequestDTO;
import com.zxcl.webapp.biz.service.activity.ActivityBxtxRedEnvelopesService;
import com.zxcl.webapp.dto.PolicySalesActivityFeeDTO;
import com.zxcl.webapp.dto.activity.ActivityBxtxRedEnvelopesDTO;
import com.zxcl.webapp.integration.dao.PolicySalesActivityFeeDAO;
import com.zxcl.webapp.integration.dao.activity.ActivityBxtxRedEnvelopesDAO;

@Service
public class ActivityBxtxRedEnvelopesServiceImpl implements
		ActivityBxtxRedEnvelopesService {

	private static Logger logger = Logger.getLogger(ActivityBxtxRedEnvelopesServiceImpl.class);
	
	@Autowired
	private ActivityBxtxRedEnvelopesDAO activityBxtxRedEnvelopesDAO;
	
	@Autowired
	private PolicySalesActivityFeeDAO policySalesActivityFeeDAO;
	
	@Autowired
	private WalletSettleService walletSettleServicel;
	
	@Override
	public Integer getWallteCountForRedEnvelope(String userId,String openType) {
		
		return activityBxtxRedEnvelopesDAO.getTotalNumberOfDraws(userId, null,openType);
	}

	@Override
	public Integer getWallteCountForPolicyActivityFee(String userId) {
		return policySalesActivityFeeDAO.getPolicySalesCount(userId);
	}

	@Override
	public Integer getWallteRedEnvelopeCount(String userId, String batchNo,String openType) {
		
		return activityBxtxRedEnvelopesDAO.getTotalNumberOfDraws(userId, batchNo,openType);
		
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Map<String, String> updateLuckDraw(String userId,String batchNo, String type,String openType) throws Exception {
		
		if(StringUtils.equals(type, "BXTX")){
			try {
				int totalNumber = activityBxtxRedEnvelopesDAO.getTotalNumber(userId, batchNo, openType);
				int totalNumberLuckRraws = activityBxtxRedEnvelopesDAO.getTotalNumberOfDraws(userId, batchNo, openType);
				
				if(totalNumber < 1){
					Map<String, String> result = new HashMap<String, String>();
					result.put("result", "-10");
					return result;
				}
				if(totalNumber > 0 && totalNumberLuckRraws < 1){
					Map<String, String> result = new HashMap<String, String>();
					result.put("result", "-11");
					return result;
				}
				
				ActivityBxtxRedEnvelopesDTO redenv = activityBxtxRedEnvelopesDAO.getRedEnvelope(userId, batchNo,openType);
				if(redenv != null ){
					logger.info("已查询一条可用的红包数据，开始更新数据状态，红包ID：" + redenv.getId() + ",userId:" +userId);
					Integer count = activityBxtxRedEnvelopesDAO.updateRedEnvelope(redenv);
					if(count != null && count.intValue() > 0){
						logger.info("已锁定并更新红包数据，开始调用营销系统接口，红包ID：" + redenv.getId() + ",userId:" +userId);
						
						WalletBillInsertRequestDTO reqDTO = new WalletBillInsertRequestDTO();
						reqDTO.setBillType("I"); //收支类型,收入I,支出O,待激活WI，激活WO  {更新余额:金额大于等于0传I 小于0传O,  更新待激活金额:金额大于等于0传WI 小于0传WO}
						reqDTO.setAmountType("I3"); //I1-佣金 I2-分销 I3-活动 I4-充值  O1-提现O2-消费
						reqDTO.setAmount(redenv.getMoney());
						reqDTO.setUserId(redenv.getUserId());
						reqDTO.setTargetId("0");
						
						String msg = walletSettleServicel.inWalletService(reqDTO);
						if(StringUtils.isNotBlank(msg)){
							logger.info("已锁定并更新红包数据，但调用营销系统接口失败，红包ID：" + redenv.getId() + ",userId:" +userId);
							throw new Exception("处理红包失败，营销接口返回：" + msg);
						}
						
						logger.info("红包记录更新成功，营销系统接口调用成功，红包ID：" + redenv.getId() + ",userId:" +userId);
						
						Map<String, String> result = new HashMap<String, String>();
						result.put("result", "1");
						result.put("amount", redenv.getMoney().toString());
						
						return result;
					}
				}
			} catch (Exception e) {
				logger.error("打开红包失败", e);
				throw new Exception("打开红包失败",e);
			}
		}else if(StringUtils.equals(type, "BD")){
			try {
				PolicySalesActivityFeeDTO feeDTO = policySalesActivityFeeDAO.getALuckyDrawData(userId);
				
				if(feeDTO != null ){

					logger.info("已查询一条可用的爆点红包数据，policyId：" + feeDTO.getPolicySalesActivityFeeId() + ",userId:" +userId +",开始调用营销系统接口");
					
					
					WalletBillInsertRequestDTO reqDTO = new WalletBillInsertRequestDTO();
					reqDTO.setBillType("I"); //收支类型,收入I,支出O,待激活WI，激活WO  {更新余额:金额大于等于0传I 小于0传O,  更新待激活金额:金额大于等于0传WI 小于0传WO}
					reqDTO.setAmountType("I3"); //I1-佣金 I2-分销 I3-活动 I4-充值  O1-提现O2-消费
					reqDTO.setAmount(feeDTO.getAmount());
					reqDTO.setUserId(feeDTO.getPayeeUserId());
					reqDTO.setTargetId(feeDTO.getPolicySalesActivityFeeId());
					
					String msg = walletSettleServicel.inWalletService(reqDTO);
					if(StringUtils.isNotBlank(msg)){
						logger.info("打开爆点红包失败，policyId：" + feeDTO.getPolicySalesActivityFeeId() + ",userId:" +userId);
						throw new Exception("处理爆点红包失败，营销接口返回：" + msg);
					}
					
					logger.info("打开爆点红包成功；policyId：" + feeDTO.getPolicySalesActivityFeeId() + ",userId:" +userId + "amount:" + feeDTO.getAmount().toString());
					
					Map<String, String> result = new HashMap<String, String>();
					result.put("result", "1");
					result.put("amount", feeDTO.getAmount().toString());
					
					return result;
				}
			} catch (Exception e) {
				logger.error("打开爆点红包失败", e);
				throw new Exception("打开爆点红包失败",e);
			}
		}
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("success", "0");
		
		return result;
	}

}
