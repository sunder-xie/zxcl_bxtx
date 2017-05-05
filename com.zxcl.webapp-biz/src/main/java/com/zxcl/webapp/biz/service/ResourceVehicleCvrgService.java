package com.zxcl.webapp.biz.service;

import java.util.List;

import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.ResourceVehicleCvrgDTO;

/**
 * 险别资源信息Service
 * @author zx
 *
 */
public interface ResourceVehicleCvrgService {
	/**
	 * 添加
	 * @param resourceVehicleCvrgDTO 险别资源信息
	 * @throws Exception
	 */
	public void insert(ResourceVehicleCvrgDTO resourceVehicleCvrgDTO) throws BusinessServiceException;
	
	/**
	 * 根据车牌号获取
	 * @param plateNo 车牌号
	 * @return 
	 * @throws Exception
	 */
	public List<ResourceVehicleCvrgDTO> getInfoByPlateNo(String plateNo) throws BusinessServiceException;
	
	/**
	 * 根据车牌号删除
	 * @param plateNo 车牌号
	 * @throws Exception
	 */
	public void delete(String plateNo) throws BusinessServiceException;
	
	/**
	 * 根据询价单号，查询出需要添加的信息并添加
	 * @param inquiryId
	 * @throws Exception
	 */
	public void insert(String inquiryId) throws BusinessServiceException;
}
