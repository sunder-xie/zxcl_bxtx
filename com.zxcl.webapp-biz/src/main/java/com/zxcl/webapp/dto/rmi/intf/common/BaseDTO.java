package com.zxcl.webapp.dto.rmi.intf.common;

import java.util.Date;

/**
 * @author zxj
 * @date 2016年7月13日
 * @description 
 */
public class BaseDTO extends CommonDTO{

	private static final long serialVersionUID = -2369429227459306491L;

	protected String crtCode;

	protected Date crtTime;

	protected String updCode;

	protected Date updTime;

	public String getCrtCode() {
		return crtCode;
	}

	public void setCrtCode(String crtCode) {
		this.crtCode = crtCode;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getUpdCode() {
		return updCode;
	}

	public void setUpdCode(String updCode) {
		this.updCode = updCode;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
}