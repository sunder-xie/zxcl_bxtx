package com.zxcl.webapp.web.util;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author zxj
 * @date 2016年7月28日
 * @description 
 */
public final class CacheUtil {
	
	private static Logger logger = Logger.getLogger(CacheUtil.class);
	
	public static Map<String, Long> DATE_MAP = new ConcurrentHashMap<String, Long>();
	public static Map<String, String> OPENID_IVI_MAP = new ConcurrentHashMap<String, String>();
	public static void addOpenIdIvi(String openId, String ivi){
		logger.info("addOpenIdIvi,openId="+openId+",ivi="+ivi);
		if(StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(ivi)){
			OPENID_IVI_MAP.put(openId, ivi);
			DATE_MAP.put(openId, System.currentTimeMillis());
		}
	}
	public static String getIviFromMap(String openId){
		logger.info("getIviFromMap,openId="+openId);
		String result = "";
		if(StringUtils.isNotBlank(openId)){
			if(!OPENID_IVI_MAP.isEmpty()){
				result = OPENID_IVI_MAP.get(openId);
			}
		}
		logger.info("getIviFromMap,result="+result);
		return result;
	}
	static{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted()){
					if(!DATE_MAP.isEmpty()){
						ArrayList<String> list = new ArrayList<String>();
						for(Map.Entry<String, Long> item : DATE_MAP.entrySet()){
							if((System.currentTimeMillis() - item.getValue()) > 5*60*1000){//缓存5分钟
								list.add(item.getKey());
							}
						}
						if(CollectionUtils.isNotEmpty(list)){
							for(String item : list){
								logger.info("删除过期缓存," + item);
								OPENID_IVI_MAP.remove(item);
								DATE_MAP.remove(item);
							}
						}
					}
					
					try {
						TimeUnit.SECONDS.sleep(60);
					} catch (InterruptedException e) {
						//ignore
					}
				}
				
			}
		}).start();
	}
}
