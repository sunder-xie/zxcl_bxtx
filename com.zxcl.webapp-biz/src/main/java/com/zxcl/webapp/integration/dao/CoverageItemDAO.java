package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;



import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;

/**
 * 询价的险种信息
 * 
 * @author 5555
 *
 */
public interface CoverageItemDAO {

	public void insert(@Param("covers") List<CoverageItemDTO> covers)
			throws Exception;

	/**
	 * 根据询价ID查询险别信息
	 * 
	 * @param inquiryId
	 * @return
	 */
	public List<CoverageItemDTO> getInquiryInsTypeByInquiryId(String inquiryId)
			throws Exception;

	public List<CoverageItemDTO> getCoverageItemByInquiryId(String inquiryId)
			throws Exception;;

	/**
	 * 查询询价单险别信息
	 * 
	 * @param inquiryId
	 * @param microId
	 * @return
	 */
	public List<CoverageItemDTO> getCoverageItems(
			@Param("inquiryId") String inquiryId,
			@Param("microId") String microId) throws Exception;

	/**
	 * 更新询价单险别表
	 * 
	 * @param inquiryId
	 * @throws Exception
	 */
	public void deleteCoverageItemsByInquiryId(String inquiryId)
			throws Exception;
	
	/**
	 * 根据询价单号获取险种信息
	 * @param inquiryId 询价单号
	 * @return
	 * @throws Exception
	 */
	public List<CoverageItemDTO> getCoverageItems2(String inquiryId) throws Exception;
}