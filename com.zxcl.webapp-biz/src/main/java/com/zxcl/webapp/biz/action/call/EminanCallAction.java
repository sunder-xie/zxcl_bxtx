package com.zxcl.webapp.biz.action.call;

import java.util.Map;

import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
/**
 * 民安保险接口
 * @author zxcl
 *
 */
public interface EminanCallAction {

	/**
	 * 车型
	 * @param userId 用户ID
	 * @param carName 车型名称
	 * @param ins 保险公司
	 * @param inquiryId 询价单ID
	 * @param configMap 配置信息
	 * @return 
	 * @throws ActionException
	 */
	public Map<String,Object> vehicleQuery(String userId, String carName, InsuranceDTO ins,String inquiryId,Map<String,Object> configMap)
			throws ActionException;
	
	
	/**
	 * 报价
	 * @param userId 用户ID
	 * @param inquiryId 询价单ID
	 * @param insId 保险公司ID
	 * @param configMap 配置信息
	 * @return
	 * @throws ActionException
	 */
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId,Map<String,Object> configMap)
			throws ActionException;
}
