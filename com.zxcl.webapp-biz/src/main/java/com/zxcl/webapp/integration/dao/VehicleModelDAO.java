package com.zxcl.webapp.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meyacom.fw.app.dto.PagingResult;
import com.zxcl.webapp.dto.rmi.intf.vehicle.resp.VehicleModelDTO;

public interface VehicleModelDAO {

	/**
	 * 插入车型信息
	 * 
	 * @param vehicles
	 */
	public void insert(@Param("vehicles")List<VehicleModelDTO> vehicles) throws Exception;

	/**
	 * 获取所有的
	 * @param insId 
	 * @return
	 */
	public PagingResult<VehicleModelDTO> getAll(String insId) throws Exception;
}
