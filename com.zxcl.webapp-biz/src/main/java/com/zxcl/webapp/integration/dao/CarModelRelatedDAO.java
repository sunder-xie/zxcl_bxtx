package com.zxcl.webapp.integration.dao;

import com.zxcl.webapp.dto.CarModelRelatedDTO;

/**
 * 精友车型编码关联DAO
 * @author xiaoxi
 *
 */
public interface CarModelRelatedDAO {

	/**
	 * 获取已关联的车型编码数据.
	 * @param carModelRelatedDTO
	 * @return
	 */
	public CarModelRelatedDTO getCarModelRelated(CarModelRelatedDTO carModelRelatedDTO);
	
}
