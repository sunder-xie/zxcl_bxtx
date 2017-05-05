package com.zxcl.webapp.biz.service.impl;



import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zxcl.webapp.biz.service.CommonCarInsurance;
import com.zxcl.webapp.biz.service.MessageRmiService;
import com.zxcl.webapp.biz.service.WalletBankService;
import com.zxcl.webapp.biz.service.WalletBillService;
//import com.zxcl.webapp.dto.activity.unicome.ActivityUnicomePhoneDTO;
//import com.zxcl.webapp.integration.dao.ActivityUnicomePhoneDAO;
import com.zxcl.webapp.integration.dao.FeeRulesDAO;
import com.zxcl.webapp.integration.dao.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:app-biz-cxf.xml","classpath:app-biz-context.xml","classpath:app-biz-dao.xml" })
public class WebTest3 {
	
	@Autowired
	private MessageRmiService messageRmiService;
	
	@Autowired
	private CommonCarInsurance commonCarInsurance;
	
	@Autowired
	private WalletBankService walletBankService;
	
	@Autowired
	private WalletBillService walletBillService;
	
	@Autowired
	private FeeRulesDAO feeRulesDAO;
	
	@Autowired
	private UserDAO userDAO;
	
//	@Autowired
//	private ActivityUnicomePhoneDAO phoneDAO ;
	
	private static final String[] strs = {"2","3","4","5","6","7","8","9"};
	private static final Random random = new Random();
	
	private static final String getPhoneId(){
		StringBuilder result = new StringBuilder("158");
		for (int i = 0; i < 8; i++) {
			result.append(strs[random.nextInt(strs.length)]);
		}
		return result.toString();
	}
	
	@Test
	public void test2() throws Exception {
//		System.out.println(walletBillService.getBillListByParam("xiaoming", "11", "I"));
//		System.out.println(feeRulesDAO.selectParentRatioNowWith2("AICS", "xiaoming", "TCI", "00"));
//		System.out.println(feeRulesDAO.selectRatioNowWith2("AICS", "xiaoming", "TCI", "00"));
	}
	
//	@Test
//	public void test() throws Exception {
//		ActivityUnicomePhoneDTO phone = new ActivityUnicomePhoneDTO();
//		phone.setCrtBy("zhaoxijun");
//		phone.setUpdBy("zhaoxijun");
//		phone.setOperate("联通");
//		try {
//			for (int i = 0; i < 2000; i++) {
//				phone.setPhoneId(getPhoneId());
//				phoneDAO.insertSelective(phone);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
