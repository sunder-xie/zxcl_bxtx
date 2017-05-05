package com.zxcl.webapp.dto.port.funde;

import java.io.Serializable;

import com.zxcl.webapp.dto.rmi.intf.vote.req.RecognizeeDTO;
import com.zxcl.webapp.dto.rmi.intf.vote.req.VoteInsuranceDTO;

/**
 * 身份采集DTO
 * @author zxj
 *
 */
public class CIdentityCollectDTO  implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * userID
	 */
	private String userId;
	/**
	 * 投保人
	 */
	private VoteInsuranceDTO vote; 
	/**
	 * 被保人
	 */
	private RecognizeeDTO rec;
	/**
	 * 业务号
	 */
	private String businessNo;
	/**
	 *报价单号 
	 */
	private String quotaId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public VoteInsuranceDTO getVote() {
		return vote;
	}
	public void setVote(VoteInsuranceDTO vote) {
		this.vote = vote;
	}
	public RecognizeeDTO getRec() {
		return rec;
	}
	public void setRec(RecognizeeDTO rec) {
		this.rec = rec;
	}
	
	public String getQuotaId() {
		return quotaId;
	}
	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	
	public CIdentityCollectDTO(String userId, VoteInsuranceDTO vote,
			RecognizeeDTO rec, String businessNo, String quotaId) {
		super();
		this.userId = userId;
		this.vote = vote;
		this.rec = rec;
		this.businessNo = businessNo;
		this.quotaId = quotaId;
	}
	public CIdentityCollectDTO() {
		super();
	}
	
}
