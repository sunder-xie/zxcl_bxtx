package com.zxcl.webapp.dto.wallet;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class WalletDTO {
    private String walletId;

    private String userId;

    private BigDecimal amount;

    private BigDecimal amountIncome;

    private BigDecimal amountCash;
    
    private BigDecimal amountCashStay;
    
    private BigDecimal amountStay;

    private String walletPwd;

    private Date crtTm;

    private String crtCde;

    private Date updTm;

    private String updCde;

    private String status;
    
    private String versionId;

    
    public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public BigDecimal getAmountStay() {
		return amountStay;
	}

	public void setAmountStay(BigDecimal amountStay) {
		this.amountStay = amountStay;
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
		this.userId = userId;
	}

	public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountIncome() {
        return amountIncome;
    }

    public void setAmountIncome(BigDecimal amountIncome) {
        this.amountIncome = amountIncome;
    }

    public BigDecimal getAmountCash() {
        return amountCash;
    }

    public void setAmountCash(BigDecimal amountCash) {
        this.amountCash = amountCash;
    }

    public String getWalletPwd() {
        return walletPwd;
    }

    public void setWalletPwd(String walletPwd) {
        this.walletPwd = walletPwd == null ? null : walletPwd.trim();
    }

    public Date getCrtTm() {
        return crtTm;
    }

    public void setCrtTm(Date crtTm) {
        this.crtTm = crtTm;
    }

    public String getCrtCde() {
        return crtCde;
    }

    public void setCrtCde(String crtCde) {
        this.crtCde = crtCde == null ? null : crtCde.trim();
    }

    public Date getUpdTm() {
        return updTm;
    }

    public void setUpdTm(Date updTm) {
        this.updTm = updTm;
    }

    public String getUpdCde() {
        return updCde;
    }

    public void setUpdCde(String updCde) {
        this.updCde = updCde == null ? null : updCde.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public BigDecimal getAmountCashStay() {
		return amountCashStay;
	}

	public void setAmountCashStay(BigDecimal amountCashStay) {
		this.amountCashStay = amountCashStay;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
    
}