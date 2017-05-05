package com.zxcl.webapp.biz.action.call;

import java.util.Map;

import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

/**
 * 天平接口
 * @author zx
 *
 */
public interface HttpAxatpCallAction {
	
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
