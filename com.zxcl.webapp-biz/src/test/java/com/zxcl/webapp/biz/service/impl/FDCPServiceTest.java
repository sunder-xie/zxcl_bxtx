//package com.zxcl.webapp.biz.service.impl;
//
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.zxcl.webapp.biz.action.call.FDCPCallAction;
//import com.zxcl.webapp.biz.exception.ActionException;
//import com.zxcl.webapp.biz.exception.SAOException;
//import com.zxcl.webapp.dto.InquiryDTO;
//import com.zxcl.webapp.dto.InsXmlDTO;
//import com.zxcl.webapp.dto.port.comdto.quote.QuoteDTO;
//import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
//import com.zxcl.webapp.integration.sao.FDCPAnalysisSAO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath:fw-biz-context.xml", "classpath:fw-security-context.xml",
//		"classpath:app-biz-context.xml","classpath:app-biz-cxf.xml" })
//public class FDCPServiceTest {
//	@Autowired
//	private FDCPAnalysisSAO fdcpSao ;
//	
//	@Autowired
//	private FDCPCallAction fdcpAction;
//	
//	//车辆信息查询
//	@Test
//	public void test1() {
//		InsXmlDTO insXMl = new InsXmlDTO();
//		insXMl.setInsId("FDCP");
//		insXMl.setCrtCode("xiaoming");
//		insXMl.setUpdCode("xiaoming");
//		insXMl.setXmlType("3");
//		try {
//			fdcpSao.vehicleQuery(null, null, new InsuranceDTO("FDCP"), insXMl, "xiaoming");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	//车辆真实价值
//	@Test
//	public void test2() {
//		InsXmlDTO insXMl = new InsXmlDTO();
//		insXMl.setInsId("FDCP");
//		insXMl.setCrtCode("xiaoming");
//		insXMl.setUpdCode("xiaoming");
//		insXMl.setXmlType("3");
//		try {
////			fdcpSao.carRealPriQuery(new InquiryDTO("20160113110155136"), "FDCP", insXMl, "xiaoming");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	//报价
//	@Test
//	public void test3() {
////		try {
//////			fdcpAction.quotas("xiaoming", "20160113110929821", "FDCP");
////		} catch (ActionException e) {
////			e.printStackTrace();
////		}
//	}
//
//}
