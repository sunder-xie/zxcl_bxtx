package com.zxcl.webapp.dto.wxmsg;

/**
 * @ClassName: 
 * @Description:
 * @author zxj
 * @date 
 */
public class SendGroupContentMsgDTO {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public SendGroupContentMsgDTO(String content) {
		super();
		this.content = content;
	}

	public SendGroupContentMsgDTO() {
		super();
	}
	
}
