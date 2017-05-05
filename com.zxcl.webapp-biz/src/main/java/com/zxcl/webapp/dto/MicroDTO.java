package com.zxcl.webapp.dto;

import java.util.List;

import com.zxcl.webapp.dto.rmi.intf.common.BaseDTO;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;


/**
 * 小微用户信息
 * 
 * @author 3333
 *
 */
public class MicroDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键,小微用户编码
	 */
	private String micro_id;
	
	/**
	 * 登陆用户账号编码
	 */
	private String user_id;
	//选中的保险公司(从页面接受)
	private List<InsuranceDTO> chosen;
	/**
	 * 创建人
	 */
	private String crt_cde;
	
	/**
	 * 修改人
	 */
	private String upd_cde;
	/**
	 *  邀请码
	 */
	
	private String invitation;
	
	private String relevance;
	
	//密码
	private String password;
	
	/**
	 * 代理机构编码
	 */
	private String agent_id;
	
	/**
	 * 代理机构编码
	 */
	private AgencyDTO agency;

	/**
	 * 中介公司团队编码
	 */
	private String agt_team_id;
	
	/**
	 *  中介公司团队团队名
	 */
	private String agtTeamName;

	
	//微信号
	private String weChatId;
	
	private String weChatType;
	
	/**
	 * 小微用户昵称
	 */
	private String micro_name;

	/**
	 * 登陆用户账号编码
	 */
	private UserDTO user;

	/**
	 * 联系电话
	 */
	private String tel;

	/**
	 * 代理机构编码
	 */
	private String micro_class;

	/**
	 * 状态 0：无效 1：有效
	 */
	private int status;

	/**
	 * 费率
	 */
	private String rote;
	
	/**
	 * 小微职责,0:团队管理员，1：一般营销员
	 */
	private String duty;

	
	private String appId;
	/**
	 * 主键,小微用户编码
	 */
	public String getMicro_id() {
		return micro_id;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	/**
	 * 主键,小微用户编码
	 */
	public void setMicro_id(String micro_id) {
		this.micro_id = micro_id;
	}

	/**
	 * 代理机构编码
	 */
	public AgencyDTO getAgency() {
		return agency;
	}

	/**
	 * 代理机构编码
	 */
	public void setAgency(AgencyDTO agency) {
		this.agency = agency;
	}

	/**
	 * 中介公司团队编码
	 */
	public String getAgt_team_id() {
		return agt_team_id;
	}
	
	public String getInvitation() {
		return invitation;
	}

	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}

	/**
	 * 中介公司团队编码
	 */
	public void setAgt_team_id(String agt_team_id) {
		this.agt_team_id = agt_team_id;
	}

	/**
	 * 小微用户昵称
	 */
	public String getMicro_name() {
		return micro_name;
	}

	/**
	 * 小微用户昵称
	 */
	public void setMicro_name(String micro_name) {
		this.micro_name = micro_name;
	}

	/**
	 * 登陆用户账号编码
	 */
	public void setUser(UserDTO user) {
		this.user = user;
	}

	/**
	 * 登陆用户账号编码
	 */
	public UserDTO getUser() {
		return user;
	}

	/**
	 * 联系电话
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 联系电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}


	/**
	 * 小微业务类型基础信息编码
	 */
	public String getMicro_class() {
		return micro_class;
	}

	/**
	 * 小微业务类型基础信息编码
	 */
	public void setMicro_class(String micro_class) {
		this.micro_class = micro_class;
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
	 * 邮箱地址
	 */
	private String email;
	
	/**
	 * 邮箱地址
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 邮箱地址
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRote() {
		return rote;
	}
	
	public void setRote(String rote) {
		this.rote = rote;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAgent_id() {
		return agent_id;
	}
	
	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getCrt_cde() {
		return crt_cde;
	}

	public void setCrt_cde(String crt_cde) {
		this.crt_cde = crt_cde;
	}

	public String getUpd_cde() {
		return upd_cde;
	}

	public void setUpd_cde(String upd_cde) {
		this.upd_cde = upd_cde;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getWeChatId() {
		return weChatId;
	}

	public void setWeChatId(String weChatId) {
		this.weChatId = weChatId;
	}
	
	public String getWeChatType() {
		return weChatType;
	}

	public void setWeChatType(String weChatType) {
		this.weChatType = weChatType;
	}
	
	
	public List<InsuranceDTO> getChosen() {
		return chosen;
	}

	public void setChosen(List<InsuranceDTO> chosen) {
		this.chosen = chosen;
	}
	
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getRelevance() {
		return relevance;
	}

	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}

	public String getAgtTeamName() {
		return agtTeamName;
	}

	public void setAgtTeamName(String agtTeamName) {
		this.agtTeamName = agtTeamName;
	}

	@Override
	public String toString() {
		return "MicroDTO [micro_id=" + micro_id + ", user_id=" + user_id
				+ ", chosen=" + chosen + ", crt_cde=" + crt_cde + ", upd_cde="
				+ upd_cde + ", invitation=" + invitation + ", relevance="
				+ relevance + ", agent_id="
				+ agent_id + ", agency=" + agency + ", agt_team_id="
				+ agt_team_id + ", agtTeamName=" + agtTeamName + ", weChatId="
				+ weChatId + ", weChatType=" + weChatType + ", micro_name="
				+ micro_name + ", user=" + user + ", tel=" + tel
				+ ", micro_class=" + micro_class + ", status=" + status
				+ ", rote=" + rote + ", duty=" + duty + ", appId=" + appId
				+ ", email=" + email + "]";
	}

	
	
}
