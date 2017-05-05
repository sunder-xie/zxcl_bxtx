package com.zxcl.webapp.biz.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.DriverDTO;

/**
 * 驾驶员Service
 * 
 * @author 5555
 *
 */
public interface DriverService {

	/**
	 * 插入信息
	 * 
	 * @param driver
	 * @throws BusinessServiceException
	 */
	public void insert(List<DriverDTO> driver) throws BusinessServiceException;

	/**
	 * 根据询价单号查询信息总条数
	 * 
	 * @throws BusinessServiceException
	 */
	public int getCount(String inquiryId) throws BusinessServiceException;

	/**
	 * 根据询价单号查询驾驶员
	 * 
	 * @param inquiryId
	 * @return
	 */
	// public List<DriverDTO> getDriverByInquiryId(String inquiryId);

	/**
	 * 组织驾驶员信息:插入数据库
	 * @throws Exception 
	 */
	public List<DriverDTO> organizeDriversForInsert(String userId,
			String inquiryId, List<DriverDTO> drivers,Date vciStartDate) throws ParseException,
			BusinessServiceException;

	/**
	 * 获取驾驶员信息
	 */
	public List<DriverDTO> getDrivers(String inquiryId, String microId)
			throws BusinessServiceException;

	/**
	 * 组织保险公司:组织保险数据
	 */
	public DriverDTO organizeDriverForIns(DriverDTO driver,Date vciStartDate)
			throws ParseException;

	/**
	 * 根据询价单号删除驾驶员信息
	 * 
	 * @throws BusinessServiceException
	 */
	public void deleteDriverByInquiryId(String inquiryId)
			throws BusinessServiceException;
}
