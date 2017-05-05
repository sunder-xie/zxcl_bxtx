package com.zxcl.webapp.dto.port.comdto.quote;

import java.util.List;

/**
 * 险种信息    发送
 * @author zx
 *
 */
public class CoverageItemDTO {
	
	/**
	 * 锦泰：险种代码
	 * 阳光：险种代码（RISKCODE）
	 * 太平：险种代码（riskCode）
	 */
	private String coverageCode;
	
	
	/**
	 * 阳光：险别代码
	 * 太平：险别代码（kindCode）
	 * 富德：险别代码 
	 * 天平：险别责任代码（CoverageCode）
	 * 华泰：险别代码
	 */
	private String kindCode;
	
	/**
	 * 阳光：险别名称
	 * 太平：险别名称（kindName）
	 * 华泰：险别名称
	 */
	private String kindName;
	
	/**
	 * 锦泰：保额/限额
	 * 阳光：保险金额/赔偿限额
	 * 太平：保额（sumInsured）
	 * 富德：保额
	 * 天平：保额（InsuredAmount）
	 */
	private String sumLimit;
	
	/**
	 * 锦泰：投保座位数
	 */
	private String seatNum;
	
	/**
	 * 阳光：数量
	 * 富德：数量
	 */
	private String quantity;
	
	/**
	 * 锦泰：是否投了不计免赔（0：否，1：是）
	 * 太平：是否投保不计免赔（relatedInd，1：是；0：否）
	 */
	private String noDduct;
	
	/**
	 * 锦泰：玻璃类型（投保玻璃破碎险，玻璃类型必传）
	 * 阳光：投保方式代码/玻璃险类别（MODECODE）
	 */
	private String glsType;
	
	/**
	 * 阳光：投保方式名称（玻璃）
	 */
	private String modeName;
	
	/**
	 * 阳光：单位保额（UNITAMOUNT）
	 * 太平：每人保额（unitInsured）
	 * 富德：代为保额(UnitInsured)
	 * 华泰：保险金额/赔偿限额
	 */
	private String amount;
	
	/**
	 * 阳光：免赔额
	 */
	private String deductible;
	
	/**
	 * 阳光：免赔率
	 * 富德：损失比例档次
	 */
	private String deductibleRate;
	
	/**
	 * 阳光：短期系数
	 */
	private String shortRate;
	
	/**
	 * 阳光：调整系数
	 * 富德：险别校验 1，0
	 */
	private String adjustRate;
	
	/**
	 * 阳光：标志位
	 */
	private String flag;
	
	/**
	 * 阳光：可选免赔金额
	 */
	private String value;
	
	/**
	 * 富德：价值方式
	 */
	private String valueType;
	
	/**
	 * 阳光：短期汇率（SIG-节点对应规则表中未找到）
	 */
	private String shortRateFlag;
	
	
	/**
	 * 华泰：费率
	 * 富德：费率
	 */
	private String rate;
	
	/**
	 * 天平：附加信息
	 */
	private String subsidiary;
	
	/**
	 * 天平：车身划痕损失免赔率
	 */
	private String deductionRate;
	
	/**
	 * 天平：指定专修厂特约条款费率
	 */
	private String ariExpense;
	
	/**
	 * 纯风险保费
	 */
	private List<PureRiskPremiumItemDTO> pureRiskPremiumItemList;

	/**
	 * 天平：比例
	 */
	private String premiumRate;
	
	/**
	 * 华泰：折扣率
	 */
	private String discount;
	/**
	 * 华泰：保费
	 */
	private String premium;
	/**
	 * 华泰:基础保费
	 */
	private String basePremium;
	/**
	 * 华泰：标准保费/折前保费
	 */
	private String  benchMarkPremium;
	
	/**
	 * 华泰：总保险费(交强险)
	 */
	private String sumPremium;

	/**
	 * 华泰：车船税合计
	 */
	private String sumPaytax;

