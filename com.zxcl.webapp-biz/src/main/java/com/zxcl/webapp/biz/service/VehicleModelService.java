package com.zxcl.webapp.biz.service;

import java.text.ParseException;
import java.util.List;

import com.meyacom.fw.app.dto.PagingResult;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.dto.rmi.intf.vehicle.resp.VehicleModelDTO;

public interface VehicleModelService {

	public void insert(List<VehicleModelDTO> vehicles, String insId)
			throws BusinessServiceException;

	public PagingResult<VehicleModelDTO> getAll(String insId) throws BusinessServiceException;

	/**
	 * 组织车型描述和车的配置信息
	 * @throws ParseException 
	 */
	public VehicleModelDTO getConfigDesc(VehicleModelDTO v) throws ParseException;
}
