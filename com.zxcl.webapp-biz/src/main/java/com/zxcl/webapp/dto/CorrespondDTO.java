package com.zxcl.webapp.dto;

/**
 * 系统和保险公司对应
 * 
 * @author 5555
 *
 */
public class CorrespondDTO {
	public CorrespondDTO() {
		super();
	}

	public CorrespondDTO(String ins_id, String codeClass) {
		super();
		this.ins_id = ins_id;
		this.codeClass = codeClass;
	}

	public CorrespondDTO(String ins_id, String codeClass, String code) {
		super();
		this.ins_id = ins_id;
		this.codeClass = codeClass;
		this.code = code;
	}
	
	public CorrespondDTO(String ins_id, String codeClass, String code,
			String ins_code) {
		super();
		this.ins_id = ins_id;
		this.codeClass = codeClass;
		this.code = code;
		this.ins_code = ins_code;
	}
	public CorrespondDTO(String ins_id, String codeClass, String code,
			String ins_code,String remark) {
		super();
		this.ins_id = ins_id;
		this.codeClass = codeClass;
		this.code = code;
		this.ins_code = ins_code;
		this.remark = remark;
	}


	/**
	 * 保险公司的id
	 */
	private String ins_id;

	/**
	 * 类型名称
	 */
	private String codeClass;

	/**
	 * 系统定义的名称
	 */
	private String name;

	/**
	 * 系统定义的编号
	 */
	private String code;

	/**
	 * 保险公司定义的名称
	 */
	private String ins_name;

	/**
	 * 保险公司的定义编号
	 */
	private String ins_code;

	/**
	 * 是否默认
	 */
	private String isDefault;
	/**
	 * 状态
	 */
	private char status;
	/**
	 * 备注
	 */
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIns_id() {
		return ins_id;
	}

	public void setIns_id(String ins_id) {
		this.ins_id = ins_id;
	}

	/**
	 * 类型名称
	 */
	public String getCodeClass() {
		return codeClass;
	}

	/**
	 * 类型名称
	 */
	public void setCodeClass(String codeClass) {
		this.codeClass = codeClass;
	}

	/**
	 * 系统定义的名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 系统定义的名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 系统定义的编号
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 系统定义的编号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 保险公司定义的名称
	 */

	public String getIns_name() {
		return ins_name;
	}

	/**
	 * 保险公司定义的名称
	 */
	public void setIns_name(String ins_name) {
		this.ins_name = ins_name;
	}

	/**
	 * 保险公司的定义编号
	 */
	public String getIns_code() {
		return ins_code;
	}

	/**
	 * 保险公司的定义编号
	 */
	public void setIns_code(String ins_code) {
		this.ins_code = ins_code;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * 状态
	 */
	public char getStatus() {
		return status;
	}

	/**
	 * 状态
	 */
	public void setStatus(char status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CorrespondDTO [ins_id=" + ins_id + ", codeClass=" + codeClass + ", name=" + name + ", code=" + code + ", ins_name=" + ins_name
				+ ", ins_code=" + ins_code + ", isDefault=" + isDefault + ", status=" + status + "]";
	}
}
