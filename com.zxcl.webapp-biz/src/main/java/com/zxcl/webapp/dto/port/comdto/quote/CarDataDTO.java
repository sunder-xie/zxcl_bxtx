package com.zxcl.webapp.dto.port.comdto.quote;

/**
 * 车辆信息 
 * @author zx
 *
 */
public class CarDataDTO {
	/**
	 * 锦泰：车型代码
	 */
	private String vehicleId;
	
	/**
	 * 锦泰：号牌号码
	 * 阳光：车辆号牌（LICENSENO）
	 * 太平：车牌号（licenseNo）
	 * 天平：号牌号码（VehicleLicenceCode）
	 */
	private String licensePlateNo;
	
	/**
	 * 锦泰：号牌种类代码
	 * 阳光：号牌种类代码（LICENSETYPE）
	 * 天平：号牌种类（LicenseTypeCode）
	 */
	private String licensePlateType;
	
	/**
	 * 锦泰：车辆种类代码
	 * 天平：车型代码（AutoModelCode）
	 */
	private String motorTypeCode;
	
	/**
	 * 锦泰：车辆使用性质代码
	 * 天平：使用性质（UsageAttributeCode）
	 */
	private String motorUsageTypeCode;
	
	/**
	 * 锦泰：车辆初始登记日期（格式：精确到天YYYYMMDD）
	 * 太平：登记日期（SingeinDate）
	 * 天平:初登日期（FirstRegisterDate，格式：精确到天）
	 */
	private String firstRegisterDate;
	
	/**
	 * 锦泰：车辆识别代号（车架号/VIN码）
	 * 太平：车架号（frameNo）
	 * 天平：车架号（VehicleFrameNo）
	 */
	private String vin;
	
	/**
	 * 锦泰：发动机号
	 * 太平：发动机号（engineNo）
	 * 天平：发动机号（EngineNo）
	 */
	private String engineNo;
	
	/**
	 * 锦泰：交管车辆种类
	 * 天平：车辆种类（VehicleType）
	 */
	private String vehicleType;
	
	/**
	 * 锦泰：转移登记日期（格式：精确到天YYYYMMDD）
	 * 太平：过户日期（transferDate）
	 */
	private String transferDate;
	
	/**
	 * 锦泰：未上牌车辆标志（0：上牌车辆,1：未上牌车辆）
	 */
	private String noLicenseFlag;
	
	/**
	 * 锦泰：新车标志（0：非新车，1：新车）
	 * 阳光：新旧生命标示别标示（LIFETABLEDXBOM）
	 */
	private String newVehicleFlag;
	
	/**
	 * 锦泰：过户车辆标志（1：过户重新投保，0：未过户投保）
	 * 太平：是否过户车（0:空，01投保过户，02批改过户）
	 */
	private String chgOwnerFlag;
	
	/**
	 * 锦泰：外地车标志（0：本地车，1：外地车）
	 * 天平：外地车标志（0：否，1：是，2：港澳车）
	 */
	private String ecdemicVehicleFlag;
	
	/**
	 * 锦泰：是否车贷投保多年标志（0：不是车贷投保多年，1：车贷投保多年）
	 * 天平：是否车贷投保多年标志（LoanVehicleFlag（0：不是车贷投保多年；1：车贷投保多年））
	 */
	private String loanVehicleFlag;
	
	/**
	 * 锦泰：车队标志（0：非车队车，1：车队车，默认值：0）
	 * 天平：车队标志（isMotorcade（0：非车队车；1：车队车））
	 */
	private String fleetFlag;
	
	/**
	 * 锦泰：车队号
	 */
	private String fleetNo;
	
	/**
	 * 锦泰：跨省首年投保未出险证明的年数
	 */
	private String noDamageYears;
	
	/**
	 * 锦泰：车架号异常标志代码（0：否，1：是）
	 */
	private String rankNoFlag;
	
