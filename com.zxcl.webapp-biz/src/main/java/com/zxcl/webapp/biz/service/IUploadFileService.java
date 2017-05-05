package com.zxcl.webapp.biz.service;

import java.io.File;
import java.io.InputStream;

/**
 * 文件上传接口
 * @author xiaoxi
 *
 */
public interface IUploadFileService {

	/**
	 * 根据File完成文件上传
	 * @param file
	 * @param fileSuffixName 文件后缀名称
	 * @return 目标文件地址
	 */
	public String uploadFile(File file,String fileSuffixName);
	
	/**
	 * 根据流完成文件上传
	 * @param inputStream
	 * @param fileSuffixName 文件后缀名称
	 * @param fileSize 文件大小
	 * @return 目标文件地址
	 */
	public String uploadFile(InputStream inputStream,String fileSuffixName,Long fileSize);
	
	/**
	 * 根据访问地址获取文件流
	 * @param visitUrl 访问地址
	 */
	public InputStream getFileInputStream(String visitUrl);
	
	/**
	 * 根据相对路径获取对应目录下的所有目录，文件信息.
	 * @param url
	 * @return
	 */
	public File[] getDirectoryFiles(String url);
}
