package com.zxcl.webapp.util;

/**
 * 签名用的DTO（使用的保险公司：太平、）
 * @author 444
 *
 */
public class SignDTO {
	/**
	 * 分配的商户号编码
	 */
	private String client;
	/**
	 * 由太平分配
	 */
	private String token;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 接口名称
	 */
	private String interfaceType;
	
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
}
