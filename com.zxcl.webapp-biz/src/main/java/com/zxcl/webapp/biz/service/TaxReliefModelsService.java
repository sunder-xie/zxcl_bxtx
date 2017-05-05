package com.zxcl.webapp.biz.service;

import java.util.Date;

import com.zxcl.webapp.dto.TaxReliefModelsDTO;

/**
 * 减免税车型
 * @author xiaoxi
 *
 */
public interface TaxReliefModelsService {

	/**
	 * 根据车型和初登日期获取减免税对象 
	 * @param modelNo   
	 * @param debut
	 * @param vehicleName 车名全称 ，目前通过车名里面的编码来查找是否减免税，通过车型号无法查询的时候，将通过名称来判断是否是特殊车型.
	 * @return 减免税对象
	 */
	public TaxReliefModelsDTO getTaxReliModelByModelNOAndDebut(String modelNo,Date fstRegTime,
			String vehicleName);
}
