package com.zxcl.webapp.dto.wxmsg;

import java.util.Map;

public class SendTempMsgDTO {
	private String touser; 
	private String template_id; 
	private String url; 
	private Map<String, ? extends SendTempMsgItemDTO> data;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, ? extends SendTempMsgItemDTO> getData() {
		return data;
	}
	public void setData(Map<String, ? extends SendTempMsgItemDTO> data) {
		this.data =  data;
	}
	
}
