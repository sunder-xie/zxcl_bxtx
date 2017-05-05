package com.zxcl.webapp.dto.port.comdto.quote;

import java.util.List;

import com.zxcl.webapp.dto.DriverDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.dto.port.comdto.AttestationDTO;

/**
 * 报价    发送
 * @author zx
 */
public class QuoteDTO {

	/**
	 * 认证
	 */
	private AttestationDTO attestationBase;
	
	/**
	 * 锦泰：是否同时交商业车船险（1：是，0：否）
	 */
	private String appTciVci;
	
	/**
	 * 锦泰：是否交车船险（1：是，0：否）
	 */
	private String appTci;
	
	/**
	 * 锦泰：是否交商业险（1：是，0：否）
	 */
	private String appVci;
	
	/**
	 * 锦泰：协议编码
	 * 太平：子协议代码（SolutionCode）
	 */
	private String ptlCode;
	
	/**
	 * 锦泰：城市名称
	 */
	private String cityName;
	
	/**
	 * 太平：续保单号
	 */
	private String renewalNo;
	
	/**
	 * 太平：承保套餐代码
	 */
	private String packageCode;
	
	/**
	 * 锦泰：报价单号
	 */
	private String qtnID;
	
	/**
	 * 车辆信息
	 */
	private CarDataDTO carData;
	
	/**
	 * 车主信息
	 */
	private OwnerDTO owner;
	
	/**
	 * 驾驶员信息
	 */
	private List<DriverDataDTO> driverData;
	
	/**
	 * 投保单信息（商业）
	 */
	private ApplicationVCIDTO applicationVCI;
	
	/**
	 * 投保单信息（交强）
	 */
	private ApplicationTCIDTO applicationTCI;
	
	/**
	 * 险种信息（集合）
	 */
	private List<CoverageItemDTO> coverageItemList;

	/**
	 * 阳光：页码大小
	 */
	private String pagenum;
	
	/**
	 * 阳光：页码
	 */
	private String page;
	
	/**
	 * 阳光：系统标识符
	 */
	private String operateSite;
	
	/**
	 * 阳光：（值都为一）
	 */
	private String newFlag;
	
	/**
	 * 阳光：监管保费（交强）
	 */
	private String superVisePremiumCI;
	
	/**
	 * 阳光：监管保费（商业）
	 */
	private String superVisePremiumBI;
	
	/**
	 * 阳光：实际自主核保因子（交强）
	 */
	private String actualUndWrtFactorsCI;
	
	/**
	 * 阳光：实际自主核保因子（商业）
	 */
	private String actualUndWrtFactorsBI;
	
	/**
	 * 阳光：协议申报因子(商业)
	 */
	private String agreeMentDeclareBI;
	
	/**
	 * 阳光：协议申报因子(交强)
	 */
	private String agreeMentDeclareCI;
	
	/**
	 * 阳光：是否车队
	 */
	private String isMotorCadeFlag;
	
	/**
	 * 阳光：上年终保日期（交强）
	 */
	private String lastYearEndDateCI;
	
	/**
	 * 阳光：上年终保日期（商业）
	 */
	private String lastYearEndDateBI;
	
	/**
	 * 阳光：新车报备信息
	 */
	private String recordInfo;
	
	/**
	 * 阳光：商业险险种
	 */
	private String riskcodebi;
	
	/**
	 * 阳光：调整保费（交强）
	 */
	private String adjustPremiumOfMainCI;
	
	/**
	 * 阳光：调整保费（商业）
	 */
	private String adjustPremiumOfMainBI;
	
	/**
	 * 阳光：交强险比例
	 */
	private String adjustRateOfMainCI;

	/**
	 * 阳光：商业险比例
	 */
	private String adjustRateOfMainBI;
	
	/**
	 * 阳光：是否承保交强险
	 */
	private String flagCI;
	
	/**
	 * 阳光：关联保单费
	 */
	private String sumSubprem;
	
	/**
	 * 阳光：关联保单利率
	 */
	private String profitMargin;
	
	/**
	 * 阳光：关联单风险保费
	 */
	private String riskPremium;
	
	/**
	 * 阳光：本地地址
	 */
	private String computerIP;
	
	/**
	 * 阳光：车型查询码
	 */
	private String searchSequenceno;
	
	/**
	 * 阳光：被重复投保车车架号
	 */
	private String repeatFrameno;
	
	/**
	 * 车船税信息
	 */
	private CartaxDataDTO cartaxData;
	
	/**
	 * 阳光：新增装备（集合）
	 */
	private List<DeviceDataDTO> deviceData;

	/**
	 * 太平：险种起期
	 * 华泰：险种起期
	 */
	private String startDate;
	
