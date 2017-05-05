package com.zxcl.webapp.biz.util.model;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class BaseParam {
	//操作人
	private String operateUser;
	
	//操作时间
	private Date opetateDate;
	
	//request
	private HttpServletRequest request;
	
	//searchName
	private String wd;
	
	private String microId;
	
	
	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getMicroId() {
		return microId;
	}

	public void setMicroId(String microId) {
		this.microId = microId;
	}

	public BaseParam(String operateUser, Date opetateDate){
		this.operateUser = operateUser;
		this.opetateDate = opetateDate;
	}
	
	public BaseParam(String operateUser){
		this.operateUser = operateUser;
	}
	
	public BaseParam(String operateUser, Date opetateDate, HttpServletRequest request){
		this.operateUser = operateUser;
		this.opetateDate = opetateDate;
		this.request = request;
	}
	
	public BaseParam() {
		super();
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public Date getOpetateDate() {
		if(null == this.opetateDate){
			return new java.util.Date();
		}
		return opetateDate;
	}
	public void setOpetateDate(Date opetateDate) {
		this.opetateDate = opetateDate;
	}
	public String getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}
}
