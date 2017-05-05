package com.zxcl.webapp.dto.rmi.intf.vote.req;

import java.util.Date;

import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**
 * 被保人
 * 
 * @author 5555
 *
 */
public class RecognizeeDTO extends BaseDTO {
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
	 * 驾驶员名字
	 */
	private String recName;

	/**
	 * 证件号码
	 */
	private String recCertNo;

	/**
	 * 证件类型编码
	 */
	private String recCertType;

	/**
	 * 证件类型名称
	 */
	private String recCertTypeName;

	/**
	 * 1:男，2：女
	 */
	private char recSex;

	/**
	 * 年龄
	 */
	private Integer recAge;
	/**
	 * 出生日期
	 */
	private Date recBirthday;
	
	private String recBirthdayStr;

	/**
	 * 电话
	 */
	private String recPhone;

	/**
	 * 省级编码
	 */
	private String recProvince;

	/**
	 * 市级编码
	 */
	private String recCity;

	/**
	 * 地址
	 */
	private String recAddress;
	
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

	public String getRecBirthdayStr() {
		if(recBirthdayStr == null && null != recBirthday){
			this.recBirthdayStr = DateUtil.dateToString(DateUtil.YYYY_MM_DD, recBirthday);
		}
		return recBirthdayStr;
	}

	public void setRecBirthdayStr(String recBirthdayStr) {
		this.recBirthdayStr = recBirthdayStr;
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

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getRecCertNo() {
		return recCertNo;
	}

	public void setRecCertNo(String recCertNo) {
		this.recCertNo = recCertNo;
	}

	public String getRecCertType() {
		return recCertType;
	}

	public void setRecCertType(String recCertType) {
		this.recCertType = recCertType;
	}

	public String getRecCertTypeName() {
		return recCertTypeName;
	}

	public void setRecCertTypeName(String recCertTypeName) {
		this.recCertTypeName = recCertTypeName;
	}

	public char getRecSex() {
		return recSex;
	}

	public void setRecSex(char recSex) {
		this.recSex = recSex;
	}

	public Integer getRecAge() {
		return recAge;
	}

	public void setRecAge(Integer recAge) {
		this.recAge = recAge;
	}

	public Date getRecBirthday() {
		return recBirthday;
	}

	public void setRecBirthday(Date recBirthday) {
		this.recBirthday = recBirthday;
	}

	public String getRecPhone() {
		return recPhone;
	}

	public void setRecPhone(String recPhone) {
		this.recPhone = recPhone;
	}

	public String getRecProvince() {
		return recProvince;
	}

	public void setRecProvince(String recProvince) {
		this.recProvince = recProvince;
	}

	public String getRecCity() {
		return recCity;
	}

	public void setRecCity(String recCity) {
		this.recCity = recCity;
	}

	public String getRecAddress() {
		return recAddress;
	}

	public void setRecAddress(String recAddress) {
		this.recAddress = recAddress;
	}
}
