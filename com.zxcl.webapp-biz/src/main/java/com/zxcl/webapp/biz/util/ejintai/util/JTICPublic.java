package com.zxcl.webapp.biz.util.ejintai.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zxcl.webapp.biz.util.HttpPostUtil;

public class JTICPublic {

	private static Logger logger = Logger.getLogger(JTICPublic.class);
	
	/**
	 * 创建锦泰车型查询接口的报文的头部获取签名
	 * 
	 * @param params
	 * @return
	 */
	public static String doSign(String appId,String key, String method, String timeStamp, String bizContent) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("method", method);
		params.put("appId", appId);
		params.put("timestamp", timeStamp);
		params.put("bizContent", bizContent);

		String sign = HttpPostUtil.makeSign(params, key);

		return sign;
	}

	/**
	 * 创建锦泰接口的报文
	 * 
	 * @param params
	 * @return
	 */
	public static String createHead(String appId, String method, String timeStamp, String bizContent, String sign) {

		String xml = "<xml encoding=\"utf-8\">\n";

		xml += "<appId>" + appId + "</appId>\n";

		xml += "<method>" + method + "</method>\n";

		xml += "<timestamp>" + timeStamp + "</timestamp>\n";

		xml += "<sign>" + sign + "</sign>\n";

		xml += "<bizContent><![CDATA[" + bizContent + "]]></bizContent>\n";

		xml += "</xml>";

		return xml;
	}

	/**
	 * for循环创建锦泰接口的报文结构
	 */

	public static String createBizContentBody(Object obj) {

		if (obj == null)
			return null;
		Field[] fields = obj.getClass().getDeclaredFields();
		String xml = "";
		for (int j = 0; j < fields.length; j++) {
			fields[j].setAccessible(true);
			xml = xml + "<" + fields[j].getName() + ">";
			try {
				Field[] fields2 = fields[j].get(obj).getClass().getDeclaredFields();
				if (fields[j].getType().getName().equals(java.util.List.class.getName())) {
					xml = xml + returnListXml(fields[j].getName(), (List) fields[j].get(obj));
					xml = xml + "</" + fields[j].getName() + ">";
				} else {
					xml = xml + returnXml(fields2, fields[j].get(obj));
					xml = xml + "</" + fields[j].getName() + ">";
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return xml;
	}

	private static String returnXml(Field[] fields2, Object object) throws Exception, IllegalAccessException {
		// TODO Auto-generated method stub
		String xml = "";
		for (int n = 0; n < fields2.length; n++) {
			fields2[n].setAccessible(true);
			// 字段名
			xml = xml + "<" + fields2[n].getName() + ">";
			if (fields2[n].getType().getName().equals(java.util.Date.class.getName())) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
				xml = xml + simpleDateFormat.format(fields2[n].get(object));
			} else if (fields2[n].getType().getName().equals(java.lang.Boolean.class.getName())) {
				if (fields2[n].get(object).toString().equals("true")) {
					xml = xml + "1";
				} else {
					xml = xml + "0";
				}
			} else {
				xml = xml + fields2[n].get(object);
			}
			xml = xml + "</" + fields2[n].getName() + ">";
		}
		return xml;
	}

	private static String returnListXml(String listName, List object) throws Exception, IllegalAccessException {
		// TODO Auto-generated method stub
		String xml = "";
		String na = listName.substring(0, listName.length() - 4);
		for (int i = 0; i < object.size(); i++) {
			xml = xml + "<" + na + ">";
			xml = xml + returnEach(object.get(i));
			xml = xml + "</" + na + ">";
		}
		return xml;
	}

	private static String returnEach(Object object) throws IllegalArgumentException, IllegalAccessException {
		String xml = "";
		Field[] fields2 = object.getClass().getDeclaredFields();
		for (int n = 0; n < fields2.length; n++) {
			fields2[n].setAccessible(true);
			// 字段名
			xml = xml + "<" + fields2[n].getName() + ">";
			if (fields2[n].getType().getName().equals(java.util.Date.class.getName())) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
				xml = xml + simpleDateFormat.format(fields2[n].get(object));
			} else if (fields2[n].getType().getName().equals(java.lang.Boolean.class.getName())) {
				if (fields2[n].get(object).toString().equals("true")) {
					xml = xml + "1";
				} else {
					xml = xml + "0";
				}
			} else {
				xml = xml + fields2[n].get(object);
			}
			xml = xml + "</" + fields2[n].getName() + ">";
		}
		return xml;

	}

}
