package com.zxcl.webapp.biz.action.call.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxcl.bxtx.call.InsuranceAPI;
import com.zxcl.bxtx.dto.intf.InquiryDTO;
import com.zxcl.bxtx.dto.intf.OrderQueryReturnDTO;
import com.zxcl.bxtx.dto.intf.PayReturnDTO;
import com.zxcl.bxtx.dto.intf.QuoteReturnDTO;
import com.zxcl.bxtx.dto.intf.UnderwriteReturnDTO;
import com.zxcl.bxtx.dto.intf.VehicleReturnListDTO;
import com.zxcl.webapp.biz.service.ParamConfigService;
import com.zxcl.webapp.dto.ParamConfigDTO;
import com.zxcl.webapp.util.SpringContextUtil;

@Service("insuranceAPIImpl")
public class InsuranceAPIImpl implements InsuranceAPI {

	@Autowired
	private ParamConfigService paramConfigService;


	@Override
	public VehicleReturnListDTO vhlQuery(String carName) {
		ParamConfigDTO vehicleQueryInsId = paramConfigService.getParamConfigByKey("VEHICLE_QUERY_INSID");
		InsuranceAPI insuranceAPI = (InsuranceAPI) SpringContextUtil.getBean(vehicleQueryInsId.getValue()+"InsuranceAPI");
		if(insuranceAPI == null ){
			VehicleReturnListDTO vehicleReturnListDTO = new VehicleReturnListDTO();
			vehicleReturnListDTO.setErrorCode(InsuranceAPI.ERROR);
			vehicleReturnListDTO.setErrorMessage("未配置接口");
			return vehicleReturnListDTO;
		}
		return insuranceAPI.vhlQuery(carName);
	}

	@Override
	public QuoteReturnDTO quote(InquiryDTO inquiryDTO,
			Map<String, String> configMap) {
		InsuranceAPI insuranceAPI = (InsuranceAPI) SpringContextUtil.getBean(inquiryDTO.getInsId()+"InsuranceAPI");
		if(insuranceAPI == null ){
			QuoteReturnDTO returnDTO = new QuoteReturnDTO();
			returnDTO.setErrorCode(InsuranceAPI.ERROR);
			returnDTO.setErrorMessage("未配置接口");
			return returnDTO;
		}
		return insuranceAPI.quote(inquiryDTO, configMap);
	}

	@Override
	public UnderwriteReturnDTO underwrite(InquiryDTO inquiryDTO,
			Map<String, String> configMap) {
		InsuranceAPI insuranceAPI = (InsuranceAPI) SpringContextUtil.getBean(inquiryDTO.getInsId()+"InsuranceAPI");
		return insuranceAPI.underwrite(inquiryDTO, configMap);
	}

	@Override
	public PayReturnDTO pay(InquiryDTO inquiryDTO, Map<String, String> configMap) {
		InsuranceAPI insuranceAPI = (InsuranceAPI) SpringContextUtil.getBean(inquiryDTO.getInsId()+"InsuranceAPI");
		return insuranceAPI.pay(inquiryDTO, configMap);
	}

	@Override
	public OrderQueryReturnDTO orderQuery(InquiryDTO inquiryDTO,
			Map<String, String> configMap, String queryState) {
		InsuranceAPI insuranceAPI = (InsuranceAPI) SpringContextUtil.getBean(inquiryDTO.getInsId()+"InsuranceAPI");
		return insuranceAPI.orderQuery(inquiryDTO, configMap, queryState);
	}

}
