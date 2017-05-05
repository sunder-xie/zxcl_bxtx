package com.zxcl.webapp.dto;

import java.util.Date;

public class InquiryCustomerDTO {
    private String inquiryId;

    private String customerName;

    private String customerCardId;

    private Integer customerType;
    
    
    /**
     * 是否同车主 1是,0否
     */
    private Integer isVhlOwner;

    private String crtBy;

    private Date crtTm;

    private String updBy;

    private Date updTm;

    private Integer status;
    
    private String certfCdeType;
    
    public Integer getIsVhlOwner() {
		return isVhlOwner;
	}

	public void setIsVhlOwner(Integer isVhlOwner) {
		this.isVhlOwner = isVhlOwner;
	}

	public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId == null ? null : inquiryId.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerCardId() {
        return customerCardId;
    }

    public void setCustomerCardId(String customerCardId) {
        this.customerCardId = customerCardId == null ? null : customerCardId.trim();
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getCrtBy() {
        return crtBy;
    }

    public void setCrtBy(String crtBy) {
        this.crtBy = crtBy == null ? null : crtBy.trim();
    }

    public Date getCrtTm() {
        return crtTm;
    }

    public void setCrtTm(Date crtTm) {
        this.crtTm = crtTm;
    }

    public String getUpdBy() {
        return updBy;
    }

    public void setUpdBy(String updBy) {
        this.updBy = updBy == null ? null : updBy.trim();
    }

    public Date getUpdTm() {
        return updTm;
    }

    public void setUpdTm(Date updTm) {
        this.updTm = updTm;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getCertfCdeType() {
		return certfCdeType;
	}

	public void setCertfCdeType(String certfCdeType) {
		this.certfCdeType = certfCdeType;
	}
}