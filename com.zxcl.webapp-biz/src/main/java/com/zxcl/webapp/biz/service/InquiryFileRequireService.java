package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.dto.InquiryFileRequireDTO;

public interface InquiryFileRequireService {

	public boolean isInquiryFileRequireUploaded(String inquiryId,String insId);
	
	public List<InquiryFileRequireDTO> getInquiryFileRequires(String inquiryId,String insId);
	
	public int updateInquiryFileRequires(InquiryFileRequireDTO inquiryFileRequireDTO);
}
