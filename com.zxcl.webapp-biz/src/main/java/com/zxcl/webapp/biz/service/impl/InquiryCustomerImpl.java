package com.zxcl.webapp.biz.service.impl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.InquiryCustomer;
import com.zxcl.webapp.dto.InquiryCustomerDTO;
import com.zxcl.webapp.integration.dao.InquiryCustomerDAO;

/**
 * @author zxj
 * @date 2016年8月10日
 * @description 
 */
@Service
public class InquiryCustomerImpl implements InquiryCustomer {

	@Autowired
	private InquiryCustomerDAO inquiryCustomerDAO;
	
	protected Logger logger = Logger.getLogger(getClass());
	
	@Override
	public int insertSelective(InquiryCustomerDTO inquiryCustomerDTO) {
		logger.info("新增投保人或被保人信息"+ToStringBuilder.reflectionToString(inquiryCustomerDTO));
		int c = inquiryCustomerDAO.insertSelective(inquiryCustomerDTO);
		logger.info("影响行数"+c);
		return c;
	}

	@Override
	public int updateSelective(InquiryCustomerDTO inquiryCustomerDTO) {
		logger.info("更新投保人或被保人信息"+ToStringBuilder.reflectionToString(inquiryCustomerDTO));
		int c = inquiryCustomerDAO.updateSelective(inquiryCustomerDTO);
		logger.info("影响行数"+c);
		return c;
	}

	@Override
	public InquiryCustomerDTO selectByInquiryId(String inquiryId, String customerType) {
		logger.info("查询投保人或被保人信息,inquiryId="+inquiryId+",customerType="+customerType);
		InquiryCustomerDTO inquiryCustomerDTO = inquiryCustomerDAO.selectByInquiryId(inquiryId, customerType);
		logger.info("查询结果"+ToStringBuilder.reflectionToString(inquiryCustomerDTO));
		return inquiryCustomerDTO;
	}

}
