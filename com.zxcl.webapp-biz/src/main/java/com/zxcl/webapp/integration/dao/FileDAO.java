package com.zxcl.webapp.integration.dao;

import java.util.List;


import com.zxcl.webapp.dto.FileDTO;

/**
 * 文件上传
 * @author lyl
 *
 */
public interface FileDAO {
	/**
	 * 根据询价单号拿到我们的图片信息
	 * @param inquiryid
	 * @return
	 */
	public List<FileDTO> getFileByinquiryId(String inquiryid) throws Exception;
	/**
	 * 保存附件
	 * @param f:附件
	 */
	public void saveFile(FileDTO f) throws Exception;
	/**
	 * 保存附件到子表
	 * @param f
	 */
	public void saveFileSub(FileDTO f) throws Exception;
}
