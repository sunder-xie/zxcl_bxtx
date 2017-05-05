package com.zxcl.webapp.dto.port.comdto.seaMeal;

/**
 * 汽车模型数据
 * @author zx
 *
 */
public class CarModelDataDTO {
	
	/**
	 * 阳光：VIN码
	 */
	private String vinNo;
	
	/**
	 * 阳光：平均行驶里程
	 */
	private String runMiles;
	
	/**
	 * 阳光：上年终保日期（交强）
	 */
	private String lastYearEndDateCI;
	
	/**
	 * 阳光：上年终保日期（商业）
	 */
	private String lastYearEndDateBI;
	
	/**
	 * 阳光：车型查询码（有车险查询平台地区必传）
	 */
	private String searchSequenceNo;	
	
	/**
	 * 阳光：车牌号码（有牌车必传）
	 */
	private String licenseNo;	
	
	/**
	 * 阳光：车辆种类代码
	 */
	private String carKindCode;
	
	/**
	 * 阳光：号牌种类代码
	 */
	private String licenseType;
	
	/**
	 * 阳光：车架号
	 */
	private String frameNo;
	
	/**
	 * 阳光：发动机号
	 */
	private String enginNo;
	
	/**
	 * 阳光：行驶证车主
	 */
	private String carOwner;
	
	/**
	 * 阳光：初次登记日期
	 */
	private String enrollDate;
	
	/**
	 * 阳光：行驶证发证日期（北京新车必传）
	 */
	private String carCertifiCateDate;	
	
	/**
	 * 阳光：车身颜色
	 */
	private String colorCode;
	
	/**
	 * 阳光：号牌底色
	 */
	private String licenseColor;
	
	/**
	 * 阳光：新车标识
	 */
	private String newVehicleFlag;
	
	/**
	 * 阳光：外地车标志
	 */
	private String ecdemicVehicleFlag;
	
	/**
	 * 阳光：过户车标志
	 */
	private String chgOwnerFlag;
	
	/**
	 * 阳光：转移登记日期
	 */
	private String firstRegisterDate;
	
	/**
	 * 阳光：是否上门投保业务（1是，2否，预留（厦门使用））
	 */
	private String isInDoor;	
	
	/**
	 * 阳光：是否车贷投保多年标志
	 */
	private String loanVehicleFlag;
	
	/**
	 * 阳光：交通违法（1上年无违法记录2上年有违法记录）
	 */
	private String peccancyadJust;	
	
	/**
	 * 阳光：使用性质
	 */
	private String useType;
	
	/**
	 * 阳光：行驶证车辆类型
	 */
	private String vehicleType;
	
	/**
	 * 阳光：港粤两地车牌标识（0-否 1-是）
	 */
	private String HKFlag;	
	
	/**
	 * 阳光：港粤车牌号（一期不考虑此业务，预留）
	 */
	private String HKLicenseNo;	
	
	/**
	 * 阳光：车辆型号（中文+字母数字的组合，如：帕萨特PASSAT B5 1.8T）
	 */
	private String brandName;	
	
	/**
	 * 阳光：车型代码
	 */
	private String rbCode;
	
	/**
	 * 阳光：是否古老特异车型（0否1是）
	 */
	private String specialCarFlag;	
	
	/**
	 * 阳光：车辆品牌（新车，外地车必传）
	 */
	private String vehicleBrand1;	
	
	/**
	 * 阳光：制造厂商
	 */
	private String madeFactory;
	
	/**
	 * 阳光：出厂日期
	 */
	private String makeDate;
	
	/**
	 * 阳光：新车购置价
	 */
	private String purchasePrice;
	
	/**
	 * 阳光：类比车价
	 */
	private String purchasePricelb;
	
	/**
	 * 阳光：原始车价
	 */
	private String oldPurchasePrice;
	
	/**
	 * 阳光：核定载客（单位：座）
	 */
	private String seatCount;	
	
	/**
	 * 阳光：原始载客
	 */
	private String oldSeatCount;
	
	/**
	 * 阳光：整车/整备质量（单位：千克）
	 */
	private String vehicleWeight;	
	
