package com.zxcl.webapp.dto;

public class AgentServiceSiteKeyDTO {
    private String tOrgId;

    private String cAgetnId;

    public String gettOrgId() {
        return tOrgId;
    }

    public void settOrgId(String tOrgId) {
        this.tOrgId = tOrgId == null ? null : tOrgId.trim();
    }

    public String getcAgetnId() {
        return cAgetnId;
    }

    public void setcAgetnId(String cAgetnId) {
        this.cAgetnId = cAgetnId == null ? null : cAgetnId.trim();
    }
}