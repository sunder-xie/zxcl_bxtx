package com.zxcl.webapp.dto;



/**
 * 附件上传
 * @author daitao
 *
 */
public class FileDTO {
	/**
	 * 询价单号
	 */
	private String inquiryId;
	/**
	 * 附件id
	 */
	private String fileId;
	/**
	 * 附件保存地址
	 */
	private String filePath;
	
	private String newFilePath;
	/**
	 * 附件类型()
	 */
	private String fileType;
	/**
	 * 附件名字
	 */
	private String fileName;
	/**
	 * 附件状态
	 */
	private String status;
	private FileDTO fileDTO;
	public String getInquiryId() {
		return inquiryId;
	}
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNewFilePath() {
		return newFilePath;
	}
	public void setNewFilePath(String newFilePath) {
		this.newFilePath = newFilePath;
	}
	public String getFilePath() {
		return filePath;
	}
	@Override
	public String toString() {
		return "FileDTO [nquiryId=" + inquiryId
				+ ", fileId=" + fileId + ", filePath=" + filePath
				+ ", newFilePath=" + newFilePath + ", fileType=" + fileType
				+ ", fileName=" + fileName + ", status=" + status
				+ ", fileDTO=" + fileDTO + "]";
	}
	
}	
