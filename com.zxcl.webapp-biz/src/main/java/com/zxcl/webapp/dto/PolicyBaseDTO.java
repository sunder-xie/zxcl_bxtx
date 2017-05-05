package com.zxcl.webapp.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PolicyBaseDTO {
    private String policyBaseId;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String productCode;

    private String agentId;

    private String insId;

    private String policyNo;

    private Date policyCrtTm;

    private String plateNo;

    private String frmNo;

    private String engNo;

    private String ownerName;

    private BigDecimal policyPrm;

    private BigDecimal tax;

    private String userId;

    private String calStatus;
    
    private String teamId;

    public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getPolicyBaseId() {
        return policyBaseId;
    }

    public void setPolicyBaseId(String policyBaseId) {
        this.policyBaseId = policyBaseId == null ? null : policyBaseId.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId == null ? null : insId.trim();
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo == null ? null : policyNo.trim();
    }

    public Date getPolicyCrtTm() {
        return policyCrtTm;
    }

    public void setPolicyCrtTm(Date policyCrtTm) {
        this.policyCrtTm = policyCrtTm;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo == null ? null : plateNo.trim();
    }

    public String getFrmNo() {
        return frmNo;
    }

    public void setFrmNo(String frmNo) {
        this.frmNo = frmNo == null ? null : frmNo.trim();
    }

    public String getEngNo() {
        return engNo;
    }

    public void setEngNo(String engNo) {
        this.engNo = engNo == null ? null : engNo.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public BigDecimal getPolicyPrm() {
        return policyPrm;
    }

    public void setPolicyPrm(BigDecimal policyPrm) {
        this.policyPrm = policyPrm;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCalStatus() {
        return calStatus;
    }

    public void setCalStatus(String calStatus) {
        this.calStatus = calStatus == null ? null : calStatus.trim();
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
    
}