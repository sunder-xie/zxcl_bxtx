package com.zxcl.webapp.dto.port.comdto.quote;

/**
 * 车船税信息     发送
 * @author zx
 *
 */
public class CartaxDataDTO {
	
	/**
	 * 富德：计税分类
	 *  01	不代收税
		02	代收税
		03	已完税
		04	减税
		05	免税
		06	纳税并补税
	 */
	private String taxRelifFlag = "02";
	
	/**
	 * 富德：能源种类
	 *  0	燃油
		1	纯电动
		2	燃料电池
		3	插电式混合动力
		4	其他混合动力
	 */
	private String fuelType = "0";
	
	/**
	 * 富德：减免税原因
	 *  0	无
		1	具备减免税证明
		2	拖拉机
		3	军队、武警专用车辆
		4	警车
		5	外国使领馆、国际组织及其人员车辆
		6	其他
		7	能源减免
		8	临时入境减免
	 */
	private String relifReason;
	
	/**
	 * 锦泰：免税方案
	 * 阳光：减免税方案（BASETAXATION，纳税类型减免必传，1减免金额，2减免比例）
	 * 富德：减免税方案
	 *  A	金额减免
		E	免税
		P	比例减免
	 */
	private String deductionDueType;
	
	/**
	 * 富德：税务机关代码或名称
	 */
	private String taxCompancyCode;
	
	/**
	 * 富德：减免税比例
	 */
	private String freeRate;
	
	/**
	 * 富德：税务机关名称
	 */
	private String taxCompancyName;
	
	/**
	 * 富德：完税减免税凭证号
	 */
	private String paidFreeCertificate;
	
	/**
	 * 富德：税目明细代码
	 *  01	大型载客汽车
		02	中型载客汽车
		03	小型载客汽车
		04	微型汽车
	 */
	private String taxStandard = "03";
	
	/**
	 * 富德：计算方式代码
	 *  1	按保险期间起期
		2	按签单日期
		3	按投保日期
		4	按车辆购置日期
	 */
	private String calculateMode = "1";
	
	/**
	 * 富德：车船税本地计算标志：0:本地计算，1：平台计算
	 */
	private String calculateFlag = "1";
	
	
	
	
	/**
	 * 阳光：车船税纳税标志
	 */
	private String taxrelifFlag;
	
	/**
	 * 阳光：纳税人名称
	 */
	private String taxpayerName;
	
	/**
	 * 阳光：纳税人证件类型
	 */
	private String identifyType;
	
	/**
	 * 阳光：纳税人证件号码
	 * 天平：纳税人识别号
	 */
	private String identifyNumber;
	
	/**
	 * 阳光：购车发票日期（新车必传）
	 */
	private String buyCarDate;
	
	/**
	 * 锦泰：完税凭证号（完税时必填）
	 * 阳光：完税凭证号（减免税证明号）（PAIDFREECERTIFICATE，外地车已完税必传纳税类型为减免时必传）
	 */
	private String taxPaymentRecptNo;
	
	/**
	 * 阳光：开具税务机关名称（纳税类型为完税或者减免必传）
	 */
	private String taxComName;
	
	/**
	 * 阳光：减免说明（纳税类型为减免必传）
	 */
	private String freeRateText;
	
	/**
	 * 阳光：是否计算往年补交（0否，1是）
	 */
	private String hisFeeFlag;
	
	/**
	 * 阳光：是否滞纳金（0否，1是）
	 */
	private String lateFeeFlag;
	
	/**
	 * 阳光：前次缴费年度（计算往年补交是时必传）
	 */
	private String payLastYear;
	
	/**
	 * 阳光：前次保单止期（计算往年补交是时必传）
	 */
	private String hisPolicyEndDate;
	
	/**
	 * 阳光：补缴税改差额
	 */
	private String payBalanceFee;
	
	/**
	 * 阳光：纳税人识别号（除上海机构）
	 */
	private String taxPayerIdentifier;
	
	/**
	 * 阳光：滞纳金计算起始日期
	 */
	private String lateFeeStartDate;
	
