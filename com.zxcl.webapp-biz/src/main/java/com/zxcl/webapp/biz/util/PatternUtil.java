package com.zxcl.webapp.biz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式相关
 * @author xiaoxi
 *
 */
public class PatternUtil {

	/**
	 * 获取正则截取的字符串,(仅查找一次)
	 * @param str
	 * @param patterStr
	 * @return
	 */
	public static String getInterceptionStr(String str,String patterStr){
		Pattern p = Pattern.compile(patterStr);
		Matcher m = p.matcher(str);
		if(m.find()){
			return m.group(0);
		}
		
		return "";
	}
	
	public static void main(String[] args) {
		System.out.println(getInterceptionStr("警告：车牌号“”的保单发生重复投保，与其重复投保的其它公司的保单信息如下：投保确认码 02PAIC510016001456726636113135;保单号 12696033900136493032;起保日期 2016-05-05 00;终保日期 2017-05-05 00;车牌号 川AXN750;号牌种类 02;车架号 LGWEF3K56DF057893;发动机号 1304532918。\n平台车主提示信息:01\n未传送车辆过户标志，查询的车主名称与上张保单不一致"
				, "(起保日期)+\\s+\\d{4}\\-\\d{2}\\-\\d{2}"));
	}
}
