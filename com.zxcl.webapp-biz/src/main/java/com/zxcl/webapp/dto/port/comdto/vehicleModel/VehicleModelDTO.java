package com.zxcl.webapp.dto.port.comdto.vehicleModel;

import com.zxcl.webapp.dto.port.comdto.AttestationDTO;

/**
 * 车型  发送
 * @author zx
 *
 */
public class VehicleModelDTO {
	
	/**
	 * 认证
	 */
	private AttestationDTO attestationBase;
	
	/**
	 * 锦泰：车型名称
	 * 阳光：厂牌型号（BRANDNAME）
	 * 太平：品牌车型
	 * 天平：车型名称（VehicleName）
	 * 华泰：车型名称（BRANDNAME）
	 */
	private String vehicleName;
	
	/**
	 * 阳光：企业代码
	 */
	private String extenterpCode;
	
	/**
	 * 阳光：企业分支机构代码
	 */
	private String extbranchCode;
	
	/**
	 * 阳光：企业交易流水号
	 */
	private String extsequenCeno;
	
	/**
	 * 阳光：企业险种代码
	 */
	private String extriskCode;
	
	/**
	 * 阳光：企业交易发起日期
	 */
	private String transDate;
	
	/**
	 * 阳光：企业交易发起时间
	 */
	private String transtime;
	
	/**
	 * 阳光：行业企业操作员代码
	 */
	private String extoperatorCode;
	
	/**
	 * 阳光：页码大小
	 */
	private String pageNum;
	
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
	 * 阳光：车型代码
	 * 富德：车型代码
	 * 天平：车型代码
	 */
	private String modelCode;
	
	/**
	 * 阳光：品牌
	 */
	private String carBrand;
	
	/**
	 * 阳光：车系
	 */
	private String familyId;
	
	/**
	 * 阳光：别名
	 */
	private String aliasName;
	
	/**
	 * 阳光：上市年份
	 */
	private String carYear;
	
	/**
	 * 阳光：车辆种类
	 */
	private String carKind;
	
	/**
	 * 阳光：A/B类（对应计算保费、生成投保单的COUNTRYNATURE（国别性质(A. 进口/B. 国产)））
	 */
	private String carStyle;
	
	/**
	 * 阳光：核定载客
	 * 富德：核定载客
	 * 华泰：核定载客
	 */
	private String seatCount;
	
	/**
	 * 阳光：核定载质量
	 */
	private String tonCount;
	
	/**
	 * 阳光：新车购置价
	 * 富德：新车购置价
	 */
	private String purchasePrice;
	
	/**
	 * 阳光：预料字段
	 */
	private String bizFlag;
	
	/**
	 * 阳光：归属机构
	 */
	private String comCode;
	
	/**
	 * 阳光：险种代码
	 */
	private String riskCode;
	
	/**
	 * 阳光：号牌号码
	 * 华泰：号牌号码（LICENSENO）
	 */
	private String licenseno;
	
	/**
	 * 阳光：号牌种类代码
	 */
	private String licenseType;
	
	/**
	 * 阳光：发动机号
	 * 华泰：发动机号（ENGINENO）
	 */
	private String engineno;
	
	/**
	 * 阳光：车架号
	 * 华泰：车架号（FRAMENO）
	 */
	private String frameno;
	
	/**
	 * 阳光：外地车标志
	 */
	private String ecdemicvehicle;
	
	/**
	 * 阳光：车辆型号
	 * 富德：车辆型号
	 */
	private String modelName;
	
	/**
	 * 阳光：行驶证整备质量
	 */
	private String completeKerbMass;
	
	/**
	 * 阳光：初次登记日期
	 */
	private String enrollDate;
	
	/**
	 * 阳光：车辆类型描述
	 */
	private String vehicleStyleDesc;
	
	/**
	 * 阳光：排量
	 */
	private String exhaustScale;
	
	/**
	 * 阳光：出厂日期
	 */
	private String makeDate;
	
	/**
	 * 阳光：制造厂名称
	 */
	private String madeFactory;
	
	/**
	 * 阳光：渠道类型
	 */
	private String channelType;
	
	/**
	 * 阳光：使用性质
	 */
	private String usenatureCode;
	
	/**
	 * 阳光：车辆大类
	 */
	private String mainCarKindCode;
	
	/**
	 * 阳光：新车标志（“0”非新车，“1”新车，“9”不计）
	 */
	private String newcarFlag;
	
	/**
	 * 富德：业务号
	 */
	private String businessNo;
	/**
	 * 富德：国产车 /进口车 等
	 */
	private String vehicleTypeDesc;
	
	/**
	 * 富德：品牌
	 */
	private String vehicleBrand;
	
	/**
	 * 富德：制造厂
	 */
	private String manufacturer;
	
