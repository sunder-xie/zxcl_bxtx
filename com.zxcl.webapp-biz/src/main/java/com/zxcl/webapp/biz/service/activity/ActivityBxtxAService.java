package com.zxcl.webapp.biz.service.activity;

import java.math.BigDecimal;
import java.util.Map;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

public interface ActivityBxtxAService {

	/**
	 * 拆开红包.
	 * @return
	 * @throws BusinessServiceException
	 */
	public Map<String, Object> updateOpenRedPacket() throws BusinessServiceException ;
}
