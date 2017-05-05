package com.zxcl.webapp.dto.wallet;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class WalletBankDTO {
    private String id;

    private String userId;

    private String bankNo;

    private String bankName;

    private String cardNo;

    private String cardOwner;
    
    private String cardType;

    private String tel;

    private String crtCde;

    private Date crtTm;

    private String updCde;

    private Date updTm;
    
    private String agentMicroId;//名义代理人小微id
    
    private String isAgent;//是否是名义代理人
    
    

    public String getAgentMicroId() {
		return agentMicroId;
	}

	public void setAgentMicroId(String agentMicroId) {
		this.agentMicroId = agentMicroId;
	}

	public String getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}

	public String getId() {
        return id;
    }

    public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo == null ? null : bankNo.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner == null ? null : cardOwner.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
    
}