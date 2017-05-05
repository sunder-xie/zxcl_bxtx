package com.zxcl.webapp.dto;

public class ApiAppControlDTO {
    private String appId;

    private String controlItem;

    private Integer isOn;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getControlItem() {
        return controlItem;
    }

    public void setControlItem(String controlItem) {
        this.controlItem = controlItem == null ? null : controlItem.trim();
    }

    public Integer getIsOn() {
        return isOn;
    }

    public void setIsOn(Integer isOn) {
        this.isOn = isOn;
    }
}