package com.zxcl.webapp.integration.dao;

import java.util.List;


import com.zxcl.webapp.dto.ResourceVehicleCvrgDTO;

/**
 * 险别资源信息DAO 
 * @author zx
 *
 */
public interface ResourceVehicleCvrgDAO {
	
	/**
	 * 添加
	 * @param resourceVehicleCvrgDTO 险别资源信息
	 * @throws Exception
	 */
	public void insert(ResourceVehicleCvrgDTO resourceVehicleCvrgDTO) throws Exception;
	
	/**
	 * 根据车牌号获取
	 * @param plateNo 车牌号
	 * @return 
	 * @throws Exception
	 */
	public List<ResourceVehicleCvrgDTO> getInfoByPlateNo(String plateNo) throws Exception;
	
	/**
	 * 根据车牌号删除
	 * @param plateNo 车牌号
	 * @throws Exception
	 */
	public void delete(String plateNo) throws Exception;
}
