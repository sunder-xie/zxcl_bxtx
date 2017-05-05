package com.zxcl.webapp.biz.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zxcl.webapp.dto.MicroDTO;
import com.zxcl.webapp.integration.dao.MicroDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:fw-biz-context.xml", "classpath:fw-security-context.xml",
		"classpath:app-biz-context.xml","classpath:app-biz-cxf.xml" ,"classpath:app-context.properties" })
public class AreaServiceImplTest {
	
	@Autowired
	MicroDAO Micro;
	
//	@Test
//	public void get() {
//		Integer selectCount = Micro.selectCount();
//	}
}  
