package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.bxtx.ha.intf.util.DateUtil;
import com.zxcl.webapp.biz.service.MessageSendProvideService;
import com.zxcl.webapp.biz.service.WalletBillService;
import com.zxcl.webapp.biz.service.WalletCoreService;
import com.zxcl.webapp.biz.service.WalletRmiService;
import com.zxcl.webapp.biz.util.Log;
import com.zxcl.webapp.dto.PolicySalesActivityFeeDTO;
import com.zxcl.webapp.dto.wallet.WalletBillDTO;
import com.zxcl.webapp.dto.wallet.WalletDTO;
import com.zxcl.webapp.dto.wallet.response.BillBaseRspDTO;
import com.zxcl.webapp.util.constants.WalletConstant;

/**
 * @ClassName: 
 * @Description:钱包远程回调
 * @author zxj
 * @date 
 */
@Service
@Transactional(rollbackFor=Exception.class)
@Primary
public class WalletRmiServiceImpl implements WalletRmiService {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private WalletBillService billService;
	
	@Autowired
	private MessageSendProvideService messageSendProvideService;
	
	@Autowired
	private WalletCoreService walletService;

//	@Override
//	@Log("远程回调更新账单状态")
//	public void updBillStatus(BillBaseRspDTO billBaseRspDTO) throws Exception {
//		if(null == billBaseRspDTO || billBaseRspDTO.getPayOrderNo() == null){
//			logger.info("结算单号不能为空");
//			throw new Exception("结算单号不能为空");
//		}
//		if(null == billBaseRspDTO.getAction()){
//			logger.info("动作不能为空");
//			throw new Exception("动作不能为空");
//		}
//		logger.info("结算单号：" + billBaseRspDTO.getPayOrderNo());
//		WalletBillDTO billDTO = billService.getWalletBillByPayOrderNo(billBaseRspDTO.getPayOrderNo());
//		if(null == billDTO){
//			logger.info("未查询到账单,结算单号" + billBaseRspDTO.getPayOrderNo());
//			throw new Exception("未查询到账单,结算单号" + billBaseRspDTO.getPayOrderNo());
//		}
//		WalletDTO walletDTO = walletService.getWalletByUserId(billDTO.getUserId());
//		if(null == walletDTO){
//			throw new Exception("用户【"+billDTO.getUserId()+"】钱包不存在");
//		}
//		
//		//审核回调
//		if(BillBaseRspDTO.ACTION_WALLET_CHECK.equals(billBaseRspDTO.getAction())){
//			if(!WalletConstant.TRANS_TYPE_TIXIAN_STATUS_CHECKING.equals(billDTO.getStatus())){
//				logger.info("审核回调校验：结算单号状态："+billDTO.getStatus()+",当前状态不是审核中状态，操作失败"+",结算单号：" + billBaseRspDTO.getPayOrderNo());
//				throw new Exception("审核回调校验：结算单号状态："+billDTO.getStatus()+",当前状态不是审核中状态，操作失败"+",结算单号：" + billBaseRspDTO.getPayOrderNo());
//			}
//			updBillTXCheckStatus(billBaseRspDTO, billDTO, walletDTO.getVersionId());
//			return;
//		}
//		
//		//提现回调   2016年6月3日17:07:35改：跳过审核阶段
//		else if(BillBaseRspDTO.ACTION_WALLET_PAY.equals(billBaseRspDTO.getAction())){
//			if(!WalletConstant.TRANS_TYPE_TIXIAN_STATUS_CHECKING.equals(billDTO.getStatus()) && !WalletConstant.TRANS_TYPE_TIXIAN_STATUS_CHECKSUCC.equals(billDTO.getStatus())){
//				logger.info("提现回调校验：结算单号状态："+billDTO.getStatus()+",当前状态不是提现中状态，操作失败"+",结算单号：" + billBaseRspDTO.getPayOrderNo());
//				throw new Exception("提现回调校验：结算单号状态："+billDTO.getStatus()+",当前状态不是提现中状态，操作失败"+",结算单号：" + billBaseRspDTO.getPayOrderNo());
//			}
//			updBillTXPayStatus(billBaseRspDTO, billDTO, walletDTO.getVersionId());
//			return ;
//		}else{
//			logger.info("该动作未找到，动作="+billBaseRspDTO.getAction());
//			throw new Exception("该动作未找到，动作="+billBaseRspDTO.getAction());
//		}
//	}
	
	
//	@Log("远程回调更新账单状态-提现支付回调")
//	public void updBillTXPayStatus(BillBaseRspDTO billBaseRspDTO, WalletBillDTO billDTO, String versionId) throws Exception{
//		logger.info("提现支付回调开始...");
//		WalletBillDTO walletBillDTO = new WalletBillDTO();
//		walletBillDTO.setBillId(billDTO.getBillId());
//		walletBillDTO.setTransType(billDTO.getTransType());
//		walletBillDTO.setUserId(billDTO.getUserId());
//		walletBillDTO.setUpdCde("BXTX");
//		walletBillDTO.setUpdTm(new Date());
//		walletBillDTO.setBillAmount(billDTO.getBillAmount());
//		walletBillDTO.setOldStatus(billDTO.getStatus());
//		if(!"0".equals(billBaseRspDTO.getSuccess())){
//			logger.info("提现支付失败，原因："+billBaseRspDTO.getReason()+" 结算单号："+billBaseRspDTO.getPayOrderNo());
//			try {
//				messageSendProvideService.sendMessageWithCashFailed(billDTO.getUserId(), "抱歉，提现失败", billDTO.getBillAmount().toString()+"元", DateUtil.date2String(new Date()), "如有疑问请联系保行天下客服,感谢你的支持！");
//			} catch (Exception e) {
//				logger.error("消息推送失败", e);
//			}
//			walletBillDTO.setResultMsg(billBaseRspDTO.getReason());
//			walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_TIXIAN_STATUS_PAYFAIL);
//		}else{
//			walletBillDTO.setResultMsg("提现成功");
//			try {
//				messageSendProvideService.sendMessageWithCashSuccess(billDTO.getUserId(), "恭喜你，提现成功", billDTO.getBillAmount().toString()+"元", DateUtil.date2String(new Date()), "如有疑问请联系保行天下客服,感谢你的支持！");
//			} catch (Exception e) {
//				logger.error("消息推送失败", e);
//			}
//			logger.info("提现支付成功，结算单号："+billBaseRspDTO.getPayOrderNo());
//			walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_TIXIAN_STATUS_PAYSUCC);
//		}
		
