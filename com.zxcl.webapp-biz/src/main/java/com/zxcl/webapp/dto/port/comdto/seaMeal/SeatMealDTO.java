package com.zxcl.webapp.dto.port.comdto.seaMeal;

import java.util.List;

import com.zxcl.webapp.dto.port.comdto.AttestationDTO;

/**
 * 套餐
 * @author 444
 *
 */
public class SeatMealDTO {
	/**
	 * 认证
	 */
	private AttestationDTO attestationBase;
	
	/**
	 * 阳光：请求代码
	 */
	private String transType;
	
	/**
	 * 阳光：出单机构
	 */
	private String makeCom;
	
	/**
	 * 阳光：系统标识符
	 */
	private String operateSite;
	
	/**
	 * 阳光：新老电销标示
	 */
	private String newFlag;
	
	/**
	 * 阳光：车型信息的id号
	 */
	private String modelInfoId;
	
	/**
	 * 阳光：是否承保交强险（0否，1是）
	 */
	private String CIFlag;		
	
	/**
	 * 阳光：保单种类
	 */
	private String policySort;
	
	/**
	 * 阳光：同保标示
	 */
	private String relievIngareaCode;
	
	/**
	 * 阳光：商业险起保日期（交强、商业分开调用时，此节点不可与 CISTARTDATE同时传值）
	 */
	private String BIStartDate;
	
	/**
	 * 阳光：商业险终保日期
	 */
	private String BIEndDate;
	
	/**
	 * 阳光：商业险起保小时（默认0）
	 */
	private String BIStartHour;		
	
	/**
	 * 阳光：商业险终保小时（默认24）
	 */
	private String BIEndHour;		
	
	/**
	 * 阳光：商业险录单日期
	 */
	private String inputDate;
	
	/**
	 * 阳光：上年是否在本公司投保商业险（0否，1是）
	 */
	private String renewalFlag;		
	
	/**
	 * 阳光：上年是否在本公司投保交强险（0否，1是）
	 */
	private String renewalCIFlag;
	
	/**
	 * 阳光：交强被续保单号
	 */
	private String renewalCIPolicyNo;
	
	/**
	 * 阳光：商业被续保单号
	 */
	private String renewalBIPolicyNo;
	
	/**
	 * 阳光：交强跨省未出险年数
	 */
	private String CINoDamageYears;
	
	/**
	 * 阳光：商业跨省未出险年数
	 */
	private String BINoDamageYears;
	
	/**
	 * 阳光：交强险起保日期（CI_Flag为1时必传）
	 */
	private String CIStartDate;		
	
	/**
	 * 阳光：交强险终保日期（CI_Flag为1时必传）
	 */
	private String CIEndDate;		
	
	/**
	 * 阳光：交强险起保小时（CI_Flag为1时必传默认0）
	 */
	private String CIStartHour;		
	
	/**
	 * 阳光：交强险终保小时（CI_Flag为1时必传默认24）
	 */
	private String ciendHour;		
	
	/**
	 * 阳光：交强险保单号（在其他公司承保交强险时必传）
	 */
	private String CIPolicyNo;		
	
	/**
	 * 阳光：交强险承保公司代码（在其他公司承保交强险时必传）
	 */
	private String CICompanyCode;		
	
	/**
	 * 阳光：不投保交强险原因代码（不承保交强险时必传01军警车辆，02厂内车辆）
	 */
	private String ciunProposalReason;	
	
	/**
	 * 阳光：代理人名称
	 */
	private String agentName;
	
	/**
	 * 阳光：代理人代码
	 */
	private String agentCode;
	
	/**
	 * 阳光：代理协议号
	 */
	private String agreeMentno;
	
	/**
	 * 阳光：操作员
	 */
	private String operatorCode;
	
	/**
	 * 阳光：经办人
	 */
	private String handlercode;
	
	/**
	 * 阳光：业务来源
	 */
	private String bussinessNature;
	
	/**
	 * 阳光：车队协议号
	 */
	private String contractNo;
	
	/**
	 * 阳光：服务区域代码
	 */
	private String serviceArea;
	
	/**
	 * 阳光：代理人资格证号
	 */
	private String prospectNo;
	
	/**
	 * 阳光：会员卡号
	 */
	private String businessRemark2;
	
	/**
	 * 阳光：交强险险种
	 */
	private String CIRiskCode;
	
	/**
	 * 阳光：商业险险种
	 */
	private String BIRiskcode;
	
	/**
	 * 阳光：车主
	 */
	private String carOwner;
	
	/**
	 * 阳光：交强险即时生效标志
	 */
	private String immEvalIdFlagCI;
	
	/**
	 * 阳光：商业险即时生效
	 */
	private String immEvalIdFlagBI;
	
	/**
	 * 阳光：业务渠道(八大渠道)【业务渠道】
	 */
	private String channelType;
	
	/**
	 * 阳光：除外标识
	 */
	private String businessMarks;
	
	/**
	 * 阳光：满足清单
	 */
	private String exceptiontags;
	
