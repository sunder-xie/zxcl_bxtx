package com.zxcl.webapp.dto.rmi.intf.common;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author zxj
 * @date 2016年7月13日
 * @description 
 */
public class CommonDTO implements Serializable{

	private static final long serialVersionUID = 1576733361156222576L;
	
	private HashMap<String, Object> map;

	public HashMap<String, Object> getMap() {
		return map;
	}

	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}
	
	
	
}
