package com.zxcl.webapp.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zxcl.webapp.biz.service.InsMsgMatchService;
import com.zxcl.webapp.dto.InsMsgMatchDTO;
import com.zxcl.webapp.integration.dao.InsMsgMatchDAO;

/**
 * @author zxj
 * @date 2016年10月8日
 * @description 
 */
@Service
public class InsMsgMatchServiceImpl implements InsMsgMatchService {

	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private InsMsgMatchDAO insMsgMatchDAO;
	
	@Override
	public Map<String,String> matchMsg(String insId, String msg) {
		logger.info("匹配==>insId="+insId+",msg="+msg);
		Map<String,String> map = new HashMap<String,String>();
		//是否能转人工报价，1-能，非1-不能
		String manual_quotn = "";
		if(StringUtils.isBlank(insId) || StringUtils.isBlank(msg)){
			map.put("msg",msg);
			map.put("manual_quotn",manual_quotn);
			return map;
		}
		String matchMsg = null;
		
		try {
			List<InsMsgMatchDTO> list = insMsgMatchDAO.selectByInsId(insId);
			logger.info("list="+JSONObject.toJSONString(list));
			
			if(CollectionUtils.isNotEmpty(list)){
				for (InsMsgMatchDTO item : list) {
					matchMsg = match(item, msg);
					if(StringUtils.isNotBlank(matchMsg)){
						manual_quotn = item.getManualQuotn();
						break;
					}
				}
			}
			
		} catch (Exception e) {
			logger.info("匹配失败", e);
		}
		
		if(StringUtils.isBlank(matchMsg)){
			logger.info("未匹配到,返回原msg");
			matchMsg = msg;
		}
		else{
			logger.info("未匹配到结果,matchMsg="+matchMsg);
		}
		map.put("manual_quotn",manual_quotn);
		map.put("msg",matchMsg);
		return map;
	}

	
	protected String match(InsMsgMatchDTO item, String msg){
		if(null == item || StringUtils.isBlank(msg)){
			return null;
		}
		
		String result = null;
		if(null != item.getMatchType() && item.getMatchType().equals(1)){//indexOf
			if(StringUtils.isNotBlank(item.getKeyWord()) && msg.contains(item.getKeyWord())){
				return item.getMatchMsg();
			}
		}
		else if(null != item.getMatchType() && item.getMatchType().equals(2)){//正则
			if(StringUtils.isNotBlank(item.getKeyWord()) && msg.matches(item.getKeyWord())){
				return item.getMatchMsg();
			}
		}
		return result;
	}
}
