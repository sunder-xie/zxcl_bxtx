package com.zxcl.webapp.dto;

/**
 * @author zxj
 * @date 2016年10月11日
 * @description 
 */
public class BankOcrDTO {
	private String cardNo;
	
    private String cardName;

    private Integer cardLen;

    private String cardType;

    private String cardPresign;

    private String bankName;

    private String ocrBankNo;

    private String remark;

    public String getCardName() {
        return cardName;
    }

    public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    public Integer getCardLen() {
        return cardLen;
    }

    public void setCardLen(Integer cardLen) {
        this.cardLen = cardLen;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getCardPresign() {
        return cardPresign;
    }

    public void setCardPresign(String cardPresign) {
        this.cardPresign = cardPresign == null ? null : cardPresign.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getOcrBankNo() {
        return ocrBankNo;
    }

    public void setOcrBankNo(String ocrBankNo) {
        this.ocrBankNo = ocrBankNo == null ? null : ocrBankNo.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}