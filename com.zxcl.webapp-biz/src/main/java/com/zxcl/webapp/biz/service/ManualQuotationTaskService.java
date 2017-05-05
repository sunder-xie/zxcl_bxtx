package com.zxcl.webapp.biz.service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.ManualQuotationTaskDTO;

/**
 * 人工报价Service
 * @author zx
 *
 */
public interface ManualQuotationTaskService {
	
	/**
	 * 保存人工报价信息
	 * @param manualQuotationTaskDTO
	 * @throws BusinessServiceException
	 */
	public void insert(ManualQuotationTaskDTO manualQuotationTaskDTO) throws BusinessServiceException;
	
	/**
	 * 创建任务组装数据
	 * @param inquiryId 询价单号
	 * @param userId 用户ID
	 * @param quoteId 报价单ID
	 * @param insId 保险公司ID
	 * @param teamId 团队ID
	 * @return
	 * @throws BusinessServiceException
	 */
	public ManualQuotationTaskDTO createTaskDataAssembly(String inquiryId,String userId,String quoteId,String insId,String teamId) throws BusinessServiceException;
	
	/**
	 * 撤回人工报价任务
	 * @param inquiryId 询价单号
	 * @param userId 用户号
	 * @throws BusinessServiceException
	 */
	public void withdrawQuotn(String inquiryId,String userId) throws BusinessServiceException;
	
	/**
	 * 根据报价单号获取人工报价信息
	 * @param quoteId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ManualQuotationTaskDTO queryByQuoteId(String quoteId) throws BusinessServiceException;
	
	/**
	 * 核保任务信息相关修改
	 * @param quoteId 报价单号
	 * @param userId 用户ID
	 * @throws BusinessServiceException
	 */
	public void underwrite(String quoteId,String userId) throws BusinessServiceException;
}
