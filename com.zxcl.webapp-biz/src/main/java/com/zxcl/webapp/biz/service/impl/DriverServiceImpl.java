package com.zxcl.webapp.biz.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;

import com.zxcl.webapp.biz.service.DriverService;
import com.zxcl.webapp.biz.util.CommonUtil;
import com.zxcl.webapp.biz.util.DateUtil;
import com.zxcl.webapp.dto.DriverDTO;
import com.zxcl.webapp.dto.InquiryDTO;
import com.zxcl.webapp.integration.dao.DriverDAO;
import com.zxcl.webapp.util.StringCodeUtil;

/**
 * 地区的Service的实现
 * 
 * @author 5555
 *
 */
@Service
public class DriverServiceImpl implements DriverService {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private DriverDAO driverDao;

	@Override
	public void insert(List<DriverDTO> driver) throws BusinessServiceException {
		logger.info("插入驾驶员信息 入参    List<DriverDTO>："+driver);
		try {
			if (driver.size() > 0) {
				driverDao.insert(driver);
			}
		} catch (Exception e) {
			logger.error("插入驾驶员信息失败:" + e,e);
			throw new BusinessServiceException("插入驾驶员信息失败");
		}

	}

	@Override
	public int getCount(String inquiryId) throws BusinessServiceException {
		logger.info("根据询价单号查询驾驶员信息总条数 入参    询价单号："+inquiryId);
		int count = 0;
		try {
			count = driverDao.getCount(inquiryId);
		} catch (Exception e) {
			logger.error("查询询价单:" + inquiryId + "的驾驶员总条数失败:" + e,e);
			throw new BusinessServiceException("根据询价单号查询驾驶员信息总条数失败");
		}
		return count;

	}

	// @Override
	// public List<DriverDTO> getDriverByInquiryId(String inquiryId) {
	// return driverDao.getDriverByInquiryId(inquiryId);
	// }

	public List<DriverDTO> organizeDriversForInsert(String userId,
			String inquiryId, List<DriverDTO> drivers,Date vciStartDate) throws ParseException,
			BusinessServiceException {
		logger.info("组织驾驶员信息 入参    用户ID："+userId+"  询价单号："+inquiryId+"  List<DriverDTO>："+drivers+"  vciStartDate："+vciStartDate);
		for (DriverDTO driver : drivers) {
			driver.setCrtCode(userId);
			driver.setUpdCode(userId);
			driver.setInquiry(new InquiryDTO(inquiryId));
			driver.setDriverId(String.valueOf(this.getCount(inquiryId) + 1));
			if(StringCodeUtil.isIdCard(driver.getCertNo())){
				String certNo = driver.getCertNo();
				driver.setSex(CommonUtil.getSex(certNo) % 2 == 0 ? '2' : '1');
				Date birth = CommonUtil.getBrith(certNo);
				driver.setBirthday(birth);
				driver.setAge(DateUtil.getYear(vciStartDate)- DateUtil.getYear(birth));
			}
			driver.setLicenseNo((driver.getCertNo().toUpperCase()));
		}
		return drivers;
	}

	public DriverDTO organizeDriverForIns(DriverDTO driver,Date vciStartDate)
			throws ParseException {
		logger.info("组织保险公司 入参    DriverDTO："+driver+"  vciStartDate:"+vciStartDate);
		String certNo = driver.getCertNo();
		driver.setSex(CommonUtil.getSex(certNo) % 2 == 0 ? '2' : '1');
		Date brith = CommonUtil.getBrith(certNo);
		driver.setBirthday(brith);
		driver.setAge(DateUtil.getYear(vciStartDate) - DateUtil.getYear(brith));
		driver.setLicenseNo((driver.getCertNo().toUpperCase()));
		return driver;
	}

	@Override
	public List<DriverDTO> getDrivers(String inquiryId, String microId)
			throws BusinessServiceException {
		logger.info("获取驾驶员信息 入参    询价单号："+inquiryId+"  小微ID："+microId);
		List<DriverDTO> list = new ArrayList<DriverDTO>();
		try {
			list = driverDao.getDrivers(inquiryId, microId);
		} catch (Exception e) {
			logger.error("查询询价单:" + inquiryId + "查询驾驶员信息失败:" + e,e);
			throw new BusinessServiceException("获取驾驶员信息失败");
		}
		return list;
	}

	@Override
	public void deleteDriverByInquiryId(String inquiryId)
			throws BusinessServiceException {
		logger.info("删除询价单信息 入参    询价单号："+inquiryId);
		try {
			driverDao.deleteDriverByInquiryId(inquiryId);
		} catch (Exception e) {
			logger.error("查询询价单:" + inquiryId + "查询驾驶员信息失败:" + e);
			throw new BusinessServiceException("删除询价单信息失败");
		}
	}
}
