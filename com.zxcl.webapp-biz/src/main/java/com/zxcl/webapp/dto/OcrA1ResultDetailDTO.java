package com.zxcl.webapp.dto;

import java.io.Serializable;

/**
 * 行驶证识别结果详情DTO
 * @author zxj
 * @date 2016年11月7日
 * @description 
 */
public class OcrA1ResultDetailDTO implements Serializable{
	
	private static final long serialVersionUID = 6189864327836214337L;

	private String plate_no;//号牌号码。
	
	private String vehicle_type;//车辆类型。
	
	private String owner;//所有人。
	
	private String address;//住址。
	
	private String use_character;//使用性质。
	
	private String model;//品牌型号。
	
	private String vin;//车辆识别代号。(车架号)
	
	private String engine_no;//发动机号码。
	
	private String register_date;//注册日期，格式为YYYY-MM-DD
	
	private String issue_date;//发证日期，格式为YYYY-MM-DD
	
	private String issued_by;//签发机关。

	public String getPlate_no() {
		return plate_no;
	}

	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}

	public String getVehicle_type() {
		return vehicle_type;
	}

	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUse_character() {
		return use_character;
	}

	public void setUse_character(String use_character) {
		this.use_character = use_character;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getEngine_no() {
		return engine_no;
	}

	public void setEngine_no(String engine_no) {
		this.engine_no = engine_no;
	}

	public String getRegister_date() {
		return register_date;
	}

	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}

	public String getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}

	public String getIssued_by() {
		return issued_by;
	}

	public void setIssued_by(String issued_by) {
		this.issued_by = issued_by;
	}
	
	
}
