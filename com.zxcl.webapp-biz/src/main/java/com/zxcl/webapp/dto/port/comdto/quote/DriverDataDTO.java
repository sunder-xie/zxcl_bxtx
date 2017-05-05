package com.zxcl.webapp.dto.port.comdto.quote;

/**
 * 驾驶员信息     发送
 * @author zx
 *
 */
public class DriverDataDTO {
	/**
	 * 锦泰：驾驶员名称
	 */
	private String name;
	
	/**
	 * 锦泰：证件号码
	 */
	private String credentialNo;
	
	/**
	 * 锦泰：性别
	 */
	private String sex;
	
	/**
	 * 锦泰：年龄
	 */
	private String age;
	
	/**
	 * 锦泰：驾驶证类型
	 */
	private String driverTypeCode;
	
	/**
	 * 锦泰：驾驶证描述
	 */
	private String licenseDesc;
	
	/**
	 * 锦泰：驾驶证号
	 */
	private String licenseNo;
	
	/**
	 * 锦泰：驾驶证首次发证日期（精确到天，格式YYYYMMDD）
	 */
	private String licensedDate;
	
	/**
	 * 锦泰：是否是主驾驶员
	 */
	private String isMajorDriver;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCredentialNo() {
		return credentialNo;
	}

	public void setCredentialNo(String credentialNo) {
		this.credentialNo = credentialNo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDriverTypeCode() {
		return driverTypeCode;
	}

	public void setDriverTypeCode(String driverTypeCode) {
		this.driverTypeCode = driverTypeCode;
	}

	public String getLicenseDesc() {
		return licenseDesc;
	}

	public void setLicenseDesc(String licenseDesc) {
		this.licenseDesc = licenseDesc;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getLicensedDate() {
		return licensedDate;
	}

	public void setLicensedDate(String licensedDate) {
		this.licensedDate = licensedDate;
	}

	public String getIsMajorDriver() {
		return isMajorDriver;
	}

	public void setIsMajorDriver(String isMajorDriver) {
		this.isMajorDriver = isMajorDriver;
	}
}
