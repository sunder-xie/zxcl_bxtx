package com.zxcl.webapp.biz.service.openapi.dto;

public class QtnVhlOwnerDTO {

	private String ownerName;
	private String ownerCertNo;
	
	/**
	 * YYYY-MM-DD
	 */
	private String ownerBirthday;
	
	/**
	 * M/F
	 */
	private String ownerSexCode;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerCertNo() {
		return ownerCertNo;
	}

	public void setOwnerCertNo(String ownerCertNo) {
		this.ownerCertNo = ownerCertNo;
	}

	public String getOwnerBirthday() {
		return ownerBirthday;
	}

	public void setOwnerBirthday(String ownerBirthday) {
		this.ownerBirthday = ownerBirthday;
	}

	public String getOwnerSexCode() {
		return ownerSexCode;
	}

	public void setOwnerSexCode(String ownerSexCode) {
		this.ownerSexCode = ownerSexCode;
	}
	
}
