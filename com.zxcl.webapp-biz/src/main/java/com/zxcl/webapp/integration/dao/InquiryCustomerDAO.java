package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.InquiryCustomerDTO;

/**
 * @author zxj
 * @date 2016年8月10日
 * @description 
 */
public interface InquiryCustomerDAO {
	
    int insertSelective(InquiryCustomerDTO inquiryCustomerDTO);
    
    int delByInquiryId(String inquiryId);
    
    int updateSelective(InquiryCustomerDTO inquiryCustomerDTO);
    
    InquiryCustomerDTO selectByInquiryId(@Param("inquiryId")String inquiryId, @Param("customerType") String customerType);
    
    List<InquiryCustomerDTO> selectsByInquiryId(String inquiryId);
}