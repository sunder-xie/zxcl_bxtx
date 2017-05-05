package com.zxcl.webapp.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meyacom.fw.app.web.controller.BaseController;
import com.zxcl.file_server.client.FileServiceClient;
import com.zxcl.file_server.client.dto.UploadResult;
import com.zxcl.webapp.biz.service.IUploadFileService;
import com.zxcl.webapp.biz.service.InquiryFileRequireService;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.MicroService;
import com.zxcl.webapp.biz.service.UserService;
import com.zxcl.webapp.dto.InquiryFileDTO;
import com.zxcl.webapp.dto.InquiryFileRequireDTO;
import com.zxcl.webapp.integration.dao.InquiryFileDAO;
import com.zxcl.webapp.web.util.Base64ToImage;
import com.zxcl.webapp.web.vo.AjaxResult;

/**
 * 询价单附件
 * @author zxj
 * @date 2016年8月4日
 * @description 
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController{
	
	private Logger logger = Logger.getLogger(UploadController.class);
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private InquiryFileDAO inquiryFileDAO;
	
	@Autowired
	private MicroService microService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	@Autowired
	private FileServiceClient fileServiceClient;
	
	@Autowired
	private InquiryFileRequireService inquiryFileRequireService;
	
	@Value("${fileserverUrl}")
	private String fileServerUrl;
	
	@Value("${share.url}")
    private String currentServerUrl;
	
	@RequestMapping(value="inquiry_index.do")
	public String inquiryIndex(HttpServletRequest request, HttpServletResponse response, String inquiryId, Model model) throws IOException{
		logger.info("inquiryUpload.do ==> inquiryId="+inquiryId);
		model.addAttribute("baseDomain", currentServerUrl);
		model.addAttribute("inquiryId", inquiryId);
		return "upload.inquiry";
	}
	
	@RequestMapping(value="inquiry_file_add.do")
	@ResponseBody
	public AjaxResult inquiryFileAdd(HttpServletRequest request, HttpServletResponse response, Model model, String inquiryId, String fileId) throws IOException{
		logger.info("inquiryFileAdd.do ==> inquiryId="+inquiryId+",fileId="+fileId);
		AjaxResult r = new AjaxResult();
		
		//基本校验
		if(StringUtils.isBlank(inquiryId)){
			r.setMsg("询价单必传");
			r.setSucc(false);
			return r;
		}
		if(StringUtils.isBlank(fileId)){
			r.setMsg("文件ID必传");
			r.setSucc(false);
			return r;
		}
		
		try {
			
			//校验询价单是否存在
			if(null == inquiryService.get(inquiryId, microService.getMicroByUserId(this.getAuthenticatedUserId()).getMicro_id())){
				r.setMsg("询价单["+inquiryId+"]不存在");
				r.setSucc(false);
				return r;
			}
			
			//存库
			InquiryFileDTO f = new InquiryFileDTO();
			f.setCrtBy(this.getAuthenticatedUserId());
			f.setFileId(fileId);
			f.setInquiryId(inquiryId);
			f.setStatus(1);
			f.setRemark(null);
			f.setUpdBy(f.getCrtBy());
			inquiryFileDAO.insertSelective(f);
			
			r.setMsg("上传成功");
			r.setSucc(true);
		} catch (Exception e) {
			logger.error("上传失败", e);
			r.setMsg("上传失败");
			r.setSucc(false);
		}
		return r;
	}
	
	@RequestMapping(value="inquiry_file_add2.do")
	@ResponseBody
	public AjaxResult inquiryFileAdd(HttpServletRequest request, HttpServletResponse response, Model model,
			String inquiryId, String name,String image,String insId) throws IOException{
		logger.info("inquiryFileAdd.do ==> inquiryId="+inquiryId );
		AjaxResult r = new AjaxResult();
		
		//基本校验
		if(StringUtils.isBlank(inquiryId)){
			r.setMsg("询价单必传");
			r.setSucc(false);
			return r;
		}
		if(StringUtils.isBlank(image) || StringUtils.isBlank(name)){
			r.setMsg("文件及文件类型必传");
			r.setSucc(false);
			return r;
		}
		
		try {
			
			//校验询价单是否存在
			if(null == inquiryService.get(inquiryId, microService.getMicroByUserId(this.getAuthenticatedUserId()).getMicro_id())){
				r.setMsg("询价单["+inquiryId+"]不存在");
				r.setSucc(false);
				return r;
			}
			
			String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
			String path= request.getSession().getServletContext().getRealPath("/");
			File target = new File(path + "/WEB-INF/tmp/upload_videomaterial" + fileName );
			Base64ToImage.base64ToImage(image,
					target);
			FileInputStream in =  new FileInputStream(target);
			
			UploadResult uploadResult = fileServiceClient.upload(fileName, in.available(),in);
			in.close();
			target.delete();
			
			if(uploadResult == null ){
				r.setMsg("附件上传失败");
				r.setSucc(false);
				return r;
			}
			
			//存库
			InquiryFileDTO f = new InquiryFileDTO();
			f.setCrtBy(this.getAuthenticatedUserId());
			f.setFileId(uploadResult.getFileId());
			f.setInquiryId(inquiryId);
			f.setStatus(1);
			f.setRemark(null);
			f.setUpdBy(f.getCrtBy());
			inquiryFileDAO.insertSelective(f);
			
			r.setMsg("上传成功");
			r.setSucc(true);
			r.setData(fileServerUrl + uploadResult.getFileId());
			
			if(StringUtils.equals("ACIC", insId)){
				InquiryFileRequireDTO inquiryFileRequireDTO = new InquiryFileRequireDTO();
				inquiryFileRequireDTO.setInquiryId(inquiryId);
				inquiryFileRequireDTO.setInsId(insId);
				inquiryFileRequireDTO.setFileType(name);
				inquiryFileRequireDTO.setFileId(uploadResult.getFileId());
				inquiryFileRequireDTO.setStatus(0);
				inquiryFileRequireDTO.setCrtBy(getAuthenticatedUserId());
				inquiryFileRequireDTO.setCrtTm(new Date());
				inquiryFileRequireDTO.setUpdBy(getAuthenticatedUserId());
				inquiryFileRequireDTO.setUpdTm(new Date());
				
				inquiryFileRequireService.updateInquiryFileRequires(inquiryFileRequireDTO);
			}
		} catch (Exception e) {
			logger.error("上传失败", e);
			r.setMsg("上传失败");
			r.setSucc(false);
		}
		return r;
	}
	
	@RequestMapping(value="inquiry_file_del.do")
	@ResponseBody
	public AjaxResult inquiryFileDel(HttpServletRequest request, HttpServletResponse response, Model model, String fileId) throws IOException{
		logger.info("inquiryUpload.do ==> fileId="+fileId);
		AjaxResult r = new AjaxResult();
		
		//基本校验
		if(StringUtils.isBlank(fileId)){
			r.setMsg("文件ID必传");
			r.setSucc(false);
			return r;
		}
		
		try {
			
			//del
			InquiryFileDTO f = inquiryFileDAO.getByFileId(fileId);
			if(null == f || !this.getAuthenticatedUserId().equals(f.getCrtBy())){
				r.setMsg("文件不存在");
				r.setSucc(false);
				return r;
			}
			inquiryFileDAO.delAsUpdateByFileId(fileId);
			
			r.setMsg("删除成功");
			r.setSucc(true);
		} catch (Exception e) {
			logger.error("删除失败", e);
			r.setMsg("删除失败");
			r.setSucc(false);
		}
		return r;
	}
	
	@RequestMapping(value="inquiry_file_list.do")
	@ResponseBody
	public AjaxResult inquiryFileList(HttpServletRequest request, HttpServletResponse response, Model model, String inquiryId) throws IOException{
		logger.info("inquiryFileList.do ==> inquiryId="+inquiryId);
		AjaxResult r = new AjaxResult();
		
		//基本校验
		if(StringUtils.isBlank(inquiryId)){
			r.setMsg("询价单必传");
			r.setSucc(false);
			return r;
		}
		
		try {
			
			//getList
			List<InquiryFileDTO> list = inquiryFileDAO.getListByInquiryId(inquiryId);
			r.setData(list);
			r.setMsg("查询成功");
			r.setSucc(true);
		} catch (Exception e) {
			logger.error("查询失败", e);
			r.setMsg("查询失败");
			r.setSucc(false);
		}
		return r;
	}
}

