package com.zxcl.webapp.biz.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.exception.LogicException;
import com.zxcl.webapp.biz.exception.ServiceException;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.QuotaDetailedDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

public interface QuotaService {

	/**
	 * 报价后的详细信息
	 * 
	 * @param detaileds
	 * @throws LogicException
	 * @throws ServiceException
	 */
	public void insertDetailed(QuotaDTO quota, QuotaDetailedDTO detaileds)
			throws ServiceException;

	/**
	 * 获取单个对象的值
	 */
	public QuotaDTO get(QuotaDTO quota) throws BusinessServiceException;

	/**
	 * 插入支付信息
	 */
	// public void insertOrderPay(String quotaId);

	/**
	 * 
	 * @param userId
	 *            登录的id
	 * @param quotaId
	 *            报价单号
	 * @param TCIPremTax
	 *            交强险金额
	 * @param VCIPremTax
	 *            商业险金额
	 * @param vehicleTax
	 *            车船税
	 * @param micro
	 *            小薇账号
	 * @param inquiry
	 *            询价
	 * @param insurance
	 *            保险公司
	 * @return
	 * @throws Exception
	 */
	public QuotaDTO organizeQuota(String userId, String inquiryId,
			QuotaReturnDTO quotaReturn, MicroDTO micro, InsuranceDTO insurance)
			throws BusinessServiceException;

	public QuotaDTO getByQuotaId(String quotaid)
			throws BusinessServiceException;

	/**
	 * 根据小薇编码查找保险公司的订单信息
	 * 
	 * @param micro_id
	 * @return
	 */
	public List<QuotaDTO> getQuotasByMicId(String micro_id)
			throws BusinessServiceException;

	
	/**
	 * 
	* @Title: peopleAaskQuery
	* @Description: 人工报价查询
	* @param @return
	* @param @throws BusinessServiceException
	* @return List<QuotaDTO> 
	* @throws
	 */
	public List<QuotaDTO> peopleAaskQueryService(String microId)
			throws BusinessServiceException;

	/**
	 * 更新报价单状态
	 * 
	 * @param status
	 * @param quotaId
	 * @throws Exception
	 */
	public void updateQuotaStatusByInquiryId(String status, String inquiryId,String updCde)
			throws BusinessServiceException;
	/**
	 * 根据询价单获取报价单信息
	 */
	public List<QuotaDTO> getQuotasByInqueryId(String inquiryId)
			throws BusinessServiceException;
	
	/**
	 * 
	* @Title: infoViewService
	* @Description: 综合查询显示
	* @param @param inquiryId 询价单号
	* @param @return
	* @param @throws BusinessServiceException
	* @return List<QuotaDTO> 
	* @throws
	 */
	public List<QuotaDTO> infoViewService(String inquiryId)
			throws BusinessServiceException;
	
	/**
	 * 根据询价单编码查询没有订单的报价单信息
	 * @param inquiryId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<QuotaDTO> getQuotaNoOrderByInquiryId(String inquiryId)
			throws BusinessServiceException;
	
	/**
	 * 
	* @Title: existByInsIdService
	* @Description: 判断有没有该保险公司的报价单
	* @param  inquiryId
	* @param  microId
	* @param  insId
	* @param @return 
	* @param @throws Exception
	* @return List<QuotaDTO>
	* @throws
	 */
	public QuotaDTO existByInsIdService(String inquiryId,String microId,String insId)throws BusinessServiceException;
	
	
	
	
	/**
	 * 统计用户在某时间段内每天报价的次数    按车架号统计
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return Map<String, Integer>  如：{'2016-03-14', 2}
	 * @throws BusinessServiceException
	 */
	public Map<String, Integer> quotaCountWithBetweenDate(String userId, Date startDate, Date endDate) throws BusinessServiceException;
	
	/**
	 * 根据报价单号获取创建时间
	 * @param quoteId 报价单号
	 * @return
	 * @throws Excetpion
	 */
	public Date getCreateTimeByQuoteId(String quoteId) throws BusinessServiceException;
	
	/**
	 * 查询改询价单下的有效人工报价单数
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public Integer queryManualQuotationByInquiryId(String inquiryId) throws BusinessServiceException;
	
}
