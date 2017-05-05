package com.zxcl.webapp.dto.rmi.intf.vehicle.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zxcl.webapp.dto.ResourceVehicleCvrgDTO;
import com.zxcl.webapp.dto.rmi.intf.common.CommonDTO;

/**
 * 车型返回
 * @author zxj
 * @date 2016年7月13日
 * @description 
 */
public class VehicleModelDTO extends CommonDTO implements Serializable{
	
	private static final long serialVersionUID = 955387462883811342L;
	
	/**
	 * 保险公司(写代码CODE)
	 */
	private String insId;
	/**
	 * 车型编码
	 */
	private String modelCode;

	/**
	 * 车型名称:比亚迪BYD7150A轿车
	 */
	private String vehicleName;

	/**
	 * 车型别名(DETAIl)
	 */
	private String vehicleAlias;

	/**
	 * 上市年份(yyyy-MM-dd)
	 */
	private String marketaDate;

	/**
	 * 座位数
	 */
	private Integer seatNum;

	/**
	 * 排量
	 */
	private BigDecimal displacement;

	/**
	 * 车辆价格
	 */
	private BigDecimal vehiclePrice;


	/**
	 * 车辆配置信息:2013款 5座 40562元
	 */
	private String configDesc;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 险种代码
	 */
	private List<ResourceVehicleCvrgDTO> cvrgList;
	
	protected String crtCode;

	protected Date crtTime;

	protected String updCode;

	protected Date updTime;

	public String getCrtCode() {
		return crtCode;
	}

	public void setCrtCode(String crtCode) {
		this.crtCode = crtCode;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getUpdCode() {
		return updCode;
	}

	public void setUpdCode(String updCode) {
		this.updCode = updCode;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	

	public List<ResourceVehicleCvrgDTO> getCvrgList() {
		return cvrgList;
	}

	public void setCvrgList(List<ResourceVehicleCvrgDTO> cvrgList) {
		this.cvrgList = cvrgList;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getVehicleAlias() {
		return vehicleAlias;
	}

	public void setVehicleAlias(String vehicleAlias) {
		this.vehicleAlias = vehicleAlias;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getMarketaDate() {
		return marketaDate;
	}

	public void setMarketaDate(String marketaDate) {
		this.marketaDate = marketaDate;
	}

	public Integer getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}

	public BigDecimal getDisplacement() {
		return displacement;
	}

	public void setDisplacement(BigDecimal displacement) {
		this.displacement = displacement;
	}

	public BigDecimal getVehiclePrice() {
		return vehiclePrice;
	}

	public void setVehiclePrice(BigDecimal vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public VehicleModelDTO(String vehicleName){
		super();
		this.vehicleName = vehicleName;
	}
	public VehicleModelDTO(){
		super();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((insId == null) ? 0 : insId.hashCode());
		result = prime * result + ((modelCode == null) ? 0 : modelCode.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleModelDTO other = (VehicleModelDTO) obj;
		if (insId == null) {
			if (other.insId != null)
				return false;
		} else if (!insId.equals(other.insId))
			return false;
		if (modelCode == null) {
			if (other.modelCode != null)
				return false;
		} else if (!modelCode.equals(other.modelCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleModelDTO [insId=" + insId + ", modelCode=" + modelCode
				+ ", vehicleName=" + vehicleName + ", vehicleAlias="
				+ vehicleAlias + ", marketaDate=" + marketaDate + ", seatNum="
				+ seatNum + ", displacement=" + displacement
				+ ", vehiclePrice=" + vehiclePrice + ", configDesc="
				+ configDesc + ", remark=" + remark + ", cvrgList=" + cvrgList
				+ ", crtCode=" + crtCode + ", crtTime=" + crtTime
				+ ", updCode=" + updCode + ", updTime=" + updTime + "]";
	}


	
}
