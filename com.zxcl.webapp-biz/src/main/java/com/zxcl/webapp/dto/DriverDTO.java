package com.zxcl.webapp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;

/**
 * 驾驶员信息
 * 
 * @author 5555
 *
 */
public class DriverDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 询价的信息
	 */
	private InquiryDTO inquiry;

	/**
	 * 驾驶员的ID
	 */
	private String driverId;

	/**
	 * 驾驶员名字
	 */
	private String name;

	/**
	 * 身份证号码
	 */
	private String certNo;

	/**
	 * 1:男，2：女
	 */
	private char sex;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 出生日期
	 */
	private Date birthday;
	/**
	 * 驾驶证号
	 */
	private String licenseNo;
	/**
	 * 驾驶证类型编码
	 */
	private String driverType;
	/**
	 * 驾驶证类型名称
	 */
	private String driverTypeName;

	/**
	 * 驾驶证首发日期
	 */
	private Date licenseDate;

	public InquiryDTO getInquiry() {
		return inquiry;
	}

	public void setInquiry(InquiryDTO inquiry) {
		this.inquiry = inquiry;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getName() {
		return name;
	}

	public String getLicenseDateStr() {
		return null != licenseDate ? DateUtil.dateToString(DateUtil.YYYY_MM_DD, licenseDate) : null;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getDriverType() {
		return driverType;
	}

	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}

	public String getDriverTypeName() {
		return driverTypeName;
	}

	public void setDriverTypeName(String driverTypeName) {
		this.driverTypeName = driverTypeName;
	}

	public String getLicenseDate() {
		return DateUtil.dateToString(DateUtil.YYYY_MM_DD, this.licenseDate);
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setLicenseDate(Date licenseDate) {
		this.licenseDate = licenseDate;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
