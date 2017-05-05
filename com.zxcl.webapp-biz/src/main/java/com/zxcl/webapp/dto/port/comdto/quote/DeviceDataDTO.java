package com.zxcl.webapp.dto.port.comdto.quote;

/**
 * 新增设备    发送
 * @author zx
 *
 */
public class DeviceDataDTO {
	
	/**
	 * 阳光：新增设备名称
	 */
	private String deviceName;
	
	/**
	 * 阳光：数量
	 */
	private String quantity;
	
	/**
	 * 阳光：新件重置价合计
	 */
	private String purchasePrice;
	
	/**
	 * 阳光：购置日期（格式：“YYYY-MM-DD”）
	 */
	private String buyDate;
	
	/**
	 * 阳光：设备实际价值
	 */
	private String actualValue;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public String getActualValue() {
		return actualValue;
	}

	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}
	
}

