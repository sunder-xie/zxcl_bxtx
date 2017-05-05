package com.zxcl.webapp.biz.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.biz.service.CarModelRelatedService;
import com.zxcl.webapp.dto.CarModelRelatedDTO;
import com.zxcl.webapp.integration.dao.CarModelRelatedDAO;

@Service
public class CarModelRelatedServiceImpl implements CarModelRelatedService {

	@Autowired
	private CarModelRelatedDAO carModelRelatedDAO;
	
	@Override
	public String getCarModelRelated(String modelCodeA, String modelCodeB) {
		
		if(StringUtils.isBlank(modelCodeA) && StringUtils.isBlank(modelCodeB)){
			
			return null;
		}
		
		if(StringUtils.isNotBlank(modelCodeA) && StringUtils.isNotBlank(modelCodeB)){
			
			return null;
		}
		
		CarModelRelatedDTO param = new CarModelRelatedDTO();
		param.setModelCodeA(modelCodeA);
		param.setModelCodeB(modelCodeB);
		
		CarModelRelatedDTO result = carModelRelatedDAO.getCarModelRelated(param);
		if(result == null){
			return null;
		}
		
		if(StringUtils.isNotBlank(modelCodeA)){
			return result.getModelCodeB();
		}else if(StringUtils.isNotBlank(modelCodeB)){
			return result.getModelCodeA();
		}else 
			return null;
	}

}
