package com.zxcl.webapp.dto;

import java.util.Date;
import java.util.List;

/**
 * @author zxj
 * @date 2016年8月22日
 * @description 
 */
public class MicroApproveDTO {
    private Long approveId;

    private String microId;

    private String microCardId;

    private String microRealName;

    private Integer approveState;

    private String approveReason;

    private String crtCde;

    private Date crtTime;

    private String updCde;

    private Date updTime;
    
    private List<MicroApproveFileDTO> files;
    
    private Integer status;
    
    
    


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getApproveId() {
        return approveId;
    }

    public void setApproveId(Long approveId) {
        this.approveId = approveId;
    }

    public String getMicroId() {
        return microId;
    }

    public void setMicroId(String microId) {
        this.microId = microId == null ? null : microId.trim();
    }

    public String getMicroCardId() {
        return microCardId;
    }

    public void setMicroCardId(String microCardId) {
        this.microCardId = microCardId == null ? null : microCardId.trim();
    }

    public String getMicroRealName() {
        return microRealName;
    }

    public void setMicroRealName(String microRealName) {
        this.microRealName = microRealName == null ? null : microRealName.trim();
    }

    public Integer getApproveState() {
        return approveState;
    }

    public void setApproveState(Integer approveState) {
        this.approveState = approveState;
    }

    public String getApproveReason() {
        return approveReason;
    }

    public void setApproveReason(String approveReason) {
        this.approveReason = approveReason == null ? null : approveReason.trim();
    }

    public String getCrtCde() {
        return crtCde;
    }

    public void setCrtCde(String crtCde) {
        this.crtCde = crtCde == null ? null : crtCde.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getUpdCde() {
        return updCde;
    }

    public void setUpdCde(String updCde) {
        this.updCde = updCde == null ? null : updCde.trim();
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

	public List<MicroApproveFileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<MicroApproveFileDTO> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "MicroApproveDTO [approveId=" + approveId + ", microId="
				+ microId + ", microCardId=" + microCardId + ", microRealName="
				+ microRealName + ", approveState=" + approveState
				+ ", approveReason=" + approveReason + ", crtCde=" + crtCde
				+ ", crtTime=" + crtTime + ", updCde=" + updCde + ", updTime="
				+ updTime + ", files=" + files + ", status=" + status + "]";
	}


    
}