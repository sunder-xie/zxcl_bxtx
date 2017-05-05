package com.zxcl.webapp.biz.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.PolicySalesActivityFeeService;
import com.zxcl.webapp.biz.service.WalletBillService;
import com.zxcl.webapp.dto.PolicySalesActivityFeeDTO;
import com.zxcl.webapp.integration.dao.PolicySalesActivityFeeDAO;

@Service
public class PolicySalesActivityFeeServiceImpl implements
		PolicySalesActivityFeeService {
	
	private static Logger logger = Logger.getLogger(PolicySalesActivityFeeServiceImpl.class);
	
	@Autowired
	private PolicySalesActivityFeeDAO policySalesActivityFeeDAO;
	
	@Autowired
	private WalletBillService walletBillService;
	
	@Override
	public Integer getUserPolicySalesCount(String userId) {
		return policySalesActivityFeeDAO.getPolicySalesCount(userId);
	}

//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public BigDecimal updateUserLuckDraw(String userId) throws BusinessServiceException {
//		
//		PolicySalesActivityFeeDTO policySalesActivityFeeDTO = policySalesActivityFeeDAO.getALuckyDrawData(userId);
//		if(policySalesActivityFeeDTO == null){
//			return new BigDecimal(-1);
//		}
//		
//		Integer result = policySalesActivityFeeDAO.updateLuckDraw(userId, policySalesActivityFeeDTO.getPolicySalesActivityFeeId());
//		if(result == null || result.intValue() < 1){
//			return new BigDecimal(-2);
//		}
//		
//		try {
//			walletBillService.addWalletPolicySaleActivityFeeBill(policySalesActivityFeeDTO);
//		} catch (BusinessServiceException e) {
//			logger.error("嘉诚出商业险单奖励活动，发放奖励出错", e);
//			throw new BusinessServiceException(e);
//		}
//		
//		return policySalesActivityFeeDTO.getAmount().setScale(2, RoundingMode.HALF_DOWN);
//	}

}
