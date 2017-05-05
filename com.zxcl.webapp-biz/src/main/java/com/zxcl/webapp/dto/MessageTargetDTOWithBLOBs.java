package com.zxcl.webapp.dto;


public class MessageTargetDTOWithBLOBs extends MessageTargetDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3864542567583276104L;

	private String messageBody;

    private String wxPostContent;

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody == null ? null : messageBody.trim();
    }

    public String getWxPostContent() {
        return wxPostContent;
    }

    public void setWxPostContent(String wxPostContent) {
        this.wxPostContent = wxPostContent == null ? null : wxPostContent.trim();
    }
}