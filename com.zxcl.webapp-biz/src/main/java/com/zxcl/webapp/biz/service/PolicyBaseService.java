package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.PolicyBaseDTO;

/**
 * @ClassName:佣金提现 
 * @Description:
 * @author zxj
 * @date 
 */
public interface PolicyBaseService {
	/**
	 * 新增费用计算记录数据
	 * @param policyBaseDTO
	 * @throws BusinessServiceException
	 */
	public void insertPolicyBase(PolicyBaseDTO policyBaseDTO) throws BusinessServiceException;
	
	/**
	 * 获取费用计算记录DTO
	 * @param orderId 订单ID（保行天下）
	 * @param insId 保险公司ID 非顶级ID
	 * @throws BusinessServiceException
	 * @return PolicyBaseDTO
	 */
	public List<PolicyBaseDTO> getPolicyBase(String orderId, String insId) throws BusinessServiceException;
	
	/**
	 * 添加费用计算记录
	 * @param orderId
	 * @param insId
	 * @throws BusinessServiceException
	 */
	public void addPolicyBase(String orderId, String insId) throws BusinessServiceException;
	
}
