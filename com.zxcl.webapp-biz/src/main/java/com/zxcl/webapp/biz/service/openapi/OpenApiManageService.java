package com.zxcl.webapp.biz.service.openapi;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.webapp.dto.openapi.ApiAppDTO;
import com.zxcl.webapp.dto.openapi.ApiAppQtnConfigDTO;
import com.zxcl.webapp.dto.openapi.ApiCallPlateQueryRecordDTO;
import com.zxcl.webapp.dto.openapi.ApiCallRecordDTO;
import com.zxcl.webapp.integration.dao.openapi.OpenApiManageDAO;

@Service
public class OpenApiManageService {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private OpenApiManageDAO openApiManageDAO;
	
	public ApiAppDTO findApp(String appId) {
		return openApiManageDAO.findApp(appId);
	}
	
	public ApiAppDTO findAppByUserId(String userId) {
		return openApiManageDAO.findAppByUserId(userId);
	}
	
	public List<ApiAppQtnConfigDTO> findAppQtnConfigList(String appId) {
		return openApiManageDAO.findAppQtnConfigList(appId);
	}
	
	public long addApiCallRecord(ApiCallRecordDTO record){
		openApiManageDAO.addApiCallRecord(record);
		logger.info("新增API记录,callId=" + record.getCallId());
		return record.getCallId();
	}
	
	public void updateApiCallRecord(ApiCallRecordDTO record){
		openApiManageDAO.updateApiCallRecord(record);
	}
	
	public void addApiCallPlateQueryRecord(ApiCallPlateQueryRecordDTO record){
		openApiManageDAO.addApiCallPlateQueryRecord(record);
		logger.info("新增API车牌查询记录,callId=" + record.getCallId());
	}
}