	/**
	 * 太平：城市编码
	 */
	private String cityCode;
	
	/**
	 * 阳光：专修费率（Q3险）
	 */
	private String speciaListRate;
	
	/**
	 * 交强险信息（交强险与商业险分开投保用的）
	 */
	private CoverageItemDTO coverageItemBI;
	
	/**
	 * 阳光：总的不计不计免赔标志，如果有一个险为不计免赔，值为1，否则为0
	 */
	private String totalNoDduct;
	
	/**
	 * 阳光：投保方式代码/玻璃险类别
	 */
	private String glasssPecies;
	
	/**
	 * 错误编码
	 */
	private String errorCode;
	
	/**
	 * 错误信息
	 */
	private String errorMassage;
	
	/**
	 * 富德：业务号
	 */
	private String businessNo;
	
	/**
	 * 富德：驾驶员信息
	 */
	private List<DriverDTO> drivers;
	
	/**
	 * 富德：询价单
	 */
	private InquiryDTO inquiry;
	
	/**
	 * 富德：车辆实际价值
	 */
	private String realPrice;
	
	/**
	 * 锦泰：代理出单账号，必传 30
	 */
	private String appDotEmpCode;
	
	/**
	 * 华泰：险种代码
	 */
	private String riskCode;
	/**
	 * 华泰：投保日期格式：YYYY-MM-DD
	 */
	private String operateDate;
	/**
	 * 华泰：录入日期格式：YYYY-MM-DD HH24:mm:ss
	 */
	private String inputDate;
	/**
	 * 华泰：终保日期格式：YYYY-MM-DD HH24:mm:ss
	 */
	private String endDate;
	
	/**
	 * 华泰：总折扣率
	 */
	private String discount;

	/**
	 * 华泰：总保险费
	 */
	private String sumPremium;

	/**
	 * 华泰：车船税合计
	 */
	private String sumPaytax;

	/**
	 * 华泰:投保查询码 商业险
	 */
	private String  platFormExchangeMessage;

	/**
	 * 华泰：商业险优惠信息
	 * 直接存放xml报文
	 */
	private String sy_profitdetailListXml;
	/**
	 * 华泰：交强险优惠信息
	 * 直接存放xml报文
	 */
	private String jq_profitdetailListXml;

	/**
	 * 华泰：车辆信息
	 * 直接存放xml报文
	 */
	private String itemCarXml;

	/**
	 * 华泰：投保关系人信息
	 * 直接存放xml报文
	 */
	private String insuredListXml;
	/**
	 * 华泰:商业险别
	 * 直接存放xml报文
	 */
	private String sy_itemKindListXml;
	/**
	 * 华泰：交强险别
	 * 直接存放xml报文
	 */
	private String jq_itemKindListXml;
	
	/**
	 * 锦泰：业务员代码
	 */
	private String salesCode;
	
	public String getSy_profitdetailListXml() {
		return sy_profitdetailListXml;
	}

	public void setSy_profitdetailListXml(String sy_profitdetailListXml) {
		this.sy_profitdetailListXml = sy_profitdetailListXml;
	}

	public String getJq_profitdetailListXml() {
		return jq_profitdetailListXml;
	}

	public void setJq_profitdetailListXml(String jq_profitdetailListXml) {
		this.jq_profitdetailListXml = jq_profitdetailListXml;
	}

	public String getItemCarXml() {
		return itemCarXml;
	}

	public void setItemCarXml(String itemCarXml) {
		this.itemCarXml = itemCarXml;
	}

	public String getInsuredListXml() {
		return insuredListXml;
	}

	public void setInsuredListXml(String insuredListXml) {
		this.insuredListXml = insuredListXml;
	}

	public String getSy_itemKindListXml() {
		return sy_itemKindListXml;
	}

	public void setSy_itemKindListXml(String sy_itemKindListXml) {
		this.sy_itemKindListXml = sy_itemKindListXml;
	}

	public String getJq_itemKindListXml() {
		return jq_itemKindListXml;
	}

	public void setJq_itemKindListXml(String jq_itemKindListXml) {
		this.jq_itemKindListXml = jq_itemKindListXml;
	}

	public String getPlatFormExchangeMessage() {
		return platFormExchangeMessage;
	}

	public void setPlatFormExchangeMessage(String platFormExchangeMessage) {
		this.platFormExchangeMessage = platFormExchangeMessage;
	}

	public QuoteDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuoteDTO(String errorCode, String errorMassage) {
		super();
		this.errorCode = errorCode;
		this.errorMassage = errorMassage;
	}

	public String getActualUndWrtFactorsBI() {
		return actualUndWrtFactorsBI;
	}

	public String getActualUndWrtFactorsCI() {
		return actualUndWrtFactorsCI;
	}
	
