package com.zxcl.webapp.integration.dao.openapi;

import java.util.List;

import com.zxcl.webapp.dto.openapi.ApiAppDTO;
import com.zxcl.webapp.dto.openapi.ApiAppQtnConfigDTO;
import com.zxcl.webapp.dto.openapi.ApiCallPlateQueryRecordDTO;
import com.zxcl.webapp.dto.openapi.ApiCallRecordDTO;

public interface OpenApiManageDAO {
	
	ApiAppDTO findApp(String appId);
	
	List<ApiAppQtnConfigDTO> findAppQtnConfigList(String appId);
	
	long addApiCallRecord(ApiCallRecordDTO record);
	
	void updateApiCallRecord(ApiCallRecordDTO record);
	
	void addApiCallPlateQueryRecord(ApiCallPlateQueryRecordDTO record);

	ApiAppDTO findAppByUserId(String userId);
}
