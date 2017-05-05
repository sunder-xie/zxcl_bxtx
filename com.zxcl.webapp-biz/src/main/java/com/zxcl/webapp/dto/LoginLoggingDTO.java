package com.zxcl.webapp.dto;

import java.util.Date;

/**
 * 
 * @author MAXiaoqiang
 *
 */
public class LoginLoggingDTO {

    /**
     * 主键
     */
    private int id;
    /**
     * um帐号
     */
    private String userId;
    /**
     * 登录时间
     */
    private Date loginDateTime;
    /**
     * 浏览器地址
     */
    private String remoteIPaddress;
    /**
     * 登录服务器地址
     */
    private String serverIPaddress;
    /**
     * 登录结果，1成功，0失败
     */
    private String result;
    /**
     * 应用标识
     */
    private String appId;
    /**
     * 应用名称
     */
    private String appName;

    /**
     * 失败原因
     */
    private String reason;

    /**
     * 失败原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 失败原因
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 主键
     */
    public int getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * um帐号
     */
    public String getUserId() {
        return userId;
    }

    /**
     * um帐号
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 登录时间
     */
    public Date getLoginDateTime() {
        return loginDateTime;
    }

    /**
     * 登录时间
     */
    public void setLoginDateTime(Date loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    /**
     * 浏览器地址
     */
    public String getRemoteIPaddress() {
        return remoteIPaddress;
    }

    /**
     * 浏览器地址
     */
    public void setRemoteIPaddress(String remoteIPaddress) {
        this.remoteIPaddress = remoteIPaddress;
    }

    /**
     * 登录服务器地址
     */
    public String getServerIPaddress() {
        return serverIPaddress;
    }

    /**
     * 登录服务器地址
     */
    public void setServerIPaddress(String serverIPaddress) {
        this.serverIPaddress = serverIPaddress;
    }

    /**
     * 登录结果，1成功，0失败
     */
    public String getResult() {
        return result;
    }

    /**
     * 登录结果，1成功，0失败
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 应用标识
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 应用标识
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 应用名称
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 应用名称
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "LogLogin [id=" + id + ", userId=" + userId + ", loginDateTime="
                + loginDateTime + ", remoteIPaddress=" + remoteIPaddress
                + ", serverIPaddress=" + serverIPaddress + ", result=" + result
                + ", appId=" + appId + ", appName=" + appName + ", reason="
                + reason + "]";
    }

}
