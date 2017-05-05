/**
 * 
 */
package com.zxcl.webapp.biz.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.MyService;

/**
 * @author f
 *
 */
@Service
public class MyServicePojoImpl implements MyService {

	/**
	 * 
	 */
	Logger logger = Logger.getLogger(MyServicePojoImpl.class);

	/**
	 * 
	 */
	public MyServicePojoImpl() {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxcl.biz.service.MyService#showMessage()
	 */
	@Override
	public void showMessage() {
		logger.debug("this is a test message.");
		logger.debug("this is a test message.");
		logger.debug("this is a test message.");
		logger.debug("this is a test message.");
	}

}
