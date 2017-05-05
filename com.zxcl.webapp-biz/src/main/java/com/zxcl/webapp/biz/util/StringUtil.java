package com.zxcl.webapp.biz.util;

import java.util.UUID;

public class StringUtil {
	public static String getRandomId(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	public static String getIdByPrimaryName(String name){
		return UUID.nameUUIDFromBytes(name.getBytes()).toString().replace("-", "").toUpperCase();
	}
}
