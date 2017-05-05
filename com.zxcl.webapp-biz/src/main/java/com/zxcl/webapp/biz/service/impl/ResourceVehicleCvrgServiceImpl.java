package com.zxcl.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.InquiryService;
import com.zxcl.webapp.biz.service.ResourceVehicleCvrgService;
import com.zxcl.webapp.dto.ResourceVehicleCvrgDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.CoverageItemDTO;
import com.zxcl.webapp.integration.dao.CoverageItemDAO;
import com.zxcl.webapp.integration.dao.InquiryDAO;
import com.zxcl.webapp.integration.dao.ResourceVehicleCvrgDAO;

@Service
public class ResourceVehicleCvrgServiceImpl implements ResourceVehicleCvrgService{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ResourceVehicleCvrgDAO resourceVehicleCvrgDAO;
	
	@Autowired
	private InquiryDAO inquiryDAO;
	
	@Autowired
	private CoverageItemDAO coverageItemDAO;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Override
	public void insert(ResourceVehicleCvrgDTO resourceVehicleCvrgDTO)
			throws BusinessServiceException {
		logger.info("添加险别资源信息 入参    resourceVehicleCvrgDTO："+resourceVehicleCvrgDTO);
		try {
			resourceVehicleCvrgDAO.insert(resourceVehicleCvrgDTO);
		} catch (Exception e) {
			logger.error("添加险别资源信息失败",e);
			throw new BusinessServiceException("添加险别资源信息失败");
		}
	}

	@Override
	public List<ResourceVehicleCvrgDTO> getInfoByPlateNo(String plateNo)
			throws BusinessServiceException {
		logger.info("根据车牌号获取险别资源信息 入参        plateNo："+plateNo);
		List<ResourceVehicleCvrgDTO> resourceVehicleCvrgList = new ArrayList<ResourceVehicleCvrgDTO>();
		try {
			resourceVehicleCvrgList = resourceVehicleCvrgDAO.getInfoByPlateNo(plateNo);
		} catch (Exception e) {
			logger.error("根据车牌号获取险别资源信息失败",e);
			throw new BusinessServiceException("根据车牌号获取险别资源信息失败");
		}
		return resourceVehicleCvrgList;
	}

	@Override
	public void delete(String plateNo) throws BusinessServiceException {
		logger.info("根据车牌号删除 入参   plateNo："+plateNo);
		try {
			resourceVehicleCvrgDAO.delete(plateNo);
		} catch (Exception e) {
			logger.error("根据车牌号删除失败",e);
			throw new BusinessServiceException("根据车牌号删除失败");
		}
	}

	@Override
	public void insert(String inquiryId) throws BusinessServiceException {
		logger.info("根据询价单号，查询出需要添加的信息并添加险别资源信息 入参    inquiryId： "+inquiryId);
		try {
			logger.info("根据询价单获取险别信息");
			List<CoverageItemDTO> coverageItemList =coverageItemDAO.getCoverageItems2(inquiryId);
			logger.info("根据询价单获取车牌号");
			String plateNo = inquiryDAO.getPlateNoByInquiryId(inquiryId);
			if(StringUtils.isNotBlank(plateNo) && plateNo.length() == 7){				
				logger.info("根据车牌号删除信息");
				this.delete(plateNo);
//			InquiryDTO inquiryDTO = inquiryService.getInquiryVehicleByInquiryId(inquiryId);
				/*if("1".equals(inquiryDTO.getTciSign())){//投交强险
				ResourceVehicleCvrgDTO resourceVehicleCvrgDTO = new ResourceVehicleCvrgDTO();
				resourceVehicleCvrgDTO.setPlateNo(plateNo);
				resourceVehicleCvrgDTO.setCvrgId("0320");
				this.insert(resourceVehicleCvrgDTO);
			}
			if("1".equals(inquiryDTO.getVciSign())){//投商业险
				ResourceVehicleCvrgDTO resourceVehicleCvrgDTO = new ResourceVehicleCvrgDTO();
				resourceVehicleCvrgDTO.setPlateNo(plateNo);
				resourceVehicleCvrgDTO.setCvrgId("0326");
				this.insert(resourceVehicleCvrgDTO);
			}*/
				for (CoverageItemDTO coverageItemDTO : coverageItemList) {
					ResourceVehicleCvrgDTO resourceVehicleCvrgDTO = new ResourceVehicleCvrgDTO();
					resourceVehicleCvrgDTO.setPlateNo(plateNo);
					resourceVehicleCvrgDTO.setCvrgId(coverageItemDTO.getCode());
					resourceVehicleCvrgDTO.setGvrgAmount(coverageItemDTO.getSumLimit());
					resourceVehicleCvrgDTO.setExcldDeductible(String.valueOf(coverageItemDTO.getNoDduct()));
					resourceVehicleCvrgDTO.setGlsType(coverageItemDTO.getGlsType());
					this.insert(resourceVehicleCvrgDTO);
				}
			}
		} catch (Exception e) {
			logger.error("根据询价单号，查询出需要添加的信息并添加险别资源信息失败",e);
			throw new BusinessServiceException("根据询价单号，查询出需要添加的信息并添加险别资源信息失败");
		}
	}
}
