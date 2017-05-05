package com.zxcl.webapp.dto;

import java.io.Serializable;
import java.util.Date;

public class MessageDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7647762564802958797L;

	private String messageId;

    private String messageBodyId;

    private String messageType;

    private String messageSendFrom;

    private String messageSendTo;

    private String wxTemplateId;

    private String sendResultMsg;

    private Date crtTm;

    private String crtBy;

    private Date updTm;

    private String updBy;

    private Integer timerStatus;

    private Integer status;
    
    private MessageTargetDTOWithBLOBs messageTargetDTO;
    
    

    public MessageTargetDTOWithBLOBs getMessageTargetDTO() {
		return messageTargetDTO;
	}

	public void setMessageTargetDTO(MessageTargetDTOWithBLOBs messageTargetDTO) {
		this.messageTargetDTO = messageTargetDTO;
	}

	public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public String getMessageBodyId() {
        return messageBodyId;
    }

    public void setMessageBodyId(String messageBodyId) {
        this.messageBodyId = messageBodyId == null ? null : messageBodyId.trim();
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }

    public String getMessageSendFrom() {
        return messageSendFrom;
    }

    public void setMessageSendFrom(String messageSendFrom) {
        this.messageSendFrom = messageSendFrom == null ? null : messageSendFrom.trim();
    }

    public String getMessageSendTo() {
        return messageSendTo;
    }

    public void setMessageSendTo(String messageSendTo) {
        this.messageSendTo = messageSendTo == null ? null : messageSendTo.trim();
    }

    public String getWxTemplateId() {
        return wxTemplateId;
    }

    public void setWxTemplateId(String wxTemplateId) {
        this.wxTemplateId = wxTemplateId == null ? null : wxTemplateId.trim();
    }

    public String getSendResultMsg() {
        return sendResultMsg;
    }

    public void setSendResultMsg(String sendResultMsg) {
        this.sendResultMsg = sendResultMsg == null ? null : sendResultMsg.trim();
    }

    public Date getCrtTm() {
        return crtTm;
    }

    public void setCrtTm(Date crtTm) {
        this.crtTm = crtTm;
    }

    public String getCrtBy() {
        return crtBy;
    }

    public void setCrtBy(String crtBy) {
        this.crtBy = crtBy == null ? null : crtBy.trim();
    }

    public Date getUpdTm() {
        return updTm;
    }

    public void setUpdTm(Date updTm) {
        this.updTm = updTm;
    }

    public String getUpdBy() {
        return updBy;
    }

    public void setUpdBy(String updBy) {
        this.updBy = updBy == null ? null : updBy.trim();
    }

    public Integer getTimerStatus() {
        return timerStatus;
    }

    public void setTimerStatus(Integer timerStatus) {
        this.timerStatus = timerStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}