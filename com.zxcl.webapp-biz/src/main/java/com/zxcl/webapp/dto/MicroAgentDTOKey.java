package com.zxcl.webapp.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zxj
 */
public class MicroAgentDTOKey {
    private String microId;

    private String microIdAgent;

    public String getMicroId() {
        return microId;
    }

    public void setMicroId(String microId) {
        this.microId = microId == null ? null : microId.trim();
    }

    public String getMicroIdAgent() {
        return microIdAgent;
    }

    public void setMicroIdAgent(String microIdAgent) {
        this.microIdAgent = microIdAgent == null ? null : microIdAgent.trim();
    }
    
    public MicroAgentDTOKey() {
		super();
	}

	public MicroAgentDTOKey(String microId, String microIdAgent) {
		super();
		this.microId = microId;
		this.microIdAgent = microIdAgent;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}