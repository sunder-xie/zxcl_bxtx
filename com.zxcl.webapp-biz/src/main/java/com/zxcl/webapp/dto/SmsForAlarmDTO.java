package com.zxcl.webapp.dto;

/**
 * 短信监控用户密码信息
 * @author zx
 *
 */
public class SmsForAlarmDTO {
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 保险公司ID
	 */
	private String insId;
	
	/**
	 * 用户名
	 */
	private String keyWord;
	
	/**
	 * 短信内容
	 */
	private String content;
	
	/**
	 * 短信时间
	 */
	private String noteTime;
	
	/**
	 * 短信类型
	 * 1、用户密码错误
	 */
	private String errorType;
	
	/**
	 * 创建人
	 */
	private String crtCde;
	
	/**
	 * 修改人
	 */
	private String updCde;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNoteTime() {
		return noteTime;
	}

	public void setNoteTime(String noteTime) {
		this.noteTime = noteTime;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
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

	@Override
	public String toString() {
		return "SmsForAlarmDTO [id=" + id + ", insId=" + insId + ", keyWord="
				+ keyWord + ", content=" + content + ", noteTime=" + noteTime
				+ ", errorType=" + errorType + ", crtCde=" + crtCde
				+ ", updCde=" + updCde + "]";
	}
	
}
