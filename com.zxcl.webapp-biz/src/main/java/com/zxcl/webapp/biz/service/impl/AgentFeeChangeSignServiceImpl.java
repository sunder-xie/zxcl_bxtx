package com.zxcl.webapp.biz.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.AgentFeeChangeSignService;
import com.zxcl.webapp.integration.dao.AgentFeeChangeSignDAO;

@Service
public class AgentFeeChangeSignServiceImpl implements AgentFeeChangeSignService{

	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private  AgentFeeChangeSignDAO agentFeeChangeSignDAO;
	
	@Override
	public String[] getAll() throws BusinessServiceException {
		String [] agentIds = null;
		try {
			agentIds = agentFeeChangeSignDAO.getAll();
		} catch (Exception e) {
			logger.error("查询所有未费改的代理公司ID失败",e);
			throw new BusinessServiceException("查询所有未费改的代理公司ID失败");
		}
		return agentIds;
	}
}
