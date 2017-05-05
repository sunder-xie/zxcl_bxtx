package com.zxcl.webapp.biz.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.meyacom.fw.um.security.SecurityHolder;
import com.zxcl.webapp.biz.service.IUploadFileService;
import com.zxcl.webapp.dto.FileUploadDTO;
import com.zxcl.webapp.integration.dao.FileUploadDAO;

@Service
public class LocalUploadFileServiceImpl implements IUploadFileService{

	@Autowired
	private FileUploadDAO fileUploadDAO;
	
	@Autowired
	protected SecurityHolder securityHolder;
	
	private static Calendar c = Calendar.getInstance();
	
	@Value(value="${imageServerUrl}")
	private String serverUrl;
	
	@Value(value="${imageVisitUrl}")
	private String visitUrl;
	
	private static Logger logger = Logger.getLogger(LocalUploadFileServiceImpl.class);
	
	@Override
	public String uploadFile(File file, String fileSuffixName) {
		try {
			return uploadFile(new FileInputStream(file),fileSuffixName,file.length());
		} catch (FileNotFoundException e) {
			logger.error("上传文件失败", e);
			return null;
		}
	}

	@Override
	public String uploadFile(InputStream inputStream, String fileSuffixName,Long fileSize) {
		Date now = new Date();
		c.setTime(now);
		
		String fileId = UUID.randomUUID().toString().replaceAll("-", "");
		String dir = "/" + c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) +1)  + "/";
		String fileName = fileId + fileSuffixName;
		copyTo(inputStream,serverUrl + dir + fileName);
		
		FileUploadDTO fileUploadDTO = new FileUploadDTO();
		fileUploadDTO.setCrtCde(securityHolder.getAuthenticatedUserId());
		fileUploadDTO.setCrtTm(new Date());
		fileUploadDTO.setUpdCde(securityHolder.getAuthenticatedUserId());
		fileUploadDTO.setUpdTm(new Date());
		fileUploadDTO.setFileExt(fileSuffixName.replace(".", ""));
		fileUploadDTO.setFileSize(fileSize.intValue()); //一般不会超过2G，所以暂时先这样
		fileUploadDTO.setFileType("1");
		fileUploadDTO.setPath(serverUrl + dir + fileName);
		fileUploadDTO.setSystemName("BXTX_MANAGE");
		fileUploadDTO.setFileId(fileId);
		fileUploadDAO.insertFileUpload(fileUploadDTO);
		String url = visitUrl.replace("{fileId}", fileId);
		
		return url;
	}

	@Override
	public File[] getDirectoryFiles(String url) {
		
		//不允许使用..移动到上一级目录
		if (url.indexOf("..") >= 0) {
			logger.info("Access is not allowed.");
			return null;
		}
		//最后一个字符不是/
		if (!"".equals(url) && !url.endsWith("/")) {
			logger.info("Parameter is not valid.");
			return null;
		}
		//目录不存在或不是目录
		File currentPathFile = new File(serverUrl + url);
		if(!currentPathFile.isDirectory()){
			logger.info("Directory does not exist.");
			return null;
		}
		
		return currentPathFile.listFiles();
	}

	
	private void copyTo(InputStream inputStream, String remoteUrl){
		
		try {
			File file = new File(remoteUrl);
			File parentDic = new File(file.getParent());
			OutputStream fileOutputStream = null;
	        // create remote folder if not exist
	        if (!parentDic.exists())
	        	parentDic.mkdirs();
	        // create remote file
	        if (!file.exists())
	        	file.createNewFile();
	      //  file.setReadWrite();
	        byte[] buf;
	        int len;
	        try {
	            fileOutputStream = new FileOutputStream(file);

	            buf = new byte[16 * 1024 * 1024];
	            while ((len = inputStream.read(buf)) > 0) {
	                fileOutputStream.write(buf, 0, len);
	            }
	        } catch (Exception e) {
	        	logger.error("上传文件失败", e);
	        } finally {
	        	inputStream.close();
	            fileOutputStream.close();
	            logger.info("Upload " + remoteUrl + ", done");
	        }
		} catch (Exception e) {
			logger.error("上传文件失败", e);
		}
		
	}
	
	@Override
	public InputStream getFileInputStream(String visitUrl) {
		
		FileUploadDTO fileUploadDTO = fileUploadDAO.getFileUploadByFileId(visitUrl);
		if(fileUploadDTO == null){
			return null;
		}
		
		try {
			return new FileInputStream(new File(fileUploadDTO.getPath()));
		} catch (FileNotFoundException e) {
			logger.error("读取文件流失败",e);
		}
		
		return null;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}


	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}
	
}