	/**
	 * 锦泰：能源种类
	 * 阳光：燃料种类（FUELTYPE）
	 * 天平：能源类型（FuelType）
	 */
	private String fuelType;
	
	/**
	 * 太平：精友车型编码
	 * 天平：行业车型编码（modelCode）
	 * 
	 */
	private String modelCode;
	
	/**
	 * 阳光：行驶证车辆类型(车辆类型描述)
	 * 天平：交管车辆类型（VehicleStyle）
	 */
	private String licenseCategory;
	
	/**
	 * 锦泰：年平均年行驶里程(公里)（runMil）
	 * 阳光：年平均行驶里程
	 */
	private String runMiles;
	
	/**
	 * 锦泰：行驶区域代码（travelArea）
	 * 阳光：行驶区域代码
	 * 太平: 城市编码（cityCode）
	 */
	private String runAreaCode;
	
	/**
	 * 阳光：新车购置价（原始）
	 * 天平：新车购置价（PurchasePrice）
	 */
	private String purchasePrice;
	
	/**
	 * 阳光：行业车型编码
	 */
	private String purchasePriceModi;
	
	/**
	 * 阳光：产地种类
	 */
	private String contryCode;
	
	/**
	 * 阳光：是否节能车（是 1 否 0）
	 */
	private String isEnergyConserVation;
	
	/**
	 * 阳光：车辆与被保险人关系
	 */
	private String carInsureDrelation;
	
	/**
	 * 阳光：无赔款优待及上年赔款记录费率系数
	 */
	private String damagedFactorGrade;
	
	/**
	 * 阳光：验车性质
	 */
	private String carcheckStatus;
	
	/**
	 * 阳光：其它性质
	 */
	private String otherNature;
	
	/**
	 * 阳光：选择费率浮动
	 */
	private String noneFluctuateFlag;
	
	/**
	 * 阳光：车型单
	 */
	private String vehicleCode;
	
	/**
	 * 天平：所属性质
	 */
	private String ownershipAttributeCode;
	
	/**
	 * 天平：排气量
	 */
	private String exhaustCapability;
	
	/**
	 * 天平：整备质量
	 */
	private String weight;
	
	/**
	 * 天平：核定载质量
	 */
	private String vehicleTonnages;
	
	/**
	 * 天平：功率
	 */
	private String power;
	
	/**
	 * 天平：座位数
	 */
	private String seat;
	
	/**
	 * 天平：品牌
	 */
	private String vehicleBrand;
	
	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getLicensePlateNo() {
		return licensePlateNo;
	}

	public void setLicensePlateNo(String licensePlateNo) {
		this.licensePlateNo = licensePlateNo;
	}

	public String getLicensePlateType() {
		return licensePlateType;
	}

	public void setLicensePlateType(String licensePlateType) {
		this.licensePlateType = licensePlateType;
	}

	public String getMotorTypeCode() {
		return motorTypeCode;
	}

	public void setMotorTypeCode(String motorTypeCode) {
		this.motorTypeCode = motorTypeCode;
	}

	public String getMotorUsageTypeCode() {
		return motorUsageTypeCode;
	}

	public void setMotorUsageTypeCode(String motorUsageTypeCode) {
		this.motorUsageTypeCode = motorUsageTypeCode;
	}

	public String getFirstRegisterDate() {
		return firstRegisterDate;
	}

