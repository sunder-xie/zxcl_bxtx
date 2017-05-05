package com.zxcl.webapp.dto.rmi.intf.vote.req;

import java.util.Date;

import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.OrderDTO;
import com.zxcl.webapp.dto.QuotaDTO;
import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;

/**
 * 投保人
 * 
 * @author 5555
 *
 */
public class VoteInsuranceDTO extends BaseDTO {
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
	 * 投保人名字
	 */
	private String voteName;

	/**
	 * 证件号码
	 */
	private String voteCertNo;

	/**
	 * 证件类型
	 */
	private String voteCertType;

	/**
	 * 证件类型
	 */
	private String voteCertTypeName;

	/**
	 * 1:男，2：女
	 */
	private char voteSex;
	/**
	 * 年龄
	 */
	private Integer voteAge;
	/**
	 * 出生日期
	 */
	private Date voteBirthday;
	
	private String voteBirthdayStr;

	/**
	 * 电话
	 */
	private String votePhone;

	/**
	 * 地址
	 */
	private String voteAddress;

	/**
	 * 省级编码
	 */
	private String voteProvince;

	/**
	 * 市级编码
	 */
	private String voteCity;
	
	/**
	 * 地址
	 */
	private String addressForPort;

	
	public String getVoteBirthdayStr() {
		if(voteBirthdayStr == null && null != voteBirthday){
			this.voteBirthdayStr = DateUtil.dateToString(DateUtil.YYYY_MM_DD, voteBirthday);
		}
		return voteBirthdayStr;
	}

	public void setVoteBirthdayStr(String voteBirthdayStr) {
		this.voteBirthdayStr = voteBirthdayStr;
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

	public String getVoteName() {
		return voteName;
	}

	public void setVoteName(String voteName) {
		this.voteName = voteName;
	}

	public String getVoteCertNo() {
		return voteCertNo;
	}

	public void setVoteCertNo(String voteCertNo) {
		this.voteCertNo = voteCertNo;
	}

	public String getVoteCertType() {
		return voteCertType;
	}

	public void setVoteCertType(String voteCertType) {
		this.voteCertType = voteCertType;
	}

	public String getVoteCertTypeName() {
		return voteCertTypeName;
	}

	public void setVoteCertTypeName(String voteCertTypeName) {
		this.voteCertTypeName = voteCertTypeName;
	}

	public char getVoteSex() {
		return voteSex;
	}

	public void setVoteSex(char voteSex) {
		this.voteSex = voteSex;
	}

	public Integer getVoteAge() {
		return voteAge;
	}

	public void setVoteAge(Integer voteAge) {
		this.voteAge = voteAge;
	}

	public Date getVoteBirthday() {
		return voteBirthday;
	}

	public void setVoteBirthday(Date voteBirthday) {
		this.voteBirthday = voteBirthday;
	}

	public String getVotePhone() {
		return votePhone;
	}

	public void setVotePhone(String votePhone) {
		this.votePhone = votePhone;
	}

	public String getVoteAddress() {
		return voteAddress;
	}

	public void setVoteAddress(String voteAddress) {
		this.voteAddress = voteAddress;
	}

	public String getVoteProvince() {
		return voteProvince;
	}

	public void setVoteProvince(String voteProvince) {
		this.voteProvince = voteProvince;
	}

	public String getVoteCity() {
		return voteCity;
	}

	public void setVoteCity(String voteCity) {
		this.voteCity = voteCity;
	}

	public String getAddressForPort() {
		return addressForPort;
	}

	public void setAddressForPort(String addressForPort) {
		this.addressForPort = addressForPort;
	}
}