	/**
	 * 富德：车系
	 */
	private String vehicleSeries;
	
	/**
	 * 天平：使用性质
	 */
	private String usageAttributeCode;
	
	
	
	public String getVehicleTypeDesc() {
		return vehicleTypeDesc;
	}

	public void setVehicleTypeDesc(String vehicleTypeDesc) {
		this.vehicleTypeDesc = vehicleTypeDesc;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getVehicleSeries() {
		return vehicleSeries;
	}

	public void setVehicleSeries(String vehicleSeries) {
		this.vehicleSeries = vehicleSeries;
	}

	public AttestationDTO getAttestationBase() {
		return attestationBase;
	}

	public void setAttestationBase(AttestationDTO attestationBase) {
		this.attestationBase = attestationBase;
	}

	public String getVehicleName() {
		return vehicleName;
	}
	
	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getExtenterpCode() {
		return extenterpCode;
	}

	public void setExtenterpCode(String extenterpCode) {
		this.extenterpCode = extenterpCode;
	}

	public String getExtbranchCode() {
		return extbranchCode;
	}

	public void setExtbranchCode(String extbranchCode) {
		this.extbranchCode = extbranchCode;
	}

	public String getExtsequenCeno() {
		return extsequenCeno;
	}

	public void setExtsequenCeno(String extsequenCeno) {
		this.extsequenCeno = extsequenCeno;
	}

	public String getExtriskCode() {
		return extriskCode;
	}

	public void setExtriskCode(String extriskCode) {
		this.extriskCode = extriskCode;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTranstime() {
		return transtime;
	}

	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}

	public String getExtoperatorCode() {
		return extoperatorCode;
	}

	public void setExtoperatorCode(String extoperatorCode) {
		this.extoperatorCode = extoperatorCode;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
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

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getCarYear() {
		return carYear;
	}

	public void setCarYear(String carYear) {
		this.carYear = carYear;
	}

	public String getCarKind() {
		return carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	public String getCarStyle() {
		return carStyle;
	}

	public void setCarStyle(String carStyle) {
		this.carStyle = carStyle;
	}

	public String getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(String seatCount) {
		this.seatCount = seatCount;
	}

	public String getTonCount() {
		return tonCount;
	}

	public void setTonCount(String tonCount) {
		this.tonCount = tonCount;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getBizFlag() {
		return bizFlag;
	}

	public void setBizFlag(String bizFlag) {
		this.bizFlag = bizFlag;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getEngineno() {
		return engineno;
	}

	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}

	public String getFrameno() {
		return frameno;
	}

	public void setFrameno(String frameno) {
		this.frameno = frameno;
	}

	public String getEcdemicvehicle() {
		return ecdemicvehicle;
	}

	public void setEcdemicvehicle(String ecdemicvehicle) {
		this.ecdemicvehicle = ecdemicvehicle;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getCompleteKerbMass() {
		return completeKerbMass;
	}

	public void setCompleteKerbMass(String completeKerbMass) {
		this.completeKerbMass = completeKerbMass;
	}

	public String getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getVehicleStyleDesc() {
		return vehicleStyleDesc;
	}

	public void setVehicleStyleDesc(String vehicleStyleDesc) {
		this.vehicleStyleDesc = vehicleStyleDesc;
	}

	public String getExhaustScale() {
		return exhaustScale;
	}

	public void setExhaustScale(String exhaustScale) {
		this.exhaustScale = exhaustScale;
	}

	public String getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}

	public String getMadeFactory() {
		return madeFactory;
	}

	public void setMadeFactory(String madeFactory) {
		this.madeFactory = madeFactory;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getUsenatureCode() {
		return usenatureCode;
	}

	public void setUsenatureCode(String usenatureCode) {
		this.usenatureCode = usenatureCode;
	}

	public String getMainCarKindCode() {
		return mainCarKindCode;
	}

	public void setMainCarKindCode(String mainCarKindCode) {
		this.mainCarKindCode = mainCarKindCode;
	}

	public String getNewcarFlag() {
		return newcarFlag;
	}

	public void setNewcarFlag(String newcarFlag) {
		this.newcarFlag = newcarFlag;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public VehicleModelDTO(String vehicleName) {
		super();
		this.vehicleName = vehicleName;
	}
	public VehicleModelDTO(String vehicleName,AttestationDTO attestationBase) {
		super();
		this.vehicleName = vehicleName;
		this.attestationBase = attestationBase;
	}
	public VehicleModelDTO() {
		super();
	}

	public String getUsageAttributeCode() {
		return usageAttributeCode;
	}

	public void setUsageAttributeCode(String usageAttributeCode) {
		this.usageAttributeCode = usageAttributeCode;
	}
}

