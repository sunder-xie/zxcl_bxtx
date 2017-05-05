package com.zxcl.webapp.dto.rmi.intf.vote.resp;

import java.io.Serializable;

import com.zxcl.webapp.dto.rmi.intf.common.CommonDTO;

/**
 * 投保单综合查询
 * @author zxj
 * @date 2016年7月13日
 * @description 
 */
public class CombinedQueryDTO extends CommonDTO implements Serializable {

	private static final long serialVersionUID = 1943940477080074973L;
	
	/**
	 * 保险公司编码
	 */
	private String insId;
	/**
	 * 错误代码；参见代码
	 */
	private String errorCode;

	/**
	 * 错误说明；
	 */
	private String errorMessage;

	/**
	 * 交强险投保单状态；参见代码
	 */
	private String tciApplyStatus;

	/**
	 * 交强险保单号
	 * 
	 */
	private String tciPolicyNO;

	/**
	 * 商业险投保单状态；参见代码
	 */
	private String vciApplyStatus;

	/**
	 * 商业险保单号
	 */
	private String vciPolicyNO;

	/**
	 * 人工核保意见
	 */
	private String undrOpinion;

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getTciApplyStatus() {
		return tciApplyStatus;
	}

	public void setTciApplyStatus(String tciApplyStatus) {
		this.tciApplyStatus = tciApplyStatus;
	}

	public String getTciPolicyNO() {
		return tciPolicyNO;
	}

	public void setTciPolicyNO(String tciPolicyNO) {
		this.tciPolicyNO = tciPolicyNO;
	}

	public String getVciApplyStatus() {
		return vciApplyStatus;
	}

	public void setVciApplyStatus(String vciApplyStatus) {
		this.vciApplyStatus = vciApplyStatus;
	}

	public String getVciPolicyNO() {
		return vciPolicyNO;
	}

	public void setVciPolicyNO(String vciPolicyNO) {
		this.vciPolicyNO = vciPolicyNO;
	}

	public String getUndrOpinion() {
		return undrOpinion;
	}

	public void setUndrOpinion(String undrOpinion) {
		this.undrOpinion = undrOpinion;
	}

	@Override
	public String toString() {
		return "CombinedQueryDTO [insId=" + insId + ", errorCode=" + errorCode
				+ ", errorMessage=" + errorMessage + ", tciApplyStatus="
				+ tciApplyStatus + ", tciPolicyNO=" + tciPolicyNO
				+ ", vciApplyStatus=" + vciApplyStatus + ", vciPolicyNO="
				+ vciPolicyNO + ", undrOpinion=" + undrOpinion + "]";
	}
	
}
