package com.zxcl.webapp.biz.service.openapi.dto;

public class QtnTaxResultDTO {
	private String taxBeginDate;
	private String taxEndDate;
	private String taxCurrentYear;
	private String taxLastYear;
	private String taxOverdue;
	private String taxTotal;
	
	public String getTaxBeginDate() {
		return taxBeginDate;
	}
	public void setTaxBeginDate(String taxBeginDate) {
		this.taxBeginDate = taxBeginDate;
	}
	public String getTaxEndDate() {
		return taxEndDate;
	}
	public void setTaxEndDate(String taxEndDate) {
		this.taxEndDate = taxEndDate;
	}
	public String getTaxCurrentYear() {
		return taxCurrentYear;
	}
	public void setTaxCurrentYear(String taxCurrentYear) {
		this.taxCurrentYear = taxCurrentYear;
	}
	public String getTaxLastYear() {
		return taxLastYear;
	}
	public void setTaxLastYear(String taxLastYear) {
		this.taxLastYear = taxLastYear;
	}
	public String getTaxOverdue() {
		return taxOverdue;
	}
	public void setTaxOverdue(String taxOverdue) {
		this.taxOverdue = taxOverdue;
	}
	public String getTaxTotal() {
		return taxTotal;
	}
	public void setTaxTotal(String taxTotal) {
		this.taxTotal = taxTotal;
	}

}