	/**
	 * 阳光：载重量
	 */
	private String vehicleTonnage;
	
	/**
	 * 阳光：原始载质量（单位：吨）
	 */
	private String oldVehicleTonnage;	
	
	/**
	 * 阳光：排量
	 */
	private String exhaustCapacity;
	
	/**
	 * 阳光：原始排量（单位：毫升）
	 */
	private String oldExhaustCapacity;	
	
	/**
	 * 阳光：燃料种类
	 */
	private String fuleType;
	
	/**
	 * 阳光：产地种类
	 */
	private String importFlag;
	
	/**
	 * 阳光：是否有ABS（预留0-否 1-是）
	 */
	private String absFlag;
	
	/**
	 * 阳光：安全气囊数（预留）
	 */
	private String airBagNum;	
	
	/**
	 * 阳光：是否有防盗装备（预留0-否 1-是）
	 */
	private String alarmFlag;	
	
	/**
	 * 阳光：车主性质
	 */
	private String vehicleOwnerNature;
	
	/**
	 * 阳光：打印厂牌型号
	 */
	private String printBrandName;
	
	/**
	 * 阳光：车龄（天）（"核心计算EnrollDate,StartDate无需外部发送"）
	 */
	private String useDays;	
	
	/**
	 * 阳光：车龄（年)（"核心计算EnrollDate,StartDate,comcode无需外部发送"）
	 */
	private String vehicleUsedYears;	
	
	/**
	 * 阳光：车龄(月)	（"核心计算EnrollDate,StartDate无需外部发送"）
	 */
	private String vehicleUsedMonths;	
	
//	/**
//	 * 阳光：精友车价【精友含税价】	
//	 */
//		private String PURCHASEPRICE;
	
	/**
	 * 阳光：精友不含税车价
	 */
	private String purchasePricenoTax;
	
	/**
	 * 阳光：车辆大类
	 */
	private String mainCarKindCode;
	
	/**
	 * 阳光：客车座位数与系统值所在费率档一致（"核心计算SEATCOUNT,prpdcarmodel.seatcount无需外部发送"）
	 */
	private String seatCountAgree;	
	
	/**
	 * 阳光：座位数低于系统所在费率档（规则查无此值）
	 */
	private String seatCountHigherpz;	
	
	/**
	 * 阳光：签单地点（客观信息对象）
	 */
	private String operateSite;
	
	/**
	 * 阳光：上市年份（核心根据modelcode查询prpdcarmodel表得）
	 */
	private String carYear;	
	
	/**
	 * 阳光：当前实际价值
	 */
	private String realPrice;
	
	/**
	 * 阳光：交强险条款类型
	 */
	private String clauseTypeCI;
	
	/**
	 * 阳光：商业险条款类型
	 */
	private String clauseTypeBI;
	
	/**
	 * 阳光：参考代码1
	 */
	private String refCode1;
	
	/**
	 * 阳光：参考代码2
	 */
	private String refCode2;

