package com.zxcl.webapp.integration.dao;

import java.util.List;

import com.zxcl.webapp.dto.InquiryFileDTO;

/**
 * @author zxj
 * @date 2016年8月4日
 * @description 
 */
public interface InquiryFileDAO {
    int insertSelective(InquiryFileDTO record);

	InquiryFileDTO getByFileId(String fileId);

	int delAsUpdateByFileId(String fileId);

	List<InquiryFileDTO> getListByInquiryId(String inquiryId);
}