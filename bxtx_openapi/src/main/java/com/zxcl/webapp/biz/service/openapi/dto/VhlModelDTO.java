package com.zxcl.webapp.biz.service.openapi.dto;


public class VhlModelDTO {
	
	/**
	 * 车型代码
	 */
	private String vehicleId;
	
	/**
	 * 车型名称
	 */
	private String vehicleName;

	/**
	 * 车型别名
	 */
	private String vehicleAlias;

	/**
	 * 上市年份yyyyMMdd
	 */
	private String marketaDate;

	/**
	 * 座位数
	 */
	private String vehicleSeat;

	/**
	 * 排量1.998
	 */
	private String vehicleExhaust;

	/**
	 * 车辆价格
	 */
	private String vehiclePrice;

	/**
	 * 备注
	 */
	private String remark;

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehicleAlias() {
		return vehicleAlias;
	}

	public void setVehicleAlias(String vehicleAlias) {
		this.vehicleAlias = vehicleAlias;
	}

	public String getMarketaDate() {
		return marketaDate;
	}

	public void setMarketaDate(String marketaDate) {
		this.marketaDate = marketaDate;
	}

	public String getVehicleSeat() {
		return vehicleSeat;
	}

	public void setVehicleSeat(String vehicleSeat) {
		this.vehicleSeat = vehicleSeat;
	}

	public String getVehicleExhaust() {
		return vehicleExhaust;
	}

	public void setVehicleExhaust(String vehicleExhaust) {
		this.vehicleExhaust = vehicleExhaust;
	}

	public String getVehiclePrice() {
		return vehiclePrice;
	}

	public void setVehiclePrice(String vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
