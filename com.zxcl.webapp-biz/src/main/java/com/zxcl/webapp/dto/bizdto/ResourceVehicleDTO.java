package com.zxcl.webapp.dto.bizdto;

import java.io.Serializable;
import java.util.List;

import com.zxcl.webapp.dto.ResourceVehicleCvrgDTO;

/**
 * 
* @ClassName:  ResourceVehicleDTO 
* @Description: 车辆信息DTO
* @author 赵晋
* @date 2015年11月18日 上午10:28:11
*
 */
public class ResourceVehicleDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2326742344534804040L;
	/**
	 * 车牌号
	 */
	private String plateNo;
	/**
	 * 发动机号
	 */
	private String engNo;
	/**
	 * 车架号
	 */
	private String frmNo;
	/**
	 * 初登年月:年月日(20130201)
	 */
	private String fstRegYm;
	/**
	 * 车型名称
	 */
	private String modelNme;
	/**
	 * 车主名称
	 */
	private String ownerNme;
	/**
	 * 身份证号
	 */
	private String certfCde;
	/**
	 * 商业保险止期
	 */
	private String vciInsureEnd;
	/**
	 * 交强保险止期
	 */
	private String tciInsureEnd;
	
	
	/**
	 * 商业险种
	 */
	private List<ResourceVehicleCvrgDTO> cvrgList;
	
	
	/**
	 * 数据来源CODE
	 */
	private String dataSource;
	
	/**
	 * 数据来源
	 */
	private String dataSourceName;
	
	/**
	 * 说明
	 */
	private String remark;

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public List<ResourceVehicleCvrgDTO> getCvrgList() {
		return cvrgList;
	}
	public void setCvrgList(List<ResourceVehicleCvrgDTO> cvrgList) {
		this.cvrgList = cvrgList;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public String getEngNo() {
		return engNo;
	}
	public void setEngNo(String engNo) {
		this.engNo = engNo;
	}
	public String getFrmNo() {
		return frmNo;
	}
	public void setFrmNo(String frmNo) {
		this.frmNo = frmNo;
	}
	public String getFstRegYm() {
		return fstRegYm;
	}
	public void setFstRegYm(String fstRegYm) {
		this.fstRegYm = fstRegYm;
	}
	public String getModelNme() {
		return modelNme;
	}
	public void setModelNme(String modelNme) {
		this.modelNme = modelNme;
	}
	public String getOwnerNme() {
		return ownerNme;
	}
	public void setOwnerNme(String ownerNme) {
		this.ownerNme = ownerNme;
	}
	public String getCertfCde() {
		return certfCde;
	}
	public void setCertfCde(String certfCde) {
		this.certfCde = certfCde;
	}
	public String getVciInsureEnd() {
		return vciInsureEnd;
	}
	public void setVciInsureEnd(String vciInsureEnd) {
		this.vciInsureEnd = vciInsureEnd;
	}
	public String getTciInsureEnd() {
		return tciInsureEnd;
	}
	public void setTciInsureEnd(String tciInsureEnd) {
		this.tciInsureEnd = tciInsureEnd;
	}
	@Override
	public String toString() {
		return "ResourceVehicleDTO [plateNo=" + plateNo + ", engNo=" + engNo
				+ ", frmNo=" + frmNo + ", fstRegYm=" + fstRegYm + ", modelNme="
				+ modelNme + ", ownerNme=" + ownerNme + ", certfCde="
				+ certfCde + ", vciInsureEnd=" + vciInsureEnd
				+ ", tciInsureEnd=" + tciInsureEnd + ", cvrgList=" + cvrgList
				+ "]";
	}
	
	
}
