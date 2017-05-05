package com.zxcl.webapp.dto.port.comdto.seaMeal;

/**
 * 投保人信息
 * @author zx
 *
 */
public class InsuredDataDTO {
	
	/**
	 * 阳光：关系人类型（2投保人，1被保人，0车主，3纳税人）
	 */
	private String personType;	
	
	/**
	 * 阳光：关系人类别（PERSONTYPE=0时车主使用性质                 0自然人，其他 法人或者组织）
	 */
	private String personClass;	
	
	/**
	 * 阳光：关系人名称（投保人名称、被保人名称）
	 */
	private String personName;	
	
	/**
	 * 阳光：性别（被保险人性别，1男，2女）
	 */
	private String sex;	
	
	/**
	 * 阳光：证件类型
	 */
	private String identifyType;
	
	/**
	 * 阳光：证件号码
	 */
	private String identifyNumber;
	
	/**
	 * 阳光：手机号
	 */
	private String mobile;
	
	/**
	 * 阳光：邮箱
	 */
	private String email;
	
	/**
	 * 阳光：寄送地址
	 */
	private String postAddress;
	
	/**
	 * 阳光：寄送邮编
	 */
	private String postCode;
	
	/**
	 * 阳光：个人类型类
	 */
	private String personTypeClass;
	
	/**
	 * 阳光：个人类型
	 */
	private String individualType;
	
	/**
	 * 阳光：年龄
	 */
	private String age;

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getPersonClass() {
		return personClass;
	}

	public void setPersonClass(String personClass) {
		this.personClass = personClass;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPersonTypeClass() {
		return personTypeClass;
	}

	public void setPersonTypeClass(String personTypeClass) {
		this.personTypeClass = personTypeClass;
	}

	public String getIndividualType() {
		return individualType;
	}

	public void setIndividualType(String individualType) {
		this.individualType = individualType;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
