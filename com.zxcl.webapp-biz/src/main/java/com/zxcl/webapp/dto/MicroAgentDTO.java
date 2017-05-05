package com.zxcl.webapp.dto;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zxj
 */
public class MicroAgentDTO extends MicroAgentDTOKey {
    private Date crtTm;

    private String crtBy;

    private Date updTm;

    private String updBy;

    private Integer status;
    
    private String userId;

    private String userIdAgent;
    
    private String userAgentMircoName;
    
    

    public String getUserAgentMircoName() {
		return userAgentMircoName;
	}

	public void setUserAgentMircoName(String userAgentMircoName) {
		this.userAgentMircoName = userAgentMircoName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserIdAgent() {
		return userIdAgent;
	}

	public void setUserIdAgent(String userIdAgent) {
		this.userIdAgent = userIdAgent;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
    
}