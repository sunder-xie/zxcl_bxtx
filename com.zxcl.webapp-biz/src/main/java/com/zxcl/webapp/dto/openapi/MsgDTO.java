package com.zxcl.webapp.dto.openapi;

import java.io.Serializable;

/**
 * 消息数据对象
 * @author wenchang001
 *
 */
public class MsgDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 合作方应用标识(代理商户号)
	 */
	private String appId;
	/**
	 * 调用的服务方法
	 */
	private String method;
	/**
	 * 消息时间戳
	 */
	private String timestamp;
	/**
	 * 消息明文
	 */
	private String bizContent;
	
	private long callId;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getBizContent() {
		return bizContent;
	}
	public void setBizContent(String bizMsg) {
		this.bizContent = bizMsg;
	}
	public long getCallId() {
		return callId;
	}
	public void setCallId(long callId) {
		this.callId = callId;
	}
	
}
