package com.zxcl.webapp.biz.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.CoverageItemService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.OrderService;
import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.dto.OrderDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:fw-biz-context.xml",
		"classpath:fw-security-context.xml", "classpath:app-biz-context.xml" })
public class CoverageItemServiceTestImpl {

	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private CoverageItemService corrItemService;
	
	@Autowired
	private MicroService microService;

	@Autowired
	private OrderService orderService;
	
	
	@Test
	public void test() {
//		try {
//			corrItemService.deleteCoverageItemsByInquiryId("20151113095517694");
//		} catch (BusinessServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		String xml = "<?xml version='1.0' encoding='UTF-8'?><package><head><requestType>0001</requestType><timestamp>2015-12-10 15:21:28</timestamp></head><body><proposalNo>EMC20150000057141</proposalNo></body></package>";
		String backXml = "";
		try {
			org.dom4j.Document xmlDocument = null;
			xmlDocument = DocumentHelper.parseText(xml);
			List<org.dom4j.Node> requestType = xmlDocument.selectNodes("//head/requestType");
			List<org.dom4j.Node> timestamp = xmlDocument.selectNodes("//head/timestamp");
			List<org.dom4j.Node> proposalNo = xmlDocument.selectNodes("//body/proposalNo");
			MicroDTO microDTO = microService.getMicroByUserId("xiaoming");
			OrderDTO order = orderService.getOrderByQuotaId(proposalNo.get(0).getText(), microDTO.getMicro_id(), "TPIC");
			order.setForStatus("5");
			orderService.updatePay(order);
			backXml = "<?xml version=1.0' encoding='UTF-8'?><package><head><status>S</status><errCode>0000</errCode><errMsg>0000</errMsg></head><body></body></package>";
		} catch (Exception e) {
			e.printStackTrace();
			backXml = "<?xml version=1.0' encoding='UTF-8'?><package><head><status>F</status><errCode></errCode><errMsg></errMsg></head><body></body></package>";
		}finally{
			System.out.println(backXml);;
		}
		
//		String xml = "<?xml version='1.0' encoding='UTF-8'?><package><head><requestType>0002</requestType><timestamp>20150821000000002</timestamp></head><body><proposalNo>EMC20150000057037</proposalNo><forcePolicyNo>63002080120150052442</forcePolicyNo><businessPolicyNo>63002080820150000029</businessPolicyNo></body></package>";
//		String backXml = "";
//		try {
//			org.dom4j.Document xmlDocument = null;
//			xmlDocument = DocumentHelper.parseText(xml);
//			List<org.dom4j.Node> requestType = xmlDocument.selectNodes("//head/requestType");
//			List<org.dom4j.Node> timestamp = xmlDocument.selectNodes("//head/timestamp");
//			List<org.dom4j.Node> proposalNo = xmlDocument.selectNodes("//body/proposalNo");
//			List<org.dom4j.Node> forcePolicyNo = xmlDocument.selectNodes("//body/forcePolicyNo");
//			List<org.dom4j.Node> businessPolicyNo = xmlDocument.selectNodes("//body/businessPolicyNo");
//			;
//			MicroDTO microDTO = microService.getMicroByUserId("xiaoming");
//			OrderDTO order = orderService.getOrderByQuotaId(proposalNo.get(0).getText(), microDTO.getMicro_id(), "TPIC");
//			order.setForStatus("6");
//			orderService.updatePay(order);
//			backXml = "<?xml version=1.0' encoding='UTF-8'?><package><head><status>S</status><errCode>0000</errCode><errMsg>0000</errMsg></head><body></body></package>";
//		} catch (Exception e) {
//			e.printStackTrace();
//			backXml = "<?xml version=1.0' encoding='UTF-8'?><package><head><status>F</status><errCode></errCode><errMsg></errMsg></head><body></body></package>";
//		}finally{
//			System.out.println(backXml);;
//		}
	}
}
