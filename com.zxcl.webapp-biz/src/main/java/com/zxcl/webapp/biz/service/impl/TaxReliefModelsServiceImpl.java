package com.zxcl.webapp.biz.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.TaxReliefModelsService;
import com.zxcl.webapp.biz.util.DateUtils;
import com.zxcl.webapp.biz.util.PatternUtil;
import com.zxcl.webapp.dto.TaxReliefModelsDTO;
import com.zxcl.webapp.integration.dao.TaxReliefModelDAO;

@Service
public class TaxReliefModelsServiceImpl implements TaxReliefModelsService {

	private static Logger logger = Logger.getLogger(TaxReliefModelsServiceImpl.class);
	
	@Autowired
	private TaxReliefModelDAO taxReliefModelDAO;
	
	@Override
	public TaxReliefModelsDTO getTaxReliModelByModelNOAndDebut(String modelNo,
			Date fstRegTime,String vehicleName) {
		
		/**
		 * 备注：因目前提供的数据的车型号都是从车型名称里提取的。
		 * 所以我们这里实现也需要从车名里面提取数据.后面如果修改为modelCode,这里需要改改
		 * 
		 * 
		 * 1.先从数据查询符合车型的减免税对象。
		 * 2.判断车的初登日期是否符合该减免税的要求。
		 * 3.如果不符合返回null.符合返回减免税对象
		 * 
		 * 提示：当满足燃料电池、纯电动，就直接免税。
		 */
		
		if(StringUtils.isBlank(vehicleName)){
			return null;
		}
		
		//从车名中提取非中文的字符串.
		modelNo = PatternUtil.getInterceptionStr(vehicleName, "[0-9a-zA-Z]+");
		
		if(StringUtils.isBlank(modelNo)  || fstRegTime == null ){
			//车型和初登日期必传.
			return null;
		}
		
		TaxReliefModelsDTO dto = null;
		// 获取纯电动车和燃料电池车数据. 这两种特殊车型使用特殊车型号表示
		if(StringUtils.isNotBlank(vehicleName) && vehicleName.indexOf("纯电动") > -1 ){
			dto = taxReliefModelDAO.getTaxReliefModelsByModelNumber("TSCX-CDDCYC");  //纯电动乘用车
		}else if(StringUtils.isNotBlank(vehicleName) && vehicleName.indexOf("燃料电池") > -1){
			dto = taxReliefModelDAO.getTaxReliefModelsByModelNumber("TSCX-RLDCJC"); //燃料电池轿车
		}
		
		if(dto == null){
			dto = taxReliefModelDAO.getTaxReliefModelsByModelNumber(modelNo);
		}
		
		if(dto == null){
			//通过查询特殊车型后，如果依然为null。则返回.
			return null;
		}
		
		if(StringUtils.isBlank(dto.getFstReg())){
			return dto; //初登日期未设置，可以直接返回减免税对象
		}
		
		//目前从需求中只看到初登日期为"早于YYYY-MM-DD"的情况，故，暂时只判断这种情况.
		if(StringUtils.isNotBlank(dto.getFstReg()) &&
				dto.getFstReg().indexOf("早于") > -1){
			try {
				Date debutTime = DateUtils.getStrToDate(
						PatternUtil.getInterceptionStr(dto.getFstReg(),
								"\\d{4,}-+\\d{2,}-+\\d{2,}") + " 00:00:00", 
								DateUtils.YYYY_MM_DD_00_00_00);
				if(fstRegTime.before(debutTime)){
					//如果初登日期早于减免税的初登日期，则返回.
					return dto;
				}
				return null;
			} catch (Exception e) {
				logger.error("转化日期出错，初登日期：" + dto.getFstReg(), e);
			}
			
		}
		
		return null;
	}

}
