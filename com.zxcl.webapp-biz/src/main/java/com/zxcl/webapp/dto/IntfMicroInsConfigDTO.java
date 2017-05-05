package com.zxcl.webapp.dto;

import java.util.Date;

/**
 * 访问记录信息DTO
 * @author 444
 *
 */
public class IntfMicroInsConfigDTO extends IntfMicroInsConfigDTOKey {
	
	/**
	 * 创建时间
	 */
    private Date crtTm;
    /**
     * 修改时间
     */
    private Date updTm;
    /**
     * 总访问次数
     */
    private Integer totalCount;
    /**
     * 当天总访问次数
     */
    private Integer todayCount;
    /**
     * 最大访问次数
     */
    private Integer todayMaxCount;
    /**
     * 状态
     */
    private String status;
    
    public Date getCrtTm() {
        return crtTm;
    }

    

	public IntfMicroInsConfigDTO(String userId, String insId,Integer totalCount, Integer todayCount, String userType) {
		super(userId, insId, userType);
		this.totalCount = totalCount;
		this.todayCount = todayCount;
	}

	public IntfMicroInsConfigDTO() {
		super();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }



	@Override
	public String toString() {
		return "IntfMicroInsConfigDTO [crtTm=" + crtTm + ", updTm=" + updTm
				+ ", totalCount=" + totalCount + ", todayCount=" + todayCount
				+ ", todayMaxCount=" + todayMaxCount + ", status=" + status
				+ "]";
	}

}