package com.zxcl.webapp.dto;

/**
 * 短信监控配置人信息
 * @author zx
 *
 */
public class SmsAlarmConfigDTO {
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 告警短信类型
	 */
	private String warningSignalType;
	
	/**
	 * 接收人
	 */
	private String receiver;
	
	/**
	 * 接收人电话
	 */
	private String receiverPhone;
	
	/**
	 * 创建人
	 */
	private String crtCde;
	
	/**
	 * 修改人
	 */
	private String updCde;
	
	/**
	 * 保险公司ID
	 */
	private String insId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWarningSignalType() {
		return warningSignalType;
	}

	public void setWarningSignalType(String warningSignalType) {
		this.warningSignalType = warningSignalType;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(String crtCde) {
		this.crtCde = crtCde;
	}

	public String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(String updCde) {
		this.updCde = updCde;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	@Override
	public String toString() {
		return "SmsAlarmConfigDTO [id=" + id + ", warningSignalType="
				+ warningSignalType + ", receiver=" + receiver
				+ ", receiverPhone=" + receiverPhone + ", crtCde=" + crtCde
				+ ", updCde=" + updCde + ", insId=" + insId + "]";
	}
}
