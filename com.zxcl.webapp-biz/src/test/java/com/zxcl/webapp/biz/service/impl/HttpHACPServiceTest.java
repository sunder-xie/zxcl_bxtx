package com.zxcl.webapp.biz.service.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zxcl.webapp.biz.action.call.HttpHACPCallAction;
import com.zxcl.webapp.biz.exception.ActionException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:fw-biz-context.xml", "classpath:fw-security-context.xml",
		"classpath:app-biz-context.xml","classpath:app-biz-cxf.xml" })
public class HttpHACPServiceTest {
	
	@Autowired
	private HttpHACPCallAction haAction;
	
	//报价
	@Test
	public void test() {
		try {
			haAction.quotas("mou", "20160113110929821", "HACP");
		} catch (ActionException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println(1);
	}
}
