package com.zxcl.webapp.integration.dao;

import org.apache.ibatis.annotations.Param;


import com.zxcl.webapp.dto.bizdto.ResourceVehicleDTO;

/**
 * 
* @ClassName:  ResourceVehicleDAO 
* @Description: 车辆信息DAO
* @author 赵晋
* @date 2015年11月18日 上午10:39:16
*
 */
public interface ResourceVehicleDAO {

	/**
	 * 
	* @Title: getResVehicleByPlateNoDAO
	* @Description: 根据车牌号查询车辆信息
	* @param plateNo 车牌号
	* @throws Exception
	* @return ResourceVehicleDTO
	* @throws
	 */
	public ResourceVehicleDTO getResVehicleByPlateNoDAO(@Param("plateNo") String plateNo) throws Exception;

	/**
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public ResourceVehicleDTO getResVehicleByModelName(String modelName) throws Exception;
	
	/**
	 * 添加资源信息
	 * @param resourceVehicleDTO 资源信息DTO
	 * @throws Exception
	 */
	public void insert(ResourceVehicleDTO resourceVehicleDTO) throws Exception;
	
	/**
	 * 根据车牌号删除
	 * @param plateNo
	 * @throws Exception
	 */
	public void delete(String plateNo) throws Exception;
	
	/**
	 * 根据车牌号返回条数
	 * @param plateNo
	 * @return
	 * @throws Exception
	 */
	public Integer getByPlateNo(String plateNo) throws Exception;
}
