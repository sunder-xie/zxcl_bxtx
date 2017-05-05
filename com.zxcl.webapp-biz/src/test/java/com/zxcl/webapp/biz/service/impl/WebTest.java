//package com.zxcl.webapp.biz.service.impl;
//
//
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.zxcl.webapp.biz.service.OrderQueryService;
//import com.zxcl.webapp.biz.service.QuotaService;
//import com.zxcl.webapp.biz.service.WalletActiveService;
//import com.zxcl.webapp.biz.util.DateUtil;
//import com.zxcl.webapp.biz.util.DateUtils;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath:app-biz-cxf-local.xml","classpath:app-biz-context.xml" })
//public class WebTest {
//	@Autowired
//	private QuotaService quotaService;
//	
//	@Autowired
//	private WalletActiveService activeService;
//	
//	@Autowired
//	private OrderQueryService orderQueryService;
//	
//	@Test
//	public void test() throws Exception {
//		System.out.println(quotaService.quotaCountWithBetweenDate("xiaoming", DateUtil.stringToDate(DateUtils.YYYY_MM_DD_00_00_00, "2016-03-01 00:00:00"),  DateUtil.stringToDate(DateUtils.YYYY_MM_DD_23_59_59, "2016-03-01 23:59:59")));;
//	}
//	
//	@Test
//	public void test2() throws Exception {
//		System.out.println(orderQueryService.order6CountWithBetweenDate("13076079692", DateUtil.stringToDate(DateUtils.YYYY_MM_DD_00_00_00, "2016-03-21 00:00:00"),  DateUtil.stringToDate(DateUtils.YYYY_MM_DD_23_59_59, "2016-03-27 23:59:59")));;
//	}
//	
//	@Test
//	public void test3() throws Exception {
////		activeService.activeWithVote("test9_2", "JTIC");
//	}
//}
