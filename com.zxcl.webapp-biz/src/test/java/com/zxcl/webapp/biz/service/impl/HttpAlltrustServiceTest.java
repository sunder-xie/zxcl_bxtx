package com.zxcl.webapp.biz.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.zxcl.webapp.biz.action.call.HttpAlltrustCallAction;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:fw-biz-context.xml", "classpath:fw-security-context.xml",
		"classpath:app-biz-context.xml","classpath:app-biz-intf-yongcheng.xml"})
public class HttpAlltrustServiceTest {
	@Autowired
	private HttpAlltrustCallAction callAction;
	
	@Test
	public void testName() {
		try {
			System.out.println("111");
			QuotaReturnDTO quota = callAction.quotas("mou", "20160226061218356", "AICSHTTP");
			System.out.println("quota:"+JSONObject.toJSONString(quota));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
