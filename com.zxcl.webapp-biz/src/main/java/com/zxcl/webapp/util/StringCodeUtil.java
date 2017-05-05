package com.zxcl.webapp.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.biz.util.DateUtil;

/**
 * 
 * @ClassName: StringCodeUtil
 * @Description: 字符串处理公共方法
 * @author 赵晋
 * @date 2015年12月1日 上午10:10:09
 *
 */
public class StringCodeUtil {
	
	private static Logger logger = Logger.getLogger(StringCodeUtil.class);
	
	/**
	 * 
	* @Title: isIdCard
	* @Description: 身份证验证
	* @param  idCard 身份证号
	* @param @throws Exception
	* @return Boolean
	* @throws
	 */
	public static Boolean isIdCard(String idCard){
		boolean isIdCard = false;
		if (null != idCard && !"".equals(idCard)) {
			if (idCard.matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)")) {
				isIdCard = true;
			}
		}
		return isIdCard;
	}
	
	/**
	 * 
	* @Title: getSex
	* @Description: 根据身份证号获取性别
	* @param  idCard
	* @return char
	* @throws
	 */
	public static char getSex(String idCard){
		char sex=CommonUtil.getSex(idCard) % 2 == 0 ? '2' : '1';
		return sex;
	}
	
	/**
	 * 
	* @Title: getAge
	* @Description: 根据身份证号获取生日
	* @param  idCard
	* @return Date
	* @throws
	 */
	public static Date getBrithday(String idCard){
		Date brithday;
		try {
			brithday = CommonUtil.getBrith(idCard);
			return brithday;
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}
	

	/**
	 * 
	* @Title: getAge
	* @Description: 根据身份证号获取年龄
	* @param @param idCard
	* @param @return
	* @return int
	* @throws
	 */
	public static int getAge(Date brithday){
		int age;
		try {
			age = DateUtil.getYear(new Date())- DateUtil.getYear(brithday);
			return age;
		} catch (ParseException e) {
			logger.error(e);
		}
		return 0;
	}

}
