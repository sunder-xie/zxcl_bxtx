package com.zxcl.webapp.dto.activity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动C的中奖名单
 * @author xiaoxi
 *
 */
public class ActivityBxtxCNameListDTO {

	private String userId;
	
	/**
	 * 总的抽奖次数
	 */
	private Integer lotteryNum;
	
	/**
	 * 已抽奖次数
	 */
	private Integer lotteriedNum;
	
	/**
	 * 总的抽奖金额
	 */
	private BigDecimal lotteryTotal;
	
	/**
	 * 已抽奖金额
	 */
	private BigDecimal lotteriedTotal;
	
	/**
	 * 活动序号
	 */
	private String activitySeqId;
	
	/**
	 * 状态1正常-1失效
	 */
	private Integer status;
	
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

	public Integer getLotteryNum() {
		return lotteryNum;
	}

	public void setLotteryNum(Integer lotteryNum) {
		this.lotteryNum = lotteryNum;
	}

	public Integer getLotteriedNum() {
		return lotteriedNum;
	}

	public void setLotteriedNum(Integer lotteriedNum) {
		this.lotteriedNum = lotteriedNum;
	}

	public BigDecimal getLotteryTotal() {
		return lotteryTotal;
	}

	public void setLotteryTotal(BigDecimal lotteryTotal) {
		this.lotteryTotal = lotteryTotal;
	}

	public BigDecimal getLotteriedTotal() {
		return lotteriedTotal;
	}

	public void setLotteriedTotal(BigDecimal lotteriedTotal) {
		this.lotteriedTotal = lotteriedTotal;
	}

	public String getActivitySeqId() {
		return activitySeqId;
	}

	public void setActivitySeqId(String activitySeqId) {
		this.activitySeqId = activitySeqId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		return "ActivityBxtxCNameListDTO [userId=" + userId + ", lotteryNum="
				+ lotteryNum + ", lotteriedNum=" + lotteriedNum
				+ ", lotteryTotal=" + lotteryTotal + ", lotteriedTotal="
				+ lotteriedTotal + ", activitySeqId=" + activitySeqId
				+ ", status=" + status + ", crtTm=" + crtTm + ", crtCde="
				+ crtCde + ", updTm=" + updTm + ", updCde=" + updCde + "]";
	}
	
}
