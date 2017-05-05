package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;


import com.zxcl.webapp.dto.QuotaDetailedDTO;

/**
 * 报价明细的信息
 * 
 * @author 5555
 *
 */
public interface QuotaDetailedDAO {

	/**
	 * 插入报价明细表
	 * 
	 * @param detaileds
	 */
	public int insertDetailed(@Param("detailed") QuotaDetailedDTO detailed)
			throws Exception;

	/**
	 * 获取报价详细信息
	 * 
	 * @param quotaId
	 * @return
	 */
	public QuotaDetailedDTO getDetailed(@Param("quotaId") String quotaId,
			@Param("insId") String insId) throws Exception;

	/**
	 * 删除报价单明细
	 * 
	 * @param quota
	 * @return
	 * @throws Exception
	 */
	public int deleteQuotaDetailedByQuotaId(String quotaId)
			throws Exception;
}
