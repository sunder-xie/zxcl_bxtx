package com.zxcl.webapp.dto.openapi;

public class ResultMsgDTO {

	private String bizRetCode;
	
	private String bizRetMsg;
	
	private String resultXml;

	public String getBizRetCode() {
		return bizRetCode;
	}

	public void setBizRetCode(String errorCode) {
		this.bizRetCode = errorCode;
	}

	public String getBizRetMsg() {
		return bizRetMsg;
	}

	public void setBizRetMsg(String errorMsg) {
		this.bizRetMsg = errorMsg;
	}

	public String getResultXml() {
		return resultXml;
	}

	public void setResultXml(String resultXml) {
		this.resultXml = resultXml;
	}
	
}
