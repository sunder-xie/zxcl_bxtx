package com.zxcl.webapp.dto;

/**
 * @author zxj
 * @date 2016年11月3日
 * @description 
 */
public class AgentDisplayDTO {
    private String compCode;

    private String headTitle;

    private String headImgUrl;

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode == null ? null : compCode.trim();
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle == null ? null : headTitle.trim();
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
    }

	public AgentDisplayDTO() {
		super();
	}

	public AgentDisplayDTO(String headTitle, String headImgUrl) {
		super();
		this.headTitle = headTitle;
		this.headImgUrl = headImgUrl;
	}

	public static AgentDisplayDTO defaultDisplay() {
		return new AgentDisplayDTO("保行天下", null);
	}
}