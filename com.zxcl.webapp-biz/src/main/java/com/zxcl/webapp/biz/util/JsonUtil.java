package com.zxcl.webapp.biz.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * json工具类
 * @author zengjun
 *
 */
public class JsonUtil {

	static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 对象>字符串
	 * @param obj
	 * @return 
	 */
	public static String objectToJsonStr(Object obj){
		try {
			return mapper.writeValueAsString(obj);
		} catch (IOException e) {
			
		}
		return "";
	}
	
}
