package com.zxcl.webapp.biz.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.exception.ServiceException;
import com.zxcl.webapp.biz.service.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:fw-biz-context.xml", "classpath:fw-security-context.xml", "classpath:app-biz-context.xml" })
public class OrderServiceImplTest {

	@Autowired
	OrderService orderService;

	@Test
	public void deleteOrderByQuotaId() throws ServiceException {
//		try {
////			orderService.deleteOrderByQuotaId("1020101A3262015000429", "1", "JTIC01");
//		} catch (BusinessServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