	/**
	 * 锦泰：缴税起始年月（格式：精确到天yyyyMMdd）
	 * 阳光：税款所属起期（PAYSTARTDATE）
	 */
	private String taxEffBgnTm;
	
	/**
	 * 锦泰：缴税终止年月（格式：精确到天yyyyMMdd）
	 * 阳光：税款所属止期（PAYENDDATE）
	 */
	private String taxEffEndTm;
	
	/**
	 * 阳光：税款所属起期（PAYSTARTDATE）交强
	 * 天平：税款所属起期（startDate，精确到日）
	 */
	private String taxEffBgnTmBI;
	
	/**
	 * 阳光：税款所属止期（PAYENDDATE）交强
	 * 天平：税款所属止期（endDate，精确到日）
	 */
	private String taxEffEndTmBI;
	
	/**
	 * 锦泰：减免税原因
	 * 阳光：减免税原因（RELIFREASON，纳税类型为减免必传）
	 */
	private String deductionDueCode;
	
	/**
	 * 锦泰：算税标示（默认值：一）
	 */
	private String calcTaxFlag;
	
	/**
	 * 锦泰：减免金额
	 * 阳光：减免税额（TAXRELIEF，纳税类型减免且减免方案按金额减免必传）
	 */
	private String deduction;
	
	/**
	 * 锦泰：减免比例
	 * 阳光：减免比例（FREERATE，纳税类型为减免且减免方案按比例减免必传）
	 */
	private String deductionDueProportion;
	
	/**
	 * 锦泰：开具税务机关
	 * 阳光：开具税务机关代码（TAXCOMCODE，纳税类型为完税或者减免必传）
	 */
	private String taxDepartment;
	
	/**
	 * 锦泰：纳税类型代码
	 * 天平：纳税类型（TaxConditionCode）
	 */
	private String taxConditionCode;

	public String getTaxrelifFlag() {
		return taxrelifFlag;
	}

	public void setTaxrelifFlag(String taxrelifFlag) {
		this.taxrelifFlag = taxrelifFlag;
	}

	public String getTaxpayerName() {
		return taxpayerName;
	}

	public void setTaxpayerName(String taxpayerName) {
		this.taxpayerName = taxpayerName;
	}

	public String getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public String getBuyCarDate() {
		return buyCarDate;
	}

	public void setBuyCarDate(String buyCarDate) {
		this.buyCarDate = buyCarDate;
	}

	public String getTaxPaymentRecptNo() {
		return taxPaymentRecptNo;
	}

	public void setTaxPaymentRecptNo(String taxPaymentRecptNo) {
		this.taxPaymentRecptNo = taxPaymentRecptNo;
	}

	public String getTaxEffBgnTm() {
		return taxEffBgnTm;
	}

	public void setTaxEffBgnTm(String taxEffBgnTm) {
		this.taxEffBgnTm = taxEffBgnTm;
	}

	public String getTaxEffEndTm() {
		return taxEffEndTm;
	}

	public void setTaxEffEndTm(String taxEffEndTm) {
		this.taxEffEndTm = taxEffEndTm;
	}

	public String getDeductionDueCode() {
		return deductionDueCode;
	}

	public void setDeductionDueCode(String deductionDueCode) {
		this.deductionDueCode = deductionDueCode;
	}

	public String getDeductionDueType() {
		return deductionDueType;
	}

	public String getFreeRate() {
		return freeRate;
	}

	public void setFreeRate(String freeRate) {
		this.freeRate = freeRate;
	}

	public void setDeductionDueType(String deductionDueType) {
		this.deductionDueType = deductionDueType;
	}

	public String getCalcTaxFlag() {
		return calcTaxFlag;
	}

	public void setCalcTaxFlag(String calcTaxFlag) {
		this.calcTaxFlag = calcTaxFlag;
	}

	public String getTaxCompancyName() {
		return taxCompancyName;
	}

	public void setTaxCompancyName(String taxCompancyName) {
		this.taxCompancyName = taxCompancyName;
	}

