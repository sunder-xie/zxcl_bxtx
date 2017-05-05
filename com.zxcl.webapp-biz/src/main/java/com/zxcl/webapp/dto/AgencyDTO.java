package com.zxcl.webapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 中介公司
 * 
 * @author 3333
 *
 */
public class AgencyDTO {
	/**
	 * 主键,中介公编码
	 */
	private String agent_id;

	/**
	 * 中介公司名称
	 */
	private String agent_name;

	/**
	 * 代理机构级别1:总公司2：分公司
	 */
	private int agent_level;

	/**
	 * 上级公司编号
	 */
	private String agent_par_id;

	/**
	 * 开户银行
	 */
	private String bank;

	/**
	 * 开户名称
	 */
	private String bank_name;

	/**
	 * 银行账号
	 */
	private String bankNo;

	/**
	 * 联系人
	 */
	private String linkman_name;

	/**
	 * 联系方式
	 */
	private String linkman_tel;

	/**
	 * 公司地址
	 */
	private String company_adress;

	/**
	 * 营业执照注册号
	 */
	private String business_licence;

	/**
	 * 组织机构代码
	 */
	private String company_code;
	
	/**
	 * 状态 0：无效 1：有效
	 */
	private int status;
	
	/**
	 * 协议编码
	 */
	private String ptlCode;
	
	/**
	 * 调用接口需要的appId
	 */
	private String appId;
	
	/**
	 * 调用接口需要的key
	 */
	private String key;
	
	/**
	 * 代理人编码
	 */
	private String prxCode;

	/**
	 * 创建时间
	 */
	private String crt_tm;

	/**
	 * 创建人
	 */
	private String crt_cde;

	/**
	 * 修改时间
	 */
	private String upd_tm;

	/**
	 * 修改时间
	 */
	private String upd_cde;

	/**
	 * 上级中介
	 */
	private AgencyDTO parent;

	/**
	 * 主键,中介公编码
	 */
	public String getAgent_id() {
		return agent_id;
	}

	/**
	 * 主键,中介公编码
	 */
	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	/**
	 * 中介公司名称
	 */
	public String getAgent_name() {
		return agent_name;
	}

	/**
	 * 中介公司名称
	 */
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	/**
	 * 代理机构级别1:总公司2：分公司
	 */
	public int getAgent_level() {
		return agent_level;
	}

	/**
	 * 代理机构级别1:总公司2：分公司
	 */
	public void setAgent_level(int agent_level) {
		this.agent_level = agent_level;
	}


	/**
	 * 上级公司编号
	 */
	public String getAgent_par_id() {
		return agent_par_id;
	}

	/**
	 * 上级公司编号
	 */
	public void setAgent_par_id(String agent_par_id) {
		this.agent_par_id = agent_par_id;
	}

	/**
	 * 开户银行
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * 开户银行
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * 开户名称
	 */
	public String getBank_name() {
		return bank_name;
	}

	/**
	 * 开户名称
	 */
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	/**
	 * 银行账号
	 */
	public String getBankNo() {
		return bankNo;
	}

	/**
	 * 银行账号
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * 联系人
	 */
	public String getLinkman_name() {
		return linkman_name;
	}

	/**
	 * 联系人
	 */
	public void setLinkman_name(String linkman_name) {
		this.linkman_name = linkman_name;
	}

	/**
	 * 联系方式
	 */
	public String getLinkman_tel() {
		return linkman_tel;
	}

	/**
	 * 联系方式
	 */
	public void setLinkman_tel(String linkman_tel) {
		this.linkman_tel = linkman_tel;
	}

	/**
	 * 公司地址
	 */
	public String getCompany_adress() {
		return company_adress;
	}

	/**
	 * 公司地址
	 */
	public void setCompany_adress(String company_adress) {
		this.company_adress = company_adress;
	}

	/**
	 * 营业执照注册号
	 */
	public String getBusiness_licence() {
		return business_licence;
	}

	/**
	 * 营业执照注册号
	 */
	public void setBusiness_licence(String business_licence) {
		this.business_licence = business_licence;
	}

	/**
	 * 组织机构代码
	 */
	public String getCompany_code() {
		return company_code;
	}

	/**
	 * 组织机构代码
	 */
	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	/**
	 * 状态 0：无效 1：有效
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 状态 0：无效 1：有效
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "YYYY-MM-DD")
	public String getCrt_tm() {
		return crt_tm;
	}

	/**
	 * 创建时间
	 */
	public void setCrt_tm(String crt_tm) {
		this.crt_tm = crt_tm;
	}

	/**
	 * 创建人
	 */
	public String getCrt_cde() {
		return crt_cde;
	}

	/**
	 * 创建人
	 */
	public void setCrt_cde(String crt_cde) {
		this.crt_cde = crt_cde;
	}

	/**
	 * 修改时间
	 */
	public String getUpd_tm() {
		return upd_tm;
	}

	/**
	 * 修改时间
	 */
	public void setUpd_tm(String upd_tm) {
		this.upd_tm = upd_tm;
	}

	/**
	 * 修改人
	 */
	public String getUpd_cde() {
		return upd_cde;
	}

	/**
	 * 修改人
	 */
	public void setUpd_cde(String upd_cde) {
		this.upd_cde = upd_cde;
	}

	/**
	 * 上级中介
	 */
	public AgencyDTO getParent() {
		return parent;
	}

	/**
	 * 上级中介
	 */
	public void setParent(AgencyDTO parent) {
		this.parent = parent;
	}

	public String getPtlCode() {
		return ptlCode;
	}

	public void setPtlCode(String ptlCode) {
		this.ptlCode = ptlCode;
	}

	public String getPrxCode() {
		return prxCode;
	}

	public void setPrxCode(String prxCode) {
		this.prxCode = prxCode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "AgencyDTO [agent_id=" + agent_id + ", agent_name=" + agent_name + ", agent_level=" + agent_level + ", agent_par_id=" + agent_par_id
				+ ", bank=" + bank + ", bank_name=" + bank_name + ", bankNo=" + bankNo + ", linkman_name=" + linkman_name + ", linkman_tel="
				+ linkman_tel + ", company_adress=" + company_adress + ", business_licence=" + business_licence + ", company_code=" + company_code
				+ ", status=" + status + ", crt_tm=" + crt_tm + ", crt_cde=" + crt_cde + ", upd_tm=" + upd_tm + ", upd_cde=" + upd_cde + "]";
	}

}
