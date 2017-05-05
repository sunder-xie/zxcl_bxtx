package com.zxcl.webapp.dto.port.comdto.order;

import com.zxcl.webapp.dto.port.comdto.AttestationDTO;

/**
 * 订单号状态
 * @author zx
 *
 */
public class CombinedQueryDTO {
	/**
	 * 认证
	 */
	private AttestationDTO attestationBase;
	
	/**
	 * 锦泰：交强险投保单号
	 */
	private String tciApplyNo;
	
	/**
	 * 锦泰：商业险投保单号
	 */
	private String vciApplyNo;
	
	/**
	 * 富德：主投保单号
	 */
	private String proposalNo;
	/**
	 * 富德：子投保单号
	 */
	private String subProposalNo;
	
	
	/**
	 * 富德：业务号
	 */
	private String businessNo;
	
	/**
	 * userID
	 */
	private String userId;
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubProposalNo() {
		return subProposalNo;
	}

	public void setSubProposalNo(String subProposalNo) {
		this.subProposalNo = subProposalNo;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public AttestationDTO getAttestationBase() {
		return attestationBase;
	}

	public void setAttestationBase(AttestationDTO attestationBase) {
		this.attestationBase = attestationBase;
	}

	public String getTciApplyNo() {
		return tciApplyNo;
	}

	public void setTciApplyNo(String tciApplyNo) {
		this.tciApplyNo = tciApplyNo;
	}

	public String getVciApplyNo() {
		return vciApplyNo;
	}

	public void setVciApplyNo(String vciApplyNo) {
		this.vciApplyNo = vciApplyNo;
	}
	
}
