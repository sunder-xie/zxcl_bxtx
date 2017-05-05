package com.zxcl.webapp.dto.port.comdto.getPaySeriaNum;

/**
 * 支付产品
 * @author zx
 *
 */
public class ProductDTO {
	
	/**
	 * 天平：投保单流水号
	 */
	private String policyId;
	
	/**
	 * 天平：投保单号
	 */
	private String applyPolicyNo;
	
	/**
	 * 天平：保单号
	 */
	private String policyNo;
	
	/**
	 * 天平：网上支付的话对应的是网销EC_INSURE_ID网上投保流水号，快钱电话支付的话对应的是电销PARTY_ID,每个用户对应一个
	 */
	private String productCode;
	
	/**
	 * 天平：险种编码
	 */
	private String planCode;
	
	/**
	 * 天平：产品名称
	 */
	private String productName;
	
	/**
	 * 天平：产品金额
	 */
	private String productAmt;
	
	/**
	 * 天平：产品类型
	 */
	private String productType;
	
	/**
	 * 天平：是否验证产品
	 */
	private String isVerify;
	
	/**
	 * 天平：验证接口名称
	 */
	private String verifyInterface;
	
	/**
	 * 天平：受益人姓名
	 */
	private String userName;
	
	/**
	 * 天平：受益人手机
	 */
	private String productPhoneNumber;
	
	/**
	 * 天平：查看投保单信息a
	 */
	private String viewProductUrl;
	
	/**
	 * 天平：生效日期
	 */
	private String insuranceBeginTime;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getApplyPolicyNo() {
		return applyPolicyNo;
	}

	public void setApplyPolicyNo(String applyPolicyNo) {
		this.applyPolicyNo = applyPolicyNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductAmt() {
		return productAmt;
	}

	public void setProductAmt(String productAmt) {
		this.productAmt = productAmt;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getIsVerify() {
		return isVerify;
	}

	public void setIsVerify(String isVerify) {
		this.isVerify = isVerify;
	}

	public String getVerifyInterface() {
		return verifyInterface;
	}

	public void setVerifyInterface(String verifyInterface) {
		this.verifyInterface = verifyInterface;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProductPhoneNumber() {
		return productPhoneNumber;
	}

	public void setProductPhoneNumber(String productPhoneNumber) {
		this.productPhoneNumber = productPhoneNumber;
	}

	public String getViewProductUrl() {
		return viewProductUrl;
	}

	public void setViewProductUrl(String viewProductUrl) {
		this.viewProductUrl = viewProductUrl;
	}

	public String getInsuranceBeginTime() {
		return insuranceBeginTime;
	}

	public void setInsuranceBeginTime(String insuranceBeginTime) {
		this.insuranceBeginTime = insuranceBeginTime;
	}
	
}
