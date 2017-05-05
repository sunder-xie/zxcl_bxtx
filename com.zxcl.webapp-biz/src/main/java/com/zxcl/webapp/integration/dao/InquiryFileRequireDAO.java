package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zxcl.webapp.dto.InquiryFileRequireDTO;

/**
 * @author xiaoxi
 * @date 2016年8月4日
 * @description 
 */
public interface InquiryFileRequireDAO {
    int insertInquiryFileRequire(InquiryFileRequireDTO record);

    int deleteInquiryFileRequire(@Param("inquiryId")String inquiryId,@Param("insId")String insId);

	List<InquiryFileRequireDTO> getListByInquiryId(@Param("inquiryId")String inquiryId,@Param("insId")String insId );
	
	public int updateInquiryFileRequires(InquiryFileRequireDTO inquiryFileRequireDTO);
}