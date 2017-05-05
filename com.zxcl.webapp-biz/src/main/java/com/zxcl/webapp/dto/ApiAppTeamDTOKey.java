package com.zxcl.webapp.dto;

public class ApiAppTeamDTOKey {
    private String appId;

    private String teamId;

    public ApiAppTeamDTOKey() {
		super();
	}

	public ApiAppTeamDTOKey(String appId, String teamId) {
		super();
		this.appId = appId;
		this.teamId = teamId;
	}

	public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId == null ? null : teamId.trim();
    }
}