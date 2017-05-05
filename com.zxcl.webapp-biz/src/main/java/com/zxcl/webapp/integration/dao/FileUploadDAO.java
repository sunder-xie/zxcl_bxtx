package com.zxcl.webapp.integration.dao;

import com.zxcl.webapp.dto.FileUploadDTO;

/**
 * 文件上传的
 * @author xiaoxi
 *
 */
public interface FileUploadDAO {

	/**
	 * 添加文件上传记录
	 * @param fileUploadDTO
	 * @return
	 */
	public Integer insertFileUpload(FileUploadDTO fileUploadDTO);
	
	/**
	 * 根据file获取已上传的文件信息
	 * @param fileId
	 * @return
	 */
	public FileUploadDTO getFileUploadByFileId(String fileId);
}
