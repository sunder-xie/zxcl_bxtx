package com.zxcl.webapp.biz.service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.QuotaDetailedDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

/**
 * 报价返回的明细
 * 
 * @author 5555
 *
 */
public interface QuotaDetailedService {

	/**
	 * 插入报价明细表
	 * 
	 * @param detaileds
	 * @throws BusinessServiceException
	 */
	public void insertDetailed(QuotaDetailedDTO detaileds) throws BusinessServiceException;

	/**
	 * 获取报价详细信息
	 * 
	 * @param quotaId
	 * @return
	 * @throws BusinessServiceException
	 */
	public QuotaDetailedDTO getDetailed(String quotaId, String insId)
			throws BusinessServiceException;

	/**
	 * 
	 * @param userId
	 *            登录的id
	 * @param inquiry
	 *            询价
	 * @param insurance
	 *            保险
	 * @param quota
	 *            报价基础
	 * @param coverItemsDB
	 *            已经查询出的险种信息
	 * @param amounts
	 *            险种code和金额对应
	 * @return
	 * @throws Exception
	 */
	public QuotaDetailedDTO organizeQuotaDetaileds(String userId, String inquiryId,
			InsuranceDTO insurance, QuotaDTO quota, QuotaReturnDTO quotaRequest) throws BusinessServiceException;
}
