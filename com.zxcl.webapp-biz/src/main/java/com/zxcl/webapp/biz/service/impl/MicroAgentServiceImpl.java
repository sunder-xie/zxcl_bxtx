package com.zxcl.webapp.biz.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zxcl.webapp.biz.service.MicroAgentService;
import com.zxcl.webapp.dto.MicroAgentDTO;
import com.zxcl.webapp.dto.MicroAgentDTOKey;
import com.zxcl.webapp.integration.dao.MicroAgentDAO;

/**
 * @author zxj
 */
@Service
public class MicroAgentServiceImpl implements MicroAgentService {

	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MicroAgentDAO microAgentDAO;
	
	@Override
	public int deleteByPrimaryKey(MicroAgentDTOKey key) {
		logger.info("删除名义代理人关联信息==>key="+key);
		int count = microAgentDAO.deleteByPrimaryKey(key);
		logger.info("删除名义代理人关联信息<==count="+count);
		return count;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int insertSelective(MicroAgentDTO record) {
		logger.info("新增名义代理人关联信息==>record="+record);
		int count = microAgentDAO.insertSelective(record);
		logger.info("新增名义代理人关联信息<==count="+count);
		return count;
	}

	@Override
	public MicroAgentDTO selectByPrimaryKey(MicroAgentDTOKey key) {
		logger.info("查询名义代理人关联信息==>key="+key);
		MicroAgentDTO microAgentDTO = microAgentDAO.selectByPrimaryKey(key);
		logger.info("查询名义代理人关联信息<==microAgentDTO="+microAgentDTO);
		return microAgentDTO;
	}

	@Override
	public int updateByPrimaryKeySelective(MicroAgentDTO record) {
		logger.info("更新名义代理人关联信息==>record="+record);
		int count = microAgentDAO.updateByPrimaryKeySelective(record);
		logger.info("更新名义代理人关联信息<==count="+count);
		return count;
	}

	@Override
	public List<MicroAgentDTO> selectMicroAgentListByMicroId(String microId) {
		logger.info("查询用户关联的名义代理人列表==>microId="+microId);
		List<MicroAgentDTO> microAgentDTOList = microAgentDAO.selectMicroAgentListByMicroId(microId);
		logger.info("查询用户关联的名义代理人列表<==microAgentDTOList="+microAgentDTOList);
		return microAgentDTOList;
	}

	@Override
	public MicroAgentDTO selectMicroAgentByAgentMicroId(String agentMicroId) {
		logger.info("查询名义代理小微被谁关联==>agentMicroId="+agentMicroId);
		MicroAgentDTO microAgentDTO = microAgentDAO.selectMicroAgentByAgentMicroId(agentMicroId);
		logger.info("查询名义代理小微被谁关联<==microAgentDTO="+microAgentDTO);
		return microAgentDTO;
	}

}
