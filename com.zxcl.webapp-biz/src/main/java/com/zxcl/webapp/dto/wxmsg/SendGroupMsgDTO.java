package com.zxcl.webapp.dto.wxmsg;

import java.util.List;

/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */
public class SendGroupMsgDTO {
	private List<String> touser;
	private String msgtype = "text";
	private SendGroupContentMsgDTO text;
	public List<String> getTouser() {
		return touser;
	}
	public void setTouser(List<String> touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public SendGroupContentMsgDTO getText() {
		return text;
	}
	public void setText(SendGroupContentMsgDTO text) {
		this.text = text;
	}
	public SendGroupMsgDTO(List<String> touser, String msgtype,
			SendGroupContentMsgDTO text) {
		super();
		this.touser = touser;
		this.msgtype = msgtype;
		this.text = text;
	}
	public SendGroupMsgDTO() {
		super();
	}
	public SendGroupMsgDTO(List<String> touser, String msgtype) {
		super();
		this.touser = touser;
		this.msgtype = msgtype;
	}
	
}
