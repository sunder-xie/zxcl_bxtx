package com.zxcl.webapp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;


/**
 * 保单活动销售分成
 * @author xiaoxi
 *
 */
public class PolicySalesActivityFeeDTO implements Serializable{

	
	//update by zhaoxijun
	private static final long serialVersionUID = 6528600283957140746L;
	
	
	/**************************add by zhaoxijun******************************/
	/**
	 * 收支类型,收入I,支出O,待激活WI，激活WO
	 * 注意大小写
	 * {更新余额:金额大于等于0传I 小于0传O,  更新待激活金额:金额大于等于0传WI 小于0传WO}
	 */
	private String billType;
	
	/**
	 * 交易类型
	 * {自营出单奖励:11,嘉诚太平出单奖励:12}
	 */
	private String transType;
	
	private String policyBaseId;
	
	/**
	 * 预留
	 */
	private HashMap<String, Object> map;
	
	
	/********************************************************/

	/**
	 * 保单销售活动分成ID
	 */
	private String policySalesActivityFeeId;
	
	/**
	 * 产品编码 TCI-交强 VCI-商业
	 */
	private String productCode;
	
	
	/**
	 * 保费
	 */
	private BigDecimal policyPrm;
	
	/**
	 * 代理公司
	 */
	private String agentId;
	
	/**
	 * 保险公司
	 */
	private String insId;
	
	/**
	 * 保单号
	 */
	private String policyNo;
	
	/**
	 * 比例
	 */
	private BigDecimal ratio;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 出单人
	 */
	private String userId;
	
	/**
	 * 0新单未抽奖1已抽奖
	 */
	private String status;
	
	private String payeeUserId;
	
	/**
	 * 创建人
	 */
	private String crtCde;
	
	/**
	 * 创建时间
	 */
	private Date crtTm;
	
	/**
	 * 更新人
	 */
	private String updCde;
	
	/**
	 * 更新时间
	 */
	private Date updTm;

	public String getPolicySalesActivityFeeId() {
		return policySalesActivityFeeId;
	}

	public void setPolicySalesActivityFeeId(String policySalesActivityFeeId) {
		this.policySalesActivityFeeId = policySalesActivityFeeId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getPolicyPrm() {
		return policyPrm;
	}

	public void setPolicyPrm(BigDecimal policyPrm) {
		this.policyPrm = policyPrm;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(String crtCde) {
		this.crtCde = crtCde;
	}

	public Date getCrtTm() {
		return crtTm;
	}

	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}

	public String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(String updCde) {
		this.updCde = updCde;
	}

	public Date getUpdTm() {
		return updTm;
	}

	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getPolicyBaseId() {
		return policyBaseId;
	}

	public void setPolicyBaseId(String policyBaseId) {
		this.policyBaseId = policyBaseId;
	}

	public HashMap<String, Object> getMap() {
		return map;
	}

	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}

	public String getPayeeUserId() {
		return payeeUserId;
	}

	public void setPayeeUserId(String payeeUserId) {
		this.payeeUserId = payeeUserId;
	}

	@Override
	public String toString() {
		return "PolicySalesActivityFeeDTO [billType=" + billType
				+ ", transType=" + transType + ", policyBaseId=" + policyBaseId
				+ ", map=" + map + ", policySalesActivityFeeId="
				+ policySalesActivityFeeId + ", productCode=" + productCode
				+ ", policyPrm=" + policyPrm + ", agentId=" + agentId
				+ ", insId=" + insId + ", policyNo=" + policyNo + ", ratio="
				+ ratio + ", amount=" + amount + ", userId=" + userId
				+ ", status=" + status + ", payeeUserId=" + payeeUserId
				+ ", crtCde=" + crtCde + ", crtTm=" + crtTm + ", updCde="
				+ updCde + ", updTm=" + updTm + "]";
	}
	
}