	/**
	 * 阳光：客户等级
	 */
	private String customerLevel;
	
	/**
	 * 阳光：交强险续保保单号
	 */
	private String crenewPolicyNoCI;
	
	/**
	 * 阳光：商业险续保保单号
	 */
	private String crenewPolicyNoBI;
	
	/**
	 * 阳光：交强险关联保单号
	 */
	private String relationPolicyNoCI;
	
	/**
	 * 阳光：商业险关联保单号
	 */
	private String relationPolicyNoBI;
	
	/**
	 * 阳光：开具税务机关代码
	 */
	private String taxComCode;
	
	/**
	 * 阳光：完税（减免税）凭证号
	 */
	private String paidFreeCertifiCate;
	
	/**
	 * 阳光：无赔优待等级
	 */
	private String noclaimadJustLevel;
	
	/**
	 * 阳光：业务模式
	 */
	private String businessModel;

	/**
	 * 汽车模型数据
	 */
	private List<CarModelDataDTO> carModelDataList;
	
	/**
	 * 投保人信息
	 */
	private InsuredDataDTO insuredDataDTO;
	
	/**
	 * 
	 */
	private List<PrptNewEngageDataDTO> prptNewEngageDataList;

	public AttestationDTO getAttestationBase() {
		return attestationBase;
	}

	public void setAttestationBase(AttestationDTO attestationBase) {
		this.attestationBase = attestationBase;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getMakeCom() {
		return makeCom;
	}

	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	public String getOperateSite() {
		return operateSite;
	}

	public void setOperateSite(String operateSite) {
		this.operateSite = operateSite;
	}

	public String getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}

	public String getModelInfoId() {
		return modelInfoId;
	}

	public void setModelInfoId(String modelInfoId) {
		this.modelInfoId = modelInfoId;
	}

	public String getCIFlag() {
		return CIFlag;
	}

	public void setCIFlag(String cIFlag) {
		CIFlag = cIFlag;
	}

	public String getPolicySort() {
		return policySort;
	}

	public void setPolicySort(String policySort) {
		this.policySort = policySort;
	}

	public String getRelievIngareaCode() {
		return relievIngareaCode;
	}

	public void setRelievIngareaCode(String relievIngareaCode) {
		this.relievIngareaCode = relievIngareaCode;
	}

	public String getBIStartDate() {
		return BIStartDate;
	}

	public void setBIStartDate(String bIStartDate) {
		BIStartDate = bIStartDate;
	}

	public String getBIEndDate() {
		return BIEndDate;
	}

	public void setBIEndDate(String bIEndDate) {
		BIEndDate = bIEndDate;
	}

	public String getBIStartHour() {
		return BIStartHour;
	}

	public void setBIStartHour(String bIStartHour) {
		BIStartHour = bIStartHour;
	}

	public String getBIEndHour() {
		return BIEndHour;
	}

