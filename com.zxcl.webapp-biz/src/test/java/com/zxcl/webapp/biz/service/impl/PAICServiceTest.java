//package com.zxcl.webapp.biz.service.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.zxcl.webapp.biz.action.data.PAICDataAction;
//import com.zxcl.webapp.dto.port.paic.platformInsuredQuery.response.PAICPlatInsQueryResponseDTO;
//import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
//import com.zxcl.webapp.integration.sao.PAICAnalysisSAO;
//import com.zxcl.webapp.integration.sao.RequestMessageSAO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath:fw-biz-context.xml", "classpath:fw-security-context.xml",
//		"classpath:app-biz-context.xml","classpath:app-biz-cxf.xml" })
//public class PAICServiceTest {
//	@Autowired
//	private PAICAnalysisSAO		analysisSAO;
//	@Autowired
//	private RequestMessageSAO	requestMessageSAO;
//	@Autowired
//	private PAICDataAction		dataAction;
//
//	/**
//	 * 
//	 * @Title: token
//	 * @Description: 获取平安token
//	 * @param
//	 * @return void
//	 * @throws
//	 */
//	
//	public void tokenTest() {
//		try {
//			Map<String, Object> token = analysisSAO.getTokenSAO("", "xiaoming");
//			System.out.println("错误编码：" + token.get("errorCode") + "  token:" + token.get("token"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: queryMachine
//	 * @Description: 平安查询车型
//	 * @param
//	 * @return void
//	 * @throws
//	 */
//	
//	public void queryMachineTest() {
//		try {
//
//			// 转成JSON
//			String requestJSON = dataAction.vehicleQueryData("xiaoming", "20151223031759792");
//			InsuranceDTO ins = new InsuranceDTO();
//			ins.setInsId("PAIC");
//			Map<String, Object> map = analysisSAO.vehicleQuerySAO(ins, requestJSON, "1", "20151223031759792",
//					"4DACDF23903848658CCBC89336DB0EE7");
//			System.out.println(map.get("respJSON"));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: circQueryByIbcs
//	 * @Description: 平台投保查询
//	 * @param
//	 * @return void
//	 * @throws
//	 */
//
//	public void circQueryByIbcsTest() {
//		try {
//			InsuranceDTO ins = new InsuranceDTO();
//			ins.setInsId("PAIC");
//			Map<String, Object> map = analysisSAO.circQueryByIbcsSAO("PB2EF6E87-0664-4C4E-B674-BD1711926D53", "是车主",
//					"0", "", "xiaoming", "4DACDF23903848658CCBC89336DB0EE7");
//			System.out.println(map.get("respJSON"));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: standardCalculatePremium
//	 * @Description: 计算保费
//	 * @param
//	 * @return void
//	 * @throws
//	 */
//
//	public void standardCalculatePremiumTest() {
//		try {
//
//			InsuranceDTO ins = new InsuranceDTO();
//			ins.setInsId("PAIC");
//
//			PAICPlatInsQueryResponseDTO responsedto = new PAICPlatInsQueryResponseDTO();
//			responsedto.setFlowid("PB2EF6E87-0664-4C4E-B674-BD1711926D53");
//			String requestJSON = dataAction.quotaData(responsedto, "xiaoming", "PAIC", "20151221042607694",
//					String.valueOf(0.00));
//			System.out.println(requestJSON);
//			Map<String, Object> map = analysisSAO.quoteSAO(requestJSON, ins, "xiaoming", "20151210104559652",
//					"4DACDF23903848658CCBC89336DB0EE7");
//			System.out.println(map.get("respJSON"));
//
//			/*
//			 * String encoding = "GBK"; File file = new File("D:\\json.txt"); if
//			 * (file.isFile() && file.exists()) { // 判断文件是否存在 InputStreamReader
//			 * read = new InputStreamReader(new FileInputStream(file),
//			 * encoding);// 考虑到编码格式 BufferedReader bufferedReader = new
//			 * BufferedReader(read); String lineTxt = null; while ((lineTxt =
//			 * bufferedReader.readLine()) != null) {
//			 * System.out.println(lineTxt); Map<String, Object> map =
//			 * analysisSAO.quoteSAO(lineTxt, ins, "xiaoming",
//			 * "20151210104559652", "4DACDF23903848658CCBC89336DB0EE7");
//			 * System.out.println(map.get("respJSON")); } read.close(); }
//			 */
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: specialPromiseByIBCS
//	 * @Description: 取特别约定
//	 * @param
//	 * @return void
//	 * @throws
//	 */
//	
//	public void specialPromiseByIBCSTest() {
//		try {
//			InsuranceDTO ins = new InsuranceDTO();
//			PAICPlatInsQueryResponseDTO responsedto = new PAICPlatInsQueryResponseDTO();
//			responsedto.setFlowid("");
//			ins.setInsId("PAIC");
//			String requestJSON = dataAction.specialPromiseByIBCSData("P6B476DB5-BB14-4725-9C9E-4C36D5B02E74",
//					"xiaoming", "PAIC", "20151223033001337", null, null,null);
//			System.out.println("请求JSON:" + requestJSON);
//			Map map = analysisSAO.specialPromiseByibcsSAO(requestJSON, ins, "xiaoming",
//					"4DACDF23903848658CCBC89336DB0EE7");
//			System.out.println(map.get("respJSON"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: applyPolicyByIBCS
//	 * @Description: 标准申请投保
//	 * @param
//	 * @return void
//	 * @throws
//	 */
//	public void applyPolicyByIBCSTest() {
//		try {
//			InsuranceDTO ins = new InsuranceDTO();
//			PAICPlatInsQueryResponseDTO responsedto = new PAICPlatInsQueryResponseDTO();
//			responsedto.setFlowid("");
//			ins.setInsId("PAIC");
//			Map map = analysisSAO.insureSAO("", null, ins, "xiaoming", "20151223153049449",
//					"4DACDF23903848658CCBC89336DB0EE7");
//			System.out.println(map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: applyPolicyByIBCS
//	 * @Description: 财务开单
//	 * @param
//	 * @return void
//	 * @throws
//	 */
//	@Test
//	public void noticeDoByNpsTest() {
//		try {
//			// 20151228133815893
////			Map<String, Object> map = analysisSAO.getQuoteResponse("20160105014725780");
////			Map<String, Object> flowidMap = analysisSAO.getFlowid("1", "20160105014725780", "B");
////			Map<String, Object> insureMap = analysisSAO.getInsureResponse("20160105134811583");
////			if ("0".equals(flowidMap.get("errorCode").toString())) {
////
////				if ("0".equals(map.get("errorCode"))) {
////					PAICStanCalPremiumResponseDTO quoteResponseDTO = (PAICStanCalPremiumResponseDTO) map
////							.get("responseDTO");
////					PAICInsuranceResponseDTO insuranceResponseDTO=(PAICInsuranceResponseDTO)insureMap.get("responseDTO");
////					InsuranceDTO ins = new InsuranceDTO();
////					String requestJSON = dataAction.financeNoticeData(flowidMap.get("flowid").toString(),"20151229142734755",
////							quoteResponseDTO,insuranceResponseDTO, "xiaoming");
////					System.out.println(requestJSON);
////					Map<String, Object> resmap = analysisSAO.financeNoticeSAO(ins, requestJSON, "xiaoming", "715275C19C35432BB00C7F66D10EE496");
////					System.out.println(resmap.get("respJSON"));
////				}
////			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	public void getQRcode() {
//		try {
//			// 11040088000007801196
//			analysisSAO.getQRcodeSAO("", "", "");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//
//	/**
//	 * 
//	 * @Title: applyPolicyByIBCS
//	 * @Description: 财务通知到账
//	 * @param
//	 * @return void
//	 * @throws
//	 */
//
//	public void noticeDoneByNpsTest() {
//		try {
//			InsuranceDTO ins = new InsuranceDTO();
//			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
//			Map<String, Object> detailmap = new HashMap<String, Object>();
//			detailmap.put("bankOrderNo", "TEST");
//			detailmap.put("bankTradeDate", "20151213");
//			detailmap.put("bankTradeTime", "15:28:30");
//			detailmap.put("tradeAmount", "22.22");
//			detailmap.put("tradeCode", "TEST");
//			detailmap.put("bankCode", "TEST");
//			detailmap.put("bankDeptno", "TEST");
//			detailmap.put("tellerno", "TEST");
//			detailmap.put("bkTranChnl", "TEST");
//			detailmap.put("regionCode", "TEST");
//			detailmap.put("payChannel", "75");
//			detailmap.put("clientBankCode", "TEST");
//			detailmap.put("clientBankAccount", "TEST");
//			detailmap.put("clientName", "TEST");
//			detailmap.put("clientCardFlag", "TEST");
//			detailmap.put("mobile", "TEST");
//			detailmap.put("directAmount", "TEST");
//			detailmap.put("normalAmount", "TEST");
//			detailmap.put("normalRate", "TEST");
//			detailmap.put("replaySerial", "TEST");
//			detailList.add(detailmap);
//
//			String requestJSON =  "";//dataAction.noticeDoneByNpsData(uuid, "11040088000007800026", detailList, userId)
//			analysisSAO.financeConfirmSAO(ins, requestJSON, "xiaoming", "715275C19C35432BB00C7F66D10EE496");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: applyPolicyByIBCS
//	 * @Description: 承保
//	 * @param
//	 * @return void
//	 * @throws
//	 */
//
//	public void acceptByIbcsTest() {
//		try {
////			List<PAICNoticeDTO> noticeList = new ArrayList<PAICNoticeDTO>();
////			PAICFinanceConfirmReponseDTO financeConfirmReponseDTO = new PAICFinanceConfirmReponseDTO();
////			PAICNoticeDTO dto = new PAICNoticeDTO();
////			dto.setDocumentNo("TESTNO");
////			noticeList.add(dto);
////			financeConfirmReponseDTO.setNoticeList(noticeList);
////			InsuranceDTO ins = new InsuranceDTO();
////			String requestJSON = dataAction.acceptByIbcsData(financeConfirmReponseDTO, "xiaoming");
////			ins.setInsId("PAIC");
////			Map map = analysisSAO.acceptByIbcsSAO(ins, requestJSON, "xiaoming", "715275C19C35432BB00C7F66D10EE496");
////			System.out.println(map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//}
