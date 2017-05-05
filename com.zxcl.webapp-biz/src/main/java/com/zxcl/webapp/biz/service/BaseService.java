package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.BaseDTO;

/**
 * 基础信息Service 
 * @author zx
 *
 */
public interface BaseService {
	
	/**
	 * 获取该编码对象的信息
	 * @param codeClass 编码对象
	 * @return
	 * @throws Exception
	 */
	public List<BaseDTO> queryByCodeClass(String codeClass) throws BusinessServiceException;
	
	/**
	 * 根据编码获取对象信息
	 * @param codeClass 编码对象
	 * @param code 编码
	 * @return
	 * @throws Exception
	 */
	public BaseDTO queryByCode(String codeClass,String code) throws BusinessServiceException;
}
