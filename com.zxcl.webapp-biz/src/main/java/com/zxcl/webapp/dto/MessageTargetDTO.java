package com.zxcl.webapp.dto;

import java.io.Serializable;
import java.util.Date;

public class MessageTargetDTO  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8036739051068202097L;

	private String messageBodyId;

    private String messageTheme;
    
    private String messageAgentIds;

    private Date crtTm;

    private String crtBy;

    private Date updTm;

    private String updBy;
    
    private Integer contentType;

    
    public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getMessageBodyId() {
        return messageBodyId;
    }

    public void setMessageBodyId(String messageBodyId) {
        this.messageBodyId = messageBodyId == null ? null : messageBodyId.trim();
    }

    public String getMessageTheme() {
        return messageTheme;
    }

    public String getMessageAgentIds() {
		return messageAgentIds;
	}

	public void setMessageAgentIds(String messageAgentIds) {
		this.messageAgentIds = messageAgentIds;
	}

	public void setMessageTheme(String messageTheme) {
        this.messageTheme = messageTheme == null ? null : messageTheme.trim();
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
}