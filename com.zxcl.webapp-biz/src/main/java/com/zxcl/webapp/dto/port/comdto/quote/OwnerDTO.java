package com.zxcl.webapp.dto.port.comdto.quote;

/**
 * 车主信息
 * @author zx
 *
 */
public class OwnerDTO {

	
	/**
	 * 锦泰：车主别名
	 * 太平：车主姓名（name）
	 * 天平：人员姓名（PersonnelName）
	 */
	private String vehicleOwnerName;
	
	/**
	 * 锦泰：车主类别
	 */
	private String vehicleOwnerType;
	/**
	 * 锦泰：车主出生日期，格式：yyyyMMdd
	 * 天平：出身日期（Birthday）
	 */
	private String vehicleOwnerBirth;
	/**
	 * 锦泰：车主性别：1男，2女
	 * 天平：性别（Gender（M男，N女））
	 */
	private String vehicleOwnerSex;
	/**
	 * 锦泰：车主证件类型，01身份证
	 * 天平：证件类型（CertificateType）
	 */
	private String vehicleOwnerCertfCls;
	/**
	 * 锦泰：车主证件号码
	 * 天平：证件号码（CertificateNo）
	 */
	private String vehicleOwnerCertfCde;
	
	/**
	 * 太平：车主身份证号
	 */
	private String identifyNumber;
	
	/**
	 * 太平：蚂蚁金服维他命因子
	 */
	private String vitalevel;
	
	/**
	 * 华泰：投保关系人地址
	 */
	private String insuredAddress;
	/**
	 * 华泰：移动电话
	 */
	private String tel;

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getInsuredAddress() {
		return insuredAddress;
	}

	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}

	public String getVehicleOwnerName() {
		return vehicleOwnerName;
	}

	public void setVehicleOwnerName(String vehicleOwnerName) {
		this.vehicleOwnerName = vehicleOwnerName;
	}

	public String getVehicleOwnerType() {
		return vehicleOwnerType;
	}

	public void setVehicleOwnerType(String vehicleOwnerType) {
		this.vehicleOwnerType = vehicleOwnerType;
	}

	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public String getVitalevel() {
		return vitalevel;
	}

	public void setVitalevel(String vitalevel) {
		this.vitalevel = vitalevel;
	}

	public String getVehicleOwnerBirth() {
		return vehicleOwnerBirth;
	}

	public void setVehicleOwnerBirth(String vehicleOwnerBirth) {
		this.vehicleOwnerBirth = vehicleOwnerBirth;
	}

	public String getVehicleOwnerSex() {
		return vehicleOwnerSex;
	}

	public void setVehicleOwnerSex(String vehicleOwnerSex) {
		this.vehicleOwnerSex = vehicleOwnerSex;
	}

	public String getVehicleOwnerCertfCls() {
		return vehicleOwnerCertfCls;
	}

	public void setVehicleOwnerCertfCls(String vehicleOwnerCertfCls) {
		this.vehicleOwnerCertfCls = vehicleOwnerCertfCls;
	}

	public String getVehicleOwnerCertfCde() {
		return vehicleOwnerCertfCde;
	}

	public void setVehicleOwnerCertfCde(String vehicleOwnerCertfCde) {
		this.vehicleOwnerCertfCde = vehicleOwnerCertfCde;
	}
	
}