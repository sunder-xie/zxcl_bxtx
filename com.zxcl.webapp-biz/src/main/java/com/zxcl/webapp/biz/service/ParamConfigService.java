package com.zxcl.webapp.biz.service;

import java.util.List;
import java.util.Map;

import com.zxcl.webapp.dto.ParamConfigDTO;

/**
 * 参数配置的接口相关
 * @author xiaoxi
 *
 */
public interface ParamConfigService {

	/**
	 * 获取所有的有效配置数据,(从数据库取)
	 * @return
	 */
	public List<ParamConfigDTO> getAllParamConfigs();
	
	/**
	 * 获取所有的有效配置数据(从缓存取)
	 * @return
	 */
	public Map<String, ParamConfigDTO> getAllParamConfigMaps();
	
	/**
	 * 根据key获取配置数据(从缓存取)
	 * @param key
	 * @return
	 */
	public ParamConfigDTO getParamConfigByKey(String key);
	
	/**
	 * 根据key获取value.(从缓存取)
	 * @param key
	 * @return
	 */
	public String getValueByKey(String key);
	
	
	/**设置配置数据
	 * @param paramConfigDTO
	 */
	public void setParamConfig(ParamConfigDTO paramConfigDTO);
}