	public String getAdjustPremiumOfMainBI() {
		return adjustPremiumOfMainBI;
	}
	
	public String getAdjustPremiumOfMainCI() {
		return adjustPremiumOfMainCI;
	}
	
	
	public String getAdjustRateOfMainBI() {
		return adjustRateOfMainBI;
	}

	public String getAdjustRateOfMainCI() {
		return adjustRateOfMainCI;
	}

	public String getAgreeMentDeclareBI() {
		return agreeMentDeclareBI;
	}

	public String getAgreeMentDeclareCI() {
		return agreeMentDeclareCI;
	}

	public String getAppDotEmpCode() {
		return appDotEmpCode;
	}

	public ApplicationTCIDTO getApplicationTCI() {
		return applicationTCI;
	}

	public ApplicationVCIDTO getApplicationVCI() {
		return applicationVCI;
	}

	public String getAppTci() {
		return appTci;
	}

	public String getAppTciVci() {
		return appTciVci;
	}

	public String getAppVci() {
		return appVci;
	}

	public AttestationDTO getAttestationBase() {
		return attestationBase;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public CarDataDTO getCarData() {
		return carData;
	}

	public CartaxDataDTO getCartaxData() {
		return cartaxData;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public String getComputerIP() {
		return computerIP;
	}

	public CoverageItemDTO getCoverageItemBI() {
		return coverageItemBI;
	}

	public List<CoverageItemDTO> getCoverageItemList() {
		return coverageItemList;
	}

	public List<DeviceDataDTO> getDeviceData() {
		return deviceData;
	}

	public String getDiscount() {
		return discount;
	}

	public List<DriverDataDTO> getDriverData() {
		return driverData;
	}

	public List<DriverDTO> getDrivers() {
		return drivers;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMassage() {
		return errorMassage;
	}

	public String getFlagCI() {
		return flagCI;
	}

	public String getGlasssPecies() {
		return glasssPecies;
	}

	public String getInputDate() {
		return inputDate;
	}

	public InquiryDTO getInquiry() {
		return inquiry;
	}

	public String getIsMotorCadeFlag() {
		return isMotorCadeFlag;
	}

	public String getLastYearEndDateBI() {
		return lastYearEndDateBI;
	}

	public String getLastYearEndDateCI() {
		return lastYearEndDateCI;
	}

	public String getNewFlag() {
		return newFlag;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public String getOperateSite() {
		return operateSite;
	}

	public OwnerDTO getOwner() {
		return owner;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public String getPage() {
		return page;
	}

	public String getPagenum() {
		return pagenum;
	}

	public String getProfitMargin() {
		return profitMargin;
	}

	public String getPtlCode() {
		return ptlCode;
	}

	public String getQtnID() {
		return qtnID;
	}

	public String getRealPrice() {
		return realPrice;
	}

	public String getRecordInfo() {
		return recordInfo;
	}

	public String getRenewalNo() {
		return renewalNo;
	}


	public String getRepeatFrameno() {
		return repeatFrameno;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public String getRiskcodebi() {
		return riskcodebi;
	}

	public String getRiskPremium() {
		return riskPremium;
	}

	public String getSearchSequenceno() {
		return searchSequenceno;
	}

	public String getSpeciaListRate() {
		return speciaListRate;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getSumPaytax() {
		return sumPaytax;
	}

	public String getSumPremium() {
		return sumPremium;
	}

	public String getSumSubprem() {
		return sumSubprem;
	}

	public String getSuperVisePremiumBI() {
		return superVisePremiumBI;
	}

	public String getSuperVisePremiumCI() {
		return superVisePremiumCI;
	}

	public String getTotalNoDduct() {
		return totalNoDduct;
	}

	public void setActualUndWrtFactorsBI(String actualUndWrtFactorsBI) {
		this.actualUndWrtFactorsBI = actualUndWrtFactorsBI;
	}

	public void setActualUndWrtFactorsCI(String actualUndWrtFactorsCI) {
		this.actualUndWrtFactorsCI = actualUndWrtFactorsCI;
	}

	public void setAdjustPremiumOfMainBI(String adjustPremiumOfMainBI) {
		this.adjustPremiumOfMainBI = adjustPremiumOfMainBI;
	}

	public void setAdjustPremiumOfMainCI(String adjustPremiumOfMainCI) {
		this.adjustPremiumOfMainCI = adjustPremiumOfMainCI;
	}

	public void setAdjustRateOfMainBI(String adjustRateOfMainBI) {
		this.adjustRateOfMainBI = adjustRateOfMainBI;
	}

	public void setAdjustRateOfMainCI(String adjustRateOfMainCI) {
		this.adjustRateOfMainCI = adjustRateOfMainCI;
	}

	public void setAgreeMentDeclareBI(String agreeMentDeclareBI) {
		this.agreeMentDeclareBI = agreeMentDeclareBI;
	}

	public void setAgreeMentDeclareCI(String agreeMentDeclareCI) {
		this.agreeMentDeclareCI = agreeMentDeclareCI;
	}

	public void setAppDotEmpCode(String appDotEmpCode) {
		this.appDotEmpCode = appDotEmpCode;
	}

	public void setApplicationTCI(ApplicationTCIDTO applicationTCI) {
		this.applicationTCI = applicationTCI;
	}

	public void setApplicationVCI(ApplicationVCIDTO applicationVCI) {
		this.applicationVCI = applicationVCI;
	}

	public void setAppTci(String appTci) {
		this.appTci = appTci;
	}

	public void setAppTciVci(String appTciVci) {
		this.appTciVci = appTciVci;
	}

	public void setAppVci(String appVci) {
		this.appVci = appVci;
	}

	public void setAttestationBase(AttestationDTO attestationBase) {
		this.attestationBase = attestationBase;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public void setCarData(CarDataDTO carData) {
		this.carData = carData;
	}

	public void setCartaxData(CartaxDataDTO cartaxData) {
		this.cartaxData = cartaxData;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setComputerIP(String computerIP) {
		this.computerIP = computerIP;
	}

	public void setCoverageItemBI(CoverageItemDTO coverageItemBI) {
		this.coverageItemBI = coverageItemBI;
	}

	public void setCoverageItemList(List<CoverageItemDTO> coverageItemList) {
		this.coverageItemList = coverageItemList;
	}

	public void setDeviceData(List<DeviceDataDTO> deviceData) {
		this.deviceData = deviceData;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setDriverData(List<DriverDataDTO> driverData) {
		this.driverData = driverData;
	}

	public void setDrivers(List<DriverDTO> drivers) {
		this.drivers = drivers;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMassage(String errorMassage) {
		this.errorMassage = errorMassage;
	}

	public void setFlagCI(String flagCI) {
		this.flagCI = flagCI;
	}

	public void setGlasssPecies(String glasssPecies) {
		this.glasssPecies = glasssPecies;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public void setInquiry(InquiryDTO inquiry) {
		this.inquiry = inquiry;
	}

	public void setIsMotorCadeFlag(String isMotorCadeFlag) {
		this.isMotorCadeFlag = isMotorCadeFlag;
	}

	public void setLastYearEndDateBI(String lastYearEndDateBI) {
		this.lastYearEndDateBI = lastYearEndDateBI;
	}

	public void setLastYearEndDateCI(String lastYearEndDateCI) {
		this.lastYearEndDateCI = lastYearEndDateCI;
	}
	
	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public void setOperateSite(String operateSite) {
		this.operateSite = operateSite;
	}

	public void setOwner(OwnerDTO owner) {
		this.owner = owner;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}

	public void setProfitMargin(String profitMargin) {
		this.profitMargin = profitMargin;
	}

	public void setPtlCode(String ptlCode) {
		this.ptlCode = ptlCode;
	}

	public void setQtnID(String qtnID) {
		this.qtnID = qtnID;
	}

	public void setRealPrice(String realPrice) {
		this.realPrice = realPrice;
	}

	public void setRecordInfo(String recordInfo) {
		this.recordInfo = recordInfo;
	}

	public void setRenewalNo(String renewalNo) {
		this.renewalNo = renewalNo;
	}

	public void setRepeatFrameno(String repeatFrameno) {
		this.repeatFrameno = repeatFrameno;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public void setRiskcodebi(String riskcodebi) {
		this.riskcodebi = riskcodebi;
	}

	public void setRiskPremium(String riskPremium) {
		this.riskPremium = riskPremium;
	}

	public void setSearchSequenceno(String searchSequenceno) {
		this.searchSequenceno = searchSequenceno;
	}

	public void setSpeciaListRate(String speciaListRate) {
		this.speciaListRate = speciaListRate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setSumPaytax(String sumPaytax) {
		this.sumPaytax = sumPaytax;
	}

	public void setSumPremium(String sumPremium) {
		this.sumPremium = sumPremium;
	}

	public void setSumSubprem(String sumSubprem) {
		this.sumSubprem = sumSubprem;
	}

	public void setSuperVisePremiumBI(String superVisePremiumBI) {
		this.superVisePremiumBI = superVisePremiumBI;
	}

	public void setSuperVisePremiumCI(String superVisePremiumCI) {
		this.superVisePremiumCI = superVisePremiumCI;
	}

	public void setTotalNoDduct(String totalNoDduct) {
		this.totalNoDduct = totalNoDduct;
	}

	public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}
}

