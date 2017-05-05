package com.zxcl.webapp.biz.action.call;

import com.zxcl.webapp.biz.exception.ActionException;
import com.zxcl.webapp.dto.rmi.intf.quota.resp.QuotaReturnDTO;

/**
 * 人保
 * @author Li xiaokang
 *
 */
public interface HttpXdCallAction {

	/**
	 * 报价
	 * @param userId
	 * @param inquiryId
	 * @param insId
	 * @return
	 */
	public QuotaReturnDTO quotas(String userId, String inquiryId, String insId) throws ActionException;

}
