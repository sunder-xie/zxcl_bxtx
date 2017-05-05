package com.zxcl.webapp.dto.port.comdto;

import org.apache.commons.lang.StringUtils;

/**
 * 认证
 * @author zx
 *
 */
public class AttestationDTO {
	
	public static final String FACP_PRE_USERNAME = "FDCP_USERNAME";
	public static final String FACP_PRE_PASSWORD = "FDCP_PASSWORD";
	public static final String FACP_PRE_RK_SY = "FDCP_RK_SY";
	public static final String FACP_PRE_RK_JQ = "FDCP_RK_JQ";
	
	/**
	 * 锦泰：appId
	 */
	private String appId;
	
	/**
	 * 富德：交强产品代码
	 */
	private String riskCodeJQ ;
	
	/**
	 * 富德：商业产品代码
	 */
	private String riskCodeSY ;
	
	/**
	 * 锦泰：method
	 * 天平：枚举（RequestType）
	 * 华泰：接口代码（REQCODE）
	 */
	private String method;
	
	/**
	 * 锦泰：模板（为了方便后面操作加的字段）
	 */
	private String model;
	
	/**
	 * 锦泰：流水号
	 * 阳光：流水号（SVCSEQNO）
	 * 太平：流水号
	 */
	private String timestamp;
	
	/**
	 * 锦泰：sign
	 */
	private String sign;
	
	/**
	 * 阳光：交易类型
	 */
	private String transType;
	
	/**
	 * 阳光：主机交易号码
	 */
	private String transCode;
	
	/**
	 * 阳光：用户名
	 * 天平：用户名（User）
	 * 华泰：用户名（USERCODE）
	 * 华安：username
	 */
	private String user;
	
	/**
	 * 阳光：密码
	 * 天平：密码（Password）
	 * 华泰：密码（USERPWD）
	 * 华安：password
	 */
	private String password;
	
	/**
	 * 太平：msgId
	 */
	private String msgId;

	/**
	 * 太平：session
	 */
	private String session;
	
	/**
	 * 阳光：请求渠道
	 */
	private String requestTypeCode;
	
	/**
	 * 阳光：交易日期
	 */
	private String transexeDate;
	
	/**
	 * 阳光：交易时间
	 */
	private String transexeTime;
	
	/**
	 * 阳光：车辆所在城市代码（登陆机构代码）
	 * 华泰：归属机构（COMCODE）
	 */
	private String comCode;
	
	/**
	 * 阳光：交易端口号
	 */
	private String transNo;
	
	/**
	 * 华泰：代理人代码
	 */
	private String agentCode;
	
	/**
	 * 华泰：地区编码
	 */
	private String areaCode;
	
	/**
	 * 华泰：
	 */
	private String channel;
	
	/**
	 * 太平：团队号
	 */
	private String groupNo;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRequestTypeCode() {
		return requestTypeCode;
	}

	public void setRequestTypeCode(String requestTypeCode) {
		this.requestTypeCode = requestTypeCode;
	}

	public String getTransexeDate() {
		return transexeDate;
	}

	public void setTransexeDate(String transexeDate) {
		this.transexeDate = transexeDate;
	}

	public String getTransexeTime() {
		return transexeTime;
	}

	public void setTransexeTime(String transexeTime) {
		this.transexeTime = transexeTime;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public AttestationDTO() {
		super();
	}

	public AttestationDTO(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}

	public String getRiskCodeJQ() {
		if(StringUtils.isBlank(riskCodeJQ)){
			riskCodeSY = "0801";
		}
		return riskCodeJQ;
	}

	public void setRiskCodeJQ(String riskCodeJQ) {
		this.riskCodeJQ = riskCodeJQ;
	}

	public String getRiskCodeSY() {
		if(StringUtils.isBlank(riskCodeSY)){
			riskCodeSY = "0812";
		}
		return riskCodeSY;
	}

	public void setRiskCodeSY(String riskCodeSY) {
		this.riskCodeSY = riskCodeSY;
	}

}
