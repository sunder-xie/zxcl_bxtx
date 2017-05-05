package com.zxcl.webapp.biz.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.dto.InquiryDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:fw-biz-context.xml",
		"classpath:fw-security-context.xml", "classpath:app-biz-context.xml" })
public class InquiryServiceImplTest {

	@Autowired
	private InquiryService inquiryService;

	@Test
	public void update() {
		InquiryDTO inquiry = new InquiryDTO();
		inquiry.setInquiryId("20151113095517694");
		inquiry.setRemark("达到！！");
//		try {
//			inquiryService.updateInquiryByInquiryId(inquiry);
//		} catch (BusinessServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
