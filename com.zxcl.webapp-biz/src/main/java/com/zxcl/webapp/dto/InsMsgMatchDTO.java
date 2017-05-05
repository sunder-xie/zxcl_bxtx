package com.zxcl.webapp.dto;

/**
 * @author zxj
 * @date 2016年10月8日
 * @description 
 */
public class InsMsgMatchDTO {
    private String insId;

    private String keyWord;

    private Integer matchType;

    private String matchMsg;
    
    private Integer sort;
    
    private Integer status;
    
    /**
     * 是否能转人工报价，1-能，非1-不能
     */
    private String manualQuotn;
    
    public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId == null ? null : insId.trim();
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord == null ? null : keyWord.trim();
    }

    public Integer getMatchType() {
        return matchType;
    }

    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
    }

    public String getMatchMsg() {
        return matchMsg;
    }

    public void setMatchMsg(String matchMsg) {
        this.matchMsg = matchMsg == null ? null : matchMsg.trim();
    }

	public String getManualQuotn() {
		return manualQuotn;
	}

	public void setManualQuotn(String manualQuotn) {
		this.manualQuotn = manualQuotn;
	}
}