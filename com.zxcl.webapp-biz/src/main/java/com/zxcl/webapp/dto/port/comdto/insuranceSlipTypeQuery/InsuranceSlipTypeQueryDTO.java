package com.zxcl.webapp.dto.port.comdto.insuranceSlipTypeQuery;

import com.zxcl.webapp.dto.port.comdto.AttestationDTO;

/**
 * 投保单查询
 * @author zx
 *
 */
public class InsuranceSlipTypeQueryDTO {
	
	
	/**
	 * 认证
	 */
	private AttestationDTO attestationBase;
	
	/**
	 * 天平：投保单号
	 */
	private String applyPolicyNo;

	public String getApplyPolicyNo() {
		return applyPolicyNo;
	}

	public void setApplyPolicyNo(String applyPolicyNo) {
		this.applyPolicyNo = applyPolicyNo;
	}

	public AttestationDTO getAttestationBase() {
		return attestationBase;
	}

	public void setAttestationBase(AttestationDTO attestationBase) {
		this.attestationBase = attestationBase;
	}
}
