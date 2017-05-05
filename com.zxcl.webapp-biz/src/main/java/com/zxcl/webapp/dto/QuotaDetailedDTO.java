package com.zxcl.webapp.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;

/**
 * 报价明细
 * 
 * @author 5555
 *
 */
public class QuotaDetailedDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 询价单
	 */
	private InquiryDTO inquiry;

	/**
	 * 报价单
	 */
	private QuotaDTO quota;

	/**
	 * 保险公司
	 */
	private InsuranceDTO insurance;

	/**
	 * 险种
	 */
	private List<CoverageItemDTO> coverageItems;
	
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

	public List<CoverageItemDTO> getCoverageItems() {
		return coverageItems;
	}

	public void setCoverageItems(List<CoverageItemDTO> coverageItems) {
		this.coverageItems = coverageItems;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getCrtCode() {
		return crtCode;
	}

	public void setCrtCode(String crtCode) {
		this.crtCode = crtCode;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public String getUpdCode() {
		return updCode;
	}

	public void setUpdCode(String updCode) {
		this.updCode = updCode;
	}

	@Override
	public String toString() {
		return "QuotaDetailedDTO [inquiry=" + inquiry + ", quota=" + quota + ", insurance=" + insurance + ", coverageItems=" + coverageItems
				+ ", crtTime=" + crtTime + ", crtCode=" + crtCode + ", updTime=" + updTime + ", updCode=" + updCode + "]";
	}

}
