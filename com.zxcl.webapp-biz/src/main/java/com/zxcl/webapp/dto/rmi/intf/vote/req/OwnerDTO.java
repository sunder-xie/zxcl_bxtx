package com.zxcl.webapp.dto.rmi.intf.vote.req;

import java.util.Date;

import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**
 * 车主
 * 
 * @author 5555
 *
 */
public class OwnerDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 询价
	 */
	private InquiryDTO inquiry;

	/**
	 * 报价单号
	 */
	private QuotaDTO quota;

	/**
	 * 保险公司
	 */
	private InsuranceDTO insurance;

	/**
	 * 订单
	 */
	private OrderDTO order;

	/**
	 * 车主名字
	 */
	private String ownerName;

	/**
	 * 证件号码
	 */
	private String ownerCertNo;

	/**
	 * 证件类型编码
	 */
	private String ownerCertType;

	/**
	 * 证件类型名称
	 */
	private String ownerCertTypeName;

	/**
	 * 1:男，2：女
	 */
	private char ownerSex;

	/**
	 * 省级编码
	 */
	private String ownerProvince;

	/**
	 * 市级编码
	 */
	private String ownerCity;

	/**
	 * 年龄
	 */
	private Integer ownerAge;
	/**
	 * 出生日期
	 */
	private Date ownerBirthday;

	/**
	 * 电话
	 */
	private String ownerPhone;

	/**
	 * 地址
	 */
	private String ownerAddress;

	/**
	 * 地址
	 */
	private String addressForPort;

	public String getAddressForPort() {
		return addressForPort;
	}

	public void setAddressForPort(String addressForPort) {
		this.addressForPort = addressForPort;
	}

	public InquiryDTO getInquiry() {
		return inquiry;
	}

	public void setInquiry(InquiryDTO inquiry) {
		this.inquiry = inquiry;
	}

	public QuotaDTO getQuota() {
		return quota;
	}

	public void setQuota(QuotaDTO quota) {
		this.quota = quota;
	}

	public InsuranceDTO getInsurance() {
		return insurance;
	}

	public void setInsurance(InsuranceDTO insurance) {
		this.insurance = insurance;
	}

	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}

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

	public String getOwnerCertType() {
		return ownerCertType;
	}

	public void setOwnerCertType(String ownerCertType) {
		this.ownerCertType = ownerCertType;
	}

	public String getOwnerCertTypeName() {
		return ownerCertTypeName;
	}

	public void setOwnerCertTypeName(String ownerCertTypeName) {
		this.ownerCertTypeName = ownerCertTypeName;
	}

	public char getOwnerSex() {
		return ownerSex;
	}

	public void setOwnerSex(char ownerSex) {
		this.ownerSex = ownerSex;
	}

	public String getOwnerProvince() {
		return ownerProvince;
	}

	public void setOwnerProvince(String ownerProvince) {
		this.ownerProvince = ownerProvince;
	}

	public String getOwnerCity() {
		return ownerCity;
	}

	public void setOwnerCity(String ownerCity) {
		this.ownerCity = ownerCity;
	}

	public Integer getOwnerAge() {
		return ownerAge;
	}

	public void setOwnerAge(Integer ownerAge) {
		this.ownerAge = ownerAge;
	}

	public Date getOwnerBirthday() {
		return ownerBirthday;
	}

	public void setOwnerBirthday(Date ownerBirthday) {
		this.ownerBirthday = ownerBirthday;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public String getOwnerAddress() {
		return ownerAddress;
	}

	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}
}
