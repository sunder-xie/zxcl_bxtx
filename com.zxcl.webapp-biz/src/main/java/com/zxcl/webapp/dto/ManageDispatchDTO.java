package com.zxcl.webapp.dto;

import java.util.Date;

public class ManageDispatchDTO {
    private String id;

    private String recName;

    private String phone;

    private String areaCode;
    
    private String areaCodeStr;

    private String areaChildCode;
    
    private String areaChildCodeStr;

    private String address;

    private Long sort;

    private Date crtTm;

    private String crtCde;

    private Date updTm;

    private String updCde;

    private Integer status;
    
    private Integer moren ;//1默认

    
    public String getAreaCodeStr() {
		return areaCodeStr;
	}

	public void setAreaCodeStr(String areaCodeStr) {
		this.areaCodeStr = areaCodeStr;
	}

	public String getAreaChildCodeStr() {
		return areaChildCodeStr;
	}

	public void setAreaChildCodeStr(String areaChildCodeStr) {
		this.areaChildCodeStr = areaChildCodeStr;
	}

	public Integer getMoren() {
		return moren;
	}

	public void setMoren(Integer moren) {
		this.moren = moren;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName == null ? null : recName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getAreaChildCode() {
        return areaChildCode;
    }

    public void setAreaChildCode(String areaChildCode) {
        this.areaChildCode = areaChildCode == null ? null : areaChildCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}