	public void setBIEndHour(String bIEndHour) {
		BIEndHour = bIEndHour;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getRenewalFlag() {
		return renewalFlag;
	}

	public void setRenewalFlag(String renewalFlag) {
		this.renewalFlag = renewalFlag;
	}

	public String getRenewalCIFlag() {
		return renewalCIFlag;
	}

	public void setRenewalCIFlag(String renewalCIFlag) {
		this.renewalCIFlag = renewalCIFlag;
	}

	public String getRenewalCIPolicyNo() {
		return renewalCIPolicyNo;
	}

	public void setRenewalCIPolicyNo(String renewalCIPolicyNo) {
		this.renewalCIPolicyNo = renewalCIPolicyNo;
	}

	public String getRenewalBIPolicyNo() {
		return renewalBIPolicyNo;
	}

	public void setRenewalBIPolicyNo(String renewalBIPolicyNo) {
		this.renewalBIPolicyNo = renewalBIPolicyNo;
	}

	public String getCINoDamageYears() {
		return CINoDamageYears;
	}

	public void setCINoDamageYears(String cINoDamageYears) {
		CINoDamageYears = cINoDamageYears;
	}

	public String getBINoDamageYears() {
		return BINoDamageYears;
	}

	public void setBINoDamageYears(String bINoDamageYears) {
		BINoDamageYears = bINoDamageYears;
	}

	public String getCIStartDate() {
		return CIStartDate;
	}

	public void setCIStartDate(String cIStartDate) {
		CIStartDate = cIStartDate;
	}

	public String getCIEndDate() {
		return CIEndDate;
	}

	public void setCIEndDate(String CIEndDate) {
		this.CIEndDate = CIEndDate;
	}

	public String getCIStartHour() {
		return CIStartHour;
	}

	public void setCIStartHour(String cIStartHour) {
		CIStartHour = cIStartHour;
	}

	public String getCiendHour() {
		return ciendHour;
	}

	public void setCiendHour(String ciendHour) {
		this.ciendHour = ciendHour;
	}

	public String getCIPolicyNo() {
		return CIPolicyNo;
	}

	public void setCIPolicyNo(String cIPolicyNo) {
		CIPolicyNo = cIPolicyNo;
	}

	public String getCICompanyCode() {
		return CICompanyCode;
	}

	public void setCICompanyCode(String cICompanyCode) {
		CICompanyCode = cICompanyCode;
	}

	public String getCiunProposalReason() {
		return ciunProposalReason;
	}

	public void setCiunProposalReason(String ciunProposalReason) {
		this.ciunProposalReason = ciunProposalReason;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgreeMentno() {
		return agreeMentno;
	}

	public void setAgreeMentno(String agreeMentno) {
		this.agreeMentno = agreeMentno;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getHandlercode() {
		return handlercode;
	}

	public void setHandlercode(String handlercode) {
		this.handlercode = handlercode;
	}

	public String getBussinessNature() {
		return bussinessNature;
	}

	public void setBussinessNature(String bussinessNature) {
		this.bussinessNature = bussinessNature;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public String getProspectNo() {
		return prospectNo;
	}

	public void setProspectNo(String prospectNo) {
		this.prospectNo = prospectNo;
	}

	public String getBusinessRemark2() {
		return businessRemark2;
	}

	public void setBusinessRemark2(String businessRemark2) {
		this.businessRemark2 = businessRemark2;
	}

	public String getCIRiskCode() {
		return CIRiskCode;
	}

	public void setCIRiskCode(String cIRiskCode) {
		CIRiskCode = cIRiskCode;
	}

	public String getBIRiskcode() {
		return BIRiskcode;
	}

	public void setBIRiskcode(String bIRiskcode) {
		BIRiskcode = bIRiskcode;
	}

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public String getImmEvalIdFlagCI() {
		return immEvalIdFlagCI;
	}

	public void setImmEvalIdFlagCI(String immEvalIdFlagCI) {
		this.immEvalIdFlagCI = immEvalIdFlagCI;
	}

	public String getImmEvalIdFlagBI() {
		return immEvalIdFlagBI;
	}

	public void setImmEvalIdFlagBI(String immEvalIdFlagBI) {
		this.immEvalIdFlagBI = immEvalIdFlagBI;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getBusinessMarks() {
		return businessMarks;
	}

	public void setBusinessMarks(String businessMarks) {
		this.businessMarks = businessMarks;
	}

	public String getExceptiontags() {
		return exceptiontags;
	}

	public void setExceptiontags(String exceptiontags) {
		this.exceptiontags = exceptiontags;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getCrenewPolicyNoCI() {
		return crenewPolicyNoCI;
	}

	public void setCrenewPolicyNoCI(String crenewPolicyNoCI) {
		this.crenewPolicyNoCI = crenewPolicyNoCI;
	}

	public String getCrenewPolicyNoBI() {
		return crenewPolicyNoBI;
	}

	public void setCrenewPolicyNoBI(String crenewPolicyNoBI) {
		this.crenewPolicyNoBI = crenewPolicyNoBI;
	}

	public String getRelationPolicyNoCI() {
		return relationPolicyNoCI;
	}

	public void setRelationPolicyNoCI(String relationPolicyNoCI) {
		this.relationPolicyNoCI = relationPolicyNoCI;
	}

	public String getRelationPolicyNoBI() {
		return relationPolicyNoBI;
	}

	public void setRelationPolicyNoBI(String relationPolicyNoBI) {
		this.relationPolicyNoBI = relationPolicyNoBI;
	}

	public String getTaxComCode() {
		return taxComCode;
	}

	public void setTaxComCode(String taxComCode) {
		this.taxComCode = taxComCode;
	}

	public String getPaidFreeCertifiCate() {
		return paidFreeCertifiCate;
	}

	public void setPaidFreeCertifiCate(String paidFreeCertifiCate) {
		this.paidFreeCertifiCate = paidFreeCertifiCate;
	}

	public String getNoclaimadJustLevel() {
		return noclaimadJustLevel;
	}

	public void setNoclaimadJustLevel(String noclaimadJustLevel) {
		this.noclaimadJustLevel = noclaimadJustLevel;
	}

	public String getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(String businessModel) {
		this.businessModel = businessModel;
	}

	public List<CarModelDataDTO> getCarModelDataList() {
		return carModelDataList;
	}

	public void setCarModelDataList(List<CarModelDataDTO> carModelDataList) {
		this.carModelDataList = carModelDataList;
	}

	public InsuredDataDTO getInsuredDataDTO() {
		return insuredDataDTO;
	}

	public void setInsuredDataDTO(InsuredDataDTO insuredDataDTO) {
		this.insuredDataDTO = insuredDataDTO;
	}

	public List<PrptNewEngageDataDTO> getPrptNewEngageDataList() {
		return prptNewEngageDataList;
	}

	public void setPrptNewEngageDataList(
			List<PrptNewEngageDataDTO> prptNewEngageDataList) {
		this.prptNewEngageDataList = prptNewEngageDataList;
	}
}
