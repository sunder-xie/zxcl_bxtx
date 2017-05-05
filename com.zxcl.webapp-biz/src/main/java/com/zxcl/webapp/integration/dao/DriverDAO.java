package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.zxcl.webapp.dto.DriverDTO;

/**
 * 驾驶员信息
 * 
 * @author 5555
 *
 */
public interface DriverDAO {

	/**
	 * 添加驾驶员信息
	 */
	public void insert(@Param("drivers") List<DriverDTO> driver)
			throws Exception;

	/**
	 * 根据询价单号查询信息总条数
	 */
	public int getCount(String inquiryId) throws Exception;

	/**
	 * 根据询价单号查询驾驶员
	 * 
	 * @param inquiryId
	 * @return
	 */
	public List<DriverDTO> getDriverByInquiryId(String inquiryId)
			throws Exception;

	/**
	 * 查询询价单的驾驶员信息
	 * 
	 * @param inquiryId
	 * @param microId
	 * @return
	 */
	public List<DriverDTO> getDrivers(@Param("inquiryId") String inquiryId,
			@Param("microId") String microId) throws Exception;

	/**
	 * 根据询价单编码删除驾驶员信息
	 * 
	 * @param inquiryId
	 * @return
	 */
	public void deleteDriverByInquiryId(String inquiryId) throws Exception;
}
