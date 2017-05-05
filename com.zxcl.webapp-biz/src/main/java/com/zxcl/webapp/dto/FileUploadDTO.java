package com.zxcl.webapp.dto;

import java.util.Date;

public class FileUploadDTO {

	/**
	 * 文件ID
	 */
	private String fileId;
	
	/**
	 * 文件存储路径
	 */
	private String path;
	
	/**
	 * 文件大小
	 */
	private Integer fileSize;
	
	/**
	 * 文件扩展名
	 */
	private String fileExt;
	
	/**
	 * 所属系统名称
	 */
	private String systemName;
	
	/**
	 * 文件类型，用于区分上传文件的类型在什么地方使用
	 */
	private String fileType;
	
	/**
	 * 创建人
	 */
	private String crtCde;
	
	/**
	 * 创建时间
	 */
	private Date crtTm;
	
	/**
	 * 更新人
	 */
	private String updCde;
	
	/**
	 * 更新时间
	 */
	private Date updTm;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(String crtCde) {
		this.crtCde = crtCde;
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
		this.updCde = updCde;
	}

	public Date getUpdTm() {
		return updTm;
	}

	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}
	
}
