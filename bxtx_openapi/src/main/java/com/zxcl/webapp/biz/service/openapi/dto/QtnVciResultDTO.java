package com.zxcl.webapp.biz.service.openapi.dto;

/**
 * 商业报价结果
 * @author wenchang001
 *
 */
public class QtnVciResultDTO {

	private String prodNo = "VCI";
	private String prm;
	private String disccount;
	public String getProdNo() {
		return prodNo;
	}
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	public String getPrm() {
		return prm;
	}
	public void setPrm(String prm) {
		this.prm = prm;
	}
	public String getDisccount() {
		return disccount;
	}
	public void setDisccount(String disccount) {
		this.disccount = disccount;
	}
}
