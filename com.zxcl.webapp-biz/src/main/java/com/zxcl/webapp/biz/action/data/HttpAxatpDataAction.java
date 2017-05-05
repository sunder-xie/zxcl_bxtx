package com.zxcl.webapp.biz.action.data;

import java.util.Map;

import com.zxcl.tianping_http.dto.ApplyInfoDTO;
import com.zxcl.tianping_http.dto.quoteresult.QuoteBackMessageDTO;
import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.dto.rmi.intf.common.InsuranceDTO;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

/**
 * 天平数据组装
 * @author zx
 *
 */
public interface HttpAxatpDataAction {
	/**
	 * 报价数据组装
	 * @param userId 用户ID
	 * @param insuranceDTO 保险公司
	 * @param inquiryId 询价单ID
	 * @param configMap 配置信息
	 * @return
	 * @throws Exception
	 */
	public ApplyInfoDTO quota(String userId, InsuranceDTO insuranceDTO, String inquiryId,Map<String,Object> configMap) throws ActionException;
	
	/**
	 * 报价返回数据组装
	 * @param insId 保险公司ID
	 * @param result 返回的DTO
	 * @return
	 * @throws ActionException
	 */
	public QuotaReturnDTO quotaReturn(QuoteBackMessageDTO result,String insId) throws ActionException;
}
