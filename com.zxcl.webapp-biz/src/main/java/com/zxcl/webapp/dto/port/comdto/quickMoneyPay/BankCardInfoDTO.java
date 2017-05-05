package com.zxcl.webapp.dto.port.comdto.quickMoneyPay;

/**
 * 银行卡信息
 * @author zx
 *
 */
public class BankCardInfoDTO {
	
	/**
	 * 天平：卡号
	 */
	private String cardNo;
	
	/**
	 * 天平：银行卡类：0001:信用卡，0002：借记卡
	 */
	private String cardType;
	
	/**
	 * 天平：短卡号
	 */
	private String storableCardNo;
	
	/**
	 * 天平：有效期（MMYY）
	 */
	private String expiredDate;
	
	/**
	 * 天平：对于银联和VISA卡，对应卡片背面的CVV2数字；对于MasterCard卡，对应卡片背面的CVC2数字
	 */
	private String cvv2;
	
	/**
	 * 天平：客户号
	 */
	private String customerId;
	
	/**
	 * 天平：客户姓名
	 */
	private String cardHolderName;
	
	/**
	 * 天平：客户身份证号码
	 */
	private String cardHolderId;
	
	/**
	 * 天平：特殊交易标志
	 */
	private String spFlag;
	
	/**
	 * 天平：证件类型：0:身份证，1：护照，2：军官证，3：士兵证，4：港澳台通行证，5：临时身份证，6：户口本， 7：其他证件类型，9：警官证，12外国人居住证，15：回乡证，16：企业营业执照，17：法人代码证，18：台胞证
	 */
	private String idType;
	
	/**
	 * 天平：保存鉴权信息
	 */
	private String savePciFlag;
	
	/**
	 * 天平：手机号码
	 */
	private String phone;
	
	/**
	 * 天平：手机验证码
	 */
	private String validCode;
	
	/**
	 * 天平：手机令牌码
	 */
	private String token;
	
	/**
	 * 天平：交易方式
	 */
	private String tradeType;
	
	/**
	 * 天平：支付批次
	 */
	private String payBatch;
	
	/**
	 * 天平：银行简称
	 */
	private String bankId;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getStorableCardNo() {
		return storableCardNo;
	}

	public void setStorableCardNo(String storableCardNo) {
		this.storableCardNo = storableCardNo;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardHolderId() {
		return cardHolderId;
	}

	public void setCardHolderId(String cardHolderId) {
		this.cardHolderId = cardHolderId;
	}

	public String getSpFlag() {
		return spFlag;
	}

	public void setSpFlag(String spFlag) {
		this.spFlag = spFlag;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getSavePciFlag() {
		return savePciFlag;
	}

	public void setSavePciFlag(String savePciFlag) {
		this.savePciFlag = savePciFlag;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPayBatch() {
		return payBatch;
	}

	public void setPayBatch(String payBatch) {
		this.payBatch = payBatch;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
}	
