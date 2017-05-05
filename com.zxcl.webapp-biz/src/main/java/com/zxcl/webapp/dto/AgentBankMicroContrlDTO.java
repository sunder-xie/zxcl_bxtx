package com.zxcl.webapp.dto;

import java.util.Date;

/**
 * @author zxj
 * @date 2016年10月10日
 * @description 
 */
public class AgentBankMicroContrlDTO {
    private String userId;

    private Date crtTm;

    private Date updTm;

    private Integer totalCount;

    private Integer todayCount;

    private Integer todayMaxCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCrtTm() {
        return crtTm;
    }

    public void setCrtTm(Date crtTm) {
        this.crtTm = crtTm;
    }

    public Date getUpdTm() {
        return updTm;
    }

    public void setUpdTm(Date updTm) {
        this.updTm = updTm;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(Integer todayCount) {
        this.todayCount = todayCount;
    }

    public Integer getTodayMaxCount() {
        return todayMaxCount;
    }

    public void setTodayMaxCount(Integer todayMaxCount) {
        this.todayMaxCount = todayMaxCount;
    }
}