package com.zxcl.webapp.dto.wallet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class WalletBillDTO  implements Serializable{
	private static final long serialVersionUID = 65655882130769896L;

	private String billId;

    private String walletId;

    private String userId;

    private BigDecimal billAmount;

    private String billType;

    private String remark;

    private String crtCde;

    private Date crtTm;

    private String updCde;

    private Date updTm;

    private String payChannel;

    private String payOrderNo;

    private String cashBankNo;

    private String cashCardNo;

    private String cashCardOwner;

    private String resultMsg;

    private String status;
    
    private String oldStatus;
    
    private String showName;
    
    private String amountType;
    
    private String amountTargetId;
    
    public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public String getAmountTargetId() {
		return amountTargetId;
	}

	public void setAmountTargetId(String amountTargetId) {
		this.amountTargetId = amountTargetId;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId == null ? null : billId.trim();
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId == null ? null : walletId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCrtCde() {
        return crtCde;
    }

    public void setCrtCde(String crtCde) {
        this.crtCde = crtCde == null ? null : crtCde.trim();
    }

    public Date getCrtTm() {
        return crtTm;
    }

    public void setCrtTm(Date crtTm) {
        this.crtTm = crtTm;
    }

    public String getUpdCde() {
        return updCde;
    }

    public void setUpdCde(String updCde) {
        this.updCde = updCde == null ? null : updCde.trim();
    }

    public Date getUpdTm() {
        return updTm;
    }

    public void setUpdTm(Date updTm) {
        this.updTm = updTm;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel == null ? null : payChannel.trim();
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo == null ? null : payOrderNo.trim();
    }

    public String getCashBankNo() {
        return cashBankNo;
    }

    public void setCashBankNo(String cashBankNo) {
        this.cashBankNo = cashBankNo == null ? null : cashBankNo.trim();
    }

    public String getCashCardNo() {
        return cashCardNo;
    }

    public void setCashCardNo(String cashCardNo) {
        this.cashCardNo = cashCardNo == null ? null : cashCardNo.trim();
    }

    public String getCashCardOwner() {
        return cashCardOwner;
    }

    public void setCashCardOwner(String cashCardOwner) {
        this.cashCardOwner = cashCardOwner == null ? null : cashCardOwner.trim();
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg == null ? null : resultMsg.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
    
    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}