	public String getVinNo() {
		return vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	public String getRunMiles() {
		return runMiles;
	}

	public void setRunMiles(String runMiles) {
		this.runMiles = runMiles;
	}

	public String getLastYearEndDateCI() {
		return lastYearEndDateCI;
	}

	public void setLastYearEndDateCI(String lastYearEndDateCI) {
		this.lastYearEndDateCI = lastYearEndDateCI;
	}

	public String getLastYearEndDateBI() {
		return lastYearEndDateBI;
	}

	public void setLastYearEndDateBI(String lastYearEndDateBI) {
		this.lastYearEndDateBI = lastYearEndDateBI;
	}

	public String getSearchSequenceNo() {
		return searchSequenceNo;
	}

	public void setSearchSequenceNo(String searchSequenceNo) {
		this.searchSequenceNo = searchSequenceNo;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getCarKindCode() {
		return carKindCode;
	}

	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	public String getEnginNo() {
		return enginNo;
	}

	public void setEnginNo(String enginNo) {
		this.enginNo = enginNo;
	}

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public String getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getCarCertifiCateDate() {
		return carCertifiCateDate;
	}

	public void setCarCertifiCateDate(String carCertifiCateDate) {
		this.carCertifiCateDate = carCertifiCateDate;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getLicenseColor() {
		return licenseColor;
	}

	public void setLicenseColor(String licenseColor) {
		this.licenseColor = licenseColor;
	}

	public String getNewVehicleFlag() {
		return newVehicleFlag;
	}

	public void setNewVehicleFlag(String newVehicleFlag) {
		this.newVehicleFlag = newVehicleFlag;
	}

	public String getEcdemicVehicleFlag() {
		return ecdemicVehicleFlag;
	}

	public void setEcdemicVehicleFlag(String ecdemicVehicleFlag) {
		this.ecdemicVehicleFlag = ecdemicVehicleFlag;
	}

	public String getChgOwnerFlag() {
		return chgOwnerFlag;
	}

	public void setChgOwnerFlag(String chgOwnerFlag) {
		this.chgOwnerFlag = chgOwnerFlag;
	}

	public String getFirstRegisterDate() {
		return firstRegisterDate;
	}

	public void setFirstRegisterDate(String firstRegisterDate) {
		this.firstRegisterDate = firstRegisterDate;
	}

	public String getIsInDoor() {
		return isInDoor;
	}

	public void setIsInDoor(String isInDoor) {
		this.isInDoor = isInDoor;
	}

	public String getLoanVehicleFlag() {
		return loanVehicleFlag;
	}

	public void setLoanVehicleFlag(String loanVehicleFlag) {
		this.loanVehicleFlag = loanVehicleFlag;
	}

	public String getPeccancyadJust() {
		return peccancyadJust;
	}

	public void setPeccancyadJust(String peccancyadJust) {
		this.peccancyadJust = peccancyadJust;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getHKFlag() {
		return HKFlag;
	}

	public void setHKFlag(String hKFlag) {
		HKFlag = hKFlag;
	}

	public String getHKLicenseNo() {
		return HKLicenseNo;
	}

	public void setHKLicenseNo(String hKLicenseNo) {
		HKLicenseNo = hKLicenseNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getRbCode() {
		return rbCode;
	}

	public void setRbCode(String rbCode) {
		this.rbCode = rbCode;
	}

	public String getSpecialCarFlag() {
		return specialCarFlag;
	}

	public void setSpecialCarFlag(String specialCarFlag) {
		this.specialCarFlag = specialCarFlag;
	}

	public String getVehicleBrand1() {
		return vehicleBrand1;
	}

	public void setVehicleBrand1(String vehicleBrand1) {
		this.vehicleBrand1 = vehicleBrand1;
	}

	public String getMadeFactory() {
		return madeFactory;
	}

	public void setMadeFactory(String madeFactory) {
		this.madeFactory = madeFactory;
	}

	public String getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getPurchasePricelb() {
		return purchasePricelb;
	}

	public void setPurchasePricelb(String purchasePricelb) {
		this.purchasePricelb = purchasePricelb;
	}

	public String getOldPurchasePrice() {
		return oldPurchasePrice;
	}

	public void setOldPurchasePrice(String oldPurchasePrice) {
		this.oldPurchasePrice = oldPurchasePrice;
	}

	public String getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(String seatCount) {
		this.seatCount = seatCount;
	}

	public String getOldSeatCount() {
		return oldSeatCount;
	}

	public void setOldSeatCount(String oldSeatCount) {
		this.oldSeatCount = oldSeatCount;
	}

	public String getVehicleWeight() {
		return vehicleWeight;
	}

	public void setVehicleWeight(String vehicleWeight) {
		this.vehicleWeight = vehicleWeight;
	}

	public String getVehicleTonnage() {
		return vehicleTonnage;
	}

	public void setVehicleTonnage(String vehicleTonnage) {
		this.vehicleTonnage = vehicleTonnage;
	}

	public String getOldVehicleTonnage() {
		return oldVehicleTonnage;
	}

	public void setOldVehicleTonnage(String oldVehicleTonnage) {
		this.oldVehicleTonnage = oldVehicleTonnage;
	}

	public String getExhaustCapacity() {
		return exhaustCapacity;
	}

	public void setExhaustCapacity(String exhaustCapacity) {
		this.exhaustCapacity = exhaustCapacity;
	}

	public String getOldExhaustCapacity() {
		return oldExhaustCapacity;
	}

	public void setOldExhaustCapacity(String oldExhaustCapacity) {
		this.oldExhaustCapacity = oldExhaustCapacity;
	}

	public String getFuleType() {
		return fuleType;
	}

	public void setFuleType(String fuleType) {
		this.fuleType = fuleType;
	}

	public String getImportFlag() {
		return importFlag;
	}

	public void setImportFlag(String importFlag) {
		this.importFlag = importFlag;
	}

	public String getAbsFlag() {
		return absFlag;
	}

	public void setAbsFlag(String absFlag) {
		this.absFlag = absFlag;
	}

	public String getAirBagNum() {
		return airBagNum;
	}

	public void setAirBagNum(String airBagNum) {
		this.airBagNum = airBagNum;
	}

	public String getAlarmFlag() {
		return alarmFlag;
	}

	public void setAlarmFlag(String alarmFlag) {
		this.alarmFlag = alarmFlag;
	}

	public String getVehicleOwnerNature() {
		return vehicleOwnerNature;
	}

	public void setVehicleOwnerNature(String vehicleOwnerNature) {
		this.vehicleOwnerNature = vehicleOwnerNature;
	}

	public String getPrintBrandName() {
		return printBrandName;
	}

	public void setPrintBrandName(String printBrandName) {
		this.printBrandName = printBrandName;
	}

	public String getUseDays() {
		return useDays;
	}

	public void setUseDays(String useDays) {
		this.useDays = useDays;
	}

	public String getVehicleUsedYears() {
		return vehicleUsedYears;
	}

	public void setVehicleUsedYears(String vehicleUsedYears) {
		this.vehicleUsedYears = vehicleUsedYears;
	}

	public String getVehicleUsedMonths() {
		return vehicleUsedMonths;
	}

	public void setVehicleUsedMonths(String vehicleUsedMonths) {
		this.vehicleUsedMonths = vehicleUsedMonths;
	}

	public String getPurchasePricenoTax() {
		return purchasePricenoTax;
	}

	public void setPurchasePricenoTax(String purchasePricenoTax) {
		this.purchasePricenoTax = purchasePricenoTax;
	}

	public String getMainCarKindCode() {
		return mainCarKindCode;
	}

	public void setMainCarKindCode(String mainCarKindCode) {
		this.mainCarKindCode = mainCarKindCode;
	}

	public String getSeatCountAgree() {
		return seatCountAgree;
	}

	public void setSeatCountAgree(String seatCountAgree) {
		this.seatCountAgree = seatCountAgree;
	}

	public String getSeatCountHigherpz() {
		return seatCountHigherpz;
	}

	public void setSeatCountHigherpz(String seatCountHigherpz) {
		this.seatCountHigherpz = seatCountHigherpz;
	}

	public String getOperateSite() {
		return operateSite;
	}

	public void setOperateSite(String operateSite) {
		this.operateSite = operateSite;
	}

	public String getCarYear() {
		return carYear;
	}

	public void setCarYear(String carYear) {
		this.carYear = carYear;
	}

	public String getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(String realPrice) {
		this.realPrice = realPrice;
	}

	public String getClauseTypeCI() {
		return clauseTypeCI;
	}

	public void setClauseTypeCI(String clauseTypeCI) {
		this.clauseTypeCI = clauseTypeCI;
	}

	public String getClauseTypeBI() {
		return clauseTypeBI;
	}

	public void setClauseTypeBI(String clauseTypeBI) {
		this.clauseTypeBI = clauseTypeBI;
	}

	public String getRefCode1() {
		return refCode1;
	}

	public void setRefCode1(String refCode1) {
		this.refCode1 = refCode1;
	}

	public String getRefCode2() {
		return refCode2;
	}

	public void setRefCode2(String refCode2) {
		this.refCode2 = refCode2;
	}

}
