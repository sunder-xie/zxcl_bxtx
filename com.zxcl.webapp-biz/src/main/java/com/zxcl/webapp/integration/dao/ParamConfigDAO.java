package com.zxcl.webapp.integration.dao;

import java.util.List;

import com.zxcl.webapp.dto.ParamConfigDTO;

public interface ParamConfigDAO {

	/**
	 * 获取所有有效的参数配置信息.
	 * @return
	 */
	public List<ParamConfigDTO> getParamConfigs();

	public int insertSelective(ParamConfigDTO paramConfigDTO);

	public int deleteByKey(String configKey);
	
}
