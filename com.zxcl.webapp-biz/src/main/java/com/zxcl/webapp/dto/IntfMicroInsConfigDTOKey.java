package com.zxcl.webapp.dto;

public class IntfMicroInsConfigDTOKey {
	
	/**
	 * 用户ID
	 */
    private String userId;
    /**
     * 保险公司ID
     */
    private String insId;
    /**
     * 1保行天下用户  2保险公司用户
     */
    private String userType;
    
    

    public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserId() {
        return userId;
    }

    public IntfMicroInsConfigDTOKey() {
		super();
	}

	public IntfMicroInsConfigDTOKey(String userId, String insId, String userType) {
		super();
		this.userId = userId;
		this.insId = insId;
		this.userType = userType;
	}

	public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId == null ? null : insId.trim();
    }
}