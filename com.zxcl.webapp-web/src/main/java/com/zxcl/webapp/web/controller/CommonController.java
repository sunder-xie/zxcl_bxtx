package com.zxcl.webapp.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxcl.file_server.client.FileServiceClient;
import com.zxcl.file_server.client.dto.UploadResult;
import com.zxcl.webapp.biz.service.IUploadFileService;
import com.zxcl.webapp.web.util.Base64ToImage;

@Controller
@RequestMapping
public class CommonController {

	private static Logger logger = Logger.getLogger(CommonController.class);
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	@RequestMapping("/fileserver.do")
	public void fileserver(HttpServletRequest req,HttpServletResponse rep,String fileId){
		
		if(StringUtils.isEmpty(fileId)) {
            return ;
        }
		
		InputStream is = uploadFileService.getFileInputStream(fileId);
		if(is == null ){
			return ;
		}
		
		try{
			byte[] data = new byte[is.available()];
			is.read(data);
			rep.getOutputStream().write(data);
			rep.getOutputStream().flush();
		}catch (Exception e){
			logger.error("通过response输出文件流失败",e);
		}finally{
			if (is != null) {
	            try {
	               is.close();
	            } catch (IOException e) {
	            	logger.error("文件流close失败", e);
	            }
	        }   
		}
		
	}
	
	
}