	public void setFirstRegisterDate(String firstRegisterDate) {
		this.firstRegisterDate = firstRegisterDate;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public String getNoLicenseFlag() {
		return noLicenseFlag;
	}

	public void setNoLicenseFlag(String noLicenseFlag) {
		this.noLicenseFlag = noLicenseFlag;
	}

	public String getNewVehicleFlag() {
		return newVehicleFlag;
	}

	public void setNewVehicleFlag(String newVehicleFlag) {
		this.newVehicleFlag = newVehicleFlag;
	}

	public String getChgOwnerFlag() {
		return chgOwnerFlag;
	}

	public void setChgOwnerFlag(String chgOwnerFlag) {
		this.chgOwnerFlag = chgOwnerFlag;
	}

	public String getEcdemicVehicleFlag() {
		return ecdemicVehicleFlag;
	}

	public void setEcdemicVehicleFlag(String ecdemicVehicleFlag) {
		this.ecdemicVehicleFlag = ecdemicVehicleFlag;
	}

	public String getLoanVehicleFlag() {
		return loanVehicleFlag;
	}

	public void setLoanVehicleFlag(String loanVehicleFlag) {
		this.loanVehicleFlag = loanVehicleFlag;
	}

	public String getFleetFlag() {
		return fleetFlag;
	}

	public void setFleetFlag(String fleetFlag) {
		this.fleetFlag = fleetFlag;
	}

	public String getFleetNo() {
		return fleetNo;
	}

	public void setFleetNo(String fleetNo) {
		this.fleetNo = fleetNo;
	}

	public String getNoDamageYears() {
		return noDamageYears;
	}

	public void setNoDamageYears(String noDamageYears) {
		this.noDamageYears = noDamageYears;
	}

	public String getRankNoFlag() {
		return rankNoFlag;
	}

	public void setRankNoFlag(String rankNoFlag) {
		this.rankNoFlag = rankNoFlag;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getLicenseCategory() {
		return licenseCategory;
	}

	public void setLicenseCategory(String licenseCategory) {
		this.licenseCategory = licenseCategory;
	}

	public String getRunMiles() {
		return runMiles;
	}

	public void setRunMiles(String runMiles) {
		this.runMiles = runMiles;
	}

	public String getRunAreaCode() {
		return runAreaCode;
	}

	public void setRunAreaCode(String runAreaCode) {
		this.runAreaCode = runAreaCode;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getPurchasePriceModi() {
		return purchasePriceModi;
	}

	public void setPurchasePriceModi(String purchasePriceModi) {
		this.purchasePriceModi = purchasePriceModi;
	}

	public String getContryCode() {
		return contryCode;
	}

	public void setContryCode(String contryCode) {
		this.contryCode = contryCode;
	}

	public String getIsEnergyConserVation() {
		return isEnergyConserVation;
	}

	public void setIsEnergyConserVation(String isEnergyConserVation) {
		this.isEnergyConserVation = isEnergyConserVation;
	}

	public String getCarInsureDrelation() {
		return carInsureDrelation;
	}

	public void setCarInsureDrelation(String carInsureDrelation) {
		this.carInsureDrelation = carInsureDrelation;
	}

	public String getDamagedFactorGrade() {
		return damagedFactorGrade;
	}

	public void setDamagedFactorGrade(String damagedFactorGrade) {
		this.damagedFactorGrade = damagedFactorGrade;
	}

	public String getCarcheckStatus() {
		return carcheckStatus;
	}

	public void setCarcheckStatus(String carcheckStatus) {
		this.carcheckStatus = carcheckStatus;
	}

	public String getOtherNature() {
		return otherNature;
	}

	public void setOtherNature(String otherNature) {
		this.otherNature = otherNature;
	}

	public String getNoneFluctuateFlag() {
		return noneFluctuateFlag;
	}

	public void setNoneFluctuateFlag(String noneFluctuateFlag) {
		this.noneFluctuateFlag = noneFluctuateFlag;
	}

	public String getVehicleCode() {
		return vehicleCode;
	}

	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}

	public String getOwnershipAttributeCode() {
		return ownershipAttributeCode;
	}

	public void setOwnershipAttributeCode(String ownershipAttributeCode) {
		this.ownershipAttributeCode = ownershipAttributeCode;
	}

	public String getExhaustCapability() {
		return exhaustCapability;
	}

	public void setExhaustCapability(String exhaustCapability) {
		this.exhaustCapability = exhaustCapability;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getVehicleTonnages() {
		return vehicleTonnages;
	}

	public void setVehicleTonnages(String vehicleTonnages) {
		this.vehicleTonnages = vehicleTonnages;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}
}

