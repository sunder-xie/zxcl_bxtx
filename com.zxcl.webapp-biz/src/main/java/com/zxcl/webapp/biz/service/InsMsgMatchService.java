package com.zxcl.webapp.biz.service;

import java.util.Map;

/**
 * @author zxj
 * @date 2016年10月8日
 * @description 
 */
public interface InsMsgMatchService {
	/**
	 * @param insId 保险公司代码
	 * @param msg 匹配关键词
	 * @return 匹配结果,,,如果匹配不到返回原msg
	 */
	public Map<String,String> matchMsg(String insId, String msg);
}
