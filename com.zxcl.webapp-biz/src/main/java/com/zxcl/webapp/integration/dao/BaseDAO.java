package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.BaseDTO;

/**
 * 基础信息DAO
 * @author zx
 *
 */
public interface BaseDAO {
	
	/**
	 * 获取该编码对象的信息
	 * @param codeClass 编码对象
	 * @return
	 * @throws Exception
	 */
	public List<BaseDTO> queryByCodeClass(String codeClass) throws Exception;
	
	/**
	 * 根据编码获取对象信息
	 * @param codeClass 编码对象
	 * @param code 编码
	 * @return
	 * @throws Exception
	 */
	public BaseDTO queryByCode(@Param("codeClass")String codeClass,@Param("code")String code) throws Exception;
}
