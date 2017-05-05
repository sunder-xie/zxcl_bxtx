package com.zxcl.webapp.biz.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meyacom.fw.app.dto.PagingResult;
import com.zxcl.webapp.biz.exception.BusinessServiceException;
import com.zxcl.webapp.biz.service.VehicleModelService;
import com.zxcl.webapp.dto.rmi.intf.vehicle.resp.VehicleModelDTO;
import com.zxcl.webapp.integration.dao.VehicleModelDAO;

@Service
public class VehicleModelServiceImpl implements VehicleModelService {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private VehicleModelDAO vehicleDao;

	@Override
	public void insert(List<VehicleModelDTO> vehicles, String insId)
			throws BusinessServiceException {
		logger.info("插入保险公司:" + insId + "的车辆信息");
		logger.info("入参    List<VehicleModelDTO>："+vehicles+" 保险公司ID："+insId);
		// 先排除自己重复的
		for (int i = 0; i < vehicles.size() - 1; i++) {
			for (int j = vehicles.size() - 1; j > i; j--) {
				if (vehicles.get(j).getModelCode().equals(vehicles.get(i).getModelCode())) {
					vehicles.remove(vehicles.get(i));
				}
			}
		}
		try {
			List<VehicleModelDTO> vehiclesDB = vehicleDao.getAll(insId);
			vehicles.removeAll(vehiclesDB);
			
			if (vehicles.size() > 0) {
				vehicleDao.insert(vehicles);
			}
		} catch (Exception e) {
			logger.error("插入保险公司:" + insId + "的车辆信息失败:" + e,e);
			throw new BusinessServiceException("插入保险公司:" + insId + "的车辆信息失败");
		}

	}

	@Override
	public PagingResult<VehicleModelDTO> getAll(String insId) throws BusinessServiceException {
		logger.info("得到所有的车辆信息 入参    保险公司ID："+insId);
		PagingResult<VehicleModelDTO> vehicleModelDTOs = null;
		try {
			vehicleModelDTOs = vehicleDao.getAll(insId);
		} catch (Exception e) {
			logger.error("获取保险公司:" + insId + "所有的车辆信息失败:" + e,e);
			throw new BusinessServiceException("得到所有的车辆信息失败");
		}
		return vehicleModelDTOs;
	}

	@Override
	public VehicleModelDTO getConfigDesc(VehicleModelDTO vehicle) throws ParseException {
		logger.info("组织车型描述和车的配置信息 入参    VehicleModelDTO："+vehicle);
		String config = "";
//		if (StringUtils.isNotBlank(vehicle.getVehicleAlias())) {
//			config += vehicle.getVehicleAlias() + " ";
//		}
		if (StringUtils.isNotBlank(vehicle.getMarketaDate())) {
			config += /*DateUtil.getYear(DateUtil.stringToDate(DateUtil.YYYYMMDD,
					vehicle.getMarketaDate()))*/
					vehicle.getMarketaDate().subSequence(0, 4)
					+ "款 ";
		}else if(StringUtils.isNotBlank(vehicle.getRemark())){
			int count = vehicle.getRemark().indexOf("款 ");
			if(count > 0){				
				config = vehicle.getRemark().substring(count-4, count)+"款 ";
			}
		}
		if (null != vehicle.getDisplacement()) {
			config += vehicle.getDisplacement().setScale(1, BigDecimal.ROUND_HALF_UP) + "L ";
		}
		if (null != vehicle.getSeatNum()) {
			config += vehicle.getSeatNum() + "座 ";
		}
		if (null != vehicle.getVehiclePrice()) {
			config += vehicle.getVehiclePrice() + "元 ";
		}
		vehicle.setConfigDesc(config);
		return vehicle;
	}
}
