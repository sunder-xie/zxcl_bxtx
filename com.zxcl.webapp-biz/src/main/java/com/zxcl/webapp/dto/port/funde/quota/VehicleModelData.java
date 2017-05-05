package com.zxcl.webapp.dto.port.funde.quota;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class VehicleModelData {
	
	@XmlElement(name="licenseNo") private String licenseNo ;
	@XmlElement(name="licenseColor") private String licenseColor ;
	@XmlElement(name="licenseType") private String licenseType ;
	@XmlElement(name="frameNo") private String frameNo ;
	@XmlElement(name="engineNo") private String engineNo ;
	@XmlElement(name="enrollDate") private String enrollDate ;
	@XmlElement(name="searchSequenceNo") private String searchSequenceNo ;
	@XmlElement(name="mdfyPurchasePrice") private String mdfyPurchasePrice ;
	@XmlElement(name="owner") private String owner ;
	@XmlElement(name="ownerCertType") private String ownerCertType ;
	@XmlElement(name="ownerCertNo") private String ownerCertNo ;
	@XmlElement(name="useType") private String useType ;
	@XmlElement(name="runArea") private String runArea ;
	@XmlElement(name="runMile") private String runMile ;
	@XmlElement(name="totalRunMile") private String totalRunMile ;
	@XmlElement(name="modelCode") private String modelCode;
	@XmlElement(name="brandName") private String brandName;
	@XmlElement(name="vehicleKind") private String vehicleKind ;
	@XmlElement(name="vehicleType") private String vehicleType ;
	@XmlElement(name="vehicleTypeDesc") private String vehicleTypeDesc;
	@XmlElement(name="bodyColor") private String bodyColor ;
	@XmlElement(name="purchasePrice") private String purchasePrice;
	@XmlElement(name="fuelType") private String fuelType ;
	@XmlElement(name="vehicleBrand") private String vehicleBrand;
	@XmlElement(name="importFlag") private String importFlag ;
	@XmlElement(name="makeDate") private String makeDate ;
	@XmlElement(name="manufacturer") private String manufacturer;
	@XmlElement(name="vehicleSeries") private String vehicleSeries;
	@XmlElement(name="seatCount") private String seatCount;
	@XmlElement(name="marketDate") private String marketDate ;
	@XmlElement(name="vehicleTonnage") private String vehicleTonnage ;
	@XmlElement(name="exhaustCapacity") private String exhaustCapacity ;
	@XmlElement(name="vehicleWeight") private String vehicleWeight ;
	@XmlElement(name="alarmFlag") private String alarmFlag ;
	@XmlElement(name="airBagNum") private String airBagNum ;
	@XmlElement(name="transmissionType") private String transmissionType ;
	@XmlElement(name="absFlag") private String absFlag ;
	@XmlElement(name="platformModelCode") private String platformModelCode ;
	
	
	public VehicleModelData(String licenseNo, String licenseColor,
			String licenseType, String frameNo, String engineNo,
			String enrollDate, String searchSequenceNo,
			String mdfyPurchasePrice, String owner, String ownerCertType,
			String ownerCertNo, String useType, String runArea, String runMile,
			String totalRunMile, String modelCode, String brandName,
			String vehicleKind, String vehicleType, String vehicleTypeDesc,
			String bodyColor, String purchasePrice, String fuelType,
			String vehicleBrand, String importFlag, String makeDate,
			String manufacturer, String vehicleSeries, String seatCount,
			String marketDate, String vehicleTonnage, String exhaustCapacity,
			String vehicleWeight, String alarmFlag, String airBagNum,
			String transmissionType, String absFlag, String platformModelCode) {
		super();
		this.licenseNo = licenseNo;
		this.licenseColor = licenseColor;
		this.licenseType = licenseType;
		this.frameNo = frameNo;
		this.engineNo = engineNo;
		this.enrollDate = enrollDate;
		this.searchSequenceNo = searchSequenceNo;
		this.mdfyPurchasePrice = mdfyPurchasePrice;
		this.owner = owner;
		this.ownerCertType = ownerCertType;
		this.ownerCertNo = ownerCertNo;
		this.useType = useType;
		this.runArea = runArea;
		this.runMile = runMile;
		this.totalRunMile = totalRunMile;
		this.modelCode = modelCode;
		this.brandName = brandName;
		this.vehicleKind = vehicleKind;
		this.vehicleType = vehicleType;
		this.vehicleTypeDesc = vehicleTypeDesc;
		this.bodyColor = bodyColor;
		this.purchasePrice = purchasePrice;
		this.fuelType = fuelType;
		this.vehicleBrand = vehicleBrand;
		this.importFlag = importFlag;
		this.makeDate = makeDate;
		this.manufacturer = manufacturer;
		this.vehicleSeries = vehicleSeries;
		this.seatCount = seatCount;
		this.marketDate = marketDate;
		this.vehicleTonnage = vehicleTonnage;
		this.exhaustCapacity = exhaustCapacity;
		this.vehicleWeight = vehicleWeight;
		this.alarmFlag = alarmFlag;
		this.airBagNum = airBagNum;
		this.transmissionType = transmissionType;
		this.absFlag = absFlag;
		this.platformModelCode = platformModelCode;
	}



	public VehicleModelData() {
		super();
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
