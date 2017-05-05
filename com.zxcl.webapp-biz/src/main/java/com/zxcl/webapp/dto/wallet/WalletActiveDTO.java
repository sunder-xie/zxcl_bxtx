package com.zxcl.webapp.dto.wallet;

import java.math.BigDecimal;
import java.util.Date;

public class WalletActiveDTO {
    private String activeId;
    
    private String insIds;
    
    private String agentId;
    
    private String imgUrl;
    
    private String linkUrl;

    private BigDecimal amount;

    private String theme;

    private Integer maxCount;

    private Integer dedCount;

    private Date activeStartTm;

    private Date activeEndTm;

    private Date aactiveDelayTm;

    private Date crtTm;

    private String crtCde;

    private Date updTm;

    private String updCde;

    private String status;

    private String remark;
    

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId == null ? null : activeId.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getDedCount() {
        return dedCount;
    }

    public void setDedCount(Integer dedCount) {
        this.dedCount = dedCount;
    }

    public Date getActiveStartTm() {
        return activeStartTm;
    }

    public void setActiveStartTm(Date activeStartTm) {
        this.activeStartTm = activeStartTm;
    }

    public Date getActiveEndTm() {
        return activeEndTm;
    }

    public void setActiveEndTm(Date activeEndTm) {
        this.activeEndTm = activeEndTm;
    }

    public Date getAactiveDelayTm() {
        return aactiveDelayTm;
    }

    public void setAactiveDelayTm(Date aactiveDelayTm) {
        this.aactiveDelayTm = aactiveDelayTm;
    }

    public Date getCrtTm() {
        return crtTm;
    }

    public void setCrtTm(Date crtTm) {
        this.crtTm = crtTm;
    }

    public String getCrtCde() {
        return crtCde;
    }

    public void setCrtCde(String crtCde) {
        this.crtCde = crtCde == null ? null : crtCde.trim();
    }

    public Date getUpdTm() {
        return updTm;
    }

    public void setUpdTm(Date updTm) {
        this.updTm = updTm;
    }

    public String getUpdCde() {
        return updCde;
    }

    public void setUpdCde(String updCde) {
        this.updCde = updCde == null ? null : updCde.trim();
    }

    public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public String getInsIds() {
		return insIds;
	}

	public void setInsIds(String insIds) {
		this.insIds = insIds;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}