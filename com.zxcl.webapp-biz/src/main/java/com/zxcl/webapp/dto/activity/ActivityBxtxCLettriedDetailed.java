package com.zxcl.webapp.dto.activity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 保行天下活动C抽奖结果明细.
 * @author xiaoxi
 *
 */
public class ActivityBxtxCLettriedDetailed {

	/**
	 * 用户编号
	 */
	private String userId;
	
	/**
	 * 中奖金额
	 */
	private BigDecimal letteriedMoney;
	
	/**
	 * 明细序号
	 */
	private String seqId;
	
	/**
	 * 创建时间
	 */
	private Date crtTm;
	
	/**
	 * 创建人
	 */
	private String crtCde;
	
	/**
	 * 更新时间
	 */
	private Date updTm;
	
	/**
	 * 更新人
	 */
	private String updCde;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getLetteriedMoney() {
		return letteriedMoney;
	}

	public void setLetteriedMoney(BigDecimal letteriedMoney) {
		this.letteriedMoney = letteriedMoney;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public Date getCrtTm() {
		return crtTm;
	}

	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}

	public String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(String crtCde) {
		this.crtCde = crtCde;
	}

	public Date getUpdTm() {
		return updTm;
	}

	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}

	public String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(String updCde) {
		this.updCde = updCde;
	}

	@Override
	public String toString() {
		return "ActivityBxtxCLettriedDetailed [userId=" + userId
				+ ", letteriedMoney=" + letteriedMoney + ", seqId=" + seqId
				+ ", crtTm=" + crtTm + ", crtCde=" + crtCde + ", updTm="
				+ updTm + ", updCde=" + updCde + "]";
	}
}
