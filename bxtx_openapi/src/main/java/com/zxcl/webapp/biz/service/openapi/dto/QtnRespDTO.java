package com.zxcl.webapp.biz.service.openapi.dto;

import java.util.List;

public class QtnRespDTO {

	private String errorCode;
	
	private String errorMessage;
	
	private String qtnId;
	
	private QtnTciResultDTO tciQuoteResult;
	
	private QtnVciResultDTO vciQuoteResult;
	
	private QtnTaxResultDTO taxQuoteResult;
	
	private QtnReInsureDTO reInsureItem;
	
	private List<CvrgDTO> cvrgList;

	public String getQtnId() {
		return qtnId;
	}

	public void setQtnId(String qtnId) {
		this.qtnId = qtnId;
	}

	public QtnTciResultDTO getTciQuoteResult() {
		return tciQuoteResult;
	}

	public void setTciQuoteResult(QtnTciResultDTO tciQuoteResult) {
		this.tciQuoteResult = tciQuoteResult;
	}

	public QtnVciResultDTO getVciQuoteResult() {
		return vciQuoteResult;
	}

	public void setVciQuoteResult(QtnVciResultDTO vciQuoteResult) {
		this.vciQuoteResult = vciQuoteResult;
	}

	public QtnTaxResultDTO getTaxQuoteResult() {
		return taxQuoteResult;
	}

	public void setTaxQuoteResult(QtnTaxResultDTO taxQuoteResult) {
		this.taxQuoteResult = taxQuoteResult;
	}

	public List<CvrgDTO> getCvrgList() {
		return cvrgList;
	}

	public void setCvrgList(List<CvrgDTO> cvrgList) {
		this.cvrgList = cvrgList;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public QtnReInsureDTO getReInsureItem() {
		return reInsureItem;
	}

	public void setReInsureItem(QtnReInsureDTO reInsureItem) {
		this.reInsureItem = reInsureItem;
	}
	
}
