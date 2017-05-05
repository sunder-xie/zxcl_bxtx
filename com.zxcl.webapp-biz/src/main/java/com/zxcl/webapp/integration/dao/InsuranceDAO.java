package com.zxcl.webapp.integration.dao;

import java.util.List;



import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**
 * 保险公司
 * 
 * @author 5555
 *
 */
public interface InsuranceDAO {

	/**
	 * 获取所有合作的总公司
	 * 
	 * @return
	 */
	public List<InsuranceDTO> getHeadOffice() throws Exception;

	/**
	 * 根据code 获取单个保险公司的信息
	 */
	public InsuranceDTO get(String code) throws Exception;

}
