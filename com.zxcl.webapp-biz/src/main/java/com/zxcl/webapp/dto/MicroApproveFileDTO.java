package com.zxcl.webapp.dto;

import java.util.Date;

/**
 * @author zxj
 * @date 2016年8月22日
 * @description 
 */
public class MicroApproveFileDTO {
    private Long approveId;

    private String fileId;

    private String crtCde;

    private Date crtTm;

    private String updCde;

    private Date updTm;

    private Integer status;
    
    

    @Override
	public String toString() {
		return "MicroApproveFileDTO [approveId=" + approveId + ", fileId="
				+ fileId + ", crtCde=" + crtCde + ", crtTm=" + crtTm
				+ ", updCde=" + updCde + ", updTm=" + updTm + ", status="
				+ status + "]";
	}

	public Long getApproveId() {
        return approveId;
    }

    public void setApproveId(Long approveId) {
        this.approveId = approveId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getCrtCde() {
        return crtCde;
    }

    public void setCrtCde(String crtCde) {
        this.crtCde = crtCde == null ? null : crtCde.trim();
    }

    public Date getCrtTm() {
        return crtTm;
    }

    public void setCrtTm(Date crtTm) {
        this.crtTm = crtTm;
    }

    public String getUpdCde() {
        return updCde;
    }

    public void setUpdCde(String updCde) {
        this.updCde = updCde == null ? null : updCde.trim();
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
}