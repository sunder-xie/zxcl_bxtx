package com.zxcl.webapp.integration.dao;

import java.util.List;

import com.zxcl.webapp.dto.RelyInsuranceDTO;

/**
 * 有车型查询依赖关系的保险公司DAO
 * @author zx
 *
 */
public interface RelyInsuranceDAO {
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<RelyInsuranceDTO> getAll() throws Exception;
}
