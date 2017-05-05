package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.BankOcrDTO;

/**
 * @author zxj
 * @date 2016年10月11日
 * @description 
 */
public interface BankOcrDAO {
	
    int insertSelective(BankOcrDTO record);

	BankOcrDTO selectBySelitic(@Param("len")Integer len, @Param("preSign")String preSign);

	String getTlBankNoByOcrBankNo(String ocrBankNo);
}