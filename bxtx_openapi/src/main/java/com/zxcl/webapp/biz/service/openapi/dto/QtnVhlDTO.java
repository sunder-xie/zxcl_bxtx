package com.zxcl.webapp.biz.service.openapi.dto;

public class QtnVhlDTO {

	/**
	 * 车型代码
	 */
	private String vehicleId;
	
	/**
	 * 厂牌车型; 车型名称;
	 */
	private String vehicleName;
	
	/**
	 * 新车购置价(不含税)
	 */
	private String vehiclePrice;
	
	/**
	 * 车牌号 如果未上牌,传空
	 */
	private String licensePlateNo;
	
	/**
	 * 初登日期 YYYY-MM-DD
	 */
	private String firstRegisterDate;
	
	/**
	 * 车架号
	 */
	private String vin;
	
	/**
	 * 发动机号
	 */
	private String engineNo;
	
	/**
	 * 转移登记日期(过户日期)
	 */
	private String transferDate;
	
	/**
	 * 未上牌标记  0已上牌, 1未上牌
	 */
	private String noLicenseFlag;
	
	/**
	 * 过户车标记  1：过户重新投保  0：未过户投保
	 */
	private String chgOwnerFlag;
	
	/**
	 * 外地车标记 1:外地车 0:本地车
	 */
	private String ecdemicVehicleFlag;

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehiclePrice() {
		return vehiclePrice;
	}

	public void setVehiclePrice(String vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}

	public String getLicensePlateNo() {
		return licensePlateNo;
	}

	public void setLicensePlateNo(String licensePlateNo) {
		this.licensePlateNo = licensePlateNo;
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

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	
}
