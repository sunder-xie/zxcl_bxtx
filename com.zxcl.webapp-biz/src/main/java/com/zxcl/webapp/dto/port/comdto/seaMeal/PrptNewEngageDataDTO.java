package com.zxcl.webapp.dto.port.comdto.seaMeal;

/**
 * PRPT新的参与数据
 * @author zx
 *
 */
public class PrptNewEngageDataDTO {
	
	/**
	 * 阳光：投保单号
	 */
	private String proposalNo;
	
	/**
	 * 阳光：第一受益人信息
	 */
	private String firBennefitInfo;
	
	/**
	 * 阳光：专职经理姓名
	 */
	private String managerName;
	
	/**
	 * 阳光：专职经理手机号
	 */
	private String managerMobileNo;
	
	/**
	 * 阳光：是否是阳光客户
	 */
	private String sunShineCustomer;
	
	/**
	 * 阳光：专修厂
	 */
	private String speciaLize;
	
	/**
	 * 阳光：标示
	 */
	private String flag;

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getFirBennefitInfo() {
		return firBennefitInfo;
	}

	public void setFirBennefitInfo(String firBennefitInfo) {
		this.firBennefitInfo = firBennefitInfo;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerMobileNo() {
		return managerMobileNo;
	}

	public void setManagerMobileNo(String managerMobileNo) {
		this.managerMobileNo = managerMobileNo;
	}

	public String getSunShineCustomer() {
		return sunShineCustomer;
	}

	public void setSunShineCustomer(String sunShineCustomer) {
		this.sunShineCustomer = sunShineCustomer;
	}

	public String getSpeciaLize() {
		return speciaLize;
	}

	public void setSpeciaLize(String speciaLize) {
		this.speciaLize = speciaLize;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