		//更新账单状态
//		billService.updWalletBillStatusWithCash(walletBillDTO, versionId);
		
//		logger.info("提现支付回调结束...");
//	}
	
//	@Log("远程回调更新账单状态-提现审核回调")
//	public void updBillTXCheckStatus(BillBaseRspDTO billBaseRspDTO, WalletBillDTO billDTO, String versionId) throws Exception{
//		logger.info("提现审核回调开始...");
//		WalletBillDTO walletBillDTO = new WalletBillDTO();
//		walletBillDTO.setBillId(billDTO.getBillId());
//		walletBillDTO.setTransType(billDTO.getTransType());
//		walletBillDTO.setUserId(billDTO.getUserId());
//		walletBillDTO.setUpdCde("BXTX");
//		walletBillDTO.setUpdTm(new Date());
//		walletBillDTO.setBillAmount(billDTO.getBillAmount());
//		walletBillDTO.setOldStatus(billDTO.getStatus());
//		if(!"0".equals(billBaseRspDTO.getSuccess())){
//			logger.info("提现审核未通过，原因："+billBaseRspDTO.getReason());
//			try {
//				messageSendProvideService.sendMessageWithCashCheck(billDTO.getUserId(), "抱歉，提现审核未通过", billDTO.getBillAmount().toString()+"元", billDTO.getCashCardOwner(), DateUtil.date2String(new Date()), "如有疑问请联系保行天下客服,感谢你的支持！");
//			} catch (Exception e) {
//				logger.error("消息推送失败", e);
//			}
//			walletBillDTO.setResultMsg(billBaseRspDTO.getReason());
//			walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_TIXIAN_STATUS_CHECKFAIL);
//		}else{
//			try {
//				messageSendProvideService.sendMessageWithCashCheck(billDTO.getUserId(), "恭喜你，提现审核已通过", billDTO.getBillAmount().toString()+"元", billDTO.getCashCardOwner(), DateUtil.date2String(new Date()), "如有疑问请联系保行天下客服,感谢你的支持！");
//			} catch (Exception e) {
//				logger.error("消息推送失败", e);
//			}
//			walletBillDTO.setResultMsg("审核已通过，处理中...");
//			logger.info("提现审核通过，结算单号："+billBaseRspDTO.getPayOrderNo());
//			walletBillDTO.setStatus(WalletConstant.TRANS_TYPE_TIXIAN_STATUS_CHECKSUCC);
//		}
		
		//更新账单状态
//		billService.updWalletBillStatusWithCash(walletBillDTO, versionId);
		
//		logger.info("提现审核回调结束...");
//	}


//	@Override
//	@Log("远程调用新增自营出单奖励")
//	public HashMap<String, String> policySelfActivityWallet(ArrayList<PolicySalesActivityFeeDTO> policySalesActivityFeeDTOList) throws Exception {
//		HashMap<String, String> resultMap = new HashMap<String, String>(2);
//		resultMap.put("status", "4");
//		resultMap.put("msg", "无服务");
//		return resultMap;
//		String status = "";
//		String msg = "";
//		
//		
//		if(CollectionUtils.isNotEmpty(policySalesActivityFeeDTOList)){
//			for(PolicySalesActivityFeeDTO policySalesActivityFeeDTO : policySalesActivityFeeDTOList){
//				billService.addPolicySelfActivityWallet(policySalesActivityFeeDTO);
//			}
//			status = "0";
//			msg = "结算成功";
//		}
//		else{
//			status = "4";
//			msg = "结算失败：结算列表为空";
//		}
//		
//		
//		
//		resultMap.put("status", status);
//		resultMap.put("msg", msg);
//		return resultMap;
//	}
}
