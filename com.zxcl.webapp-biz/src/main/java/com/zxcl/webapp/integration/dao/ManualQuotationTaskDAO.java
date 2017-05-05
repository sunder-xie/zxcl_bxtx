package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.ManualQuotaNotfiyToQuoterDTO;
import com.zxcl.webapp.dto.ManualQuotationTaskDTO;

/**
 * 人工报价DAO
 * @author zx
 *
 */
public interface ManualQuotationTaskDAO {
	
	/**
	 * 保存人工报价信息
	 * @param manualQuotationTaskDTO
	 * @throws Exception
	 */
	public void insert(ManualQuotationTaskDTO manualQuotationTaskDTO) throws Exception;
	
	/**
	 * 撤回人工报价任务
	 * @param inquiryId 询价单号
	 * @param userId 用户ID
	 * @throws Exception
	 */
	public void withdrawQuotn(@Param("inquiryId")String inquiryId,@Param("userId")String userId) throws Exception;
	
	/**
	 * 根据询价单号获取人工报价任务
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public List<ManualQuotationTaskDTO> queryInfoByInquiryId(String inquiryId) throws Exception;
	
	/**
	 * 根据报价单号获取人工报价信息
	 * @param quoteId
	 * @return
	 * @throws Exception
	 */
	public ManualQuotationTaskDTO queryByQuoteId(String quoteId) throws Exception;
	
	/**
	 * 修改任务状态
	 * @param status 状态
	 * @param quoteId 报价单号
	 * @param userId 用户ID
	 * @throws Exception
	 */
	public void underwriteTypeUpdate(@Param("status")String status,@Param("quoteId")String quoteId,@Param("userId")String userId) throws Exception;
	
	/**
	 * 获取要通知到报价员列表信息.
	 * @return
	 */
	public List<ManualQuotaNotfiyToQuoterDTO> getNotifyQuoters(@Param("start")int start,@Param("end")int end);
	
	
}
