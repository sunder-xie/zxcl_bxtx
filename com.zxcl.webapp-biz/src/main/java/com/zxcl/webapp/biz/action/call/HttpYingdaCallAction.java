package com.zxcl.webapp.biz.action.call;



import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;
/**
 * 英大爬虫接口
 * 
 * @author zxj
 *
 */
public interface HttpYingdaCallAction {

	/**
	 * 报价
	 * @param userId 用户ID
	 * @param inquiryId 询价单ID
	 * @param insId 保险公司
	 * @return
	 * @throws Exception
	 */
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId)
			throws ActionException;
}
