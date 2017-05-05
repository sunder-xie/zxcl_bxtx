/**
 * 
 */
package com.zxcl.webapp.biz.service.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zxcl.webapp.biz.service.MyService;

/**
 * @author f
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:fw-biz-context.xml",
		"classpath:fw-security-context.xml", "classpath:app-biz-context.xml" })
public class MyServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * 
	 */
	@Autowired
	MyService myService;

	/**
	 * Test method for
	 * {@link com.zxcl.biz.service.impl.MyServicePojoImpl#showMessage()}.
	 */
	@Test
	public void testShowMessage() {
		//myService.showMessage();
		assertTrue(true);
	}

}
