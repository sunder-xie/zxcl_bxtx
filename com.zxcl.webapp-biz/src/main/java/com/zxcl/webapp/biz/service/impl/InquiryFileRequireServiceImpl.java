package com.zxcl.webapp.biz.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.InquiryFileRequireService;
import com.zxcl.webapp.dto.InquiryFileRequireDTO;
import com.zxcl.webapp.integration.dao.InquiryFileRequireDAO;

@Service
public class InquiryFileRequireServiceImpl implements InquiryFileRequireService {

	
	@Autowired
	private InquiryFileRequireDAO inquiryFileRequireDAO;
	
	@Override
	public boolean isInquiryFileRequireUploaded(String inquiryId, String insId) {
		List<InquiryFileRequireDTO> ifrs = getInquiryFileRequires(inquiryId, insId);
		if(ifrs == null || ifrs.size() == 0){
			return true;
		}
		
		for(InquiryFileRequireDTO ifr : ifrs){
			if(StringUtils.isBlank(ifr.getFileId())){
				return false;
			}
		}
		
		
		return true;
	}

	@Override
	public List<InquiryFileRequireDTO> getInquiryFileRequires(String inquiryId,
			String insId) {
		return inquiryFileRequireDAO.getListByInquiryId(inquiryId,insId);
	}

	@Override
	public int updateInquiryFileRequires(
			InquiryFileRequireDTO inquiryFileRequireDTO) {

		return inquiryFileRequireDAO.updateInquiryFileRequires(inquiryFileRequireDTO);
	}
	
	

}
