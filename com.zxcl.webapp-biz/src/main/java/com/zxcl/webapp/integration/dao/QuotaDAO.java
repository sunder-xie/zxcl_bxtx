package com.zxcl.webapp.integration.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;



import com.zxcl.webapp.dto.QuotaDTO;

/**
 * 报价后的信息
 * 
 * @author 5555
 *
 */
public interface QuotaDAO {

	/**
	 * 报价基础信息
	 * 
	 * @param quota
	 */
	public void insert(QuotaDTO quota) throws Exception;

	/**
	 * 获取单个对象
	 * 
	 * @param quota
	 * @return
	 */
	public QuotaDTO get(QuotaDTO quota) throws Exception;

	/**
	 * 插入支付信息
	 */
	public void insertOrderPay(String quotaId) throws Exception;

	public QuotaDTO getByQuotaId(String quotaid) throws Exception;
	
	/**
	 * 
	* @Title: peopleAaskQueryDAO
	* @Description: 人工报价查询
	* @param @return
	* @param @throws BusinessServiceException
	* @return List<QuotaDTO> 
	* @throws
	 */
	public List<QuotaDTO> peopleAaskQueryDAO(String microId) throws Exception;
	/**
	 * 
	* @Title: infoViewByInquiryIDDAO
	* @Description: 综合查询显示
	* @param  inquiryId 询价单号
	* @param @throws Exception
	* @return QuotaDTO
	* @throws
	 */
	public List<QuotaDTO> infoViewByInquiryIDDAO(String inquiryId) throws Exception;


	/**
	 * 根据小薇编码查找报价的保险公司信息
	 * 
	 * @param microId
	 * @return
	 */
	public List<QuotaDTO> getQuotasByMicId(String microId) throws Exception;

	public List<QuotaDTO> getQuotasByInqueryId(String inquiryId)
			throws Exception;

	/**
	 * 更新报价单状态
	 * 
	 * @param quota
	 * @return
	 * @throws Exception
	 */
	public void updateQuotaStatusByInquiryId(
			@Param("status") String status, @Param("inquiryId") String inquiryId,@Param("updCde")String updCde)
			throws Exception;
	
	/**
	 * 根据询价单编码查询没有订单的报价单
	 * @param inquiryId
	 * @return
	 */
	public List<QuotaDTO> getQuotaNoOrderByInquiryId(String inquiryId)throws Exception;
	/**
	 * 
	* @Title: existByInsIdDAO
	* @Description: 判断有没有该保险公司的报价单
	* @param  inquiryId
	* @param  microId
	* @param  insId
	* @param @return 
	* @param @throws Exception
	* @return QuotaDTO
	* @throws
	 */
	public QuotaDTO existByInsIdDAO(@Param("inquiryId")String inquiryId,@Param("microId")String microId,@Param("insId")String insId)throws Exception;

	/**
	 * 统计用户在某时间段内每天报价的次数 按车架号统计
	 * @param userId
	 * @param date1
	 * @param date2
	 * @return
	 */
	public List<QuotaDTO> quotaCountWithBetweenDate(@Param("userId")String userId, @Param("date1")String date1, @Param("date2")String date2);
	
	/**
	 * 根据报价单号获取创建时间
	 * @param quoteId 报价单号
	 * @return
	 * @throws Excetpion
	 */
	public Date getCreateTimeByQuoteId(String quoteId) throws Exception;
	
	
	/**
	 * 更新保费
	 * @param taxPrm
	 * @param vciPrm
	 * @param tciPrm
	 * @return
	 */
	public int updateQuotePrm(@Param("taxPrm") BigDecimal taxPrm, @Param("vciPrm") BigDecimal vciPrm, @Param("tciPrm") BigDecimal tciPrm, @Param("quoteId") String quoteId);
	
	/**
	 * 查询改询价单下的有效人工报价单数
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public Integer queryManualQuotationByInquiryId(String inquiryId) throws Exception;
}
