package com.zxcl.webapp.biz.service;


import com.zxcl.webapp.dto.InquiryCustomerDTO;

/**
 * @author zxj
 * @date 2016年8月10日
 * @description 
 */
public interface InquiryCustomer {
	
	int insertSelective(InquiryCustomerDTO inquiryCustomerDTO);
    
    int updateSelective(InquiryCustomerDTO inquiryCustomerDTO);
    
    InquiryCustomerDTO selectByInquiryId(String inquiryId, String customerType);
}
