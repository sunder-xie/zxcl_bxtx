package com.zxcl.webapp.dto.wallet;

public class WalletStayDTOKey {
    private String userId;

    private String stayId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getStayId() {
        return stayId;
    }

    public void setStayId(String stayId) {
        this.stayId = stayId == null ? null : stayId.trim();
    }
}