	/**
	 * 华泰:投保查询码 
	 */
	private String  platFormExchangeMessage;
	
	/**
	 * 华泰：起保日期
	 */
	private String startDate;
	
	/**
	 * 华泰：终保日期
	 */
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAdjustRate() {
		return adjustRate;
	}

	public String getAmount() {
		return amount;
	}

	public String getAriExpense() {
		return ariExpense;
	}

	public String getBasePremium() {
		return basePremium;
	}

	public String getBenchMarkPremium() {
		return benchMarkPremium;
	}

	public String getCoverageCode() {
		return coverageCode;
	}
	public String getDeductible() {
		return deductible;
	}

	public String getDeductibleRate() {
		return deductibleRate;
	}

	public String getDeductionRate() {
		return deductionRate;
	}

	public String getDiscount() {
		return discount;
	}

	public String getFlag() {
		return flag;
	}

	public String getGlsType() {
		return glsType;
	}

	public String getKindCode() {
		return kindCode;
	}

	public String getKindName() {
		return kindName;
	}

	public String getModeName() {
		return modeName;
	}

	public String getNoDduct() {
		return noDduct;
	}

	public String getPlatFormExchangeMessage() {
		return platFormExchangeMessage;
	}

	public String getPremium() {
		return premium;
	}

	public String getPremiumRate() {
		return premiumRate;
	}

	public List<PureRiskPremiumItemDTO> getPureRiskPremiumItemList() {
		return pureRiskPremiumItemList;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getRate() {
		return rate;
	}

	public String getSeatNum() {
		return seatNum;
	}

	public String getShortRate() {
		return shortRate;
	}

	public String getShortRateFlag() {
		return shortRateFlag;
	}

	public String getSubsidiary() {
		return subsidiary;
	}

	public String getSumLimit() {
		return sumLimit;
	}

	public String getSumPaytax() {
		return sumPaytax;
	}

	public String getSumPremium() {
		return sumPremium;
	}

	public String getValue() {
		return value;
	}

	public String getValueType() {
		return valueType;
	}

	public void setAdjustRate(String adjustRate) {
		this.adjustRate = adjustRate;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setAriExpense(String ariExpense) {
		this.ariExpense = ariExpense;
	}

	public void setBasePremium(String basePremium) {
		this.basePremium = basePremium;
	}

	public void setBenchMarkPremium(String benchMarkPremium) {
		this.benchMarkPremium = benchMarkPremium;
	}

	public void setCoverageCode(String coverageCode) {
		this.coverageCode = coverageCode;
	}

	public void setDeductible(String deductible) {
		this.deductible = deductible;
	}

	public void setDeductibleRate(String deductibleRate) {
		this.deductibleRate = deductibleRate;
	}

	public void setDeductionRate(String deductionRate) {
		this.deductionRate = deductionRate;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setGlsType(String glsType) {
		this.glsType = glsType;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public void setNoDduct(String noDduct) {
		this.noDduct = noDduct;
	}

	public void setPlatFormExchangeMessage(String platFormExchangeMessage) {
		this.platFormExchangeMessage = platFormExchangeMessage;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public void setPremiumRate(String premiumRate) {
		this.premiumRate = premiumRate;
	}

	public void setPureRiskPremiumItemList(List<PureRiskPremiumItemDTO> pureRiskPremiumItemList) {
		this.pureRiskPremiumItemList = pureRiskPremiumItemList;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}

	public void setShortRate(String shortRate) {
		this.shortRate = shortRate;
	}

	public void setShortRateFlag(String shortRateFlag) {
		this.shortRateFlag = shortRateFlag;
	}

	public void setSubsidiary(String subsidiary) {
		this.subsidiary = subsidiary;
	}

	public void setSumLimit(String sumLimit) {
		this.sumLimit = sumLimit;
	}

	public void setSumPaytax(String sumPaytax) {
		this.sumPaytax = sumPaytax;
	}

	public void setSumPremium(String sumPremium) {
		this.sumPremium = sumPremium;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
}