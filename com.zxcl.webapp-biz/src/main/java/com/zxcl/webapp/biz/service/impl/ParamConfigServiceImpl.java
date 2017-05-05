package com.zxcl.webapp.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.dto.ParamConfigDTO;
import com.zxcl.webapp.integration.dao.ParamConfigDAO;

@Service
public class ParamConfigServiceImpl implements ParamConfigService,
	ApplicationListener<ContextRefreshedEvent>{

	private static Logger logger = Logger.getLogger(ParamConfigServiceImpl.class);
	
	//private static Map<String,ParamConfigDTO> map = new HashMap<>();
	
	//private volatile boolean isRefresh = false;
	
	@Autowired
	private ParamConfigDAO paramConfigDAO;
	
	@Override
	public List<ParamConfigDTO> getAllParamConfigs() {
		return paramConfigDAO.getParamConfigs();
	}

	@Override
	public Map<String, ParamConfigDTO> getAllParamConfigMaps() {
		Map<String,ParamConfigDTO> map = new HashMap<>();
		List<ParamConfigDTO> lists = getAllParamConfigs();
		if(lists != null && lists.size() > 0){
			for(ParamConfigDTO configDTO : lists){
				map.put(configDTO.getKey(), configDTO);
			}
		}
		return map;
//		while(true){
//			if(!isRefresh){
//				return map;
//			}
//		}
	}

	@Override
	public ParamConfigDTO getParamConfigByKey(String key) {
		Map<String, ParamConfigDTO> map = getAllParamConfigMaps();
		ParamConfigDTO paramConfigDTO = map.get(key);
		logger.info(paramConfigDTO);
		return paramConfigDTO;
	}

	@Override
	public String getValueByKey(String key) {
		ParamConfigDTO t = getParamConfigByKey(key) ;
		return t != null ? t.getValue() : "";
	}

	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		//referParamConfig();
		
	}
	
//	@Scheduled(cron="${referparamconfig_cron}")
//	private void referParamConfig(){
//		isRefresh = true;
//		logger.info("开始刷新 参数配置数据.");
//		List<ParamConfigDTO> lists = getAllParamConfigs();
//		if(lists != null && lists.size() > 0){
//			for(ParamConfigDTO configDTO : lists){
//				map.put(configDTO.getKey(), configDTO);
//			}
//		}
//		
//		isRefresh = false;
//	}

	@Override
	public void setParamConfig(ParamConfigDTO paramConfigDTO) {
		logger.info("参数==>paramConfigDTO="+paramConfigDTO);
		if(null == paramConfigDTO || StringUtils.isBlank(paramConfigDTO.getKey()) || StringUtils.isBlank(paramConfigDTO.getValue())){
			logger.info("设置失败 key-value必填");
			return;
		}
		if(StringUtils.isBlank(paramConfigDTO.getRemark())){
			paramConfigDTO.setRemark(paramConfigDTO.getKey());
		}
		
		paramConfigDAO.deleteByKey(paramConfigDTO.getKey());
		
		paramConfigDAO.insertSelective(paramConfigDTO);
		
		//referParamConfig();
		
		
		
	}

}