	public String getDeduction() {
		return deduction;
	}

	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}

	public String getDeductionDueProportion() {
		return deductionDueProportion;
	}

	public void setDeductionDueProportion(String deductionDueProportion) {
		this.deductionDueProportion = deductionDueProportion;
	}

	public String getTaxDepartment() {
		return taxDepartment;
	}

	public void setTaxDepartment(String taxDepartment) {
		this.taxDepartment = taxDepartment;
	}

	public String getTaxComName() {
		return taxComName;
	}

	public void setTaxComName(String taxComName) {
		this.taxComName = taxComName;
	}

	public String getFreeRateText() {
		return freeRateText;
	}

	public void setFreeRateText(String freeRateText) {
		this.freeRateText = freeRateText;
	}

	public String getHisFeeFlag() {
		return hisFeeFlag;
	}

	public void setHisFeeFlag(String hisFeeFlag) {
		this.hisFeeFlag = hisFeeFlag;
	}

	public String getLateFeeFlag() {
		return lateFeeFlag;
	}

	public void setLateFeeFlag(String lateFeeFlag) {
		this.lateFeeFlag = lateFeeFlag;
	}

	public String getPayLastYear() {
		return payLastYear;
	}

	public void setPayLastYear(String payLastYear) {
		this.payLastYear = payLastYear;
	}

	public String getHisPolicyEndDate() {
		return hisPolicyEndDate;
	}

	public void setHisPolicyEndDate(String hisPolicyEndDate) {
		this.hisPolicyEndDate = hisPolicyEndDate;
	}

	public String getPayBalanceFee() {
		return payBalanceFee;
	}

	public void setPayBalanceFee(String payBalanceFee) {
		this.payBalanceFee = payBalanceFee;
	}

	public String getTaxPayerIdentifier() {
		return taxPayerIdentifier;
	}

	public void setTaxPayerIdentifier(String taxPayerIdentifier) {
		this.taxPayerIdentifier = taxPayerIdentifier;
	}

	public String getLateFeeStartDate() {
		return lateFeeStartDate;
	}

	public void setLateFeeStartDate(String lateFeeStartDate) {
		this.lateFeeStartDate = lateFeeStartDate;
	}

	public String getTaxConditionCode() {
		return taxConditionCode;
	}

	public void setTaxConditionCode(String taxConditionCode) {
		this.taxConditionCode = taxConditionCode;
	}

	public String getTaxEffBgnTmBI() {
		return taxEffBgnTmBI;
	}

	public void setTaxEffBgnTmBI(String taxEffBgnTmBI) {
		this.taxEffBgnTmBI = taxEffBgnTmBI;
	}

	public String getTaxEffEndTmBI() {
		return taxEffEndTmBI;
	}

	public void setTaxEffEndTmBI(String taxEffEndTmBI) {
		this.taxEffEndTmBI = taxEffEndTmBI;
	}

	public String getTaxRelifFlag() {
		return taxRelifFlag;
	}

	public void setTaxRelifFlag(String taxRelifFlag) {
		this.taxRelifFlag = taxRelifFlag;
	}

	public String getTaxStandard() {
		return taxStandard;
	}

	public void setTaxStandard(String taxStandard) {
		this.taxStandard = taxStandard;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getRelifReason() {
		return relifReason;
	}

	public void setRelifReason(String relifReason) {
		this.relifReason = relifReason;
	}

	public String getTaxCompancyCode() {
		return taxCompancyCode;
	}

	public void setTaxCompancyCode(String taxCompancyCode) {
		this.taxCompancyCode = taxCompancyCode;
	}

	public String getPaidFreeCertificate() {
		return paidFreeCertificate;
	}

	public void setPaidFreeCertificate(String paidFreeCertificate) {
		this.paidFreeCertificate = paidFreeCertificate;
	}

	public String getCalculateMode() {
		return calculateMode;
	}

	public void setCalculateMode(String calculateMode) {
		this.calculateMode = calculateMode;
	}

	public String getCalculateFlag() {
		return calculateFlag;
	}

	public void setCalculateFlag(String calculateFlag) {
		this.calculateFlag = calculateFlag;
	}
	
}