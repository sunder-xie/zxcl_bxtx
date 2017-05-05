package com.zxcl.webapp.dto;

import java.util.Date;

public class ManualQuotaNotfiyToQuoterDTO {

	private int quoterId;
	
	private String quoterName;
	
	private String wxOpenId;
	
	private String startWorkTime;
	
	private String stopWorkTime;
	
	private Date lastSendTime;
	
	private int count;

	public int getQuoterId() {
		return quoterId;
	}

	public void setQuoterId(int quoterId) {
		this.quoterId = quoterId;
	}

	public String getQuoterName() {
		return quoterName;
	}

	public void setQuoterName(String quoterName) {
		this.quoterName = quoterName;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public String getStartWorkTime() {
		return startWorkTime;
	}

	public void setStartWorkTime(String startWorkTime) {
		this.startWorkTime = startWorkTime;
	}

	public String getStopWorkTime() {
		return stopWorkTime;
	}

	public void setStopWorkTime(String stopWorkTime) {
		this.stopWorkTime = stopWorkTime;
	}

	public Date getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
