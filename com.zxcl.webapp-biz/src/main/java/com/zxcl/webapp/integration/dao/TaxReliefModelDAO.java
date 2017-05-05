package com.zxcl.webapp.integration.dao;

import com.zxcl.webapp.dto.TaxReliefModelsDTO;

/**
 * 减免税车型DAO
 * @author xiaoxi
 *
 */
public interface TaxReliefModelDAO {

	/**
	 * 根据车型号码获取减免税信息.
	 * @param modelNumber
	 * @return
	 */
	public TaxReliefModelsDTO getTaxReliefModelsByModelNumber(String modelNumber);
	
}
