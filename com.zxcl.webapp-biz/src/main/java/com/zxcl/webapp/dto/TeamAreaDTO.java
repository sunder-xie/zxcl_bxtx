package com.zxcl.webapp.dto;

import java.util.Date;

/**
 * 团队对应的地区
 * @author xiaoxi
 *
 */
public class TeamAreaDTO {

	/**
	 * 团队编号
	 */
	private String teamId;
	
	/**
	 * 区域编码
	 */
	private String areaCode;
	
	/**
	 * 创建时间
	 */
	private Date crtTm;
	
	/**
	 * 创建人
	 */
	private String crtCde;
	
	/**
	 * 更新时间
	 */
	private Date updTm;
	
	/**
	 * 更新人
	 */
	private String updCde;

	@Override
	public String toString() {
		return "TeamAreaDTO [teamId=" + teamId + ", areaCode=" + areaCode
				+ ", crtTm=" + crtTm + ", crtCde=" + crtCde + ", updTm="
				+ updTm + ", updCde=" + updCde + "]";
	}
	
}
